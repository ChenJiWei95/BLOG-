package com.blog.control.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blog.control.BaseControl;
import com.blog.entity.Menu;
import com.blog.entity.Role;
import com.blog.entity.RoleItem;
import com.blog.entity.WebsiteBase;
import com.blog.service.MenuService;
import com.blog.service.RoleItemService;
import com.blog.service.RoleService;
import com.blog.service.WebsiteBaseService;
import com.blog.util.ActionUtil;
import com.blog.util.Message;
import com.blog.util.TimeUtil;

@Controller
// 数据字典
@RequestMapping("/admin/role")
public class RoleControl extends BaseControl{
	
	@Autowired
	private MenuService menuServiceImpl;
	@Autowired
	private RoleService roleServiceImpl;
	@Autowired
	private RoleItemService roleItemService;
	
	@Autowired
	private WebsiteBaseService websiteBaseServiceImpl;
	
	// 返回 页面 
	@RequestMapping("/listview.chtml")
	public String listview1(ModelMap model){ 
		return "../../views/admin/role/list";
	}
	// 返回 页面 
	@Transactional
	@RequestMapping("/save_or_update.chtml") 
	public String save_or_update(String type, String id, ModelMap model){
		// 添加 查找所有页面传入 
		List<Menu> list = menuServiceImpl.gets("url != '####'");
//		for(Menu item : list) System.out.println(item);
		model.addAttribute("apps", list);
		if("0".equals(type)){
			model.addAttribute("type", true);
		}else if ("1".equals(type)){
			// 修改 查找已授权的页面传入 获取appid
			List list_ = roleServiceImpl.gets("role_id = '"+id+"'");
			model.addAttribute("roleItems", list_);
			model.addAttribute("type", false );
		}
			
		return "../../views/admin/role/save_or_update";
	}  
	
	// 添加
	@RequestMapping("add.do")
	@ResponseBody
	@Transactional
	public Object add(Role role, HttpServletRequest request) throws IOException{ 
		try{
			System.out.println(role);
			role.setCreate_time(getNowTime());
			Map<String, String> params = getRequestParameterMap(request); System.out.println(params);
			roleServiceImpl.insert(role);
			params.remove("id");  params.remove("desc"); params.remove("state");  params.remove("create_time");  params.remove("update_time");  params.remove("name"); 
			Role roleItem;
			for(String item : params.keySet()) {
				roleItem = new Role();
				roleItem.setId(TimeUtil.randomId());
				roleItem.setCreate_time(getNowTime());
				roleItem.setApp_id(item.substring(0, item.indexOf("|")));
				roleItem.setRole_id(role.getId());
				roleItem.setName(item.substring(item.indexOf("|")+1));
				roleItem.setDesc("角色授权项");
				roleServiceImpl.insert(roleItem);
			}
			// JSONObject object = jsonToJSONObject();
			return Message.success("请求成功", role);
		}catch(Exception e){
			return Message.error("请求失败："+e.getMessage(), role);
		}
	}
	
	/**
	 * 递归 删除关联菜单
	 * @param id
	 */
	protected StringBuilder remove_(String id){
		// 递归删除关联
		Menu m = new Menu();
		m.setRelate_id(id);
		List<Menu> list = menuServiceImpl.gets(m);
		StringBuilder sb = new StringBuilder();
		if(list != null && list.size() > 0){
			for(Menu item : list)
				sb.append(" AND id = " + item.getId() + remove_(item.getId()));
		}
		return sb;		
	}
	
	/**
	 * 删除 
	 * @param menu
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("remove.do")
	@ResponseBody
	@Transactional
	public Object remove(HttpServletRequest request) throws IOException{
		
		// 判断token是否正确  删除角色 
		JSONArray json = JSONObject.parseArray(ActionUtil.read(request));
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0; i < json.size(); i++) {
			JSONObject object = json.getJSONObject(i);
			sb.append("id = ").append("'"+object.getString("id")+"'").append(" OR ");
			sb.append("role_id = ").append("'"+object.getString("id")+"'").append(" OR ");
		}
		if(json.size() > 0) {
			sb.delete(sb.length()-4, sb.length());
			roleServiceImpl.delete(sb.toString());
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
	@RequestMapping("update.do")
	@ResponseBody
	@Transactional
	public Object update(Role role, HttpServletRequest request) throws IOException{ 
		try{
			Map<String, String> params = getRequestParameterMap(request);
			Map<String, Object> eq = new HashMap<>(1);
			eq.put("id", role.getId());
			role.setUpdate_time(getNowTime());
			roleServiceImpl.update(role, eq);
			params.remove("id");  params.remove("desc"); params.remove("state");  params.remove("create_time");  params.remove("update_time");  params.remove("name"); 
			Role roleItem;
			List<Role> list_ = roleServiceImpl.gets("role_id = '"+role.getId()+"'");
			boolean isContain;
			for(String item : params.keySet()) {
				isContain = false;
				for(Role role_ : list_){
					if(role_.getApp_id().equals(item.substring(0, item.indexOf("|")))){
						isContain = true;
						break;
					}
				} 
				if(!isContain){
					roleItem = new Role();
					roleItem.setId(TimeUtil.randomId());
					roleItem.setCreate_time(getNowTime());
					roleItem.setApp_id(item.substring(0, item.indexOf("|")));
					roleItem.setRole_id(role.getId());
					roleItem.setName(item.substring(item.indexOf("|")+1));
					roleItem.setDesc("角色授权项");
					roleServiceImpl.insert(roleItem);
				}
			}
			if(params.size() < list_.size()){
				for(Role role_ : list_){
					isContain = false;
					for(String item : params.keySet()) {
						if(role_.getApp_id().equals(item.substring(0, item.indexOf("|")))){
							isContain = true;
							break;
						}
					}
					if(!isContain){
						roleServiceImpl.delete("app_id = "+ role_.getApp_id());
					}
				} 
				
			}
			return Message.success("请求成功", role);
		}catch(Exception e){
			return Message.error("请求失败："+e.getMessage(), role);
		}
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
				object.put("label", item.getName()); 
				object.put("isTab", item.getUrl().indexOf("####") == -1 ? false : true); 
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
	@RequestMapping("list.do")
	@ResponseBody
	@Transactional
	public Message init() throws IOException{
		List<Role> list = null;
		try{
			list = roleServiceImpl.gets("`state` IS NOT NULL ");
		}catch(Exception e){
			return Message.error(e.getMessage(), null);
		}
		return Message.success("请求成功", listToJSONArray(list));
	}
	
	
	
}
