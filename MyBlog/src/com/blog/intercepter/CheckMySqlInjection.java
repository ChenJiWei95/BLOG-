package com.blog.intercepter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.blog.anno.TParamer;


/**
 * <b></b>
 * <p>
 * 描述:<br>
 * 已经记录在java基础中
 * @author 威 
 * <br>2020年6月7日 下午3:45:01 
 * @see
 * @since 1.0
 */
//@Aspect
//@Component
public class CheckMySqlInjection {
	private static Logger log = Logger.getLogger(CheckMySqlInjection.class);
	
	// 上一次要监视那个登录的对象检测是否有注入，其实这个监听的目标（com.blog.service.impl.*.*(..))）对象错了 接下来注释掉
//	@Pointcut("execution(* com.blog.service.impl.*.*(..))")
  	public void performance(){
		log.info("performance....");
		System.out.println("performance....");
	}
	
//	@Before("performance()")
  	public void watchPerformance(JoinPoint joinPoint) throws Throwable{
		log.info("Mysql check");
		try {
			
			Class<?>[] clazz = new Class<?>[joinPoint.getArgs().length];
	    	for(int i = 0; i < joinPoint.getArgs().length; i++)// 没有传key 那么就用参数拼接 中间无任何分隔符
	    	{
	    		clazz[i] = joinPoint.getArgs()[i].getClass(); // 拼接所注解方法的传入参数类型
	    	}
	    	
			Method method = joinPoint.getTarget().getClass().getMethod(joinPoint.getSignature().getName(), 
	    			clazz);
			
			Annotation[] annotations = method.getAnnotations();
	    	for (int i = 0; i < annotations.length; i++) {
				if (annotations[i] instanceof TParamer) {
					log.info("========================");
					log.info(joinPoint.getArgs()[i]);
				}
	    	}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
