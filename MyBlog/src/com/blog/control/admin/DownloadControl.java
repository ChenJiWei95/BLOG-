package com.blog.control.admin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.blog.control.BaseControl;
import com.blog.entity.FileUpAndDown;
import com.blog.entity.WebsiteBase;
import com.blog.service.FileUpAndDownService;
import com.blog.service.WebsiteBaseService;
import com.blog.util.Message;
import com.blog.util.SnowFlakeGenerator;
import com.blog.util.UploadAndDownload;

@Controller
/**
 * <b>图片上传下载</b>
 * <p>
 * 描述:<br>
 * 
 * @author 威 
 * <br>2020年5月28日 上午5:55:49 
 * @see
 * @since 1.0
 */
@RequestMapping("/admin/down")
public class DownloadControl extends BaseControl{
	private static Logger log = Logger.getLogger(DownloadControl.class); // 日志对象
	
	@Autowired
	private WebsiteBaseService websiteBaseServiceImpl;
	
	@Autowired
	private FileUpAndDownService fileUpAndDownServiceImpl;
	
	@RequestMapping("download.do")
	@ResponseBody
	public void down(HttpServletRequest request, 
			HttpServletResponse response, 
			String fileName) throws IOException{ 
		
		try{
			WebsiteBase w = websiteBaseServiceImpl.get(singleOfEqString("id", "1"));
			if(w == null 
				|| w.getUpload() == null 
				|| "".equals(w.getUpload().trim())) 
				throw new NullPointerException("配置未找到");
			
//			String filePath = request.getSession().getServletContext().getRealPath("/upload")+"/"+fileName;
			String filePath = w.getUpload()+fileName;
			 
			UploadAndDownload.downloadFile(request, response, fileName, filePath);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	// 返回 页面 
	@RequestMapping("/listview.chtml") 
	public String listview1(HttpServletRequest request, String agentno, ModelMap model){
		
		List<FileUpAndDown> fileUpAndDowns = fileUpAndDownServiceImpl.getAll();
		request.getSession().setAttribute("fileUpAndDowns", fileUpAndDowns);
		return "admin/down/list";
	}
	// 返回 页面 
	/*@RequestMapping("/save_or_update.chtml")
	public String save_or_update(HttpServletRequest request, String agentno,ModelMap model){
		// 角色集供选择
		return "admin/aimg/save_or_update";
	}*/
	 
	/**
	 * 这里用一句话描述这个方法的作用
	 * <p>	 
	 * @param file 图片file
	 * @param fileName
	 * @param request
	 * @return
	 * @throws Exception 
	 * @see
	 * @since 1.0
	 */
	@RequestMapping("/editMovieInfo.do")
	@ResponseBody
	public Object editMovieInfo1(MultipartFile file, String fileName, HttpServletRequest request) throws Exception {
		
		/*Aimg img = new Aimg();
		img.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
		img.setCreate_time(getNowTime());
		img.setName(path);
		img.setPath(path);*/
		// 生成新的文件名
		
		try {
			// fileUpAndDownServiceImpl
			if(fileName == null || "".equals(fileName)) {
				fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
			}
			
//			String name = UUID.randomUUID().toString().replace("-", "");
			// 实际名称中获取后缀
			String ends = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			
			// 查询数据库是否存在当前名称
			if(fileUpAndDownServiceImpl.get(singleOfEqString("file_name", fileName+ends)) != null)
				return Message.error(fileName+"，该名称已经存在！");
			
			//log.info("basePath:"+basePath(request)+"; filename:"+fileName+","+file.getName()+" ContentType:"+file.getContentType()+" OriginalFilename:"+file.getOriginalFilename());
			
			FileUpAndDown fileUpAndDown = new FileUpAndDown();
			fileUpAndDown.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
			fileUpAndDown.setCreate_date(getNowTime());
			fileUpAndDown.setFile_name(fileName+ends);
			fileUpAndDown.setActual_name(fileName+ends);
			fileUpAndDown.setStatus("00");
			fileUpAndDownServiceImpl.insert(fileUpAndDown);
			
			WebsiteBase w = websiteBaseServiceImpl.get(singleOfEqString("id", "1"));
			if(w == null 
				|| w.getUpload() == null 
				|| "".equals(w.getUpload().trim())) 
				return Message.error("配置未找到！");
//			String realPath = request.getSession().getServletContext().getRealPath("/upload/")+fileName+ends;
			String realPath = w.getUpload()+fileName+ends;
			File dest = new File(realPath);
			if (!dest.getParentFile().exists()) // 判断文件父目录是否存在
				dest.getParentFile().mkdir(); 
			file.transferTo(dest); // 保存文件
			
	        return Message.success("操作成功！");
		}catch(RuntimeException e) {
			e.printStackTrace();
			return Message.error("操作失败，"+e.getMessage());
		}
	}
	
	/*public String upload(MultipartFile file, String path, String fileName) throws Exception {
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
	}*/
	
	/**
	 * 删除 
	 * @param menu
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("remove.do")
	@ResponseBody
	public Object remove(FileUpAndDown t, HttpServletRequest request) throws IOException{
		
		// 判断token是否正确  删除admin 和 adminInfor
		try {
			WebsiteBase w = websiteBaseServiceImpl.get(singleOfEqString("id", "1"));
			if(w == null 
				|| w.getUpload() == null 
				|| "".equals(w.getUpload().trim())) 
				return Message.error("配置未找到！");
			
			String realPath = w.getUpload()+t.getActual_name();
			File dest = new File(realPath);
			log.info(dest.toString());
			if(dest.exists()){
				dest.delete();
			} else {
				return Message.error("删除时，文件不存在，操作失败！", null);
			}
			fileUpAndDownServiceImpl.delete(t);
			return Message.success("删除成功", null);
		}catch(Exception e) {
			return Message.success("删除失败，"+e.getMessage(), null);
		}
		
	}
	
	/**
	 * 修改
	 * @param menu
	 * @param spread
	 * @return
	 * @throws IOException
	 */
	/*@RequestMapping("update.do")
	@ResponseBody
	public Object update(Aimg t) throws IOException{ 
		try {
			log.info("修改接收参数："+t); 
			// 根据admin ID 对账号和进行修改 根据id 对adminInfor信息进行修改
			t.setUpdate_time(getNowTime());
			aimgServiceImpl.update(t, singleMarkOfEq("id", t.getId())); 
			return Message.success("请求成功", null);
		}catch(Exception e) {
			return Message.success("请求失败，"+e.getMessage(), null);
		}
	} */
	
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
		}catch(Exception e) {
			return Message.success("请求失败，"+e.getMessage(), null);
		}
	}*/
	
	
	
}
