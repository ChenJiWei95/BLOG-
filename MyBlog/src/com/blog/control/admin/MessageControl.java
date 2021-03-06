package com.blog.control.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blog.Filter;
import com.blog.Order;
import com.blog.Page;
import com.blog.QueryHelper;
import com.blog.control.BaseControl;
import com.blog.entity.CMessage;
import com.blog.service.MessageService;
import com.blog.util.ActionUtil;
import com.blog.util.sql.EqAdapter;

@Controller
// 数据字典
@RequestMapping("/admin/message")
public class MessageControl extends BaseControl{
	
	@Autowired
	private MessageService messageServiceImpl;
	
	// 返回 页面 
	@RequestMapping("/listview.chtml") 
	public String listview1(HttpServletRequest request, ModelMap model){
		model.addAttribute("artCount", messageServiceImpl.count(singleOfEqString("type", "02")+" AND "+singleOfEqString("isRead", "01")));
		model.addAttribute("repCount", messageServiceImpl.count(singleOfEqString("type", "03")+" AND "+singleOfEqString("isRead", "01")));
		model.addAttribute("sysCount", messageServiceImpl.count(singleOfEqString("type", "04")+" AND "+singleOfEqString("isRead", "01")));
		
		return "admin/message/list";
	}
	
	/*@RequestMapping("artCount.do")
	@ResponseBody
	public Integer count(String type){
		return messageServiceImpl.count(singleMarkOfEq("type", type)+" AND "+singleMarkOfEq("isRead", "01"));
	}*/
	
