package com.blog.control.admin;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
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
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blog.entity.Menu;
import com.blog.service.MenuService;
import com.blog.util.ActionUtil;

@Controller("menuControl")
//admin/menu/editMovieInfo.do
@RequestMapping("/admin/menu")
public class MenuControl {
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private MenuService menuServiceImpl;
	
	
	// 返回 页面 
	@RequestMapping("/listview.chtml") 
	public String listview1(String agentno,ModelMap model){
		return "../../views/admin/menu/list";
//		return "../../views/admin_view";
	}
	// 返回 页面 
	@RequestMapping("/save_or_update.chtml") 
	public String save_or_update(String agentno,ModelMap model){
		return "../../views/admin/menu/save_or_update";
//		return "../../views/admin_view";
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
	@SuppressWarnings("unchecked")
	@RequestMapping("add.do")
	@ResponseBody
	public Object branchAdd(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, String> map = getRequestParameterMap(request);
		System.out.println("添加接受参数："+map);
		
		JSONObject resultObject = (JSONObject) JSONObject.parse(map.get("data"));
		
		Menu m = new Menu(); 
		m.setId(resultObject.getString("id"));
		m.setName(resultObject.getString("name"));
		m.setPriority(resultObject.getString("priority"));
		m.setCreate_time(com.blog.util.TimeUtil.getDatetime());
		m.setUrl(resultObject.getString("url"));
		m.setRelate_id(map.get("relateId"));
		m.setMsg(resultObject.getString("msg"));
		menuServiceImpl.insert(m);
		
		JSONObject obj1 = new JSONObject();
		obj1.put("id", m.getId());
		obj1.put("label", m.getName());
		obj1.put("isTab", m.getUrl().indexOf("####") == -1 ? false : true);
		obj1.put("priority", m.getPriority());
		obj1.put("url", m.getUrl());
		obj1.put("create_time", m.getCreate_time());
		obj1.put("msg", m.getMsg());
		
		return obj1;
	}
	
	// 删除
	@SuppressWarnings("unchecked")
	@RequestMapping("remove.do")
	@ResponseBody
	public Object delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, String> map = getRequestParameterMap(request);
		System.out.println("删除接受参数："+map);
		
		JSONObject resultObject = (JSONObject) JSONObject.parse(map.get("data"));
		
		Menu m = new Menu();
		m.setId(resultObject.getString("id"));
		menuServiceImpl.delete(m);
		
		JSONObject object = new JSONObject();
		object.put("result", "success");
		return object;
	}
	
	// 修改
	@SuppressWarnings("unchecked")
	@RequestMapping("update.do")
	@ResponseBody
	public Object branchUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, String> map = getRequestParameterMap(request);
		System.out.println("修改接受参数："+map);
		
		JSONObject resultObject = (JSONObject) JSONObject.parse(map.get("data"));
		
		Menu m = new Menu(); 
		m.setName(resultObject.getString("name"));
		m.setPriority(resultObject.getString("priority"));
		m.setUpdate_time(com.blog.util.TimeUtil.getDatetime());
		m.setUrl(resultObject.getString("url"));
		m.setMsg(resultObject.getString("msg"));
		Map<String, String> eq = new HashMap<>();
		eq.put("id", resultObject.getString("id"));
		menuServiceImpl.update(m, eq);
		
