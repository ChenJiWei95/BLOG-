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
      <label class="layui-form-label">组件名称</label>
      <div class="layui-input-inline">
        <input type="text" name="name" autocomplete="off" class="layui-input">
      </div>
    </div>
	<div class="layui-form-item">
      <label class="layui-form-label">前置语</label>
      <div class="layui-input-inline">
        <input type="text" name="label" autocomplete="off" class="layui-input">
      </div>
    </div> 
	<div class="layui-form-item">
      <label class="layui-form-label">表单值</label>
      <div class="layui-input-inline">
        <input type="text" name="value" autocomplete="off" class="layui-input">
      </div>
    </div> 
	<div class="layui-form-item">
      <label class="layui-form-label">提示</label>
      <div class="layui-input-inline">
        <input type="text" name="placeholder" autocomplete="off" class="layui-input">
      </div>
    </div> 
    <div class="layui-form-item">
      <label class="layui-form-label">html</label>
      <div class="layui-input-inline">
        <input type="text" name="html" autocomplete="off" class="layui-input">
      </div>
    </div> 
    <div class="layui-form-item">
      <label class="layui-form-label">date数据</label>
      <div class="layui-input-inline">
        <input type="text" name="type_date" autocomplete="off" class="layui-input">
      </div>
    </div>  
    <div class="layui-form-item">
      <label class="layui-form-label">是否禁用</label>
      <div class="layui-input-inline">
        <select name="disable">
		  <option value="01">否</option> 
		  <option value="00">是</option> 
        </select> 
      </div>
    </div> 
    <div class="layui-form-item">
      <label class="layui-form-label">是否内联</label>
      <div class="layui-input-inline">
        <select name="inline">
		  <option value="01">否</option> 
		  <option value="00">是</option> 
        </select>
      </div>
    </div> 
    <div class="layui-form-item">
      <label class="layui-form-label">是否隐藏</label>
      <div class="layui-input-inline">
        <select name="hide">
		  <option value="01">否</option> 
		  <option value="00">是</option> 
        </select>
      </div>
    </div> 
    <div class="layui-form-item">
      <label class="layui-form-label">组件类型</label>
      <div class="layui-input-inline">
        <select name=type>
		  <option value="-1">选择排序方式</option> 
          <option value="01">搜索栏表单组件</option> 
		  <option value="03">普通操作按钮</option>  
        </select>
      </div>
    </div> 
    <div class="layui-form-item">
      <label class="layui-form-label">事件关联</label>
      <div class="layui-input-inline">
        <input type="text" name="event" autocomplete="off" class="layui-input">
      </div>
    </div> 
    <div class="layui-form-item">
      <label class="layui-form-label">描述信息</label>
      <div class="layui-input-block">
        <textarea class="layui-textarea" name="desc" placeholder="请输入描述信息"></textarea>
      </div>
    </div> 
    <div class="layui-form-item layui-hide">
      <input type="button" lay-submit lay-filter="C-admin-temp-add" id="C-admin-temp-add" value="确认">
      <input type="button" lay-submit lay-filter="C-admin-temp-update" id="C-admin-temp-update" value="确认">
    </div>
  </div>
  <script src="<%=basePath%>layuiadmin/layui/layui.js"></script>  
  <script>
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
    ,l = "C-admin-search-table";
  	
	form.on("submit("+a+")", function(data){
		console.log("submit... ========================");
		console.log(data.field);
		admin.cajax({
			method: 'search_add'
			,id: l
			,data: data.field  
		});
		return false;
	})
	,form.on("submit("+b+")", function(data){
		admin.cajax({
			method: 'search_update'
			,id: l
			,data: data.field  
		});
		return false;
	}) 
  })
  </script>
</body>
</html>