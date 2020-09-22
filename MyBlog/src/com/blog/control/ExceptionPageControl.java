package com.blog.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionPageControl {
	private Logger log = Logger.getLogger(ExceptionPageControl.class);
	
	@RequestMapping("code_error")
	public String exceptionExecute(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		log.info("============getStatus:"+statusCode);
		switch(statusCode){
		case 401:
    		return "/common/401";
        case 404:
            return "/common/404";
        case 400:
            return "/common/400";
        case 403:
        	return "/common/403";
        default:
            return "/common/500";
		}
	}
	
	/*private Integer getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        log.info("getStatus:"+statusCode);
        return statusCode;
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    } */
}
