package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.ArticleContentDao;
import com.blog.dao.BaseDao;
import com.blog.entity.ArticleContent;
import com.blog.service.ArticleContentService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class ArticleContentServiceImpl extends BasiServiceImpl<ArticleContent, Object> implements ArticleContentService, AssociaInterface  {
	@Resource
	ArticleContentDao articleContentDao; 
	
	public BaseDao<ArticleContent> getDao(){
		return articleContentDao;
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
		return "article_content";
	}
}

