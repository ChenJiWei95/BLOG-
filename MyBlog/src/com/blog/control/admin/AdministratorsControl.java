package com.blog.control.admin;

import java.io.IOException;
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
import com.blog.Constants;
import com.blog.Filter;
import com.blog.Order;
import com.blog.Page;
import com.blog.QueryHelper;
import com.blog.control.BaseControl;
import com.blog.entity.Admin;
import com.blog.entity.AdminInfor;
import com.blog.entity.WebsiteBase;
import com.blog.enumer.Operator;
import com.blog.service.AdminInforService;
import com.blog.service.AdminService;
import com.blog.service.RoleService;
import com.blog.service.WebsiteBaseService;
import com.blog.util.ActionUtil;
import com.blog.util.Message;
import com.blog.util.TimeUtil;

@Controller
@RequestMapping("/admin/administrators")
public class AdministratorsControl extends BaseControl{
	
	@Autowired
	private RoleService roleServiceImpl;
	
	@Autowired
	private AdminService adminServiceImpl;
	
	@Autowired
	private AdminInforService adminInforServiceImpl;
	
	@Autowired
	private WebsiteBaseService websiteBaseServiceImpl;
	
	// 返回 页面 
	@RequestMapping("/listview.chtml") 
	public String listview1(HttpServletRequest request, String agentno, ModelMap model){
		model.addAttribute("roles", listToJSONArray(roleServiceImpl.gets("app_id IS NULL")));
		return "admin/administrators/list";
	}
	
	// 返回 页面 
	@RequestMapping("/save_or_update.chtml") 
	public String save_or_update(HttpServletRequest request, String agentno,ModelMap model){
		// 角色集供选择
		model.addAttribute("roles", listToJSONArray(roleServiceImpl.gets("app_id IS NULL")));
		return "admin/administrators/save_or_update";
	} 
	
	@RequestMapping("/info.chtml") 
	public String info(HttpServletRequest request, String agentno,ModelMap model) throws Exception{
		Admin a = (Admin) request.getSession().getAttribute(Constants.USER_CONTEXT);
		AdminInfor adminInfor = adminInforServiceImpl.get(singleOfEqString("admin_id", a.getId()));
		model.addAttribute("admin", adminInfor);
		Object o = new Object();
		List<Map<String, Object>> list =  adminServiceImpl.getByManyTable("b.name as `name`", 
				"`admin_infor` a, `role` b", 
				singleOfEq("a.`role_id`", "b.`id`") + " AND " + singleOfEq("a.`id`", quma2(adminInfor.getId())));
		model.addAttribute("roleName", list.get(0).get("name"));
		System.out.println(list.get(0).get("name"));
		return "admin/administrators/info";
	}
	
	@RequestMapping("/password.chtml") 
	public String password(HttpServletRequest request, ModelMap model){
		return "admin/administrators/password";
	}

	@RequestMapping("/website.chtml") 
	public String website(HttpServletRequest request, ModelMap model) throws Exception{
		WebsiteBase websiteBase = websiteBaseServiceImpl.get(singleOfEqString("id", "1"));
		model.addAttribute("website", websiteBase);
		return "admin/administrators/website";
	}
	
	@RequestMapping("/setinfo.do")
	@ResponseBody
	public Object setInfo(AdminInfor adminInfor) {
		try {
			adminInfor.setUsername(null);
			adminInforServiceImpl.update(adminInfor, singleOfEqString("id", adminInfor.getId()));
			return Message.success("信息修改成功!");
		}catch(RuntimeException e) {
			e.printStackTrace();
			return Message.error("操作失败!"+e.getMessage());
		}
	}
	
