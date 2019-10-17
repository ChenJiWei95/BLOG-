package com.blog.service;

import java.util.List;
import java.util.Map;

import com.blog.dao.BaseDao;
import com.blog.entity.Menu;
import com.blog.util.sql.ManyTable;

public interface BasiService<T, V> {
	
	Integer count();
	
	Integer count(String eq);
	
	Integer count(Map<String, Object> eq);
	
	Integer count(T t);
	
	List<T> find(String sql);
	
	BaseDao<T> getDao();
	
	/**
	 * 查询单个
	 * @param t 条件实体 
	 * @return
	 */
	T get(T t);
	
	T get(T t, Object column);
	
	T get(String eq); 
	
	T getForColum(String eq, Object column); 
	
	List<T> getAll(); 
	
	T getForColumn(Object column); 
	
	/**
	 * 查询集合
	 * @param t 条件实体
	 * @return
	 */
	List<T> gets(T t);
	
	/**
	 * 查询集合
	 * @param t 条件实体
	 * @return
	 */
	List<T> gets(T t, Object... column);
	
	/**
	 * 查询集合
	 * 不用写where前缀 
	 * @param eq 条件语句
	 * @return
	 */
	List<T> gets(String eq);
	
	/**
	 * 查询集合
	 * 不用写where前缀 
	 * @param eq 条件语句
	 * @return
	 */
	List<T> gets(String eq, Object column);
	
	/**
	 * 排序 	不倡导调用此接口
	 * @param t 条件实体
	 * @param sort 增减序
	 * @return
	 */
	List<T> getBySort(T t, String sort, String column);
	
	/**
	 * 排序 降序
	 * @param t
	 * @param column
	 * @return
	 */
	List<T> getByASC(T t, String column);
	
	/**
	 * 排序 降序
	 * @param eq
	 * @param column
	 * @return
	 */
	List<T> getByASC(String eq, String column);
	
	/**
	 * 排序 降序
	 * @param eq
	 * @param column
	 * @return
	 */
	List<T> getByASC(Map<String, Object> eq, String column);
	
	/**
	 * 排序 降序
	 * @param column
	 * @return
	 */
	List<T> getByASC(String column);
	
	/**
	 * 排序 升序
	 * @param t
	 * @param column
	 * @return
	 */
	List<T> getByDESC(T t, String column);
	
	/**
	 * 排序 升序
	 * @param eq
	 * @param column
	 * @return
	 */
	List<T> getByDESC(String eq, String column);
	
	/**
	 * 排序 升序
	 * @param eq
	 * @param column
	 * @return
	 */
	List<T> getByDESC(Map<String, Object> eq, String column);
	
	/**
	 * 排序 升序
	 * @param column
	 * @return
	 */
	List<T> getByDESC(String column);
	
	/**
	 * 排序 分页
	 * @param t 条件实体
	 * @param sort 增减序
	 * @param start 开始
	 * @param size 页面长度
	 * @return
	 */
	List<T> getBySortAndLimit(T t, String sort, String column, int start, int size);
	
	/**
	 * 分页
	 * @param t 查询条件实体
	 * @param start	开始
	 * @param size	页面长度
	 * @return
	 */
	List<T> getByLimit(T t, int start, int size);
	
	/**
	 * 删除 
	 * @param t 条件实体
	 */
	void delete(T t);
	
	/**
	 * 删除 
	 * @param t 条件实体
	 */
	void delete(String eq);
	
	/**
	 * 删除 
	 * @param t 条件实体
	 */
	void delete(Map<String, Object> eq);
	
	/**
	 * 修改
	 * @param t 修改内容实体
	 * @param eq 条件
	 */
	void update(T t, Map<String, Object> eq);
	
	/**
	 * 修改
	 * @param t 修改内容实体
	 * @param eq 条件
	 */
	void update(T t, String eq);
	
	/**
	 * 插入
	 * @param t
	 */
	void insert(T t);
	
	/**
	 * 关联
	 * @param t
	 * @return
	 */
	List<V> getAssociat(T t);
	
	/**
	 * 关联
	 * @param t
	 * @param sort
	 * @param column
	 * @return
	 */
	List<V> getAssociatOfOrderBySort(T t, String sort, String column);
	
	/**
	 * 关联
	 * @param t
	 * @param sort
	 * @param column
	 * @param start
	 * @param size
	 * @return
	 */
	List<V> getAssociatOfOrderBySortAndLimit(T t, String sort, String column, int start, int size);
	
	/**
	 * 关联
	 * @param t
	 * @param start
	 * @param size
	 * @return
	 */
	List<V> getAssociatOfLimit(T t, int start, int size);
	
	/**
	 * 多表查询
	 * @param colStatement		查询结果字段语句
	 * @param tableStatement	多表语句
	 * @param eqStatement		查询条件语句
	 * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> getOfManyTable(String colStatement, String tableStatement, String eqStatement);
	
	/**
	 * 多表查询
	 * <p>	 
	 * @param t
	 * @return
	 * List<Map<String,Object>>
	 * @see
	 * @since 1.0
	 */
	List<Map<String, Object>> getOfManyTable(ManyTable t);

	/**
	 * 多表查询 分页
	 * <p>	 
	 * @param t
	 * @param start
	 * @param size
	 * @return
	 * List<Map<String,Object>>
	 * @see
	 * @since 1.0
	 */
	List<Map<String, Object>> getOfManyTableByLimit(ManyTable t, int start, int size);

	/**
	 * 多表查询 
	 * <p>	 
	 * @param t
	 * @param col
	 * @return
	 * List<Map<String,Object>>
	 * @see
	 * @since 1.0
	 */
	List<Map<String, Object>> getOfManyTableByASC(ManyTable t, String col);
	
	/**
	 * 多表查询
	 * <p>	 
	 * @param t
	 * @param col
	 * @return
	 * List<Map<String,Object>>
	 * @see
	 * @since 1.0
	 */
	List<Map<String, Object>> getOfManyTableByDESC(ManyTable t, String col);
}
