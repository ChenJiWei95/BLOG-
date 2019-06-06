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
}
