package com.blog.control;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blog.util.ActionUtil;

/**
 * <b>工作需求</b>
 * <p>
 * 描述:<br>
 * 回调
 * https://www.cnblogs.com/jiafuwei/p/9512400.html 邮箱发送详细
 * @author 威 
 * <br>2019年4月28日 下午10:11:02 
 * @see
 * @since 1.0
 */
@Controller
public class NotifyControl {
	
	@RequestMapping("/api/payNotify.do")
	public void notify(HttpServletRequest request, HttpServletResponse response){
		
		try {
			
			String data = ActionUtil.read(request);
			sendMail("1281384046@qq.com", null, "支付", "<html><body><h1>异步通知</h1><p>"+data+"</p></body></html>", null);
			ActionUtil.returnRes(response, "success");
		} catch (IOException e) { 
			System.out.println("RESCODE_0002");
			e.printStackTrace();
			
		}
		
		
		
	}
	@RequestMapping("/api/rePayNotify.do")
	public void reNotify(HttpServletRequest request, HttpServletResponse response){
		
		try {
			
			String data = ActionUtil.read(request);
			sendMail("1281384046@qq.com", null, "代付", "<html><body><h1>异步通知</h1><p>"+data+"</p></body></html>", null);
			ActionUtil.returnRes(response, "success");
		} catch (IOException e) { 
			System.out.println("RESCODE_0002");
			e.printStackTrace();
		}
		
		
		
	}
	@RequestMapping("/api/bindNotify.do")
	public void bindNotify(HttpServletRequest request, HttpServletResponse response){
		
		try {
			String data = ActionUtil.read(request);
			System.out.println("data:"+data);
			sendMail("1281384046@qq.com", null, "绑卡", "<html><body><h1>异步通知</h1><p>"+data+"</p></body></html>", null);
			ActionUtil.returnRes(response, "success");
		} catch (IOException e) { 
			System.out.println("RESCODE_0002");
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("/api/fz/payNotify.do")
	public void notify1(HttpServletRequest request, HttpServletResponse response){
		
		try {
			
			String data = ActionUtil.read(request);
			sendMail("1209986709@qq.com", null, "支付", "<html><body><h1>异步通知</h1><p>"+data+"</p></body></html>", null);
			ActionUtil.returnRes(response, "success");
		} catch (IOException e) { 
			System.out.println("RESCODE_0002");
			e.printStackTrace();
			
		}
		
		
		
	}
	@RequestMapping("/api/fz/rePayNotify.do")
	public void reNotify1(HttpServletRequest request, HttpServletResponse response){
		
		try {
			
			String data = ActionUtil.read(request);
			sendMail("1209986709@qq.com", null, "代付", "<html><body><h1>异步通知</h1><p>"+data+"</p></body></html>", null);
			ActionUtil.returnRes(response, "success");
		} catch (IOException e) { 
			System.out.println("RESCODE_0002");
			e.printStackTrace();
		}
		
	}
	@RequestMapping("/api/fz/bindNotify.do")
	public void bindNotify1(HttpServletRequest request, HttpServletResponse response){
		
		try {
			String data = ActionUtil.read(request);
			System.out.println("data:"+data);
			sendMail("1209986709@qq.com", null, "绑卡", "<html><body><h1>异步通知</h1><p>"+data+"</p></body></html>", null);
			ActionUtil.returnRes(response, "success");
		} catch (IOException e) { 
			System.out.println("RESCODE_0002");
			e.printStackTrace();
		}
		
	}
	
	/*private static final String FROM_MAIL_SMTP = "smtp.exmail.qq.com";
    private static final String FROM_MAIL_NAME = "aaaaaa@qq.com";
    private static final String FROM_MAIL_PASS = "xxxxxxx";*/
	
	private static final String FROM_MAIL_SMTP = "smtp.163.com";
    private static final String FROM_MAIL_NAME = "chenjiwey@163.com";
    private static final String FROM_MAIL_PASS = "c123456";
	
	/**
     * 发送邮件(灵活度高，通用版)
     * @param from 发件人
     * @param to 收件人, 多个Email以英文逗号分隔
     * @param cc 抄送, 多个Email以英文逗号分隔        可为空
     * @param subject 主题
     * @param content 内容
     * @param fileList 附件列表
     * @return
     */
    public static boolean sendMail(String to, String cc, String subject, String content, String[] fileList){
         try {
             Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
             final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
             final Properties p = System.getProperties() ;
             p.setProperty("mail.smtp.host", FROM_MAIL_SMTP);
             p.setProperty("mail.smtp.auth", "true");
             p.setProperty("mail.smtp.user", FROM_MAIL_NAME);
             p.setProperty("mail.smtp.pass", FROM_MAIL_PASS);
             
             p.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
             p.setProperty("mail.smtp.socketFactory.fallback", "false");
             //邮箱发送服务器端口,这里设置为465端口
             p.setProperty("mail.smtp.port", "465");
             p.setProperty("mail.smtp.socketFactory.port", "465");
    
            // 根据邮件会话属性和密码验证器构造一个发送邮件的session
            Session session = Session.getInstance(p, new Authenticator(){
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(p.getProperty("mail.smtp.user"),p.getProperty("mail.smtp.pass"));
                }
            });
            session.setDebug(true);
            Message message = new MimeMessage(session);
            //消息发送的主题
            message.setSubject(subject);
            //接受消息的人
            message.setReplyTo(InternetAddress.parse(FROM_MAIL_NAME));
            //消息的发送者
            message.setFrom(new InternetAddress(p.getProperty("mail.smtp.user"), "自动发送"));
            // 创建邮件的接收者地址，并设置到邮件消息中
            String[] split = to.split(",");
            InternetAddress []tos = new InternetAddress[split.length];
            for (int i = 0; i < split.length; i++) {
                tos[i]=new InternetAddress(split[i]);
            }
            // 设置抄送人
            if (cc != null && cc.length() > 0) {
                message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc)); 
            }
            message.setRecipients(Message.RecipientType.TO, tos);
            // 消息发送的时间
            message.setSentDate(new Date());
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(content, "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            // 将MiniMultipart对象设置为邮件内容
            message.setContent(mainPart);
            // 设置附件
            if (fileList != null && fileList.length > 0) {
                for (int i = 0; i < fileList.length; i++) {
                    html = new MimeBodyPart();
                    FileDataSource fds = new FileDataSource(fileList[i]); 
                    html.setDataHandler(new DataHandler(fds)); 
                    html.setFileName(MimeUtility.encodeText(fds.getName(), "UTF-8", "B"));
                    mainPart.addBodyPart(html); 
                }
            }
            message.setContent(mainPart); 
            message.saveChanges(); 
            Transport.send(message);
         } catch (MessagingException e) {
             e.printStackTrace();
             return false;
         } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
	
	
	public static void main(String[] args){
//		String url = "http://localhost:8080/MyBlog/api/bindNotify.do";
		//payNotify rePayNotify bindNotify
		String url = "http://localhost:8080/authsys/api/channel/ls/kj/payNotify.do";
		post(url, "encryptData=BoVx7xRdAistcNPvmdpiNtqFLaNq5E%2B7cO8uokPw1vAtbaPeSz%2F1LSIPaYgBlYOTz37ClOMtfb9kPQokT06032jqf%2FnHzmGTIfQnqE7sUVKQKIqneX%2Bf0wVvdHLv42eZYBPeOrWFAebw8DCGqEVdIxXCfm%2Fqbfc4ye1R0wy2n4BYndHYWRXK8nEv9uYpWwHBWVqJwZhJFJg1whC1egVv%2FANJGWFHAJMLL4cI5o4an101FcTvyJ%2FgaXeCtO605Sp1QQ5%2FWYx4GK98n3%2FHzujqgg%3D%3D&encryptKey=Ewe9uhiD%2Fo%2B7r4zM1Vyp0nwtJJV8MONtitsCkNm8VJgHiYbK0jypJd7x7q1uaI46sVeklpcsPJcFV2ffVPf6gMegUCOpxG1ZXBT8b9NniNMBMp2pzhbEQSOP7KN%2Fws3QELifVrmGTSIBT0KoWqHa96VXTGr0h2DWE1edb9pLTRO0vIDRlewAVDCt7zf8eAZDVuVPL1FjPB%2BTR43FyKf%2B0lAaHijv%2F82bMDq4zt0ANE3DVr3MsVnVlCZHlRo5BcHnOC3ixIGRuc80S2I6L3M%2Bw0jH9cGzUKvEL8ICUC8iNMLhFPUQ3F%2B4gzuqNZOI0k7BQcgaLLsu8tND3TSShjb06w%3D%3D&companyId=&strMerchantID=1010012910&signData=frtTlt%2F2JNvb%2Fu8WEFO2el5DFY%2FLHOQCm6SRN7cjWp%2Faa%2B1C%2F9XWRFiNNDQqNNzvBhMTdmAm6iZLwmGHod%2Fc9jq5%2FkLvD1qV%2B1x1GeCofTqBUCgvFOD7TG%2FvsohBF3B%2BTfo9R2CJjv0lyP1MVg0re%2FXMv%2FtJcz1wU3r6fFBkPFiVcwoJcES7YeBPLyjo1sBiIgRNsafVtLzEfxvpnvOhyKgf8mwnA8pHLzppZMxAdjLjPe2GQjzzs31GSa%2BoMfTaIWh8A2FQ2nQKqOAwQ8FQ01HxvCymRicu3XezH0btW1Wkf96GQQY28RAPDbWiya5cBVaPmsq%2F0yzfsH0H6q70OA%3D%3D&");
        
	}
	
	public static String post(String urlStr, String data){
		String result = "";
		URL url = null;
		try {
			url = new URL(urlStr);
		} catch (MalformedURLException e) {
            System.out.println("url异常");
            e.printStackTrace();
        }
		InputStream in = null;
		DataOutputStream dos = null;
		ByteArrayOutputStream baos = null;
		try {
            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            conn.setConnectTimeout(3000);
            //设置参数
            conn.setDoOutput(true);   //需要输出
            conn.setDoInput(true);
            conn.setUseCaches(false);  //不允许缓存
            conn.setRequestMethod("POST");   //设置POST方式连接
            //设置请求属性
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            conn.setRequestProperty("Charset", "UTF-8");
            
            //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
            conn.connect();
            //建立输入流，向指向的URL传入参数
            dos=new DataOutputStream(conn.getOutputStream());
            dos.write(data.getBytes("utf-8"));
            dos.flush();
            dos.close();
            
            in = conn.getInputStream();
            baos = new ByteArrayOutputStream();
            int len = -1;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) != -1) 
                baos.write(buffer, 0, len);
            result = new String(baos.toByteArray());
            
        } catch (IOException e) {
            System.out.println("io异常");
            e.printStackTrace();
        } finally {
            /*try {
            	baos.flush();
				baos.close();
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
        }
		return result;
	}
}
