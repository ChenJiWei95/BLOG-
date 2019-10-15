package com.blog.control.admin;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blog.control.BaseControl;
import com.blog.entity.TempComponent;
import com.blog.entity.TempContext;
import com.blog.entity.TempRow;
import com.blog.service.RoleService;
import com.blog.service.TempComponentService;
import com.blog.service.TempContextService;
import com.blog.service.TempRowService;
import com.blog.util.ActionUtil;
import com.blog.util.Message;
import com.blog.util.TimeUtil;

@Controller
// 数据字典
@RequestMapping("/admin/tmeplate")
public class TemplateControl extends BaseControl{
	@Autowired
	private TempContextService tempContextServiceImpl;
	@Autowired
	private TempRowService tempRowServiceImpl;
	
	@Autowired
	private TempComponentService tempComponentServiceImpl;
	
	// 打开主要页面 
	@RequestMapping("/listview.chtml") 
	public String listview1(HttpServletRequest request, String agentno, ModelMap model){
		return "admin/template/list";
	}
	// 打开详细页
	@RequestMapping("/detail.chtml") 
	public String detail(HttpServletRequest request, String id, ModelMap model){
		model.addAttribute("id", id);
		return "admin/template/listview";
	}
	// 返回 页面 
	@RequestMapping("/save_or_update.chtml") 
	public String save_or_update(HttpServletRequest request, String agentno,ModelMap model){
		 
		return "admin/template/save_or_update";
	} 
	@RequestMapping("/search_save_or_update.chtml") 
	public String save_or_update1(HttpServletRequest request, String agentno,ModelMap model){
		 
		return "admin/template/search_save_or_update";
	} 
	@RequestMapping("/table_save_or_update.chtml") 
	public String save_or_update2(HttpServletRequest request,ModelMap model){
		 
		return "admin/template/table_save_or_update";
	} 
	@RequestMapping("/form_save_or_update.chtml") 
	public String save_or_update3(HttpServletRequest request, String agentno,ModelMap model){
		 
		return "admin/template/form_save_or_update";
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
			List<TempContext> list = tempContextServiceImpl.getAll();
			return Message.success("请求成功", listToJSONArray(list));
		} catch(Exception e) {
			return Message.error("请求失败，"+e.getMessage(), null);
		}
	}
	@RequestMapping("add.do")
	@ResponseBody
	public Object add(TempContext c) { 
		
		try {
			c.setCreate_time(getNowTime());
			tempContextServiceImpl.insert(c);
			
			// list页组件初始化
			// 按钮
			TempComponent t = new TempComponent();
			t.setId(TimeUtil.randomId());
			t.setC_id(c.getId());
			t.setLabel("input");
			t.setType("03");
			t.setValue("添加");
			t.setType_date("add");
			tempComponentServiceImpl.insert(t);
			TempComponent t2 = new TempComponent();
			t2.setId(TimeUtil.randomId());
			t2.setC_id(c.getId());
			t2.setLabel("input");
			t2.setType("03");
			t2.setValue("编辑");
			t2.setType_date("edit");
			tempComponentServiceImpl.insert(t2);
			TempComponent t3 = new TempComponent();
			t3.setId(TimeUtil.randomId());
			t3.setC_id(c.getId());
			t3.setLabel("input");
			t3.setType("03");
			t3.setValue("删除");
			t3.setType_date("del");
			tempComponentServiceImpl.insert(t3);
			// 搜索
			TempComponent t4 = new TempComponent();
			t4.setId(TimeUtil.randomId());
			t4.setC_id(c.getId());
			t4.setLabel("ID");
			t4.setName("id");
			t4.setInline("01");
			t4.setType("01");
			tempComponentServiceImpl.insert(t4);
			
			// 表头项初始化
			TempRow r = new TempRow();
			r.setId(TimeUtil.randomId());
			r.setC_id(c.getId());
			r.setField("id");
			r.setTitle("ID");
			tempRowServiceImpl.insert(r);
			TempRow r_1 = new TempRow();
			r_1.setId(TimeUtil.randomId());
			r_1.setC_id(c.getId());
			r_1.setField("name");
			r_1.setTitle("名称");
			tempRowServiceImpl.insert(r_1);
			
			// 表单页初始化
			// 表单组件
			TempComponent t1_1 = new TempComponent();
			t1_1.setId(TimeUtil.randomId());
			t1_1.setC_id(c.getId());
			t1_1.setLabel("ID");
			t1_1.setName("id");
			t1_1.setHide("00");
			t1_1.setType("02");
			tempComponentServiceImpl.insert(t1_1);
			TempComponent t1_2 = new TempComponent();
			t1_2.setId(TimeUtil.randomId());
			t1_2.setC_id(c.getId());
			t1_2.setLabel("名称");
			t1_2.setName("name");
			t1_2.setHide("01");
			t1_2.setInline("01");
			t1_2.setType("02");
			tempComponentServiceImpl.insert(t1_2);
			
			// 提交按钮
			TempComponent t1_3 = new TempComponent();
			t1_3.setId(TimeUtil.randomId());
			t1_3.setC_id(c.getId());
			t1_3.setValue("添加");
			t1_3.setName("C-admin-temp-add");
			t1_3.setType("04");
			tempComponentServiceImpl.insert(t1_3);
			TempComponent t1_4 = new TempComponent();
			t1_4.setId(TimeUtil.randomId());
			t1_4.setC_id(c.getId());
			t1_4.setValue("编辑");
			t1_4.setName("C-admin-temp-update");
			t1_4.setType("04");
			tempComponentServiceImpl.insert(t1_4);
			
			return com.blog.util.Message.success("请求成功", null);
		}catch(Exception e) {
			return com.blog.util.Message.error("请求失败，"+e.getMessage(), null);
		}
	}
	@RequestMapping("update.do")
	@ResponseBody
	public Object update(TempContext c) { 
		try {
			c.setUpdate_time(getNowTime());
			String id = c.getId();
			c.setId(null);
			tempContextServiceImpl.update(c, "id = '"+ id +"'");
			return com.blog.util.Message.success("请求成功", null);
		}catch(Exception e) {
			return com.blog.util.Message.error("请求失败，"+e.getMessage(), null);
		}
	}
	@RequestMapping("remove.do")
	@ResponseBody
	public Object remove(HttpServletRequest request) {
		try {
			// 判断token是否正确  删除admin 和 adminInfor
			JSONArray json = JSONObject.parseArray(ActionUtil.read(request));
			StringBuffer sb = new StringBuffer();
			StringBuffer sb1 = new StringBuffer();
			
			for(int i = 0; i < json.size(); i++) {
				JSONObject object = json.getJSONObject(i);
				sb.append("id = ").append("'"+object.getString("id")+"'").append(" OR ");
				sb1.append("c_id = ").append("'"+object.getString("id")+"'").append(" OR ");
			}
			if(json.size() > 0) {
				sb.delete(sb.length()-4, sb.length());
				sb1.delete(sb1.length()-4, sb1.length());
				tempContextServiceImpl.delete(sb.toString());
				tempRowServiceImpl.delete(sb1.toString());	
				tempComponentServiceImpl.delete(sb1.toString());
			}
			return com.blog.util.Message.success("请求成功", null);
		}catch(Exception e) {
			return com.blog.util.Message.error("请求失败"+e.getMessage(), null);
		}
	}
	// 添加
	@RequestMapping({"search_add.do", "form_add.do"})
	@ResponseBody
	public Object search_add(TempComponent c) throws IOException{ 
		System.out.println("添加接收参数："+c); 
		// 保存admin账号 密码默认    保存admin信息
		try{
			tempComponentServiceImpl.insert(c);
			return com.blog.util.Message.success("请求成功", null);
		}catch(Exception e){
			return com.blog.util.Message.error("请求失败"+e.getMessage(), null);
		}
	} 
	// 添加
	@RequestMapping("table_add.do")
	@ResponseBody
	public Object table_add(TempRow t) throws IOException{ 
		System.out.println("添加接收参数："+t); 
		
		// 保存admin账号 密码默认    保存admin信息
		try{ 
			tempRowServiceImpl.insert(t);
			return com.blog.util.Message.success("请求成功", null);
		}catch(Exception e){
			return com.blog.util.Message.error("请求失败，"+e.getMessage(), null);
		}
	}	 
	
	/**
	 * 删除 
	 * @param menu
	 * @return
	 * @throws IOException
	 */
	@RequestMapping({"search_remove.do", "form_remove.do"})
	@ResponseBody
	public Object remove1(HttpServletRequest request) {
		try {
			JSONArray json = JSONObject.parseArray(ActionUtil.read(request));
			StringBuffer sb = new StringBuffer();
			
			for(int i = 0; i < json.size(); i++) {
				JSONObject object = json.getJSONObject(i);
				sb.append(singleMarkOfEq("id", object.getString("id")) + "OR ");
			}
			if(json.size() > 0) {
				sb.delete(sb.length()-3, sb.length());
				tempComponentServiceImpl.delete(sb.toString());
			}
			return Message.success("请求成功", null);
		} catch(Exception e) {
			return Message.error("请求失败，"+e.getMessage(), null);
		}
		
	} 
	@RequestMapping("table_remove.do")
	@ResponseBody
	public Object remove3(HttpServletRequest request) {
		try {
			JSONArray json = JSONObject.parseArray(ActionUtil.read(request));
			StringBuffer sb = new StringBuffer();
			
			for(int i = 0; i < json.size(); i++) {
				JSONObject object = json.getJSONObject(i);
				sb.append(singleMarkOfEq("id", object.getString("id")) + "OR ");
			}
			if(json.size() > 0) {
				sb.delete(sb.length()-3, sb.length());
				tempRowServiceImpl.delete(sb.toString());
			}
			return Message.success("请求成功", null);
		} catch (Exception e) {
			return Message.error("请求失败，"+e.getMessage(), null);
		}
		
		
	}
	
	/**
	 * 修改
	 * @param menu
	 * @param spread
	 * @return
	 * @throws IOException
	 */
	@RequestMapping({"search_update.do", "form_update.do"})
	@ResponseBody
	public Object update1(TempComponent t) throws IOException{ 
		System.out.println("修改接收参数："+t);  
		tempComponentServiceImpl.update(t, singleMarkOfEq("id", t.getId()));
		return Message.success("请求成功！", null);
	}  
	@RequestMapping("table_update.do")
	@ResponseBody
	public Object update3(TempRow t) throws IOException{ 
		System.out.println("修改接收参数："+t); 
		tempRowServiceImpl.update(t, singleMarkOfEq("id", t.getId()));
		return Message.success("请求成功！", null);
	} 
	
	/**
	 * 初始化
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("table_list.do")
	@ResponseBody
	public Object init1(String id) throws IOException{
		 
		return Message.success("请求成功", listToJSONArray(tempRowServiceImpl.gets(singleMarkOfEq("c_id", id))));
	}
	@RequestMapping("form_list.do")
	@ResponseBody
	public Object init2(String id) throws IOException{ 
		return Message.success("请求成功", listToJSONArray(tempComponentServiceImpl.gets(singleMarkOfEq("c_id", id)+"AND "+singleMarkOfEq("type", "02")+"OR "+singleMarkOfEq("c_id", id)+"AND "+singleMarkOfEq("type", "04"))));
	}
	@RequestMapping("search_list.do")
	@ResponseBody
	public Object init3(String id) throws IOException{
		return Message.success("请求成功", listToJSONArray(tempComponentServiceImpl.gets(singleMarkOfEq("c_id", id)+"AND "+singleMarkOfEq("type", "01")+"OR "+singleMarkOfEq("c_id", id)+"AND "+singleMarkOfEq("type", "03"))));
	}
	
}
