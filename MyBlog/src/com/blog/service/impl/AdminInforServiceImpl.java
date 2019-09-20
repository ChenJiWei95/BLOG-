package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.AdminInforDao;
import com.blog.dao.BaseDao;
import com.blog.entity.AdminInfor;
import com.blog.service.AdminInforService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class AdminInforServiceImpl extends BasiServiceImpl<AdminInfor, Object> implements AdminInforService<AdminInfor, Object>, AssociaInterface  {
	@Resource
	AdminInforDao adminInfor; 
	
	public BaseDao<AdminInfor> getDao(){
		return adminInfor;
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
		return "admin_infor";
	}
}

