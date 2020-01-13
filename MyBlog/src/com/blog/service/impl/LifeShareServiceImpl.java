package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.LifeShareDao;
import com.blog.dao.BaseDao;
import com.blog.entity.LifeShare;
import com.blog.service.LifeShareService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class LifeShareServiceImpl extends BasiServiceImpl<LifeShare, Object> implements LifeShareService, AssociaInterface  {
	@Resource
	LifeShareDao lifeShareDao; 
	
	public BaseDao<LifeShare> getDao(){
		return lifeShareDao;
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
		return "";
	}
	@Override
	public String getId() {
		return "id";
	}
	@Override
	public String getTable() {
		return "life";
	}
}

