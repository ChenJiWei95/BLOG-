package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.FileUpAndDownDao;
import com.blog.dao.BaseDao;
import com.blog.entity.FileUpAndDown;
import com.blog.service.FileUpAndDownService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class FileUpAndDownServiceImpl extends BasiServiceImpl<FileUpAndDown, Object> implements FileUpAndDownService, AssociaInterface  {
	@Resource
	FileUpAndDownDao fileUpAndDownDao; 
	
	public BaseDao<FileUpAndDown> getDao(){
		return fileUpAndDownDao;
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
		return "file_up_down";
	}
}

