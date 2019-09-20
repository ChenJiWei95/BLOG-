package com.blog.control;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.blog.entity.Menu;
import com.blog.util.GsonUtil; 

public class BaseControl {
	public static Logger logger = LogManager.getLogger(BaseControl.class);
	private Object ovje;
	protected String getNowTime() {
		return com.blog.util.TimeUtil.getDatetime();
	}
	// 生成JSONObject对象
	protected JSONObject jsonToJSONObject(Menu menu){
		return JSONObject.parseObject(GsonUtil.objToJson(menu));
	}	
	protected static Map<String, String> getRequestParameterMap(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		try {
			Map<String, String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				System.out.println("name:"+name+", values:"+values);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
					params.put(name, valueStr);
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return params;
	}
	protected String basePath(HttpServletRequest request){
		// http://localhost:8080/MyBlog/
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
	}
} 


