		JSONObject obj1 = new JSONObject();
		obj1.put("id", resultObject.getString("id"));
		obj1.put("label", resultObject.get("name"));
		obj1.put("isTab", ((String) resultObject.get("url")).indexOf("####") == -1 ? false : true);
		obj1.put("priority", resultObject.get("priority"));
		obj1.put("url", resultObject.get("url"));
		obj1.put("create_time", resultObject.getString("create_time"));
		obj1.put("update_time", m.getUpdate_time());
		obj1.put("msg", resultObject.get("msg"));
		return obj1;
	}
	
	// 初始化
	@RequestMapping("init.do")
	@ResponseBody
	public JSONObject init(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		Menu m = new Menu(); 
		m.setRelate_id("");
		List<Menu> ms = menuServiceImpl.gets(m);
		for(Menu item : ms) {
			m = new Menu(); 
			m.setRelate_id(item.getId());
			List<Menu> ms_ = menuServiceImpl.gets(m);
		}
		
		JSONObject obj1 = new JSONObject();
		obj1.put("id", "2");
		obj1.put("label", "菜单管理");
		obj1.put("isTab", false);
		obj1.put("priority", "1");
		obj1.put("url", "http://localhost:8080/MyBlog/views/tagmanage.jsp");
		obj1.put("create_time", "2019-09-09");
		obj1.put("update_time", "2019-09-09");
		obj1.put("msg", "备注");
		
		JSONObject obj2 = new JSONObject();
		obj2.put("id", "3");
		obj2.put("label", "数据字典");
		obj2.put("isTab", true);
		obj2.put("priority", "1");
		obj2.put("url", "####");
		obj2.put("create_time", "2019-09-09");
		obj2.put("update_time", "2019-09-09");
		obj2.put("msg", "备注");
		
		JSONArray arr_1 = new JSONArray();
		arr_1.add(obj1);
		arr_1.add(obj2);
		
		JSONObject object = new JSONObject();
		// id label priority url create_time update_time msg
		object.put("id", "1");
		object.put("label", "资源管理");
		object.put("isTab", true);
		object.put("priority", "1");
		object.put("url", "####");
		object.put("create_time", "2019-09-09");
		object.put("update_time", "2019-09-09");
		object.put("msg", "备注");
		object.put("children", arr_1);
		
		JSONObject obj1_1 = new JSONObject();
		obj1_1.put("id", "4");
		obj1_1.put("label", "访客管理");
		obj1_1.put("isTab", false);
		obj1_1.put("priority", "1");
		obj1_1.put("url", "http://localhost:8080/MyBlog/views/usermanage.jsp");
		obj1_1.put("create_time", "2019-09-09");
		obj1_1.put("update_time", "2019-09-09");
		obj1_1.put("msg", "备注");
		
		JSONObject obj2_2 = new JSONObject();
		obj2_2.put("id", "5");
		obj2_2.put("label", "后天管理员");
		obj2_2.put("isTab", false);
		obj2_2.put("priority", "1");
		obj2_2.put("url", "http://localhost:8080/MyBlog/views/adminmanage.jsp");
		obj2_2.put("create_time", "2019-09-09");
		obj2_2.put("update_time", "2019-09-09");
		obj2_2.put("msg", "备注");
		
		JSONArray arr_1_1 = new JSONArray();
		arr_1_1.add(obj1_1);
		arr_1_1.add(obj2_2);
		
		JSONObject object_1 = new JSONObject();
		// id label priority url create_time update_time msg
		object_1.put("id", "6");
		object_1.put("label", "权限管理");
		object_1.put("isTab", true);
		object_1.put("priority", "1");
		object_1.put("url", "####");
		object_1.put("create_time", "2019-09-09");
		object_1.put("update_time", "2019-09-09");
		object_1.put("msg", "备注");
		object_1.put("children", arr_1_1);
		
		JSONArray arr = new JSONArray();
		arr.add(object);
		arr.add(object_1);
		System.out.println("初始化:"+arr.toString());
		
		JSONObject resultObj = new JSONObject();
		resultObj.put("responseCode", "success");
		resultObj.put("responseMsg", "初始化成功！");
		resultObj.put("data", arr);
		String[] strs = {"1"};
		resultObj.put("spread", strs);
		return resultObj;
	}
	protected static Map<String, String> getRequestParameterMap(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		try {
			Map<String, String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				System.out.println("name:"+name+", values:"+values);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
					params.put(name, valueStr);
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return params;
	}
	protected String basePath(HttpServletRequest request){
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
	}
}
