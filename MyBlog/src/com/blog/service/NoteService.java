package com.blog.service;

import java.util.List;

import com.blog.entity.Note;

public interface NoteService extends BasiService<Note, Object>{
	String findNicknameById(String id);
	List<Note> show(String sql);
	List<Note> find_(String sql);
}
