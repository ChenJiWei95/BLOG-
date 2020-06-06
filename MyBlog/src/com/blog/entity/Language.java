package com.blog.entity;
public class Language extends Base{
	private String id;
	private String code;
	private String cn_zh;
	private String en_us;


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCn_zh() {
		return cn_zh;
	}
	public void setCn_zh(String cn_zh) {
		this.cn_zh = cn_zh;
	}
	public String getEn_us() {
		return en_us;
	}
	public void setEn_us(String en_us) {
		this.en_us = en_us;
	}
}
