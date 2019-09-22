package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.BaseDao;
import com.blog.dao.MenuDao;
import com.blog.entity.Clazz;
import com.blog.entity.Menu;
import com.blog.service.MenuService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class MenuServiceImpl extends BasiServiceImpl<Menu, Clazz> implements MenuService, AssociaInterface  {
	@Resource
	MenuDao menuDao; 
	
	public BaseDao<Menu> getDao(){
		return menuDao;
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
		return "menu";
	}
}

