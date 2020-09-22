package com.blog.entity;
public class Language extends Base{
	private String id;
	private String code;
	private String zh_CN;
	private String en_US;

	public Language(){}
	
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

	public String getZh_CN() {
		return zh_CN;
	}

	public void setZh_CN(String zh_CN) {
		this.zh_CN = zh_CN;
	}

	public String getEn_US() {
		return en_US;
	}

	public void setEn_US(String en_US) {
		this.en_US = en_US;
	}

}
