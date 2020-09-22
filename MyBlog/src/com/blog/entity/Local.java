package com.blog.entity;

public class Local {
	/** 语言 ‘zh_CN’/‘en_US’ */
	private String lang;
	/** label显示 */
	private String text;

	public Local(String lang, String text){
		this.lang = lang;
		this.text = text;
	}
	
	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
