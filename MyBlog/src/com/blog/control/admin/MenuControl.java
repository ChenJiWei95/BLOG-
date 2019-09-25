package com.blog.control.admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
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
import com.blog.control.BaseControl;
import com.blog.entity.Menu;
import com.blog.entity.WebsiteBase;
import com.blog.service.MenuService;
import com.blog.service.WebsiteBaseService;
import com.blog.util.ActionUtil;

import sun.misc.BASE64Decoder;

@SuppressWarnings("deprecation")
@Controller
//admin/menu/editMovieInfo.do
@RequestMapping("/admin/menu")
public class MenuControl extends BaseControl{
	
	@Autowired
	private MenuService menuServiceImpl;
	
	@Autowired
	private WebsiteBaseService websiteBaseServiceImpl;
	
	// 返回 页面 
	@RequestMapping("/listview.chtml") 
	public String listview1(HttpServletRequest request, String agentno, ModelMap model){
		return "../../views/admin/menu/list";
	}
	// 返回 页面 
	@RequestMapping("/save_or_update.chtml") 
	public String save_or_update(HttpServletRequest request, String agentno,ModelMap model){
		return "../../views/admin/menu/save_or_update";
	} 
	
	/**
	* 有file文件时
	* @param movieDto 封装了需要传递过来的参数
	* @param file 图片file
	 * @throws IOException 
	 * @throws ClientProtocolException 
	*/
	@RequestMapping("/editMovieInfo.do")
	@ResponseBody
	public String editMovieInfo1(MultipartFile file) throws ClientProtocolException, IOException {
		String result = editMovieInfo(file);
		System.out.println(result);
		JSONObject result1 = new JSONObject();
        result1.put("ss", false);
        result1.put("msg", "格式不支持");
        return result1.toString(); 
	}
	
