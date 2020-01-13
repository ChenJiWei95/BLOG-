package com.blog.entity;
public class PlanDo extends Base{
	private String id;
	private String plan_base_id;
	private String plan_tag_id;
	private String name;
	private String time;
	private String status;


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlan_base_id() {
		return plan_base_id;
	}
	public void setPlan_base_id(String plan_base_id) {
		this.plan_base_id = plan_base_id;
	}
	public String getPlan_tag_id() {
		return plan_tag_id;
	}
	public void setPlan_tag_id(String plan_tag_id) {
		this.plan_tag_id = plan_tag_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
