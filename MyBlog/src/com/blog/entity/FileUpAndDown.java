package com.blog.entity;
public class FileUpAndDown extends Base{
	private String id;
	private String create_date;
	private String file_name;
	private String actual_name;
	private String path;
	private String status;


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getActual_name() {
		return actual_name;
	}
	public void setActual_name(String actual_name) {
		this.actual_name = actual_name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
