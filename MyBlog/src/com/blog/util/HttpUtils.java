package com.blog.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * http 工具类
 * 
 * @className HttpUtils
 * @Description
 * @author xuguiyi
 * @contact
 * @date 2016-9-18 上午11:22:12
 */
public class HttpUtils {

	private static Logger logger = Logger.getLogger(HttpUtils.class);

	/**
	 * httpGet
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String httpGet(String url) throws Exception {

		CloseableHttpClient httpclient = null;
		HttpGet httpGet = null;
		HttpEntity entity = null;
		CloseableHttpResponse response = null;

		// 设置http参数
		try {
			logger.info("HttpUtils request:" + url);

			httpclient = HttpClientUtil.getHttpClient();
			httpGet = new HttpGet(url);

			response = httpclient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() != 200) {
				logger.error("HttpUtils statusLine.getStatusCode() != 200 " + statusLine.getStatusCode() + "=" + statusLine.getReasonPhrase());
				return null;
			}
			entity = response.getEntity();

			return entity == null ? null : EntityUtils.toString(entity, "UTF-8");

		} catch (Exception e) {
			logger.error("HttpUtils 异常", e);
			return null;
		} finally {
			if (response != null)
				response.close();

			if (httpGet != null)
				httpGet.releaseConnection();

			if (entity != null) {
				EntityUtils.consume(entity); // 会自动释放连接
			}
		}
	}

	/**
	 * post参数请求
	 * 
	 * @param url
	 * @param reqparams
	 * @return
	 * @throws Exception
	 */
	public String httpPost(String url, Map<String, String> reqparams) throws Exception {
		return this.httpPost(url, null, reqparams);
	}

	/**
	 * post参数请求
	 * 
	 * @param url
	 * @param headerMap
	 * @param reqparams
	 * @return
	 * @throws Exception
	 */
	public String httpPost(String url, Map<String, String> headerMap, Map<String, String> reqparams) throws Exception {

		CloseableHttpClient httpclient = null;
		HttpPost httpPost = null;
		HttpEntity entity = null;
		CloseableHttpResponse response = null;

		// 设置http参数

		try {
			httpclient = HttpClientUtil.getHttpClient();
			httpPost = new HttpPost(url);

			// httpPost.addHeader(HttpHeaders.USER_AGENT,
			// "Mozilla/4.7 (compatible; MSIE 8.0; Windows NT 6.1; Maxthon;)");
			//
			// Map<String, String> headerMap = new HashMap<String, String>();
			// headerMap.put("Accept", "application/json");
			// headerMap.put("Content-Type",
			// "application/x-www-form-urlencoded");

			if (headerMap != null && headerMap.size() > 0) {
				BasicHeader h = null;
				for (String hkey : headerMap.keySet()) {
					if (hkey == null) {
						continue;
					}
					h = new BasicHeader(hkey, headerMap.get(hkey));
					httpPost.addHeader(h);
				}
			}

			if (reqparams != null && reqparams.size() > 0) {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(reqparams.keySet().size());
				for (String key : reqparams.keySet()) {
					nameValuePairs.add(new BasicNameValuePair(key, reqparams.get(key) == null ? "" : reqparams.get(key)));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			}

			response = httpclient.execute(httpPost);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() != 200) {
//				logger.warn("HttpUtils httpPost statusLine.getStatusCode() != 200 " + statusLine.getStatusCode() + "=" + statusLine.getReasonPhrase());
				try {
					entity = response.getEntity();
//					logger.warn("HttpUtils httpPost response:" + EntityUtils.toString(entity, "UTF-8"));
				} catch (Exception e) {
				}
			
				return null;
			}
			entity = response.getEntity();

			return entity == null ? null : EntityUtils.toString(entity, "UTF-8");

		} catch (Exception e) {
			logger.error("HttpUtils httpPost 异常", e);
			return null;
		} finally {
			if (response != null)
				response.close();

			if (httpPost != null)
				httpPost.releaseConnection();

			if (entity != null) {
				EntityUtils.consume(entity); // 会自动释放连接
			}
		}

	}

}
