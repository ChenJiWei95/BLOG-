package com.blog.test;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.event.ListSelectionEvent;

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
	public static void main(String[] args) throws InstantiationException, IllegalAccessException{
	/*	log.info("hello word");
		log.error("hello word");
		log.fatal("hello word");
		log.debug("hello word");
		log.info("hello word");
		log.trace("我是trace");
 
		log.trace("退出程序.");*/
		Class<?> clazz = PrivateClass.class;// 获取PrivateClass整个类
		PrivateClass pc = (PrivateClass) clazz.newInstance();// 创建一个实例
		/*pc.setName("cjw");
		pc.setAge("24");
		pc.setLarge("lagere");*/

		/*Field[] fs = clazz.getDeclaredFields();// 获取PrivateClass所有属性
		for (int i = 0; i < fs.length; i++) {
			fs[i].setAccessible(true);// 将目标属性设置为可以访问
			List<String> fields = new ArrayList<>(fs.length);
			if(fs[i].get(pc) != null) {
				fields.add(fs[i].getName());
				
			} 
			switch(fs[i].getModifiers()) {
				case 2 : 	System.out.println("private " + 2); 		break;
				case 1 : 	System.out.println("public " + 1); 			break;
				case 9 : 	System.out.println("static " + 9); 			break;
				case 17 : 	System.out.println("public final " + 17); 	break;
				case 18 : 	System.out.println("private final " + 18); 	break;
			}
//			System.out.println(fields.toArray().length);
			fs[i].set(pc, "null");//将属性值重新赋值
			System.out.println("赋值后：" + fs[i].getName() + ":" + fs[i].get(pc));
		} */
		String endDate, startDate;
		SimpleDateFormat formatter;
		Date date1 = new Date();
        //获取String类型的时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date1);
    	date1 = calendar.getTime(); //这个时间就是日期往后推一天的结果 
        formatter = new SimpleDateFormat("yyyyMMdd");
        endDate = formatter.format(date1);
		/*for (int i = 0; i <10; i++){
			calendar.add(calendar.DATE,-i);//把日期往后增加一天.整数往后推,负数往前移动
		}	*/
		calendar.add(calendar.DATE,-10);
		date1 = calendar.getTime(); //这个时间就是日期往后推一天的结果 
        formatter = new SimpleDateFormat("yyyyMMdd");
        startDate = formatter.format(date1);//取十天
        System.out.println(startDate + " " + endDate);
	}
}
