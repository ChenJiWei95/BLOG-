package com.blog.util.sql;

import com.blog.Constant;

/**
 * 多表查询生成局部sql语句
 * @author Administrator
 */
public class ManyTable {
	
	// 执行案例
	// new ManyTable().selete("c.url").as("url").and("b")
	// .form("admin_infor").as("a").and("role").as("b").and("menu").as("c")
	// .where("a.`admin_id` = '123456'").and("a.`admin_id` = '123456'").and("a.`admin_id` = '123456'").or("a.`admin_id` = '123456'")

	// 结果 
	// idnex = 0 c.url AS url, b
	// idnex = 1 admin_infor a, role b, menu c
	// idnex = 2 a.`admin_id` = '123456' AND a.`admin_id` = '123456' AND a.`admin_id` = '123456' OR a.`admin_id` = '123456'
	private String[] result = new String[3];
	private StringBuilder sb;
	private String and, or, as;
	private Integer index = 0;
	
	public ManyTable selete(String s) {
		
		index = 0;
		and = ", ";
		as = " AS ";
		sb = new StringBuilder();
		sb.append(s);
		return this;
	}
	
	public ManyTable form(String s) {
		
		result[index] = sb.toString();
		index = 1;
		and = ", ";
		as = " ";
		sb = new StringBuilder();
		sb.append(s);
		return this;
	}
	
	public ManyTable where(String s) {
		
		result[index] = sb.toString();
		index = 2;
		and = " AND ";
		or = " OR ";
		sb = new StringBuilder();
		sb.append(s);
		return this;
	}
	
	public ManyTable orderby(String s) {
		
		result[index] = sb.toString();
		index = 3;
		sb = new StringBuilder();
		sb.append(s);
		return this;
	}
	
	public ManyTable orderbyasc() {
		
		result[index] = sb.toString();
		index = 3;
		sb = new StringBuilder();
		sb.append(Constant.ORDERBY_ASC);
		return this;
	}
	
	public ManyTable orderbydesc() {
		
		result[index] = sb.toString();
		index = 3;
		sb = new StringBuilder();
		sb.append(Constant.ORDERBY_DESC);
		return this;
	}
	
	// 三种情况都有可能
	public ManyTable and(String s) {
		
		sb.append(and+s); 
		return this;
	}
	
	public ManyTable as(String s) {
		
		sb.append(as+s); 
		return this;
	}
	
	public ManyTable or(String s) {
		
		sb.append(or+s); 
		return this;
	}
	
	public String[] getStatement() {
		
		result[index] = sb.toString();
		return result;
	}
	
}