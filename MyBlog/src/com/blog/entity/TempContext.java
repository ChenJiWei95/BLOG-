package com.blog.entity;


public class TempContext {
	private String id;
	private String sign;
	private String title;
	private String key;
	private String desc;
	private String create_time;
	private String update_time;
		
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
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
	public static void main(String[] args) {
		// C:/Users/Administrator/git/MyBlog/MyBlog/src/com/blog/entity/temp/
		System.out.println(TempContext.class.getResource("/").getPath().replace("build/classes", "src"));
		
	}
}
