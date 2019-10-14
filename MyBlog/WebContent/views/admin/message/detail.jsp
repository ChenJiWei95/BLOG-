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
  <title>消息详情标题</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/style/admin.css" media="all">
</head>
<body>

  <div class="layui-fluid" id="LAY-app-message-detail">
    <div class="layui-card layuiAdmin-msg-detail">
      <!-- 
      <script type="text/html" template lay-url="{{ layui.setter.base }}json/message/detail.js?id={{ layui.router().search.id }}">
        <div class="layui-card-header">
          <h1>{{ d.data.title }}</h1>
          <p>
            <span>{{ layui.util.timeAgo(d.data.time) }}</span>
          </p>
        </div>
        <div class="layui-card-body layui-text">
          <div class="layadmin-text">
            {{ d.data.content }}
          </div>
          <div style="padding-top: 30px;">
            <a href="javascript:;" layadmin-event="back" class="layui-btn layui-btn-primary layui-btn-sm">返回上级</a>
          </div>
        </div>
      </script>
      -->
      <div class="layui-card-header">
          <h1>${message.title}</h1>
          <p>
            <span>${message.time}</span>
          </p>
        </div>
        <div class="layui-card-body layui-text">
          <div class="layadmin-text">
            ${message.content}
          </div>
          <div style="padding-top: 30px;">
            <a href="javascript:;" layadmin-event="back" class="layui-btn layui-btn-primary layui-btn-sm">返回上级</a>
          </div>
        </div>
    </div>
  </div>

<script src="<%=basePath%>layuiadmin/layui/layui.js"></script>  
  <script>
  layui.config({
    base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index'], function(){ 
  	
  });
  </script>
</body>
</html>
