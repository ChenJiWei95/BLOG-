package com.blog.control;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blog.Constants;
import com.blog.entity.WebsiteBase;
import com.blog.service.RedisService;
import com.blog.service.WebsiteBaseService;
import com.blog.util.SpringUtils;
/**
 * <b>初始化语种在redis的缓存 默认中文</b>
 * <p>
 * 描述:<br>
 * 
 * @author 威 
 * <br>2020年6月6日 下午2:14:07 
 * @see
 * @since 1.0
 */
@Component
public class InitStander implements InitializingBean{
	private static Logger log = Logger.getLogger(InitStander.class);
	/** WebApplicationContext、springMVC 这两种情况都会调用到这个类，所以要控制一下 */
	private static boolean firstInit = true; 
	
	@Autowired
	private RedisService redisService;
	@Autowired
	WebsiteBaseService websiteBaseServiceImpl; 
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(firstInit){
			redisService.set(Constants.LANGUAGE_CURRENT, Constants.LANGUAGE_ZH_CN);
			log.info("初始化语言环境："+redisService.get(Constants.LANGUAGE_CURRENT));
			WebsiteBase base = websiteBaseServiceImpl.get("id="+Constants.MANAGER_SYS_BASE_ID);
			redisService.set(Constants.MANAGER_SYS_BASE, base, 120);
			log.info("初始化网站基本配置:"+redisService.get(Constants.MANAGER_SYS_BASE));
			firstInit = false;
		}
	}

}
 

  