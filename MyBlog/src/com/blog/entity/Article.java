package com.blog.entity;
public class Article extends Base{
	private String id;
	private String name;
	private String create_time;
	private String update_time;
	private String pit_url;
	private String mark_url;
	private String simp_desc;


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
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getPit_url() {
		return pit_url;
	}
	public void setPit_url(String pit_url) {
		this.pit_url = pit_url;
	}
	public String getMark_url() {
		return mark_url;
	}
	public void setMark_url(String mark_url) {
		this.mark_url = mark_url;
	}
	public String getSimp_desc() {
		return simp_desc;
	}
	public void setSimp_desc(String simp_desc) {
		this.simp_desc = simp_desc;
	}
}
