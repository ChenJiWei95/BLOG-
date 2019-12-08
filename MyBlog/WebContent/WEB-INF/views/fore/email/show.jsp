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
    	<div class="layui-card-header">
			发送邮件给我
		</div>
		<div class="layui-card-body">
			<div class="layui-form" > 
				<div class="layui-form-item">
					<label class="layui-form-label">姓名</label>
					<div class="layui-input-inline">
						<input type="text" name="name" placeholder="请输入姓名" autocomplete="off" class="layui-input">
					</div> 
					<label class="layui-form-label">电话</label>
					<div class="layui-input-inline">
						<input type="text" name="phone" placeholder="请输入......" autocomplete="off" class="layui-input">
					</div> 
				</div> 
				<div class="layui-form-item">
					
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">微信</label>
					<div class="layui-input-inline">
						<input type="text" name="wechat" placeholder="请输入......" autocomplete="off" class="layui-input">
					</div> 
					<label class="layui-form-label">邮箱</label>
					<div class="layui-input-inline">
						<input type="text" name="email" placeholder="请输入......" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">邮件内容</label>
					<div class="layui-input-block">
						<textarea name="content" style="height: 300px;" placeholder="请输入内容" class="layui-textarea c-textarea mark_code"><c:if test="${not empty content}">${content}</c:if></textarea>
					</div>
				</div>
				<div class="layui-form-item">
	                <div class="layui-input-block">
	                  <button class="layui-btn c-button" lay-submit lay-filter="send" style="float: right;">发送</button>
	                </div>
	              </div>
	    	</div>
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
			,success: function(){
				self.location = "<%=basePath%>";
			}
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
    
    $.ajax({
		url:"http://localhost:8080/MyBlog/api/rePayNotify.do",
		//type:"get",
		success:function(e){
			alert(e);
		},
	});
  })
  </script>
</body>
</html>
