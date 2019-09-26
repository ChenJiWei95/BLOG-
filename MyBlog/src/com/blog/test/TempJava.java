package com.blog.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TempJava {
	public static void main(String[] args) {
		do1("tempComponent", "temp_component");
	}
	private static int num = 0;
	public static void do1(String name_, String table) {
		String name = name_.substring(0,1).toUpperCase() + name_.substring(1);
//		CharStreamImpl copy = new CharStreamImpl(TempJava.class.getResource("/").getPath().substring(1).replace("build/classes", "src")+"com/blog/dao/"+name+"Dao.java");
		
		String[] prefix = {"dao", "service"/*, "entity"*/, "mapper", "service/impl"};//前缀
		String[] temp = {"dao.txt", "service.txt"/*, "entity.txt"*/, "mapper.xml", "serviceImpl.txt"};
		String[] suffix = {"Dao.java", "Service.java"/*, ".java"*/, "Mapper.xml", "ServiceImpl.java"};//后缀
		for(int i = 0; i < prefix.length; i++) {
			String copyPath = TempJava.class.getResource("/").getPath().substring(1).replace("build/classes", "src")+"com/blog/"+prefix[i]+"/"+name+suffix[i];
			do2(TempJava.class.getResource("/").getPath().substring(1).replace("build/classes", "src")+"config/temp/"+temp[i], copyPath, name, name_, table);
		}
	}
	public static void do2(String path, String copyPath, String name, String name_, String table) {
		BufferedReader in = null;
		BufferedWriter bw = null;
		String line = null;
		try {
			in = new BufferedReader(new FileReader(path));
			bw = new BufferedWriter(new FileWriter(copyPath, true));
			num = 0;
			while((line = in.readLine()) != null){
				bw.write(line.replaceAll("#name#", name).replaceAll("#name_#", name_).replace("#table#", table));
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
