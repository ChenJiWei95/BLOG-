package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.DataDao;
import com.blog.dao.BaseDao;
import com.blog.entity.Data;
import com.blog.service.DataService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class DataServiceImpl extends BasiServiceImpl<Data, Object> implements DataService, AssociaInterface  {
	@Resource
	DataDao dataDao; 
	
	public BaseDao<Data> getDao(){
		return dataDao;
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
		return "data";
	}
}

