package com.blog.entity.temp;

public class TextareaEntry extends CompEntity {
	
	public String toString() {
		// 输出自身特有的 super.toString() 继承公共的 
		return super.toString("<textarea class=\"layui-textarea\" name=\""+this.getName()+"\" placeholder=\"请输入信息...\"></textarea>");
	}
}
