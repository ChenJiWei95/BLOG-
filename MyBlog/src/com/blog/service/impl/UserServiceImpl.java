package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.UserDao;
import com.blog.entity.User;
import com.blog.service.UserService;

@Service("userServiceImpl")
@Transactional
public class UserServiceImpl extends BasiServiceImpl implements UserService  {
	@Resource
	UserDao userDao;
	
	public void test() {
		try {
			User user = userDao.getById(1);
			System.out.println("username:" + user.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
