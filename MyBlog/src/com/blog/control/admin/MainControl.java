package com.blog.control.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.blog.entity.AdminInfor;
import com.blog.entity.Menu;
import com.blog.entity.Role;
import com.blog.entity.WebsiteBase;
import com.blog.service.AdminInforService;
import com.blog.service.AdminService;
import com.blog.service.MenuService;
import com.blog.service.RoleService;
import com.blog.service.WebsiteBaseService;
import com.blog.util.ActionUtil;

/**
 * 
 * @author cjw	
 *
 */
@RequestMapping("/admin/main")
@Controller
public class MainControl extends BaseControl{
	@Autowired
	MenuService menuServiceImpl;
	@Autowired
	WebsiteBaseService websiteBaseServiceImpl;
	
	@Autowired
	private AdminInforService adminInforServiceImpl;
	
	@Autowired
	private AdminService adminServiceImpl;
	
	@Autowired
	private RoleService roleServiceImpl;
	
	// 返回 页面 
	@RequestMapping("/listview.chtml")
	public String listview1(HttpServletRequest request, String agentno, ModelMap model){
		Admin a = (Admin) request.getSession().getAttribute(Constant.USER_CONTEXT);
		AdminInfor ai = adminInforServiceImpl.get(singleMarkOfEq("admin_id", a.getId()));
		model.addAttribute("name", ai.getName());
		
		try {
			System.out.println(getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/admin_view";
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
	
	@RequestMapping("/aly_control.chtml")
	public String control(HttpServletRequest request, String agentno, ModelMap model){
		return "redirect:https://swas.console.aliyun.com/?spm=5176.12818093.aliyun_sidebar.aliyun_sidebar_swas.488716d06X0Cxb#/server/801f7b4cfd3f4a40b65d5e40132ede11/cn-shenzhen/dashboard";
	}
	
	/**
	 * 递归初始化
	 * @param id
	 * @return
	 */
	protected JSONArray init_(String id, List<String> app_ids){
		JSONArray jsonArray = null;
		Menu menu = new Menu();
		menu.setRelate_id(id); 
		List<Menu> ms = menuServiceImpl.getOfOrderBySort(menu, "ASC", "priority");
		if(ms != null && ms.size() > 0){
			jsonArray = new JSONArray();
			for(Menu item : ms) { 
				JSONObject object = jsonToJSONObject(item); 
				object.remove("name"); object.remove("id"); object.remove("url"); 
				object.remove("update_time"); object.remove("create_time"); object.remove("relate_id"); 
				object.remove("msg"); object.remove("priority"); 
				object.put("desc", item.getName()); 
				object.put("key", item.getId());
				object.put("href", item.getUrl());
				// url不是#### 那么判断id是否在权限中
				if(item.getUrl().indexOf("####") != -1) {
					object.put("dataName", item.getId());
					JSONArray jsonArray_ = null;
					if((jsonArray_ = init_(item.getId(), app_ids)) != null) {
						object.put("children", jsonArray_);
					}
					if(jsonArray_ != null && jsonArray_.size() > 0) jsonArray.add(object); 
				}else {
					if(app_ids.contains(item.getId())) jsonArray.add(object);
				}
			}
		}
		return jsonArray;
	}
	
	/**
	 * 初始化
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("init.do")
	@ResponseBody
	public JSONObject init(HttpServletRequest request) throws IOException{
		
		// 用户根据权限获取 appid集合
		Admin a = (Admin) request.getSession().getAttribute(Constant.USER_CONTEXT);
		AdminInfor adminInfor = adminInforServiceImpl.get(singleMarkOfEq("admin_id", a.getId()));
		List<Role> roles = roleServiceImpl.gets(singleMarkOfEq("role_id", adminInfor.getRole_id()));
		List<String> app_ids = new ArrayList<>();
		for(Role role : roles) {
			app_ids.add(role.getApp_id());
		}
		
		// 收集菜单信息
		Menu m = new Menu();
		m.setRelate_id("");
		List<Menu> ms = menuServiceImpl.getOfOrderBySort(m, "ASC", "priority"); 
		JSONArray jsonArray = new JSONArray();
		for(Menu item : ms) { 
			JSONObject object = jsonToJSONObject(item); 
			object.remove("name"); 
			object.remove("id"); 
			object.remove("url"); 
			object.remove("update_time"); 
			object.remove("create_time"); 
			object.remove("relate_id"); 
			object.remove("msg"); 
			object.remove("priority"); 
			object.put("desc", item.getName()); 
			object.put("key", item.getId());
			object.put("href", item.getUrl());
			if(item.getUrl().indexOf("####") != -1)
				object.put("dataName", item.getId()); 
			JSONArray jsonArray_ = null;
			if(item.getUrl().indexOf("####") != -1 && (jsonArray_ = init_(item.getId(), app_ids)) != null) 
				object.put("children", jsonArray_);
			if(jsonArray_ != null && jsonArray_.size() > 0) jsonArray.add(object); // 子元素为空夹也不需要了
		} 
		
		WebsiteBase base = new WebsiteBase();
		base.setId("1"); 
		base = websiteBaseServiceImpl.get(base);
//		System.out.println(base);
		
		JSONObject resultObj = new JSONObject();
		resultObj.put("responseCode", "success");
		resultObj.put("responseMsg", "初始化成功！");
		resultObj.put("data", jsonArray);
		resultObj.put("spread", base.getSpread());
		resultObj.put("desc", base.getName());
		resultObj.put("href", base.getUrl());
//		System.out.println("初始化返回数据："+resultObj.toString());
		return resultObj;
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
