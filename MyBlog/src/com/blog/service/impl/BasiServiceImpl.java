package com.blog.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.blog.dao.BaseDao;
import com.blog.service.BasiService;
import com.blog.util.sql.AssociaInterface;
import com.blog.util.sql.EqAdapter;
import com.blog.util.sql.SelectAdapter;

public class BasiServiceImpl<T, V> implements BasiService<T, V>, AssociaInterface{

	@Resource
	BaseDao<T> baseDao;
	@Override
	public T get(T t) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setLimit(0, 1)
				.setTarget(t);
		try {
			return (T) baseDao.get(sql).get(0);
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
			return baseDao.get(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<T> getOfOrderBy(T t, String sort) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setTarget(t);
		sort = sort.toLowerCase();
		if("asc".equals(sort))
			sql.setOrderByASC(sort);
		else if("desc".equals(sort))
			sql.setOrderByDESC(sort);
		try {
			return baseDao.get(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<T> getOfOrderByAndLimit(T t, String sort, int start, int size) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setTarget(t)
				.setLimit(start, size);
		sort = sort.toLowerCase();
		if("asc".equals(sort))
			sql.setOrderByASC(sort);
		else if("desc".equals(sort))
			sql.setOrderByDESC(sort);
		try {
			return baseDao.get(sql);
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
			return baseDao.get(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void delete(T t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(T t, Map<String, Object> eq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(T t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<V> getAssociat(T t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<V> getAssociatOfOrderBy(T t, String sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<V> getAssociatOfOrderByAndLimit(T t, String sort, int start, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<V> getAssociatOfLimit(T t, int start, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBrige_table() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBrige_key() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBrige_association_key() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAssociation_table() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAssociation_table_id() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	 
	
}
