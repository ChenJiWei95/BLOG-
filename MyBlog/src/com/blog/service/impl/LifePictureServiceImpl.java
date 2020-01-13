package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.LifePictureDao;
import com.blog.dao.BaseDao;
import com.blog.entity.LifePicture;
import com.blog.service.LifePictureService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class LifePictureServiceImpl extends BasiServiceImpl<LifePicture, Object> implements LifePictureService, AssociaInterface  {
	@Resource
	LifePictureDao lifePictureDao; 
	
	public BaseDao<LifePicture> getDao(){
		return lifePictureDao;
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
		return "life_picture";
	}
}

