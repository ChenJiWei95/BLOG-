<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
  <!--<link rel="stylesheet" href="../../layuiadmin/style/admin.css" media="all">-->

</head>
<body style="padding-right=20px">
	<div class="layui-form" lay-filter="branch-form-tags" id="branch-form-tags" style="padding:  20px 30px 0 0; text-align: center; background: #fff">
		<div class="layui-hide-item">
			<label class="layui-form-label">ID：</label
			<div class="layui-input-block"> 
				<label class="layui-form-label" name="id"></label> 
			</div>
		</div> 
		<div class="layui-form-item">
			<label class="layui-form-label">名称：</label>
			<div class="layui-input-block">
				<label class="layui-form-label" name="name"></label> 
			</div>
		</div> 
		<div class="layui-form-item">
			<label class="layui-form-label">创建时间：</label>
			<div class="layui-input-block">
				<label class="layui-form-label" name="create_time"></label> 
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">修改时间：</label>
			<div class="layui-input-block">
				<label class="layui-form-label" name="update_time"></label> 
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">优先级：</label>
			<div class="layui-input-block"> 
				<label class="layui-form-label" name="priority"></label>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">链接：</label>
			<div class="layui-input-inline"> 
				<label class="layui-form-label" name="url"></label>
			</div> 
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">备注：</label>
			<div class="layui-input-block">
				<textarea placeholder="请输入内容" class="layui-textarea" name="msg"></textarea>
			</div>
		</div>   
	</div> 
</body>
</html>