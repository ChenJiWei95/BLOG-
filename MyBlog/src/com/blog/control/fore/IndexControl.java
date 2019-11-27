package com.blog.control.fore;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blog.Constant;
import com.blog.service.ArticleService;

@RequestMapping("/blog")
@Controller
public class IndexControl {
	
	@Autowired
	private ArticleService articleServiceImpl;

	// 返回 页面 
	@RequestMapping("/index.chtml")
	public String index(HttpServletRequest request, 
			String agentno, 
			ModelMap model){
		model.addAttribute("articles", 
				articleServiceImpl.getBySortAndLimit(null, 
						Constant.ORDERBY_ASC, 
						"create_time", 
						0, 
						3));
		return "fore/index";
	}	
}
