package com.blog.service;

import java.util.List;
import java.util.Map;

import com.blog.dao.BaseDao;
import com.blog.entity.Menu;

public interface BasiService<T, V> {
	BaseDao<T> getDao();
	/**
	 * 查询单个
	 * @param t 条件实体 
	 * @return
	 */
	T get(T t);
	/**
	 * 查询集合
	 * @param t 条件实体
	 * @return
	 */
	List<T> gets(T t);
	/**
	 * 排序
	 * @param t 条件实体
	 * @param sort 增减序
	 * @return
	 */
	List<T> getOfOrderBySort(T t, String sort, String column);
	/**
	 * 排序 分页
	 * @param t 条件实体
	 * @param sort 增减序
	 * @param start 开始
	 * @param size 页面长度
	 * @return
	 */
	List<T> getOfOrderBySortAndLimit(T t, String sort, String column, int start, int size);
	/**
	 * 分页
	 * @param t 查询条件实体
	 * @param start	开始
	 * @param size	页面长度
	 * @return
	 */
	List<T> getOfLimit(T t, int start, int size);
	/**
	 * 删除 
	 * @param t 条件实体
	 */
	void delete(T t);
	/**
	 * 修改
	 * @param t 修改内容实体
	 * @param eq 条件
	 */
	void update(T t, Map<String, Object> eq);
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
}
