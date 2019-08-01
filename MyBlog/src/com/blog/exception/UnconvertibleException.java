package com.blog.exception;

/**
 * <b>一句话描述该类</b>
 * <p>
 * 描述:<br>
 * 
 * @author 威 
 * <br>2019年8月2日 上午12:36:32 
 * @see
 * @since 1.0
 */
public class UnconvertibleException extends Exception {

	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */ 
	private static final long serialVersionUID = 1L;
	
	public UnconvertibleException(Object type){
		super("不能转换该类型：【"+type+"】");
	}
}
