package com.blog.entity;
public class PlanBase extends Base{
	private String id;
	private String secret_key;
	private String excitation_text;
	private String plan_name;
	private String create_date;


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSecret_key() {
		return secret_key;
	}
	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}
	public String getExcitation_text() {
		return excitation_text;
	}
	public void setExcitation_text(String excitation_text) {
		this.excitation_text = excitation_text;
	}
	public String getPlan_name() {
		return plan_name;
	}
	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
}
