package com.blog.entity;

public class Target_ {
	public final static String TABLE_FIELD 					= "target_"		;
	public final static String BRIGE_TABLE_FIELD 			= "relate"		;
	public final static String BRIGE_KEY_FIELD 				= "target_"		;
	public final static String BRIGE_ASSOCIATION_KEY_FIELD 	= "target__"	;
	public final static String ASSOCIATION_TABLE_FIELD		= "target__"	;
	public final static String ASSOCIATION_TABLE_ID_FIELD 	= "id_Target__"	;
	
	public static void main(String[] args){
		String[] arr = {
				"table_field"
				, "brige_table_field"
				, "brige_key_field"
				, "brige_association_key_field"
				, "association_table_field"
				, "association_table_id_field"
				};
		for (String field : arr){
			System.out.println(field.toUpperCase());
		}
	}
	
	private String id_Target_;
	private String name_Target_;
	public String getId_Target_() {
		return id_Target_;
	}
	public void setId_Target_(String id_Target_) {
		this.id_Target_ = id_Target_;
	}
	public String getName_Target_() {
		return name_Target_;
	}
	public void setName_Target_(String name_Target_) {
		this.name_Target_ = name_Target_;
	}
	
}
