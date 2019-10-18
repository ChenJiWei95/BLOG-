package com.blog.control.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Many;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blog.Constant;
import com.blog.control.BaseControl;
import com.blog.entity.Admin;
import com.blog.entity.CMessage;
import com.blog.service.AdminService;
import com.blog.service.MessageService;
import com.blog.util.ActionUtil;
import com.blog.util.Message;
import com.blog.util.SnowFlakeGenerator;
import com.blog.util.sql.ManyTable;

@Controller
@RequestMapping("/admin")
public class LoginControl extends BaseControl{
	
	@Autowired
	private AdminService adminServiceImpl;

	@Autowired
	private MessageService messageServiceImpl;
	
	// 返回 页面 
	@RequestMapping("/login.chtml") 
	public String listview1(ModelMap model){
		return "admin/login";
	}
	@RequestMapping("/privilege.chtml") 
	public String privilege(ModelMap model){
		return "admin/privilege";
	}
	  
<<<<<<< HEAD
	// 添加 
	@RequestMapping("login.do")
	@ResponseBody 
=======
	// 添加
	@RequestMapping("login.do") 
	@ResponseBody
>>>>>>> branch 'x2' of https://github.com/ChenJiWei95/BLOG-.git
	public Object login(Admin t, HttpServletRequest re, ModelMap model) throws Exception{ 
		CMessage cm = new CMessage();
		cm.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
		cm.setExe_name(t.getUsername());
		cm.setDesc("登录提示--后台管理系统");
		cm.setIsRead(Constant.COMMON_FALSE);
		cm.setType(Constant.MESSAGE_TYPE_SYS);
		cm.setTime(getNowTime());
		cm.setTitle("登录提示");
		cm.setContent(t.getUsername()+" 于 "+cm.getTime()+" 进行登录, IP:"+getIpAddr(re));
		messageServiceImpl.insert(cm);
		try{
			Admin a = adminServiceImpl.get(singleMarkOfEq("username", t.getUsername()));
			if(a != null && a.getPassword().equals(t.getPassword())) {
				if("01".equals(a.getState())) {
					return com.blog.util.Message.error("账号已禁用！");
				} else if(Integer.parseInt(a.getLogin_count()) < 5) {
					t.setLogin_count("0");
					adminServiceImpl.update(t, singleMarkOfEq("username", t.getUsername()));
				} else 
					return com.blog.util.Message.error("账号已锁定！");
			} else {
				// 密码错误或账号不存在
				if(a != null) {
					if(Integer.parseInt(a.getLogin_count()) >= 5) 
						return com.blog.util.Message.error("账号已锁定！");
					t.setLogin_count((Integer.parseInt(a.getLogin_count())+1)+"");
					t.setPassword(null);
					adminServiceImpl.update(t, singleMarkOfEq("username", t.getUsername()));
					return com.blog.util.Message.error("密码错误，第"+t.getLogin_count()+"次！连续五次输错将会锁定账户！");
				}
				return com.blog.util.Message.error("账号不存在！");
			}
			JSONObject data = new JSONObject();
			String token = String.valueOf(new SnowFlakeGenerator(2, 2).nextId());
					
			data.put("token", token);
			re.getSession().setAttribute(Constant.USER_CONTEXT, a);
			
			// 权限链接集
			List<Map<String, Object>> permissionMap =  adminServiceImpl.getByManyTable(new ManyTable()
					.selete("c.`url`").as("url")
					.form("`admin_infor`").as("a")
						.and("`role`").as("b")
						.and("`menu`").as("c")
					.where("a.`admin_id` = "+quma2(a.getId()))
						.and("a.`role_id` = b.`role_id`")
						.and("b.`app_id` = c.`id`"));
			List<String> permissionList = new java.util.ArrayList<>(permissionMap.size());
			for(int i = 0, len = permissionMap.size(); i < len; i++)
				permissionList.add(re.getContextPath()+"/"+permissionMap.get(i).get("url"));
			permissionList.add(re.getContextPath()+"/"+"admin/main/listview.chtml");
			re.getSession().setAttribute(Constant.PERMISSION_LIST, permissionList);
			
			return com.blog.util.Message.success("登录成功", data);
		}catch(Exception e){
			e.printStackTrace();
			return com.blog.util.Message.error("服务器异常，"+e.getMessage());
		}
	} 
 
	@RequestMapping("logout.do")
	@ResponseBody
	public Object logout(HttpServletRequest re) throws IOException{ 
		try{
			System.out.println("退出");
			re.getSession().setAttribute(Constant.USER_CONTEXT, null);
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
			JSONArray json = JSONObject.parseArray(ActionUtil.read(request));
			StringBuffer sb = new StringBuffer();
			
			for(int i = 0; i < json.size(); i++) {
				JSONObject object = json.getJSONObject(i);
				sb.append(singleMarkOfEq("id", object.getString("id"))).append(" OR ");
			}
			if(json.size() > 0) {
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
			System.out.println("修改接收参数："+t); 
			// 根据admin ID 对账号和进行修改 根据id 对adminInfor信息进行修改
			adminServiceImpl.update(t, singleMarkOfEq("id", t.getId())); 
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
        System.out.println("***访问的Url中的服务器IP："+requestUrlIP);
        System.out.println("***用户客户端的IP地址："+userIpAddr); 
        
        return ipString;
    }
	
}
