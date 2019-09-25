package com.blog.entity.temp;

import java.util.ArrayList;
import java.util.List;

public class ComponentSet {
	
	List<TempEntity> entitys = new ArrayList<>();
	public List<TempEntity> getEntitys(){
		return entitys;
	}
	public void add(TempEntity entity) {
		entitys.add(entity);
	}
	public String toString() {
		// 基本输出 每一个元素
		StringBuilder sb = new StringBuilder();
		for(TempEntity entity : entitys)
			sb.append(entity.toString()).append(System.lineSeparator());
		return sb.toString();
	}
}