	@RequestMapping("/setpassword.do")
	@ResponseBody
	public Object setPassword(String oldPassword, String repassword, HttpServletRequest request) {
		try {
			Admin a = (Admin) request.getSession().getAttribute(Constants.USER_CONTEXT);
			if(a.getPassword().equals(oldPassword)) {
				a.setPassword(repassword);
				adminServiceImpl.update(a, singleOfEqString("id", a.getId()));
				return Message.success("密码修改成功!");
			}
			return Message.error("当前密码错误!");
		}catch(RuntimeException e) {
			e.printStackTrace();
			return Message.error("操作失败!"+e.getMessage());
		}
	}
	@RequestMapping("/setwebsite.do")
	@ResponseBody
	public Object setWebsite(WebsiteBase websiteBase) {
		try {
			websiteBaseServiceImpl.update(websiteBase, singleOfEqString("id", "1"));
			return Message.success("修改成功!");
		}catch(RuntimeException e) {
			e.printStackTrace();
			return Message.error("操作失败!"+e.getMessage());
		}
	}
	
	
	// 添加
	@RequestMapping("add.do")
	@ResponseBody
	public Object add(Admin admin, AdminInfor adminI) throws IOException{ 
		System.out.println("添加接收参数："+admin + adminI); 
		
		// 保存admin账号 密码默认    保存admin信息
		try{
			admin.setId(TimeUtil.randomId());
			admin.setLogin_count("0");
			admin.setPassword("12345678");
			adminServiceImpl.insert(admin);
			
			adminI.setCreate_time(getNowTime());
			adminI.setId(TimeUtil.randomId());
			adminI.setName_(adminI.getName());
			adminI.setUsername(admin.getUsername());
			adminI.setAdmin_id(admin.getId());
			adminI.setRole_id("-1".equals(adminI.getRole_id()) ? null : adminI.getRole_id());
			adminInforServiceImpl.insert(adminI);
			 
			return com.blog.util.Message.success("请求成功", null);
		}catch(Exception e){
			return com.blog.util.Message.error("请求失败"+e.getMessage(), null);
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
	@RequestMapping("update.do")
	@ResponseBody
	public Object update(Admin admin, AdminInfor aInfor) throws IOException{ 
		System.out.println("修改接收参数："+admin + aInfor); 
		// 根据admin ID 对账号和进行修改 根据id 对adminInfor信息进行修改
		adminServiceImpl.update(admin, "id = "+admin.getId());
		aInfor.setId(null);
		aInfor.setUpdate_time(getNowTime());
		adminInforServiceImpl.update(aInfor, "admin_id = "+admin.getId());

		return Message.success("请求成功！", null);
	} 
	
	/**
	 * 根据条件分页获取数据
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("list.do")
	@ResponseBody
	public Object list (Page page, HttpServletRequest request) throws IOException{
		try { 
			int limit = page.getLimit();
			int pageNum = page.getPage();
			Integer count = 0;
			page.addFilter(new Filter("a.id", Operator.eq, "b.admin_id", Filter.IGNORE_TYPE)); // Qu_type_eq_s
			page.addFilter(new Filter("b.role_id", Operator.eq, "c.id", Filter.IGNORE_TYPE)); // Qu_type_eq_s
			// log.info(limit+ ","+pageNum);
			QueryHelper queryHelper = new QueryHelper(); 
			// 获取前台参数
			queryHelper.paramBind(request, page);	
			queryHelper.addCloumnAlias("roleId", "role_id");
			// 该字段所传的值无效
			queryHelper.addDisableSelect("roleId", "-1");
			// 排序 
			page.addOrder(Order.desc("b.create_time"));
			// 自定义查询语句拼接 前台可以任意传递参数 并且参数自带条件语义
			// 进行分页
			String buildQueryStr;
			String sql = 
					queryHelper.buildSelect()+
					queryHelper.buildForm("admin a, admin_infor b, role c")+
					(buildQueryStr = queryHelper.buildQuery(page.getFilters()))+ 
					queryHelper.buildOrder(page.getOrders(), page.getAlias())+
					queryHelper.buildLimit(pageNum, limit);
			List<Admin> list = adminServiceImpl.find(sql);
			if(queryHelper.sizeOfFilter() > 0) {
				if(buildQueryStr.indexOf("and") != -1)
					count = adminServiceImpl.count("admin a, admin_infor b, role c "
						, buildQueryStr.substring(buildQueryStr.indexOf("and")+3));
				else count = adminServiceImpl.count();
			}
			else
				count = adminServiceImpl.count();
			
			// 因为是多表查询，需要换成前端识别的对象JSONArray
			JSONArray jsonArray = new JSONArray();
			for(Admin item : list){
				JSONObject jsonObject = jsonToJSONObject(item.getAdmin_infor()); 
				jsonObject.put("id", item.getId());
				jsonObject.put("username", item.getUsername());
				jsonObject.put("state", item.getState());
				jsonObject.put("role_id", item.getAdmin_infor().getRole_id());
				jsonObject.put("role_name", item.getRole().getName());
				jsonArray.add(jsonObject);
			} 
			// 构建返回数据
			page.setData(jsonArray);
			page.setMsg("ok");
			page.setCount(count);
			page.setCode("0"); 
			return page;
		}catch(Exception e) {
			return com.blog.util.Message.error("请求失败");
		}
	
	}
	
	/**
	 * 初始化
	 * @return
	 * @throws IOException
	 */
	/*@RequestMapping("list.do")
	@ResponseBody
	public Object init() throws IOException{
		JSONArray jsonArray = new JSONArray();
		List<Admin> list = adminServiceImpl.gets(null, "id", "username", "state");
		for(Admin item : list){
			AdminInfor aInfor = adminInforServiceImpl.get("admin_id = "+item.getId());
			Role role = roleServiceImpl.getForColum("id = "+aInfor.getRole_id(), "name");
			if(role == null) continue; 
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
	}*/
	
	
	
}
