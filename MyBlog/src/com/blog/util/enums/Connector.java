package com.blog.util.enums;

public enum Connector {
	and,
	or;
	public static Connector fromString(String value) {
		return Connector.valueOf(value.toLowerCase());
	}
}
