package com.blog.entity;
/**
 * 保存 
 * 	后台基础信息 id = 1
 * 	网站基础信息
 * @author cjw
 *
 */
public class WebsiteBase extends Base { 
	private String id;
	private String name; 
	private String url; 
	private String update_time; 
	private String msg;  
	private String spread;
	
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


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getUpdate_time() {
		return update_time;
	}


	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public String getSpread() {
		return spread;
	}


	public void setSpread(String spread) {
		this.spread = spread;
	}


	public static void main(String[] args) {
		WebsiteBase b = new WebsiteBase();
		b.setId("12");
		b.setMsg("355");
		b.setName("3566");
		b.setUpdate_time("346");
		b.setUrl("64657"); 
		System.out.println(b.toString());
	}
	
}
