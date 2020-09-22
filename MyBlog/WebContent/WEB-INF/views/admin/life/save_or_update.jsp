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
  <title>iframe-LifeShare</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
</head>
<body style="padding-right:20px">
  <div class="layui-form" lay-filter="C-admin-life-form" id="C-admin-life-form" style="padding: 20px 30px 0 0;">
    <div class="layui-hide">
		<label class="layui-form-label">ID</label>
		<div class="layui-input-inline">
			<input type="text" name="id" disabled autocomplete="off" class="layui-input layui-disabled">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">时间</label>
		<div class="layui-input-inline">
			<input type="text" name="time" disabled placeholder="请输入时间" autocomplete="off" class="layui-input layui-disabled">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">图片路径</label>
		<div class="layui-input-inline">
			<input type="text" name="pit_url" placeholder="请输入" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">评论数</label>
		<div class="layui-input-inline">
			<input type="text" name="chat_num" placeholder="请输入评论数" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">浏览数</label>
		<div class="layui-input-inline">
			<input type="text" name="brow_num" placeholder="请输入浏览数" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">喜欢数</label>
		<div class="layui-input-inline">
			<input type="text" name="like_num" placeholder="请输入喜欢数" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">内容</label>
		<div class="layui-input-inline">
			<textarea class="layui-textarea" name="content" placeholder="请输入描述信息"></textarea>
		</div>
	</div>

    <div class="layui-form-item layui-hide">
      <button lay-submit lay-filter="C-admin-life-add" id="C-admin-life-add">添加</button>
	  <button lay-submit lay-filter="C-admin-life-update" id="C-admin-life-update">修改</button>
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
	,a = "C-admin-life-add"
	,b = 'C-admin-life-update'
    ,l = "C-admin-life-table";
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
