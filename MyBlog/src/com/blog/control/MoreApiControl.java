package com.blog.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blog.Constants;
import com.blog.util.ActionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MoreApiControl {
	/**
	 * 发短信接口 
	 * <p>	 
	 * to:发送邮箱
	 * subject:标题
	 * text:内容
	 * pwd:密码
	 * 
	 * resCode:返回码
	 * resDesc:返回提示
	 * 
	 * @param request
	 * @param response
	 * void
	 * @see
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/api/util/sendEmail.do")
	public void sendEmail(HttpServletRequest request, HttpServletResponse response){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> res = new HashMap<String, String>();
	    try {
	    	String data = ActionUtil.read(request);
	    	Map<String, String> mapData = mapper.readValue(data, Map.class);
	    	
	    	if("cjw168999".equals(mapData.get("pwd"))){
	    		res.put("resCode", Constants.RESCODE_0001);
				res.put("resDesc", "发送失败，密码错误！");
				ActionUtil.returnRes(response, JSONObject.fromObject(response).toString());
				return ;
	    	}
	    	String to = mapData.get("to");
		    Properties props = new Properties();
		    props.setProperty("mail.smtp.auth", "true");
		    props.setProperty("mail.transport.protocol", "smtp");
		    Session session = Session.getInstance(props);
		    session.setDebug(true);
		    Message msg = new MimeMessage(session);  
	    	msg.setText(mapData.get("text"));  
	    	msg.setFrom(new InternetAddress("chenjiwey@163.com", 
	    			MimeUtility.encodeText("title")));
	    	msg.setSubject(mapData.get("subject"));
		    Transport transport = session.getTransport();  
		    transport.connect("smtp.163.com", 25, "chenjiwey@163.com", "c123456"); 
			transport.sendMessage(msg,  
			        new Address[]{new InternetAddress(to)});
			transport.close();
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
			res.put("resCode", Constants.RESCODE_0001);
			res.put("resDesc", "发送失败，"+e.getMessage());
		} 
	    res.put("resCode", Constants.RESCODE_0000);
		res.put("resDesc", "发送成功");
	    try {
			ActionUtil.returnRes(response, JSONObject.fromObject(response).toString());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args){
	 	    
	}
}
