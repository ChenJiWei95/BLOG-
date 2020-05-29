package com.blog;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.blog.dao.AdminDao;
import com.blog.entity.Admin;
import com.blog.util.sql.BaseInterface;
import com.blog.util.sql.EqAdapter;
import com.blog.util.sql.SelectAdapter;

public class FxSessionFactory implements BaseInterface{
	
	private static SqlSessionFactory sf ;
	static {
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		SqlSessionFactory sf = (SqlSessionFactory) context.getBean("dataSource"); 
	}
	public static SqlSession openSession(){
		return sf.openSession();
	}
	public static void main(String[] rags){
		try {
			new FxSessionFactory().test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void test() throws Exception{
		ApplicationContext context = 
				 new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		AdminDao dao = (AdminDao) openSession().getMapper(com.blog.entity.Admin.class);
		EqAdapter eq = new SelectAdapter()
				.setParame(this)
				.setLimit(0, 1)
				.setTarget(null);
		
		List<Admin> list = dao.get(eq);
		System.out.println(list.get(0).toString());
	}
	@Override
	public String getTable() {
		return "admin";
	}
}
