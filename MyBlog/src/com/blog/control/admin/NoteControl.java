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
import com.blog.ORFilter;
import com.blog.Order;
import com.blog.Page;
import com.blog.QueryHelper;
import com.blog.control.BaseControl;
import com.blog.entity.Admin;
import com.blog.entity.Data;
import com.blog.entity.Note;
import com.blog.entity.NoteTabBrige;
import com.blog.enumer.Operator;
import com.blog.service.DataService;
import com.blog.service.NoteService;
import com.blog.service.NoteTabBrigeService;
import com.blog.util.ActionUtil;
import com.blog.util.Message;
import com.blog.util.SnowFlakeGenerator;
import com.blog.util.sql.EqAdapter;
/**
 * <b>一句话描述该类</b>
 * <p>
 * 描述:<br>
 * 
 * @author 威 
 * <br>2020年5月28日 下午4:16:01 
 * @see
 * @since 1.0
 */
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
		Admin admin = (Admin) request.getSession().getAttribute(Constant.USER_CONTEXT);
		
		// 拼接 上一次请求的 条件
		StringBuilder query = new StringBuilder("");
		Map<String, String> parame = getRequestParameterMap(request);
//		query.append("type=" + type).append("&");
		for(Map.Entry<String, String> item : parame.entrySet()) 
			if(item.getValue() != null) query.append(item.getKey() + "=" + item.getValue()).append("&");
		if(parame.size() > 0) query.delete(query.length()-1, query.length());// 删除多余符号
		// 拼接 上一次请求的 条件
		// 刷新的时候能够衔接上一次的条件
		model.addAttribute("query", query.toString()); 
		
		List<Note> list = getNoteOfCommon(type, parame, request, page, admin);
		JSONArray jsonArray = new JSONArray();
		for(Note item : list){
			JSONObject jsonObject = jsonToJSONObject(item); 
			jsonObject.put("author", item.getAdmin_infor().getName_()); 
			jsonArray.add(jsonObject);
		} 
		model.addAttribute("notes", jsonArray);
		
		// 标签集 用于标签查找
		List<Data> listData = dataServiceImpl.gets(singleOfEqString("type", "note_tab"));
		model.addAttribute("all", listData); 
		
		model.addAttribute("adminId", admin.getId());
		model.addAttribute("author", noteServiceImpl.findNicknameById(admin.getId()));	
		
