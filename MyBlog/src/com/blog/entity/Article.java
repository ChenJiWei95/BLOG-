package com.blog.entity;
public class Article extends Base{
	private String id;
	private String name;
	private String create_time;
	private String update_time;
	private String pit_url;
	private String simp_desc;
	private String status;
	private String tags;
	private String browse_num;
	private String like_num;
	private String chat_num;


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
	public String getSimp_desc() {
		return simp_desc;
	}
	public void setSimp_desc(String simp_desc) {
		this.simp_desc = simp_desc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getBrowse_num() {
		return browse_num;
	}
	public void setBrowse_num(String browse_num) {
		this.browse_num = browse_num;
	}
	public String getLike_num() {
		return like_num;
	}
	public void setLike_num(String like_num) {
		this.like_num = like_num;
	}
	public String getChat_num() {
		return chat_num;
	}
	public void setChat_num(String chat_num) {
		this.chat_num = chat_num;
	}
}
