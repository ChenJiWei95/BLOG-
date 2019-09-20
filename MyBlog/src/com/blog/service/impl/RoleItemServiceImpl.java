package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.BaseDao;
import com.blog.dao.RoleItemDao;
import com.blog.entity.RoleItem;
import com.blog.service.RoleItemService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class RoleItemServiceImpl extends BasiServiceImpl<RoleItem, Object> implements RoleItemService<RoleItem, Object>, AssociaInterface  {
	@Resource
	RoleItemDao roleItemDao; 
	
	public BaseDao<RoleItem> getDao(){
		return roleItemDao;
	}
	
	@Override
	public String getBrige_table() {
		return "";
	}
	@Override
	public String getBrige_key() {
		return "";
	}
	@Override
	public String getBrige_association_key() {
		return "";
	}
	@Override
	public String getAssociation_table() {
		return "";
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
		return "role_item";
	}
}

