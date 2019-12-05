package com.blog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.ArticleDao;
import com.blog.dao.BaseDao;
import com.blog.entity.Article;
import com.blog.service.ArticleService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class ArticleServiceImpl extends BasiServiceImpl<Article, Object> implements ArticleService, AssociaInterface  {
	@Resource
	ArticleDao articleDao; 
	
	public BaseDao<Article> getDao(){
		return articleDao;
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
		return "article";
	}

	@Override
	public List<Article> getHotArticles() {
		return null;
	}
}

