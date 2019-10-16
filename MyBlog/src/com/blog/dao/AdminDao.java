package com.blog.dao;

import java.util.List;
import java.util.Map;

import com.blog.entity.Admin;
import com.blog.util.sql.EqAdapter;

public interface AdminDao extends BaseDao<Admin> {
	List<Map<String, Object>> getOfManyTable(EqAdapter eq);
}
