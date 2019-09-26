package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.TempComponentDao;
import com.blog.dao.BaseDao;
import com.blog.entity.TempComponent;
import com.blog.service.TempComponentService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class TempComponentServiceImpl extends BasiServiceImpl<TempComponent, Object> implements TempComponentService, AssociaInterface  {
	@Resource
	TempComponentDao tempComponentDao; 
	
	public BaseDao<TempComponent> getDao(){
		return tempComponentDao;
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
		return "temp_component";
	}
}

