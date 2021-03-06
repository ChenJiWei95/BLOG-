package com.blog.util.sql;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.blog.util.TypeToolsGenerics;
/**
 * 
 * @author cjw
 * 关联的时候必须要调用setId 设置主键名 
 */
public class SelectAdapter extends EqAdapter {
	private String orderBySql				; // 排序
	private String limitSql					; // 分页 
	private String sql						;
	
	// 关联对象 
	private String brige_table				; // 桥接表 名 
	private String brige_key				; // 桥接表 对应本类字段 
	private String brige_association_key	; // 桥接表 对应关联类字段 
	private String association_table		; // 关联表 名 
	private String association_table_id		; // 关联表 id字段	
	
	/**
	 * 升序
	 * @param columnName
	 * @param sort 
	 * 		EqAdapter.ORDERBY_DESC，EqAdapter.ORDERBY_ASC
	 * @return
	 */
	public EqAdapter setOrderByDESC(String columnName, String sort) {
		if(columnName == null || "".equals(columnName)) throw new NullPointerException();
		orderBySql = SQL_ORDERBY + quma(columnName)+" "+sort+" ";
		return this;
	} 
	/**
	 * 限制查询数量 例如 ‘LIMIT 0, 1000’
	 * <p>	 
	 * @return
	 * String
	 * @see
	 * @since 1.0
	 */
	public String getLimitSql() {
		return limitSql;
	}
	
	@Override
	public EqAdapter like(String cloumn, String likeStr) {
		setLike(cloumnUtil(cloumn) + " LIKE '" + likeStr + "'");
		return this;
	}
	@Override
	public String getOrderBySql() {
		return orderBySql;
	}
	@Override
	public String getWhereSql() throws Exception {
		Object target = this.getTarget();
		if(target != null)  
			eq(parseMapByEntity(target));
		
		return super.getWhereSql();
	}	
	@Override
	public EqAdapter setOrderByDESC(String columnName) {
		if(columnName == null || "".equals(columnName)) throw new NullPointerException();
		orderBySql = SQL_ORDERBY + quma(columnName)+" "+EqAdapter.SQL_ORDERBY_DESC+" ";
		return this;
	} 
	@Override
	public EqAdapter setOrderByASC(String columnName) {
		if(columnName == null || "".equals(columnName)) throw new NullPointerException();
		orderBySql = SQL_ORDERBY + quma(columnName)+" "+EqAdapter.SQL_ORDERBY_ASC+" ";
		return this;
	}
	@Override
	public String getColumnSql() {
		String columns = this.getColumns();
		if(columns != null && !"".equals(columns))
			return columns; 
		return "*";
	}
	@Override
	public EqAdapter setSql(String sql) {
		this.sql = sql;
		return this;
	}
	@SuppressWarnings("unchecked")
	@Override
	public EqAdapter setParame(BaseInterface associaInterface) {
		super.setParame(associaInterface);
		
		if(associaInterface == null)
			throw new NullPointerException("setParame 参数不能为空:"+associaInterface);
		Class<AssociaInterface> clazz = (Class<AssociaInterface>) associaInterface.getClass();
		Class<?> thisClazz = this.getClass();
		String[] fields = {"brige_table", "brige_key", "brige_association_key", "association_table", "association_table_id", "id"};
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
		return this;
	}
	
	
	/**
	 * 可以插入完整的任何sql语句 例如 ‘SELECT * FROM `admin`’
	 * <p>	 
	 * @return
	 * String
	 * @see
	 * @since 1.0
	 */
	public String getSql() {
		return sql;
	}
	
	
	
	/**
	 * 关联查询的条件
	 * <p>	 
	 * @return
	 * @throws Exception
	 * String
	 * @see
	 * @since 1.0
	 */
	public String getWhereSqlOfAssociation() throws Exception {
		
		String table = this.getTable();
		Object target = this.getTarget();
		if(target != null)  
			eq(parseMapByEntity(target));
		if(table == null || "".equals(table))
			throw new NullPointerException("table不能为空 :" + table);
		
		Map<String, Object> eqAndPutMap = this.getEqAndPutMap();
		if(eqAndPutMap == null || eqAndPutMap.size() == 0)
			throw new NullPointerException("条件查询结果集为空：map = " + eqAndPutMap);
		StringBuilder whereSql = new StringBuilder(SQL_WHERE);
		whereSql.append(" "+getBrige_table()+"."+getBrige_key()+" = ");
		whereSql.append(getTable()+"."+getId()+" ").append(SQL_AND);
		for(Map.Entry<String, Object> item : eqAndPutMap.entrySet()){
			whereSql	.append(" "+quma(item.getKey())+" = ")
					.append("string".equals(TypeToolsGenerics.getType(item.getValue())) 
							? quma2((String) item.getValue())+" " 
							: (item.getValue()) + " ")
					.append(SQL_AND);
		}
		whereSql.delete(whereSql.length()-4, whereSql.length());
		return whereSql.toString();
	}
	
	/**
	 * 分页
	 * 0为第一个下标
	 * @param start 初始位置
	 * @param size	 长度
	 */
	public EqAdapter setLimit(int start, int size) {
		limitSql = SQL_LIMIT + start + "," + size;
		return this;
	}	
	
	/**
	 * 分页
	 * start 默认零开始
	 * @param size 
	 */
	public EqAdapter setLimit(int start) {
		setLimit(start, 10);
		return this;
	} 
	
	public String getBrige_table() {
		return cloumnUtil(brige_table);
	}

	public String getBrige_key() {
		return cloumnUtil(brige_key);
	}

	public String getBrige_association_key() {
		return cloumnUtil(brige_association_key);
	}

	public String getAssociation_table() {
		return cloumnUtil(association_table);
	}

	public String getAssociation_table_id() {
		return cloumnUtil(association_table_id);
	}
	
	public EqAdapter setBrige_table(String brige_table) {
		this.brige_table = brige_table;
		return this;
	}

	public EqAdapter setBrige_key(String brige_key) {
		this.brige_key = brige_key;
		return this;
	}

	public EqAdapter setBrige_association_key(String brige_association_key) {
		this.brige_association_key = brige_association_key;
		return this;
	}

	public EqAdapter setAssociation_table(String association_table) {
		this.association_table = association_table;
		return this;
	}

	public EqAdapter setAssociation_table_id(String association_table_id) {
		this.association_table_id = association_table_id;
		return this;
	}
	 
}
