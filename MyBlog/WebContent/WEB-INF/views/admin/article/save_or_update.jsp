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
  <title>iframe-文章管理</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
</head>
<body style="padding-right:20px">
  <div class="layui-form" lay-filter="C-admin-article-form" id="C-admin-article-form" style="padding: 20px 30px 0 0;">
    <div class="layui-hide">
		<label class="layui-form-label">ID</label>
		<div class="layui-input-inline">
			<input type="text" name="id" disabled autocomplete="off" class="layui-input layui-disabled">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">文章名称</label>
		<div class="layui-input-inline">
			<input type="text" name="name" placeholder="请输入文章名称" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">创建时间</label>
		<div class="layui-input-inline">
			<input type="text" name="create_time" disabled placeholder="请输入创建时间" autocomplete="off" class="layui-input layui-disabled">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">修改时间</label>
		<div class="layui-input-inline">
			<input type="text" name="update_time" disabled placeholder="请输入修改时间" autocomplete="off" class="layui-input layui-disabled">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">图片</label>
		<div class="layui-input-inline">
			<input type="text" name="pit_url" placeholder="请输入图片" autocomplete="off" class="layui-input">
		</div>
	</div> 
	<div class="layui-form-item">
      <label class="layui-form-label">标签</label>
      <div class="layui-input-block"> 
        <c:choose>
        	<c:when test="${type}">
        		<!-- 循环输出所有未选的标签 -->
	        	<c:forEach begin="0" items="${tags}" step="1" var="ATag" varStatus="varsta">
				   	 <input type="checkbox" name="${ATag.id}|${ATag.name}" lay-skin="primary" title="${ATag.name}">
				</c:forEach>
        	</c:when>
        	<c:otherwise>
        		<!-- 循环判断已选和未选的标签 -->
        		<c:forEach begin="0" items="${tags}" step="1" var="ATag" varStatus="varsta"> 
        			<c:set var="iscontain" value="false" />
        			<c:forEach begin="0" items="${tagsed}" step="1" var="TagBrige" varStatus="varsta">
        				<c:if test="${ATag.id eq TagBrige.t_id}">
        					<c:set var="iscontain" value="true"/>
        				</c:if> 
        			</c:forEach>  
        			<c:if test="${iscontain}"><input type="checkbox" checked  name="${ATag.id}|${ATag.name}" lay-skin="primary" title="${ATag.name}"/></c:if> 
        			<c:if test="${!iscontain}"><input type="checkbox" name="${ATag.id}|${ATag.name}" lay-skin="primary" title="${ATag.name}"/></c:if> 
				</c:forEach>
				
        	</c:otherwise>
        </c:choose>  
      </div>
    </div>
	<div class="layui-form-item">
		<label class="layui-form-label">描述</label>
		<div class="layui-input-inline">
			<textarea class="layui-textarea" name="simp_desc" placeholder="请输入描述信息"></textarea>
		</div>
	</div>

    <div class="layui-form-item layui-hide">
      <button lay-submit lay-filter="C-admin-article-add" id="C-admin-article-add">添加</button>
	  <button lay-submit lay-filter="C-admin-article-update" id="C-admin-article-update">修改</button>
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
	,a = "C-admin-article-add"
	,b = 'C-admin-article-update'
    ,l = "C-admin-article-table";
	form.on("submit("+a+")", function(data){
		admin.cajax({
			method: 'add'
			,id: l
			,data: data.field  
		});
		return false;
	})
	,form.on("submit("+b+")", function(data){
		admin.cajax({
			method: 'update'
			,id: l
			,data: data.field  
		});
		return false;
	}) 
  })
  </script>
</body>
</html>
