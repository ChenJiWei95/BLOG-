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
  </div>
 <script src="<%=basePath%>layuiadmin/layui/layui.js"></script>
  <script>
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
  });
  </script>
</body>
</html>

