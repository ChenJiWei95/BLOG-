package com.blog.entity;

/**
 * 
 * @author cjw
 */
public class User {
	private Integer id;
	private String username;
	private String password;
	private String tableName;
	private String content;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		sb.append("id:" + id).append(", ");
		sb.append("username:" + username).append(", ");
		sb.append("password:" + password).append("]");
		return sb.toString();
	}
}
