package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.ATagDao;
import com.blog.dao.BaseDao;
import com.blog.entity.ATag;
import com.blog.service.ATagService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class ATagServiceImpl extends BasiServiceImpl<ATag, Object> implements ATagService, AssociaInterface  {
	@Resource
	ATagDao aTagDao; 
	
	public BaseDao<ATag> getDao(){
		return aTagDao;
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
		return "article_tag";
	}
}

