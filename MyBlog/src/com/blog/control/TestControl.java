package com.blog.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blog.entity.Clazz;
import com.blog.entity.Menu;
import com.blog.entity.User;
import com.blog.service.MenuService;
import com.blog.service.UserService;
import com.blog.util.ActionUtil; 


@Controller
public class TestControl {
	public static Logger logger = LogManager.getLogger(TestControl.class);
	
	@Autowired
	private UserService userServiceImpl;
	
	@Autowired
	private MenuService menuServiceImpl;
	
	//请求 http://localhost:8080/MyBlog/api/test.do
	@RequestMapping("/api/test.do")
	public void test(HttpServletRequest request, HttpServletResponse response) throws IOException{
		/*logger.error("servlet path:"+request.getServletPath());
		String url="http://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI(); 
		logger.error("全路径："+url);
		logger.error("路径:" + System.getProperty("catalina.home"));
		//return "index";
		*/	
		System.out.println(ActionUtil.read(request));
		ActionUtil.returnRes(response, "success");
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/api/test1.do")
	public void test1(HttpServletRequest request, HttpServletResponse response) throws IOException{
		// 单个查询
		/*User u = new User();
		u.setId(1);
		System.out.println(userServiceImpl.get(u));
		userServiceImpl.test();*/
		
		// 多个查询
		User u = new User();
		u.setPassword("%8%");
		List<User> list = userServiceImpl.getOfOrderBySortAndLimit(u, "desc", "id", 0, 5);
		for(User u_ : list)
			System.out.println(u_.toString());
		
		// 删除
		/*User u = new User();
		u.setId(6);
		userServiceImpl.delete(u);*/
		
		// 插入
		/*User u = new User();
		u.setUsername("cjw1");
		u.setPassword("66666668");
		userServiceImpl.insert(u);*/
		
		// 修改
		/*User u = new User();
		u.setUsername("cjx");
		u.setPassword("8888888888");
		Map<String, Object> eq = new HashMap<>(1);
		eq.put("id", 7);
		userServiceImpl.update(u, eq);*/
		
		// 关联
		
		/*User u = new User();
		u.setId(1);
		List<Clazz> list = userServiceImpl.getAssociat(u);
		for(Clazz u_ : list)
			System.out.println(u_.toString());*/
	}
	@RequestMapping("/api/test/json.do")
	@ResponseBody
	public Object test2(HttpServletRequest request, HttpServletResponse response) throws IOException{
		JSONObject object = new JSONObject();
		object.put("code", 0);
		object.put("msg", "xx");
		object.put("count", 30);
		JSONArray arr = new JSONArray();
		
		JSONObject object_ = new JSONObject();
		object_.put("id", "0001");
		object_.put("typeCode", "state");
		object_.put("typeName", "启用状态-关");
		object_.put("createTime", "2019-05-31 16:58:30");
		object_.put("updateTime", "");
		object_.put("typeVal", "00");
		object_.put("typeMsg", "");
		arr.add(object_);
		
		object_ = new JSONObject();
		object_.put("id", "0002");
		object_.put("typeCode", "state");
		object_.put("typeName", "启用状态-开");
		object_.put("createTime", "2019-05-31 16:58:30");
		object_.put("updateTime", "2019-05-31 16:58:30");
		object_.put("typeVal", "01");
		object_.put("typeMsg", "");
		arr.add(object_);
		
		object.put("data", arr);
		
		return object;
	}
	@RequestMapping("/api/test/role.do")
	@ResponseBody
	public Object test5(HttpServletRequest request, HttpServletResponse response) throws IOException{
		JSONObject object = new JSONObject();
		object.put("code", 0);
		object.put("msg", "权限表");
		object.put("count", 20);
		JSONArray arr = new JSONArray();
		
		JSONObject object_ = new JSONObject();
		object_.put("id", "0001");
		object_.put("rolename", "超级管理员");
		object_.put("limits", "所有管理");
		object_.put("descr", "管理");
		arr.add(object_);
		
		object_ = new JSONObject();
		object_.put("id", "0002");
		object_.put("rolename", "管理员");
		object_.put("limits", "部分管理");
		object_.put("descr", "管理");
		arr.add(object_);
		
		object.put("data", arr);
		
		return object;
	}
	@RequestMapping("/api/test/admin.do")
	@ResponseBody
	public Object test6(HttpServletRequest request, HttpServletResponse response) throws IOException{
		JSONObject object = new JSONObject();
		object.put("code", 0);
		object.put("msg", "管理员");
		object.put("count", 20);
		JSONArray arr = new JSONArray();
		
		JSONObject object_ = new JSONObject();
		object_.put("id", "0001");
		object_.put("account", "root");
		object_.put("pass", "cjw168999");
		object_.put("role", "超级管理员"); 
		object_.put("create_time", "2019-06-11");
		object_.put("update_time", "");
		object_.put("phone", "1111111111");
		object_.put("email", "2222222222"); 
		object_.put("msg", "xxx"); 
		arr.add(object_);
		
		object_ = new JSONObject();
		object_.put("id", "0002");
		object_.put("account", "admin");
		object_.put("pass", "123456");
		object_.put("role", "管理员"); 
		object_.put("create_time", "2019-06-11");
		object_.put("update_time", "2019-06-12");
		object_.put("phone", "1111111111");
		object_.put("email", "333333333"); 
		object_.put("msg", "xxx"); 
		arr.add(object_);
		
		object.put("data", arr);
		
		return object;
	}
	@RequestMapping("/api/test/tags/add.do")
	@ResponseBody
	public Object test7(HttpServletRequest request, HttpServletResponse response) throws IOException{
		System.out.println("访问add");
		Object data = request.getParameter("data");
		if(data != null)System.out.println(data.toString());
		JSONObject object = new JSONObject();
		object.put("code", 0);
		object.put("msg", "管理员");
		object.put("count", 20);
		JSONArray arr = new JSONArray();
		
		JSONObject object_ = new JSONObject();
		object_.put("id", "0001");
		object_.put("account", "root");
		object_.put("pass", "cjw168999");
		object_.put("role", "超级管理员"); 
		object_.put("create_time", "2019-06-11");
		object_.put("update_time", "");
		object_.put("phone", "1111111111");
		object_.put("email", "2222222222"); 
		object_.put("msg", "xxx"); 
		arr.add(object_);
		
		object_ = new JSONObject();
		object_.put("id", "0002");
		object_.put("account", "admin");
		object_.put("pass", "123456");
		object_.put("role", "管理员"); 
		object_.put("create_time", "2019-06-11");
		object_.put("update_time", "2019-06-12");
		object_.put("phone", "1111111111");
		object_.put("email", "333333333"); 
		object_.put("msg", "xxx"); 
		arr.add(object_);
		
		object.put("data", arr);
		
		return object;
	}
	@RequestMapping("/api/test/tags/update.do")
	@ResponseBody
	public Object test8(HttpServletRequest request, HttpServletResponse response) throws IOException{
		System.out.println("访问update");
		Object data = request.getParameter("data");
		if(data != null)System.out.println(data.toString());
		JSONObject object = new JSONObject();
		object.put("code", 0);
		object.put("msg", "管理员");
		object.put("count", 20);
		JSONArray arr = new JSONArray();
		
		JSONObject object_ = new JSONObject();
		object_.put("id", "0001");
		object_.put("account", "root");
		object_.put("pass", "cjw168999");
		object_.put("role", "超级管理员"); 
		object_.put("create_time", "2019-06-11");
		object_.put("update_time", "");
		object_.put("phone", "1111111111");
		object_.put("email", "2222222222"); 
		object_.put("msg", "xxx"); 
		arr.add(object_);
		
		object_ = new JSONObject();
		object_.put("id", "0002");
		object_.put("account", "admin");
		object_.put("pass", "123456");
		object_.put("role", "管理员"); 
		object_.put("create_time", "2019-06-11");
		object_.put("update_time", "2019-06-12");
		object_.put("phone", "1111111111");
		object_.put("email", "333333333"); 
		object_.put("msg", "xxx"); 
		arr.add(object_);
		
		object.put("data", arr);
		
		return object;
	}
	@RequestMapping("/api/test/tags/del.do")
	@ResponseBody
	public Object test9(HttpServletRequest request, HttpServletResponse response) throws IOException{
		System.out.println("访问del");
		Object data = request.getParameter("data");
		if(data != null)System.out.println(data.toString());
		JSONObject object = new JSONObject();
		object.put("code", 0);
		object.put("msg", "管理员");
		object.put("count", 20);
		JSONArray arr = new JSONArray();
		
		JSONObject object_ = new JSONObject();
		object_.put("id", "0001");
		object_.put("account", "root");
		object_.put("pass", "cjw168999");
		object_.put("role", "超级管理员");
		object_.put("create_time", "2019-06-11");
		object_.put("update_time", "");
		object_.put("phone", "1111111111");
		object_.put("email", "2222222222");
		object_.put("msg", "xxx");
		arr.add(object_);
		
		object_ = new JSONObject();
		object_.put("id", "0002");
		object_.put("account", "admin");
		object_.put("pass", "123456");
		object_.put("role", "管理员"); 
		object_.put("create_time", "2019-06-11");
		object_.put("update_time", "2019-06-12");
		object_.put("phone", "1111111111");
		object_.put("email", "333333333");
		object_.put("msg", "xxx");
		arr.add(object_);
		object.put("data", arr);
		return object;
	}
	@RequestMapping("/api/test/tag1.do")
	@ResponseBody
	public Object test10(HttpServletRequest request, HttpServletResponse response) throws IOException{
		System.out.println("初始化");
		Object data = request.getParameter("data");
		if(data != null)System.out.println(data.toString());
		JSONObject object = new JSONObject();
		object.put("code", 0);
		object.put("msg", "标签");
		object.put("count", 20);
		JSONArray arr = new JSONArray();
		
		JSONObject object_ = new JSONObject();
		object_.put("id", "0001");
		object_.put("tags", "JAVA");
		object_.put("msg", "描述些什么");
		arr.add(object_);
		
		object_ = new JSONObject();
		object_.put("id", "0002");
		object_.put("tags", "美食");
		object_.put("msg", "描述些什么...");
		arr.add(object_);
		
		object.put("data", arr);
		
		return object;
	}
	@RequestMapping("/api/test/tagsxxx.do")
	@ResponseBody
	public Object test11(HttpServletRequest request, HttpServletResponse response) throws IOException{
		JSONObject object = new JSONObject();
		object.put("result", "success");
		return object;
	}
	public static void main(String[] args){
		String json = "{\r\n" + 
				"	\"code\": 0,\r\n" + 
				"	\"msg\": \"yy\",\r\n" + 
				"	\"count\": 1000,\r\n" + 
				"	\"data\": [{\r\n" + 
				"		\"id\": 0001,\r\n" + 
				"		\"typeCode\": \"state\",\r\n" + 
				"		\"typeName\": \"启用状态-关\",\r\n" + 
				"		\"createTime\": \"2019-05-31 16:58:30\",\r\n" + 
				"		\"updateTime\": \"\",\r\n" + 
				"		\"typeVal\": \"00\",\r\n" + 
				"		\"typeMsg\": \"\",\r\n" + 
				"	}, {\r\n" + 
				"		\"id\": 0002,\r\n" + 
				"		\"typeCode\": \"state\",\r\n" + 
				"		\"typeName\": \"启用状态-开\",\r\n" + 
				"		\"createTime\": \"2019-05-31 16:58:40\",\r\n" + 
				"		\"updateTime\": \"\",\r\n" + 
				"		\"typeVal\": \"00\",\r\n" + 
				"		\"typeMsg\": \"\",\r\n" + 
				"	}]\r\n" + 
				"}";
		
		System.out.println(json);
	}
	@RequestMapping("/api/test/str.do")
	@ResponseBody
	public Object test3(HttpServletRequest request, HttpServletResponse response) throws IOException{
		JSONObject object = new JSONObject();
		object.put("result", "success");
		return object;
	}
	@RequestMapping("/api/test/admin/index.do")
	public String test4(HttpServletRequest request, HttpServletResponse response) throws IOException{
		return "../../../views/admin_view";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/api/test/branch/add.do")
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
	@SuppressWarnings("unchecked")
	@RequestMapping("/api/test/branch/update.do")
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
//		menuServiceImpl.update(m, eq);
		
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
	@SuppressWarnings("unchecked")
	@RequestMapping("/api/test/branch/del.do")
	@ResponseBody
	public Object branchDel(HttpServletRequest request, HttpServletResponse response) throws IOException{
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
	@RequestMapping("/api/test/branch/init.do")
	@ResponseBody
	public JSONObject branchInit(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
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
	public static Map<String, String> getRequestParameterMap(HttpServletRequest request) {
		logger.info("getRequestParameterMap");
		Map<String, String> params = new HashMap<String, String>();
		try {
			Map<String, String[]> requestParams = request.getParameterMap();
			logger.info("do");
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
} 


















