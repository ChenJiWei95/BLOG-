<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>晚安提示</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/style/admin.css" media="all">
  
</head>
<body>  
  <div class="head" style="background: #393D49; text-align: center; color: #fff; line-height: 66px;">
  	<h1>早睡早起</h1>
  </div>
  <div class="layui-fluid">   
    <div class="layui-card">
              <div class="layui-card-header">数据概览</div>
              <div class="layui-card-body">
                
                <div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade" lay-filter="LAY-index-dataview">
                  <div carousel-item id="LAY-index-dataview">
                    <div><i class="layui-icon layui-icon-loading1 layadmin-loading"></i></div>
                    <div></div>
                  </div>
                </div>
                
              </div>
    </div>
    <div class="layui-card" style="position: relative; padding-bottom: 10px; overflow: hidden;">
      <div class="layui-form" lay-filter="admin-form-plan" id="admin-form-plan">
		<div class="layui-card-header">${dayText1}</div>
		<div class="layui-card-body">
			<ul>
				<c:forEach begin="0" items="${todayPlanDos}" step="1" var="PlanDo" varStatus="varsta">
		        <li style="list-style-type: circle; margin-left: 32px;">
					<label>${PlanDo.name}</label>
					<c:if test="${PlanDo.status eq '00'}">
					<label style="color:rgb(32, 183, 89);">
						<i class="layui-icon layui-icon-ok-circle"></i>
					</label>
					</c:if>
				</li>
		        </c:forEach>
			</ul>
		</div>
	  </div>
    </div>
    <div class="layui-card" style="position: relative; padding-bottom: 10px; overflow: hidden;">
      <div class="layui-form" lay-filter="admin-form-plan" id="admin-form-plan">
        <div class="layui-card-header">${dayText2}</div>
		<div class="layui-card-body">
			 <ul>
			    <c:forEach begin="0" items="${yesterdayPlanDos}" step="1" var="PlanDo" varStatus="varsta">
		        <li style="list-style-type: circle; margin-left: 32px;">
					<label>${PlanDo.name}</label>
					<c:if test="${PlanDo.status eq '00'}">
					<label style="color:rgb(32, 183, 89);">
						<i class="layui-icon layui-icon-ok-circle"></i>
					</label>
					</c:if>
				</li>
		        </c:forEach>
		  	</ul>
		</div>
	  </div>
    </div>
    <c:if test="${nextPlanStatus} eq '00'">
    <div class="layui-card">
          <div class="layui-card-header">制定明日计划</div>
          <div class="layui-card-body" pad15>
              <div class="layui-form" lay-filter="admin-form-plan" id="admin-form-plan" style="padding: 20px 30px 0 0;">
				<div class="layui-hide">
					<label class="layui-form-label">ID</label>
					<div class="layui-input-block">
						<input type="text" name="id" lay-verify="id" value="${planBase.id}" autocomplete="off" class="layui-input">
					</div>
				</div> 
				<div class="layui-form-item">
			      <label class="layui-form-label">激励语</label>
			      <div class="layui-input-block">
			        <input type="text" value="${planBase.excitation_text}" name="excitation_text" lay-verify="required" autocomplete="off" class="layui-input">
			      </div>
			    </div>  
			    <div class="layui-form-item">
			      <label class="layui-form-label">新建计划项</label>
			      <div class="layui-input-block">
			        <input type="text" name="new_tags" placeholder="多个新建由‘,’分隔开" autocomplete="off" class="layui-input">
			      </div>
			    </div>  
				<div class="layui-form-item">
			      <label class="layui-form-label">选取已有项</label>
			      <div class="layui-input-block"> 
			        <!-- 循环判断已授权和未授权的模块 -->
			        <c:forEach begin="0" items="${allTags}" step="1" var="Data" varStatus="varsta"> 
			        	<c:set var="iscontain" value="false" />
			        	<c:set var="isfinish" value="false" />
			        	<c:forEach begin="0" items="${otherTags}" step="1" var="PlanDo" varStatus="varsta">
			        		<c:if test="${Data.id eq PlanDo.plan_tag_id}">
			        			<c:set var="iscontain" value="true"/>
			        			<c:if test="${PlanDo.status eq '00'}">
			        				<c:set var="isfinish" value="true" />
			        			</c:if>
			        		</c:if> 
			        	</c:forEach>  
			        	<input type="checkbox"
			        	<c:if test="${iscontain}"> checked </c:if>
			        	<c:if test="${isfinish}"> disabled=""</c:if>
			        	name="${Data.id}|${Data.name}" lay-skin="primary" title="${Data.name}"/> 
					</c:forEach>
			      </div>
			    </div>
				 
			    <div class="layui-form-item">
				  <button class="layui-btn c-button" lay-submit lay-filter="admin-plan-update" id="admin-plan-update">确认</button>
			    </div>
			  </div>
          </div>
        </div>
    </c:if>
    
  </div>
 <script src="<%=basePath%>layuiadmin/layui/layui.js"></script>
  <script>
  var token = top.token;
  var table;
  var weekDate = '${weekDate}'.split(",")
  ,planCount = '${planCount}'.split(",")
  ,planPercent = '${planPercent}'.split(",")
  ,planAllCount = '${planAllCount}'.split(",");
  layui.config({
    base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
    ,plandata: 'admin/plan/plandata'
  }).use(['index', 'admin', 'util', 'form', 'plandata'], function(){
    var $ = layui.$
    ,form = layui.form
    ,util = layui.util
	,b = 'admin-plan-update'
    ,f = "iframe"
	,t = 'admin-form-plan'
    ,admin = layui.admin;
    layer.open({
        type: 1
        ,title: false //不显示标题栏
        ,closeBtn: false
        ,area: ["90%", "60%"]
        ,shade: 0.8
        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
        ,btn: ['好的']
        ,btnAlign: 'c'
        ,moveType: 1 //拖拽模式，0或者1
        ,content: '<div style="padding: 50px; line-height: 22px; min-height: 69%; background-color: #393D49; color: #fff; font-weight: 300;">晚安警告^_^<br><br>一个人最好的生活状态，有自己的生活和情趣，努力完善自己。没人爱时专注自己，有人爱时，有能努力拥抱彼此。</div>'
         
    });
    util.clickEvent({
    	edit: function(e){
			layer.open({
                type: 2,
                title: "编辑计划",
                content: "save_or_update.chtml?id="+e.data("id"),
                area: ["90%", "60%"],
                btn: ["确定", "取消"],
                yes: function(index, layero) {
                    layero.find(f).contents().find("#"+b).click();
                }
            })
		} 
    });
    
    form.on('checkbox(planDo)', function(obj){
	 	console.log(obj.elem.checked);
	 	var status = obj.elem.checked ? "00" : "02"
	 		   ,id = $(obj.elem).data("id");
	 	admin.cajax({
			method: 'update'
			,data: {status: status, id: id, type: "1"}   
		});
	 	return false;
	})
	form.on("submit("+b+")", function(data){
		admin.cajax({
			method: 'update'
			,data: $.extend(data.field, {token : token})
			,success: function(){
				layui.layer.msg("明日计划已生成，晚安！");
			}
		});	  
		return false;
	});
  });
  </script>
</body>
</html>

