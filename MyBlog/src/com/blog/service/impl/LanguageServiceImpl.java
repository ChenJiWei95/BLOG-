package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.LanguageDao;
import com.blog.dao.BaseDao;
import com.blog.entity.Language;
import com.blog.service.LanguageService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class LanguageServiceImpl extends BasiServiceImpl<Language, Object> implements LanguageService, AssociaInterface  {
	@Resource
	LanguageDao languageDao; 
	
	public BaseDao<Language> getDao(){
		return languageDao;
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
		return "language";
	}
}

