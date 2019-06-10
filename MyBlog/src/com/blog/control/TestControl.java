package com.blog.control;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.util.ActionUtil;

@Controller
public class TestControl {
	public static Logger logger = LogManager.getLogger(TestControl.class);
	
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
	@RequestMapping("/api/test/json.do")
	@ResponseBody
	public Object test2(HttpServletRequest request, HttpServletResponse response) throws IOException{
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
		return json;
	}
	@RequestMapping("/api/test/str.do")
	@ResponseBody
	public String test3(HttpServletRequest request, HttpServletResponse response) throws IOException{
		return "success";
	}
	@RequestMapping("/api/test/admin/index.do")
	public String test4(HttpServletRequest request, HttpServletResponse response) throws IOException{
		return "../../../views/admin_view";
	}
}
class Result{
	private String result = "success";
	private String msg = "成功";
}
