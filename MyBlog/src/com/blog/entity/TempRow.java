package com.blog.entity;

/**
 * 模板
 * @author cjw
 *
 */
public class TempRow extends Base {
	private String id;
	private String field;
	private String title;
	private Integer width;
	private boolean sort;
	private String align;
	private String templet;
	private String desc;
	private String c_id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public boolean isSort() {
		return sort;
	}
	public void setSort(boolean sort) {
		this.sort = sort;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public String getTemplet() {
		return templet;
	}
	public void setTemplet(String templet) {
		this.templet = templet;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getC_id() {
		return c_id;
	}
	public void setC_id(String c_id) {
		this.c_id = c_id;
	}
}
