package com.blog.control.admin;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.control.BaseControl;
import com.blog.entity.Data;
import com.blog.entity.PlanBase;
import com.blog.entity.PlanDo;
import com.blog.entity.WebsiteBase;
import com.blog.service.DataService;
import com.blog.service.PlanBaseService;
import com.blog.service.PlanDoService;
import com.blog.service.WebsiteBaseService;
import com.blog.util.Message;
import com.blog.util.SnowFlakeGenerator;
import com.blog.util.TimeUtil;

@Controller
@RequestMapping("/admin/plan")
public class PlanControl extends BaseControl{
	
	public static String DATA_SIGN = "plan_sign";
	
	private static Logger log = Logger.getLogger(PlanControl.class);
	
	@Autowired
	private PlanDoService planDoServiceImpl;
	
	@Autowired
	private PlanBaseService planBaseServiceImpl;
	
	@Autowired
	private WebsiteBaseService websiteBaseServiceImpl;
	
	@Autowired
	private DataService dataServiceImpl;
	
	// 返回 页面 
	@RequestMapping("/show.chtml") 
	public String listview1(ModelMap model, String secret_key, String isTips, HttpServletRequest request){
		if(!secretKeyCheck(secret_key)){
			model.addAttribute("msg", "secret_key:"+secret_key+" 错误！");
			return "admin/error";
		}
		
		PlanBase planBase  = getPlanBase(model, TimeUtil.getDate(TimeUtil.DATE_FORMAT));
		List<PlanDo> planDo = planDoServiceImpl.gets(eq("plan_base_id", planBase.getId()));
		model.addAttribute("planBase", planBase);
		model.addAttribute("planDo", planDo);
		model.addAttribute("secret_key", websiteBaseServiceImpl.get(eq("id", "1")).getSecret_key());
		model.addAttribute("isTips", isTips);
		return "admin/plan/list";
	}
	
	@RequestMapping("/night.chtml") 
	public String listview2(ModelMap model, String secret_key, HttpServletRequest request){
		if(!secretKeyCheck(secret_key))
			return "admin/error";
		
		statistics(model); 
		nextPlan(model);
		return "admin/plan/night";
	}

	protected void statistics(ModelMap model) {
		String currentDate = TimeUtil.getDate(TimeUtil.DATE_FORMAT)
		,dayText1 = "今日完成情况"
		,dayText2 = "昨日完成情况";
		PlanBase planBase = planBaseServiceImpl.get(eq("create_date", 
				currentDate));
		if(planBase == null){
			// 获取上一天的日期  即当前天
			String foreDate = (currentDate = getPreDay(TimeUtil.getDate(TimeUtil.DATE_FORMAT)));
			planBase = planBaseServiceImpl.get(eq("create_date", 
					foreDate));
			dayText1 = dayText2;
			dayText2 = "前日完成情况";
		}
		List<PlanDo> planDo = planDoServiceImpl.gets(eq("plan_base_id", planBase.getId()));
		
		// 前一天
		String foreDate = getPreDay(planBase.getCreate_date());
		planBase = planBaseServiceImpl.get(eq("create_date", 
				foreDate));
		
		List<PlanDo> planDo2 = null;
		if(planBase != null){
			planDo2 = planDoServiceImpl.gets(eq("plan_base_id", planBase.getId()));
		}
		
		model.addAttribute("todayPlanDos", planDo);
		model.addAttribute("yesterdayPlanDos", planDo2);
		model.addAttribute("dayText1", dayText1);
		model.addAttribute("dayText2", dayText2);
		
		// 计算数据概览图
		String[] data = planDate(currentDate);
		model.addAttribute("weekDate", data[0]);
		model.addAttribute("planCount", data[1]);
		model.addAttribute("planPercent", data[2]); 
		model.addAttribute("planAllCount", data[3]);
	}

	@RequestMapping("/statistics.chtml") 
	public String listview3(ModelMap model, String secret_key){
		if(!secretKeyCheck(secret_key))
			return "admin/error";
		statistics(model);
		return "admin/plan/statistics";
	}
	
