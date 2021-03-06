package com.blog.control.admin;

import java.util.ArrayList;
import java.util.List;

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
import com.blog.control.BaseControl;
import com.blog.entity.Admin;
import com.blog.entity.AdminInfor;
import com.blog.entity.Local;
import com.blog.entity.Menu;
import com.blog.entity.Role;
import com.blog.entity.Usertest;
import com.blog.entity.WebsiteBase;
import com.blog.service.AdminInforService;
import com.blog.service.MenuService;
import com.blog.service.RedisService;
import com.blog.service.RoleService;
import com.blog.service.UsertestService;
import com.blog.service.WebsiteBaseService;
import com.blog.service.impl.TUserServiceImpl;
import com.blog.util.Message;
import com.blog.util.SnowFlakeGenerator;

/**
 * 
 * @author cjw	
 *
 */
@RequestMapping("/admin/main")
@Controller
public class MainControl extends BaseControl{
	//import org.apache.log4j.Logger;
	private static Logger log = Logger.getLogger(MainControl.class); // 日志对象
	
	@Autowired
	MenuService menuServiceImpl;
	
	@Autowired
	WebsiteBaseService websiteBaseServiceImpl; 
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	private AdminInforService adminInforServiceImpl;

	@Autowired
	private UsertestService usertestServiceImpl;
	
	@Autowired
	private TUserServiceImpl tUserServiceImpl;
	
	@Autowired
	private RoleService roleServiceImpl;
	
	@RequestMapping("/relogin.chtml")
	public String relogin(){
		return "admin/relogin";
	}
	@RequestMapping("/error.chtml")
	public String error(){
		return "admin/error";
	}
	@RequestMapping("/accessDenied.chtml")
	public String accessDenied(){
		return "common/accessDenied";
	}
	// 返回 页面 
	@RequestMapping("/listview.chtml")
	public String listview1(String token, HttpServletRequest request, String agentno, ModelMap model) throws Exception{
		log.info("listview.chtml");
		
		// 测试缓存
//		Usertest u = new Usertest();
//		u.setId(String.valueOf(new SnowFlakeGenerator(1,1).nextId()));
//		u.setName("xiaoming");
//		usertestServiceImpl.save(u);
//		log.info("获取："+usertestServiceImpl.getByID("384778304613388281"));
//		log.info("获取："+usertestServiceImpl.getByID(u.getId()));
		
		/** 语言切换按钮 */
		String[] languages = ((WebsiteBase) redisService.get(Constants.MANAGER_SYS_BASE)).getLanguages().split(",");
		List<Local> locals = new ArrayList<>();
		String currentLang = (String) redisService.get(Constants.LANGUAGE_CURRENT);
		for(String lang : languages){
			log.info(lang);
			String[] arr = lang.split("-");
			if(!currentLang.equals(arr[0])){
				locals.add(new Local(arr[0], getText(arr[1])));
			}
		}
		request.setAttribute("locals", locals);
		request.setAttribute("token", token);
		/** 语言切换按钮 */
		
//		Admin a = (Admin) request.getSession().getAttribute(Constants.USER_CONTEXT);
		Admin a = (Admin) redisService.get(token);
		if(a == null) return "admin/login";
		AdminInfor ai = adminInforServiceImpl.get(singleOfEqString("admin_id", a.getId()));
		model.addAttribute("name", ai.getName_());
		
		try {
			log.info(getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		异常测试 
		request.setAttribute(Constant.MESSAGE_ERROR_CODE, "0001");
		int num = 5/0;
		*/
//		log.info(tUserServiceImpl.getUserAndRole("123456").getRole().getName());
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
	protected JSONArray init_(String id, List<String> app_ids){
		JSONArray jsonArray = null;
		Menu menu = new Menu();
		menu.setRelate_id(id); 
		List<Menu> ms = menuServiceImpl.getBySort(menu, "ASC", "priority");
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
	 * @throws Exception 
	 */
	@RequestMapping("init.do")
	@ResponseBody
	public Message init(String token, HttpServletRequest request, ModelMap model) {
		
		try{
			// 用户根据权限获取 appid集合
//			Admin a = (Admin) request.getSession().getAttribute(Constants.USER_CONTEXT);
			Admin a = (Admin) redisService.get(token);
			AdminInfor adminInfor = adminInforServiceImpl.get(singleOfEqString("admin_id", a.getId()));
			List<Role> roles = roleServiceImpl.gets(singleOfEqString("role_id", adminInfor.getRole_id()));
			List<String> app_ids = new ArrayList<>();
			for(Role role : roles) {
				app_ids.add(role.getApp_id());
			}
			
			// 收集菜单信息
			Menu m = new Menu();
			m.setRelate_id("");
			List<Menu> ms = menuServiceImpl.getBySort(m, "ASC", "priority"); 
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
			
			model.addAttribute("sitename", base.getSitename());
			model.addAttribute("index_url", base.getIndex_url());
			
			JSONObject resultObj = new JSONObject();
			resultObj.put("responseCode", "success");
			resultObj.put("responseMsg", "初始化成功！");
			resultObj.put("data", jsonArray);
			resultObj.put("spread", base.getSpread());
			resultObj.put("desc", base.getSitename());
			resultObj.put("href", base.getIndex_url());
//			log.info("初始化返回数据："+resultObj.toString());
			return Message.success("初始化成功", resultObj);
		}catch(Exception e ){
			return Message.error("服务器异常："+e.getMessage());
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
