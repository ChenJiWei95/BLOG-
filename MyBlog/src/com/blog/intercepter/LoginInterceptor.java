package com.blog.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.blog.Constant;

public class LoginInterceptor implements HandlerInterceptor {
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		System.out.println("拦截登录");
		if(arg0.getRequestURI().indexOf("login.chtml")>0 || arg0.getRequestURI().indexOf("login.do")>0) {
			return true;
		}
		System.out.println(arg0.getSession().getAttribute(Constant.USER_CONTEXT)+"<<<<<<<<");
		if(arg0.getSession().getAttribute(Constant.USER_CONTEXT)!=null) {
			return true;
		}
		arg1.sendRedirect("../login.chtml");
		return false;
	} 
}