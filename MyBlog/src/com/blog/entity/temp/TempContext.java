package com.blog.entity.temp;


public class TempContext {
	private String id;
	private String sign;
	private String title;
	private String key;
	private String desc; 
		
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

	public static void main(String[] args) {
		// C:/Users/Administrator/git/MyBlog/MyBlog/src/com/blog/entity/temp/
		System.out.println(TempContext.class.getResource("").getPath().replace("build/classes", "src"));
	}
}
