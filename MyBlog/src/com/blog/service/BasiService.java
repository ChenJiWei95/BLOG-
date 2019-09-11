package com.blog.service;

import java.util.List;
import java.util.Map;

public interface BasiService<T, V> {
	T get(T t);
	List<T> gets(T t);
	List<T> getOfOrderBy(T t, String sort);
	List<T> getOfOrderByAndLimit(T t, String sort, int start, int size);
	List<T> getOfLimit(T t, int start, int size);
	void delete(T t);
	void update(T t, Map<String, Object> eq);
	void insert(T t);
	List<V> getAssociat(T t);
	List<V> getAssociatOfOrderBy(T t, String sort);
	List<V> getAssociatOfOrderByAndLimit(T t, String sort, int start, int size);
	List<V> getAssociatOfLimit(T t, int start, int size);
}
