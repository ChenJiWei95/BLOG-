package com.blog.control.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blog.Constant;
import com.blog.Filter;
import com.blog.Order;
import com.blog.Page;
import com.blog.QueryHelper;
import com.blog.control.BaseControl;
import com.blog.entity.Admin;
import com.blog.entity.CMessage;
import com.blog.entity.Data;
import com.blog.entity.Note;
import com.blog.entity.NoteTabBrige;
import com.blog.service.DataService;
import com.blog.service.NoteService;
import com.blog.service.NoteTabBrigeService;
import com.blog.util.ActionUtil;
import com.blog.util.Message;
import com.blog.util.SnowFlakeGenerator;
import com.blog.util.sql.EqAdapter;

@Controller
@RequestMapping("/admin/note")
public class NoteControl extends BaseControl{
	
	private static Logger log = Logger.getLogger(NoteControl.class); // 日志对象
	
	@Autowired
	private NoteService noteServiceImpl;
	@Autowired
	private DataService dataServiceImpl;
	@Autowired
	private NoteTabBrigeService noteTabBrigeServiceImpl;
	
	// 返回 页面 
	@RequestMapping("/listview.chtml") 
	public String listview1(ModelMap model){
		return "admin/note/list"; 
	}
	
	@RequestMapping("/show.chtml") 
	public String show(HttpServletRequest request, ModelMap model, String type, Page page) throws IOException{
		// 对标签进行更新
		/*List<Note> temps = noteServiceImpl.getAll();   
		for(Note item : temps) {
			List<NoteTabBrige> tempss = noteTabBrigeServiceImpl.gets(singleOfEqString("note_id", item.getId()));
			StringBuilder tempsss = new StringBuilder("");
			for(NoteTabBrige item_ : tempss) {
				tempsss.append(item_.getName()).append(",");
			}
			Note note = new Note();
			note.setTags(tempsss.toString());
			noteServiceImpl.update(note, singleOfEqString("id", item.getId()));
			log.info("特殊更新完毕====================="+item.getName());
		} */
		
		Admin admin = (Admin) request.getSession().getAttribute(Constant.USER_CONTEXT);
		
		// 拼接 上一次请求的 条件
		StringBuilder query = new StringBuilder("");
		Map<String, String> parame = getRequestParameterMap(request);
		for(Map.Entry<String, String> item : parame.entrySet()) 
			query.append(item.getKey() + "=" + item.getValue()).append("&");
		if(parame.size() > 0) query.delete(query.length()-1, query.length());// 删除多余符号
		log.info("queryStr "+query.toString());
		model.addAttribute("query", query.toString()); // 拼接 上一次请求的 条件
		
		model.addAttribute("notes", getNoteOfCommon(type, parame, request, page, admin));
		
		// 标签集 用于标签查找
		List<Data> listData = dataServiceImpl.gets(singleOfEqString("type", "note_tab"));
		model.addAttribute("all", listData); 
		
		model.addAttribute("adminId", admin.getId());
		
//		List<NoteTabBrige> list2 = noteTabBrigeServiceImpl.gets(singleOfEqString("admin_id", admin.getId()));
		//model.addAttribute("noteTabs", list2);// 标签集 用于note获取对应的标签
		//model.addAttribute("noteTabsJSON", net.sf.json.JSONArray.fromObject(list2).toString());// 转换json字符串供前台更多加载时标签获取的使用 用于note获取对应的标签
		return "admin/note/show";
	}

