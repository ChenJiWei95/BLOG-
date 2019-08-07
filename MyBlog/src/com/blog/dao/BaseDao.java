package com.blog.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.blog.entity.EqAdapter;
import com.blog.entity.Relate;
import com.blog.entity.User;




/**
 * 
 * @className BaseDao
 * @Description
 * @author rjht
 * @contact qq 676342073
 * @date 2014-9-26 下午12:14:17
 */
public interface BaseDao<T> {
	T getById(Integer id) throws Exception;
	T get(T t) throws Exception;
	List<T> gets(T t) throws Exception;
	void save(T t) throws Exception;
	void update(T t) throws Exception;
	void delete(T t) throws Exception; 
	
	List<T> getTest(EqAdapter eq) throws Exception;
	void updateTest(EqAdapter eq) throws Exception;
	void insertTest(EqAdapter eq) throws Exception;
	
	User getTest2(Integer id) throws Exception;
	
	List<Relate> relateTest () throws Exception;

}
