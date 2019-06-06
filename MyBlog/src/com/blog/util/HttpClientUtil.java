package com.blog.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * httpClient工具类
 * @ClassName HttpClientUtil
 * @Description 
 * @author xuguiyi
 * @date 2016-8-23 下午10:03:49
 * @contact 676342073
 */
public class HttpClientUtil {
	

	private static PoolingHttpClientConnectionManager cm;
	private final static Object syncLock = new Object();
	private static CloseableHttpClient httpClient = null;
	private static RequestConfig requestConfig;
	
	private static final int CONNECTION_TIMEOUT = 59000;
	private static final int SOCKET_TIMEOUT = 59000;
	private static final int CONNECTION_MANAGER_TIMEOUT = 59000;
	
	static{
		requestConfig = RequestConfig.custom().setConnectTimeout(CONNECTION_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT)
				.setConnectionRequestTimeout(CONNECTION_MANAGER_TIMEOUT).build();
		cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(800);// 整个连接池最大连接数
		cm.setDefaultMaxPerRoute(100);// 每路由最大连接数，默认值是2
	}

	/**
	 * 获取连接
	 * @return
	 */
	public static CloseableHttpClient getHttpClient() {
		if (httpClient == null) {
			synchronized (syncLock) {
				if (httpClient == null) {
					httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig)
							.build();
				}
			}
		}
		return httpClient;
	}
	
	
}
