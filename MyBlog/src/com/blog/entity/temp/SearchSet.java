package com.blog.entity.temp;

public class SearchSet extends ComponentSet {
	public String toString(){
		// 自动生成submit键 
		String str = 	"<div class=\"layui-inline\">" + 
						"<button class=\"layui-btn c-button\" lay-submit lay-filter=\"C-btn-search\">" + 
						"<i class=\"layui-icon layui-icon-search C-btn-search\"></i>" + 
						"</button>" + 
						"</div>";
		return super.toString() + str;
	}
}
