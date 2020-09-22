<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>iframe-标签管理</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
</head>
<body style="padding-right:20px">
  <div class="layui-form" lay-filter="C-admin-atag-form" id="C-admin-atag-form" style="padding: 20px 30px 0 0;">
	<div class="layui-hide">
		<label class="layui-form-label">ID</label>
		<div class="layui-input-inline">
			<input type="text" name="id" disabled autocomplete="off" class="layui-input layui-disabled">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">名称</label>
		<div class="layui-input-inline">
			<input type="text" name="name" placeholder="请输入名称" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">创建时间</label>
		<div class="layui-input-inline">
			<input type="text" name="create_time" disabled placeholder="请输入" autocomplete="off" class="layui-input layui-disabled">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">修改时间</label>
		<div class="layui-input-inline">
			<input type="text" name="update_time" disabled placeholder="请输入创建时间" autocomplete="off" class="layui-input layui-disabled">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">描述</label>
		<div class="layui-input-inline">
			<textarea class="layui-textarea" name="desc" placeholder="请输入描述信息"></textarea>
		</div>
	</div>
  	<div class="layui-form-item layui-hide">
      <button lay-submit lay-filter="C-admin-atag-add" id="C-admin-atag-add">添加</button>
	  <button lay-submit lay-filter="C-admin-atag-update" id="C-admin-atag-update">修改</button>
	</div>
  </div>
  <script src="<%=basePath%>layuiadmin/layui/layui.js"></script>  
  <script>
  var token = top.token;
  layui.config({
    base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'form', 'admin'], function(){
    var $ = layui.$
    ,form = layui.form 
	,admin = layui.admin
	,a = "C-admin-atag-add"
	,b = 'C-admin-atag-update'
    ,l = "C-admin-atag-table";
	form.on("submit("+a+")", function(data){
		admin.cajax({
			method: 'add'
			,id: l
			,data: $.extend(data.field, {token : token})  
		});
		return false;
	})
	,form.on("submit("+b+")", function(data){
		admin.cajax({
			method: 'update'
			,id: l
			,data: $.extend(data.field, {token : token})
		});
		return false;
	}) 
  })
  </script>
</body>
</html>
