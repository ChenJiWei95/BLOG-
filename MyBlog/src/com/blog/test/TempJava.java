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
	public static void main(String[] args) throws IOException {
		do7("id name value code type desc create_time update_time".split(" "), "ID 名称 值 代码 类型 描述 创建时间 修改时间".split(" "), "cjw2", "测试2");
//		doHTML("id name value code type desc create_time update_time".split(" "), "ID 名称 值 代码 类型 描述 创建时间 修改时间".split(" "));
//		doJSTableHead("id name value code type desc create_time update_time".split(" "), "ID 名称 值 代码 类型 描述 创建时间 修改时间".split(" "));
//		doJSEdit("id name value code type desc create_time update_time".split(" "));
		// 只操作字符串类型的数据
		// 类名称-小写	 	表名称 	实体类字段 
//		do1("data", "data", "data", new String[]{"id", "name", "value", "code", "type", "desc", "create_time", "update_time"});
//		delete("tempText");
		
//		do6("TempText", "tempText", "temp"); // 生成控制类
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
	
	private static void delete2(String name) {
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
	private static String upFirst(String str) {
		return str.substring(0,1).toUpperCase() + str.substring(1);
	}
	
	// 生成js代码
	public static void do1_1(String name_, String table, String classify,  String fileds, String Texts){
		//args 根据字段生成表头js文
		doJSTableHead(fileds.split(" "), Texts.split(" "));
		do1(name_, table, classify, fileds.split(" "));
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
	
	// 生成前端模板文件
	public static void do7 (String[] args, String[] texts, String classify, String title) throws IOException{
		
		String dir = webContentPath("views/admin/"+classify+"/save_or_update.jsp");
		dir = dir.substring(0, dir.lastIndexOf("/"));
		File file = null;
		if(!(file = new File(dir)).isDirectory()){
			System.out.println("创建文件夹【"+file.getPath()+"】");
			file.mkdir();
		}
		
		CharStreamImpl c = new CharStreamImpl(srcPath("config/temp/save_or_update.txt"));
		
		CharStreamImpl copy = new CharStreamImpl(webContentPath("views/admin/"+classify+"/save_or_update.jsp"));
		c.read(line->{
			String str = (String) line;
			copy.write(str
					.replaceAll("#form-item#", doHTML(args, texts))
					.replaceAll("#title#", title)
					.replaceAll("#classify#", classify)
					.replaceAll("#js-edit#", doJSEdit(args))
					.replaceAll("#table-head#", doJSTableHead(args, texts))
					.replaceAll("#classify#", classify), true);
		});
		copy.close();
		System.out.println("完成文件【"+webContentPath("views/admin/"+classify+"/save_or_update.jsp")+"】的生成");
		
		c = new CharStreamImpl(srcPath("config/temp/list.txt"));
		CharStreamImpl copy_ = new CharStreamImpl(webContentPath("views/admin/"+classify+"/list.jsp"));
		c.read(line->{
			String str = (String) line;
			copy_.write(str
					.replaceAll("#form-item#", doHTML(args, texts))
					.replaceAll("#title#", title)
					.replaceAll("#classify#", classify)
					.replaceAll("#js-edit#", doJSEdit(args))
					.replaceAll("#table-head#", doJSTableHead(args, texts))
					.replaceAll("#classify#", classify), true);
		});
		copy_.close();
		System.out.println("完成文件【"+webContentPath("views/admin/"+classify+"/list.jsp")+"】的生成");
	}
	private static String doHTML(String[] args, String[] texts){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < args.length; i++){
			if("id".equals(args[i])){
				sb.append("<div class=\"layui-hide\">"+"\n"+
						"\t<label class=\"layui-form-label\">ID</label>"+"\n"+
						"\t<div class=\"layui-input-inline\">"+"\n"+
						"\t\t<input type=\"text\" name=\"id\" disabled autocomplete=\"off\" class=\"layui-input layui-disabled\">"+"\n"+
						"\t</div>"+"\n"+
						"</div>"+"\n");
			}else if("desc".equals(args[i])){
				sb.append("<div class=\"layui-form-item\">"+"\n"+
						"\t<label class=\"layui-form-label\">"+texts[i]+"</label>"+"\n"+
		      			"\t<div class=\"layui-input-inline\">"+"\n"+
		        		"\t\t<textarea class=\"layui-textarea\" name=\""+args[i]+"\" placeholder=\"请输入描述信息\"></textarea>"+"\n"+
		        		"\t</div>"+"\n"+
						"</div>"+"\n");
			}else {
				sb.append("<div class=\"layui-form-item\">"+"\n"+
						"\t<label class=\"layui-form-label\">"+texts[i]+"</label>"+"\n"+
		      			"\t<div class=\"layui-input-inline\">"+"\n"+
		        		"\t\t<input type=\"text\" name=\""+args[i]+"\" placeholder=\"请输入"+texts[i]+"\" autocomplete=\"off\" class=\"layui-input\">"+"\n"+
		        		"\t</div>"+"\n"+
						"</div>"+"\n");
			}
		}
//		System.out.println(sb.toString());
		return sb.toString();
	}
	private static String doJSEdit(String[] args){
		StringBuilder sb = new StringBuilder();
		for(String arg : args){
			sb.append(",iframe.find('"+("desc".equals(arg)?"textarea" : "input")+"[name=\""+arg+"\"]')[0].value = data[0]."+arg);
			sb.append("\n");
		}
		return sb.deleteCharAt(0).toString();
	}
	private static String doJSTableHead(String[] args, String[] texts){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < args.length; i++){
			sb.append(",{field:'"+args[i]+"', title:'"+texts[i]+"'}");
			sb.append("\n");
		}
		return sb.toString();
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
	public static String webContentPath(String path){
		//C:/Users/Administrator.USER-20160224QQ/git/repository6/MyBlog/WebContent/
		return TempJava.class.getResource("/").getPath().substring(1).replace("build/classes", "WebContent")+path;
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
