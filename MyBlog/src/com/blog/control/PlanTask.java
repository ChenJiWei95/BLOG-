package com.blog.control;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.blog.entity.PlanBase;
import com.blog.entity.PlanDo;
import com.blog.service.PlanBaseService;
import com.blog.service.PlanDoService;
import com.blog.util.SnowFlakeGenerator;
import com.blog.util.TimeUtil;
/**
 * <b>计划任务</b>
 * <p>
 * 描述:<br>
 * 每天 早上六点发起 晚上九点结束
 * @author 威 
 * <br>2020年1月11日 下午5:06:47 
 * @see
 * @since 1.0
 */
@Component
@Lazy(false)
public class PlanTask {
	private static Logger log = Logger.getLogger(PlanTask.class);
			
	@Autowired
	private PlanDoService planDoServiceImpl;
	
	@Autowired
	private PlanBaseService planBaseServiceImpl;
	
	@Scheduled(cron="0 0/1 20 * * ?")
	public void test2(){
		log.info("开始任务");
		
	}
	
	@Scheduled(cron="0 15 17,18 * * ?")
	public void test(){
		log.info("test");
	}
	
	@Scheduled(cron="0 0 6 * * ?")
	public void startPlan(){
		log.info("今日计划定时任务");
		try {
			String today = TimeUtil.getDatetime(TimeUtil.DATE_FORMAT);
			// 防止重复调用
			if(planBaseServiceImpl.get("`create_date` = '"+today+"' ") != null)
				return ;
			// 创建新的计划base
			PlanBase planBase = new PlanBase();
			planBase.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
			planBase.setCreate_date(today);
			planBase.setExcitation_text("看着自己走过的计划，不浪费每一天");
			planBase.setPlan_name("每日计划");
			planBase.setSecret_key(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
			
			planBaseServiceImpl.insert(planBase);
			
			// 获取上一天的日期
			String foreDate = TimeUtil.getDay(planBase.getCreate_date(), TimeUtil.DATE_FORMAT, -1);
			// 根据日期找计划base 最后查找符合的计划PlanDo
			PlanBase foreP = planBaseServiceImpl.get("`create_date` = '"+foreDate+"' ");
			List<PlanDo> list = planDoServiceImpl.gets("`plan_base_id` = '"+foreP.getId()+"' ");
			
			PlanDo planDo = null;
			// 创建和昨天相同的计划PlanDo
			for(PlanDo p : list){
				planDo = new PlanDo();
				planDo.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
				planDo.setTime(TimeUtil.getDate());
				planDo.setName(p.getName());
				planDo.setStatus("02");
				planDo.setPlan_base_id(planBase.getId());
				planDo.setPlan_tag_id(p.getPlan_tag_id());
				planDoServiceImpl.insert(planDo);
			}
			
			//发送邮件
			NotifyControl.sendMail("1281384046@qq.com", 
					null, 
					"今日计划", 
					"<html><body><h1>今日计划</h1><a href=\"http://www.chenjiwey.cn:8080/MyBlog/admin/plan/show.chtml?secret_key="+planBase.getSecret_key()+"\">点击前往</a></body></html>", 
					null);
		}catch(DataIntegrityViolationException e) {
			log.info("修改失败，数据可能过长；"+e.getMessage());
			e.printStackTrace();
		}catch(RuntimeException e){
			log.info(e.getMessage());
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron="0 40 21 */1 * ?")
	public void goodNight(){
		// 提示晚安
		PlanBase planBase = planBaseServiceImpl.get("`create_date` = '"+ 
				TimeUtil.getDate(TimeUtil.DATE_FORMAT)+"'");
		planBase.setSecret_key("");
		planBaseServiceImpl.update(planBase, "`id` = '"+planBase.getId()+"'");
		//发送邮件
		NotifyControl.sendMail("1281384046@qq.com", 
				null, 
				"晚安提示", 
				"<html><body><h1>晚安</h1><a href=\"http://www.chenjiwey.cn:8080/MyBlog/admin/plan/night.chtml\">点击前往</a></body></html>", 
				null);
	}
	public static void main(String[] args){
		String str = "0.83";
			
		double d = Double.parseDouble(str);
		
		System.out.println(String.valueOf(d*100));
	}
	public static void test1(){
		String currentDate = TimeUtil.getDate(TimeUtil.DATE_FORMAT);
		
		int[] arr = {-4,-3,-2,-1,0};
		StringBuilder weekDate = new StringBuilder();
		StringBuilder planCount = new StringBuilder();
		StringBuilder planPercent = new StringBuilder();
		
		for(int i = 0; i < arr.length; i++){
				String date = "";
				try {
					date = TimeUtil.getDay(currentDate, TimeUtil.DATE_FORMAT, arr[i]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				System.out.println(date);
				// num 根据日期date和条件`status`='00'查询完成的数量
				// planCount.append(num+",") 拼接planCount
				// count 根据日期date 查询总数量
				// planPercent.append(num/count+",") 拼接planPercent
				weekDate.append(date.substring(5)+",");
		}
		weekDate.deleteCharAt(weekDate.length()-1);
//		planCount.deleteCharAt(weekDate.length()-1);
//		planPercent.deleteCharAt(weekDate.length()-1);
		
		
		
		System.out.println(weekDate);
		double d = (double) 0/0;
		System.out.println(String.valueOf(new BigDecimal(d).multiply(new BigDecimal("100")).setScale(0,BigDecimal.ROUND_HALF_DOWN)));
	}
	
	 
}
