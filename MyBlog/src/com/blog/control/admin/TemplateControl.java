package com.blog.control.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.blog.util.ActionUtil;
import com.blog.util.Message;
import com.blog.util.TimeUtil;
import com.sun.corba.se.spi.orbutil.fsm.Action;

@Controller
// 数据字典
@RequestMapping("/admin/tmeplate")
public class TemplateControl extends BaseControl{
	
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
		return "../../views/admin/template/list";
	}
	@RequestMapping("/detail.chtml") 
	public String detail(HttpServletRequest request, String agentno, ModelMap model){
		model.addAttribute("roles", listToJSONArray(roleServiceImpl.gets("app_id IS NULL")));
		return "../../views/admin/template/listview";
	}
	// 返回 页面 
	@RequestMapping("/save_or_update.chtml") 
	public String save_or_update(HttpServletRequest request, String agentno,ModelMap model){
		// 角色集供选择
		model.addAttribute("roles", listToJSONArray(roleServiceImpl.gets("app_id IS NULL")));
		return "../../views/admin/template/save_or_update";
	} 
	@RequestMapping("/search_save_or_update.chtml") 
	public String save_or_update1(HttpServletRequest request, String agentno,ModelMap model){
		// 角色集供选择
		model.addAttribute("roles", listToJSONArray(roleServiceImpl.gets("app_id IS NULL")));
		return "../../views/admin/template/search_save_or_update";
	} 
	@RequestMapping("/table_save_or_update.chtml") 
	public String save_or_update2(HttpServletRequest request, String agentno,ModelMap model){
		// 角色集供选择
		model.addAttribute("roles", listToJSONArray(roleServiceImpl.gets("app_id IS NULL")));
		return "../../views/admin/template/table_save_or_update";
	} 
	@RequestMapping("/form_save_or_update.chtml") 
	public String save_or_update3(HttpServletRequest request, String agentno,ModelMap model){
		// 角色集供选择
		model.addAttribute("roles", listToJSONArray(roleServiceImpl.gets("app_id IS NULL")));
		return "../../views/admin/template/form_save_or_update";
	} 
	
	@RequestMapping("add.do")
	@ResponseBody
	public Object add(Admin admin, AdminInfor adminI) { 
		
		try {
			return com.blog.util.Message.success("请求成功", null);
		}catch(Exception e) {
			return com.blog.util.Message.error("请求失败"+e.getMessage(), null);
		}
	}
	@RequestMapping("update.do")
	@ResponseBody
	public Object update(Admin admin, AdminInfor adminI) { 
		
		try {
			return com.blog.util.Message.success("请求成功", null);
		}catch(Exception e) {
			return com.blog.util.Message.error("请求失败"+e.getMessage(), null);
		}
	}
	@RequestMapping("remove.do")
	@ResponseBody
	public Object remove(Admin admin, AdminInfor adminI) { 
		try {
			return com.blog.util.Message.success("请求成功", null);
		}catch(Exception e) {
			return com.blog.util.Message.error("请求失败"+e.getMessage(), null);
		}
		
	}
	// 添加
	@RequestMapping("search_add.do")
	@ResponseBody
	public Object search_add(Admin admin, AdminInfor adminI) throws IOException{ 
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
	// 添加
	@RequestMapping("form_add.do")
	@ResponseBody
	public Object form_add(Admin admin, AdminInfor adminI) throws IOException{ 
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
	// 添加
	@RequestMapping("table_add.do")
	@ResponseBody
	public Object table_add(Admin admin, AdminInfor adminI) throws IOException{ 
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
	@RequestMapping("search_remove.do")
	@ResponseBody
	public Object remove1(HttpServletRequest request) throws IOException{
		
		// 判断token是否正确  删除admin 和 adminInfor
		JSONArray json = JSONObject.parseArray(ActionUtil.read(request));
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		
		for(int i = 0; i < json.size(); i++) {
			JSONObject object = json.getJSONObject(i);
			sb.append("id = ").append("'"+object.getString("id")+"'").append(" OR ");
			sb1.append("admin_id = ").append("'"+object.getString("id")+"'").append(" OR ");
		}
		if(json.size() > 0) {
			sb.delete(sb.length()-4, sb.length());
			sb1.delete(sb1.length()-4, sb1.length());
			adminServiceImpl.delete(sb.toString());
			adminInforServiceImpl.delete(sb1.toString());
			System.out.println("删除："+json.toString());
		}
		
		return Message.success("请求成功", null);
	}
	@RequestMapping("form_remove.do")
	@ResponseBody
	public Object remove2(HttpServletRequest request) throws IOException{
		
		// 判断token是否正确  删除admin 和 adminInfor
		JSONArray json = JSONObject.parseArray(ActionUtil.read(request));
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		
		for(int i = 0; i < json.size(); i++) {
			JSONObject object = json.getJSONObject(i);
			sb.append("id = ").append("'"+object.getString("id")+"'").append(" OR ");
			sb1.append("admin_id = ").append("'"+object.getString("id")+"'").append(" OR ");
		}
		if(json.size() > 0) {
			sb.delete(sb.length()-4, sb.length());
			sb1.delete(sb1.length()-4, sb1.length());
			adminServiceImpl.delete(sb.toString());
			adminInforServiceImpl.delete(sb1.toString());
			System.out.println("删除："+json.toString());
		}
		
		return Message.success("请求成功", null);
	}
	@RequestMapping("table_remove.do")
	@ResponseBody
	public Object remove3(HttpServletRequest request) throws IOException{
		
		// 判断token是否正确  删除admin 和 adminInfor
		JSONArray json = JSONObject.parseArray(ActionUtil.read(request));
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		
		for(int i = 0; i < json.size(); i++) {
			JSONObject object = json.getJSONObject(i);
			sb.append("id = ").append("'"+object.getString("id")+"'").append(" OR ");
			sb1.append("admin_id = ").append("'"+object.getString("id")+"'").append(" OR ");
		}
		if(json.size() > 0) {
			sb.delete(sb.length()-4, sb.length());
			sb1.delete(sb1.length()-4, sb1.length());
			adminServiceImpl.delete(sb.toString());
			adminInforServiceImpl.delete(sb1.toString());
			System.out.println("删除："+json.toString());
		}
		
		return Message.success("请求成功", null);
	}
	
	/**
	 * 修改
	 * @param menu
	 * @param spread
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("search_update.do")
	@ResponseBody
	public Object update1(Admin admin, AdminInfor aInfor) throws IOException{ 
		System.out.println("修改接收参数："+admin + aInfor); 
		// 根据admin ID 对账号和进行修改 根据id 对adminInfor信息进行修改
		adminServiceImpl.update(admin, "id = "+admin.getId());
		aInfor.setId(null);
		aInfor.setUpdate_time(getNowTime());
		adminInforServiceImpl.update(aInfor, "admin_id = "+admin.getId());

		return Message.success("请求成功！", null);
	} 
	@RequestMapping("form_update.do")
	@ResponseBody
	public Object update2(Admin admin, AdminInfor aInfor) throws IOException{ 
		System.out.println("修改接收参数："+admin + aInfor); 
		// 根据admin ID 对账号和进行修改 根据id 对adminInfor信息进行修改
		adminServiceImpl.update(admin, "id = "+admin.getId());
		aInfor.setId(null);
		aInfor.setUpdate_time(getNowTime());
		adminInforServiceImpl.update(aInfor, "admin_id = "+admin.getId());

		return Message.success("请求成功！", null);
	} 
	@RequestMapping("table_update.do")
	@ResponseBody
	public Object update3(Admin admin, AdminInfor aInfor) throws IOException{ 
		System.out.println("修改接收参数："+admin + aInfor); 
		// 根据admin ID 对账号和进行修改 根据id 对adminInfor信息进行修改
		adminServiceImpl.update(admin, "id = "+admin.getId());
		aInfor.setId(null);
		aInfor.setUpdate_time(getNowTime());
		adminInforServiceImpl.update(aInfor, "admin_id = "+admin.getId());

		return Message.success("请求成功！", null);
	} 
	
	/**
	 * 初始化
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("table_list.do")
	@ResponseBody
	public Object init1() throws IOException{
		JSONArray jsonArray = new JSONArray();
		List<Admin> list = adminServiceImpl.gets(null, "id", "username", "state");
		for(Admin item : list){
			AdminInfor aInfor = adminInforServiceImpl.get("admin_id = "+item.getId());
			Role role = roleServiceImpl.getForColum("id = "+aInfor.getRole_id(), "name");
			JSONObject jsonObject = jsonToJSONObject(aInfor); 
			jsonObject.put("id", item.getId());
			jsonObject.put("username", item.getUsername());
			jsonObject.put("state", item.getState());
			jsonObject.put("role_id", aInfor.getRole_id());
			jsonObject.put("role_name", role.getName());
			jsonArray.add(jsonObject);
		}
		System.out.println("初始化数据："+jsonArray);
		return Message.success("请求成功", jsonArray);
	}
	@RequestMapping("form_list.do")
	@ResponseBody
	public Object init2() throws IOException{
		JSONArray jsonArray = new JSONArray();
		List<Admin> list = adminServiceImpl.gets(null, "id", "username", "state");
		for(Admin item : list){
			AdminInfor aInfor = adminInforServiceImpl.get("admin_id = "+item.getId());
			Role role = roleServiceImpl.getForColum("id = "+aInfor.getRole_id(), "name");
			JSONObject jsonObject = jsonToJSONObject(aInfor); 
			jsonObject.put("id", item.getId());
			jsonObject.put("username", item.getUsername());
			jsonObject.put("state", item.getState());
			jsonObject.put("role_id", aInfor.getRole_id());
			jsonObject.put("role_name", role.getName());
			jsonArray.add(jsonObject);
		}
		System.out.println("初始化数据："+jsonArray);
		return Message.success("请求成功", jsonArray);
	}
	@RequestMapping("search_list.do")
	@ResponseBody
	public Object init3() throws IOException{
		JSONArray jsonArray = new JSONArray();
		List<Admin> list = adminServiceImpl.gets(null, "id", "username", "state");
		for(Admin item : list){
			AdminInfor aInfor = adminInforServiceImpl.get("admin_id = "+item.getId());
			Role role = roleServiceImpl.getForColum("id = "+aInfor.getRole_id(), "name");
			JSONObject jsonObject = jsonToJSONObject(aInfor); 
			jsonObject.put("id", item.getId());
			jsonObject.put("username", item.getUsername());
			jsonObject.put("state", item.getState());
			jsonObject.put("role_id", aInfor.getRole_id());
			jsonObject.put("role_name", role.getName());
			jsonArray.add(jsonObject);
		}
		System.out.println("初始化数据："+jsonArray);
		return Message.success("请求成功", jsonArray);
	}
	
	
	
}
