package com.blog.entity.temp;

public class ItextEntry extends CompEntity {
	private String placeholder;
	
	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public String toString() {
		// 输出自身特有的 super.toString() 继承公共的 
		return super.toString("<input type=\"text\" "+(this.isDisable() ? "disabled":"")+" name=\""+this.getName()+"\" placeholder=\""+(this.getPlaceholder() == null ? "请输入..." : this.getPlaceholder()) +"\" autocomplete=\"off\" class=\"layui-input "+(this.isDisable()?"layui-disabled":"")+"\">");
	}
}