	protected List<Note> getNoteByNameAndDate(HttpServletRequest request, Page page, Admin admin) {
		QueryHelper queryHelper = new QueryHelper(); 
		queryHelper.addCloumnAlias("createDate", "create_date"); // 前端设定为createDate 实际数据库为 create_date 
		queryHelper.paramBind(request, page);	// 获取前台参数
		// 自定义查询条件 admin_id = ‘ ’
		/*Filter f = new Filter();
		f.setOperator(com.blog.Filter.Operator.eq);
		f.setProperty("admin_id");
		f.setValue(admin.getId());
		page.addFilter(f);	*/	
		page.addOrder(Order.desc("create_date"));		// 排序
		// 自定义查询语句拼接 前台可以任意传递参数 并且参数自带条件语义
		// 进行分页
		List<Note> resultList = noteServiceImpl.find("SELECT * FROM note "+
		queryHelper.buildAllQuery(page)+ 
		" "+EqAdapter.SQL_LIMIT + (page.getPage()-1)*page.getLimit() + "," + page.getLimit());
		return resultList;
	}

	//  根据名称模糊查找和标签查找的公共方法
	protected List<Note> getNoteOfCommon(String type, Map<String, String> parame, 
			HttpServletRequest request, Page page, Admin admin) {
		// 清除多余的字段
		
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.addCloumnAlias("createDate", "create_date"); // 前端设定为createDate 实际数据库为 create_date 
		queryHelper.paramBind(request, page);	// 获取前台参数
		
		StringBuilder tagQueryStr = new StringBuilder("");
		for(Map.Entry<String, String> item : parame.entrySet()){	// 拼接标签id
			if("on".equals(item.getValue()))
				tagQueryStr.append(singleOfEq("c.id", "'"+item.getKey()+"'")).append(" OR ");
		}
		StringBuilder sql = new StringBuilder();
		if(tagQueryStr.length() > 0 || "2".equals(type)) // 如果有标签则进行标签查询 当type=‘2’时
			sql.append("SELECT DISTINCT a.id, a.`name`, a.update_date, a.create_date, a.status, a.tags, a.content, a.admin_id ")
			.append("FROM note a, note_tab_brige b, `data` c ");// 查询三个表确认notes
		else // 一般查询
			sql.append("SELECT * FROM note a ");
		page.setAlias("a");
		sql.append(queryHelper.buildAllQuery(page));
		if(tagQueryStr.length() > 0) // 如果有标签则进行标签查询
			sql.append(" AND a.id = b.note_id")
			.append(" AND c.id = b.note_tab_id");
			/*.append(" AND a.admin_id='"+admin.getId()+"'")*/
		if(tagQueryStr.length() > 0)
			sql.append(" AND ("+tagQueryStr.delete(tagQueryStr.length()-4, tagQueryStr.length()).toString()+")");// 
		sql.append(" ORDER BY a.create_date DESC ")
			.append(" "+EqAdapter.SQL_LIMIT + (page.getPage()-1)*page.getLimit() + "," + page.getLimit());
		return noteServiceImpl.find(sql.toString());
		
	}
	
	// 返回 页面 
	@RequestMapping("/save_or_update.chtml") 
	public String save_or_update(String type, String id, HttpServletRequest request, ModelMap model){
		
		// 添加 查找所有页面传入 
		List<Data> listData = dataServiceImpl.gets(singleOfEqString("type", "note_tab"));
		model.addAttribute("all", listData);
		// 0 添加  1/2 修改
		if("0".equals(type)){
			model.addAttribute("type", true);
		}else if ("1".equals(type)|| "2".equals(type)){
			// 修改 查找已授权的页面传入 获取appid
			Admin admin = (Admin) request.getSession().getAttribute(Constant.USER_CONTEXT); 
			List<NoteTabBrige> list_ = noteTabBrigeServiceImpl.gets(
					singleOfEqString("admin_id", admin.getId())+" AND "+singleOfEqString("note_id", id));
			model.addAttribute("seleteds", list_);
			model.addAttribute("type", false);
			if("2".equals(type)) { // show.jsp 页面调用修改页面时 所传递的值；旨在获取note内容
				model.addAttribute("content", 
						noteServiceImpl.get(singleOfEqString("id", id)).getContent());
			}
		}		
		return "admin/note/save_or_update";
	} 
	
