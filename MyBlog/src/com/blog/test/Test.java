package com.blog.test;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger; 
/**
 * <b>测试log4j</b>
 * <p>
 * 描述:<br>
 * 
 * @author 威 
 * <br>2019年4月27日 上午1:09:41 
 * @see
 * @since 1.0
 */
public class Test {
	private static Logger log = LogManager.getLogger(Test.class);
	public static void main(String[] args){
		log.info("hello word");
		log.error("hello word");
		log.fatal("hello word");
		log.debug("hello word");
		log.info("hello word");
		log.trace("我是trace");
 
		log.trace("退出程序.");
	}
}
