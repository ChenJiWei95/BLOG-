package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.TagBrigeDao;
import com.blog.dao.BaseDao;
import com.blog.entity.TagBrige;
import com.blog.service.TagBrigeService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class TagBrigeServiceImpl extends BasiServiceImpl<TagBrige, Object> implements TagBrigeService, AssociaInterface  {
	@Resource
	TagBrigeDao tagBrigeDao; 
	
	public BaseDao<TagBrige> getDao(){
		return tagBrigeDao;
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
		return "article_tag_brige";
	}
}

