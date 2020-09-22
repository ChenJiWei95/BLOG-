package com.blog.util.i18n;

import java.util.List;

import org.apache.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.stereotype.Component;

import com.blog.entity.Language;
import com.blog.service.LanguageService;
import com.blog.util.SpringUtils;

/**
 * <b>i18n 整合mybatis</b>
 * <p>
 * 描述:<br>
 * 
 * @author 威 
 * <br>2020年6月7日 下午4:54:58 
 * @see
 * @since 1.0
 */
@Component
public class Ci18nMybatis extends AbstractCi18nCore {
	
	private static Logger log = Logger.getLogger(Ci18nMybatis.class); // 日志对象
	 
	protected Language getLanguage(String currentCode){
		LanguageService langService = (LanguageService) SpringUtils.getBean("languageServiceImpl");
		Language lang = null;
		try{
			log.info("查询数据库");
			lang = langService.get("code='"+currentCode+"'");
		} catch(MyBatisSystemException e){
			log.info("查询数据库 有多个数据");
			List<Language> langs = langService.gets("code='"+currentCode+"'");
			for(Language l : langs){
				if(l.getCode().equals(currentCode)){
					lang = l;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lang;
	}
	
}
