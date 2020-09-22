package com.blog;

public class Constants {
	/** 成功  */
	public static String RESCODE_0000 = "0000";//
	/** 失败  */
	public static String RESCODE_0001 = "0001";//
	/** 异常  */
	public static String RESCODE_0002 = "0002";//   
	
	/** 登录标识  */
	public static String USER_CONTEXT = "LOGIN"; 
	/** 异常编码标识  */
	public static String MESSAGE_ERROR_CODE = "MESSAGE_ERROR_CODE"; 
	/** 权限链接标识  */
	public static String PERMISSION_LIST 	= "permission";//
	
	/** 文章消息类型  */
	public static String MESSAGE_TYPE_ART = "02";   
	/** 随笔消息类型  */
	public static String MESSAGE_TYPE_REQ = "03";   
	/** 系统消息类型 */
	public static String MESSAGE_TYPE_SYS = "04";
	
	/** 升序 */
	public static String ORDERBY_ASC 		= "ASC";
	/** 降序 */
	public static String ORDERBY_DESC 		= "DESC";

	/** 公用 true值 */
	public static String COMMON_TRUE 		= "00";
	
	/** 公用  false值 */
	public static String COMMON_FALSE 		= "01";
	
	public static String STATE_SUCCESS 		= "00";
	public static String STATE_HANDLE 		= "01";	
	public static String STATE_FAIL 		= "02";
	
	public static String PAY_TYPE_WECHAT 	= "01";
	public static String PAY_TYPE_ZHIFUBAO 	= "02";
	
	public static String PLAY_01 = "01";
	public static String PLAY_02 = "02";
	public static String PLAY_03 = "03";
	
	/** 英文 */
	public final static String LANGUAGE_EN_US		= "en_US";
	/** 中文 */
	public final static String LANGUAGE_ZH_CN		= "zh_CN";
	/** 当前语言 */
	public final static String LANGUAGE_CURRENT		= "LANGUAGE_CURRENT";
	
	/** WebsiteBase 管理系统基础配置信息id */
	public final static String MANAGER_SYS_BASE_ID	= "1";
	public final static String MANAGER_SYS_BASE		= "MANAGER_SYS_BASE";
	
	public final static String CI18N_CLASS_NAME 	= "ci18nMybatis";
}