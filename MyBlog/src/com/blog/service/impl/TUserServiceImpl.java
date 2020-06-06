package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.BaseDao;
import com.blog.dao.TUserDao;
import com.blog.dao.UserDao;
import com.blog.entity.Clazz;
import com.blog.entity.TUser;
import com.blog.entity.TempComponent;
import com.blog.entity.User;
import com.blog.service.UserService;
import com.blog.util.sql.AssociaInterface;

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
@Service
@Transactional
public class TUserServiceImpl{
	@Resource
	TUserDao tUserDao; 
	
	public TUserDao getDao(){
		return tUserDao;
	}
	
	public TUser getUserAndRole(String id){
		return tUserDao.getUserAndRole(id);
	}
}

