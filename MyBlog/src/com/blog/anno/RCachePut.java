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
public @interface RCachePut {
	String key();
	/** 指定缓存值，不给则以运算后的返回值为缓存值；写法: #user/#user.id */
	String result() default "";
	/** 默认3600 */
	long time() default 3600;
}
