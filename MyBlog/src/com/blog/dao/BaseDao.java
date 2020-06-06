package com.blog.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.blog.entity.Target__;
import com.blog.util.sql.EqAdapter;




/**
 * 
 * @className BaseDao
 * @Description
 * @author rjht
 * @contact qq 676342073
 * @date 2014-9-26 下午12:14:17
 */

@Mapper
public interface BaseDao<T> { 
	
	
	/**
	 * 删除
	 * @param eq
	 * @throws Exception
	 */
	void delete(EqAdapter eq) throws Exception;  
	
	T getOne(EqAdapter eq) throws Exception;
	/**
	 * 查询
	 * @param eq
	 * @return
	 * @throws Exception
	 */
	List<T> get(EqAdapter eq) throws Exception;
	
	/**
	 * '@Select({"select * from my_student where id = #{id}"})'
	 * <p>	 
	 * @param id
	 * @return
	 * @throws Exception
	 * T
	 * @see
	 * @since 1.0
	 */
//	abstract T getById(Integer id) throws Exception ;
	
	/**
	 * 查询
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	List<T> find(EqAdapter eq) throws Exception;
	
	/**
	 * 修改
	 * @param eq
	 * @throws Exception
	 */
	void update(EqAdapter eq) throws Exception;
	
	/**
	 * 插入
	 * @param eq
	 * @throws Exception
	 */
	void insert(EqAdapter eq) throws Exception;
	
	/**
	 * 查找关联对象集 通过中间表
	 * <p>	 
	 * @param eq
	 * @return
	 * List<Target__>
	 * @see
	 * @since 1.0
	 */
	List<Target__> associate(EqAdapter eq);
	
	Integer count(EqAdapter eq);
	
	List<Map<String, Object>> getOfManyTable(EqAdapter eq);
	
	/*@Select("${eq.sql}")
	@Results
	List<T> find_(@Param("eq") EqAdapter eq) throws Exception;*/
	
}
