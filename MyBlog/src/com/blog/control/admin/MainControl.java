package com.blog.control.admin;

import java.io.IOException;
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
import com.blog.entity.Menu;
import com.blog.entity.WebsiteBase;
import com.blog.service.MenuService;
import com.blog.service.WebsiteBaseService;

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
	
	// 返回 页面 
	@RequestMapping("/listview.chtml")
	public String listview1(HttpServletRequest request, String agentno, ModelMap model){
		System.out.println("session: "+request.getSession().getAttribute(Constant.USER_CONTEXT));
		model.addAttribute("name", "123456");
		try {
			System.out.println(getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/admin_view";
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
	protected JSONArray init_(String id){
		JSONArray jsonArray = null;
		Menu menu = new Menu();
		menu.setRelate_id(id); 
		List<Menu> ms = menuServiceImpl.getOfOrderBySort(menu, "ASC", "priority");
		if(ms != null && ms.size() > 0){
			jsonArray = new JSONArray();
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
				JSONArray jsonArray_;
				if(item.getUrl().indexOf("####") != -1 && (jsonArray_ = init_(item.getId())) != null) 
					object.put("children", jsonArray_);
				jsonArray.add(object); 
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
	public JSONObject init() throws IOException{
		
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
			JSONArray jsonArray_;
			if(item.getUrl().indexOf("####") != -1 && (jsonArray_ = init_(item.getId())) != null) 
				object.put("children", jsonArray_);
			jsonArray.add(object); 
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
