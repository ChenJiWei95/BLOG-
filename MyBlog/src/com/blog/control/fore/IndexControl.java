package com.blog.control.fore;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blog.Constants;
import com.blog.control.BaseControl;
import com.blog.control.NotifyControl;
import com.blog.service.ArticleService;
import com.blog.service.LifeShareService;

@RequestMapping("/blog")
@Controller
public class IndexControl extends BaseControl{
	
	@Autowired
	private ArticleService articleServiceImpl;
	
	@Autowired
	private LifeShareService lifeShareServiceImpl;

	// 返回 页面 
	@RequestMapping("/index.chtml")
	public String index(HttpServletRequest request, 
			String agentno, 
			ModelMap model){
		model.addAttribute("articles", 
				articleServiceImpl.getBySortAndLimit(null, 
						Constants.ORDERBY_ASC, 
						"create_time", 
						0, 
						3));
		model.addAttribute("lifes", 
				lifeShareServiceImpl.getBySortAndLimit(null, 
						Constants.ORDERBY_ASC, 
						"time", 
						0, 
						3));
		return "fore/index";
	}	
	@RequestMapping("/email.chtml")
	public String email(HttpServletRequest request, 
			ModelMap model){
		/*model.addAttribute("articles", 
				articleServiceImpl.getBySortAndLimit(null, 
						Constant.ORDERBY_ASC, 
						"create_time", 
						0, 
						3));*/
		return "fore/email/show";
	}	
	@RequestMapping("/sendEmail.do")
	public Object sendEmail(String email, String phone, String name, String content){
		try{
			if(NotifyControl.sendMail("1281384046@qq.com", null, "博客邮件联系我", 
					"email："+email+"，phone："+phone+"，name："+name+"，content："+content, null))
				return com.blog.util.Message.success("发送成功，稍后自动跳转回主页", null);
			else 
				return com.blog.util.Message.error("发送失败", null);
		}catch(Exception e){
			e.printStackTrace();
			return com.blog.util.Message.error("服务器异常，"+e.getMessage(), null);
		}
	}	
	
	
}
