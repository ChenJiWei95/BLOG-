package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.MessageDao;
import com.blog.dao.BaseDao;
import com.blog.entity.Message;
import com.blog.service.MessageService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class MessageServiceImpl extends BasiServiceImpl<Message, Object> implements MessageService, AssociaInterface  {
	@Resource
	MessageDao messageDao; 
	
	public BaseDao<Message> getDao(){
		return messageDao;
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
		return "message";
	}
}

