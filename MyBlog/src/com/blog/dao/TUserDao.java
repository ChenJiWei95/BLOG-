package com.blog.dao;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.blog.entity.TUser;
/** 
 * 描述:<br>
 * 测试mybatis以注解的方式获取多个表的数据
 * @author 威 
 * <br>2020年5月31日 下午2:19:07 
 * @see com.blog.dao.TUserDao
 * @see com.blog.dao.TRoleDao
 * @see com.blog.entity.TUser
 * @see com.blog.entity.TRole
 * @since 1.0
 */
public interface TUserDao {
	@Select("SELECT * FROM tuser WHERE `id` = #{id}")
	TUser getById(@Param("id") String id);
	
	@Select("SELECT * FROM tuser WHERE `id` = #{user.id}")
	TUser get(@Param("user") TUser user);
	
	@Select("SELECT * FROM tuser WHERE `id` = #{id}")
	@Results({
        @Result(id=true,column="id",property="id"),
        @Result(column="username"),
        @Result(column="role_id",property="role",one=@One(select="com.blog.dao.TRoleDao.getById"))
    })
	TUser getUserAndRole(@Param("id") String id);
}
