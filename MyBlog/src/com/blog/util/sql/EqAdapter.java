package com.blog.util.sql;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
 * 	有id=NULL 的查询时，用eq集合
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
	
	public static final String SQL_WHERE 		= "WHERE"		;
	public static final String SQL_INSERT 		= "INSERT INTO ";
	public static final String SQL_AND 			= " AND "		;
	public static final String SQL_MULL 		= "NULL "		;
	public static final String SQL_ORDERBY 		= "ORDER BY "	;
	public static final String SQL_LIMIT 		= "LIMIT "		;
	public static final String SQL_ORDERBY_DESC = "DESC "		;
	public static final String SQL_ORDERBY_ASC 	= "ASC "		;
	public static final String SQL_TABLE 		= "table "		;
	public static final String SQL_LIKE 		= "LIKE "		;
	
	 
	protected String id						; // 主键 
	private String table					; // 表名 
	private String values;
	private String eqSql;
	
	private String columns					; // 结果		集字符 
	private Object target;
	private Map<String, Object> eqAndPutMap	; // 条件&插入	集 
	private Map<String, Object> updateMap	; // 修改     	集 
	
	private String like;
	
	private BaseInterface parame;

	public EqAdapter(){
	}
	
	public BaseInterface getParame() {
		return parame;
	}
	
	// 条件语句
	public String getWhereSql() throws Exception { 
		if(getEqSql() != null || "".equals(getEqSql()))
			return SQL_AND + " " + getEqSql();
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
		StringBuilder whereSql = new StringBuilder();
		for(Map.Entry<String, Object> item : eqAndPutMap.entrySet()){
			whereSql.append(SQL_AND)
					.append(quma(item.getKey())+" = ")
//					.append((isLike(((String) item.getValue()).trim()) ? " LIKE " : " = "))
					.append("string".equals(TypeToolsGenerics.getType(item.getValue()))
							? quma2((String) item.getValue())+" "
							: (item.getValue() == null ? SQL_MULL : item.getValue()) + " "); 	
		}
		return whereSql.toString();
	}
	
	/*protected boolean isLike(String value) {
		if(value.length() > 0) 
			return value.charAt(0) == '%' 
				? (value.charAt(value.length()-1) == '%' ? true : false) 
				: false; 
		return false; 
	}*/
	
	public String getInsertSql() throws Exception{
		
		if(eqAndPutMap == null || eqAndPutMap.size() == 0)
			throw new Exception("数据库插入操作-插入数据不能为空；集合为空或者集合元素不能为零：map = " + eqAndPutMap);
		if(table == null || "".equals(table))
			throw new Exception("table = " + table);
		
		StringBuilder cloumns = new StringBuilder();
		StringBuilder values = new StringBuilder();
		for(Map.Entry<String, Object> item : eqAndPutMap.entrySet())
		{
			if(item.getKey() == null || "".equals(item.getKey()))
				continue;
			cloumns.append(quma(item.getKey())+",");
			values.append("string".equals(TypeToolsGenerics.getType(item.getValue())) 
							? quma2((String) item.getValue())
							: (item.getValue())).append(",");
		}
		
		StringBuilder insertSql = new StringBuilder();
		insertSql.append(SQL_INSERT).append(table)
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
		if(this.updateMap!=null)
			this.updateMap.putAll(updateMap);
		else
			this.updateMap = updateMap;
	}

	public Map<String, Object> getEqAndPutMap() {
		return eqAndPutMap;
	}

	public void setEqAndPutMap(Map<String, Object> eqAndPutMap) {
		if(this.eqAndPutMap!=null)
			this.eqAndPutMap.putAll(eqAndPutMap);
		else
			this.eqAndPutMap = eqAndPutMap;
	}

	public String getColumns() {
		return columns;
	}

	public EqAdapter setColumns(String columns) {
		this.columns = columns; return this;
	}

	public String getEqSql() {
		return eqSql;
	}

	public EqAdapter setEqSql(String eqSql) {
		this.eqSql = eqSql;
		return this;
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
		return table;
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
	

	/**
	 * 自定义查找列名 否则默认‘*’
	 * <p>	 
	 * @param columns
	 * void
	 * @see
	 * @since 1.0
	 */
	public EqAdapter setColumns(Object... columns) {
		StringBuilder temp = new StringBuilder("");
		for(Object item : columns){
			if(item == null || "".equals(item))
				continue;
			temp.append(quma((String) item)+", ");
		}
		this.columns = temp.length() > 0 ? temp.deleteCharAt(temp.length()-1).deleteCharAt(temp.length()-1).toString() : temp.toString(); 
		return this;
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
		this.table = cloumnUtil(table);
		return this;
	}
	public EqAdapter setTableStatement(String table) {
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
	public String getLikeSql() {
		return getLike();
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
	public EqAdapter like(String cloumn, String likeStr) {return this;}
	public EqAdapter setSql(String sql) {return this;}

	// 输入必要参数
	@SuppressWarnings("unchecked")
	public EqAdapter setParame(BaseInterface associaInterface) {
		if(associaInterface != null) {
			Class<AssociaInterface> clazz = (Class<AssociaInterface>) associaInterface.getClass();
			Class<?> thisClazz = this.getClass();
			String[] fields = {"table"};
			try {
				for(String field : fields) {
					String field_ = field.substring(0,1).toUpperCase().concat(field.substring(1).toLowerCase());
					// 将associaInterface接口的数据反射写入adapter fields集中的字段
					thisClazz.getMethod("set" + field_, String.class)
						.invoke(this, clazz.getMethod("get" + field_).invoke(associaInterface));
				}
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return this; 
	}	
	
	// 只获取private修饰的字段
	// 获取所有字段 属性集合
	protected Object [] parseColumnOfObjectAll(Object target) throws InstantiationException, IllegalAccessException {
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
		return fields.toArray();
	}
	// 只获取private修饰的字段
	// 获取字段值非空的 值数组集
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
	// 获取字段值非空的 键值
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
	// 获取字段值非空的 值
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

	public String getLike() {
		return like;
	}

	public void setLike(String like) {
		this.like = like;
	}
	
	/**
	 * 加符号 通常为字段或表 返回 `str`
	 * @param str
	 * @return
	 */
	protected String quma(String str) {
		return "`"+str+"`";
	}
	/**
	 * 加符号 通常为字符串 返回 'str'
	 * @param str
	 * @return
	 */
	protected String quma2(String str) {
		return "'"+str+"'";
	}
	
	/**
	 * 加符号 通常为字段或表 返回 `str`
	 * @param str
	 * @return
	 */
	protected String cloumnUtil(String cloumn){
		return "`"+cloumn+"`";
	}
}
