package com.blog.entity;

public class TempComponent extends Base {
	private String id;
	private String value;
	private String name;
	private String disable;
	private String label;
	private String hide;
	private String inline;
	private String html;  //如果有值 将会优先使用 只是相关部分具体看源码
	private String type;
	private String type_date;
	private String placeholder;
	private String event;
	private String c_id;
	private String desc;
	
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
	 
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	 
	public String getDisable() {
		return disable;
	}
	public void setDisable(String disable) {
		this.disable = disable;
	}
	public String getHide() {
		return hide;
	}
	public void setHide(String hide) {
		this.hide = hide;
	}
	public String getInline() {
		return inline;
	}
	public void setInline(String inline) {
		this.inline = inline;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType_date() {
		return type_date;
	}
	public void setType_date(String type_date) {
		this.type_date = type_date;
	}
	public String getPlaceholder() {
		return placeholder;
	}
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getC_id() {
		return c_id;
	}
	public void setC_id(String c_id) {
		this.c_id = c_id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
