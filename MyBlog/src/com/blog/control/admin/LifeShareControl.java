package com.blog.control.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blog.control.BaseControl;
import com.blog.entity.LifeShare;
import com.blog.service.LifeShareService;
import com.blog.util.ActionUtil;
import com.blog.util.Message;

@Controller
@RequestMapping("/admin/life")
public class LifeShareControl extends BaseControl{
	
	@Autowired
	private LifeShareService lifeShareServiceImpl;
	
	// 返回 页面 
	@RequestMapping("/listview.chtml") 
	public String listview1(ModelMap model){
		return "admin/life/list";
	}
	// 返回 页面 
	@RequestMapping("/save_or_update.chtml") 
	public String save_or_update(ModelMap model){
		// 角色集供选择
		return "admin/life/save_or_update";
	} 
	
	// 添加
	@RequestMapping("add.do")
	@ResponseBody
	public Object add(LifeShare t) throws IOException{ 
		System.out.println("添加接收参数："+t); 
		
		try{
			t.setBrow_num("0");
			t.setChat_num("0");
			t.setLike_num("0");
			t.setTime(getNowTime());
			lifeShareServiceImpl.insert(t);
			
			return com.blog.util.Message.success("请求成功", null);
		}catch(Exception e){
			e.printStackTrace();
			return com.blog.util.Message.error("请求失败，"+e.getMessage(), null);
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
			String[] ids = request.getParameter("ids").split(",");
			StringBuffer sb = new StringBuffer();
			
			for(String id : ids) {
				sb.append("id = ").append("'"+id+"'").append(" OR ");
			}
			if(ids.length > 0) {
				sb.delete(sb.length()-4, sb.length());
				lifeShareServiceImpl.delete(sb.toString());
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
	public Object update(LifeShare t) throws IOException{ 
		try {
			System.out.println("修改接收参数："+t); 
			// 根据admin ID 对账号和进行修改 根据id 对adminInfor信息进行修改
			lifeShareServiceImpl.update(t, singleOfEqString("id", t.getId())); 
			return Message.success("请求成功", null);
		}catch(DataIntegrityViolationException e) {
			return Message.success("修改失败，数据可能过长；"+e.getMessage(), null);
		} catch(RuntimeException e) {
			return Message.success("请求失败，运行异常； "+e.getMessage(), null);
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
			List<LifeShare> list = lifeShareServiceImpl.getAll();
			return Message.success("请求成功", listToJSONArray(list));
		}catch(Exception e) {
			e.printStackTrace();
			return Message.success("请求失败，"+e.getMessage(), null);
		}
	}
	
	
	
}
