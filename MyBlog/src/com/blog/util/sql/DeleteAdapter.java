package com.blog.util.sql;


public class DeleteAdapter extends EqAdapter {
	private String likeSql					; // 模糊查询
	// 条件语句
	public String getWhereSql() throws Exception {
		Object target = this.getTarget();
		if(target != null)  
			eq(parseMapOfObject(target));
		
		return super.getWhereSql();
	}	
	
	public String getLikeSql() {
		return likeSql;
	}
	public EqAdapter like(String cloumn, String likeStr) {
		this.likeSql = cloumnUtil(cloumn) + "LIKE" + likeStr;
		return this;
	}
	 	
}
