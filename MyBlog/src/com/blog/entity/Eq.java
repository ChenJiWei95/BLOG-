package com.blog.entity;

import java.util.HashMap;
import java.util.Map;

import com.blog.util.TypeToolsGenerics;
/**
 * <b>生成条件语句</b>
 * <p>
 * WHERE `id` = 1 
 * 描述:<br>
 * 
 * @author 威 
 * <br>2019年7月30日 下午10:22:35 
 * @see
 * @since 1.0
 */
public class Eq{
	private StringBuilder content;
	private final String FIX = "WHERE";
	private final String AND = "AND";
	
	Map<String, Object> map = new HashMap<>();
	public void put(String key, Object value){
		map.put(key, value);
	}
	public String getContent() {
		int len = map.size();
		if(len > 0){
			content = new StringBuilder(FIX);
			for(Map.Entry<String, Object> item : map.entrySet()){
				content	.append(" `"+ item.getKey() +"` = ")
						.append("string".equals(TypeToolsGenerics.getType(item.getValue())) 
								? "'"+item.getValue()+"' " 
								: (item.getValue()) + " ")
						.append(AND);
			}
			content.delete(content.length()-4, content.length());
			return content.toString();
		}else return "";
	}
	
}
