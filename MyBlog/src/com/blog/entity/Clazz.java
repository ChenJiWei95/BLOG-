package com.blog.entity;

public class Clazz {
	private String id;
	private String class_name;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		sb.append("id:" + id).append(", ");
		sb.append("class_name:" + class_name).append(", ");
		sb.append("]");
		return sb.toString();
	}
}
