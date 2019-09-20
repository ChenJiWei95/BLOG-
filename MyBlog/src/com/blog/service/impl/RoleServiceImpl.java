package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.BaseDao;
import com.blog.dao.RoleDao;
import com.blog.entity.Role;
import com.blog.entity.RoleItem;
import com.blog.service.RoleService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class RoleServiceImpl extends BasiServiceImpl<Role, RoleItem> implements RoleService<Role, RoleItem>, AssociaInterface  {
	@Resource
	RoleDao roleDao; 
	
	public BaseDao<Role> getDao(){
		return roleDao;
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
		return "role";
	}
}

