<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
  <meta name="Author" content="cjw">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <title>机构管理</title>
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/style/admin.css" media="all">
  <style>
	*{margin: 0; padding: 0;}
	.manage-button, .layui-card {border-radius: 5px;}
	.manage-button {background: #555;}

  </style>
 </head>
 <body layadmin-themealias="default">
	<div class="layui-fluid">
		<div class="layui-row layui-col-space15">
			<div class="layui-col-md12"> 
				<div class="layui-card layui-circle">
					<div class="layui-card-header layuiadmin-card-header-auto">
						<button class="layui-btn manage-button" data-type="add">添加</button>  
					</div>
					<div class="layui-card-body">
						<div class="layui-collapse" id="show-manage">
						</div>	  	 
					</div>
					 
				</div>
			</div>
		</div>
	</div>  
	<script src="<%=basePath%>layuiadmin/layui/layui.js?t=1"></script>
	<script>  
	var token = top.token;
	var oparate_active, initAjax;// 子页面调用 active 
	layui.config({
		base: '<%=basePath%>layuiadmin/' // 静态资源所在路径
	}).extend({
		index: 'lib/index' // 主入口模块
		,tree_etc: 'modules/tree_etc'
	}).use(['index', 'menuEditor'], function() {
		
		var layer  = layui.layer 
		,$ = layui.jquery
		,index = layui.index
		,form = layui.form 
		,menuEditor = layui.menuEditor;
		
		
		
		
	}); 
	</script>
 </body>
</html>