package com.blog.control.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.SpelEvaluationException;
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
import com.blog.entity.ATag;
import com.blog.entity.Article;
import com.blog.entity.ArticleContent;
import com.blog.entity.TagBrige;
import com.blog.service.ATagService;
import com.blog.service.ArticleContentService;
import com.blog.service.ArticleService;
import com.blog.service.TagBrigeService;
import com.blog.test.TempJava;
import com.blog.util.ActionUtil;
import com.blog.util.Message;
import com.blog.util.SnowFlakeGenerator;
import com.blog.util.sql.EqAdapter;

@Controller
// 数据字典
@RequestMapping("/admin/article")
public class ArticleControl extends BaseControl{
	private static Logger log = Logger.getLogger(ArticleControl.class); // 日志对象
	
	@Autowired
	private ArticleService articleServiceImpl;
	@Autowired
	private ArticleContentService articleContentServiceImpl;
	@Autowired
	private ATagService aTagServiceImpl;
	@Autowired
	private TagBrigeService tagBrigeServiceImpl;
	
	private static boolean fristLine = true;
	
	// 返回 页面 
	@RequestMapping("/listview.chtml") 
	public String listview1(HttpServletRequest request, String agentno, ModelMap model){
		return "admin/article/list";
	}
	
	// 返回 页面 
	@RequestMapping("/edit_content.chtml") 
	public String edit_content(HttpServletRequest request, String id, ModelMap model) throws Exception{
		fristLine = false;
		Article a = articleServiceImpl.get(singleOfEqString("id", id)); 
		ArticleContent cnt = articleContentServiceImpl.get(singleOfEqString("a_id", id));
		model.addAttribute("mark_code", cnt.getContent());
		model.addAttribute("name", a.getName());
		model.addAttribute("id", id);
		return "admin/article/edit_content";
	}
	@RequestMapping("/save_or_update.chtml") 
	public String save_or_update(HttpServletRequest request, String type, String id, ModelMap model){
		// 角色集供选择
		// 添加 查找所有页面传入 
		List<ATag> list = aTagServiceImpl.getAll();
			model.addAttribute("tags", list);
		if("0".equals(type)){
			model.addAttribute("type", true);
		}else if ("1".equals(type)){
			// 修改 查找已授权的页面传入 获取appid
			Article t = new Article();
			t.setId(id);
			List<TagBrige> list_ = tagBrigeServiceImpl.gets(singleOfEqString("a_id", id));
			model.addAttribute("tagsed", list_);
			model.addAttribute("type", false );
		}		
		return "admin/article/save_or_update";
	} 
	
