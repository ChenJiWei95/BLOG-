package com.blog.jstl;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;

public class FmGenerateForm extends SimpleTagSupport {

private Logger log = Logger.getLogger(FmGenerateForm.class);
	
	// 标签属性text
	/*private String name;
	
	public void setName(String name) {
		this.name = name;
	}*/

	// 内容体
	StringWriter sw = new StringWriter();
	
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		StringBuilder html = new StringBuilder(); 
		html.append("<form class='layui-form' action='' onsubmit='return false'>");
		html.append("  <div class='layui-form-item'>");
		html.append("    <label class='layui-form-label'>输入框</label>");
		html.append("    <div class='layui-input-block'>");
		html.append("      <input type='text' name='gjnjj' required  lay-verify='required' placeholder='请输入输入框内容' autocomplete='off' class='layui-input'>");
		html.append("    </div>");
		html.append("  </div>");
		html.append("  <div class='layui-form-item'>");
		html.append("    <label class='layui-form-label'>密码框</label>");
		html.append("    <div class='layui-input-block'>");
		html.append("      <input type='password' name='nmi2g' required  lay-verify='required' placeholder='请输入密码框内容' autocomplete='off' class='layui-input'>");
		html.append("    </div>");
		html.append("  </div>");
		html.append("  <div class='layui-form-item'>");
		html.append("    <label class='layui-form-label'>选择框</label>");
		html.append("    <div class='layui-input-block'>");
		html.append("      <select name='tf59m' lay-verify='required' lay-search>");
		html.append("        <option value=''></option>");
		html.append("        <option value='0'>北京</option>");
		html.append("        <option value='1'>上海</option>");
		html.append("        <option value='2'>广州</option>");
		html.append("        <option value='3'>深圳</option>");
		html.append("        <option value='4'>杭州</option>");
		html.append("      </select>");
		html.append("    </div>");
		html.append("  </div>");
		html.append("  <div class='layui-form-item'>");
		html.append("    <label class='layui-form-label'>复选框</label>");
		html.append("    <div class='layui-input-block'>");
		html.append("      <input type='checkbox' name='f3myl[]' title='写作'>");
		html.append("      <input type='checkbox' name='f3myl[]' title='阅读' checked>");
		html.append("      <input type='checkbox' name='f3myl[]' title='发呆'>");
		html.append("    </div>");
		html.append("  </div>");
		html.append("  <div class='layui-form-item'>");
		html.append("    <label class='layui-form-label'>开关</label>");
		html.append("    <div class='layui-input-block'>");
		html.append("      <input type='checkbox' name='r3jys' lay-skin='switch'>");
		html.append("    </div>");
		html.append("  </div>");
		html.append("  <div class='layui-form-item'>");
		html.append("    <label class='layui-form-label'>单选框</label>");
		html.append("    <div class='layui-input-block'>");
		html.append("      <input type='radio' name='3nun3' value='男' title='男'>");
		html.append("      <input type='radio' name='3nun3' value='女' title='女' checked>");
		html.append("    </div>");
		html.append("  </div>");
		html.append("  <div class='layui-form-item layui-form-text'>");
		html.append("    <label class='layui-form-label'>文本域</label>");
		html.append("    <div class='layui-input-block'>");
		html.append("      <textarea name='kwkt' placeholder='请输入内容' class='layui-textarea'></textarea>");
		html.append("    </div>");
		html.append("  </div>");
		html.append("  <div class='layui-form-item'>");
		html.append("    <div class='layui-input-block'>");
		html.append("      <button class='layui-btn' lay-submit lay-filter='formDemo'>立即提交</button>");
		html.append("      <button type='reset' class='layui-btn layui-btn-primary'>重置</button>");
		html.append("    </div>");
		html.append("  </div>");
		html.append("</form>");
		out.println(html.toString());
	}
	
}
