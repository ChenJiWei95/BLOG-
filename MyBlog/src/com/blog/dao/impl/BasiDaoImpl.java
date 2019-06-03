package com.blog.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.blog.dao.BasiDao;
import com.blog.dao.Session;

public class BasiDaoImpl<T> implements BasiDao<T> {
	private Class<T> entityClass;
	@Autowired
	private SessionFactory sessionFactory;
	public BasiDaoImpl() {
		Type type = getClass().getGenericSuperclass();
		Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
		entityClass = (Class<T>) parameterizedType[0];
	}
	public Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}
	@Override
	public Serializable save(Object entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable save(Object entity, Session session) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Object entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Object entity, Session session) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Object entity) {
		// TODO Auto-generated method stub
		Assert.notNull(entity);
		this.getCurrentSession().delete(entity);
	}
	

	@Override
	public Object find(Object entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object load(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(Serializable id) {
		return null;
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List finds(Object entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findsById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refresh(Object entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void evict(Object entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}
	
}
