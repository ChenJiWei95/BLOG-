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
  <title>layuiAdmin 角色管理 iframe 框</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
</head>
<body style="padding-right=20px">
  <div class="layui-form" lay-filter="layuiadmin-form-role" id="layuiadmin-form-role" style="padding: 20px 30px 0 0;">
    <div class="layui-form-item">
      <label class="layui-form-label">ID</label>
      <div class="layui-input-inline"> 
        <input type="text" name="id" disabled placeholder="000000" autocomplete="off" class="layui-input layui-disabled">
      </div>
    </div>
	<div class="layui-form-item">
      <label class="layui-form-label">名称</label>
      <div class="layui-input-inline">
        <input type="text" name="type_name" lay-verify="required" autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label">级别</label>
      <div class="layui-input-inline">
        <input type="text" name="级别" lay-verify="number" placeholder="级别数越高权利越大" autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label">创建时间</label>
      <div class="layui-input-inline">
        <input type="text" name="create_time" disabled autocomplete="off" class="layui-input layui-disabled">
      </div>
    </div>
	<div class="layui-form-item">
      <label class="layui-form-label">修改时间</label>
      <div class="layui-input-inline">
        <input type="text" name="update_time" disabled autocomplete="off" class="layui-input layui-disabled">
      </div>
    </div>
	<div class="layui-form-item">
      <label class="layui-form-label">权限范围</label>
      <div class="layui-input-block">
        <input type="checkbox" name="limits[]" lay-skin="primary" title="发货">
        <input type="checkbox" name="limits[]" lay-skin="primary" title="采购">
        <input type="checkbox" name="limits[]" lay-skin="primary" title="系统设置">
        <input type="checkbox" name="limits[]" lay-skin="primary" title="发邮件">
        <input type="checkbox" name="limits[]" lay-skin="primary" title="发短信">
        <input type="checkbox" name="limits[]" lay-skin="primary" title="审核">
        <input type="checkbox" checked name="limits[]" lay-skin="primary" title="看效果">
      </div>
    </div>
	<div class="layui-form-item">
      <label class="layui-form-label">描述信息</label>
      <div class="layui-input-block">
        <textarea class="layui-textarea" name="msg" placeholder="请输入描述信息"></textarea>
      </div>
    </div>
    <div class="layui-form-item layui-hide">
	  <button class="layui-btn" lay-submit lay-filter="LAY-user-role-add" id="LAY-user-role-add">添加</button>
	  <button class="layui-btn" lay-submit lay-filter="LAY-user-role-update" id="LAY-user-role-update">修改</button>
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
	,index = layui.index 
	,admin = layui.admin 
	,a = "LAY-user-role-add" 
	,b = "LAY-user-role-update";
	form.on("submit("+a+")", function(data){
		var index = parent.layer.getFrameIndex(window.name); 
		//执行 Ajax 后重载
		admin.req({
			url: 'add.do'
			,type: 'post'	
			,dataType: "json"
			,done: function(data){
				layer.msg("添加成功！", {time: 2000}),
				parent.table.reload(l);
			} 
		});			  
		parent.layer.close(index);
		return false;
	})
	,form.on("submit("+b+")", function(data){
		var index = parent.layer.getFrameIndex(window.name); 
		//执行 Ajax 后重载
		admin.req({
			url: 'update.do'
			,type: 'post'	
			,dataType: "json"
			,done: function(data){
				layer.msg("添加成功！", {time: 2000}),
				parent.table.reload(l);
			} 
		});			  
		parent.layer.close(index);
		return false;
	})
  })
  </script>
</body>
</html>