	// 添加
	@RequestMapping("add.do")
	@ResponseBody
	public Object add(Article t, HttpServletRequest request) throws IOException{
		System.out.println("添加接收参数："+t);
		try{
			t.setBrowse_num("0");
			t.setChat_num("0");
			t.setLike_num("0");
			t.setCreate_time(getNowTime());
			t.setUpdate_time(t.getCreate_time());
			t.setPit_url("pho.jpg");
			
			ArticleContent c = new ArticleContent();
			c.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
			c.setA_id(t.getId());
			articleContentServiceImpl.insert(c);
			
			// 获取check表单值
			StringBuilder tags = new StringBuilder();
			Map<String, String> params = getRequestParameterMap(request); System.out.println(params);
			TagBrige tagBrige;
			for(Map.Entry<String, String> item : params.entrySet()){	// 处理关联标签
				if("on".equals(item.getValue())) {// 符合check格式的 value = on
					String key = item.getKey();
					
					// 创建标签关联项
					tagBrige = new TagBrige();
					tagBrige.setA_id(t.getId());
					tagBrige.setT_id(key.substring(0, key.indexOf("|")));
					
					tags.append(key.substring(key.indexOf("|")+1)).append(",");
					try{
						tagBrigeServiceImpl.insert(tagBrige);
					}catch(SpelEvaluationException e){
						log.info(e.getMessage());
					}
					// 以字符串和逗号为分隔符保存关联标签名
				}
			}			
			// 添加文章表 添加内容表数据
			t.setTags(tags.toString());
			articleServiceImpl.insert(t);
			return com.blog.util.Message.success("请求成功", null);
		}catch(Exception e){
			e.printStackTrace();
			return Message.error("请求失败，"+e.getMessage(), null);
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
		// 
		try {
			String[] ids = request.getParameter("ids").split(",");
			StringBuffer sb = new StringBuffer();
			StringBuffer sb1 = new StringBuffer();
			
			for(String id : ids) {
				sb.append("id = ").append("'"+id+"'").append(" OR ");
				sb.append("a_id = ").append("'"+id+"'").append(" OR ");
			}
			if(ids.length > 0) {
				sb.delete(sb.length()-4, sb.length());
				sb1.delete(sb1.length()-4, sb1.length());
				articleServiceImpl.delete(sb.toString());
				articleContentServiceImpl.delete(sb1.toString());
			}
			return Message.success("请求成功", null);
		}catch(Exception e) {
			e.printStackTrace();
			return Message.error("请求失败，"+e.getMessage(), null);
		}		 
	}
	
	/**
	 * 修改
	 * @param menu
	 * @param spread
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("editcontent.do")
	@ResponseBody
	public Object editcontent(String id, String mark_code, HttpServletRequest request) throws Exception{
		try {
			System.out.println("editcontent "+ id);
			
			ArticleContent c = articleContentServiceImpl.get(singleOfEqString("a_id", id));
			c.setContent(mark_code);
			articleContentServiceImpl.update(c, singleOfEqString("id", c.getId()));
//			Article a =  articleServiceImpl.get(singleMarkOfEq("id", id));
//			File file = new File(ArticleControl.class.getResource("/").getPath().substring(1)+"config/mark/"+a.getMark_url()+".txt");
//			CharStreamImpl c = new CharStreamImpl(file);
//			c.write(mark_code);
//			c.close();
			System.out.println("结束");
			return Message.success("请求成功");
		} catch (RuntimeException e) {
			e.printStackTrace();
			return Message.error("请求失败，"+e.getMessage());
		}
	}
	
	
	@RequestMapping("update.do")
	@ResponseBody
	public Object update(Article t, HttpServletRequest request) throws IOException{
		try {
			System.out.println("修改接收参数："+t); 
			// 根据admin ID 对账号和进行修改 根据id 对adminInfor信息进行修改
			t.setUpdate_time(getNowTime());
			StringBuilder tags = new StringBuilder(t.getTags());
			
			
			Map<String, String> params = getRequestParameterMap(request);
			List<TagBrige> list_ = tagBrigeServiceImpl.gets(singleOfEqString("a_id", t.getId()));
			boolean isContain;
			for(Map.Entry<String, String> item : params.entrySet()){	// 拼接标签id
				if("on".equals(item.getValue())) {
					isContain = false;
					String key = item.getKey();
					for(TagBrige tag : list_){
						if(tag.getT_id().equals(key.substring(0, key.indexOf("|")))){
							isContain = true;
							break;
						}
					}
					if(!isContain){
						TagBrige tagBrige = new TagBrige();
						tagBrige.setA_id(t.getId());
						tagBrige.setT_id(key.substring(0, key.indexOf("|")));
						
						tags.append(key.substring(key.indexOf("|")+1)).append(",");
						try{
							tagBrigeServiceImpl.insert(tagBrige);
						}catch(SpelEvaluationException e){
							log.info(e.getMessage());
						}
					}
				}
			}
			for(TagBrige tag : list_){
				isContain = false;
				String key = "";
				for(Map.Entry<String, String> item : params.entrySet()){	// 拼接标签id
					key = item.getKey();
					if("on".equals(item.getValue()) 
							&& tag.getT_id().equals(key.substring(0, key.indexOf("|")))) {
						isContain = true;
						break;
					}
				}
				if(!isContain){ 
					ATag atag = aTagServiceImpl.get(singleOfEqString("id", tag.getT_id()));
					int index = tags.indexOf(atag.getName());
					if(index != -1) tags.delete(index, index+atag.getName().length()+1);
					tagBrigeServiceImpl.delete(singleOfEqString("t_id", tag.getT_id()));
				}
			}	
			t.setTags(tags.toString());
			articleServiceImpl.update(t, singleOfEqString("id", t.getId()));
			
			return Message.success("请求成功");
		}catch(Exception e) {
			e.printStackTrace();
			return Message.error("请求失败，"+e.getMessage(), null);
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
			String table = "article";// 表  
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
			List<Article> list = articleServiceImpl.find(sql);
			
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
				count = articleServiceImpl.count(eq.toString());
			}
			else
				count = articleServiceImpl.count();
			
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
	
	public String srcPath(String path){
		return TempJava.class.getResource("/").getPath().substring(1).replace("build/classes", "src")+path;
	}
	
}