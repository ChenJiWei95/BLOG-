package com.blog.control.admin;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.control.BaseControl;

@Controller
// 数据字典
@RequestMapping("/vue")
public class VueTestControl extends BaseControl{
	private static Logger log = Logger.getLogger(VueTestControl.class); // 日志对象
	@ResponseBody
	@RequestMapping("test.do")
	public Object test(String id, String name){
		log.info("vue进行请求-----; id:"+id+", name:"+name);
		return "{\"name\":\"jack\"}";
	}
}
