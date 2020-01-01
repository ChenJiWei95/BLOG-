<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>随笔</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/style/admin.css" media="all"> 
  <link rel="stylesheet" href="<%=basePath%>css/git-plugin.css" media="all">  	
  <link rel="stylesheet" href="<%=basePath%>css/basi.css" media="all"> 	
  <style>
  * {
    margin: 0;
    padding: 0;
  }
  body {
    font-size: 12px;
    font-family: "Microsoft YaHei", 宋体, "Segoe UI", "Lucida Grande", Helvetica, Arial, sans-serif, FreeSans, Arimo;
  }
  .item-body {
  	display: block;
  	width: 99%;
    height: 54px;
    margin-top: 2px;
    padding: 4px; 
  }
  .content {
    padding-top: 1px;
    height: 51px;
    overflow: hidden;
  }
  .item-tag{
  	display: block;
  	overflow: hidden;
  	float: left;
  }
  .item-tag-block {
  	margin-left: 4px;
    background: #ddd;
    border-radius: 4px;
    padding: 2px 4px;
    display: inline-block;
    color: #fff;
    float: left;
  }
  .layui-card-header .layui-icon { 
  	top: 40%;
  }
  .more-oprate li{
  
  }
  
  .more-oprate li > i, .more-oprate li > .layui-icon{
  	font-size: 28px;
  	cursor: pointer; 
    line-height: 39px;
    margin: 4px;
  }
  
  	/* 代码修饰 样式 */
  	.iframe_box{width:800px; height:480px; border:1px solid #ddd; border-radius:5px; overflow:hidden; padding:5px;}
	.iframe_box_title{height:30px; background:rgba(85, 85, 85, 0.15)}
	.iframe_box_title i {cursor:pointer; width:16px; height:16px;}
	.iframe_box_title #switch_button{background:url(images/hide.png); margin:0px 782px;}
	.iframe_box_title #big_button{background:url(images/big.png); margin:0 761px;}
	.iframe_box_title span{color:#555; line-height:30px; padding:8px;}
	.iframe_box iframe{width:798px; height:450px; border:1px solid #ddd;}
	.code_box .iframe_div{height:30px;width:800px;border-bottom:1px solid #ccc; background:rgba(85, 85, 85, 0.15)}
	.code_box .iframe_div i{background:url(images/hide.png); margin:0px 782px; cursor:pointer; display:none; width:16px; height:16px;}
	.code_box .iframe_div span{color:#555; line-height:30px; padding:8px;}
	.code_box .code_div{background:#f6f4f0; padding:10px;}
	.code_box .code::-webkit-scrollbar {width: 4px;border-radius:2px;}
	.code_box .code::-webkit-scrollbar-button {background-color: #fff;}
	.code_box .code::-webkit-scrollbar-track {height:100px;background: #fff;}
	.code_box .code::-webkit-scrollbar-thumb {background: #ccc;border-radius: 5px;}
	.code_box .code{overflow:hidden; max-height:700px; overflow-y:auto; overflow-x:auto; width:781px; }
	.code_box .code table tbody tr td {padding-top: 0 !important; padding-bottom: 0 !important}
	.code_box .code table tbody tr td span{color:blue;}
	.code_box .code table tbody tr td span.red{color:red;}
	.code_box .code table tbody tr td span.purple{color:#ff66ff}
	.code_box .code table tbody tr td span.text{color:#555}
	.code_box .code table tbody tr td span.content{color:#00cc33;}

	.code_box{margin-top:10px; width:800px; border:1px solid #ddd; border-radius:5px; padding:5px; font-size:12px; font-family:Consolas; overflow:hidden;}
	.code_box .code table{border-spacing: 0;border-collapse: collapse; background: #f6f4f0;}
	.code_box .code table tbody{display: table-row-group;vertical-align: middle;border-color: inherit;}
	.code_box .code table tbody tr{height:24px; white-space:nowrap;}
	.code_box .code table tbody tr:hover{background:#ece8dd;}
	.code_box .code table tbody tr td{padding:5px 10px;}
	.code_box .code table tbody tr .Serial{width:20px; color:rgba(27,31,35,0.3); text-align:right;}
	.code_box .code table tbody tr .Serial:hover{color:rgba(27,31,35,0.5); cursor:pointer;}
	.code_box .code table tbody tr .row_code{}
	
	/* 代码修饰 样式 */
	
	.note-more {padding-top: 20px; padding-bottom: 20px;}
	.note-more:hover {cursor: pointer; color: #000;}
  </style>
  <style id="code-css">
  	
  </style>
</head>
<body>
  <!-- <div class="code_box code-box1" style="margin-top: 0px;">
	显示基本的行数和文件大小以及一些基本的操作
	<div class="iframe_div">
		<span>源码</span>
	</div>
	显示从后台读取的代码
	<div class="code_div">
		<div class="code">
			<table class="code_table">
				<tbody id="code"></tbody>
			</table>
		</div>
	</div>
  </div> -->
	
  <div class="myModal" click-event="myModalClick">
	<span class="close" click-event="myModalClick">×</span>
	<img class="myModalImg" alt="">
	<div class="desc"></div>
 </div>
	
  <%@ include file="../../include/fore/article/blog-more-oprate.jsp" %>
  
  <%@ include file="../../include/fore/article/blog-note-list.jsp" %>
  
  <div class="layui-container note-more" click-event="more" data-page="1">
  	<div class="layui-card" style="text-align:center; line-height: 30px;">
	  	更多
	</div>
  </div>
  
  <script src="<%=basePath%>layuiadmin/layui/layui.js"></script>
  <script src="<%=basePath%>js/git-plugin V0.js"></script>
  <script src="<%=basePath%>js/DealCodeV0-1.js"></script>
  
  <!-- c:set 中转作用  解决不能直接使用的地方 -->
  <c:set var="queryStr" value="${query}"></c:set> 
  <c:set var="adminID" value="${adminId}"></c:set> 
  
  <script id="noteTpl" type="text/html">
	<div class="layui-card note-item" data-id="{{d.id}}">
  		<div class="layui-card-header">
			<label class="name" style="display: block; float: left; font-size: 14px; font-weight: bold; color: #555;"
			>{{d.name}}</label>
			<label style="display: block; float: right; color:#999;">
	  			{{d.update_time}}<i class="layui-icon layui-icon-log" style="margin-left: 6px;position: unset"></i>
	  		</label>  
		</div>
  		<div class="layui-card-body" style="overflow: hidden;">
  			<!-- 内容中转 -->
  			<xmp class="content layui-hide">{{d.simp_desc}}</xmp>
  			<!-- 编译之后的内容 --> 
  			<div class="mark_code" style="margin-bottom: 8px; height: 156px;"></div>
  			<label class="tags-value layui-hide">{{d.tags}}</label>
  			<!-- 私有还是公开 暂存 -->
  			<label class="status layui-hide">{{d.status}}</label>
  			<label class="item-tag"></label>
  		</div> 
	</div>
  </script>
  <script>
  //var noteTabs = JSON.parse('${jsonStr}')
  var queryStr = '${queryStr}'; // 拼接原有查询条件 根据这一查询条件查询更多 
  // codeCount 	html代码处理计数
  // codeArr	html代码处理数组 暂时存储用
  // currentCount html代码处理保存某一阶段的下标位置     比如初始阶段，或者再获取更多的时候也是一个阶段
  var $, codeCount, codeArr, currentCount;
  codeCount = 0
  ,codeArr = []
  ,base  = '<%=basePath%>';
  
  layui.config({
    base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块 
    ,moreBtns: 'admin/note/moreBtns'
    ,articleList: 'fore/article/articleList'
    ,myModelImg: 'fore/article/myModelImg'
  }).use(['index', 'table', 'moreBtns', 'articleList', 'myModelImg', 'admin', 'laytpl', 'util'], function(){
    var form = layui.form
    ,admin = layui.admin 
    ,$ = layui.$
    ;
  });
  </script>
</body>
</html>

