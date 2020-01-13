package com.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.PlanDoDao;
import com.blog.dao.BaseDao;
import com.blog.entity.PlanDo;
import com.blog.service.PlanDoService;
import com.blog.util.sql.AssociaInterface;

@Service
@Transactional
public class PlanDoServiceImpl extends BasiServiceImpl<PlanDo, Object> implements PlanDoService, AssociaInterface  {
	@Resource
	PlanDoDao planDoDao; 
	
	public BaseDao<PlanDo> getDao(){
		return planDoDao;
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
		return "plan_do";
	}
}

