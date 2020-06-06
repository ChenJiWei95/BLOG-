<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<!--time: 2018年11月21日 15:25:32-->
	<!--author: dbag-->
	<!--blog: https://github.com/9499574/layui-form-create-->
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>layui表单生成器-懒人专用</title>
	<link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
	<style>
	html,body{background-color: #f2f2f2}
	.layui-fluid{margin-top: 15px;}
	.content{min-height: 796px;}
	.nav{text-align: center;}
	.nav button{margin-bottom: 3px;width: 80px;}
	.layui-card-body .layui-btn+.layui-btn{margin-left: 0px;}
	.code-show{min-height: 454px;}
	.js-show{min-height: 200px;}
	.del-form{line-height: initial;position: absolute;right: 15px;top: 50%;margin-top: -15px;}
	</style>
</head>
<body>
<div class="layui-fluid" >
	<div class="layui-row layui-col-space10">
		<div class="layui-col-md1">
			<div class="layui-card nav click-but">
				<div class="layui-card-header">长</div>
				<div class="layui-card-body">
					<button class="layui-btn" data-text="提示，查询" data-name="text,query" data-size="block" data-type="text" >输入框</button>
					<button class="layui-btn" data-text="提示，查询" data-name="text,query" data-size="block" data-type="select">选择框</button>
					<!-- <button class="layui-btn" data-size="block" data-type="checkbox_a">复选框</button> -->
					<button class="layui-btn" data-text="提示，查询" data-name="text,query" data-size="block" data-type="date">时间</button>
					<button class="layui-btn" data-text="提示，查询" data-name="text,query" data-size="block" data-type="date1">时间区间</button>
					<button class="layui-btn" data-size="block" data-type="submit">提交</button>
				</div>
			</div>
			<div class="layui-card nav">
				<div class="layui-card-header">短</div>
				<div class="layui-card-body">
					<button class="layui-btn" data-text="提示，查询" data-name="text,query" data-size="inline" data-type="text" >输入框</button>
					<button class="layui-btn" data-text="提示，查询" data-name="text,query" data-size="inline" data-type="select">选择框</button>
					<!-- <button class="layui-btn" data-size="inline" data-type="checkbox_a">复选框</button> -->
					<button class="layui-btn" data-text="提示，查询" data-name="text,query" data-size="inline" data-type="date">时间</button>
					<button class="layui-btn" data-text="提示，查询" data-name="text,query" data-size="inline" data-type="date1">时间区间</button>
					<button class="layui-btn" data-size="block" data-type="submit">提交</button>
				</div>
			</div>

		</div>
		<div class="layui-col-md5">
			<div class="layui-card content">
				<div class="layui-card-header">表单-问题反馈QQ群: 925487043
					<button class="layui-btn layui-btn-sm layui-btn-danger del-form" data-type="del"> <i class="layui-icon">&#xe640;</i></button>
				</div>
				<div class="layui-card-body code"><form class="layui-form" action="" onsubmit="return false">
</form></div>
			</div>
		</div>
		<div class="layui-col-md6">
			<div class="layui-card r-code-html">
				<div class="layui-card-header">html</div>
				<div class="layui-card-body">
					<textarea name=""  class="layui-textarea code-show"></textarea>
				</div>
			</div>
			<div class="layui-card r-code-js">
				<div class="layui-card-header">JS</div>
				<div class="layui-card-body">
					<textarea name=""  class="layui-textarea js-show"></textarea>
				</div>
			</div>
		</div>
	</div>
</div> 
</body>
<script src="<%=basePath%>layuiadmin/layui/layui.js?t=1"></script>
<script>
    layui.config({
        base: '<%=basePath%>layuiadmin/' //静态资源所在路径
    }).extend({
    	index: 'lib/index' //主入口模块 
        ,searchTemp: 'admin/template/searchTemp'
    }).use(['index', 'searchTemp'],function(){
    });
</script>
<!-- <script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "https://hm.baidu.com/hm.js?74fccec1ffa027e00b82ec47a5b9f8f5";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script> -->
</html>