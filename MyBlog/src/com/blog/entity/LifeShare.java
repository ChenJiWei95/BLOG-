package com.blog.entity;
public class LifeShare extends Base{
	private String id;
	private String time;
	private String content;
	private String chat_num;
	private String brow_num;
	private String like_num;
	private String pit_url;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getChat_num() {
		return chat_num;
	}
	public void setChat_num(String chat_num) {
		this.chat_num = chat_num;
	}
	public String getBrow_num() {
		return brow_num;
	}
	public void setBrow_num(String brow_num) {
		this.brow_num = brow_num;
	}
	public String getLike_num() {
		return like_num;
	}
	public void setLike_num(String like_num) {
		this.like_num = like_num;
	}
	public String getPit_url() {
		return pit_url;
	}
	public void setPit_url(String pit_url) {
		this.pit_url = pit_url;
	}
}
