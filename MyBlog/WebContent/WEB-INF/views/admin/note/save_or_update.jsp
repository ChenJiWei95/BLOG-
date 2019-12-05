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
</head>
<body style="padding-right:20px">
  <div class="layui-form" lay-filter="C-admin-note-form" id="C-admin-note-form" style="padding: 20px 30px 0 0;">
    <div class="layui-hide">
		<label class="layui-form-label">ID</label>
		<div class="layui-input-inline">
			<input type="text" name="id" disabled autocomplete="off" value="{note.id}" class="layui-input layui-disabled">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">名称</label>
		<div class="layui-input-inline">
			<input type="text" name="name" placeholder="请输入名称" value="{note.name}" autocomplete="off" class="layui-input">
		</div>
		<c:if test="${type}">
		<label class="layui-form-label">新标签</label>
		<div class="layui-input-inline">
			<input type="text" name="tabs" placeholder="例如：Java,JavaScript" autocomplete="off" class="layui-input">
		</div>
		</c:if>
		<label class="layui-form-label">创建时间</label>
		<div class="layui-input-inline">
			<input type="text" name="create_date" disabled placeholder="请输入创建时间" autocomplete="off" class="layui-input layui-disabled">
		</div>
		<label class="layui-form-label">修改时间</label>
		<div class="layui-input-inline">
			<input type="text" name="update_date" disabled placeholder="请输入修改时间" autocomplete="off" class="layui-input layui-disabled">
		</div>
	</div>
	
	<div class="layui-form-item">
      <label class="layui-form-label">标签</label>
      <div class="layui-input-block"> 
        <c:choose>
        	<c:when test="${type}">
        		<!-- 循环输出所有未授权的模块 -->
	        	<c:forEach begin="0" items="${all}" step="1" var="Data" varStatus="varsta">
				   	 <input type="checkbox" name="${Data.id}|${Data.name}" lay-skin="primary" title="${Data.name}">
				</c:forEach>
        	</c:when>
        	<c:otherwise>
        		<!-- 循环判断已授权和未授权的模块 -->
        		<c:forEach begin="0" items="${all}" step="1" var="Data" varStatus="varsta"> 
        			<c:set var="iscontain" value="false" />
        			<c:forEach begin="0" items="${seleteds}" step="1" var="NoteTabBrige" varStatus="varsta">
        				<c:if test="${Data.id eq NoteTabBrige.note_tab_id}">
        					<c:set var="iscontain" value="true"/>
        				</c:if> 
        			</c:forEach>  
        			<c:if test="${iscontain}"><input type="checkbox" checked  name="${Data.id}|${Data.name}" lay-skin="primary" title="${Data.name}"/></c:if> 
        			<c:if test="${!iscontain}"><input type="checkbox" name="${Data.id}|${Data.name}" lay-skin="primary" title="${Data.name}"/></c:if> 
				</c:forEach>
				
        	</c:otherwise>
        </c:choose>  
      </div>
    </div>
	<div class="layui-form-item">
		<label class="layui-form-label">内容</label>
		<div class="layui-input-block">
			<textarea name="content" style="height: 600px;" placeholder="请输入" class="layui-textarea c-textarea mark_code"></textarea>
		</div>
	</div>

    <div class="layui-form-item layui-hide">
      <button lay-submit lay-filter="C-admin-note-add" id="C-admin-note-add">添加</button>
	  <button lay-submit lay-filter="C-admin-note-update" id="C-admin-note-update">修改</button>
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
	,a = "C-admin-note-add"
	,b = 'C-admin-note-update'
    ,l = "C-admin-note-table";
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
