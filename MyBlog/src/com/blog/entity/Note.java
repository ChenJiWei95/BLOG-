package com.blog.entity;
public class Note extends Base{
	private String id;
	private String name;
	private String create_date;
	private String update_date;
	private String content;
	private String admin_id;
	private String status;
	private String tags;
	private AdminInfor admin_infor;
	
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
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
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
	public AdminInfor getAdmin_infor() {
		return admin_infor;
	}
	public void setAdmin_infor(AdminInfor admin_infor) {
		this.admin_infor = admin_infor;
	}
	
}
