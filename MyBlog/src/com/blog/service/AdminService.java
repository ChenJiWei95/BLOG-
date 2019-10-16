package com.blog.service;

import java.util.List;
import java.util.Map;

import com.blog.entity.Admin;

public interface AdminService extends BasiService<Admin, Object>{
	/**
	 * 多表查询
	 * @param colStatement		查询结果字段语句
	 * @param tableStatement	多表语句
	 * @param eqStatement		查询条件语句
	 * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> getOfManyTable(String colStatement, String tableStatement, String eqStatement);
}
