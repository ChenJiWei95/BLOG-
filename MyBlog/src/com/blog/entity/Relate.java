package com.blog.entity;

import java.util.ArrayList;

public class Relate {
	private String id;
	private String name;
	
	private ArrayList<Target_> target_;
	private ArrayList<Target__> target__;
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
	public ArrayList<Target_> getTarget_() {
		return target_;
	}
	public void setTarget_(ArrayList<Target_> target_) {
		this.target_ = target_;
	}
	public ArrayList<Target__> getTarget__() {
		return target__;
	}
	public void setTarget__(ArrayList<Target__> target__) {
		this.target__ = target__;
	}
	 
}
