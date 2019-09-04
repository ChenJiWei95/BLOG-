package com.blog.test;

public class PrivateClass {
	private String name;
	private String age;
	private String large;
	private final String fi = "1";
	public final String ij = "2";
	public String a = "a";
	public static String LAR = "123";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getLarge() {
		return large;
	}
	public void setLarge(String large) {
		this.large = large;
	}
	
}