	@SuppressWarnings("deprecation")
	public static String editMovieInfo(MultipartFile file) throws ClientProtocolException, IOException { 
		CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = null;
        // HttpClient请求的相关设置，可以不用配置，用默认的参数，这里设置连接和超时时长(毫秒)
        RequestConfig config = RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000).build();
        try {
            HttpPost httppost = new HttpPost("http://v.juhe.cn/certificates/query.php");
            /*List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("key", "6866ed735f47cce6d5d7ac2b9cf05b2b"));
            params.add(new BasicNameValuePair("cardType", "17"));
            params.add(new BasicNameValuePair("pic", new String(file.getBytes(), "UTF-8")));
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));*/
            MultipartEntity reqEntity = new MultipartEntity();
//            FileBody f = new FileBody((File) file);
            ByteArrayBody bin = new ByteArrayBody(file.getBytes(), ContentType.APPLICATION_OCTET_STREAM, file.getName());
            reqEntity.addPart("pic", bin);

            StringBody key = new StringBody("6866ed735f47cce6d5d7ac2b9cf05b2b");
            StringBody cardType = new StringBody("17");

            reqEntity.addPart("key", key);
            reqEntity.addPart("cardType", cardType);
            httppost.setEntity(reqEntity);
            httppost.setConfig(config);
            // 执行网络请求并返回结果
            response = httpClient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                result = IOUtils.toString(resEntity.getContent(), "UTF-8");
            }
            EntityUtils.consume(resEntity);
        } finally {
        	if(response != null)
        		response.close();
        	if(httpClient != null)
        		httpClient.close();
        }
        // 得到的是JSON类型的数据需要第三方解析JSON的jar包来解析
        return result;	 
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
	
	@RequestMapping("/api/test.do")
	public void test(HttpServletRequest request, HttpServletResponse response) throws IOException{
		/*logger.error("servlet path:"+request.getServletPath());
		String url="http://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI(); 
		logger.error("全路径："+url);
		logger.error("路径:" + System.getProperty("catalina.home"));
		//return "index";
		*/	
		System.out.println(ActionUtil.read(request));
		ActionUtil.returnRes(response, "success");
	} 
	public String identification(String type, String baseImg, String savePath) {
		try {
			GenerateImage(baseImg, savePath);
			return post(type, new File(savePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	@SuppressWarnings("finally")
	public static boolean GenerateImage(String imgData, String imgFilePath) throws IOException { // 对字节数组字符串进行Base64解码并生成图片
		if (imgData == null) // 图像数据为空
            return false;    
		BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;     
	    try {
	    	out = new FileOutputStream(imgFilePath);
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgData);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            out.write(b);     
	    } catch (FileNotFoundException e) {
	       	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
		} finally {
	     	out.flush();
	     	out.close();
	       	return true;
	   	}
	}
	/**
	 * File类型 证件识别
	 * @param type
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String post(String type, File file) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = null;
        // HttpClient请求的相关设置，可以不用配置，用默认的参数，这里设置连接和超时时长(毫秒)
        RequestConfig config = RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000).build();
        try {
            HttpPost httppost = new HttpPost("http://v.juhe.cn/certificates/query.php");
            // FileBody封装File类型的参数
            FileBody bin = new FileBody(file);
            // StringBody封装String类型的参数
            StringBody keyBody = new StringBody("6866ed735f47cce6d5d7ac2b9cf05b2b", ContentType.TEXT_PLAIN);
            StringBody typeBody = new StringBody(type, ContentType.TEXT_PLAIN);
            // addPart将参数传入，并指定参数名称
            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("pic", bin).addPart("key", keyBody)
                    .addPart("cardType", typeBody).build();
            httppost.setEntity(reqEntity);
            httppost.setConfig(config);
            // 执行网络请求并返回结果
            response = httpClient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                result =IOUtils.toString(resEntity.getContent(), "UTF-8");
            }
            EntityUtils.consume(resEntity);
        } finally {
            response.close();
            httpClient.close();
        }
        // 得到的是JSON类型的数据需要第三方解析JSON的jar包来解析
        return result;
    }
	// 添加
	@RequestMapping("add.do")
	@ResponseBody
	public Object add(Menu menu) throws IOException{ 
		System.out.println("添加接收参数："+menu.toString()); 
		 
		menu.setCreate_time(com.blog.util.TimeUtil.getDatetime());  
		menu.setRelate_id(menu.getRelate_id() == null ? "" : menu.getRelate_id());
		menu.setPriority(menu.getPriority() == null || "".equals(menu.getPriority()) ? "5" : menu.getPriority());
		menuServiceImpl.insert(menu);
		
		JSONObject object = jsonToJSONObject(menu);
		object.remove("name");
		object.put("label", menu.getName());
		object.put("isTab", menu.getUrl().indexOf("####") == -1 ? false : true); 
		
		return object;
	}
	
	/**
	 * 递归 删除关联菜单
	 * @param id
	 */
	protected StringBuilder remove_(String id){
		// 递归删除关联
		Menu m = new Menu();
		m.setRelate_id(id);
		List<Menu> list = menuServiceImpl.gets(m);
		StringBuilder sb = new StringBuilder();
		if(list != null && list.size() > 0){
			for(Menu item : list)
				sb.append(" OR id = " + item.getId() + remove_(item.getId()));
		}
		return sb;		
	}
	
	/**
	 * 删除 
	 * @param menu
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("remove.do")
	@ResponseBody
	public Object remove(Menu menu) throws IOException{
		System.out.println("删除："+menu.toString());
		
		// 删除主要对象  
		menuServiceImpl.delete("id = "+menu.getId() + remove_(menu.getId()));
		
		JSONObject object = new JSONObject();
		object.put("result", "success");
		return object;
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
	public Object update(Menu menu, String spread) throws IOException{ 
		System.out.println("修改接收参数："+menu.toString()+" spread="+spread); 
		  
		menu.setUpdate_time(getNowTime()); 
		menu.setPriority(menu.getPriority() == null || "".equals(menu.getPriority()) ? "5" : menu.getPriority());
		Map<String, Object> eq = new HashMap<>();
		eq.put("id", menu.getId());
		menuServiceImpl.update(menu, eq);
		
		WebsiteBase base = new WebsiteBase();
		base.setSpread(spread);
		Map<String, Object> eq_ = new HashMap<>(1);
		eq_.put("id", "1");
		websiteBaseServiceImpl.update(base, eq_);
		
		JSONObject json = jsonToJSONObject(menu);
		json.remove("name");
		json.put("label", menu.getName());
		json.put("isTab", menu.getUrl().indexOf("####") == -1 ? false : true); 
		return json;
	} 
	
	/**
	 * 递归初始化
	 * @param id
	 * @return
	 */
	protected JSONArray init_(String id){
		JSONArray jsonArray = null;
		Menu menu = new Menu();
		menu.setRelate_id(id); 
		List<Menu> ms = menuServiceImpl.getOfOrderBySort(menu, "ASC", "priority");
		if(ms != null && ms.size() > 0){
			jsonArray = new JSONArray();
			for(Menu item : ms) { 
				JSONObject object = jsonToJSONObject(item); 
				object.remove("name"); 
				object.put("label", item.getName()); 
				object.put("isTab", item.getUrl().indexOf("####") == -1 ? false : true); 
				JSONArray jsonArray_;
				if(item.getUrl().indexOf("####") != -1 && (jsonArray_ = init_(item.getId())) != null) 
					object.put("children", jsonArray_);
				jsonArray.add(object); 
			}
		}
		return jsonArray;
	}
	
	/**
	 * 初始化
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("init.do")
	@ResponseBody
	public JSONObject init() throws IOException{
		
		Menu m = new Menu();
		m.setRelate_id("");
		List<Menu> ms = menuServiceImpl.getOfOrderBySort(m, "ASC", "priority"); 
		JSONArray jsonArray = new JSONArray();
		for(Menu item : ms) { 
			JSONObject object = jsonToJSONObject(item); 
			object.remove("name"); 
			object.put("label", item.getName()); 
			object.put("isTab", item.getUrl().indexOf("####") == -1 ? false : true); 
			JSONArray jsonArray_;
			if(item.getUrl().indexOf("####") != -1 && (jsonArray_ = init_(item.getId())) != null) 
				object.put("children", jsonArray_);
			jsonArray.add(object); 
		} 
		
		WebsiteBase base = new WebsiteBase();
		base.setId("1"); 
		base = websiteBaseServiceImpl.get(base);
//		System.out.println(base.toString());
		
		JSONObject resultObj = new JSONObject();
		resultObj.put("responseCode", "success");
		resultObj.put("responseMsg", "初始化成功！");
		resultObj.put("data", jsonArray);
		resultObj.put("spread", base.getSpread());
//		System.out.println("初始化返回数据："+resultObj.toString());
		return resultObj;
	}
	
	
	
}
