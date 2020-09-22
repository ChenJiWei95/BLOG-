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
  <title>机构管理 iframe 框</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/style/admin.css" media="all">
</head>
<body style="padding-right: 20px;">  
  <div class="layui-form" lay-filter="layuiadmin-form-data" id="layuiadmin-form-data" style="padding: 20px 30px 0 0;">
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
		<label class="layui-form-label">值</label>
		<div class="layui-input-inline">
			<input type="text" name="value" placeholder="请输入值" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">代码</label>
		<div class="layui-input-inline">
			<input type="text" name="code" placeholder="请输入代码" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">类型</label>
		<div class="layui-input-inline">
			<select name="type">
				<option value="-1">请选择类型</option>
				<c:forEach begin="0" items="${datas}" step="1" var="Data" varStatus="varsta">
					<option value="${Data.value}">${Data.name}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">创建时间</label>
		<div class="layui-input-inline">
			<input type="text" name="create_time" disabled placeholder="请输入创建时间" autocomplete="off" class="layui-input layui-disabled">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">修改时间</label>
		<div class="layui-input-inline">
			<input type="text" name="update_time" disabled placeholder="请输入修改时间" autocomplete="off" class="layui-input layui-disabled">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">描述</label>
		<div class="layui-input-inline">
			<textarea class="layui-textarea" name="desc" placeholder="请输入描述信息"></textarea>
		</div>
	</div>
    <div class="layui-form-item layui-hide">
      <button lay-submit lay-filter="set-data-form-add" id="set-data-form-add">添加</button>
	  <button lay-submit lay-filter="set-data-form-edit" id="set-data-form-edit">修改</button>
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
		var table = layui.table
		,$ = layui.$
		,a = 'set-data-form-add'
		,b = 'set-data-form-edit'
		,l = 'LAY-app-set-data'
		,form = layui.form
		,admin = layui.admin;
		
		//添加
		form.on("submit("+a+")", function(data){
			//执行 Ajax 后重载 
			admin.cajax({
				method: 'add'
				,id: l
				,data: $.extend(data.field, {token : token})  
			});		  
			return !1;
		})
		//编辑
		,form.on("submit("+b+")", function(data){
			//console.log(JSON.stringify(data.field));
			//执行 Ajax 后重载
			admin.cajax({
				method: 'update'
				,id: l
				,data: $.extend(data.field, {token : token})  
			});
			return !1;
		})
  })
  </script>
</body>
</html>