	// 返回 页面 
	@RequestMapping("/detail.chtml") 
	public String save_or_update(String id, ModelMap model) throws Exception{
		CMessage m = new CMessage();
		m.setIsRead("00");
		messageServiceImpl.update(m, singleOfEqString("id", id));
		model.addAttribute("message", messageServiceImpl.get(singleOfEqString("id", id)));
		// 角色集供选择
		return "admin/message/detail";
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
				messageServiceImpl.delete(sb.toString());
			}
			return com.blog.util.Message.success("请求成功");
		}catch(Exception e) {
			return com.blog.util.Message.success("请求失败，"+e.getMessage(), null);
		}
		
	}
	
	@RequestMapping("ready.do")
	@ResponseBody
	public Object ready(HttpServletRequest request) throws IOException{ 
		// 判断token是否正确  删除admin 和 adminInfor
		try {
			JSONArray json = JSONObject.parseArray(ActionUtil.read(request));
			StringBuffer sb = new StringBuffer();
			
			for(int i = 0; i < json.size(); i++) {
				JSONObject object = json.getJSONObject(i);
				sb.append(singleOfEqString("id", object.getString("id"))).append(" OR ");
			}
			if(json.size() > 0) {
				sb.delete(sb.length()-4, sb.length());
				CMessage m = new CMessage();
				m.setIsRead("00");
				messageServiceImpl.update(m, sb.toString());
			}
			return com.blog.util.Message.success("请求成功");
		}catch(Exception e) {
			return com.blog.util.Message.success("请求失败，"+e.getMessage(), null);
		}		
	}
	
	@RequestMapping("readyAll.do")
	@ResponseBody
	public Object readyAll(String type) throws IOException{ 
		// 判断token是否正确  删除admin 和 adminInfor
		try {
			CMessage m = new CMessage();
			m.setIsRead("00");
			messageServiceImpl.update(m, singleOfEqString("isRead", "01") + ("00".equals(type) ? "" : " AND" + singleOfEqString("type", type)));
			return com.blog.util.Message.success("请求成功");
		}catch(Exception e) {
			return com.blog.util.Message.success("请求失败，"+e.getMessage(), null);
		}		
	}
	
	private static Logger log = Logger.getLogger(NoteControl.class); // 日志对象
	/**
	 * 自定义查询语句拼接 前台可以任意传递参数 并且参数自带条件语义
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("list.do")
	@ResponseBody
	public Object init(Page page, HttpServletRequest request) throws IOException{
		try { 
			int limit = page.getLimit();
			int pageNum = page.getPage();
			// log.info(limit+ ","+pageNum);
			QueryHelper queryHelper = new QueryHelper(); 
			queryHelper.paramBind(request, page);	// 获取前台参数
			page.addOrder(Order.desc("time"));		// 排序
			page.addOrder(Order.desc("isRead"));	// 排序
			// 自定义查询语句拼接 前台可以任意传递参数 并且参数自带条件语义
			// 进行分页
			List<CMessage> list = messageServiceImpl.find("SELECT * FROM message "+
			queryHelper.buildAllQuery(page)+ 
			" "+EqAdapter.SQL_LIMIT + (pageNum-1)*limit + "," + limit);
			
			Integer count = 0;
			if(page.getFilters().size() > 0) {
				// 拼接查询数量的条件
				StringBuilder eq = new StringBuilder();
				List<Filter> fs = page.getFilters();
				for(Filter item : fs) {
					eq.append(item.getProperty())
					.append(item.getQueryOperator())
					.append(item.getValue());
					eq.append(" AND ");
				}
				if(fs.size()>0)eq.delete(eq.length()-4, eq.length());// 删除多余
				count = messageServiceImpl.count(eq.toString());
			}
			else
				count = messageServiceImpl.count();
			
			// 构建返回数据
			page.setData(list);
			page.setMsg("ok");
			page.setCount(count);
			page.setCode("0"); 
			return page;
		}catch(Exception e) {
			return com.blog.util.Message.success("请求失败，"+e.getMessage(), null);
		}
	}
	
	/*@RequestMapping("article_list.do")
	@ResponseBody
	public Object articleList() throws IOException{
		try {
			List<CMessage> list = messageServiceImpl.getByDESC(singleOfEqString("type", "02"), "isRead");
			return com.blog.util.Message.success("请求成功", listToJSONArray(list));
		}catch(Exception e) {
			return com.blog.util.Message.success("请求失败，"+e.getMessage(), null);
		}
	}
	@RequestMapping("direct_list.do")
	@ResponseBody
	public Object directList() throws IOException{
		try {
			List<CMessage> list = messageServiceImpl.getByDESC(singleOfEqString("type", "03"), "isRead");
			return com.blog.util.Message.success("请求成功", listToJSONArray(list));
		}catch(Exception e) {
			return com.blog.util.Message.success("请求失败，"+e.getMessage(), null);
		}
	}
	@RequestMapping("sys_list.do")
	@ResponseBody
	public Object sysList() throws IOException{
		try {
			List<CMessage> list = messageServiceImpl.getByDESC(singleOfEqString("type", "04"), "isRead");
			return com.blog.util.Message.success("请求成功", listToJSONArray(list));
		}catch(Exception e) {
			return com.blog.util.Message.success("请求失败，"+e.getMessage(), null);
		}
	}*/
	
	// 添加
	/*@RequestMapping("add.do")
	@ResponseBody
	public Object add(Message t) throws IOException{ 
		log.info("添加接收参数："+t); 
		
		try{
			t.setTime(getNowTime());
			messageServiceImpl.insert(t);
			
			return com.blog.util.Message.success("请求成功", null);
		}catch(Exception e){
			return com.blog.util.Message.error("请求失败，"+e.getMessage(), null);
		}
	}*/
	
	/**
	 * 修改
	 * @param menu
	 * @param spread
	 * @return
	 * @throws IOException
	 */
	/*@RequestMapping("update.do")
	@ResponseBody
	public Object update(Message t) throws IOException{ 
		try {
			log.info("修改接收参数："+t); 
			// 根据admin ID 对账号和进行修改 根据id 对adminInfor信息进行修改
			messageServiceImpl.update(t, singleMarkOfEq("id", t.getId())); 
			return com.blog.util.Message.success("请求成功", null);
		}catch(Exception e) {
			return com.blog.util.Message.success("请求失败，"+e.getMessage(), null);
		}
	} */	
	
	
}
