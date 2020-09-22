<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
  <meta charset="utf-8">
  <title>出错了</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/style/admin.css" media="all">
  <link id="layuicss-layer" rel="stylesheet" href="https://www.layui.com/admin/std/dist/layuiadmin/layui/css/modules/layer/default/layer.css?v=3.1.1" media="all">
  <style>
  .layadmin-tips h1 {
  	color: #555 !important
  }
  .layadmin-tips .layui-text {
    border-top: 5px solid #555 !important
  }
  	*{margin: 0; padding: 0; font-size: 14px;}
	table {display: block; overflow: hidden; width: 100%; border-collapse: collapse; border-spacing: 0; border-radius: 4px; box-shadow: 1px 1px 4px -2px;}
	table tr {/*  background: #fff;*/}
	table th {font-weight: 500; /* border: 1px solid #e6e6e6; */}
	table th, table td {padding: 6px 13px; /* border-left: 1px solid #e6e6e6; */ /* border-right: 1px solid #e6e6e6; */}
	table tr:last-child {/* border-bottom: 1px solid #e6e6e6 */}

	table tr{height:35px;}
	table .tr-odd{background:#f6f4f0;}
  </style>
</head>
<body layadmin-themealias="default">


<div class="layui-fluid">
  <div class="layadmin-tips">
    <i class="layui-icon" face=""></i>
    
    <div class="layui-text" style="font-size: 20px;">
      	没有权限访问此页面！！
    </div>
    
  </div>
</div>


  <script src="<%=basePath%>layuiadmin/layui/layui.js"></script>  
  <script>
  layui.config({
    base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index']);
  </script>

<style id="LAY_layadmin_theme">.layui-side-menu,.layadmin-pagetabs .layui-tab-title li:after,.layadmin-pagetabs .layui-tab-title li.layui-this:after,.layui-layer-admin .layui-layer-title,.layadmin-side-shrink .layui-side-menu .layui-nav>.layui-nav-item>.layui-nav-child{background-color:#20222A !important;}.layui-nav-tree .layui-this,.layui-nav-tree .layui-this>a,.layui-nav-tree .layui-nav-child dd.layui-this,.layui-nav-tree .layui-nav-child dd.layui-this a{background-color:#009688 !important;}.layui-layout-admin .layui-logo{background-color:#20222A !important;}</style>
</body>
</html>