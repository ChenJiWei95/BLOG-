package com.blog.intercepter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.blog.Constant; 
/**
 * @version: V 1.0 
 * @Description: 管理界面登录权限拦截
 * @author: cjw 
 * @date: 2019年11月27日 上午11:19:56
 */
public class LoginInterceptor implements HandlerInterceptor {
	
	private static Logger log = Logger.getLogger(LoginInterceptor.class);
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}
	
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}
	
	@Override
	public boolean preHandle(HttpServletRequest arg0, 
			HttpServletResponse arg1, 
			Object arg2) throws Exception {
		
		System.out.println(arg0.getRequestURI());
		log.info(arg0.getServerPort()+arg0.getContextPath());
		
		if(		arg0.getRequestURI().indexOf("login.chtml") > 0 
				|| arg0.getRequestURI().indexOf("login.do") > 0 
				|| arg0.getRequestURI().indexOf("/MyBlog/blog") == 0
				|| "/MyBlog/".equals(arg0.getRequestURI())) {
			// 登录页面、登录请求、博客请求、基本请求 跳过
			return true;
		}
		
		System.out.println("拦截登录");
		
		String basePath = arg0.getScheme()+
				"://"+arg0.getServerName()+
				":"+
				arg0.getServerPort()+
				arg0.getContextPath()+
				"/";
		
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
		
		System.out.println((arg0.getSession().getAttribute(Constant.USER_CONTEXT) != null)+" <<<<<<<< 权限");
		if(arg0.getSession().getAttribute(Constant.USER_CONTEXT) != null) {
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