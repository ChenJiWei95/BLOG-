package com.blog.dao;

import java.util.List;

import com.blog.entity.Note;
import com.blog.util.sql.EqAdapter;

public interface NoteDao extends BaseDao<Note> {
	String findNicknameById(String id);
	List<Note> show(EqAdapter eq);
}
