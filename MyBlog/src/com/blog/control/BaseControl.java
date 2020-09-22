package com.blog.control;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blog.util.GsonUtil;
import com.blog.util.SpringUtils;
import com.blog.util.TimeUtil;
import com.blog.util.i18n.AbstractCi18nCore; 

public class BaseControl {
	
	
	@Autowired
	private AbstractCi18nCore ci18nMybatis;
	
	public static Logger logger = LogManager.getLogger(BaseControl.class);
	
	public Object parseInt(String num){
		if(num != null && !"".equals(num) && !"NULL".equals(num))
			return new BigDecimal(num);
		return 0;
	}
	
	public Object parseLong(String num){
		if(num != null && "".equals(num))
			return Long.parseLong(num);
		return "";
	}
	
	public String getIpAddr(final HttpServletRequest request)
            throws Exception {
        if (request == null) {
            throw (new Exception("getIpAddr method HttpServletRequest Object is null"));
        }
        String ipString = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getRemoteAddr();
        }
     
        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ipString.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ipString = str;
                break;
            }
        }
     
        String requestUrlIP = request.getServerName();
        String userIpAddr = request.getRemoteAddr();
        logger.info("***访问的Url中的服务器IP："+requestUrlIP);
        logger.info("***用户客户端的IP地址："+userIpAddr); 
        
        return ipString;
    }
	
	/**
	 *  `id` = '1234'
	 * <p>	 
	 * @param col
	 * @param fields
	 * @return
	 * String
	 * @see
	 * @since 1.0
	 */
	/*public static String eq(String col, String fields) {
		return "`"+col+"` = '"+fields+"' ";
	}*/
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
	protected String singleOfEqString(String col, String fields) {
		return "`"+col+"` = '"+fields+"' ";
	}
	
	/**
	 * `id` = 1 或者   `id` = '1234' 自动识别传入的类型
	 * <p>	 
	 * @param col
	 * @param fields
	 * @return
	 * String
	 * @see
	 * @since 1.0
	 */
	protected String eq(String col, Object fields) {
		String eq = "`"+col+"` = ";
		switch (fields.getClass().getSimpleName()){
			case "BigDecimal" : 
			case "Integer" : eq += fields + " "; 
				break;
			case "String" : eq += "'"+fields+"' "; 
				break;
		}
		return eq;
	}
	
	public static String toHex(){    
        return Integer.toHexString((int)new Date().getTime());
	}
	 
	/**
	 * 用于数字
	 * <p>	 
	 * @param col
	 * @param fields
	 * @return
	 * String
	 * @see
	 * @since 1.0
	 */
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
		return TimeUtil.getDatetime();
	}
	
	protected String getNowDate() {
		return TimeUtil.getDate(TimeUtil.DATE_FORMAT);
	}
	
	protected <T> JSONArray listToJSONArray(List<T> list){ 
		JSONArray arr = new JSONArray();
		for(T t: list){
			arr.add(jsonToJSONObject(t)); 
		}
		return arr;
	}
	
	/**
	 * 传入一个对象 生成JSONObject对象
	 * <p>	 
	 * @param menu
	 * @return
	 * JSONObject
	 * @see
	 * @since 1.0
	 */
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
	
	protected String getText(String code){
		return ci18nMybatis.execute(code);
	}
	
	protected Object getBean(String name){
		return SpringUtils.getBean(name);
	}
} 


















