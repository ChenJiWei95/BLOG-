package com.blog.util.sql;

import java.util.Map;

import com.blog.util.TypeToolsGenerics;

public class InsertAdapter extends EqAdapter {
	// 父类中是获取所有列 包括空字段的列
	@Override
	public String getColumnSql() {
		String columns = this.getColumns();
		Object target = this.getTarget();
		if(columns != null && !"".equals(columns)) {
			return columns;
		}
			
		if(target != null) {
			try {
				put(this.parseMapOfObject(target));
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			super.setColumns(this.getEqAndPutMap().keySet().toArray());
		} 
		return super.getColumns();
	}
	
	/**
	 * 设置插入项
	 * <p>	 
	 * @param map
	 * void
	 * @see
	 * @since 1.0
	 */
	public EqAdapter put(Map<String, Object> map){
		this.eq(map);
		return this;
	}
	
	/**
	 * 设置插入项
	 * <p>	 
	 * @param key
	 * @param value
	 * void
	 * @see
	 * @since 1.0
	 */
	public EqAdapter put(String key, Object value){
		
		this.eq(key, value);
		return this;
	}
	
	/**
	 * 插入的值，一个或多个
	 * <p>	 
	 * @param vals
	 * @return
	 * EqAdapter
	 * @see
	 * @since 1.0
	 */
	public EqAdapter setValues(Object...vals) {
		StringBuffer temp = new StringBuffer();
		for(Object item : vals){
			if("string".equals(TypeToolsGenerics.getType(item)))
				temp.append(quma2((String) item)+", ");
			else 
				temp.append(item+", ");
		}
		super.setValues(temp.length() > 0 ? temp.deleteCharAt(temp.length()-1).deleteCharAt(temp.length()-1).toString() : temp.toString());
		return this;
	}
	
	@Override
	public String getValues() { 
		setValues(this.getEqAndPutMap().values().toArray());
		return super.getValues();
	}
}
