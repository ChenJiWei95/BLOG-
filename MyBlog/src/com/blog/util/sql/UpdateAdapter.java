package com.blog.util.sql;

import java.util.HashMap;
import java.util.Map;

import com.blog.util.TypeToolsGenerics;

public class UpdateAdapter extends EqAdapter { 
	
	@Override
	public EqAdapter like(String cloumn, String likeStr) {
		setLike(cloumnUtil(cloumn) + " LIKE '" + likeStr + "'");
		return this;
	}
	@Override
	public String getUpdateSql() throws Exception {
		Map <String, Object> updateMap = this.getUpdateMap();
		Object target = this.getTarget();
		String table = this.getTable();
		if(updateMap == null)
			updateMap = new HashMap<String, Object>();
		updateMap.putAll(parseMapByEntity(target));
		
		if(table == null || "".equals(table))
			throw new Exception("table = " + table);
		
		StringBuilder updateSql = new StringBuilder();
		for(Map.Entry<String, Object> item : updateMap.entrySet()){
			updateSql.append(" "+quma(item.getKey())+" = ")
					 .append("string".equals(TypeToolsGenerics.getType(item.getValue())) 
							? quma2((String) item.getValue())+" " 
							: (item.getValue()) + " ")
					 .append(",");
		}
		if(updateSql.length() > 0) updateSql.deleteCharAt(updateSql.length()-1);
		
		return updateSql.toString();	
	}
	/**
	 * 设置修改项
	 * <p>	 
	 * @param map
	 * void
	 * @see
	 * @since 1.0
	 */
	public void set(Map<String, Object> map){
		
		this.setUpdateMap(map);
	}
	
	/**
	 * 设置修改项
	 * <p>	 
	 * @param key
	 * @param value
	 * void
	 * @see
	 * @since 1.0
	 */
	public void set(String key, Object value){
		
		if((key == null || "".equals(key)) 
				&& (value == null || "".equals(value))) 
				throw new NullPointerException("key="+key+", value="+value);
		if(this.getUpdateMap() == null) this.setUpdateMap(new HashMap<>());
		
		this.getUpdateMap().put(key, value);
	}
}
