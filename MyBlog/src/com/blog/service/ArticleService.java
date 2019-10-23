package com.blog.service;

import java.util.List;

import com.blog.entity.Article;

public interface ArticleService extends BasiService<Article, Object>{
	// 获取评论最火的三篇文章
	// 获取最新的 三篇文章
	List<Article> getHotArticles();
}
