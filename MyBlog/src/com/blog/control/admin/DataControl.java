package com.blog.control.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.blog.entity.Data;
import com.blog.service.DataService;
import com.blog.util.ActionUtil;
import com.blog.util.Message;
import com.blog.util.sql.EqAdapter;

@Controller
// 数据字典
@RequestMapping("/admin/data")
public class DataControl extends BaseControl{
	
	@Autowired
	private DataService dataServiceImpl;
	
	// 返回 页面 
	@RequestMapping("/listview.chtml") 
	public String listview1(HttpServletRequest request, String agentno, ModelMap model){
		return "admin/data/list";
	}
	// 返回 页面 
	@RequestMapping("/save_or_update.chtml") 
	public String save_or_update(HttpServletRequest request, String agentno,ModelMap model){
		// 角色集供选择
		return "admin/data/save_or_update";
	} 
	
	// 添加
	@RequestMapping("add.do")
	@ResponseBody
	public Object add(Data t) throws IOException{ 
		System.out.println("添加接收参数："+t); 
		
		try{
			t.setCreate_time(getNowTime());
			dataServiceImpl.insert(t);
			
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
	@RequestMapping("remove.do")
	@ResponseBody
	public Object remove(HttpServletRequest request) throws IOException{
		
		// 判断token是否正确  删除admin 和 adminInfor
		try {
			JSONArray json = JSONObject.parseArray(ActionUtil.read(request));
			StringBuffer sb = new StringBuffer();
			
			for(int i = 0; i < json.size(); i++) {
				JSONObject object = json.getJSONObject(i);
				sb.append("id = ").append("'"+object.getString("id")+"'").append(" OR ");
			}
			if(json.size() > 0) {
				sb.delete(sb.length()-4, sb.length());
				dataServiceImpl.delete(sb.toString());
			}
			return Message.success("请求成功", null);
		}catch(Exception e) {
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
	public Object update(Data t) throws IOException{ 
		try {
			System.out.println("修改接收参数："+t); 
			// 根据admin ID 对账号和进行修改 根据id 对adminInfor信息进行修改
			t.setUpdate_time(getNowTime());
			dataServiceImpl.update(t, singleOfEqString("id", t.getId())); 
			return Message.success("请求成功", null);
		}catch(Exception e) {
			return Message.success("请求失败，"+e.getMessage(), null);
		}
	} 
	
	/**
	 * 根据条件分页获取数据
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("list.do")
	@ResponseBody
	public Object list(Page page, HttpServletRequest request) throws IOException{
		try { 
			String table = "data";// 表  
			int limit = page.getLimit();
			int pageNum = page.getPage();
			// log.info(limit+ ","+pageNum);
			QueryHelper queryHelper = new QueryHelper(); 
			queryHelper.paramBind(request, page);	// 获取前台参数
			page.addOrder(Order.desc("create_time"));// 排序 
			// 自定义查询语句拼接 前台可以任意传递参数 并且参数自带条件语义
			// 进行分页
			String sql = "SELECT * FROM "+table+" "+
					queryHelper.buildAllQuery(page)+ 
					" "+EqAdapter.SQL_LIMIT + (pageNum-1)*limit + "," + limit;
			List<Data> list = dataServiceImpl.find(sql);
			
			Integer count = 0;
			if(page.getFilters().size() > 0) {
				// 拼接查询数量的条件
				StringBuilder eq = new StringBuilder();
				List<Filter> fs = page.getFilters();
				for(Filter item : fs) {
					String prefix = "'",subfix = "'";
					if(item.getQueryOperator().indexOf("like") >= 0){
						prefix = "'%";
						subfix = "%'";
					}
					eq.append(item.getProperty())
					.append(item.getQueryOperator())
					.append(prefix+item.getValue()+subfix);
					eq.append(" AND ");
				}
				if(fs.size()>0)eq.delete(eq.length()-4, eq.length());// 删除多余
				count = dataServiceImpl.count(eq.toString());
			}
			else
				count = dataServiceImpl.count();
			
			// 构建返回数据
			page.setData(list);
			page.setMsg("ok");
			page.setCount(count);
			page.setCode("0"); 
			return page;
		}catch(Exception e) {
			return com.blog.util.Message.success("请求失败", null);
		}
	
	}
	
	
}
