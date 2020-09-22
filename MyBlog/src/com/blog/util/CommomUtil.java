package com.blog.util;

import com.blog.test.TempJava;
// *******************************
// **********废弃，不要再使用**********
// *******************************
public class CommomUtil {
	public static String srcPath(String path){
		return TempJava.class.getResource("/").getPath().substring(1).replace("build/classes", "src")+path;
	}
	public static String upFirst(String str) {
		return str.substring(0,1).toUpperCase() + str.substring(1);
	}
	public static String randomId (){
		return TimeUtil.getDatetime("yyyyMMddHHmmss")+String.valueOf(System.currentTimeMillis()).substring(11);
	}
}
