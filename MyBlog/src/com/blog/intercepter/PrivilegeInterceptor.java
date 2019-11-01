package com.blog.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.blog.Constant;

public class PrivilegeInterceptor implements HandlerInterceptor {
	
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
		// login.chtml 进入登录页面  
		// login.do 登录请求
		if(arg0.getRequestURI().indexOf("login.chtml")>0 || arg0.getRequestURI().indexOf("login.do")>0) {
			return true;
		}
		
		String basePath = arg0.getScheme()+"://"+arg0.getServerName()+":"+arg0.getServerPort()+arg0.getContextPath()+"/";
		
		/*if(arg0.getRequestURI().indexOf(".chtml") != -1){
			System.out.println(arg0.getRequestURI());
			// ‘.chtml’ 页面专属结尾 
			// 对页面链接进行匹配是否在授权范围
			List<String> permissionList = (List<String>) arg0.getSession().getAttribute(Constant.PERMISSION_LIST);
			if(permissionList != null && !permissionList.contains(arg0.getRequestURI())){
				// 跳转至无权页面
				arg1.sendRedirect(basePath+"admin/privilege.jsp");
				return false;
			}
		}*/
		
		System.out.println(arg0.getSession().getAttribute(Constant.USER_CONTEXT)+"<<<<<<<<");
		if(arg0.getSession().getAttribute(Constant.USER_CONTEXT)!=null) {
			return true;
		}
		
		// 只有admin/main/listview.chtml 才进行登录跳转其他一概跳登出业
		if(arg0.getRequestURI().indexOf("admin/main/listview.chtml") != -1)
			arg1.sendRedirect(basePath+"admin/login.chtml");
		else 
			arg1.sendRedirect(basePath+"admin/error.jsp");
		
		return false;
		
	} 
}
