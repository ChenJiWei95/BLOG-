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
	
	private final String FIX = "WHERE";
	private final String AND = "AND";
	private final String ORDER_BY_FIX = "ORDER BY ";
	private final String LIMIT_FIX = "LIMIT ";
	 
	private String table; //表名
	
	private StringBuilder content;
	private String orderBySql;
	private String limitSql;
	
	/**
	 * 升序
	 * @param cloumName
	 */
	public void setOrderByDESC(String cloumName) {
		if(cloumName == null || "".equals(cloumName)) throw new NullPointerException();
		orderBySql = ORDER_BY_FIX + cloumName + " DESC ";
	}
	/**
	 * 降序
	 * @param cloumName 
	 */
	public void setOrderByASC(String cloumName) {
		if(cloumName == null || "".equals(cloumName)) throw new NullPointerException();
		orderBySql = ORDER_BY_FIX + cloumName + " ASC ";
	}
	
	
	//limit start, size
	/**
	 * 分页
	 * @param start 初始位置
	 * @param size	 长度
	 */
	public void setLimit(int start, int size) {
		limitSql = LIMIT_FIX + start + "," + size;
	}
	/**
	 * 分页
	 * start 默认零开始
	 * @param size 
	 */
	public void setLimit(int size) {
		setLimit(0, size);
	}
	/**
	 * 分页
	 * start 默认零开始
	 * size 默认是10
	 */
	public void setLimit() {
		setLimit(0, 10);
	}
	
	Map<String, Object> map = new HashMap<>();
	public void put(String key, Object value){
		if((key == null || "".equals(key)) 
			&& (value == null || "".equals(value))) 
			throw new NullPointerException("key="+key+", value="+value);
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
	public String getOrderBySql() {
		return orderBySql;
	}
	public String getLimitSql() {
		return limitSql;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
}
