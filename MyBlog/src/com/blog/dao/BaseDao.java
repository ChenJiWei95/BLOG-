package com.blog.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;




/**
 * 
 * @className BaseDao
 * @Description
 * @author rjht
 * @contact qq 676342073
 * @date 2014-9-26 下午12:14:17
 */
public interface BaseDao<T> {

	/**
	 * 保存对象
	 * 
	 * @param entity
	 */
	public Serializable save(T entity) throws Exception;

	/**
	 * 更新对象
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void merge(T entity) throws Exception;

	/**
	 * 更新对象
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(T entity) throws Exception;

	/**
	 * 删除对象
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void delete(T entity) throws Exception;

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delBatch(Serializable[] ids) throws Exception;

	/**
	 * 通过sql获取对象
	 * 
	 * @param hql
	 * @return
	 */
	public T find(String hql) throws Exception;

	/**
	 * 根据序列化load对象
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public T load(Serializable id) throws Exception;

	/**
	 * 根据序列化id获取对象
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public T get(Serializable id) throws Exception;

	/**
	 * 根据id获取对象
	 * 
	 * @param c
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public T get(Class<T> c, Serializable id) throws Exception;

	/**
	 * 获取对象，根据多参数
	 * 
	 * @param hql
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public T find(String hql, String... values) throws Exception;

	/**
	 * 通过参数获取对象
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public T find(String hql, Map<String, Object> params) throws Exception;

	/**
	 * 通过参数获取对象
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public T find(String hql, Map<String, Object> params, Session session) throws Exception;

	/**
	 * 通过hql和条件获取对象列表
	 * 
	 * @param hql
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public List<T> findList(String hql, String... values) throws Exception;

	/**
	 * 通过hql和条件获取对象列表
	 * 
	 * @param hql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<T> findList(String hql, Map<String, Object> params) throws Exception;

	/**
	 * 根据对象获取所有列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<T> findAll() throws Exception;

	/**
	 * 根据对象获取所有列表
	 * 
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public List<T> findAll(Class<T> c) throws Exception;

	/**
	 * 根据属性值获取对象列表
	 * 
	 * @param proerty
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public List<T> findByProperty(String property, Object value) throws Exception;

	/**
	 * 根据属性值获取对象列表
	 * 
	 * @param c
	 * @param proerty
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public List<T> findByProperty(Class<T> c, String property, Object value) throws Exception;

	/**
	 * 根据属性值获取对象
	 * 
	 * @param proerty
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public T findUniqueByProperty(String property, Object value) throws Exception;

	/**
	 * 根据属性判断是否存在
	 * 
	 * @param property
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public boolean propertyExists(String property, Object value) throws Exception;

	/**
	 * 根据属性值获取对象
	 * 
	 * @param c
	 * @param proerty
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public T findUniqueByProperty(Class<T> c, String property, Object value) throws Exception;

	/**
	 * 根据属性值获取统计条数
	 * 
	 * @param property
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public long countByProperty(String property, Object value) throws Exception;

	/**
	 * 获取统计个数
	 * 
	 * @param hql
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public long count(String hql, String... values) throws Exception;

	/**
	 * 获取统计个数
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Integer countSql(String sql, Map<String, Object> params) throws Exception;

	/**
	 * 获取统计个数
	 * 
	 * @param sql
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public Integer countSql(String sql, String... values) throws Exception;

	/**
	 * 获取统计个数
	 * 
	 * @param hql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public long count(String hql, Map<String, Object> params) throws Exception;
   
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

	public List<T> findListByIn(String hql, Map<String, Object> params) throws Exception;

}
