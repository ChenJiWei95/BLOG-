package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.PlanBaseDao;
import com.blog.dao.BaseDao;
import com.blog.entity.PlanBase;
import com.blog.service.PlanBaseService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class PlanBaseServiceImpl extends BasiServiceImpl<PlanBase, Object> implements PlanBaseService, AssociaInterface  {
	@Resource
	PlanBaseDao planBaseDao; 
	
	public BaseDao<PlanBase> getDao(){
		return planBaseDao;
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
		return "plan_base";
	}
}

