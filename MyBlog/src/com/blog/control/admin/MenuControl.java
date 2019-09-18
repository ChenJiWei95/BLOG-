package com.blog.control.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blog.entity.Menu;
import com.blog.service.MenuService;

@Controller
@RequestMapping("/admin/menu")
public class MenuControl {
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private MenuService menuServiceImpl;
	
	// 返回 页面 
	@RequestMapping("listview.chtml") 
	public String listview1(String agentno,ModelMap model){
		return "/views/admin/menu/list";
	}
	// 添加
	@SuppressWarnings("unchecked")
	@RequestMapping("add.do")
	@ResponseBody
	public Object branchAdd(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, String> map = getRequestParameterMap(request);
		System.out.println("添加接受参数："+map);
		
		JSONObject resultObject = (JSONObject) JSONObject.parse(map.get("data"));
		
		Menu m = new Menu(); 
		m.setId(resultObject.getString("id"));
		m.setName(resultObject.getString("name"));
		m.setPriority(resultObject.getString("priority"));
		m.setCreate_time(com.blog.util.TimeUtil.getDatetime());
		m.setUrl(resultObject.getString("url"));
		m.setRelate_id(map.get("relateId"));
		m.setMsg(resultObject.getString("msg"));
		menuServiceImpl.insert(m);
		
		JSONObject obj1 = new JSONObject();
		obj1.put("id", m.getId());
		obj1.put("label", m.getName());
		obj1.put("isTab", m.getUrl().indexOf("####") == -1 ? false : true);
		obj1.put("priority", m.getPriority());
		obj1.put("url", m.getUrl());
		obj1.put("create_time", m.getCreate_time());
		obj1.put("msg", m.getMsg());
		
		return obj1;
	}
	
	// 删除
	@SuppressWarnings("unchecked")
	@RequestMapping("remove.do")
	@ResponseBody
	public Object delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, String> map = getRequestParameterMap(request);
		System.out.println("删除接受参数："+map);
		
		JSONObject resultObject = (JSONObject) JSONObject.parse(map.get("data"));
		
		Menu m = new Menu();
		m.setId(resultObject.getString("id"));
		menuServiceImpl.delete(m);
		
		JSONObject object = new JSONObject();
		object.put("result", "success");
		return object;
	}
	
	// 修改
	@SuppressWarnings("unchecked")
	@RequestMapping("update.do")
	@ResponseBody
	public Object branchUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, String> map = getRequestParameterMap(request);
		System.out.println("修改接受参数："+map);
		
		JSONObject resultObject = (JSONObject) JSONObject.parse(map.get("data"));
		
		Menu m = new Menu(); 
		m.setName(resultObject.getString("name"));
		m.setPriority(resultObject.getString("priority"));
		m.setUpdate_time(com.blog.util.TimeUtil.getDatetime());
		m.setUrl(resultObject.getString("url"));
		m.setMsg(resultObject.getString("msg"));
		Map<String, String> eq = new HashMap<>();
		eq.put("id", resultObject.getString("id"));
		menuServiceImpl.update(m, eq);
		
		JSONObject obj1 = new JSONObject();
		obj1.put("id", resultObject.getString("id"));
		obj1.put("label", resultObject.get("name"));
		obj1.put("isTab", ((String) resultObject.get("url")).indexOf("####") == -1 ? false : true);
		obj1.put("priority", resultObject.get("priority"));
		obj1.put("url", resultObject.get("url"));
		obj1.put("create_time", resultObject.getString("create_time"));
		obj1.put("update_time", m.getUpdate_time());
		obj1.put("msg", resultObject.get("msg"));
		return obj1;
	}
	
	// 初始化
	@RequestMapping("init.do")
	@ResponseBody
	public JSONObject init(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		Menu m = new Menu(); 
		m.setRelate_id("");
		List<Menu> ms = menuServiceImpl.gets(m);
		for(Menu item : ms) {
			m = new Menu(); 
			m.setRelate_id(item.getId());
			List<Menu> ms_ = menuServiceImpl.gets(m);
		}
		
		JSONObject obj1 = new JSONObject();
		obj1.put("id", "2");
		obj1.put("label", "菜单管理");
		obj1.put("isTab", false);
		obj1.put("priority", "1");
		obj1.put("url", "http://localhost:8080/MyBlog/views/tagmanage.jsp");
		obj1.put("create_time", "2019-09-09");
		obj1.put("update_time", "2019-09-09");
		obj1.put("msg", "备注");
		
		JSONObject obj2 = new JSONObject();
		obj2.put("id", "3");
		obj2.put("label", "数据字典");
		obj2.put("isTab", true);
		obj2.put("priority", "1");
		obj2.put("url", "####");
		obj2.put("create_time", "2019-09-09");
		obj2.put("update_time", "2019-09-09");
		obj2.put("msg", "备注");
		
		JSONArray arr_1 = new JSONArray();
		arr_1.add(obj1);
		arr_1.add(obj2);
		
		JSONObject object = new JSONObject();
		// id label priority url create_time update_time msg
		object.put("id", "1");
		object.put("label", "资源管理");
		object.put("isTab", true);
		object.put("priority", "1");
		object.put("url", "####");
		object.put("create_time", "2019-09-09");
		object.put("update_time", "2019-09-09");
		object.put("msg", "备注");
		object.put("children", arr_1);
		
		JSONObject obj1_1 = new JSONObject();
		obj1_1.put("id", "4");
		obj1_1.put("label", "访客管理");
		obj1_1.put("isTab", false);
		obj1_1.put("priority", "1");
		obj1_1.put("url", "http://localhost:8080/MyBlog/views/usermanage.jsp");
		obj1_1.put("create_time", "2019-09-09");
		obj1_1.put("update_time", "2019-09-09");
		obj1_1.put("msg", "备注");
		
		JSONObject obj2_2 = new JSONObject();
		obj2_2.put("id", "5");
		obj2_2.put("label", "后天管理员");
		obj2_2.put("isTab", false);
		obj2_2.put("priority", "1");
		obj2_2.put("url", "http://localhost:8080/MyBlog/views/adminmanage.jsp");
		obj2_2.put("create_time", "2019-09-09");
		obj2_2.put("update_time", "2019-09-09");
		obj2_2.put("msg", "备注");
		
		JSONArray arr_1_1 = new JSONArray();
		arr_1_1.add(obj1_1);
		arr_1_1.add(obj2_2);
		
		JSONObject object_1 = new JSONObject();
		// id label priority url create_time update_time msg
		object_1.put("id", "6");
		object_1.put("label", "权限管理");
		object_1.put("isTab", true);
		object_1.put("priority", "1");
		object_1.put("url", "####");
		object_1.put("create_time", "2019-09-09");
		object_1.put("update_time", "2019-09-09");
		object_1.put("msg", "备注");
		object_1.put("children", arr_1_1);
		
		JSONArray arr = new JSONArray();
		arr.add(object);
		arr.add(object_1);
		System.out.println("初始化:"+arr.toString());
		
		JSONObject resultObj = new JSONObject();
		resultObj.put("responseCode", "success");
		resultObj.put("responseMsg", "初始化成功！");
		resultObj.put("data", arr);
		String[] strs = {"1"};
		resultObj.put("spread", strs);
		return resultObj;
	}
	protected static Map<String, String> getRequestParameterMap(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		try {
			Map<String, String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				System.out.println("name:"+name+", values:"+values);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
					params.put(name, valueStr);
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return params;
	}
	protected String basePath(HttpServletRequest request){
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
	}
}
