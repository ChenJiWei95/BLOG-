package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.NoticeDao;
import com.blog.dao.BaseDao;
import com.blog.entity.Notice;
import com.blog.service.NoticeService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class NoticeServiceImpl extends BasiServiceImpl<Notice, Object> implements NoticeService, AssociaInterface  {
	@Resource
	NoticeDao noticeDao; 
	
	public BaseDao<Notice> getDao(){
		return noticeDao;
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
}

