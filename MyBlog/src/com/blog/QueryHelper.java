package com.blog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.blog.Filter.Operator;
import com.blog.Order.Direction;
import com.blog.util.CommonUtil;
import com.sun.istack.internal.logging.Logger;


/**
 * <b>查询通用帮助类 按一定格式处理 </b>
 * <p>
 * 例如：Qu_x#name_eq_s  得出查询语句为： and x.name = :name 其中s是指string
 * S=String L=Long I=Integer D=Double DT=Date ST=Short BD=BigDecimal F=Float 
 * 描述:<br>
 * 
 * @author 威 
 * <br>2019年10月18日 下午11:25:27 
 * @see
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class QueryHelper {
	
	private static Logger log = Logger.getLogger(QueryHelper.class); // 日志对象
	
	@SuppressWarnings("rawtypes")
	private Page page;//存在对应查询及排序信息
	private StringBuffer hql;
	private Map<String,Object> params = new HashMap<String, Object>();		//查询参数及对应值
	private Map<String,String> cloumnAlias = new HashMap<String, String>();	//前端列 别名 防止前端列名中有下划线影响列名解析  
	List<Object> batisParams = new ArrayList<Object>();						//查询参数及对应值
	
	/**
	 * 前端列名 别名 防止前端列名中有下划线影响列名解析 	<br>
	 * 前端设定为createDate 实际数据库为 create_date   <br>
	 * 因此调用addCloumnAlias("createDate", "create_date"); 在生成sql语句时将列名转化为真实列名 <br>
	 * <p>	 
	 * @param alias
	 * @param cloumn
	 * void
	 * @see
	 * @since 1.0
	 */
	public void addCloumnAlias(String alias, String cloumn){
		this.cloumnAlias.put(alias, cloumn);
	}
	
	/**
	 * 查询参数绑定
	 * @param request
	 * @param page
	 * @return 
	 */
	
	public void paramBind(HttpServletRequest request,Page page){
		this.page = page;
		if (request.getMethod().equalsIgnoreCase("POST")) {
			Enumeration<String> names = request.getParameterNames();
			while (names.hasMoreElements()) {
				String name = names.nextElement();
				String value = request.getParameter(name);
				//System.out.println(name+"---"+value);
				this.addFilter(name,value);
			}
		}
		
		if(StringUtils.isNotEmpty(page.getSort())){
			Order order = new Order();
			order.setProperty(page.getSort());
			if(StringUtils.isNotEmpty(page.getOrder())){
				order.setDirection(Direction.fromString(page.getOrder()));
			}
			this.page.addOrder(order);
		}
		
	}
	
	/**
	 * 增加查询条件
	 * @param name
	 * @param value
	 */
	private void addFilter(String name,String value){
		//log.info("接收:"+name);
		if(name.startsWith(SysConstant.QUERY_PRE) && StringUtils.isNotEmpty(value)){
			//log.info("接收:"+name);
			String[] params = name.split(SysConstant.QUERY_SPIT);
			if(params.length == 4){
				Filter filter = new Filter();
				filter.setProperty(params[1].replaceAll("#", "."));
				filter.setOperator(Operator.fromString(params[2]));
				filter.setValue(this.getValue(params[3],value));
				this.page.addFilter(filter);
			} else {
				System.err.println(name+"属性非法！");
			}
		} else if(name.startsWith(SysConstant.UPDATE_PRE) && StringUtils.isNotEmpty(value)){
			String[] params = name.split(SysConstant.QUERY_SPIT);
			if(params.length == 3){
				UpdateItem updateItem = new UpdateItem();
				updateItem.setProperty(params[1].replaceAll("#", "."));
				updateItem.setValue(this.getValue(params[2],value));
				this.page.addUpdateItem(updateItem);
			} else {
				System.err.println(name+"属性非法！");
			}
		} else {
			System.err.println(name+"属性非法！");
		}
	}
	/**
	 * update 语句中获取值
	 * <p>	 
	 * @param valueType
	 * @param value
	 * @return
	 * Object
	 * @see
	 * @since 1.0
	 */
	private Object getValue2(String valueType,String value) {
		value = value.trim();
		try {
			if(StringUtils.endsWithIgnoreCase(valueType, "s")){
				return "'"+value+"'";
			}
			return value;
		} catch (Exception e) {
			log.info("类型转换失败",e);
		}
		
		return null;
	}
	/**
	 * 将对应类型转换
	 * @param valueType
	 * @param value
	 * @return
	 */
	private Object getValue(String valueType,String value) {
		value = value.trim();
		try {
			if(StringUtils.endsWithIgnoreCase(valueType, "s")){
				return value;
			}
			
			if(StringUtils.endsWithIgnoreCase(valueType, "l")){
				return Long.parseLong(value);
			}
			
			if(StringUtils.endsWithIgnoreCase(valueType, "i")){
				return Integer.parseInt(value);
			}
			
			if(StringUtils.endsWithIgnoreCase(valueType, "d")){
				return Double.parseDouble(value);
			}
			
			if(StringUtils.endsWithIgnoreCase(valueType, "dt")){
				return DateUtils.parseDate(value, SysConstant.DATE_PATTERNS);
			}
			
			if(StringUtils.endsWithIgnoreCase(valueType, "bd")){
				return new BigDecimal(value);
			}
			
			if(StringUtils.endsWithIgnoreCase(valueType, "f")){
				return Float.parseFloat(value);
			}
		} catch (Exception e) {
			log.info("类型转换失败",e);
		}
		
		return null;
	}
	
	
	/**
	 * 创建查询语句包含排序
	 * @param page
	 * @return
	 */
	public String buildAllQuery(Page page){
		hql = new StringBuffer();
		hql.append(this.buildUpdate(page.getUpdateItems()));
		hql.append(this.buildQuery(page.getFilters()));
		hql.append(this.buildOrder(page.getOrders(),page.getAlias()));
		return hql.toString();
	}
	
