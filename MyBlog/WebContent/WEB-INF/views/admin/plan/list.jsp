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
  <title>每日计划</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/style/admin.css" media="all">
  
</head>
<body> 
  <div class="head" style="background: #393D49; text-align: center; color: #fff; line-height: 66px;">
  	<h1>计划</h1>
  	<ul class="layui-nav" style="position: absolute; right: 5px;top: 5px; background: none; line-height: 0px;"> 
	  <li class="layui-nav-item">
	    <i class="layui-icon layui-icon-more-vertical"></i>
	    <dl class="layui-nav-child" style="right: 0; left: unset;">
	      <dd><a href="javascript:;" click-event="statistics">计划统计</a></dd>
	      <dd><a href="javascript:;" click-event="nextPlan">明日计划</a></dd>
	      <dd><a href="javascript:;" click-event="set">设置</a></dd>
	    </dl>
	  </li>
	</ul>
  </div>
  <div class="layui-fluid">   
    <div class="layui-card" style="position: relative; overflow: hidden;">
    	<div class="layui-card-body">
		  <ul>
	        <li style="list-style-type: circle; margin-left: 32px;">
			   ${planBase.excitation_text}
			</li>
	      </ul>
		</div>
    </div>
  </div>
  <div class="layui-fluid">   
    <div class="layui-card" style="position: relative; padding-bottom: 10px; overflow: hidden;">
      <div class="layui-form" lay-filter="admin-form-plan" id="admin-form-plan">
		<div style="cursor: pointer; position:absolute; text-align: center; z-index: 99; over-flow: hidden; right: 10px; top: 7px; width: 30px; height: 30px; border-radius: 15px; background: #555; font-size: 23px; color: #fff; ">
		  <i class="layui-icon layui-icon-edit" click-event="edit" data-id="${planBase.id}"></i>
		</div>
		<div class="layui-card-header">待完成</div>
		<div class="layui-card-body">
	      <c:forEach begin="0" items="${planDo}" step="1" var="PlanDo" varStatus="varsta">
	      <c:if test="${PlanDo.status eq '02'}">
	      <div class="layui-form-item">
	      	<div class="layui-input-block" style="margin-left: 37px;"> 
	      		<input data-id="${PlanDo.id}" lay-filter="planDo" type="checkbox" lay-skin="primary" title="${PlanDo.name}">
	      	</div>
	      </div>
	      </c:if>
	      </c:forEach>
	    </div>
	  </div>
    </div>
  </div>
  <div class="layui-fluid">   
    <div class="layui-card" style="position: relative; padding-bottom: 10px; overflow: hidden;">
      <div class="layui-form" lay-filter="admin-form-plan" id="admin-form-plan">
		<div class="layui-card-header">已完成</div>
		<div class="layui-card-body">
		  <ul>
	      <c:forEach begin="0" items="${planDo}" step="1" var="PlanDo" varStatus="varsta">
	      <c:if test="${PlanDo.status eq '00'}">
	        <li style="list-style-type: circle; margin-left: 32px;">
			  <label>${PlanDo.name}</label>
			  <label style="color:rgb(32, 183, 89);">
				<i class="layui-icon layui-icon-ok-circle"></i>
			  </label>
			</li>
	      </c:if>
	      </c:forEach>
	      </ul>
	    </div>
	  </div>
    </div>
  </div>
  <div class="layui-fluid">   
    <div class="layui-card" style="position: relative; padding-bottom: 10px; overflow: hidden;">
      <div class="layui-form" lay-filter="admin-form-plan" id="admin-form-plan">
		<div style="cursor: pointer; position:absolute; text-align: center; z-index: 99; over-flow: hidden; right: 45px; top: 7px; width: 30px; height: 30px; border-radius: 15px; background: #555; font-size: 23px; color: #fff; ">
		  <i class="layui-icon layui-icon-ok" click-event="week_complete" data-id="${planBase.id}"></i>
		</div>
		<div style="cursor: pointer; position:absolute; text-align: center; z-index: 99; over-flow: hidden; right: 10px; top: 7px; width: 30px; height: 30px; border-radius: 15px; background: #555; font-size: 23px; color: #fff; ">
		  <i class="layui-icon layui-icon-edit" click-event="week_edit" data-id="${planBase.id}"></i>
		</div>
		<div class="layui-card-header">周目标</div>
		<div class="layui-card-body">
		  <%-- <ul>
	      <c:forEach begin="0" items="${planDo}" step="1" var="PlanDo" varStatus="varsta">
	      <c:if test="${PlanDo.status eq '00'}">
	        <li style="list-style-type: circle; margin-left: 32px;">
			  <label>${PlanDo.name}</label>
			  <label style="color:rgb(32, 183, 89);">
				<i class="layui-icon layui-icon-ok-circle"></i>
			  </label>
			</li>
	      </c:if>
	      </c:forEach>
	      </ul> --%>
	    </div>
	  </div>
    </div>
  </div>
  <div class="layui-fluid">   
    <div class="layui-card" style="position: relative; padding-bottom: 10px; overflow: hidden;">
      <div class="layui-form" lay-filter="admin-form-plan" id="admin-form-plan">
		<div style="cursor: pointer; position:absolute; text-align: center; z-index: 99; over-flow: hidden; right: 45px; top: 7px; width: 30px; height: 30px; border-radius: 15px; background: #555; font-size: 23px; color: #fff; ">
		  <i class="layui-icon layui-icon-ok" click-event="mounth_complete" data-id="${planBase.id}"></i>
		</div>
		<div style="cursor: pointer; position:absolute; text-align: center; z-index: 99; over-flow: hidden; right: 10px; top: 7px; width: 30px; height: 30px; border-radius: 15px; background: #555; font-size: 23px; color: #fff; ">
		  <i class="layui-icon layui-icon-edit" click-event="mounth_edit" data-id="${planBase.id}"></i>
		</div>
		<div class="layui-card-header">月目标</div>
		<div class="layui-card-body">
		  <%-- <ul>
	      <c:forEach begin="0" items="${planDo}" step="1" var="PlanDo" varStatus="varsta">
	      <c:if test="${PlanDo.status eq '00'}">
	        <li style="list-style-type: circle; margin-left: 32px;">
			  <label>${PlanDo.name}</label>
			  <label style="color:rgb(32, 183, 89);">
				<i class="layui-icon layui-icon-ok-circle"></i>
			  </label>
			</li>
	      </c:if>
	      </c:forEach>
	      </ul> --%>
	    </div>
	  </div>
    </div>
  </div>
 <script src="<%=basePath%>layuiadmin/layui/layui.js"></script>
  <script>
  var token = top.token;
  var table;
  layui.config({
    base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'admin', 'util', 'form'], function(){
    var $ = layui.$
    ,form = layui.form
    ,util = layui.util
	,b = 'admin-plan-update'
    ,f = "iframe"
	,t = 'admin-form-plan'
    ,admin = layui.admin;
    if('${isTips}' != '0'){
    	layer.open({
            type: 1
            ,title: false //不显示标题栏
            ,closeBtn: false
            ,area: ["90%", "60%"]
            ,shade: 0.8
            ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
            ,btn: ['了解']
            ,btnAlign: 'c'
            ,moveType: 1 //拖拽模式，0或者1
            ,content: '<div style="padding: 50px; line-height: 22px; min-height: 69%; background-color: #393D49; color: #fff; font-weight: 300;">显示日期^_^<br><br>${planBase.excitation_text}</div>'
        });
    }
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
	    ,set: function(){
			// 打开设置页面
			layer.open({
		        type: 2 
		        ,title: '设置'
		        ,area: ['auto']
		        ,shade: 0
		        ,content: '<%=basePath%>admin/plan/set.chtml?secret_key=${secret_key}'
		        ,success: function(layero, index){
		          layer.setTop(layero); 
		          layer.full(index);
		        }
		   });
		}
		,nextPlan: function(){
			// 打开明日计划页面
			layer.open({
		        type: 2 
		        ,title: '明日计划'
		        ,area: ['auto']
		        ,shade: 0
		        ,content: '<%=basePath%>admin/plan/nextPlan.chtml?secret_key=${secret_key}'
		        ,success: function(layero, index){
		          layer.setTop(layero); 
		          layer.full(index);
		        }
		   });
		}
		,statistics: function(){
			// 打开统计页面
			layer.open({
		        type: 2 
		        ,title: '计划统计'
		        ,area: ['auto']
		        ,shade: 0
		        ,content: '<%=basePath%>admin/plan/statistics.chtml?secret_key=${secret_key}'
		        ,success: function(layero, index){
		          layer.setTop(layero); 
		          layer.full(index);
		        }
		   });
		}
    });
    // checkbox 点击事件
    // isTips 是否有激励语提示框
    form.on('checkbox(planDo)', function(obj){
	 	console.log(obj.elem.checked);
	 	var status = obj.elem.checked ? "00" : "02"
	 		   ,id = $(obj.elem).data("id");
	 	admin.cajax({
			method: 'update'
			,data: {status: status, id: id, type: "1", token: toekn}  
	 		,success: function(){
	 			self.location.href='<%=basePath%>admin/plan/show.chtml?secret_key=${secret_key}&isTips=0'
	 		}
		});
	 	return false;
	})
  });
  </script>
</body>
</html>

