package com.blog.control.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.blog.util.Message;
import com.blog.util.TimeUtil;

@Controller
// 数据字典
@RequestMapping("/admin/administrators")
public class AdministratorsControl extends BaseControl{
	
	@Autowired
	private MenuService menuServiceImpl;
	
	@Autowired
	private RoleService roleServiceImpl;
	
	@Autowired
	private AdminService adminServiceImpl;
	
	@Autowired
	private AdminInforService adminInforServiceImpl;
	
	// 返回 页面 
	@RequestMapping("/listview.chtml") 
	public String listview1(HttpServletRequest request, String agentno, ModelMap model){
		model.addAttribute("roles", listToJSONArray(roleServiceImpl.gets("app_id IS NULL")));
		return "../../views/admin/administrators/list";
	}
	// 返回 页面 
	@RequestMapping("/save_or_update.chtml") 
	public String save_or_update(HttpServletRequest request, String agentno,ModelMap model){
		// 角色集供选择
		model.addAttribute("roles", listToJSONArray(roleServiceImpl.gets("app_id IS NULL")));
		return "../../views/admin/administrators/save_or_update";
	} 
	
	// 添加
	@RequestMapping("add.do")
	@ResponseBody
	public Object add(Admin admin, AdminInfor adminI) throws IOException{ 
		System.out.println("添加接收参数："+admin + adminI); 
		
		// 保存admin账号 密码默认    保存admin信息
		try{
			admin.setId(TimeUtil.randomId());
			admin.setLogin_count(0);
			admin.setPassword("12345678");
			adminServiceImpl.insert(admin);
			
			adminI.setCreate_time(getNowTime());
			adminI.setId(TimeUtil.randomId());
			adminI.setName_(adminI.getName());
			adminI.setAdmin_id(admin.getId());
			adminI.setRole_id("-1".equals(adminI.getRole_id()) ? null : adminI.getRole_id());
			adminInforServiceImpl.insert(adminI);
			 
			return com.blog.util.Message.success("请求成功", null);
		}catch(Exception e){
			return com.blog.util.Message.error("请求失败"+e.getMessage(), null);
		}
	}
	
	/**
	 * 递归 删除关联菜单
	 * @param id
	 */
	protected void remove_(String id){
		// 递归删除关联
		Menu m = new Menu();
		m.setRelate_id(id);
		List<Menu> list = menuServiceImpl.gets(m);
		Map<String, Object> eq = null;
		if(list != null && list.size() > 0){
			eq = new HashMap<>(1);
			for(Menu item : list){
				remove_(item.getId());
				eq.put("id", item.getId());
				menuServiceImpl.delete(eq);
			}
		}
		list = null;		
	}
	
	/**
	 * 删除 
	 * @param menu
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("remove.do")
	@ResponseBody
	public Object remove(Menu menu) throws IOException{
		System.out.println("删除："+menu.toString());
		
//		remove_(menu.getId());
		// 删除主要对象  
		menuServiceImpl.delete(menu);
		
		JSONObject object = new JSONObject();
		object.put("result", "success");
		return object;
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
	public Object update(Menu menu, String spread) throws IOException{ 
		System.out.println("修改接收参数："+menu.toString()+" spread="+spread); 
		  
		menu.setUpdate_time(getNowTime()); 
		menu.setPriority(menu.getPriority() == null || "".equals(menu.getPriority()) ? "5" : menu.getPriority());
		Map<String, Object> eq = new HashMap<>();
		eq.put("id", menu.getId());
		menuServiceImpl.update(menu, eq);
		
		WebsiteBase base = new WebsiteBase();
		base.setSpread(spread);
		Map<String, Object> eq_ = new HashMap<>(1);
		eq_.put("id", "1");
//		websiteBaseServiceImpl.update(base, eq_);
		
		JSONObject json = jsonToJSONObject(menu);
		json.remove("name");
		json.put("label", menu.getName());
		json.put("isTab", menu.getUrl().indexOf("####") == -1 ? false : true); 
		return json;
	} 
	
	/**
	 * 初始化
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("list.do")
	@ResponseBody
	public Object init() throws IOException{
		JSONArray jsonArray = new JSONArray();
		List<Admin> list = adminServiceImpl.gets(null, "id", "username", "state");
		for(Admin item : list){
			AdminInfor aInfor = adminInforServiceImpl.get("admin_id = "+item.getId());
			Role role = roleServiceImpl.getForColum("id = "+aInfor.getRole_id(), "name");
			JSONObject jsonObject = jsonToJSONObject(aInfor); 
			jsonObject.put("id", item.getId());
			jsonObject.put("username", item.getUsername());
			jsonObject.put("state", item.getState());
			jsonObject.put("role_id", aInfor.getRole_id()+"|"+role.getName());
			jsonArray.add(jsonObject);
		}
		System.out.println("初始化数据："+jsonArray);
		return Message.success("请求成功", jsonArray);
	}
	
	
	
}