//	/**
//	 * 组装查询语句
//	 * @param page
//	 */
//	public String buildQuery(Page page){
//		List<Filter> filters = page.getFilters();
//		return this.buildQuery(filters);
//	}
	
	/**
	 * 根据过滤条件组装查询语句
	 * @param filters
	 * @return
	 */
	public String buildQuery(List<Filter> filters){
		StringBuffer hql = new StringBuffer();
		hql.append(" where 1=1 ");
		for(Filter filter : filters){
			hql.append(" and ");
			String trueCloumn = this.cloumnAlias.get(filter.getProperty());
			log.info(filter.getProperty() + " " + trueCloumn);
			hql.append(trueCloumn == null ? filter.getProperty() : trueCloumn);
			hql.append(filter.getQueryOperator());
			if(!filter.getOperator().equals(Operator.is) && !filter.getOperator().equals(Operator.no)){
				mybatis(hql, filter);
			}
		}
		return hql.toString();
	}

	protected void Hibernate(StringBuffer hql, Filter filter) {
		int count = CommonUtil.cal();
		hql.append(":");
		String property = filter.getProperty().replaceAll("\\.", "_");
		hql.append(property);
		hql.append(count);
		if(filter.getOperator().equals(Operator.lk)){
			params.put(property + count, "%" + filter.getValue() + "%");
		}else if(filter.getOperator().equals(Operator.slk)){
			params.put(property + count, filter.getValue() + "%");
		}else{
			params.put(property + count, filter.getValue());
		}
	}
	
	protected void mybatis(StringBuffer hql, Filter filter) {
		if(filter.getOperator().equals(Operator.lk)){
			hql.append("'%" + filter.getValue() + "%'");
		}else if(filter.getOperator().equals(Operator.slk)){
			hql.append("'" + filter.getValue() + "%'");
		}else {
			hql.append("String".equals(filter.getValue().getClass().getSimpleName()) ? "'"+filter.getValue()+"'" : filter.getValue());
		}
	}
	
	/*public void Hibernate(StringBuffer hql, String property, int count){
		
	}*/
	/**
	 * 例如： SET name='cjw' AND age='12'
	 * <p>	 
	 * @param items
	 * @return
	 * String
	 * @see
	 * @since 1.0
	 */
	public String buildUpdate(List<UpdateItem> items){
		if(items.size() == 0) return "";
		StringBuffer hql = new StringBuffer();
		hql.append(" set ");
		for(UpdateItem item : items){
			
			String trueCloumn = this.cloumnAlias.get(item.getProperty());
			hql.append(trueCloumn == null ? item.getProperty() : trueCloumn);
			hql.append(item.getQueryOperator());
			hql.append("String".equals(item.getValue().getClass().getSimpleName()) ? "'"+item.getValue()+"'" : item.getValue());
			hql.append(", ");
			 
		}
		hql.delete(hql.length() - 2, hql.length());
		return hql.toString();
	}
	
	/**
	 * 根据过滤条件组装查询语句
	 * @param filters
	 * @return
	 */
	public String buildQuery(List<Filter> filters, String tablesInfo){
		StringBuffer hql = new StringBuffer();
		
		if(null == tablesInfo || "".equals(tablesInfo)) {
			hql.append(" where 1=1 ");
		}
		else {
			hql.append(" where " + tablesInfo);
		}
		
		for(Filter filter : filters){
			hql.append(" and ");
			String trueCloumn = this.cloumnAlias.get(filter.getProperty());
			log.info(filter.getProperty() + " " + trueCloumn);
			hql.append(trueCloumn == null ? filter.getProperty() : trueCloumn);
			hql.append(filter.getQueryOperator());
			if(!filter.getOperator().equals(Operator.is) && !filter.getOperator().equals(Operator.no)){
				int count = CommonUtil.cal();
				hql.append(":");
				String property = filter.getProperty().replaceAll("\\.", "_");
				hql.append(property);
				hql.append(count);
				if(filter.getOperator().equals(Operator.lk)){
					params.put(property + count, "%" + filter.getValue() + "%");
				}else{
					params.put(property + count, filter.getValue());
				}
			}
		}
		return hql.toString();
	}
	
	/**
	 * 创建排序
	 * @param page
	 * @return
	 */
	public String buildOrder(List<Order> orders,String alias){
		if(orders == null){
			return "";
		}
		StringBuffer hql = new StringBuffer();
		int size = orders.size();
		if(size > 0 ){
			hql.append(" order by ");
			if(size > 1){
				for(int i = 0; i < size - 1; i++){
					if(StringUtils.isNotEmpty(alias)){
						hql.append(alias);
						hql.append(".");
					}
					hql.append(orders.get(i).getProperty());
					
					hql.append(" ");
					hql.append(orders.get(i).getDirection().toString());
					hql.append(",");
				}
			}
			
			if(StringUtils.isNotEmpty(alias)){
				hql.append(alias);
				hql.append(".");
			}
			
			hql.append(orders.get(size -1).getProperty());
			hql.append(" ");
			hql.append(orders.get(size -1).getDirection().toString());
		}
		
		return hql.toString();
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
