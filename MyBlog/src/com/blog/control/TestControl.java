package com.blog.control;

import java.io.IOException;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.service.impl.UserServiceImpl;
import com.blog.util.ActionUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class TestControl {
	public static Logger logger = LogManager.getLogger(TestControl.class);
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
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
	@RequestMapping("/api/test1.do")
	public void test1(HttpServletRequest request, HttpServletResponse response) throws IOException{
		userServiceImpl.test();
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
} 


















