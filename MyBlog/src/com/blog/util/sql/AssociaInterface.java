package com.blog.util.sql;

/**
 * 关联查询接口
 * @author cjw
 *
 */
public interface AssociaInterface extends BaseInterface {
	/**
	 * 中间表名
	 * @return
	 */
	String getBrige_table();
	/**
	 * 中间表关联的主表键 	为空则没有
	 * @return
	 */
	String getBrige_key();
	/**
	 * 中间表关联的次表键	为空则没有
	 * @return
	 */
	String getBrige_association_key();
	/**
	 * 次表名
	 * @return
	 */
	String getAssociation_table();
	/**
	 * 次表ID
	 * @return
	 */
	String getAssociation_table_id();
	/**
	 * 主表主键名称
	 * @return
	 */
	String getId();
}
