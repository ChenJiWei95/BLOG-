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
  <title>iframe-NoteEryDay</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
  //<link type="text/css" rel="stylesheet" href="<%=basePath%>css/basi.css" />
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/style/admin.css" media="all">
</head>
<body style="padding-right:20px">
  <div class="layui-container layui-form" lay-filter="C-admin-note-form" id="C-admin-note-form" style="">
    
    <div class="layui-card"> 
		<div class="layui-card-body">
			发送成功！
		</div>
    </div>	
    
  </div>
  <script src="<%=basePath%>layuiadmin/layui/layui.js"></script>  
  <script src="<%=basePath%>js/git-plugin V0.js"></script>
  <script>
  layui.config({
    base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'form', 'admin'], function(){
    var $ = layui.$
    ,form = layui.form 
	,admin = layui.admin
	,a = "send" 
	form.on("submit("+a+")", function(data){
		admin.cajax({
			method: 'sendEmail' 
			,data: data.field  
		});
		return false;
	})
	
	$$$(".mark_code").eq(0).onkeydown = function(e){
		if(e.keyCode === 9){
			console.log();
			//点击制表符并向其中添加制表符
			$$.util.inputInsert(e.target, "\t");
			return false;//返回false才不会切换焦点
		}
	}
  })
  </script>
</body>
</html>
