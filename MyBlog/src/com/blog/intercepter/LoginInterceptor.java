package com.blog.intercepter;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.blog.Constants;
import com.blog.entity.WebsiteBase;
import com.blog.service.RedisService;
import com.blog.service.WebsiteBaseService;
import com.blog.util.GsonUtil;
import com.blog.util.SpringUtils; 
/**
 * @version: V 1.0 
 * @Description: 管理界面登录权限拦截
 * @author: cjw 
 * @date: 2019年11月27日 上午11:19:56
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
	
	private static Logger log = Logger.getLogger(LoginInterceptor.class);
	
	@Autowired
	private RedisService redisService;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}
	
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception { 
	} 
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse arg1, 
			Object arg2) throws Exception { 
		
		log.info("requestId:"+request.getRequestedSessionId());
		
		/* 国际化的支持 */ 
		String lang;
		if((lang = request.getParameter("request_locale")) != null){
			log.info(lang);
			RedisService redis = (RedisService) SpringUtils.getBean("redisService");
			redis.set(Constants.LANGUAGE_CURRENT, lang);
		}
		/* 国际化的支持 */ 
		
		log.info("RequestURI:"+request.getRequestURI());
		log.info("ContextPath:"+request.getServerPort()+request.getContextPath());
		
		WebsiteBase websiteBase = (WebsiteBase) redisService.get(Constants.MANAGER_SYS_BASE);
		if(websiteBase == null) {
			// 缓存失效
			websiteBase = ((WebsiteBaseService) SpringUtils.getBean("websiteBaseServiceImpl")).get("id="+Constants.MANAGER_SYS_BASE_ID);
			redisService.set(Constants.MANAGER_SYS_BASE, websiteBase, 120);
		}
		String[] list = websiteBase.getWhite_list().split(",");	
//		List<String> whites = Arrays.asList(list);
		// 白名单请求 允许（登录页面、登录请求、博客请求、基本请求）
		// 无需权限
		for(String item : list){
			if(request.getRequestURI().equals(item.trim()))
				return true;
		}
//		if(whites.contains(request.getRequestURI()))
//			return true; 
		
		log.info("进行权限认证");
		String basePath = request.getScheme()+
				"://"+request.getServerName()+
				":"+
				request.getServerPort()+
				request.getContextPath()+
				"/";
		
		String token = request.getParameter("token");
		if(true){
//			if(token != null && redisService.get(token) != null){
			log.info("redis缓存token登录许可 》》》》》》》 "+true);
			return true;
		}
		
		
		/*log.info("登录许可 》》》》》》》 "+(request.getSession().getAttribute(Constants.USER_CONTEXT) != null));
		if(request.getSession().getAttribute(Constants.USER_CONTEXT) != null) {
			return true;
		}*/
		
		log.info("授权失败");
		if(request.getRequestURI().endsWith(".do")){
			request.getRequestDispatcher(basePath+"admin/main/accessDenied.chtml");
			return false;
		}
		if(request.getRequestURI().endsWith(".chtml")){
			arg1.sendRedirect(basePath+"admin/main/accessDenied.chtml");
			return false;
		}
		
		// 只有admin/main/listview.chtml 才进行登录跳转其他一概跳登出业
		if(request.getRequestURI().indexOf("admin/main/listview.chtml") != -1)
			arg1.sendRedirect(basePath+"admin/login.chtml");
		else  // iframe 窗口失去权限的时候 重新登录
			arg1.sendRedirect(basePath+"admin/main/relogin.chtml"); 
		return false;
	} 
}