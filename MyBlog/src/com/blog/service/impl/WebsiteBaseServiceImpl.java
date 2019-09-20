package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.BaseDao;
import com.blog.dao.WebsiteBaseDao;
import com.blog.entity.WebsiteBase;
import com.blog.service.WebsiteBaseService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class WebsiteBaseServiceImpl extends BasiServiceImpl<WebsiteBase, Object> implements WebsiteBaseService<WebsiteBase, Object>, AssociaInterface  {
	@Resource
	WebsiteBaseDao websiteBaseDao; 
	
	public BaseDao<WebsiteBase> getDao(){
		return websiteBaseDao;
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
		return "website_base";
	}
}

