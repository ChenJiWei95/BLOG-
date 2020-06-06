package com.blog.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionJsonControl {
	private Logger log = Logger.getLogger(ExceptionJsonControl.class);
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Object exceptionJsonHandler(HttpServletRequest request, Exception e) {
		Map<String, Object> map = new HashMap<String, Object>();
		String code = (String) request.getAttribute(com.blog.Constants.MESSAGE_ERROR_CODE);
		map.put("code", code == null ? "666" : code);
		map.put("url", request.getRequestURL().toString());
		map.put("message", e.getMessage());
		if(e instanceof NoHandlerFoundException) {
			map.put("data", "404 未找到页面");
			map.put("code", "0404");
		}
		else map.put("data", "请求失败");
		System.out.println(getStatus(request));
		return map;
		
	}
	
	private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    } 
}
