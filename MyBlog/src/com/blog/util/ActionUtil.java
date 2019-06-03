package com.blog.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <b>辅助action 工具类</b>
 * <p>
 * 描述:<br>
 * 
 * @author 威 
 * <br>2019年4月28日 下午10:48:43 
 * @see
 * @since 1.0
 */
public class ActionUtil {
	/**
	 * 遍历request中的流 返回字符串
	 * <p>	 
	 * @param request
	 * @return
	 * @throws IOException
	 * String
	 * @see
	 * @since 1.0
	 */
	public static String read(HttpServletRequest request) throws IOException{
		
		DataInputStream dis = new DataInputStream(request.getInputStream());
		ByteArrayOutputStream baot = new ByteArrayOutputStream();
		byte[] bytes = new byte[1024];
		int len = 0;
		while((len = dis.read(bytes)) != -1)
			baot.write(bytes, 0, len);
		byte [] outByte = baot.toByteArray();
		baot.flush();
		baot.close();
		dis.close();
		return new String(outByte, "UTF-8");
		
	}
	
	
	/**
	 * response 返回数据
	 * <p>	 
	 * @param response
	 * @param msg
	 * @throws IOException
	 * void
	 * @see
	 * @since 1.0
	 */
	public static void returnRes(HttpServletResponse response, String msg) throws IOException{
		 
		ServletOutputStream out = response.getOutputStream();
		out.write(msg.getBytes("utf-8"));
		out.flush();
		out.close();
		 
	}
}
