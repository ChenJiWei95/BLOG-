package com.blog.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.blog.service.RedisService;


public class BeanUtil {
	public static Object getBean(String beanName){
		ApplicationContext context = new ClassPathXmlApplicationContext("config/applicationContext.xml") ;
		return context.getBean(beanName) ;
	}
	public static void main(String[] args){
		/*GoodsServiceImpl service = (GoodsServiceImpl) getBean("goodsServiceImpl");
		GoodsStytleService styleService = (GoodsStytleService) getBean("goodsStytleServiceImpl");
		List<Goods> list = service.findAll();
		List<GoodsStytle> goodsStytles = styleService.findAll();
		for(Goods g : list){
			g.setUrl("resource/img/"+g.getUrl());
			service.update(g);
		}
		for(GoodsStytle g : goodsStytles){
			g.setUrl("resource/img/"+g.getUrl());
			styleService.update(g);
		}*/
		
		/*String[] url = {"good1.png", "good2.png", "good3.png", "good4.png", "goods1.jpg", "goods17.jpg", "goods2.jpg", "goods3.jpg"};
		String[] price = {"289", "418", "199", "278", "166", "888", "298", "268"};
		String[] discount = {"40", "20", "30", "10", "40", "80"};
		String[] color = {"黑色", "灰色", "卡其色", "白色", "蓝色"};
		String[] colorSign = {"S", "M", "XL", "XLL"};
		String[] types = {"01", "02", "03", "04", "05"};
		for(int j = 0; j < 40; j++){
			Goods goods = new Goods();
			goods.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
			goods.setName("xxx");
			goods.setDiscount(new BigDecimal(discount[new Random().nextInt(6)]));
			goods.setPrice(new BigDecimal(price[new Random().nextInt(8)]));
			goods.setUpdateDate(TimeUtil.getDatetime());
			goods.setCreateDate(TimeUtil.getDatetime());
			goods.setUrl(url[new Random().nextInt(8)]);
			goods.setType(types[new Random().nextInt(5)]); 
			for(int i = 0; i < 4; i++){
				GoodsStytle style = new GoodsStytle();
				style.setGoodsId(goods.getId());
				style.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
				style.setUrl(url[new Random().nextInt(8)]);
				style.setColor(color[i]);
				style.setColorSign(colorSign[i]);
				styleService.save(style);
			}
			service.save(goods);
		}*/
		/*GoodsServiceImpl service = (GoodsServiceImpl) getBean("goodsServiceImpl");
		List<Goods> list = service.findAll();
		for(Goods g : list){
			BigDecimal ori = g.getDiscount();
			g.setDiscount(ori.divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_DOWN));
			service.update(g);
		}*/
		
		/*LanguageService service = (LanguageService) getBean("languageServiceImpl");
		Language lang = new Language();
		lang.setId(CommonUtil.randomId());
		lang.setCode("blog.index.closeCurrent");
		lang.setZh_CN("中文");
		lang.setEn_US("Close current tab");
		service.insert(lang); 
		System.out.println(lang.getId()+" ok");
		lang.setId(CommonUtil.randomId());
		lang.setCode("blog.index.closeCurrent");
		lang.setZh_CN("关闭当前标签");
		lang.setEn_US("Close current tab");
		service.insert(lang);
		System.out.println(lang.getId()+" ok");*/
		/*lang.setId(CommonUtil.randomId());
		lang.setCode("blog.index.managesys");
		lang.setZh_CN("博客后台管理");
		lang.setEn_US("Blog manage sys");
		service.insert(lang);
		System.out.println(lang.getId()+" ok");*/
		
		/*try {
			lang = service.get("code='shop.common.goodsdetails'");
			System.out.println(lang);
		} catch(MyBatisSystemException e){
			System.out.println("many");
		} catch (Exception e) {
			
			e.printStackTrace();
		}*/
		RedisService redis = (RedisService) getBean("redisService");
		System.out.println(redis.get("9ab06372429a409995c7200fad66fb3d"));
	}
}
