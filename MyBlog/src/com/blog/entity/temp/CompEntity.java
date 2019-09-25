package com.blog.entity.temp;

public class CompEntity extends TempEntity {
	private String value;
	private String name;
	private boolean disable;
	private String label;
	private boolean hide;
	private boolean inline;
	private String html;  //如果有值 将会优先使用 只是相关部分具体看源码
	private String type;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isDisable() {
		return disable;
	}
	public void setDisable(boolean disable) {
		this.disable = disable;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	
	public String toString() {
		// 输出基本的 
		return "";
	}
	public boolean isHide() {
		return hide;
	}
	public void setHide(boolean hide) {
		this.hide = hide;
	}
	public boolean isInline() {
		return inline;
	}
	public void setInline(boolean inline) {
		this.inline = inline;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String toString(String str) {
		return 	"<div class=\""+ (this.isHide() ? "layui-hide" : this.isInline() ? "layui-inline" : "layui-form-item") +"\">" + 
				"<label class=\"layui-form-label\">"+this.getLabel()+"</label>" + 
				"<div class=\"layui-input-inline\">" + 
				(this.getHtml() != null && !"".equals(this.getHtml()) ? this.getHtml() : str) +
				"</div>" + 
				"</div>";
	}
	
}