	@RequestMapping("/nextPlan.chtml") 
	public String listview4(ModelMap model, String secret_key){
		if(!secretKeyCheck(secret_key))
			return "admin/error";
		
		nextPlan(model);
		
		return "admin/plan/nextPlan";
	}

	
	protected void nextPlan(ModelMap model) {
		try {
			String nextDay = TimeUtil.getDay(
					TimeUtil.getDatetime(TimeUtil.DATE_FORMAT), 
					TimeUtil.DATE_FORMAT, 
					1);
			PlanBase planBase = getPlanBase(model, nextDay);
			List<Data> allTags = dataServiceImpl.gets(eq("type", DATA_SIGN));
			List<PlanDo> tags = planDoServiceImpl.gets(eq("plan_base_id", planBase.getId()));
			String secretKey = websiteBaseServiceImpl.get(eq("id", "1")).getSecret_key();
			
			model.addAttribute("secret_key", secretKey);
			model.addAttribute("planBase", planBase);
			model.addAttribute("allTags", allTags);
			model.addAttribute("otherTags", tags);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据日期获取PlanBase，没有则创建
	 * <p>	 
	 * @param model
	 * @param date
	 * @return
	 * PlanBase
	 * @see
	 * @since 1.0
	 */
	protected PlanBase getPlanBase(ModelMap model, String date) {
		
		PlanBase planBase = null;
		try {
			planBase = planBaseServiceImpl.get(eq("create_date", date));
			if(planBase == null){
				model.addAttribute("nextPlanStatus", "00");
				// 创建新的计划base
				planBase = new PlanBase();
				
				planBase.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
				planBase.setCreate_date(date);
				planBase.setExcitation_text("看着自己走过的计划，不浪费每一天");
				planBase.setPlan_name("每日计划");
				planBase.setSecret_key(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
				
				planBaseServiceImpl.insert(planBase);
				
				// 获取上一天的日期
				String preDate = TimeUtil.getDay(planBase.getCreate_date(), TimeUtil.DATE_FORMAT, -1);
				// 根据日期找计划base 最后查找符合的计划PlanDo
				PlanBase prePlanBase = planBaseServiceImpl.get("`create_date` = '"+preDate+"' ");
				if(prePlanBase == null)
//					不存在上一天的计划，则随机获取一个以往的计划
					prePlanBase = planBaseServiceImpl.getByASC("create_date").get(0);
				if(prePlanBase != null){
					List<PlanDo> list = planDoServiceImpl.gets("`plan_base_id` = '"+prePlanBase.getId()+"' ");
					
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
				} 
			}
		
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return planBase;
	}
	
	@RequestMapping("/set.chtml") 
	public String listview5(ModelMap model, String secret_key){ 
		WebsiteBase websiteBase = websiteBaseServiceImpl.get(eq("id", "1"));
		if(!secret_key.equals(websiteBase.getSecret_key()))
			return "admin/error";	
		model.addAttribute("website", websiteBase);
		return "admin/plan/set";
	}
	
	/**
	 * 校验秘钥
	 * <p>	 
	 * @param secret_key
	 * @return
	 * boolean
	 * @see
	 * @since 1.0
	 */
	protected boolean secretKeyCheck(String secret_key) {
		WebsiteBase websiteBase = websiteBaseServiceImpl.get(eq("id", "1"));
		log.info("-"+secret_key+"-"+websiteBase.getSecret_key()+"-");
		return secret_key.equals(websiteBase.getSecret_key());
	}
	
	
	protected String[] planDate(String currentDate){
		DecimalFormat df = new DecimalFormat("#.00");
		int[] arr = {-4,-3,-2,-1,0};
		StringBuffer weekDate = new StringBuffer();
		StringBuffer planCount = new StringBuffer();
		StringBuffer planAllCount = new StringBuffer();
		StringBuffer planPercent = new StringBuffer();
		
		for(int i = 0; i < arr.length; i++){
				String date = "";
				try {
					date = TimeUtil.getDay(currentDate, TimeUtil.DATE_FORMAT, arr[i]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				System.out.println(date);
				String sql1 = "SELECT * FROM plan_base a, plan_do b WHERE a.id = b.plan_base_id AND create_date = '"+date+"' AND `status` = '00'";
				int num = planDoServiceImpl.find(sql1).size();
				// num 根据日期date和条件`status`='00'查询完成的数量
				String sql0 = "SELECT * FROM plan_base a, plan_do b WHERE a.id = b.plan_base_id AND create_date = '"+date+"'";
				planAllCount.append(planDoServiceImpl.find(sql0).size()+",");
				//拼接planCount
				planCount.append(num+","); 
				// count 根据日期date 查询总数量
				String sql2 = "SELECT * FROM plan_base a, plan_do b WHERE a.id = b.plan_base_id AND create_date = '"+date+"'";
				int count = planDoServiceImpl.find(sql2).size();
				if(num == 0 || count == 0){
					log.info("numv "+num+";count: "+count+";d: 0");
					planPercent.append("0,");
				}
				else{
					double d = (double) num/count;
					log.info("num: "+num+";count: "+count+";d: "+String.valueOf(d));
					int dd = (int) (Double.parseDouble(df.format(d))*100);
					//拼接planPercent
					planPercent.append(String.valueOf(dd)+","); 
				}
				weekDate.append(date.substring(5)+",");
		}
		weekDate.deleteCharAt(weekDate.length()-1);
		planCount.deleteCharAt(planCount.length()-1);
		planPercent.deleteCharAt(planPercent.length()-1);
		planAllCount.deleteCharAt(planAllCount.length()-1);
		
		return new String[]{weekDate.toString(),
				planCount.toString(),
				planPercent.toString(),
				planAllCount.toString()};
	} 
	
	public String getPreDay(String date){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat spdf = new SimpleDateFormat(TimeUtil.DATE_FORMAT);
		try {
			c.setTime(spdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.add(Calendar.DATE, -1);
		return spdf.format(c.getTime());
	}
	
	// 返回 页面 
	@RequestMapping("/save_or_update.chtml") 
	public String save_or_update(ModelMap model, HttpServletRequest request){
		//  
		Map<String, String> parame = getRequestParameterMap(request);
		PlanBase planBase = planBaseServiceImpl.get(eq("id", 
				parame.get("id")));
		List<Data> allTags = dataServiceImpl.gets(eq("type", DATA_SIGN));
		List<PlanDo> tags = planDoServiceImpl.gets(eq("plan_base_id", planBase.getId()));
		String secretKey = websiteBaseServiceImpl.get(eq("id", "1")).getSecret_key();
		
		model.addAttribute("secret_key", secretKey);
		model.addAttribute("planBase", planBase);
		model.addAttribute("allTags", allTags);
		model.addAttribute("otherTags", tags);
		return "admin/plan/save_or_update";
	} 
	
	/**
	 * 修改
	 * @param menu
	 * @param spread
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("set.do")
	@ResponseBody
	public Object set(WebsiteBase t) throws IOException{ 
		try {
			websiteBaseServiceImpl.update(t, eq("id", "1"));
			return Message.success("修改成功。 ");
		}catch(Exception e){
			return Message.error("请求失败，运行异常； "+e.getMessage());
		}
	}
	 
	@RequestMapping("targetUpdate.do")
	@ResponseBody
	public Object targetUpdate(WebsiteBase t) throws IOException{ 
		try {
			websiteBaseServiceImpl.update(t, eq("id", "1"));
			return Message.success("修改成功。 ");
		}catch(Exception e){
			return Message.error("请求失败，运行异常； "+e.getMessage());
		}
	}
	
	/**
	 * 修改
	 * @param menu
	 * @param spread
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("update.do")
	@ResponseBody
	public Object update(PlanBase t, String new_tags, String type, HttpServletRequest request) throws IOException{ 
		try {
			
			if("1".equals(type)){
				Map<String, String> params = getRequestParameterMap(request);
				PlanDo planDo = planDoServiceImpl.get(eq("id", params.get("id")));
				planDo.setStatus(params.get("status"));
				planDoServiceImpl.update(planDo, eq("id", planDo.getId()));
				return Message.success("02".equals(params.get("status")) 
						? "取消"+planDo.getName()+"计划" 
						: "已完成"+planDo.getName()+"计划"
								, null);
			}
			log.info("修改接收参数："+t); 
			
			if(new_tags != null && !"".equals(new_tags)) {
				String[] arr = new_tags.split(",");
				for(String item : arr) {
					Data dataTable = dataServiceImpl.get(eq("name", item));
					if(dataTable == null) {
						dataTable = new Data();
						dataTable.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
						dataTable.setType(DATA_SIGN);
						dataTable.setName(item);
						dataTable.setDesc("新建plan计划");
						dataTable.setCreate_time(getNowTime());
						dataServiceImpl.insert(dataTable);
					}
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// 并不能添加进去
					planDoServiceImpl.insert(newPlanDo(t, item, dataTable.getId()));
				}
			}
			
			Map<String, String> params = getRequestParameterMap(request); 
			List<PlanDo> planDos = planDoServiceImpl.gets(eq("plan_base_id", t.getId()));
			boolean isContain;
			
			for(Map.Entry<String, String> item : params.entrySet()){	// 拼接标签id
				if("on".equals(item.getValue())) {
					isContain = false;
					String key = item.getKey();
					for(PlanDo planDo : planDos) {
						if(planDo.getPlan_tag_id().equals(key.substring(0, key.indexOf("|")))) {
							isContain = true;
							break;
						}
					}
					if(!isContain){
						planDoServiceImpl.insert(newPlanDo(t, 
								key.substring(key.indexOf("|")+1), 
								key.substring(0, key.indexOf("|"))));
					}
				}
			} 
			
			for(PlanDo planDo : planDos) {
				isContain = false;
				for(Map.Entry<String, String> item : params.entrySet()){	// 拼接标签id
					String key = item.getKey();
					if("on".equals(item.getValue()) 
							&& planDo.getPlan_tag_id().equals(key.substring(0, key.indexOf("|")))) {
						isContain = true;
						break;
					}
				} 
				if(!isContain){
					planDoServiceImpl.delete("id = "+ planDo.getId());
				}
			}	
			
			planBaseServiceImpl.update(t, eq("id", t.getId())); 
			return Message.success("编辑成功", null);
		}catch(DataIntegrityViolationException e) {
			return Message.error("修改失败，数据可能过长；"+e.getMessage(), null);
		} catch(RuntimeException e) {
			return Message.error("请求失败，运行异常； "+e.getMessage(), null);
		}
	}
	protected PlanDo newPlanDo(PlanBase t, String name, String tag_id) {
		PlanDo p = new PlanDo();
		p.setId(String.valueOf(new SnowFlakeGenerator(2, 2).nextId()));
		p.setPlan_base_id(t.getId());
		p.setName(name);
		p.setPlan_tag_id(tag_id);
		p.setStatus("02");
		p.setTime(getNowTime());
		return p;
	} 
	
}
