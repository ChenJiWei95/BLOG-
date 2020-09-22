package com.blog.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * <b>redis 缓存</b>
 * <p>
 * 描述:<br>
 * 
 * @author 威 
 * <br>2019年11月2日 下午8:54:29 
 * @see
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RResult {
	/** 指定缓存的对象 */
	String value(); 
}
