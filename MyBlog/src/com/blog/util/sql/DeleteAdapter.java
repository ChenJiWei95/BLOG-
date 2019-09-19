package com.blog.util.sql;


public class DeleteAdapter extends EqAdapter {
	
	// 条件语句
	public String getWhereSql() throws Exception {
		Object target = this.getTarget();
		if(target != null)  
			eq(parseMapOfObject(target));
		
		return super.getWhereSql();
	}	
	 
	public EqAdapter like(String cloumn, String likeStr) {
		setLike(cloumnUtil(cloumn) + " LIKE '" + likeStr + "'");
		return this;
	}
	 	
}
