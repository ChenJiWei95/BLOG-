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

@Controller
// 数据字典
@RequestMapping("/admin/article")
public class ArticleControl extends BaseControl{
	
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
	public String edit_content(HttpServletRequest request, String id, ModelMap model){
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
			t.setCreate_time(getNowTime());
//			String path = String.valueOf(new SnowFlakeGenerator(2, 2).nextId());
			//D:\wokespace1\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\MyBlog\WEB-INF\classes\config\mark 
			/*File file = new File(ArticleControl.class.getResource("/").getPath().substring(1)+"config/mark/"+path+".txt");
			file.createNewFile();
			t.setMark_url(path);*/
			// 添加文章表 添加内容表数据
			articleServiceImpl.insert(t);
			ArticleContent c = new ArticleContent();
			c.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
			c.setA_id(t.getId());
			articleContentServiceImpl.insert(c);
			
			// 删除多余字段 获取标签id并创建关联标签数据
			Map<String, String> params = getRequestParameterMap(request); System.out.println(params);
			params.remove("id");
			params.remove("name");	params.remove("create_time");	params.remove("pit_url");
			params.remove("mark_url");	params.remove("update_time");	params.remove("simp_desc");
			
			TagBrige tagBrige;
			for(String item : params.keySet()) {
				tagBrige = new TagBrige();
				tagBrige.setA_id(t.getId());
				tagBrige.setT_id(item.substring(0, item.indexOf("|")));
				tagBrigeServiceImpl.insert(tagBrige);
			}
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
			JSONArray json = JSONObject.parseArray(ActionUtil.read(request));
			StringBuffer sb = new StringBuffer();
			StringBuffer sb1 = new StringBuffer();
			
			for(int i = 0; i < json.size(); i++) {
				JSONObject object = json.getJSONObject(i);
//				Article a = articleServiceImpl.get(singleMarkOfEq("id", object.getString("id")));
//				File file = new File(ArticleControl.class.getResource("/").getPath().substring(1)+"config/mark/"+a.getMark_url()+".txt");
//				file.delete();
				sb.append(singleOfEqString("id", object.getString("id"))).append(" OR ");
				sb1.append(singleOfEqString("a_id", object.getString("id"))).append(" OR ");
			}
			if(json.size() > 0) {
				sb.delete(sb.length()-4, sb.length());
				sb1.delete(sb.length()-4, sb.length());
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
	 * @throws IOException
	 */
	@RequestMapping("editcontent.do")
	@ResponseBody
	public Object editcontent(String id, String mark_code, HttpServletRequest request) throws IOException{
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
			articleServiceImpl.update(t, singleOfEqString("id", t.getId()));
			
			Map<String, String> params = getRequestParameterMap(request);
			params.remove("id");
			params.remove("name");	params.remove("create_time");	params.remove("pit_url");
			params.remove("mark_url");	params.remove("update_time");	params.remove("simp_desc");

			List<TagBrige> list_ = tagBrigeServiceImpl.gets(singleOfEqString("a_id", t.getId()));
			boolean isContain;
			for(String item : params.keySet()) {
				isContain = false;
				for(TagBrige tag : list_){
					if(tag.getT_id().equals(item.substring(0, item.indexOf("|")))){
						isContain = true;
						break;
					}
				}
				if(!isContain){
					TagBrige tagBrige = new TagBrige();
					tagBrige.setA_id(t.getId());
					tagBrige.setT_id(item.substring(0, item.indexOf("|")));
					tagBrigeServiceImpl.insert(tagBrige);
				}
			}
			for(TagBrige tag : list_){
				isContain = false;
				for(String item : params.keySet()) {
					if(tag.getT_id().equals(item.substring(0, item.indexOf("|")))){
						isContain = true;
						break;
					}
				}
				if(!isContain){
					tagBrigeServiceImpl.delete(singleOfEqString("t_id", tag.getT_id()));
				}
			}	
			
			return Message.success("请求成功");
		}catch(Exception e) {
			e.printStackTrace();
			return Message.error("请求失败，"+e.getMessage(), null);
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
			List<Article> list = articleServiceImpl.getAll();
			return Message.success("请求成功", listToJSONArray(list));
		}catch(Exception e) {
			e.printStackTrace();
			return Message.error("请求失败，"+e.getMessage(), null);
		}
	}
	
	public String srcPath(String path){
		return TempJava.class.getResource("/").getPath().substring(1).replace("build/classes", "src")+path;
	}
	
}