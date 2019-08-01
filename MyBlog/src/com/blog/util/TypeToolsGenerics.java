package com.blog.util;

import java.util.HashMap;
import java.util.Map;

import com.blog.exception.UnconvertibleException;

/**
 * 
 * <b>获取一个对象的类型工具类<b>
 * @author 威 
 * <br>2018年1月7日 下午7:43:07 
 *
 */
public class TypeToolsGenerics  
{  
    private static final Map<String,String> typeMap=new HashMap<String,String>();  
    static {  
        typeMap.put("java.lang.Byte", "byte");  
        typeMap.put("java.lang.Short", "short");  
        typeMap.put("java.lang.Integer", "int");  
        typeMap.put("java.lang.Long", "long");  
        typeMap.put("java.lang.Double", "double");  
        typeMap.put("java.lang.Float", "float");  
        typeMap.put("java.lang.Character", "char");  
        typeMap.put("java.lang.Boolean", "boolean");  
        typeMap.put("java.lang.String", "string");
        typeMap.put("java.sql.Timestamp", "timestamp");
    }  
    public final static <T> String getType(T t) {  
        if(t == null){ return null; }  
        String typeInfo = t.getClass().getName();  
        return typeMap.containsKey(typeInfo) 
        		? typeMap.get(typeInfo) 
        		: typeInfo;
    }  
    /**
     * 获取对应的类型
     * <p>	 
     * @return
     * Object
     * @see
     * @since 1.0
     */
    public final static <T> Object tokenerType(T t, String currentParame){
    	if(t == null) return null;
    	switch (TypeToolsGenerics.getType(t)){
    		case "byte" : return Byte.parseByte(currentParame);
    		case "short" : return Short.parseShort(currentParame);
    		case "int" : return Integer.parseInt(currentParame);
    		case "long" : return Integer.parseInt(currentParame);
    		case "double" : return Double.parseDouble(currentParame);
    		case "float" : return Float.parseFloat(currentParame);
    		case "boolean" : return Boolean.parseBoolean(currentParame);
    		case "timestamp" : return java.sql.Timestamp.valueOf(currentParame);
    		default : System.out.println("该类型，不受理："+ t.getClass());
    	}
		return currentParame;
    }
    
    /**
	 * 转换成sql类型
	 * <p>	 
	 * @param type
	 * @return
	 * @throws UnconvertibleException
	 * String
	 * @see
	 * @since 1.0
	 */
	public final static <T> String transTypeForSql(T t) throws UnconvertibleException{
		
		String temp = "";
		switch(getType(t)){
			case "string" : temp = "VARCHAR"; break;
			case "timestamp" : temp = "TIMESTAMP"; break;
			case "int" : temp = "INTEGER"; break;
			default : throw new UnconvertibleException(t);
		}
		return temp;
	}
} 
