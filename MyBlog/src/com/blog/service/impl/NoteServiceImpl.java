package com.blog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.NoteDao;
import com.blog.dao.BaseDao;
import com.blog.entity.Note;
import com.blog.service.NoteService;
import com.blog.util.sql.AssociaInterface;
import com.blog.util.sql.EqAdapter;
import com.blog.util.sql.SelectAdapter;

@Service
@Transactional
public class NoteServiceImpl extends BasiServiceImpl<Note, Object> implements NoteService, AssociaInterface  {
	@Resource
	NoteDao noteDao; 
	
	public BaseDao<Note> getDao(){
		return noteDao;
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
		return "note";
	}

	@Override
	public String findNicknameById(String id) {
		return noteDao.findNicknameById(id);
	}

	@Override
	public List<Note> show(String sql) {
		EqAdapter adapter = new SelectAdapter()
				.setParame(this)
				.setSql(sql);
		try {
			return ((NoteDao) getDao()).show(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Note> find_(String sql) {
		try {
			return ((NoteDao) getDao()).show(
					new SelectAdapter()
					.setSql(sql));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