	// 添加
	@RequestMapping("add.do")
	@ResponseBody
	public Object add(Note t, String tabs, HttpServletRequest request) throws IOException{ 
		System.out.println("添加接收参数："+t); 
		
		try{
			StringBuilder tags = new StringBuilder("");
			t.setUpdate_date("xxxx-xx-xx xx:xx:xx");
			t.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
			Admin admin = (Admin) request.getSession().getAttribute(Constant.USER_CONTEXT);
			if(tabs != null && !"".equals(tabs)) {// 根据tabs创建新标签
				String[] arr = tabs.split(",");
				for(String item : arr) {
					//log.info("',' arr:"+item);
					Data dataTable = dataServiceImpl.get(singleOfEqString("name", item));
					//log.info("==============data:"+dataTable);
					if(dataTable == null) {
						dataTable = new Data();
						dataTable.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
						dataTable.setType("note_tab");
						dataTable.setName(item);
						dataTable.setDesc("note标签");
						dataTable.setCreate_time(getNowTime());
						dataServiceImpl.insert(dataTable);
					}
					
					NoteTabBrige newTab = new NoteTabBrige();
					newTab.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
					newTab.setAdmin_id(admin.getId());
					newTab.setName(item);
					newTab.setNote_id(t.getId());
					newTab.setNote_tab_id(dataTable.getId());
					noteTabBrigeServiceImpl.insert(newTab);
					
					tags.append(newTab.getName()).append(",");
				}
			}
			
			Map<String, String> params = getRequestParameterMap(request); 
			
			// 获取check表单值
			for(Map.Entry<String, String> item : params.entrySet()){	// 拼接标签id
				if("on".equals(item.getValue())) {
					String key = item.getKey();
					NoteTabBrige newTab = new NoteTabBrige();
					newTab.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
					newTab.setAdmin_id(admin.getId());
					newTab.setName(key.substring(key.indexOf("|")+1));
					newTab.setNote_id(t.getId());
					newTab.setNote_tab_id(key.substring(0, key.indexOf("|")));
					noteTabBrigeServiceImpl.insert(newTab);
					
					tags.append(newTab.getName()).append(",");
				}
			}
			
			t.setCreate_date(getNowTime());
			t.setAdmin_id(admin.getId());
			t.setTags(tags.toString());
			noteServiceImpl.insert(t);
			
			return com.blog.util.Message.success("添加成功", null);
		}catch(Exception e){
			e.printStackTrace();
			return com.blog.util.Message.error("添加失败，"+e.getMessage(), null);
		}
	}
	
