package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.AimgDao;
import com.blog.dao.BaseDao;
import com.blog.entity.Aimg;
import com.blog.service.AimgService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class AimgServiceImpl extends BasiServiceImpl<Aimg, Object> implements AimgService, AssociaInterface  {
	@Resource
	AimgDao aimgDao; 
	
	public BaseDao<Aimg> getDao(){
		return aimgDao;
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
		return "aimg";
	}
}

