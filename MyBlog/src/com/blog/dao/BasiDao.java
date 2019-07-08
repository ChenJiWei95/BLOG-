package com.blog.dao;

import java.io.Serializable;
import java.util.List;

//import org.hibernate.Session;

//import com.rjht.Page;
//import com.rjht.exception.DBException;

public interface BasiDao<T> {
	/*Serializable save(T entity);
	Serializable save(T entity, Session session);
	void update(T entity);
	void update(T entity, Session session);
	void delete(T entity);
	T find(T entity);
	T findById(Serializable id);
	T load(Serializable id);
	T get(Serializable id);
	List<T> findAll();
	List<T> finds(T entity);
	List<T> findsById(Serializable id);*/
	//public long count
	//public Page<T> findPage
	/**
	 * 刷新实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	void refresh(T entity);

	/**
	 * 清除缓存
	 */
	void clear();

	/**
	 * 清除缓存
	 */
	void evict(T entity);

	/**
	 * 同步数据
	 */
	void flush();
}
