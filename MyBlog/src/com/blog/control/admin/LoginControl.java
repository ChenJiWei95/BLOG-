package com.blog.control.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.blog.service.AdminService;
import com.blog.util.ActionUtil;
import com.blog.util.Message;
import com.blog.util.SnowFlakeGenerator;

@Controller
// 数据字典
@RequestMapping("/admin")
public class LoginControl extends BaseControl{
	
	@Autowired
	private AdminService adminServiceImpl;
	
	// 返回 页面 
	@RequestMapping("/login.chtml") 
	public String listview1(ModelMap model){
		return "admin/login";
	}
	  
	// 添加
	@RequestMapping("login.do")
	@ResponseBody
	public Object login(Admin t, HttpServletRequest re, ModelMap model) throws IOException{ 
		System.out.println("添加接收参数："+ t + " " + ActionUtil.read(re)); 
		
		try{
			Admin a = adminServiceImpl.get(singleMarkOfEq("username", t.getUsername()));
			if(a != null && a.getPassword().equals(t.getPassword())) {
				if(Integer.parseInt(a.getLogin_count()) < 5) {
					t.setLogin_count("0");
					t.setState("00");
					adminServiceImpl.update(t, singleMarkOfEq("username", t.getUsername()));
				} else 
					return com.blog.util.Message.error("账号已锁定！");
			} else {
				// 密码错误或账号不存在
				if(Integer.parseInt(a.getLogin_count()) >= 5) 
					return com.blog.util.Message.error("账号已锁定！");
				if(a != null) {
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
			return com.blog.util.Message.success("登录成功", data);
		}catch(Exception e){
			return com.blog.util.Message.error("服务器异常，"+e.getMessage());
		}
	}
	@RequestMapping("logout.do")
	@ResponseBody
	public Object logout(Admin t, HttpServletRequest re, ModelMap model) throws IOException{ 
		try{
			System.out.println("添加接收参数："+ t + " " + ActionUtil.read(re)); 
			Admin a = (Admin) re.getSession().getAttribute(Constant.USER_CONTEXT);
			re.getSession().setAttribute(Constant.USER_CONTEXT, null);
			a.setState("01");
			adminServiceImpl.update(a, singleMarkOfEq("id", a.getId()));
			return com.blog.util.Message.success("已登出");
		}catch(Exception e){
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
			return Message.success("请求失败，"+e.getMessage(), null);
		}
	}
	
	
	
}
