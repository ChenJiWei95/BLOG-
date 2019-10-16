package com.blog.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.AdminDao;
import com.blog.dao.BaseDao;
import com.blog.entity.Admin;
import com.blog.service.AdminService;
import com.blog.util.sql.AssociaInterface;
import com.blog.util.sql.EqAdapter;
import com.blog.util.sql.SelectAdapter;

@Service
@Transactional
public class AdminServiceImpl extends BasiServiceImpl<Admin, Object> implements AdminService, AssociaInterface  {
	@Resource
	AdminDao adminDao; 
	
	public BaseDao<Admin> getDao(){
		return adminDao;
	}
	
	public List<Map<String, Object>> getOfManyTable(String colStatement, String tableStatement, String eqStatement){
		EqAdapter eq1 = new SelectAdapter()
				.setColumns(colStatement)
				.setTableStatement(tableStatement)
				.setEqSql(eqStatement);
		try {
			return adminDao.getOfManyTable(eq1);
		}catch(RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String getBrige_table() {
		return "role_item_brige";
	}
	@Override
	public String getBrige_key() {
		return "role_id";
	}
	@Override
	public String getBrige_association_key() {
		return "item_id";
	}
	@Override
	public String getAssociation_table() {
		return "role_item";
	}
	@Override
	public String getAssociation_table_id() {
		return "id";
	}
	@Override
	public String getId() {
		return "id";
	}
	@Override
	public String getTable() {
		return "admin";
	}
}

