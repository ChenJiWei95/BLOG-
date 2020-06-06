package com.blog.util.sql;


public class DeleteAdapter extends EqAdapter {
	
	@Override
	public String getWhereSql() throws Exception {
		Object target = this.getTarget();
		if(target != null)  
			eq(parseMapByEntity(target));
		
		return super.getWhereSql();
	}	
	@Override
	public EqAdapter like(String cloumn, String likeStr) {
		setLike(cloumnUtil(cloumn)+" "+SQL_LIKE+quma2(likeStr));
		return this;
	}
	 	
}
