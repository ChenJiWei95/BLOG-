package com.blog.service.impl;

import java.util.List;
import java.util.Map;

import com.blog.dao.BaseDao;
import com.blog.service.BasiService;
import com.blog.util.sql.AssociaInterface;
import com.blog.util.sql.EqAdapter;
import com.blog.util.sql.InsertAdapter;
import com.blog.util.sql.SelectAdapter;
import com.blog.util.sql.UpdateAdapter;
/**
 * 基类 实现基本的增删查改
 * @author Administrator
 *
 * @param <T>
 * @param <V>
 */
public class BasiServiceImpl<T, V> implements BasiService<T, V>, AssociaInterface{

	@Override
	public T get(T t) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setLimit(0, 1)
				.setTarget(t);
		try {
			return (T) getDao().get(sql).get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<T> gets(T t) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setTarget(t);
		try {
			return (List<T>) getDao().get(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<T> getOfOrderBySort(T t, String sort, String column) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setTarget(t);
		sort = sort.toLowerCase();
		if("asc".equals(sort))
			sql.setOrderByASC(column);
		else if("desc".equals(sort))
			sql.setOrderByDESC(column);
		try {
			return (List<T>) getDao().get(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<T> getOfOrderBySortAndLimit(T t, String sort, String column, int start, int size) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setTarget(t)
				.setLimit(start, size);
		sort = sort.toLowerCase();
		if("asc".equals(sort))
			sql.setOrderByASC(column);
		else if("desc".equals(sort))
			sql.setOrderByDESC(column);
		try {
			return (List<T>) getDao().get(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<T> getOfLimit(T t, int start, int size) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setTarget(t)
				.setLimit(start, size);
		try {
			return (List<T>) getDao().get(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void delete(T t) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setTarget(t);
		try {
			getDao().delete(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(T t, Map<String, Object> eq) {
		EqAdapter sql = new UpdateAdapter()
				.setParame(this)
				.eq(eq)
				.setTarget(t)
				;
		try {
			getDao().update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(T t) {
		EqAdapter sql = new InsertAdapter()
				.setParame(this)
				.setTarget(t); 
		try {
			getDao().insert(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<V> getAssociat(T t) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setTarget(t);
		try {
			return (List<V>) getDao().associate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<V> getAssociatOfOrderBySort(T t, String sort, String column) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setTarget(t);
		sort = sort.toLowerCase();
		if("asc".equals(sort))
			sql.setOrderByASC(column);
		else if("desc".equals(sort))
			sql.setOrderByDESC(column);
		try {
			return (List<V>) getDao().associate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<V> getAssociatOfOrderBySortAndLimit(T t, String sort, String column, int start, int size) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setTarget(t)
				.setLimit(start, size);
		sort = sort.toLowerCase();
		if("asc".equals(sort))
			sql.setOrderByASC(column);
		else if("desc".equals(sort))
			sql.setOrderByDESC(column);
		
		try {
			return (List<V>) getDao().associate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<V> getAssociatOfLimit(T t, int start, int size) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setTarget(t)
				.setLimit(start, size);
		try {
			return (List<V>) getDao().associate(sql);
		} catch (Exception e) {	
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getTable() {
		return null;
	}

	@Override
	public String getBrige_table() {
		return null;
	}

	@Override
	public String getBrige_key() {
		return null;
	}

	@Override
	public String getBrige_association_key() {
		return null;
	}

	@Override
	public String getAssociation_table() {
		return null;
	}

	@Override
	public String getAssociation_table_id() {
		return null;
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public BaseDao<T> getDao() {
		// TODO Auto-generated method stub
		return null;
	}
}
