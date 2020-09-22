package com.blog.jstl;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.blog.Constants;
import com.blog.entity.Language;
import com.blog.service.RedisService;
import com.blog.util.SpringUtils;
import com.blog.util.i18n.AbstractCi18nCore;
import com.blog.util.i18n.Ci18nMybatis; 

public class Ci18nTag extends SimpleTagSupport {
	
	private Logger log = Logger.getLogger(Ci18nTag.class);
	
	// 标签属性text
	private String name;
	
	public void setName(String name) {
		this.name = name;
	}

	// 内容体
	StringWriter sw = new StringWriter();
	
	public void doTag() throws JspException, IOException {
		log.info("正在使用Ci18nTag");
		JspWriter out = getJspContext().getOut();
		String currentCode = "";
		if(name != null){
			currentCode = name;
		} else {
			currentCode = sw.toString();
		}
		log.info(currentCode);
		if(currentCode == null || "".equals(currentCode)){
			out.println("");
			return;
		}
		
		String value = ((AbstractCi18nCore) SpringUtils.getBean(Constants.CI18N_CLASS_NAME)).execute(currentCode);
		
		log.info("输出："+value);
		out.println(value);
	}

}
