package com.blog.control.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blog.Constants;
import com.blog.anno.TParamer;
import com.blog.control.BaseControl;
import com.blog.entity.Admin;
import com.blog.entity.CMessage;
import com.blog.service.AdminService;
import com.blog.service.MessageService;
import com.blog.service.RedisService;
import com.blog.util.ActionUtil;
import com.blog.util.Message;
import com.blog.util.SnowFlakeGenerator;
import com.blog.util.sql.ManyTable;

@Controller
@RequestMapping("/admin")
public class LoginControl extends BaseControl{
	
	private static Logger log = Logger.getLogger(LoginControl.class); // 日志对象
	
	@Autowired
	private AdminService adminServiceImpl;

	@Autowired
	private MessageService messageServiceImpl;
	
	@Autowired
	private RedisService redisService;
	
	// 返回 页面 
	@RequestMapping("/login.chtml") 
	public String listview1(ModelMap model){
		return "admin/login";
	}
	@RequestMapping("/privilege.chtml") 
	public String privilege(ModelMap model){
		return "admin/privilege";
	}
	  
	@RequestMapping("login.do")
	@ResponseBody  
	public Object login(/*@TParamer*/Admin t, HttpServletRequest re, ModelMap model) throws Exception{ 
		// 此处不需要sql注入检测，因为此处并没有通过查直接决定登录权限，而是查到结果再判断
		log.info("login start");
		log(t, re);
		try{
			Admin admin = adminServiceImpl.get(singleOfEqString("username", t.getUsername()));
			if(admin != null && admin.getPassword().equals(t.getPassword())) {
				if("01".equals(admin.getState())) {
					return com.blog.util.Message.error("账号已禁用！");
				} else if(Integer.parseInt(admin.getLogin_count()) < 5) {
					t.setLogin_count("0");
					adminServiceImpl.update(t, singleOfEqString("username", t.getUsername()));
				} else 
					return com.blog.util.Message.error("账号已锁定！");
			} else {
				// 密码错误或账号不存在
				if(admin != null) {
					if(Integer.parseInt(admin.getLogin_count()) >= 5) 
						return com.blog.util.Message.error("账号已锁定！");
					t.setLogin_count((Integer.parseInt(admin.getLogin_count())+1)+"");
					t.setPassword(null);
					adminServiceImpl.update(t, singleOfEqString("username", t.getUsername()));
					return com.blog.util.Message.error("密码错误，第"+t.getLogin_count()+"次！连续五次输错将会锁定账户！");
				}
				return com.blog.util.Message.error("账号不存在！");
			}
			JSONObject data = new JSONObject();
			String token = String.valueOf(com.blog.util.CommonUtil.getUUID());
					
			data.put("token", token);
			redisService.set(token, admin, 3600);
			log.info("token:"+token);
			log.info(redisService.get(token));
//			re.getSession().setAttribute(Constants.USER_CONTEXT, admin);
			
			// 权限链接集
			List<Map<String, Object>> permissionMap =  adminServiceImpl.getByManyTable(new ManyTable()
					.selete("c.`url`").as("url")
					.form("`admin_infor`").as("a")
						.and("`role`").as("b")
						.and("`menu`").as("c")
					.where("a.`admin_id` = "+quma2(admin.getId()))
						.and("a.`role_id` = b.`role_id`")
						.and("b.`app_id` = c.`id`"));
			List<String> permissionList = new java.util.ArrayList<>(permissionMap.size());
			for(int i = 0, len = permissionMap.size(); i < len; i++)
				permissionList.add(re.getContextPath()+"/"+permissionMap.get(i).get("url"));
			permissionList.add(re.getContextPath()+"/"+"admin/main/listview.chtml");
			re.getSession().setAttribute(Constants.PERMISSION_LIST, permissionList);
			
			return com.blog.util.Message.success("登录成功", data);
		}catch(Exception e){
			e.printStackTrace();
			return com.blog.util.Message.error("服务器异常，"+e.getMessage());
		}
	}
	
	/**
	 * 这里用一句话描述这个方法的作用
	 * <p>	 
	 * @param t
	 * @param re
	 * @throws Exception
	 * void
	 * @see
	 * @since 1.0
	 */
	protected void log(Admin t, HttpServletRequest re) throws Exception {
		CMessage cm = new CMessage();
		cm.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
		cm.setExe_name(t.getUsername());
		cm.setDesc("登录提示--后台管理系统");
		cm.setIsRead(Constants.COMMON_FALSE);
		cm.setType(Constants.MESSAGE_TYPE_SYS);
		cm.setTime(getNowTime());
		cm.setTitle("登录提示");
		cm.setContent(t.getUsername()+" 于 "+cm.getTime()+" 进行登录, IP:"+getIpAddr(re));
		messageServiceImpl.insert(cm);
	} 
 
	@RequestMapping("logout.do")
	@ResponseBody
	public Object logout(HttpServletRequest re, String token) throws IOException{ 
		try{
			log.info("退出");
//			re.getSession().setAttribute(Constants.USER_CONTEXT, null);
			if(token != null) redisService.del(token);
			return com.blog.util.Message.success("已登出");
		}catch(Exception e){
			e.printStackTrace();
			return com.blog.util.Message.error("服务器异常，"+e.getMessage());
		}
	}
	
	/**
	 * 删除 
	 * @param menu
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("remove.do")
	@ResponseBody
	public Object remove(HttpServletRequest request) throws IOException{
		
		// 判断token是否正确  删除admin 和 adminInfor
		try {
			String[] ids = request.getParameter("ids").split(",");
			StringBuffer sb = new StringBuffer();
			
			for(String id : ids) {
				sb.append("id = ").append("'"+id+"'").append(" OR ");
			}
			if(ids.length > 0) {
				sb.delete(sb.length()-4, sb.length());
				adminServiceImpl.delete(sb.toString());
			}
			return Message.success("请求成功", null);
		}catch(Exception e) {
			e.printStackTrace();
			return Message.success("请求失败，"+e.getMessage(), null);
		}
		
	}
	
	/**
	 * 修改
	 * @param menu
	 * @param spread
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("update.do")
	@ResponseBody
	public Object update(Admin t) throws IOException{ 
		try {
			log.info("修改接收参数："+t); 
			// 根据admin ID 对账号和进行修改 根据id 对adminInfor信息进行修改
			adminServiceImpl.update(t, singleOfEqString("id", t.getId())); 
			return Message.success("请求成功", null);
		}catch(Exception e) {
			e.printStackTrace();
			return Message.success("请求失败，"+e.getMessage(), null);
		}
	} 
	
	/**
	 * 初始化
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("list.do")
	@ResponseBody
	public Object init() throws IOException{
		try {
			List<Admin> list = adminServiceImpl.getAll();
			return Message.success("请求成功", listToJSONArray(list));
		}catch(Exception e) {
			e.printStackTrace();
			return Message.success("请求失败，"+e.getMessage(), null);
		}
	}
	
	public final String getIpAddr(final HttpServletRequest request)
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
        log.info("***访问的Url中的服务器IP："+requestUrlIP);
        log.info("***用户客户端的IP地址："+userIpAddr); 
        
        return ipString;
    }
	
}
