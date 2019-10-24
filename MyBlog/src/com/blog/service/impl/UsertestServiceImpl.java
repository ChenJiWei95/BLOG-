package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.UsertestDao;
import com.blog.dao.BaseDao;
import com.blog.entity.Usertest;
import com.blog.service.UsertestService;
import com.blog.util.sql.AssociaInterface;
import com.blog.util.sql.EqAdapter;
import com.blog.util.sql.InsertAdapter;

@Service
@Transactional
public class UsertestServiceImpl extends BasiServiceImpl<Usertest, Object> implements UsertestService, AssociaInterface  {
	@Resource
	UsertestDao usertestDao; 
	
	public BaseDao<Usertest> getDao(){
		return usertestDao;
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
		return "";
	}
	@Override
	public String getId() {
		return "id";
	}
	@Override
	public String getTable() {
		return "usertest";
	}

	@Override
	@Cacheable(value="users", key="#u.id")
	public Usertest save(Usertest u) {
		EqAdapter sql = new InsertAdapter()
				.setParame(this)
				.setTarget(u); 
		try {
			getDao().insert(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}
}

