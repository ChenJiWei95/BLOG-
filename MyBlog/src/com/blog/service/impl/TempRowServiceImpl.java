package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.TempRowDao;
import com.blog.dao.BaseDao;
import com.blog.entity.TempRow;
import com.blog.service.TempRowService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class TempRowServiceImpl extends BasiServiceImpl<TempRow, Object> implements TempRowService, AssociaInterface  {
	@Resource
	TempRowDao tempRowDao; 
	
	public BaseDao<TempRow> getDao(){
		return tempRowDao;
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
		return "temp_row";
	}
}

