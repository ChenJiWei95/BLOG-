package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.NoteTabBrigeDao;
import com.blog.dao.BaseDao;
import com.blog.entity.NoteTabBrige;
import com.blog.service.NoteTabBrigeService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class NoteTabBrigeServiceImpl extends BasiServiceImpl<NoteTabBrige, Object> implements NoteTabBrigeService, AssociaInterface  {
	@Resource
	NoteTabBrigeDao noteTabBrigeDao; 
	
	public BaseDao<NoteTabBrige> getDao(){
		return noteTabBrigeDao;
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
		return "note_tab_brige";
	}
}

