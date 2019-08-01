package com.blog.entity;

import java.util.HashMap;
import java.util.Map;

import com.blog.exception.UnconvertibleException;
import com.blog.util.TypeToolsGenerics;
/**
 * <b>适配类 -- 实体类和mybatis配置的文件的中间类，达到更高的复用效果</b>
 * <p>
 * 作用：
 * 1、生成条件语句
 * 2、排序
 * 3、自定义查询字段
 * 4、分页
 * <br>注意：
 * WHERE `id` = 1 
 * 强制实体类字段与数据库字段一致
 * 描述:<br>
 * 
 * @author 威 
 * <br>2019年7月30日 下午10:22:35 
 * @see
 * @since 1.0
 */
public class EqAdapter{
	
	private final String WHERE_FIX = "WHERE";
	private final String AND_FIX = "AND";
	private final String ORDER_BY_FIX = "ORDER BY ";
	private final String LIMIT_FIX = "LIMIT ";
	 
	private String table; //表名 
	private String orderBySql;		//排序
	private String limitSql;		//分页
	private String columnSql;		//查找的列
	private Map<String, Object> eqMap;
	private Map<String, Object> updateMap;
	
	public EqAdapter(){
		columnSql = "*";
	}
	
	public String getWhereSql() throws Exception {
		if(eqMap == null)
			throw new Exception("集合为空：map = " + eqMap);
		if(table == null || "".equals(table))
			throw new Exception("table = " + table);
		
		int len = eqMap.size();
		if(len > 0){
			StringBuilder whereSql = new StringBuilder(WHERE_FIX);
			for(Map.Entry<String, Object> item : eqMap.entrySet()){
				whereSql	.append(" `"+ item.getKey() +"` = ")
						.append("string".equals(TypeToolsGenerics.getType(item.getValue())) 
								? "'"+item.getValue()+"' " 
								: (item.getValue()) + " ")
						.append(AND_FIX);
			}
			whereSql.delete(whereSql.length()-4, whereSql.length());
			return whereSql.toString();
		}else return "";
	}
	public String getUpdateSql() throws Exception {
		
		if(updateMap == null || updateMap.size() == 0)
			throw new Exception("集合为空或者集合元素不能为零：map = " + updateMap); 
		if(table == null || "".equals(table))
			throw new Exception("table = " + table);
		
		StringBuilder updateSql = new StringBuilder();
		for(Map.Entry<String, Object> item : updateMap.entrySet()){
			updateSql	.append(" `"+ item.getKey() +"` = ")
					.append("string".equals(TypeToolsGenerics.getType(item.getValue())) 
							? "'"+item.getValue()+"' " 
							: (item.getValue()) + " ")
					.append(",");
		}
		updateSql.deleteCharAt(updateSql.length()-1);
		return updateSql.toString();	
	}
	public String getInsertSql() throws Exception{
		
		if(eqMap == null || eqMap.size() == 0)
			throw new Exception("集合为空或者集合元素不能为零：map = " + eqMap);
		if(table == null || "".equals(table))
			throw new Exception("table = " + table);
		
		StringBuilder cloumns = new StringBuilder();
		StringBuilder values = new StringBuilder();
		for(Map.Entry<String, Object> item : eqMap.entrySet())
		{
			if(item.getKey() == null || "".equals(item.getKey()))
				continue;
			cloumns.append("`"+item.getKey()+"`,");
			values.append("string".equals(TypeToolsGenerics.getType(item.getValue())) 
							? "'"+item.getValue()+"'" 
							: (item.getValue())).append(",");
		}
		
		StringBuilder insertSql = new StringBuilder();
		insertSql.append("insert into ").append(table)
		.append("(").append(cloumns.deleteCharAt(cloumns.length()-1)).append(") ")
		.append("values(").append(values.deleteCharAt(values.length()-1)).append(");");
		return insertSql.toString();
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
	public String getColumnSql() {
		return columnSql;
	}
	

	/**
	 * 自定义查找列名 否则默认‘*’
	 * <p>	 
	 * @param columns
	 * void
	 * @see
	 * @since 1.0
	 */
	public void setColumns(String... columns) {
		StringBuilder temp = new StringBuilder("");
		for(String column : columns){
			if(column == null || "".equals(column))
				continue;
			temp.append("`"+column+"` ,");
		}
		columnSql = temp.length() > 0 ? temp.deleteCharAt(temp.length()-1).toString() : temp.toString(); 
	}


	/**
	 * 升序
	 * @param columnName
	 */
	public void setOrderByDESC(String columnName) {
		if(columnName == null || "".equals(columnName)) throw new NullPointerException();
		orderBySql = ORDER_BY_FIX + "`" + columnName + "` DESC ";
	}
	/**
	 * 降序
	 * @param columnName 
	 */
	public void setOrderByASC(String columnName) {
		if(columnName == null || "".equals(columnName)) throw new NullPointerException();
		orderBySql = ORDER_BY_FIX + "`" + columnName + "` ASC ";
	}
	
	
	//limit start, size
	/**
	 * 分页
	 * 0为第一个下标
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
	public void setLimit(int start) {
		setLimit(start, 10);
	} 
	
	/**
	 * 设置查询条件
	 * <p>	 
	 * @param map
	 * void
	 * @see
	 * @since 1.0
	 */
	public void eq(Map<String, Object> map){
		
		this.eqMap = map;
	}
	
	/**
	 * 设置查询条件
	 * <p>	 
	 * @param key
	 * @param value
	 * void
	 * @see
	 * @since 1.0
	 */
	public void eq(String key, Object value){
		
		if((key == null || "".equals(key)) 
			&& (value == null || "".equals(value))) 
			throw new NullPointerException("key="+key+", value="+value);
		if(eqMap == null) eqMap = new HashMap<>();
		eqMap.put(key, value);
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
		
		this.updateMap = map;
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
		if(updateMap == null) updateMap = new HashMap<>();
		updateMap.put(key, value);
	}
	/**
	 * 设置插入项
	 * <p>	 
	 * @param map
	 * void
	 * @see
	 * @since 1.0
	 */
	public void put(Map<String, Object> map){
		
		this.eq(map);
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
	public void put(String key, Object value){
		
		this.eq(key, value);
	}
	
	public void setTable(String table) {
		this.table = "`"+table+"`";
	}
}
