package com.blog.control.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blog.control.BaseControl;
import com.blog.entity.Menu;
import com.blog.entity.WebsiteBase;
import com.blog.service.MenuService;
import com.blog.service.WebsiteBaseService;

@Controller
// 数据字典
@RequestMapping("/admin/data")
public class DataControl extends BaseControl{
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private MenuService menuServiceImpl;
	
	@Autowired
	private WebsiteBaseService<WebsiteBase, Object> websiteBaseServiceImpl;
	
	// 返回 页面 
	@RequestMapping("/listview.chtml") 
	public String listview1(HttpServletRequest request, String agentno, ModelMap model){
		String base = basePath(request);
		return "../../views/admin/data/list";
	}
	// 返回 页面 
	@RequestMapping("/save_or_update.chtml") 
	public String save_or_update(HttpServletRequest request, String agentno,ModelMap model){
		String base = basePath(request);
		return "../../views/admin/data/save_or_update";
	} 
	
	// 添加
	@SuppressWarnings("unchecked")
	@RequestMapping("add.do")
	@ResponseBody
	public Object add(Menu menu) throws IOException{ 
		System.out.println("添加接收参数："+menu.toString()); 
		 
		menu.setCreate_time(com.blog.util.TimeUtil.getDatetime());  
		menu.setRelate_id(menu.getRelate_id() == null ? "" : menu.getRelate_id());
		menu.setPriority(menu.getPriority() == null || "".equals(menu.getPriority()) ? "5" : menu.getPriority());
		menuServiceImpl.insert(menu);
		
		JSONObject object = jsonToJSONObject(menu);
		object.remove("name");
		object.put("label", menu.getName());
		object.put("isTab", menu.getUrl().indexOf("####") == -1 ? false : true); 
		
		return object;
	}
	
	/**
	 * 递归 删除关联菜单
	 * @param id
	 */
	@SuppressWarnings("unchecked")
	protected void remove_(String id){
		// 递归删除关联
		Menu m = new Menu();
		m.setRelate_id(id);
		List<Menu> list = menuServiceImpl.gets(m);
		Map<String, Object> eq = null;
		if(list != null && list.size() > 0){
			eq = new HashMap<>(1);
			for(Menu item : list){
				remove_(item.getId());
				eq.put("id", item.getId());
				menuServiceImpl.delete(eq);
			}
		}
		list = null;		
	}
	
	/**
	 * 删除 
	 * @param menu
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("remove.do")
	@ResponseBody
	public Object remove(Menu menu) throws IOException{
		System.out.println("删除："+menu.toString());
		
//		remove_(menu.getId());
		// 删除主要对象  
		menuServiceImpl.delete(menu);
		
		JSONObject object = new JSONObject();
		object.put("result", "success");
		return object;
	}
	
	/**
	 * 修改
	 * @param menu
	 * @param spread
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("update.do")
	@ResponseBody
	public Object update(Menu menu, String spread) throws IOException{ 
		System.out.println("修改接收参数："+menu.toString()+" spread="+spread); 
		  
		menu.setUpdate_time(getNowTime()); 
		menu.setPriority(menu.getPriority() == null || "".equals(menu.getPriority()) ? "5" : menu.getPriority());
		Map<String, String> eq = new HashMap<>();
		eq.put("id", menu.getId());
		menuServiceImpl.update(menu, eq);
		
		WebsiteBase base = new WebsiteBase();
		base.setSpread(spread);
		Map<String, Object> eq_ = new HashMap<>(1);
		eq_.put("id", "1");
		websiteBaseServiceImpl.update(base, eq_);
		
		JSONObject json = jsonToJSONObject(menu);
		json.remove("name");
		json.put("label", menu.getName());
		json.put("isTab", menu.getUrl().indexOf("####") == -1 ? false : true); 
		return json;
	} 
	
	/**
	 * 递归初始化
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected JSONArray init_(String id){
		JSONArray jsonArray = null;
		Menu menu = new Menu();
		menu.setRelate_id(id); 
		List<Menu> ms = menuServiceImpl.getOfOrderBySort(menu, "ASC", "priority");
		if(ms != null && ms.size() > 0){
			jsonArray = new JSONArray();
			for(Menu item : ms) { 
				JSONObject object = jsonToJSONObject(item); 
				object.remove("name"); 
				object.put("label", item.getName()); 
				object.put("isTab", item.getUrl().indexOf("####") == -1 ? false : true); 
				JSONArray jsonArray_;
				if(item.getUrl().indexOf("####") != -1 && (jsonArray_ = init_(item.getId())) != null) 
					object.put("children", jsonArray_);
				jsonArray.add(object); 
			}
		}
		return jsonArray;
	}
	
	/**
	 * 初始化
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("list.do")
	@ResponseBody
	public JSONObject init() throws IOException{
		
		 return null;
	}
	
	
	
}
