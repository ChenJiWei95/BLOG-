package com.blog.util.sql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blog.test.PrivateClass;
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
public abstract class EqAdapter{
	
	public static final String WHERE_FIX 		= "WHERE"		;
	public static final String AND_FIX 			= "AND"			;
	public static final String ORDER_BY_FIX 	= "ORDER BY "	;
	public static final String LIMIT_FIX 		= "LIMIT "		;
	 
	protected String id						; // 主键 
	private String table					; // 表名 
	private String values;
	
	private String columns					; // 结果		集字符 
	private Object target;
	private Map<String, Object> eqAndPutMap	; // 条件&插入	集 
	private Map<String, Object> updateMap	; // 修改     	集 
	
	public EqAdapter(){
	}
	
	// 条件语句
	public String getWhereSql() throws Exception { 
		if(table == null || "".equals(table))
			throw new Exception("table = " + table);
		if(eqAndPutMap == null || eqAndPutMap.size() < 0)
			return "";
		
		return createEqSql();	
	}	
	
	public String getUpdateSql() throws Exception {
		return "";
	}

	protected String createEqSql() {
		StringBuilder whereSql = new StringBuilder(WHERE_FIX);
		for(Map.Entry<String, Object> item : eqAndPutMap.entrySet()){
			whereSql	.append(" `"+ item.getKey() +"` = ")
					.append("string".equals(TypeToolsGenerics.getType(item.getValue())) 
							? "'"+item.getValue()+"' " 
							: (item.getValue()) + " ")
					.append(AND_FIX);
		}
		whereSql.delete(whereSql.length()-4, whereSql.length());
		return whereSql.toString();
	}
	
	
	
