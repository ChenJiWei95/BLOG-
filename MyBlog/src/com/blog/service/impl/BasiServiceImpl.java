package com.blog.service.impl;

import java.util.List;
import java.util.Map;

import com.blog.dao.BaseDao;
import com.blog.service.BasiService;
import com.blog.util.sql.AssociaInterface;
import com.blog.util.sql.EqAdapter;
import com.blog.util.sql.InsertAdapter;
import com.blog.util.sql.ManyTable;
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
		EqAdapter adapter = new SelectAdapter()
				.setParame(this)
				.setLimit(0, 1)
				.setTarget(t);
		try {
			List<T> list = getDao().get(adapter);
			return list.size()>0 ? (T) list.get(0) : null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public T get(String eq) {
		EqAdapter adapter = new SelectAdapter()
				.setParame(this)
				.setLimit(0, 1)
				.setEqSql(eq);
		try {
			List<T> list = getDao().get(adapter);
			return list.size()>0 ? (T) list.get(0) : null;
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
	public List<T> getBySort(T t, String sort, String column) {
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
	public List<T> getBySortAndLimit(T t, String sort, String column, int start, int size) {
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
	public List<T> getByLimit(T t, int start, int size) {
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
		return null;
	}

	@Override
	public void delete(Map<String, Object> eq) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.eq(eq);
		try {
			getDao().delete(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<T> gets(String eq) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setEqSql(eq);
		try {
			return (List<T>) getDao().get(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void delete(String eq) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setEqSql(eq);
		try {
			getDao().delete(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(T t, String eq) {
		EqAdapter sql = new UpdateAdapter()
				.setParame(this)
				.setEqSql(eq)
				.setTarget(t)
				;
		try {
			getDao().update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<T> find(String sql) {
		EqAdapter adapter = new SelectAdapter()
				.setParame(this)
				.setSql(sql);
		try {
			return getDao().find(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public T getForColum(String eq, Object column) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setLimit(0, 1)
				.setColumns(column)
				.setEqSql(eq);
		try {
			return getDao().get(sql).get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<T> gets(String eq, Object column) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setEqSql(eq)
				.setColumns(column);
		try {
			return (List<T>) getDao().get(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<T> gets(T t, Object... column) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setTarget(t)
				.setColumns(column);
		try {
			return (List<T>) getDao().get(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public T get(T t, Object column) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setLimit(0, 1)
				.setColumns(column)
				.setTarget(t);
		try {
			return getDao().get(sql).get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
 
	@Override
	public T getForColumn(Object column) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setLimit(0, 1)
				.setColumns(column);
		try {
			return getDao().get(sql).get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<T> getAll() {
		EqAdapter sql = new SelectAdapter()
				.setParame(this);
		try {
			return getDao().get(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Integer count() {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setColumns("count(\"1\")");
		try {
			return getDao().count(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Integer count(String eq) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setColumns("count(\"1\")")
				.setEqSql(eq);
		try {
			return getDao().count(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Integer count(Map<String, Object> eq) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setColumns("count(\"1\")")
				.eq(eq);
		try {
			return getDao().count(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Integer count(T t) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setColumns("count(\"1\")")
				.setTarget(t);
		try {
			return getDao().count(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<T> getByASC(T t, String column) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setTarget(t)
				.setOrderByASC(column); 
		try {
			return (List<T>) getDao().get(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<T> getByASC(String eq, String column) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setEqSql(eq)
				.setOrderByASC(column); 
		try {
			return (List<T>) getDao().get(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<T> getByASC(Map<String, Object> eq, String column) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.eq(eq)
				.setOrderByASC(column); 
		try {
			return (List<T>) getDao().get(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<T> getByASC(String column) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this) 
				.setOrderByASC(column); 
		try {
			return (List<T>) getDao().get(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<T> getByDESC(T t, String column) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setTarget(t)
				.setOrderByDESC(column); 
		try {
			return (List<T>) getDao().get(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<T> getByDESC(String eq, String column) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.setEqSql(eq)
				.setOrderByDESC(column); 
		try {
			return (List<T>) getDao().get(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<T> getByDESC(Map<String, Object> eq, String column) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this)
				.eq(eq)
				.setOrderByDESC(column); 
		try {
			return (List<T>) getDao().get(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<T> getByDESC(String column) {
		EqAdapter sql = new SelectAdapter()
				.setParame(this) 
				.setOrderByDESC(column); 
		try {
			return (List<T>) getDao().get(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Map<String, Object>> getOfManyTable(String colStatement, String tableStatement, String eqStatement){
		EqAdapter eq1 = new SelectAdapter()
				.setColumns(colStatement)
				.setTableStatement(tableStatement)
				.setEqSql(eqStatement);
		try {
			return getDao().getOfManyTable(eq1);
		}catch(RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<Map<String, Object>> getOfManyTable(ManyTable t) {
		EqAdapter eq1 = new SelectAdapter()
				.setColumns(t.getStatement()[0])
				.setTableStatement(t.getStatement()[1])
				.setEqSql(t.getStatement()[2]);
		try {
			return getDao().getOfManyTable(eq1);
		}catch(RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<Map<String, Object>> getOfManyTableByLimit(ManyTable t, int start, int size) {
		EqAdapter eq1 = new SelectAdapter()
				.setColumns(t.getStatement()[0])
				.setTableStatement(t.getStatement()[1])
				.setEqSql(t.getStatement()[2])
				.setLimit(start, size);
		try {
			return getDao().getOfManyTable(eq1);
		}catch(RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<Map<String, Object>> getOfManyTableByASC(ManyTable t, String col) {
		EqAdapter eq1 = new SelectAdapter()
				.setColumns(t.getStatement()[0])
				.setTableStatement(t.getStatement()[1])
				.setEqSql(t.getStatement()[2])
				.setOrderByASC(col);
		try {
			return getDao().getOfManyTable(eq1);
		}catch(RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<Map<String, Object>> getOfManyTableByDESC(ManyTable t, String col) {
		EqAdapter eq1 = new SelectAdapter()
				.setColumns(t.getStatement()[0])
				.setTableStatement(t.getStatement()[1])
				.setEqSql(t.getStatement()[2])
				.setOrderByDESC(col);
		try {
			return getDao().getOfManyTable(eq1);
		}catch(RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}
}