	/**
	 * 删除 
	 * @param menu
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("remove.do")
	@ResponseBody
	public Object remove(HttpServletRequest request) throws IOException{
		
		// 判断token是否正确  删除admin 和 adminInfor
		try {
			String str = ActionUtil.read(request);
			log.info(str);
			JSONArray json = JSONObject.parseArray(str);
			log.info(json);
			StringBuffer sb = new StringBuffer();
			
			for(int i = 0; i < json.size(); i++) {
				JSONObject object = json.getJSONObject(i);
				sb.append(singleOfEqString("id", object.getString("id"))).append(" OR ");
			}
			
			log.info(sb.toString());
			
			if(json.size() > 0) {
				sb.delete(sb.length()-4, sb.length());
				noteServiceImpl.delete(sb.toString());
			}
			return Message.success("已删除", null);
		}catch(Exception e) {
			e.printStackTrace();
			return Message.success("删除失败，"+e.getMessage(), null);
		} 
	}
	
	/**
	 * 修改
	 * @param menu
	 * @param spread
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("update.do")
	@ResponseBody
	public Object update(Note t, HttpServletRequest request){ 
		try {
			System.out.println("修改接收参数："+t); 
			
			StringBuilder tags = new StringBuilder(t.getTags());
			// 根据admin ID 对账号和进行修改 根据id 对adminInfor信息进行修改
			t.setUpdate_date(getNowTime());
			
			Map<String, String> params = getRequestParameterMap(request); 
			Admin admin = (Admin) request.getSession().getAttribute(Constant.USER_CONTEXT);
			List<NoteTabBrige> list = noteTabBrigeServiceImpl.gets(
					singleOfEqString("admin_id", admin.getId())+" AND "+singleOfEqString("note_id", t.getId()));
			boolean isContain;
			
			for(Map.Entry<String, String> item : params.entrySet()){	// 拼接标签id
				if("on".equals(item.getValue())) {
					isContain = false;
					String key = item.getKey();
					for(NoteTabBrige tab : list) {
						if(tab.getNote_tab_id().equals(key.substring(0, key.indexOf("|")))) {
							isContain = true;
							break;
						}
					}
					if(!isContain){
						NoteTabBrige newTab = new NoteTabBrige();
						newTab.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
						newTab.setAdmin_id(admin.getId());
						newTab.setName(key.substring(key.indexOf("|")+1));
						newTab.setNote_id(t.getId());
						newTab.setNote_tab_id(key.substring(0, key.indexOf("|")));
						noteTabBrigeServiceImpl.insert(newTab);
						
						tags.append(newTab.getName()).append(",");
						 
					}
				}
			} 
			
			for(NoteTabBrige tab : list) {
				isContain = false;
				for(Map.Entry<String, String> item : params.entrySet()){	// 拼接标签id
					String key = item.getKey();
					if("on".equals(item.getValue()) 
							&& tab.getNote_tab_id().equals(key.substring(0, key.indexOf("|")))) {
						isContain = true;
						break;
					}
				} 
				if(!isContain){
					int index = tags.indexOf(tab.getName());
					if(index != -1) tags.delete(index, index+tab.getName().length()+1);
					noteTabBrigeServiceImpl.delete("id = "+ tab.getId());
				}
			}	
			
			t.setTags(tags.toString());
			log.info(tags.toString());
			noteServiceImpl.update(t, singleOfEqString("id", t.getId())); 
			
			return Message.success("修改成功", null);
		} catch(DataIntegrityViolationException e) { 
			e.printStackTrace();
			return Message.success("修改失败，数据可能过长；"+e.getMessage(), null);
		} catch(RuntimeException e) {
			e.printStackTrace();
			return Message.success("请求失败，运行异常； "+e.getMessage(), null);
		}
	} 
	
	/**
	 * 初始化
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("list.do")
	@ResponseBody
	public Object list(String type, Page page, HttpServletRequest request) throws IOException{
		
		try { 
			Admin admin = (Admin) request.getSession().getAttribute(Constant.USER_CONTEXT);
			// 根据实际情况 更多加载需要保留原有查询状态 所以
			// 构建返回数据
			List<Note> resultList = getNoteOfCommon(type, 
					getRequestParameterMap(request), 
					request, 
					page, 
					admin);
			page.setData(resultList);
			page.setMsg(resultList.size() > 0 ? "已新增了"+resultList.size()+"条。" : "没有更多了！");
			page.setCount(0);
			page.setCode("0"); 
			return page;
		}catch(Exception e) {
			e.printStackTrace();
			return com.blog.util.Message.success("请求失败，"+e.getMessage(), null);
		}
	}
	protected String getRequestPostStr(HttpServletRequest request)  
            throws IOException {  
        byte buffer[] = getRequestPostBytes(request);  
        if(buffer == null) return "";
        
        String charEncoding = request.getCharacterEncoding();  
        if (charEncoding == null) {  
            charEncoding = "UTF-8";  
        }  
        
        return new String(buffer, charEncoding);  
    }
	protected byte[] getRequestPostBytes(HttpServletRequest request)  
            throws IOException {  
        int contentLength = request.getContentLength();  
        if(contentLength<0){  
            return null;  
        }  
        byte buffer[] = new byte[contentLength];  
        for (int i = 0; i < contentLength;) {  
  
            int readlen = request.getInputStream().read(buffer, i,  
                    contentLength - i);  
            if (readlen == -1) {  
                break;  
            }  
            i += readlen;  
        }  
        return buffer;  
    }  
	
	
}
