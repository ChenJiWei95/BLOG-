package com.blog.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UploadAndDownload {
	public static void  downloadFile(HttpServletRequest request, HttpServletResponse response, String fileName, String filePath)  
            throws UnsupportedEncodingException, Exception, IOException {  
        // 输出文件  
        response.setCharacterEncoding("utf-8");  
        response.setContentType("multipart/form-data");  
        // 中文文件名支持  
        String encodedfileName = null;  
        String agent = request.getHeader("USER-AGENT");
        System.out.println("agent:"+agent+", fileName:"+fileName);
        if (null != agent && -1 != agent.indexOf("MSIE")) {// IE  
            encodedfileName = java.net.URLEncoder.encode(fileName, "UTF-8");  
        } else if (null != agent && -1 != agent.indexOf("Mozilla")) {  
            encodedfileName = new String(fileName.getBytes("GB2312"), "iso-8859-1");  
        } else {  
            encodedfileName = java.net.URLEncoder.encode(fileName, "UTF-8");  
        }  
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");  
        InputStream inputStream = null;  
        OutputStream os = null;  
        try {  
            inputStream = new FileInputStream(new File(filePath));  
            os = response.getOutputStream();  
            byte[] b = new byte[2048];  
            int length;  
            while ((length = inputStream.read(b)) > 0) {  
                os.write(b, 0, length);  
            }  
        } catch (Exception e) {  
            throw e;  
        } finally {  
            if (os != null) {  
                os.close();  
            }  
            if (inputStream != null) {  
                inputStream.close();  
            }  
        }  
    } 
}
