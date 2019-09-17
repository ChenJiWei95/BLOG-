package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.BaseDao;
import com.blog.dao.MenuDao;
import com.blog.entity.Clazz;
import com.blog.entity.Menu;
import com.blog.service.MenuService;
import com.blog.util.sql.AssociaInterface;

@Service()
@Transactional
public class MenuServiceImpl extends BasiServiceImpl<Menu, Clazz> implements MenuService<Menu, Clazz>, AssociaInterface  {
	@Resource
	MenuDao menuDao; 
	
	public BaseDao<Menu> getDao(){
		return menuDao;
	}
	
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
			EqAdapter eq = new SelectAdapter()
					.setParame(this)
					.setOrderByASC("id")
					.setLimit(0)
					.setTarget(u);
			ArrayList<User> user = (ArrayList<User>) userDao.getTest(eq);
			System.out.println("username:" + user.toString());*/
			
			// 删除
			/*User u = new User();
			u.setId(6);
			EqAdapter eq = new SelectAdapter()
					.setParame(this)
					.setTarget(u);
			userDao.deleteTest(eq);*/
			
//			List<Relate> relate = relateDao.relateTest();
//			System.out.println(relate);
//			System.out.println(relate.get(0).getTarget_().get(0).getName_Target_());
//			System.out.println(relate.get(0).getTarget__().get(0).getName_Target__());
			
			// 查找关联对象集合 根据只中间表去关联
//			for (User u : userDao.gets())
//				System.out.println(u.toString());、
			/*Target_ u = new Target_();
			u.setId_Target_("1");
			EqAdapter eq = new SelectAdapter()
			.setParame(this)
			.setTable("target_")
			.setTarget(u);
//			.setTable(Target_.TABLE)
//			.eq("id_Target_", "1")
//			.setId("id_Target_")
//			.setBrige_table(Target_.BRIGE_TABLE)
//			.setBrige_key(Target_.BRIGE_KEY)
//			.setBrige_association_key(Target_.BRIGE_ASSOCIATION_KEY)
//			.setAssociation_table(Target_.ASSOCIATION_TABLE)
//			.setAssociation_table_id(Target_.ASSOCIATION_TABLE_ID)
			for (Target__ t : target_Dao.associate(eq))
				System.out.println(t.getName_Target__());*/
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
		return "";
	}
	@Override
	public String getBrige_key() {
		return "";
	}
	@Override
	public String getBrige_association_key() {
		return "";
	}
	@Override
	public String getAssociation_table() {
		return "";
	}
	@Override
	public String getAssociation_table_id() {
		return "id";
	}
	@Override
	public String getId() {
		return "id";
	}
	@Override
	public String getTable() {
		return "menu";
	}
}

