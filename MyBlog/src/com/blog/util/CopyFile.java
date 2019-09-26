package com.blog.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CopyFile {
	public static CopyFile getInstance(){
		return new CopyFile();
	}
	public void copy(String sourcePath, String copyToPath) throws Exception{
		FileInputStream fis = new FileInputStream(sourcePath);
		FileOutputStream fos = new FileOutputStream(copyToPath);
		int b = -1;
		while((b = fis.read()) != -1) fos.write(b);
		fis.close();
		fos.close();
	}
}
