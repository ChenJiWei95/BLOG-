package com.blog.entity.temp;

public class BtnEntry extends CompEntity {
	private String date_type;

	public String getDate_type() {
		return date_type;
	}

	public void setDate_type(String date_type) {
		this.date_type = date_type;
	}
	
}

class SubmitEntry extends BtnEntry {
	
	public String toString() {
		// 输出自身特有的 super.toString() 继承公共的
		return this.getHtml() != null && !"".equals(this.getHtml()) ? this.getHtml() : "<input type=\"button\" lay-submit lay-filter=\"C-admin-"+this.getDate_type()+"\" id=\"C-admin-"+this.getDate_type()+"\" value=\""+this.getLabel()+"\">";
	}
}
class ButtonEntry extends BtnEntry {
	
	
	public String toString() {
		// 输出自身特有的 super.toString() 继承公共的
		return this.getHtml() != null && !"".equals(this.getHtml()) ? this.getHtml() : "<button class=\"layui-btn layuiadmin-btn-admin c-button\" data-type=\""+this.getDate_type()+"\">"+this.getLabel()+"</button>";
	}
}
