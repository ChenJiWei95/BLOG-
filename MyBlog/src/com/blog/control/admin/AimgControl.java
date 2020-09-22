package com.blog.control.admin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blog.Order;
import com.blog.Page;
import com.blog.QueryHelper;
import com.blog.control.BaseControl;
import com.blog.entity.Admin;
import com.blog.entity.Aimg;
import com.blog.entity.WebsiteBase;
import com.blog.service.AimgService;
import com.blog.service.FileUpAndDownService;
import com.blog.service.WebsiteBaseService;
import com.blog.util.ActionUtil;
import com.blog.util.Message;
import com.blog.util.SnowFlakeGenerator;
import org.apache.log4j.Logger;
@Controller
// 数据字典
@RequestMapping("/admin/aimg")
public class AimgControl extends BaseControl{
	private static Logger log = Logger.getLogger(AimgControl.class);
	@Autowired
	private WebsiteBaseService websiteBaseServiceImpl;
	
	@Autowired
	private FileUpAndDownService fileUpAndDownServiceImpl;
	
	@Autowired
	private AimgService aimgServiceImpl;
	
	// 返回 页面 
	@RequestMapping("/listview.chtml") 
	public String listview1(HttpServletRequest request, String agentno, ModelMap model){
		return "admin/aimg/list";
	}
	// 返回 页面 
	@RequestMapping("/save_or_update.chtml") 
	public String save_or_update(HttpServletRequest request, String agentno,ModelMap model){
		// 角色集供选择
		return "admin/aimg/save_or_update";
	} 
	
	// 添加
	@RequestMapping("add.do")
	@ResponseBody
	public Object add(Aimg t) throws IOException{ 
		System.out.println("添加接收参数："+t); 
		
		try{
			t.setCreate_time(getNowTime());
			aimgServiceImpl.insert(t);
			
			return com.blog.util.Message.success("请求成功", null);
		}catch(Exception e){
			return com.blog.util.Message.error("请求失败，"+e.getMessage(), null);
		}
	}
	
	/**
	* 有file文件时
	* @param movieDto 封装了需要传递过来的参数
	* @param file 图片file
	 * @throws Exception 
	*/
	@RequestMapping("/upload.do")
	@ResponseBody
	public Object editMovieInfo1(MultipartFile file, String fileName, HttpServletRequest request) throws Exception {
		String path = UUID.randomUUID().toString().replace("-", "");
		 
		
		// 生成新的文件名
		try {
			// fileUpAndDownServiceImpl
			/*if(fileName == null || "".equals(fileName)) {
				fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
			}*/
			
//			String name = UUID.randomUUID().toString().replace("-", "");
			// 实际名称中获取后缀
			String ends = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			
			// 查询数据库是否存在当前名称
			/*if(fileUpAndDownServiceImpl.get(singleOfEqString("file_name", fileName+ends)) != null)
				return Message.error(fileName+"，该名称已经存在！");*/
			
			/*String realPath = basePath(request)+ "img/" + path + ".jpg";
			System.out.println(realPath);
			File dest = new File(realPath);
			// 判断文件父目录是否存在
			if (!dest.getParentFile().exists()) dest.getParentFile().mkdir();
			// 保存文件
			file.transferTo(dest);*/
			
			WebsiteBase w = websiteBaseServiceImpl.get(singleOfEqString("id", "1"));
			if(w == null 
				|| w.getUpload() == null 
				|| "".equals(w.getUpload().trim())) 
				return Message.error("配置未找到！");
//			String realPath = request.getSession().getServletContext().getRealPath("/upload/")+fileName+ends;
			String endPath = new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"/"+path+ends;
			String realPath = w.getUpload()+endPath;
			File dest = new File(realPath);
			if (!dest.getParentFile().exists()) // 判断文件父目录是否存在
				dest.getParentFile().mkdir(); 
			file.transferTo(dest); // 保存文件
			
			Aimg img = new Aimg();
			img.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
			img.setCreate_time(getNowTime());
			img.setName(path); 
			img.setPath("upload/"+endPath);
			aimgServiceImpl.insert(img);
	        return Message.success("操作成功！");
		}catch(RuntimeException e) {
			e.printStackTrace();
			return Message.error("操作失败，"+e.getMessage());
		}
	}
	
	public String upload(MultipartFile file, String path, String fileName) throws Exception {
		// 生成新的文件名
		String realPath = path + "/" + UUID.randomUUID().toString().replace("-", "")+fileName.substring(fileName.lastIndexOf("."));
		File dest = new File(realPath);
		// 判断文件父目录是否存在
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdir();
		}
		// 保存文件
		file.transferTo(dest);
		return dest.getName();
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
		log.info("==================");
		try {
			String[] ids = request.getParameter("ids").split(",");
			log.info(ids);
			
			StringBuffer sb = new StringBuffer();
			
			for(String id : ids) {
				sb.append("id = ").append("'"+id+"'").append(" OR ");
			}
			if(ids.length > 0) {
				sb.delete(sb.length()-4, sb.length());
				aimgServiceImpl.delete(sb.toString());	
			}
			return Message.success("请求成功", null);
		}catch(Exception e) { e.printStackTrace();
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
	@RequestMapping("update.do")
	@ResponseBody
	public Object update(Aimg t) throws IOException{ 
		try {
			System.out.println("修改接收参数："+t); 
			// 根据admin ID 对账号和进行修改 根据id 对adminInfor信息进行修改
			t.setUpdate_time(getNowTime());
			aimgServiceImpl.update(t, singleOfEqString("id", t.getId())); 
			return Message.success("请求成功", null);
		}catch(Exception e) { e.printStackTrace();
			return Message.success("请求失败，"+e.getMessage(), null);
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
		try {
			List<Aimg> list = aimgServiceImpl.getAll();
			return Message.success("请求成功", listToJSONArray(list));
		}catch(Exception e) { e.printStackTrace();
			return Message.success("请求失败，"+e.getMessage(), null);
		}
	}*/
	
	
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
			
			QueryHelper queryHelper = new QueryHelper(); 
			// 获取前台参数
			queryHelper.paramBind(request, page);
			
			page.addOrder(Order.desc("create_time"));
			
			String buildQueryStr;
			String sql = 
					queryHelper.buildSelect()+
					queryHelper.buildForm("aimg")+
					(buildQueryStr = queryHelper.buildQuery(page.getFilters()))+ 
					queryHelper.buildOrder(page.getOrders(), page.getAlias())+
					queryHelper.buildLimit(pageNum, limit);
			List<Aimg> list = aimgServiceImpl.find(sql);
			if(queryHelper.sizeOfFilter() > 0) {
				if(buildQueryStr.indexOf("and") != -1)
					count = aimgServiceImpl.count("admin a, admin_infor b, role c "
						, buildQueryStr.substring(buildQueryStr.indexOf("and")+3));
				else count = aimgServiceImpl.count();
			}
			else
				count = aimgServiceImpl.count();
			// 构建返回数据
			page.setData(list);
			page.setMsg("ok");
			page.setCount(count);
			page.setCode("0"); 
			return page;			
		}catch(Exception e) { e.printStackTrace();
			return com.blog.util.Message.error("请求失败");
		}
	}
	
	
}
