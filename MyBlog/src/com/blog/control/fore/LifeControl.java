package com.blog.control.fore;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.Page;
import com.blog.QueryHelper;
import com.blog.control.BaseControl;
import com.blog.entity.Article;
import com.blog.entity.ArticleContent;
import com.blog.service.ATagService;
import com.blog.service.ArticleContentService;
import com.blog.service.ArticleService;
import com.blog.util.Message;
import com.blog.util.sql.EqAdapter;

@Controller()
// 数据字典
@RequestMapping("/blog/life")
public class LifeControl extends BaseControl{
	private static Logger log = Logger.getLogger(LifeControl.class); // 日志对象
	
	@Autowired
	private ATagService aTagServiceImpl;
	
	@Autowired
	private ArticleService articleServiceImpl;
	
	@Autowired
	private ArticleContentService articleContentServiceImpl;
	
	// 返回 页面 
	@RequestMapping("/show.chtml") 
	public String listview1(HttpServletRequest request, 
			ModelMap model, 
			Page page, 
			String type){
		// 拼接 上一次请求的 条件
		StringBuilder query = new StringBuilder("");
		Map<String, String> parame = getRequestParameterMap(request);
		for(Map.Entry<String, String> item : parame.entrySet()) 
			query.append(item.getKey() + "=" + item.getValue()).append("&");
		if(parame.size() > 0) query.delete(query.length()-1, query.length());// 删除多余符号
		model.addAttribute("query", query.toString()); // 拼接 上一次请求的 条件
		
		model.addAttribute("articles", getArticleOfCommon(type, parame, request, page));

		model.addAttribute("all", aTagServiceImpl.getAll());
		
		return "fore/life/list";
	}
	
	/**
	 * 模糊查询 标签查询
	 * <p>	 
	 * @return
	 * List<Article>
	 * @see
	 * @since 1.0
	 */
	public List<Article> getArticleOfCommon(String type, 
			Map<String, String> parame, 
			HttpServletRequest request, 
			Page page){
		QueryHelper queryHelper = new QueryHelper();
//		queryHelper.addCloumnAlias("createDate", "create_date"); // 前端设定为createDate 实际数据库为 create_date 
		queryHelper.paramBind(request, page);	// 获取前台参数
		
		StringBuilder tagQueryStr = new StringBuilder("");
		for(Map.Entry<String, String> item : parame.entrySet()){	// 拼接标签id
			if("on".equals(item.getValue()))
				tagQueryStr.append(singleOfEq("b.t_id", "'"+item.getKey()+"'")).append(" OR ");
		}
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT a.id,a.simp_desc,a.`name`,a.update_time,a.create_time,a.pit_url,a.tags,a.status ")
			.append("FROM article a,article_tag_brige b ");// 查询两个表
		page.setAlias("a");
		sql.append(queryHelper.buildAllQuery(page));
		if(tagQueryStr.length() > 0)// 如果有标签则进行标签查询
			sql.append(" AND a.id=b.a_id")
			.append(" AND ("+tagQueryStr.delete(tagQueryStr.length()-4, tagQueryStr.length()).toString()+")");// 
		sql.append(" ORDER BY a.create_time DESC ");
		sql.append(" "+EqAdapter.SQL_LIMIT + (page.getPage()-1)*page.getLimit() + "," + page.getLimit());
		
		return articleServiceImpl.find(sql.toString());
	}
	
	/**
	 * 初始化
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("list.do")
	@ResponseBody
	public Object init(String type, Page page, HttpServletRequest request) throws IOException{
		try {
			List<Article> resultList = getArticleOfCommon(type, 
					getRequestParameterMap(request), 
					request, 
					page);
			page.setData(resultList);
			page.setMsg(resultList.size() > 0 ? "已新增了"+resultList.size()+"条。" : "没有更多了！");
			page.setCount(0);
			page.setCode("0"); 
			return page;
		}catch(Exception e) {
			return Message.success("请求失败，"+e.getMessage(), null);
		}
	}
	
	// 添加
	/*@RequestMapping("add.do")
	@ResponseBody
	public Object add(ArticleContent t) throws IOException{ 
		System.out.println("添加接收参数："+t); 
		
		try{
			articleContentServiceImpl.insert(t);
			
			return com.blog.util.Message.success("请求成功", null);
		}catch(Exception e){
			return com.blog.util.Message.error("请求失败，"+e.getMessage(), null);
		}
	}*/
	
	/**
	 * 删除 
	 * @param menu
	 * @return
	 * @throws IOException
	 */
	/*@RequestMapping("remove.do")
	@ResponseBody
	public Object remove(HttpServletRequest request) throws IOException{
		
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
				articleContentServiceImpl.delete(sb.toString());
			}
			return Message.success("请求成功", null);
		}catch(Exception e) {
			return Message.success("请求失败，"+e.getMessage(), null);
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
	public Object update(ArticleContent t) throws IOException{ 
		try {
			System.out.println("修改接收参数："+t); 
			// 根据admin ID 对账号和进行修改 根据id 对adminInfor信息进行修改
			articleContentServiceImpl.update(t, singleOfEqString("id", t.getId())); 
			return Message.success("请求成功", null);
		}catch(Exception e) {
			return Message.success("请求失败，"+e.getMessage(), null);
		}
	} */
	
}
