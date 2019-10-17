package com.blog.control;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blog.entity.Menu;
import com.blog.util.GsonUtil; 

public class BaseControl {
	public static Logger logger = LogManager.getLogger(BaseControl.class);
	private Object ovje;
	
	/**
	 * 例如：`id` = '1234'
	 * <p>	 
	 * @param col
	 * @param fields
	 * @return
	 * String
	 * @see
	 * @since 1.0
	 */
	protected String singleMarkOfEq(String col, String fields) {
		return "`"+col+"` = '"+fields+"' ";
	}
	protected String singleOfEq(String col, String fields) {
		return col + " = " + fields + " ";
	}
	protected String quma(String str) {
		return "`"+str+"`";
	}
	protected String quma2(String str) {
		return "'"+str+"'";
	}
	protected String getNowTime() {
		return com.blog.util.TimeUtil.getDatetime();
	}
	protected <T> JSONArray listToJSONArray(List<T> list){ 
		JSONArray arr = new JSONArray();
		for(T t: list){
			arr.add(jsonToJSONObject(t)); 
		}
		return arr;
	}
	// 生成JSONObject对象
	protected <T> JSONObject jsonToJSONObject(T menu){
		return JSONObject.parseObject(GsonUtil.objToJson(menu));
	}	
	protected static Map<String, String> getRequestParameterMap(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		try {
			Map<String, String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
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


















