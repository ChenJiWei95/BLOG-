package com.blog.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.blog.entity.TRole;

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
public interface TRoleDao {
	@Select("SELECT * FROM trole WHERE `id` = #{id}")
	TRole getById(@Param("id") String id);
	
}