//		List<NoteTabBrige> list2 = noteTabBrigeServiceImpl.gets(singleOfEqString("admin_id", admin.getId()));
		//model.addAttribute("noteTabs", list2);// 标签集 用于note获取对应的标签
		//model.addAttribute("noteTabsJSON", net.sf.json.JSONArray.fromObject(list2).toString());// 转换json字符串供前台更多加载时标签获取的使用 用于note获取对应的标签
		return "admin/note/show";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/showByTab.chtml") 
	public String showByTab(HttpServletRequest request, ModelMap model, String type, Page page) throws IOException{
		Admin admin = (Admin) request.getSession().getAttribute(Constant.USER_CONTEXT);
		
		int limit = page.getLimit();
		int pageNum = page.getPage();
		
		// 定义 and 关联条件
		page.addFilter(new Filter("a.id", Operator.eq, "b.note_id", 
				Filter.IGNORE_TYPE)); // Qu_type_eq_s
		page.addFilter(new Filter("c.id", Operator.eq, "b.note_tab_id", 
				Filter.IGNORE_TYPE)); // Qu_type_eq_s
		Map<String, String> parame = getRequestParameterMap(request);
		for(Map.Entry<String, String> item : parame.entrySet()){	// 拼接标签id
			if("on".equals(item.getValue()))
				// 定义 or 关联条件
				page.addORFilters(new ORFilter("c.id", Operator.eq, item.getKey(), 
						ORFilter.IGNORE_TYPE)); 
		}
			
		QueryHelper queryHelper = new QueryHelper();
		// 别名，详细的说是前端设定为createDate 实际数据库为 create_date 
		queryHelper.addCloumnAlias("createDate", "create_date"); 
		// 绑定前台参数
		queryHelper.paramBind(request, page);	
		// 排序
		page.addOrder(Order.desc("a.create_date"));
		
		StringBuilder sql = new StringBuilder();
		sql
		.append("SELECT DISTINCT a.id, a.`name`, a.update_date, a.create_date, a.status, a.tags, a.content, a.admin_id ")
		.append(queryHelper.buildForm("note a, note_tab_brige b, data c"))
		.append(queryHelper.buildQuery(page.getFilters(), page.getORFilters()))
		.append(" AND !(a.`admin_id` <> '"+admin.getId()+"' AND a.`status` = '01') ")
		.append(queryHelper.buildOrder(page.getOrders(), page.getAlias()))
		.append(queryHelper.buildLimit(pageNum, limit));
		//.append(queryHelper.buildAllQuery(page, pageNum, limit));
		
		model.addAttribute("notes", noteServiceImpl.find(sql.toString()));
		
		// 标签集 用于标签查找
		List<Data> listData = dataServiceImpl.gets(singleOfEqString("type", "note_tab"));
		model.addAttribute("all", listData); 
		
		model.addAttribute("adminId", admin.getId());
		return "admin/note/show";
	}
	
	/*public String show(HttpServletRequest request, ModelMap model, String type, Page page) throws IOException{
		// 对标签进行更新
		List<Note> temps = noteServiceImpl.getAll();   
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
		} 
		
		Admin admin = (Admin) request.getSession().getAttribute(Constant.USER_CONTEXT);
		
		// 拼接 上一次请求的 条件
		StringBuilder query = new StringBuilder("");
		Map<String, String> parame = getRequestParameterMap(request);
		for(Map.Entry<String, String> item : parame.entrySet()) 
			query.append(item.getKey() + "=" + item.getValue()).append("&");
		if(parame.size() > 0) query.delete(query.length()-1, query.length());// 删除多余符号
		log.info("queryStr "+query.toString());
		// 拼接 上一次请求的 条件
		// 刷新的时候能够衔接上一次的条件
		model.addAttribute("query", query.toString()); 
		
		model.addAttribute("notes", getNoteOfCommon(type, parame, request, page, admin));
		
		// 标签集 用于标签查找
		List<Data> listData = dataServiceImpl.gets(singleOfEqString("type", "note_tab"));
		model.addAttribute("all", listData); 
		
		model.addAttribute("adminId", admin.getId());
		
//		List<NoteTabBrige> list2 = noteTabBrigeServiceImpl.gets(singleOfEqString("admin_id", admin.getId()));
		//model.addAttribute("noteTabs", list2);// 标签集 用于note获取对应的标签
		//model.addAttribute("noteTabsJSON", net.sf.json.JSONArray.fromObject(list2).toString());// 转换json字符串供前台更多加载时标签获取的使用 用于note获取对应的标签
		return "admin/note/show";
	}*/
	
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
	@SuppressWarnings("unchecked")
	protected List<Note> getNoteOfCommon(String type, Map<String, String> parame, 
			HttpServletRequest request, Page page, Admin admin) {
		
		int limit = page.getLimit();
		int pageNum = page.getPage();
		
		QueryHelper queryHelper = new QueryHelper();
		// 前端设定为createDate 实际数据库为 create_date 
		queryHelper.addCloumnAlias("createDate", "create_date"); 
		// 获取前台参数
		queryHelper.paramBind(request, page);	
		// 排序
		page.addOrder(Order.desc("a.create_date"));
				
		StringBuilder sql = new StringBuilder();
		page.addFilter(new Filter("a.admin_id", Operator.eq, "d.admin_id", 
				Filter.IGNORE_TYPE)); 
		// 如果有标签则进行标签查询 当type=‘2’时
		if("2".equals(type)){
			// 定义 and 关联条件 多表查询的关联
			page.addFilter(new Filter("a.id", Operator.eq, "b.note_id", 
					Filter.IGNORE_TYPE)); // Qu_type_eq_s
			page.addFilter(new Filter("c.id", Operator.eq, "b.note_tab_id", 
					Filter.IGNORE_TYPE));  	
			for(Map.Entry<String, String> item : parame.entrySet()){	// 拼接标签id
				if("on".equals(item.getValue()))
					// 定义 or 关联条件
					page.addORFilters(new ORFilter("c.id", Operator.eq, item.getKey(), 
							ORFilter.IGNORE_TYPE)); 
			}
			// 查询三个表确认notes
			sql.append("SELECT DISTINCT a.id, d.name_, a.`name`, a.update_date, a.create_date, a.status, a.tags, a.content, a.admin_id ")
			.append(queryHelper.buildForm("note a, note_tab_brige b, data c, admin_infor d"))
			.append(queryHelper.buildQuery(page.getFilters(), page.getORFilters()));
			
		}
		// 一般查询
		else {
			sql.append("SELECT DISTINCT a.id, d.name_, a.`name`, a.update_date, a.create_date, a.status, a.tags, a.content, a.admin_id FROM note a, admin_infor d")
			.append(queryHelper.buildQuery(page.getFilters()));
		}
		// 反向获取符合的条件
		sql.append(" AND !(a.`admin_id` <> '"+admin.getId()+"' AND a.`status` = '01') ")
		.append(queryHelper.buildOrder(page.getOrders(), page.getAlias()))
		.append(queryHelper.buildLimit(pageNum, limit));
		log.info(sql.toString());
		return noteServiceImpl.show(sql.toString());
		
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
				model.addAttribute("note", 
						noteServiceImpl.get(singleOfEqString("id", id)));
			}
		}		
		return "admin/note/save_or_update";
	} 
	
	// 添加
	@RequestMapping("add.do")
	@ResponseBody
	public Object add(Note t, String tabs, HttpServletRequest request) throws IOException{ 
		System.out.println("添加接收参数：name = "+t.getName()); 
		
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
