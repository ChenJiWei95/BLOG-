package com.blog.entity;
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
public class TRole {
	private String id;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
