package com.blog.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.blog.util.HttpUtils2;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 使用APPCODE进行云市场ocr服务接口调用
 */

public class APPCodeDemo {

    /*
     * 获取参数的json对象
     */
    private static JSONObject getParam(int type, String dataValue) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("dataType", type);
            obj.put("dataValue", dataValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

   /* public static void main(String[] args){

        String host = "http://dm-51.data.aliyun.com";
        String path = "/rest/160601/ocr/ocr_idcard.json";
        //http://dm-51.data.aliyun.com/rest/160601/ocr/ocr_idcard.json
        String appcode = "c06aadd9e5de4eaa9ae1aab8772bb25f";
        String imgFile = "d:3.png";
        Boolean is_old_format = false;//如果文档的输入中含有inputs字段，设置为True， 否则设置为False
        //请根据线上文档修改configure字段
        JSONObject configObj = new JSONObject();
        configObj.put("side", "face");
        String config_str = configObj.toString();
        //            configObj.put("min_size", 5);
        //            String config_str = "";

        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);

        Map<String, String> querys = new HashMap<String, String>();

        // 对图像进行base64编码
        String imgBase64 = "";
        try {
            File file = new File(imgFile);
            byte[] content = new byte[(int) file.length()];
            FileInputStream finputstream = new FileInputStream(file);
            finputstream.read(content);
            finputstream.close();
            imgBase64 = new String(encodeBase64(content));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        // 拼装请求body的json字符串
        JSONObject requestObj = new JSONObject();
        try {
            if(is_old_format) {
                JSONObject obj = new JSONObject();
                obj.put("image", getParam(50, imgBase64));
                if(config_str.length() > 0) {
                    obj.put("configure", getParam(50, config_str));
                }
                JSONArray inputArray = new JSONArray();
                inputArray.add(obj);
                requestObj.put("inputs", inputArray);
            }else{
                requestObj.put("image", imgBase64);
                if(config_str.length() > 0) {
                    requestObj.put("configure", config_str);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String bodys = requestObj.toString();

        try {
            *//**
             * 重要提示如下:
             * HttpUtils2请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils2.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             *//*
            HttpResponse response = HttpUtils2.doPost(host, path, method, headers, querys, bodys);
            int stat = response.getStatusLine().getStatusCode();
            if(stat != 200){
                System.out.println("Http code: " + stat);
                System.out.println("http header error msg: "+ response.getFirstHeader("X-Ca-Error-Message"));
                System.out.println("Http body error msg:" + EntityUtils.toString(response.getEntity()));
                return;
            }

            String res = EntityUtils.toString(response.getEntity());
            JSONObject res_obj = JSON.parseObject(res);
            if(is_old_format) {
                JSONArray outputArray = res_obj.getJSONArray("outputs");
                String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
                JSONObject out = JSON.parseObject(output);
                System.out.println(out.toJSONString());
            }else{
                System.out.println(res_obj.toJSONString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }          
    }*/
    public static void main(String[] args) {
        // 将图片转换为base64编码格式
       /* String imgPath = "d:/3.png";
        String imgStr = imgToBase64(imgPath);
        ocr(imgStr);*/
    	
    	ocrBank();
    }
    /**
     * 阿里OCR接口
     */
    public static void ocr(String imgBase64){
    	String host = "http://dm-51.data.aliyun.com";
        String path = "/rest/160601/ocr/ocr_idcard.json";
//        String host = "https://ocrapi-document.taobao.com"; // 阿里接口地址
//        String path = "/ocrservice/document"; // 具体地址
        String method = "POST";  // 请求类型POST
        String appcode = "c06aadd9e5de4eaa9ae1aab8772bb25f"; // 产品密钥
        Boolean is_old_format = false;
        JSONObject configObj = new JSONObject();
        configObj.put("side", "face");
        String config_str = configObj.toString();
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE yourAppCode
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        // img 和 url 只能使用一个
//        String bodys = "{\"img\":\""+imgBase64+"\",\"prob\":false}";
        JSONObject requestObj = new JSONObject();
        try {
            if(is_old_format) {
                JSONObject obj = new JSONObject();
                obj.put("image", getParam(50, imgBase64));
                if(config_str.length() > 0) {
                    obj.put("configure", getParam(50, config_str));
                }
                JSONArray inputArray = new JSONArray();
                inputArray.add(obj);
                requestObj.put("inputs", inputArray);
            }else{
                requestObj.put("image", imgBase64);
                if(config_str.length() > 0) {
                    requestObj.put("configure", config_str);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String bodys = requestObj.toString();
        try {
        	System.out.println(bodys);
            /**
             * HttpUtils2下载：https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils2.java
             * 相关依赖请参照：https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
        	HttpResponse response = HttpUtils2.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            // 获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 将图片转换为base64字符串
     * @param imgPath 编码图片的路径
     * @return imgStr 图片的base64编码字符串
     */
    public static String imgToBase64(String imgPath){
        byte[] data = null;
        InputStream in = null;
        try {
            // 将图片读入data中
            in = new FileInputStream(new File(imgPath));
            data = new byte[in.available()];
            in.read(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 对data进行Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String imgStr = encoder.encode(data);
        return imgStr;
    }
 
    /**
     * 将Base64字符串转换为图片
     * @param imgStr 图片的base64编码字符串；
     * @param imgPath 生成图片的路径
     * @return 是否生成图片
     */
    public static boolean base64ToImg(String imgStr, String imgPath){
        if(imgStr == null){
            return false;
        }
        // 对imgBase64字符串进行解码
        OutputStream out = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i = 0 ; i <b.length ; i++){
                // 调整异常数据
                if(b[i] < 0){
                    b[i] += 256;
                }
            }
            // 生成图片文件
            out = new FileOutputStream(new File(imgPath));
            out.write(b);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    
    public static void ocrBank() {
    	//
	    String host = "http://yhk.market.alicloudapi.com";
	    String path = "/rest/160601/ocr/ocr_bank_card.json";
	    String method = "POST";
	    String appcode = "c06aadd9e5de4eaa9ae1aab8772bb25f";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    //根据API的要求，定义相对应的Content-Type
	    headers.put("Content-Type", "application/json; charset=UTF-8");
	    Map<String, String> querys = new HashMap<String, String>();
	    JSONObject requestObj = new JSONObject();
	    requestObj.put("image", imgToBase64("d:/1.jpg"));
	    String bodys = requestObj.toString();
//	    String bodys = "{\"image\":\""++"\"}";//#图片以base64编码的string

	    try {
	    	System.out.println(bodys);
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils2请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils2.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils2.doPost(host, path, method, headers, querys, bodys);
	    	System.out.println(response.toString());
	    	//获取response的body
	    	System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
}
