package com.blog.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.blog.util.CharStreamImpl;
/**
 * 操作一下文件 增删
 * dao
 * service
 * entity
 * mapper
 * service
 * 配置文件
 * @author cjw
 */
public class TempJava {
	public static void main(String[] args) {
		// 只操作字符串类型的数据
		// 类名称-小写	 	表名称 	实体类字段
//		do1("tempText", "temp_text", new String[]{"name", "id"});
//		delete("tempText");
		
//		do6("TempText", "tempText", "temp");
	}
	
	public static void delete(String name_) {
		String name = upFirst(name_);
		String[] prefix = {"dao", "service", "entity", "mapper", "service/impl"};//前缀
		String[] suffix = {"Dao.java", "Service.java", ".java", "Mapper.xml", "ServiceImpl.java"};//后缀
		for(int i = 0; i < prefix.length; i++) {
			
			String copyPath = TempJava.class.getResource("/").getPath().substring(1).replace("build/classes", "src")+"com/blog/"+prefix[i]+"/"+name+suffix[i];
			File file = new File(copyPath);
			if(file.exists()) {
				file.delete();
				System.out.println("删除文件," + copyPath);
			}
			
		}
		delete2(name);
		
	}
	
	public static void delete2(String name) {
		// mybatis-config.xml 配置mapper项
		CharStreamImpl c = new CharStreamImpl(srcPath("config/mybatis-config.xml"));
		StringBuilder sb = new StringBuilder();
		String configFileName = name+"Mapper.xml";
		c.read(line -> {
			String str = (String) line;
			if(str.indexOf(configFileName) == -1) 
				sb.append(line).append(System.lineSeparator());
			else 
				System.out.println("删除mapper配置信息：找到删除项，"+str);	
		});
		
		c.write(sb.toString());
		c.close();
		System.out.println("删除mapper配置信息：已删除！");		
	}
	
	private static int num = 0;
	public static String upFirst(String str) {
		return str.substring(0,1).toUpperCase() + str.substring(1);
	}
	public static void do1(String name_, String table, String classify,  String[] args) {
		String name = name_.substring(0,1).toUpperCase() + name_.substring(1);
		
		// 生成实体类
		do3(name, args);
		
		String[] prefix = {"dao", "service"/*, "entity"*/, "mapper", "service/impl"};//前缀
		String[] temp = {"dao.txt", "service.txt"/*, "entity.txt"*/, "mapper.xml", "serviceImpl.txt"};
		String[] suffix = {"Dao.java", "Service.java"/*, ".java"*/, "Mapper.xml", "ServiceImpl.java"};//后缀
		for(int i = 0; i < prefix.length; i++) {
			
			String copyPath = TempJava.class.getResource("/").getPath().substring(1).replace("build/classes", "src")+"com/blog/"+prefix[i]+"/"+name+suffix[i];
			File file = new File(copyPath);
			if(file.exists()) file.delete();
			do2(TempJava.class.getResource("/").getPath().substring(1).replace("build/classes", "src")+"config/temp/"+temp[i], copyPath, name, name_, table);
		
		}
		
		// 配置文件
		do5(name);
		
		// 生成控制类
		do6(name, name_, classify);
	}
	public static void do6(String name, String name_, String classify) {
		
		CharStreamImpl c = new CharStreamImpl(srcPath("config/temp/TempControl.txt"));
		CharStreamImpl copy = new CharStreamImpl(srcPath("com/blog/control/admin/"+name+"Control.java"));
		c.read(line -> {
			String str = (String) line;
			copy.write(str
					.replaceAll("#name#", name)
					.replaceAll("#name_#", name_)
					.replaceAll("#classify#", classify), true);
		});
		
		copy.close();
		
		System.out.println("生成控制类："+srcPath("com/blog/control/admin/"+name+"Control.java"));
		
	}
	public static String srcPath(String path){
		return TempJava.class.getResource("/").getPath().substring(1).replace("build/classes", "src")+path;
	}
	public static void do5(String name) {
		// mybatis-config.xml 配置mapper项
		CharStreamImpl c = new CharStreamImpl(srcPath("config/mybatis-config.xml"));
		StringBuilder sb = new StringBuilder();
		c.read(line -> sb.append(line).append(System.lineSeparator()));
		
		String repStr = "<!--#MAPPER#-->";
		String configClassPath = "<mapper resource=\"com/blog/mapper/"+name+"Mapper.xml\" />";
		String sign = "\t\t<!--#MAPPER#-->";
		if(sb.toString().indexOf(configClassPath) == -1) {
			c.write(sb.toString().replaceAll(repStr, 
					configClassPath+System.lineSeparator()+sign));
			System.out.println("添加mapper配置信息：<mapper resource=\"com/blog/mapper/"+name+"Mapper.xml\" />");
		}else System.out.println("添加mapper配置信息：配置已存在！");
		c.close();
		
	}		
	public static void do3(String name, String[] args) {
		// name 作为实体类的名称 生成实体类 
				//	收集实体类属性 属性包含 修饰符，属性名，属性类型
				//	根据属性生成getter，setter方法
		CharStreamImpl c = new CharStreamImpl(srcPath("com/blog/entity/"+name+".java"));
		
		c.write("package com.blog.entity;");
		c.write("public class "+name+" extends Base{", true);
		
		for(String arg : args) {
			c.write("\tprivate String "+arg+";", true);
		}
		
		c.write(System.lineSeparator());
		
		for(String arg : args) {
			c.write("\tpublic String get"+upFirst(arg)+"() {", true);
			c.write("\t\treturn "+arg+";", true);
			c.write("\t}", true);
			c.write("\tpublic void set"+upFirst(arg)+"(String "+arg+") {", true);
			c.write("\t\tthis."+arg+" = "+arg+";", true);
			c.write("\t}", true);
		}
		
		c.write("}");
		c.close();
		
		System.out.println("生成实体类："+srcPath("com/blog/entity/"+name+".java"));
		
	}
	public static void do2(String path, String copyPath, String name, String name_, String table) {
		BufferedReader in = null;
		BufferedWriter bw = null;
		String line = null;
		try {
			in = new BufferedReader(new FileReader(path));
			bw = new BufferedWriter(new FileWriter(copyPath, true));
			bw.write(""); // 清空
			num = 0;
			while((line = in.readLine()) != null){
				bw.write(line.replaceAll("#name#", name).replaceAll("#name_#", name_).replaceAll("#table#", table));
				bw.newLine();
				num++;
			}
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			System.out.println("已完成文件【"+copyPath+"】的复制。总复制行数："+num);
			try {
				if(bw != null)
					bw.close();
				if(in != null)
					in.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
}
