package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.UserDao;
import com.blog.entity.Eq;
import com.blog.entity.User;
import com.blog.service.UserService;

@Service("userServiceImpl")
@Transactional
public class UserServiceImpl extends BasiServiceImpl implements UserService  {
	@Resource
	UserDao userDao;
	
	public void test() {
		try {
			Eq eq = new Eq();
			eq.put("id", 1);
			User user = userDao.getTest(eq);
//			User user = userDao.getTest2(1);
			System.out.println("username:" + user.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
//		Eq eq = new Eq();
//		eq.put("id", 1);
//		eq.put("username", "cjw");
//		System.out.println(eq.getContent());
	}
}

