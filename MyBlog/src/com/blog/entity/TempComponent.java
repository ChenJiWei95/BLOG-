package com.blog.entity;

public class TempComponent extends Base {
	private String id;
	private String value;
	private String name;
	private boolean disable;
	private String label;
	private boolean hide;
	private boolean inline;
	private String html;  //如果有值 将会优先使用 只是相关部分具体看源码
	private String type;
	private String type_date;
	private String placeholder;
	private String event;
	
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
	
}
