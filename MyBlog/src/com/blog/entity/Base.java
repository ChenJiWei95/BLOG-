package com.blog.entity;

import java.lang.reflect.Field;

public class Base {  

	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getName()+" [");
		Class<?> clazz = this.getClass();// 获取PrivateClass整个类
		Field[] fs = clazz.getDeclaredFields();// 获取PrivateClass所有属性
		if(fs.length > 0) {
			for (Field item : fs) {
				item.setAccessible(true);// 将目标属性设置为可以访问
				if(item.getModifiers() == 2) {
					try {
						sb.append(item.getName()).append(" = ").append(item.get(this)).append(", ");
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					} 
				}
			}
			sb.delete(sb.length()-2, sb.length());
		}
		return sb.append("]").toString();
	}
	
}