	public String getInsertSql() throws Exception{
		
		if(eqAndPutMap == null || eqAndPutMap.size() == 0)
			throw new Exception("集合为空或者集合元素不能为零：map = " + eqAndPutMap);
		if(table == null || "".equals(table))
			throw new Exception("table = " + table);
		
		StringBuilder cloumns = new StringBuilder();
		StringBuilder values = new StringBuilder();
		for(Map.Entry<String, Object> item : eqAndPutMap.entrySet())
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
	
	
	public Map<String, Object> getUpdateMap() {
		return updateMap;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public void setUpdateMap(Map<String, Object> updateMap) {
		this.updateMap = updateMap;
	}

	public Map<String, Object> getEqAndPutMap() {
		return eqAndPutMap;
	}

	public void setEqAndPutMap(Map<String, Object> eqAndPutMap) {
		this.eqAndPutMap = eqAndPutMap;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getId() {
		return cloumnUtil(id);
	}

	/**
	 * 输入主键名称 关联查询需要该字段
	 * @param id
	 * @return
	 */
	public EqAdapter setId(String id) {
		this.id = id;
		return this;
	}
	
	

	public String getTable() {
		return cloumnUtil(table);
	}
	
	public String getColumnSql() {
		if(columns != null && !"".equals(columns))
			return columns; 
		if(target != null) {
			try {
				this.setColumns(parseColumnOfObjectAll(target));
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				return "*";
			}
			return columns;
		}
		return "*";
	}
	
	protected String cloumnUtil(String cloumn){
		return "`" + cloumn + "`";
	}

	/**
	 * 自定义查找列名 否则默认‘*’
	 * <p>	 
	 * @param columns
	 * void
	 * @see
	 * @since 1.0
	 */
	public void setColumns(Object... columns) {
		StringBuilder temp = new StringBuilder("");
		for(Object item : columns){
			if(item == null || "".equals(item))
				continue;
			temp.append("`"+item+"`, ");
		}
		this.columns = temp.length() > 0 ? temp.deleteCharAt(temp.length()-1).deleteCharAt(temp.length()-1).toString() : temp.toString(); 
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
	public EqAdapter eq(String key, Object value){
		
		if((key == null || "".equals(key)) 
			&& (value == null || "".equals(value))) 
			throw new NullPointerException("key="+key+", value="+value);
		if(eqAndPutMap == null) eqAndPutMap = new HashMap<>();
		eqAndPutMap.put(key, value);
		return this;
	}
	
	/**
	 * 设置查询条件
	 * <p>	 
	 * @param map
	 * void
	 * @see
	 * @since 1.0
	 */
	public EqAdapter eq(Map<String, Object> map){
		if(eqAndPutMap != null)
			this.eqAndPutMap.putAll(map);
		else 
			this.eqAndPutMap = map;
		return this;
	}
	
	public EqAdapter setTable(String table) {
		this.table = table;
		return this;
	}
	
	public Object getTarget() {
		return target;
	}

	public EqAdapter setTarget(Object target) {
		this.target = target;
		return this;
	}
	
	public EqAdapter setOrderByDESC(String columnName) {return this;}
	public EqAdapter setOrderByASC(String columnName) {return this;}
	public EqAdapter setLimit(int start, int size) {return this;} 
	public EqAdapter setLimit(int start) {return this;}
	public String getOrderBySql() {return "";}
	public EqAdapter setBrige_table(String brige_table) { return this;}
	public EqAdapter setBrige_key(String brige_key) { return this;}
	public EqAdapter setBrige_association_key(String brige_association_key) { return this;}
	public EqAdapter setAssociation_table(String association_table) { return this;}
	public EqAdapter setAssociation_table_id(String association_table_id) { return this;}
	public EqAdapter setAssociaInterface(AssociaInterface associaInterface) { return this;}

	// 只获取private修饰的字段
	// 获取所有字段集合
	protected String [] parseColumnOfObjectAll(Object target) throws InstantiationException, IllegalAccessException {
		Class<?> clazz = target.getClass();// 获取PrivateClass整个类
//		Object pc = clazz.newInstance();// 创建一个实例
		List<String> fields = null;
		Field[] fs = clazz.getDeclaredFields();// 获取PrivateClass所有属性
		fields = new ArrayList<>(fs.length);
		for (int i = 0; i < fs.length; i++) {
			fs[i].setAccessible(true);// 将目标属性设置为可以访问
			if(fs[i].getModifiers() == 2)
				fields.add(fs[i].getName());
		} 
		return (String[]) fields.toArray();
	}
	// 只获取private修饰的字段
	// 获取非空字段的集合
	protected String [] parseColumnOfObject(Object target) throws InstantiationException, IllegalAccessException {
		Class<?> clazz = target.getClass();// 获取PrivateClass整个类
//		Object pc = clazz.newInstance();// 创建一个实例
		List<String> fields = null;
		Field[] fs = clazz.getDeclaredFields();// 获取PrivateClass所有属性
		fields = new ArrayList<>(fs.length);
		for (int i = 0; i < fs.length; i++) {
			fs[i].setAccessible(true);// 将目标属性设置为可以访问
			if(fs[i].get(target) != null && fs[i].getModifiers() == 2)
				fields.add(fs[i].getName());
		} 
		return (String[]) fields.toArray();
	}
	// 只获取private修饰的字段
	// 获取非空字段的键值
	protected Map<String, Object> parseMapOfObject(Object target) throws InstantiationException, IllegalAccessException {
		Class<?> clazz = target.getClass();// 获取PrivateClass整个类
//		Object pc = clazz.newInstance();// 创建一个实例
		
		Field[] fs = clazz.getDeclaredFields();// 获取PrivateClass所有属性
		Map<String, Object> map = new HashMap<>();
		for (int i = 0; i < fs.length; i++) {
			fs[i].setAccessible(true);// 将目标属性设置为可以访问
			if(fs[i].get(target) != null && fs[i].getModifiers() == 2)
				map.put(fs[i].getName(), fs[i].get(target));
		} 
		return map;
	}
	// 只获取private修饰的字段
	// 获取非空字段的值
	protected Object [] parseValuesOfObject(Object target) throws InstantiationException, IllegalAccessException {
		Class<?> clazz = target.getClass();// 获取PrivateClass整个类
//		Object pc = clazz.newInstance();// 创建一个实例
		List<Object> fields = null;
		Field[] fs = clazz.getDeclaredFields();// 获取PrivateClass所有属性
		fields = new ArrayList<>(fs.length);
		for (int i = 0; i < fs.length; i++) {
			fs[i].setAccessible(true);// 将目标属性设置为可以访问
			if(fs[i].get(target) != null && fs[i].getModifiers() == 2)
				fields.add(fs[i].get(target));
		} 
		return (Object[]) fields.toArray();
	}
	
}
