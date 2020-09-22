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
  <title>管理员管理 操作</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
</head>
<body style="padding-right:20px">
  <div class="layui-form" lay-filter="C-admin-temp-form" id="C-admin-temp-form" style="padding: 20px 30px 0 0;">
    <div class="layui-hide">
		<label class="layui-form-label">ID</label>
		<div class="layui-input-block">
			<input type="text" name="id" lay-verify="id" autocomplete="off" class="layui-input">
		</div>
	</div> 
	<div class="layui-hide">
		<label class="layui-form-label">ID</label>
		<div class="layui-input-block">
			<input type="text" name="c_id" lay-verify="id" autocomplete="off" class="layui-input">
		</div>
	</div> 
	<div class="layui-form-item">
      <label class="layui-form-label">属性标识</label>
      <div class="layui-input-inline">
        <input type="text" name="field" autocomplete="off" class="layui-input">
      </div>
    </div>
	<div class="layui-form-item">
      <label class="layui-form-label">显示值</label>
      <div class="layui-input-inline">
        <input type="text" name="title" autocomplete="off" class="layui-input">
      </div>
    </div> 
	<div class="layui-form-item">
      <label class="layui-form-label">引用模板</label>
      <div class="layui-input-inline">
        <input type="text" name="templet" autocomplete="off" class="layui-input">
      </div>
    </div> 
	<div class="layui-form-item">
      <label class="layui-form-label">宽度</label>
      <div class="layui-input-inline">
        <input type="text" name="width" autocomplete="off" class="layui-input">
      </div>
    </div> 
    <div class="layui-form-item">
      <label class="layui-form-label">对齐方式</label>
      <div class="layui-input-inline">
        <select name="align">
		  <option value="center">居中</option> 
		  <option value="left">左</option> 
		  <option value="right">又</option> 
           
        </select>
	  </div>
    </div> 
	<div class="layui-form-item">
      <label class="layui-form-label">是否可排序</label>
      <div class="layui-input-inline">
        <select name="sort">
		  <option value="01">否</option> 
		  <option value="00">是</option> 
        </select> 
        </select>
	  </div>
    </div> 
    <div class="layui-form-item layui-hide">
      <input type="button" lay-submit lay-filter="C-admin-temp-add" id="C-admin-temp-add" value="确认">
      <input type="button" lay-submit lay-filter="C-admin-temp-update" id="C-admin-temp-update" value="确认">
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
	,a = "C-admin-temp-add"
	,b = 'C-admin-temp-update'
    ,l = "C-admin-table-table";
  	
	form.on("submit("+a+")", function(data){
		console.log("submit... ========================");
		console.log(data.field);
		admin.cajax({
			method: 'table_add'
			,id: l
			,data: $.extend(data.field, {token : token})
		});
		return false;
	})
	,form.on("submit("+b+")", function(data){
		admin.cajax({
			method: 'table_update'
			,id: l
			,data: $.extend(data.field, {token : token})  
		});
		return false;
	}) 
  })
  </script>
</body>
</html>