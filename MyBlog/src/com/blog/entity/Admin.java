package com.blog.entity;
/**
 * 管理员登录表
 * @author cjw
 */
public class Admin extends Base{
	private String id;
	private String username;
	private String password; 
	private String state;
	private String login_count;
	private AdminInfor admin_infor;
	private Role role;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLogin_count() {
		return login_count;
	}
	public void setLogin_count(String login_count) {
		this.login_count = login_count;
	}
	public AdminInfor getAdmin_infor() {
		return admin_infor;
	}
	public void setAdmin_infor(AdminInfor admin_infor) {
		this.admin_infor = admin_infor;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}
