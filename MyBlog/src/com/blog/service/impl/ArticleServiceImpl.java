package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.ArticleDao;
import com.blog.dao.BaseDao;
import com.blog.entity.ATag;
import com.blog.entity.Article;
import com.blog.service.ArticleService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class ArticleServiceImpl extends BasiServiceImpl<Article, ATag> implements ArticleService, AssociaInterface  {
	@Resource
	ArticleDao articleDao; 
	
	public BaseDao<Article> getDao(){
		return articleDao;
	}
	
	@Override
	public String getBrige_table() {
		return "article_tag_brige";
	}
	@Override
	public String getBrige_key() {
		return "a_id";
	}
	@Override
	public String getBrige_association_key() {
		return "t_id";
	}
	@Override
	public String getAssociation_table() {
		return "article_tag";
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
		return "article";
	}
}

