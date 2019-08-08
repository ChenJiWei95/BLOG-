package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.RelateDao;
import com.blog.dao.Target_Dao;
import com.blog.dao.UserDao;
import com.blog.entity.EqAdapter;
import com.blog.entity.Target_;
import com.blog.entity.Target__;
import com.blog.service.UserService;

@Service("userServiceImpl")
@Transactional
public class UserServiceImpl extends BasiServiceImpl implements UserService  {
	@Resource
	UserDao userDao;
	@Resource
	RelateDao relateDao;
	@Resource
	Target_Dao target_Dao;
	
	public void test() {
		try {
			EqAdapter eq = new EqAdapter();
//			eq.setTable("user");
//			eq.eq("id", "1");
//			eq.set("password", "111112");
//			userDao.updateTest(eq);
			
//			EqAdapter eq = new EqAdapter();
//			eq.setTable("user");
//			eq.put("id", 5);
//			eq.put("username", "test");
//			eq.put("password", "666666");
//			userDao.insertTest(eq);
			
//			EqAdapter eq = new EqAdapter();
//			eq.setTable("user");
//			eq.setOrderByASC("id");
//			eq.setOrderByDESC("id");
//			eq.setLimit(0);
//			ArrayList<User> user = (ArrayList<User>) userDao.getTest(eq);
//			System.out.println("username:" + user.toString());
			
//			List<Relate> relate = relateDao.relateTest();
//			System.out.println(relate);
//			System.out.println(relate.get(0).getTarget_().get(0).getName_Target_());
//			System.out.println(relate.get(0).getTarget__().get(0).getName_Target__());
			
			
//			for (User u : userDao.gets())
//				System.out.println(u.toString());
			eq.eq("id_Target_", "1");
			eq.setId("id_Target_");
			eq.setTable(Target_.TABLE_FIELD);
			eq.setBrige_table(Target_.BRIGE_TABLE_FIELD);
			eq.setBrige_key(Target_.BRIGE_KEY_FIELD);
			eq.setBrige_association_key(Target_.BRIGE_ASSOCIATION_KEY_FIELD);
			eq.setAssociation_table(Target_.ASSOCIATION_TABLE_FIELD);
			eq.setAssociation_table_id(Target_.ASSOCIATION_TABLE_ID_FIELD);
			for (Target__ t : target_Dao.associate(eq))
				System.out.println(t.getName_Target__());
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

