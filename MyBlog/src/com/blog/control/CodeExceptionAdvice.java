package com.blog.control;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*@Controller
public class CodeExceptionAdvice implements ErrorController {
	@Autowired
    HttpServletRequest request;
	
    @RequestMapping("/error")
    public String getErrorPath() {
    	System.out.println("CodeExceptionAdvice.getErrorPath()");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        switch (statusCode) {
            case 404:
                return "/404";
            case 400:
                return "/400";
            default:
                return "/500";
        }
    } 
}*/
