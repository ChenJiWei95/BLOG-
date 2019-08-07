package com.blog.entity;

public class Relate {
	private String id;
	private String name;
	
	private Target_ target_;
	private Target__ target__;
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
	public Target_ getTarget_() {
		return target_;
	}
	public void setTarget_(Target_ target_) {
		this.target_ = target_;
	}
	public Target__ getTarget__() {
		return target__;
	}
	public void setTarget__(Target__ target__) {
		this.target__ = target__;
	}
}
