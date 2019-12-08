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
import com.blog.entity.Data;
import com.blog.entity.Note;
import com.blog.entity.NoteTabBrige;
import com.blog.service.DataService;
import com.blog.service.NoteService;
import com.blog.service.NoteTabBrigeService;
import com.blog.util.ActionUtil;
import com.blog.util.Message;
import com.blog.util.SnowFlakeGenerator;

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
	public String show(HttpServletRequest request, ModelMap model, String type){
		Admin admin = (Admin) request.getSession().getAttribute(Constant.USER_CONTEXT);
		List<Note> list = null;
		log.info(type);
		if ("2".equals(type)){
			Map<String, String> parame = getRequestParameterMap(request);
			parame.remove("type");
			StringBuilder sb = new StringBuilder();
			for(String item : parame.keySet()){
				sb.append(singleOfEq("c.id", item)).append(" OR ");
			}
			if(parame.size() > 0) sb.delete(sb.length()-4, sb.length());
			list = noteServiceImpl.find("SELECT a.id, a.`name`, a.update_date, a.create_date, a.content, a.admin_id FROM note a, note_tab_brige b, `data` c where 1=1 and a.id = b.note_id AND c.id = b.note_tab_id AND a.admin_id='"+admin.getId()+"' AND ("+sb.toString()+") ORDER BY a.create_date DESC ");
		} else {
			// 根据所传条件进行查找 
			QueryHelper queryHelper = new QueryHelper();
			Page page = new Page(); 
			
			// 制定排序
			page.addOrder(Order.desc("create_date"));
			
			// 自定义查询条件 admin_id = ‘ ’
			Filter f = new Filter();
			f.setOperator(com.blog.Filter.Operator.eq);
			f.setProperty("admin_id");
			f.setValue(admin.getId());
			page.addFilter(f);
			
			queryHelper.addCloumnAlias("createDate", "create_date");// 前端设定为createDate 实际数据库为 create_date 
			queryHelper.paramBind(request, page);// 初始化数据
			String sql = queryHelper.buildAllQuery(page);// 生成sql部分语句
			log.info("=================show.jsp 以下是查询语句=============");
			log.info(sql);
			
			list = noteServiceImpl.find("SELECT * FROM note"+sql);			
		}
		List<NoteTabBrige> list2 = noteTabBrigeServiceImpl.gets(singleOfEqString("admin_id", admin.getId()));
		List<Data> listData = dataServiceImpl.gets(singleOfEqString("type", "note_tab"));
		model.addAttribute("notes", list);
		model.addAttribute("noteTabs", list2);
		model.addAttribute("all", listData); 
		return "admin/note/show"; 
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
			t.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
			Admin admin = (Admin) request.getSession().getAttribute(Constant.USER_CONTEXT);
			if(tabs != null && !"".equals(tabs)) {
				String[] arr = tabs.split(",");
				for(String item : arr) {
					log.info("',' arr:"+item);
					Data ded = dataServiceImpl.get(singleOfEqString("name", item));
					log.info("==============data:"+ded);
					if(ded == null) {
						ded = new Data();
						ded.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
						ded.setType("note_tab");
						ded.setName(item);
						ded.setDesc("note标签");
						ded.setCreate_time(getNowTime());
						dataServiceImpl.insert(ded);
					}
					
					NoteTabBrige newTab = new NoteTabBrige();
					newTab.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
					newTab.setAdmin_id(admin.getId());
					newTab.setName(item);
					newTab.setNote_id(t.getId());
					newTab.setNote_tab_id(ded.getId());
					noteTabBrigeServiceImpl.insert(newTab);
				}
			}
			
			Map<String, String> params = getRequestParameterMap(request);
			params.remove("id"); 
			params.remove("create_date");  
			params.remove("update_date");  
			params.remove("name");
			params.remove("admin_id");
			params.remove("content");
			params.remove("tabs");
			
			for(String item : params.keySet()) {
				NoteTabBrige newTab = new NoteTabBrige();
				newTab.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
				newTab.setAdmin_id(admin.getId());
				newTab.setName(item.substring(item.indexOf("|")+1));
				newTab.setNote_id(t.getId());
				newTab.setNote_tab_id(item.substring(0, item.indexOf("|")));
				noteTabBrigeServiceImpl.insert(newTab);
			}
			
			t.setCreate_date(getNowTime());
			t.setAdmin_id(admin.getId());
			noteServiceImpl.insert(t);
			
			return com.blog.util.Message.success("请求成功", null);
		}catch(Exception e){
			e.printStackTrace();
			return com.blog.util.Message.error("请求失败，"+e.getMessage(), null);
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
			return Message.success("请求成功", null);
		}catch(Exception e) {
			e.printStackTrace();
			return Message.success("请求失败，"+e.getMessage(), null);
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
			// 根据admin ID 对账号和进行修改 根据id 对adminInfor信息进行修改
			t.setUpdate_date(getNowTime());
			noteServiceImpl.update(t, singleOfEqString("id", t.getId())); 
			
			Map<String, String> params = getRequestParameterMap(request);
			params.remove("id"); 
			params.remove("create_date");  
			params.remove("update_date");  
			params.remove("name");
			params.remove("admin_id");
			params.remove("content");
			
			Admin admin = (Admin) request.getSession().getAttribute(Constant.USER_CONTEXT);
			List<NoteTabBrige> list = noteTabBrigeServiceImpl.gets(
					singleOfEqString("admin_id", admin.getId())+" AND "+singleOfEqString("note_id", t.getId()));
			boolean isContain;
			for(String item : params.keySet()) {
				isContain = false;
				for(NoteTabBrige tab : list) {
					if(tab.getNote_tab_id().equals(item.substring(0, item.indexOf("|")))) {
						isContain = true;
						break;
					}
				}
				if(!isContain){
					NoteTabBrige newTab = new NoteTabBrige();
					newTab.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
					newTab.setAdmin_id(admin.getId());
					newTab.setName(item.substring(item.indexOf("|")+1));
					newTab.setNote_id(t.getId());
					newTab.setNote_tab_id(item.substring(0, item.indexOf("|")));
					noteTabBrigeServiceImpl.insert(newTab);
					 
				}
			}
			for(NoteTabBrige tab : list) {
				isContain = false;
				for(String item : params.keySet()) {
					if(tab.getNote_tab_id().equals(item.substring(0, item.indexOf("|")))){
						isContain = true;
						break;
					}
				}
				if(!isContain){
					noteTabBrigeServiceImpl.delete("note_tab_id = "+ tab.getNote_tab_id());
				}
			}	
			return Message.success("请求成功", null);
		} catch(DataIntegrityViolationException e) {
			return Message.success("修改失败，数据可能过长；"+e.getMessage(), null);
		} catch(RuntimeException e) {
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
	public Object init() throws IOException{
		try {
			List<Note> list = noteServiceImpl.getAll();
			return Message.success("请求成功", listToJSONArray(list));
		}catch(Exception e) {
			return Message.success("请求失败，"+e.getMessage(), null);
		}
	}
	
	
	
}
