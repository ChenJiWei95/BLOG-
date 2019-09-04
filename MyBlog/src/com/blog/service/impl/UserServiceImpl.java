package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.RelateDao;
import com.blog.dao.Target_Dao;
import com.blog.dao.UserDao;
import com.blog.entity.Target_;
import com.blog.entity.Target__;
import com.blog.service.UserService;
import com.blog.util.sql.AssociaInterface;
import com.blog.util.sql.EqAdapter;
import com.blog.util.sql.SelectAdapter;

@Service("userServiceImpl")
@Transactional
public class UserServiceImpl extends BasiServiceImpl implements UserService, AssociaInterface  {
	@Resource
	UserDao userDao;
	@Resource
	RelateDao relateDao;
	@Resource
	Target_Dao target_Dao;
	
	public void test() {
		try {
			// 修改 
			// 可以先查询到数据然后再调用修改 ， 也可以根据条件直接修改
			/*User user = new User();
			user.setPassword("111111");
			EqAdapter sql = new UpdateAdapter().setTable("user").eq("id", "1").setTarget(user);
			userDao.updateTest(sql);*/
			
			// 插入 
			/*User user = new User();
			user.setId(6);
			user.setUsername("test6");
			user.setPassword("123456");
			EqAdapter sql = new InsertAdapter().setTable("user").setTarget(user); 
			userDao.insertTest(sql);*/
			
			// 查询
			/*User u = new User();
			u.setId(1);
			EqAdapter eq = new SelectAdapter().setTable("user").setOrderByASC("id").setLimit(0).setTarget(u);
			ArrayList<User> user = (ArrayList<User>) userDao.getTest(eq);
			System.out.println("username:" + user.toString());*/
			
//			List<Relate> relate = relateDao.relateTest();
//			System.out.println(relate);
//			System.out.println(relate.get(0).getTarget_().get(0).getName_Target_());
//			System.out.println(relate.get(0).getTarget__().get(0).getName_Target__());
			
			// 查找关联对象集合 根据只中间表去关联
//			for (User u : userDao.gets())
//				System.out.println(u.toString());
			EqAdapter eq = new SelectAdapter()
			.setTable(Target_.TABLE)
			.eq("id_Target_", "1")
			/*.setId("id_Target_")
			.setBrige_table(Target_.BRIGE_TABLE)
			.setBrige_key(Target_.BRIGE_KEY)
			.setBrige_association_key(Target_.BRIGE_ASSOCIATION_KEY)
			.setAssociation_table(Target_.ASSOCIATION_TABLE)
			.setAssociation_table_id(Target_.ASSOCIATION_TABLE_ID)*/
			.setAssociaInterface(this);
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
	@Override
	public String getBrige_table() {
		return Target_.BRIGE_TABLE;
	}
	@Override
	public String getBrige_key() {
		return Target_.BRIGE_KEY;
	}
	@Override
	public String getBrige_association_key() {
		return Target_.BRIGE_ASSOCIATION_KEY;
	}
	@Override
	public String getAssociation_table() {
		return Target_.ASSOCIATION_TABLE;
	}
	@Override
	public String getAssociation_table_id() {
		return Target_.ASSOCIATION_TABLE_ID;
	}
	@Override
	public String getId() {
		return "id_Target_";
	}
	@Override
	public String getTable() {
		return "";
	}
}

