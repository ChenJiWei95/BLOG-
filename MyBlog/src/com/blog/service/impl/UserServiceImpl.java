package com.blog.service.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.UserDao;
import com.blog.entity.EqAdapter;
import com.blog.entity.User;
import com.blog.service.UserService;

@Service("userServiceImpl")
@Transactional
public class UserServiceImpl extends BasiServiceImpl implements UserService  {
	@Resource
	UserDao userDao;
	
	public void test() {
		try {
//			EqAdapter eq = new EqAdapter();
//			eq.setTable("user");
//			eq.eq("id", "1");
//			eq.set("password", "111112");
//			userDao.updateTest(eq);
			
			EqAdapter eq = new EqAdapter();
			eq.setTable("user");
			eq.put("id", 5);
			eq.put("username", "test");
			eq.put("password", "666666");
			userDao.insertTest(eq);
			
//			EqAdapter eq = new EqAdapter();
//			eq.setTable("user");
//			eq.setOrderByASC("id");
//			eq.setOrderByDESC("id");
//			eq.setLimit(0);
//			ArrayList<User> user = (ArrayList<User>) userDao.getTest(eq);
//			System.out.println("username:" + user.toString());
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
