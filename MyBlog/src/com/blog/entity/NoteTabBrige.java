package com.blog.entity;
public class NoteTabBrige extends Base{
	private String id;
	private String name;
	private String note_id;
	private String note_tab_id;
	private String admin_id;


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNote_id() {
		return note_id;
	}
	public void setNote_id(String note_id) {
		this.note_id = note_id;
	}
	public String getNote_tab_id() {
		return note_tab_id;
	}
	public void setNote_tab_id(String note_tab_id) {
		this.note_tab_id = note_tab_id;
	}
	public String getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}
}
