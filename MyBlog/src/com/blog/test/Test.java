package com.blog.test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blog.anno.TParamer;
import com.blog.util.CharStreamImpl;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder; 
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
class TTTt{
	String str = new String("good");
	char[] ch = {'a', 'b', 'c'};
}
interface Subject{
	public void say();
	public void run();
} 
public class Test {
	private static Logger log = LogManager.getLogger(Test.class);
	private static int i = 100;
	public static void main(String[] args) throws InstantiationException, IllegalAccessException{
		/*
		生成mybatis的CRUD		test20_05_10_01
		创建表单项				test0320_1
		测试路径
		base64字符串转化成图片
		图片转化成base64字符串	GetImageStr
		其他					tested
		*/
//		test20_05_10_01(); // 生成mybatis的CRUD
		
//		test0320_1();	// 创建查询单项
		
//		test200531(); 	// 加载applicationcontext文件 测试项目mybatis注解
		
//		test200602_1();
		
//		test200603_1(new User(), "User");	// 生成toString
//		test200603_2("Role");				// 生成toString
//		test200603_3(new Note());			// 返回所有属性
//		test200603_4();						// 生成实体类 hibernate
//		test20_06_12_01();					//
//		test20_06_12_02();
//		test20_06_23_01();
//		System.out.println(Integer.valueOf(4));
//		test20062501(1);
//		test20062502(new Integer(1));
//		test20062701();
//		((Test)null).test20062702();
		
		 
//		test20062708();
		
//		System.out.println(test20062801("aaabbddfdbdsd"));
		
//		double d = 3f/2f;
//		int a = 3/2;
//		System.out.println(a);
//		System.out.println(d);
//		test20090401();
//		test20092101("C:\\Users\\Administrator.USER-20160224QQ\\.m2\\repository");
//		test20092101("C:\\Users\\Administrator.USER-20160224QQ\\Desktop\\ddd");
		test200926();
	}
	// jdk动态代理
	public static void test200926(){
		class SubjectProxy implements Subject{

			@Override
			public void say() {
				System.out.println("say");
			}

			@Override
			public void run() {
				System.out.println("run");
			}
			
		}
		SubjectProxy subject = new SubjectProxy(); 
		Subject sub = (Subject) Proxy.newProxyInstance(subject.getClass().getClassLoader(), 
				subject.getClass().getInterfaces(), 
				new InvocationHandler(){

					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						System.out.println("代理前置");
						Object result = method.invoke(subject, args);
						System.out.println("代理后置");
						return result;
					}
					
				}
		);
		sub.say();
		sub.run();
	}
	// maven导入项目时有感叹号异常，这是依赖包有问题，此方法时清空有问题的依赖包然后重新下载
	public static void test20092101(String path) {
		int fileNum = 0, folderNum = 0;
		File file = new File(path);
		Scanner sc = new Scanner(System.in);
	 	LinkedList<File> list = new LinkedList<>();
	 	if (file.exists()){
	 		if (null == file.listFiles()) {
                return;
            }
            list.addAll(Arrays.asList(file.listFiles()));
            while (!list.isEmpty()) {
            	File target = list.removeFirst();
                File[] files = target.listFiles();
                if (null == files) {
                    continue;
                }
                boolean isJar = false;
                boolean isLastUpdated = false;
                
                for (File f : files) {
                    if (f.isDirectory()) {
                        System.out.println("文件夹:" + f.getAbsolutePath());
                        list.add(f);
                        folderNum++;
                    } else {
                        
                        if(f.getAbsolutePath().endsWith(".jar.lastUpdated")){
                        	isLastUpdated = true;
//                        	System.out.println("以.jar.lastUpdated结尾:" + f.getAbsolutePath().endsWith(".jar.lastUpdated"));
                        } else if(f.getAbsolutePath().endsWith(".jar")){
                        	isJar = true;
//                        	System.out.println("以.jar结尾:" + f.getAbsolutePath().endsWith(".jar"));
                        }
                        fileNum++;
                    }
                }
//                System.out.println("isLastUpdated:"+isLastUpdated+"; isJar:"+isJar);
                if(isLastUpdated){
                	if(!isJar) {
                		System.out.println("删除文件夹："+target.getAbsolutePath()); 
                		System.out.println("回车进行确认删除！");
                		sc.nextLine();
//                		target.mkdir();
                		delFolder(target.getAbsolutePath());
                		System.out.println("已删除，确认继续！");
                		sc.nextLine();
                	} 
                }
            }
	 	}else {
            System.out.println("文件不存在!");
        }
	 	System.out.println("文件夹数量:" + folderNum + ",文件数量:" + fileNum);
	}  
	// 删除文件夹
	// param folderPath 文件夹完整绝对路径
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}
	// 遍历所有文件夹
	public static void folderMethod1(String path) {
		int fileNum = 0, folderNum = 0;
		File file = new File(path);
	 	LinkedList<File> list = new LinkedList<>();
	 	if (file.exists()){
	 		if (null == file.listFiles()) {
                return;
            }
            list.addAll(Arrays.asList(file.listFiles()));
            while (!list.isEmpty()) {
                File[] files = list.removeFirst().listFiles();
                if (null == files) {
                    continue;
                }
                for (File f : files) {
                    if (f.isDirectory()) {
                        System.out.println("文件夹:" + f.getAbsolutePath());
                        list.add(f);
                        folderNum++;
                    } else {
                        System.out.println("文件:" + f.getAbsolutePath());
                        fileNum++;
                    }
                }
            }
	 	}else {
            System.out.println("文件不存在!");
        }
	 	System.out.println("文件夹数量:" + folderNum + ",文件数量:" + fileNum);
	}    
	
	// 将数行代码转换成StringBuilder.append("123456");串
	public static void test20090401(){
		CharStreamImpl c = new CharStreamImpl("C:\\Users\\Administrator.USER-20160224QQ\\Desktop\\temp.txt");
		c.read(line -> {
			System.out.println("html.append(\""+((String) line).replace("\"", "'")+"\");");
		});
	}
	public static void test20_06_12_01(){
		CharStreamImpl c = new CharStreamImpl("d:/temp.txt");
		System.out.println("StringBuilder sb = new StringBuilder();");
		c.read(line -> {
			String lineStr = (String) line;
			System.out.println("sb.append(\""+lineStr.replace("\"", "\\\"")+"\");");
		});
	}
	
	// 连续重复的字母用字数代替
	// 原始值：aaabbddfdbdsd， 结果：a3b2d2fdbdsd
	public static String test20062801(String str){
		// 有数字则返回原串
		if(str.matches(".*\\d+.*"))
			return str; 
		
		char c = str.charAt(0);
		int index = 1;
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i < str.length(); i++){
			if(str.charAt(i) == c) index++;
			else{
				sb.append(c+""+(index == 1 ? "" : index));
				index = 1;
				c = str.charAt(i);
			}
		}
		return sb.append(str.charAt(str.length()-1)).toString();
	}
	
	public static void test20062708(){
		long a[] = new long[5];
		System.out.println(a[4]);
	}
	public static void change(String str, char[] ch){
		str = "test ok";
		ch[0] = 'g';
	}
	
	public static void test20062705(){
		double a = 1;
		double b = 2;
		System.out.println(a+=b);
		System.out.println(a=a+b);
	}
	public static void test20062704(){
		int a = 1;
		int b = 2;
		System.out.println(a+=b);
		System.out.println(a=a+b);
	}
	/*public static void test20062703(){
		byte a = 1;
		byte b = 2;
		System.out.println(a+=b);
		System.out.println(a=a+b);
	}*/
	public static void test20062702(){
		System.out.println("test20062702_(4)");
//		System.out.println(test20062702_(4));
	}
	public static int test20062702_(int n){
		List<Integer> list = new ArrayList<Integer>();
		for(int i = 0; i < n; i++){
			list.add(i+1);
		}
		int len = list.size(), index = -1;
		while(true){
			for(int i = 0; i < 3; i++){
				if(index < len-1) index++;
				else index = 0;
				System.out.println("index:"+index);
			}
			list.remove(index);
			index--;
			if((len = list.size()) == 1){
				return list.get(0);
			}
		} 
	}
	public static void test20062701(){
		Scanner sc = new Scanner(System.in);
		String str = sc.next();
		System.out.println(result(str));
	}
	public static String result(String str) {
		int index = 0;
		StringBuilder sb = new StringBuilder();
		for(int i = 1, len = str.length(); i <= len; i++){
			String result = mapping(str.substring(index, i));
			if(result != null){
				index = i;
				sb.append(result);
			}
		}
		return sb.toString();
	}
	public static String mapping(String str){
		switch(str){
			case "nine" : return "9";
			case "eight" : return "8";
			case "seven" : return "7";
			case "six" : return "6";
			case "five" : return "5";
			case "four" : return "4";
			case "three" : return "3";
			case "two" : return "2";
			case "one" : return "1";
			case "zero" : return "0";
		}
		return null;
	}
	
	public static void test20062501(int value){
		System.out.println("int");
	}
	public static void test20062502(Integer value){
		System.out.println("Integer");
	}
	
	static long count = 0;
	static boolean flag = false;
	static boolean flag2 = false;
	static boolean flag3 = false;
	static StringBuilder sb = new StringBuilder();
	public static void test20_06_23_01(){
		CharStreamImpl impl = new CharStreamImpl("E:\\学习\\工作项目\\AUTHSYSTEST - 副本.sql");
		CharStreamImpl write = new CharStreamImpl("E:\\学习\\工作项目\\2.txt");
		
		impl.read(line->{
//			count++;
			String l = (String) line;
			if(l.indexOf("ALERT TABLE") != -1 ) flag = true;
			if(flag){
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				/*if(l.indexOf("CREATE TABLE") != -1){
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(l);
//					flag2 = true;
				}*/
				/*if(l.indexOf("CHARSET=utf8;") != -1 && flag2){
					flag3 = true;
				}
				if(flag2){
					sb.append(l).append(System.lineSeparator());
				}
				if(flag3){
					flag2 = false;
					flag3 = false;
					
					System.out.println(sb);
					sb = new StringBuilder();
				}*/
				System.out.println(l);
			}
			
			/*if(l.indexOf(", 'SYYYY-MM-DD HH24:MI:SS'") != -1){
				write.write(l.replace(", 'SYYYY-MM-DD HH24:MI:SS'", ""), true);
			}else {
				write.write(l, true);
			}*/
		});
		System.out.println("处理完毕");
	}
	
	/** 当前页 */
	private static Integer currentIndex;
	/** 总页数 */
	private static Integer pageCount;
	// 
	public static void test20_06_12_02(){
		currentIndex = 5;
		pageCount = 10;
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"layui-box layui-laypage layui-laypage-default c-page-default\" id=\"layui-laypage-17\">");
		sb.append("  <a href=\"javascript:;\" class=\"layui-laypage-prev\" data-page=\"1\">");
		sb.append("    <i class=\"layui-icon\"></i></a>");
		String href = "";
		// 总是显示第一页按钮
		active(sb, href, 1);
		if(pageCount > 2){
			// 2 - 1 = 1 < 1, 3 - 2 = 1 < 1 
			// 特定条件下： 添加省略号
			if(currentIndex >= 4)
				sb.append("  <span class=\"layui-laypage-spr\">…</span>");
			if(currentIndex == 1){
				// 选中第一页，因为页数大于三才会进来这个if，所以直接创建第二页按钮
				active(sb, href, currentIndex+1);
				// 如果总页数不是3而已 就添加下一页按钮，然后就齐活了
				if(pageCount != 3)
					active(sb, href, currentIndex+2);
			}else if(currentIndex == pageCount){
				// 选中的是尾页 往后创建前一页按钮
				active(sb, href, pageCount-1);
				// 总数不是3，就创建更前一页按钮
				if(pageCount != 3)
					active(sb, href, pageCount-2);
			}else{
				// 其他选中，
				// 先判断其他选中的前一页不是首页则创建上一页按钮
				if(currentIndex-1 != 1)
					active(sb, href, currentIndex-1);
				// 选中页按钮，因为总数3才会进来，所以直接创建
				active(sb, href, currentIndex);
				// 最后判断选中的下一页不是尾页则创建下一页按钮
				if(currentIndex+1 != pageCount)
					active(sb, href, currentIndex+1);
			}
			// 特定条件下： 添加后方的省略号
			if(pageCount - currentIndex >= 3)
				sb.append("  <span class=\"layui-laypage-spr\">…</span>");
		}
		// 数量足够的情况 总是显示尾页按钮
		if(pageCount >= 2)
			active(sb, href, pageCount);
		
		sb.append("  <a href=\"javascript:;\" class=\"layui-laypage-next\" data-page=\"3\">");
		sb.append("    <i class=\"layui-icon\"></i>");
		sb.append("  </a>");
		sb.append("  <span class=\"layui-laypage-count\">共 94 条</span>");
		sb.append("</div>");
		System.out.println(sb.toString());
	}	
	
	protected static void active(StringBuilder sb, String href, Integer index) {
		if(currentIndex == index){
			sb.append("  <span class=\"layui-laypage-curr\">");
			sb.append("    <em class=\"layui-laypage-em c-page-em\"></em>");
			sb.append("    <em>"+index+"</em></span>");
		}else
			sb.append("  <a href=\""+href+"\" class=\"layui-laypage-last\" title=\"第"+index+"页\" data-page=\""+index+"\">"+index+"</a>");
	}
	
	// createEntityForHibernate
	protected static void test200603_4(){
		ContextConfig2 c = new ContextConfig2();
		c.setColumns("id name create_date update_date content admin_id status tags");
		c.setFileds("id name createDate updateDate content adminId status tags");
		c.setTypes("String String String String String String String String");
		c.setName_("note");
		c.setTable("note");
		createEntityForHibernate(c);
	}
	
	protected static Configure2 setParame(ContextConfig2 c) {
		
		Configure2 conf = new Configure2();
		conf.setFileds(c.getFileds());
		conf.setTitle(c.getTitle());
		conf.setColumns(c.getColumns());
		conf.setTypes(c.getTypes());
		conf.setTexts(c.getTexts());
		conf.setName_(c.getName_());
		conf.setName(upFirst(c.getName_()));
		conf.setTable(c.getTable());
		conf.setClassify(c.getClassify()); 
		return conf;
	}
	
	// 首字母大写
	private static String upFirst(String str) {
		return str.substring(0,1).toUpperCase() + str.substring(1);
	}
	
	
	public static void createEntityForHibernate(ContextConfig2 contextConfig2) {

		Configure2 conf = setParame(contextConfig2);
		
		StringBuilder mainCode = new StringBuilder();
		mainCode.append("package com.shop.entity;");
		
		mainCode.append("\n");
		mainCode.append("import javax.persistence.Column;"+"\n");
		mainCode.append("import javax.persistence.Entity;"+"\n");
		mainCode.append("import javax.persistence.Id;"+"\n");
		mainCode.append("import javax.persistence.Table;"+"\n");
		mainCode.append("\n");
		
		mainCode.append("@Entity"+"\n");
		mainCode.append("@Table(name = \""+ conf.getTable() +"\")"+"\n");
		mainCode.append("public class "+conf.getName()+" {"+"\n");
		
		String[] fileds = conf.getFileds();
		String[] columns = conf.getColumns();
		String[] types = conf.getTypes();
		boolean onceFlag = true;
		for(int i = 0, len = fileds.length; i < len; i++) {
			if(onceFlag) {mainCode.append("\t@Id"+"\n"); onceFlag = false;}
			else mainCode.append("\t@Column(name = \""+columns[i]+"\")"+"\n");
			mainCode.append("\tprivate "+types[i]+" "+fileds[i]+";"+"\n");
		}
		
		mainCode.append("\n");
		
		StringBuilder sb = new StringBuilder();
		sb.append("\tpublic String toString(){\n");
		sb.append("\t\treturn "+conf.getName()+".class + \"[\"\n");
		
		for(int i = 0, len = fileds.length; i < len; i++) {
			mainCode.append("\tpublic "+types[i]+" get"+upFirst(fileds[i])+"() {"+"\n");
			mainCode.append("\t\treturn "+fileds[i]+";"+"\n");
			mainCode.append("\t}"+"\n");
			mainCode.append("\tpublic void set"+upFirst(fileds[i])+"("+types[i]+" "+fileds[i]+") {"+"\n");
			mainCode.append("\t\tthis."+fileds[i]+" = "+fileds[i]+";"+"\n");
			mainCode.append("\t}"+"\n");
			
			sb.append("\t\t+ \" "+fileds[i]+" = \" + " + fileds[i]+" + \",\"\n");
		}
		
		sb.delete(sb.length()-6, sb.length()-1);
		sb.append( "\t\t+\"]\";\n");
		sb.append("\t}");
		
		mainCode.append(sb+"\n");
		mainCode.append("}"+"\n");
		System.out.println(mainCode.toString());
		
	}
	
	protected static void parsePropertyByEntity(Object target) throws InstantiationException, IllegalAccessException {
		Class<?> clazz = target.getClass();// 获取PrivateClass整个类
//		Object pc = clazz.newInstance();// 创建一个实例
		List<String> fields = null;
		List<String> types = null;
		Field[] fs = clazz.getDeclaredFields();// 获取PrivateClass所有属性
		fields = new ArrayList<>(fs.length);
		types = new ArrayList<>(fs.length);
		for (int i = 0; i < fs.length; i++) {
			fs[i].setAccessible(true);// 将目标属性设置为可以访问
			if(fs[i].getModifiers() == 2) {
				types.add(fs[i].getType().getSimpleName());
				fields.add(fs[i].getName());
			}
		} 
		for(Object item : types.toArray()){
			System.out.print(item+" ");
		}
		System.out.println();
		for(Object item : fields.toArray()){
			System.out.print(item+" ");
		}
	}
	
	/**
	 * 返回属性和对应类型的集合
	 * <p>	 
	 * @param target
	 * @return
	 * Map<String,String>
	 * @see
	 * @since 1.0
	 */
	protected static Map<String, String> parsePropertyAndTypeByEntity(Object target){
		Map<String, String> map = new HashMap<String, String>(); 
		Class<?> clazz = target.getClass();// 获取PrivateClass整个类
//		Object pc = clazz.newInstance();// 创建一个实例
		Field[] fs = clazz.getDeclaredFields();// 获取PrivateClass所有属性
		for (int i = 0; i < fs.length; i++) {
			fs[i].setAccessible(true);// 将目标属性设置为可以访问
			if(fs[i].getModifiers() == 2) {
				map.put(fs[i].getName(), fs[i].getType().getSimpleName());
			}
		} 
		return map;
	}
	
	public static void test200603_3(Object entity) throws InstantiationException, IllegalAccessException{
		parsePropertyByEntity(entity);
	}
	
	/**
	 * 返回 属性和值 的map集合
	 * <p>	 
	 * @param target
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * Map<String,Object>
	 * @see
	 * @since 1.0
	 */
	protected static Map<String, Object> parsePropertyAndValueByEntity(Object target) throws InstantiationException, IllegalAccessException {
		Class<?> clazz = target.getClass();// 获取PrivateClass整个类
//		Object pc = clazz.newInstance();// 创建一个实例
		
		Field[] fs = clazz.getDeclaredFields();// 获得某个类的所有声明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
		Field field;
		Map<String, Object> map = new HashMap<String, Object>(); 
		for (int i = 0; i < fs.length; i++) {
			(field = fs[i]).setAccessible(true);// 将目标属性设置为可以访问
			if(field.getModifiers() == 2) // PRIVATE: 2
				map.put(field.getName(), field.get(target));
		} 
		return map;
	}
	
	/**
	 * 
	 * <p>	 
	 * @param entity
	 * 			new User()
	 * @param entityName
	 * 			"User"
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * void
	 * @see
	 * @since 1.0
	 */
	public static void test200603_1(Object entity, String entityName) throws InstantiationException, IllegalAccessException{
		Map<String, Object> map = parsePropertyAndValueByEntity(entity);
		StringBuilder sb = new StringBuilder();
		sb.append("\tpublic String toString(){\n");
		sb.append("\t\treturn "+entityName+".class + \"[\"\n");
		for(Map.Entry<String, Object> item : map.entrySet())
			sb.append("\t\t+ \" "+item.getKey()+" = \" + " + item.getKey()+" + \",\"\n");
		sb.delete(sb.length()-6, sb.length()-1);
		sb.append( "\t\t+\"]\";\n");
		sb.append("\t}");
		System.out.println(sb);
	}
	
	/**
	 * 
	 * <p>	 
	 * @param entityName
	 * 			"User"
	 * void
	 * @see
	 * @since 1.0
	 */
	public static void test200603_2(String entityName){
		CharStreamImpl c = new CharStreamImpl("d:/temp.txt");
		StringBuilder sb = new StringBuilder();
		sb.append("\tpublic String toString(){\n");
		sb.append("\t\treturn "+entityName+".class + \"[\"\n");
		c.read(line -> {
			String lineStr = (String) line;
			String type = "", property = "";// 暂时没用到
			if(lineStr.trim().length()>0){
				
				int index = lineStr.indexOf(" ") + 1;
				int index2 = lineStr.indexOf(" ", index);
				type = lineStr.substring(index, index2);
				int index3 = lineStr.indexOf(";", index2);
				property = lineStr.substring(index2+1, index3);
				sb.append("\t\t+ \" "+property+" = \" + " + property+" + \",\"\n");
				
			}
		});
		c.close();
		
		sb.delete(sb.length()-6, sb.length()-1);
		sb.append( "\t\t+\"]\";\n");
		sb.append("\t}");
		System.out.println(sb);
		
	}
	
	@SuppressWarnings("unused")
	public static void test200602_1(){
		class TestClazz200602 {
			public void test(@TParamer int a){
				System.out.println(a);
			}
		}
		
		TestClazz200602 t = new TestClazz200602();
		
		Class<?> clazz = t.getClass();
		Method[] methods;
		Annotation[][] annoArrs;
		Parameter[] params;
		boolean isTarget = false;
		for(Method method : (methods = clazz.getMethods())){
			/*for(Parameter param : (params = method.getParameters())){
				for(Annotation[] annos : (annoArrs = param.getParameterAnnotations())) {
					for(Annotation anno : annos){
						if(anno instanceof TParamer) {
							System.out.println(anno.annotationType().getSimpleName());
							isTarget = true;
						}
					}
				}
				if(isTarget){
					method.
				}
			}*/
				
//				System.out.println(method.getParameters().getAnnotatedType().getSimpleName());
				/*for(Annotation anno : annos){
					if(anno instanceof TParamer) {
						System.out.println(anno.annotationType().getSimpleName());
						isTarget = true;
					}
				}*/
		}
	}
	
	public static void test200531(){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("classpath:config/applicationContext.xml");
		/*NoteService baseDao = (NoteService)context.getBean("noteService");
		try {
			System.out.println(baseDao.find_("select * from note"));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	/**
	 * 生成mybatis的CRUD
	 * 根据 private String username;
	 * 生成 <result column="username" property="username" />   
	 * 运用于bitas配置文件
	 * <p>	 
	 * void
	 * @see
	 * @since 1.0
	 */
	public static void test20_05_10_01(){
		CharStreamImpl c = new CharStreamImpl("d:/temp.txt");
		c.read(line -> {
			String lineStr = (String) line;
			String type = "", property = "";// 暂时没用到
			if(lineStr.trim().length()>0){
				
				int index = lineStr.indexOf(" ") + 1;
				int index2 = lineStr.indexOf(" ", index);
				type = lineStr.substring(index, index2);
				int index3 = lineStr.indexOf(";", index2);
				property = lineStr.substring(index2+1, index3);
//				System.out.println("type:"+type+", property:"+property);
				System.out.println("<result column=\""+property+"\" property=\""+property+"\" />  ");
			}
		});
	}
	
	/**
	 * 创建表单项
	 * <p>	 
	 * void
	 * @see
	 * @since 1.0
	 */
	public static void test0320_1(){
		/*<input 
		type="text" 
		name="Qu_a#createDate_ge_s" 
		placeholder="请选择开始时间" 
		id="startDate" 
		autocomplete="off" 
		style="width: 120px;" 
		class="layui-input">
		
		<input 
		type="text" 
		name="Qu_a#createDate_le_s" 
		placeholder="请选择结束时间" 
		id="endDate" 
		autocomplete="off" 
		style="width: 120px;" 
		class="layui-input">
		
		*/
		// 获取时间  type：datetime
		/*
		laydate = layui.laydate 
		laydate.render({
	        elem: '#startDate'
	        ,type: 'datetime'
	    });
	    laydate.render({
	        elem: '#endDate'
	        ,type: 'datetime'
	    });*/
		
//		[{text:, name:, tagName:select, selects:[value:, text:] }]
		JSONArray arr = new JSONArray();
		/*JSONObject input = new JSONObject();
		input.put("text", "ID");
		input.put("name", "id");
		arr.add(input);
		
		JSONObject inputStartDate = new JSONObject();
		inputStartDate.put("text", "选择时间");
		inputStartDate.put("tagName", "date");
		inputStartDate.put("startName", "Qu_a#createDate_ge_s");
		inputStartDate.put("endName", "Qu_a#createDate_le_s");
		inputStartDate.put("type", "datetime");
		arr.add(inputStartDate);
		
		JSONObject select = new JSONObject();
		select.put("text", "状态");
		select.put("name", "status");
		select.put("tagName", "select");
		String searchJSONStr = "[{\"value\":\"00\", \"text\":\"启用\"},{\"value\":\"01\", \"text\":\"禁用\"}]";
		JSONArray searchArr = JSONObject.parseArray(searchJSONStr);
		select.put("selects", searchArr);
		arr.add(select);*/
		
		// data-text : 提示，查询，查询2，选项
		// data-name : text,query,query2,option
//		arr.add(createInput("名称", "Qu_name_lk_s"));
//		arr.add(createInput("类型", "Qu_type_eq_s"));
//		arr.add(createInput("用户编号", "Qu_uid_eq_s"));
//		arr.add(createInput("开场编号", "Qu_id_eq_s"));
//		arr.add(createInput("第三方支付订单号", "Qu_payOrderId_eq_s"));
//		arr.add(createDate("选择时间", "Qu_createDate_ge_s", "Qu_createDate_le_s")); // 两个时间区之间 
//		arr.add(createDate("选择时间", "Qu_createDate_ge_s"));// 一个时间
//		arr.add(createSelectByCode("Qu_type_eq_s", "类型", ""
//				+ "icon-图标"
//				+ ",plan_sign-计划"
//				+ ",note_tab-笔记标签"
//				));// 普通的select标签
//		arr.add(createSelectByCode("Qu_winType_eq_s", "开", "01-庄,02-和,03-闲"));// 普通的select标签
		arr.add(createSelectByCode("status", "状态", "[{'entity': 'Member', 'list': 'members', 'value': 'Member.id', 'text': 'Member.name'}]", true));// 普通的select标签 用到forEach
		//arr.add(createSelectByCode("status", "状态", "id-name-members-Member", true));// 普通的select标签 用到forEach
		arr.add(createInput("订单编号", "id")); // 两个时间区之间 
		arr.add(createInput("用户编号", "uId"));// 一个时间
		arr.add(createSelectByCode("paymentStatus", "支付状态", ""
				+ "00-已支付"
				+ ",02-待支付"
				));
		arr.add(createSelectByCode("originalAmount", "发货状态", ""
				+ "00-完成"
				+ ",01-途中"
				+ ",02-未发货"
				));
		System.out.println(arr.toJSONString());
		createCom(arr);
		
	}
	public static String level_1 = "";
	public static String level_2 = "\t"+level_1;
	public static String level_3 = "\t"+level_2;
	public static String level_4 = "\t"+level_3;
	public static String level_5 = "\t"+level_4;
	public static void createCom(JSONArray arr) {
		String html = "";
		for(int i = 0; i < arr.size(); i++) {
			JSONObject object = arr.getJSONObject(i);
			
			switch((String) object.get("tagName")) {
				case "date": 
					// 时间标签
					html +=
					level_1+"<div class=\"layui-inline\">\r\n" + 
					level_2+"<label class=\"layui-form-label\">"+object.getString("text")+"</label>\r\n";
					
					html += object.get("startName") != null ? 
					level_2+"<div class=\"layui-input-inline\" style=\"width: 120px;\">\r\n" + 
					level_3+"<input \r\n" + 
					level_3+"type=\"text\" \r\n" + 
					level_3+"name=\""+object.get("startName")+"\" \r\n" + 
					level_3+"placeholder=\"请输入开始时间\" \r\n" + 
					level_3+"id=\"date\" \r\n" + 
					level_3+"autocomplete=\"off\" \r\n" + 
					level_3+"style=\"width: 120px;\" \r\n" + 
					level_3+"class=\"layui-input\">\r\n" + 
					level_2+"</div> \r\n" : "";
					html += object.get("endName") != null ? 
					level_2+"<div class=\"layui-input-inline\" style=\"width: 120px;\">\r\n" + 
					level_3+"<input \r\n" + 
					level_3+"type=\"text\" \r\n" + 
					level_3+"name=\""+object.get("endName")+"\" \r\n" + 
					level_3+"placeholder=\"请输入结束时间\" \r\n" + 
					level_3+"id=\"date1\" \r\n" + 
					level_3+"autocomplete=\"off\" \r\n" + 
					level_3+"style=\"width: 120px;\" \r\n" + 
					level_3+"class=\"layui-input\">\r\n" + 
					level_2+"</div>\r\n" : "";
					html += 
					level_1+"</div>\r\n";
					break;
				case "select": 
					// select标签
					html +=
					level_1+"<div class=\"layui-inline\">	\r\n" + 
					level_2+"<label class=\"layui-form-label\">"+object.get("text")+"</label>\r\n" + 
				    level_2+"<div class=\"layui-input-inline\">\r\n" + 
				    level_3+"<select name=\""+object.get("name")+"\">\r\n" + 
				    level_3+"<option value=\"-1\">请选择"+object.get("text")+"</option>\r\n";
					JSONArray options = (JSONArray) object.get("options");
					JSONObject option = options.getJSONObject(0);
					html += object.getBoolean("isFor") ? 
					level_3+"<c:forEach begin=\"0\" items=\"${"+option.getString("list")+"}\" step=\"1\" var=\""+option.getString("entity")+"\" varStatus=\"varsta\">\r\n" + 
					level_4+"<option value=\"${"+option.getString("value")+"}\">${"+option.getString("text")+"}</option>\r\n" + 
					level_3+"</c:forEach>\r\n" : option(options); 
					html +=
					level_3+"</select>\r\n" + 
					level_2+"</div>\r\n" + 
				    level_1+"</div>\r\n";
					break;
				default: 
					html +=
					level_1+"<div class=\"layui-inline\">\r\n" + 
					level_2+"<label class=\"layui-form-label\">"+object.get("text")+"</label>\r\n" + 
					level_2+"<div class=\"layui-input-inline\">\r\n" + 
					level_3+"<input type=\"text\" name=\""+object.get("name")+"\" placeholder=\""+(object.get("placeholder") != null?object.get("placeholder"):"请输入")+"\" autocomplete=\"off\" class=\"layui-input\">\r\n" + 
					level_2+"</div>\r\n" + 
				    level_1+"</div>\r\n";
					// 一般输入标签处理
					break;
			}
			
		}
		System.out.println(html);
	}
	public static String option(JSONArray arr) {
		String html = "";
		for(int i = 0; i < arr.size(); i++) {
			JSONObject o = arr.getJSONObject(i);
			html+=level_3+"<option value=\""+o.getString("value")+"\">"+o.getString("text")+"</option>\r\n";
		}
		return html;
	}
	public static JSONObject createInput(String name, String text) {
		JSONObject input = new JSONObject();
		input.put("text", name);
		input.put("name", text);
		input.put("tagName", "text");
		return input;
	}
	
	/* 
	laydate.render({
	    elem: '#date'
	    ,type: 'datetime'
	});
	laydate.render({
	    elem: '#date1'
	    ,type: 'datetime'
	});
	*/
	public static JSONObject createDate(String text, String startName) {
		JSONObject inputStartDate = new JSONObject();
		inputStartDate.put("text", text);
		inputStartDate.put("tagName", "date");
		inputStartDate.put("startName", startName);
		return inputStartDate;
	}
	public static JSONObject createDate(String text, String startName, String endName) {
		JSONObject inputStartDate = new JSONObject();
		inputStartDate.put("text", text);
		inputStartDate.put("tagName", "date");
		inputStartDate.put("startName", startName);
		inputStartDate.put("endName", endName);
		return inputStartDate;
	}
	// codeStr 00-启用,01-禁用   
	//		--> [{"text":"启用","value":"00"},{"text":"禁用","value":"01"}]
	// isFor=true codeStr id-name-roles-Role    
	//		--> [{"text":"name","value":"id", "items":"roles", "var":"Role"}]
	public static JSONArray coverCodeStr(String code, boolean isFor){
		String[] arr = code.split(",");
		JSONArray jSONArray = new JSONArray();
		JSONObject object;
		for(String arrStr : arr) {
			object = new JSONObject();
			object.put("value", arrStr.split("-")[0]);
			object.put("text", arrStr.split("-")[1]);
			if(isFor) {
				object.put("items", arrStr.split("-")[2]);
				object.put("var", arrStr.split("-")[3]);
			}
			jSONArray.add(object);
		}
		return jSONArray;
	}	
	// codeStr 00-启用,01-禁用
	/**
	 * 
	 * @param name		属性name值
	 * @param text		显示串    例如：状态
	 * @param codeStr	00-启用,01-禁用
	 * @return
	 */
	public static JSONObject createSelectByCode(String name, String text, String codeStr) {
		return createSelect(name, text, coverCodeStr(codeStr, false), false);
	}
	/**
	 * 
	 * @param name		属性name值
	 * @param text		显示串    例如：状态
	 * @param json		[{"text":"启用","value":"00"},{"text":"禁用","value":"01"}]   /    [{"text":"name","value":"id", "items":"roles", "var":"Role"}]
	 * @return
	 */
	public static JSONObject createSelect(String name, String text, String json) {
		return createSelect(name, text, JSONObject.parseArray(json), false);
	}
	/**
	 * 
	 * @param name		属性name值
	 * @param text		显示串    例如：状态
	 * @param json	00-启用,01-禁用
	 * @param isFor		true 则有 forEach 标签  通常是从字典表中自动获取数据
	 * @return
	 */
	public static JSONObject createSelectByCode(String name, String text, String json, boolean isFor) {
		return createSelect(name, text, JSONObject.parseArray(json), isFor);
	}
	/**
	 * 
	 * @param name		属性name值
	 * @param text		显示串    例如：状态
	 * @param json		[{"text":"启用","value":"00"},{"text":"禁用","value":"01"}]   /    [{"text":"name","value":"id", "items":"roles", "var":"Role"}]
	 * @param isFor		true 则有 forEach 标签  通常是从字典表中自动获取数据
	 * @return
	 */
	public static JSONObject createSelect(String name, String text, String json, boolean isFor) {
		return createSelect(name, text, JSONObject.parseArray(json), isFor);
	}
	/**
	 * 创建select标签
	 * @param name		属性name值
	 * @param text		显示串    例如：状态
	 * @param jsonarr	对象形式  [{"text":"name","value":"id", "items":"roles", "var":"Role"}] / [{"text":"name","value":"id", "items":"roles", "var":"Role"}]
	 * @param isFor		true 则有 forEach 标签  通常是从字典表中自动获取数据
	 * @return
	 */
	public static JSONObject createSelect(String name, String text, JSONArray jsonarr, boolean isFor) {
		JSONObject select = new JSONObject();
		select.put("text", text);
		select.put("name", name);
		select.put("isFor", isFor);
		select.put("tagName", "select");
//		String selectJSONStr = "[{\"value\":\"00\", \"text\":\"启用\"},{\"value\":\"01\", \"text\":\"禁用\"}]";
		JSONArray seelectArr = jsonarr;
		select.put("options", seelectArr);
		return select;
	}
	
	
		public static void test0319_2(){
		// 
		System.out.println(new BigDecimal("0.00").compareTo(new BigDecimal("0")));
	}
	
	public static String LEVEL_COMMISS_1 = "0.08";
	public static String LEVEL_COMMISS_2 = "0.06";
	public static String LEVEL_COMMISS_3 = "0.04";
	public static String LEVEL_COMMISS_4 = "0.02";
	public static String LEVEL_COMMISS_5 = "0.01";
	public static String LEVEL_COMMISS_6 = "0.01";
	public static String LEVEL_COMMISS_7 = "0.01";
	public static String LEVEL_COMMISS_8 = "0.01";
	public static String LEVEL_COMMISS_9 = "0.01";
	public static String LEVEL_COMMISS_n = LEVEL_COMMISS_9;
	
	public static void test20200320(){
		//总共有十级 8% %4 %2 %1
		//用户1							分得100*1%=1
		//	用户1-1						分得100*2%=2
		//		用户1-1-1	 				分得100*4%=4
		//			用户1-1-1-1      		分得100*8%=8
		//				用户1-1-1-1-1    	消费100
		
//		id nickname parent_id 
		arr.add(createJSON("0", "cjw0", null));
		arr.add(createJSON("01", "cjw0", "0"));
		arr.add(createJSON("02", "cjw0", "01"));
		arr.add(createJSON("03", "cjw0", "02"));
	}
	static JSONArray arr = new JSONArray();
	public static JSONObject selectById(String id){
		for(int i = 0; i < arr.size(); i++){
			JSONObject json;
			if((json = arr.getJSONObject(i)).getString("id").equals(id)){
				return json;
			}
		}
		return null;
	}
	public static JSONObject createJSON(String id, String nickname, String parentId){
		JSONObject object = new JSONObject();
		object.put("id", id);
		object.put("nickname", nickname);
		object.put("parent_id", parentId);
		return object;
	}
	public static void test20200320_0(String parentId, int count){
		
	}
	public static void test(){
		System.out.println(java.net.URLDecoder.decode("%23"));
	}
	static Map<String, String[]> map = new HashMap<>();
	// 工作
	public static void testWork(){
		CharStreamImpl c = new CharStreamImpl("d:/18.txt"); //生成map
		CharStreamImpl out1 = new CharStreamImpl("d:/0.txt");

		CharStreamImpl out10 = new CharStreamImpl("d:/20.txt");
		CharStreamImpl out11 = new CharStreamImpl("d:/21.txt");
		CharStreamImpl out12 = new CharStreamImpl("d:/22.txt");
		CharStreamImpl out13 = new CharStreamImpl("d:/23.txt");
		CharStreamImpl out14 = new CharStreamImpl("d:/24.txt");
		CharStreamImpl out15 = new CharStreamImpl("d:/25.txt");
		CharStreamImpl out16 = new CharStreamImpl("d:/26.txt");
		
		
		c.read(line->{
			
			System.out.println(line);
			String str = (String) line;
			String[] arr = str.split(" ");
			map.put(arr[1], arr);
			//out1.write("\"BIND_ID\" = '"+str.trim()+"' OR ");
		});
		out1.read(line->{
			String str = "";
			try{
				str = (String) line;
				String[] arr = str.split(" ");
				String[] arr_ = map.get(arr[0]);
				
				out10.write(arr_[0]);
				out11.write(arr_[1]);
				out12.write(arr_[2]);
				
				out13.write(arr[1]);
				out14.write(arr[2]);
				out15.write(arr[3]);
				out16.write(arr[4]);
			}catch(ArrayIndexOutOfBoundsException e){
				System.err.println(str);
			}
			
		});
		
		out10.close();
		out11.close();
		out12.close();
		out13.close();
		out14.close();
		out15.close();
		out16.close();
	}
	// 测试路径
	public static void testPath(){
		System.out.println(Test.class.getClassLoader().getResource("/"));
		String path = Test.class.getClassLoader().getResource("../upload/").toString();
		File file = new File(path);
		file.mkdirs();
		CharStreamImpl c = new CharStreamImpl(path+"1.txt");
		c.write("你好");
		c.close();
	}
	public static String coversNum(int num) {
		if(num<10)
			return "0"+num;
		return String.valueOf(num);
	}
	 /**
	  * base64字符串转化成图片
	  * 
	  * @param imgData
	  *            图片编码
	  * @param imgFilePath
	  *            存放到本地路径
	  * @return
	  * @throws IOException
	  */
	 @SuppressWarnings("finally")
	 public static boolean GenerateImage(String imgData, String imgFilePath) throws IOException { // 对字节数组字符串进行Base64解码并生成图片
	        if (imgData == null) // 图像数据为空
	             return false;
	         BASE64Decoder decoder = new BASE64Decoder();
	         OutputStream out = null;
	         try {
	             out = new FileOutputStream(imgFilePath);
	             // Base64解码
	             byte[] b = decoder.decodeBuffer(imgData);
	             for (int i = 0; i < b.length; ++i) {
	                 if (b[i] < 0) {// 调整异常数据
	                     b[i] += 256;
	                 }
	             }
	             out.write(b);
	  } catch (FileNotFoundException e) {
	             e.printStackTrace();
	         } catch (IOException e) {
	             e.printStackTrace();
	         } finally {
	             out.flush();
	             out.close();
	            return true;
	         }
	 }    
	 /**
	  * 图片转化成base64字符串
	  * @param imgPath
	  * @return
	  */
	public static String GetImageStr(String imgPath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		String imgFile = imgPath;// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		String encode = null; // 返回Base64编码过的字节数组字符串
		// 对字节数组Base64编码
	 	BASE64Encoder encoder = new BASE64Encoder();
	 	try {
	 		// 读取图片字节数组
	 		in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            encode = encoder.encode(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
	    		in.close();
        	} catch (IOException e) {
            	e.printStackTrace();
         	}
	 	}
	 	return encode;
	}
	
	/**
	 * 其他
	 * <p>	 
	 * void
	 * @see
	 * @since 1.0
	 */
	public static void tested(){
		// 测试redis
		/*Jedis j = new Jedis();
		System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+j.ping());
        
        j.set("runoobkey", "www.runoob.com1");
        System.out.println("redis 存储的字符串为: "+ j.get("runoobkey"));*/
        
		// 识别
		// 识别出来拿去解析成 特殊记忆码
		
//		String s = "public static void main(String[] args) throws InstantiationException, IllegalAccessException{";
//		String regex = "((private|public|protected)\\s{1})?(static\\s{1})?[a-zA-Z|_]+[0-9|_]*(\\[\\])?\\s[a-zA-Z|_]+[0-9|_]*;?"; // 函数 声明
		
//		String s = "djkk.test();";
//		String regex = "[a-zA-Z|_]+[0-9|_]*"; // 变量名
		
//		String s = "System.out.println(s.matches(regex));";
//		String regex = "([a-zA-Z|_]+[0-9|_]*\\.)+[a-zA-Z|_]+[0-9|_]*\\(.*\\);?"; // 方法的调用
		
//		String s = "String count;";
//		String regex = "((private|public|protected)\\s{1})?(static\\s{1})?[a-zA-Z|_]+[0-9|_]*(\\[\\])?\\s[a-zA-Z|_]+[0-9|_]*;?"; // 函数 声明
		
//		String s = "count = 123;";
//		String regex = "[a-zA-Z|_]+[0-9|_]*\\s?=\\s?.+;?"; // 函数 赋值
		
//		String s = "float count = 4566;";
//		String regex = "[a-zA-Z|_]+[0-9|_]*\\s[a-zA-Z|_]+[0-9|_]*\\s?=\\s?.+;?"; // 函数声明 赋值
//		String regex = "((private|public|protected)\\s{1})?(static\\s{1})?[a-zA-Z|_]+[0-9|_]*\\s[a-zA-Z|_]+[0-9|_]*\\s?=\\s?.+;?"; // 函数内部赋值
//		System.out.println(s.matches(regex));
		
		
		
		/*String str = "import package";
		String code = "019";
		int count = 36;
		String[] arr = str.split(" ");
		for(String item : arr) {
			System.out.println("commond.put(\""+item+"\", \""+code+"\");");
		}
		
		for(String item : arr) {
			System.out.println("key.put(\""+coversNum(count++)+"\", \""+item+"\");");
		}*/
        
	/*	log.info("hello word");
		log.error("hello word");
		log.fatal("hello word");
		log.debug("hello word");
		log.info("hello word");
		log.trace("我是trace");
 
		log.trace("退出程序.");*/
		/*Class<?> clazz = PrivateClass.class;// 获取PrivateClass整个类
		PrivateClass pc = (PrivateClass) clazz.newInstance();// 创建一个实例
*/		/*pc.setName("cjw");
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
		/*String endDate, startDate;
		SimpleDateFormat formatter;
		Date date1 = new Date();
        //获取String类型的时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date1);
    	date1 = calendar.getTime(); //这个时间就是日期往后推一天的结果 
        formatter = new SimpleDateFormat("yyyyMMdd");
        endDate = formatter.format(date1);
		for (int i = 0; i <10; i++){
			calendar.add(calendar.DATE,-i);//把日期往后增加一天.整数往后推,负数往前移动
		}	
		calendar.add(calendar.DATE,-10);
		date1 = calendar.getTime(); //这个时间就是日期往后推一天的结果 
        formatter = new SimpleDateFormat("yyyyMMdd");
        startDate = formatter.format(date1);//取十天
        System.out.println(startDate + " " + endDate);*/
		/*try {
			GenerateImage("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", "d://img.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
//		String str = new MenuControl().identification("17", "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", "d://1.jpg");
//		System.out.println(str);
//		testWork();
//		test();		
	}
}
