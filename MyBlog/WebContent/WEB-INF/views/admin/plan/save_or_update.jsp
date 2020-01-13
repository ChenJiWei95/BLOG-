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
  <title>修改计划</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
</head>
<body style="padding-right=20px">
  <div class="layui-form" lay-filter="admin-form-plan" id="admin-form-plan" style="padding: 20px 30px 0 0;">
	<div class="layui-hide">
		<label class="layui-form-label">ID</label>
		<div class="layui-input-block">
			<input type="text" name="id" lay-verify="id" value="${planBase.id}" autocomplete="off" class="layui-input">
		</div>
	</div> 
	<div class="layui-form-item">
      <label class="layui-form-label">激励语</label>
      <div class="layui-input-block">
        <input type="text" value="${planBase.excitation_text}" name="excitation_text" lay-verify="required" autocomplete="off" class="layui-input">
      </div>
    </div>  
    <div class="layui-form-item">
      <label class="layui-form-label">新建计划项</label>
      <div class="layui-input-block">
        <input type="text" name="new_tags" placeholder="多个新建由‘,’分隔开" autocomplete="off" class="layui-input">
      </div>
    </div>  
	<div class="layui-form-item">
      <label class="layui-form-label">选取已有项</label>
      <div class="layui-input-block"> 
        <!-- 循环判断已授权和未授权的模块 -->
        <c:forEach begin="0" items="${allTags}" step="1" var="Data" varStatus="varsta"> 
        	<c:set var="iscontain" value="false" />
        	<c:set var="isfinish" value="false" />
        	<c:forEach begin="0" items="${otherTags}" step="1" var="PlanDo" varStatus="varsta">
        		<c:if test="${Data.id eq PlanDo.plan_tag_id}">
        			<c:set var="iscontain" value="true"/>
        			<c:if test="${PlanDo.status eq '00'}">
        				<c:set var="isfinish" value="true" />
        			</c:if>
        		</c:if> 
        	</c:forEach>  
        	<input type="checkbox"
        	<c:if test="${iscontain}"> checked </c:if>
        	<c:if test="${isfinish}"> disabled=""</c:if>
        	name="${Data.id}|${Data.name}" lay-skin="primary" title="${Data.name}"/> 
		</c:forEach>
      </div>
    </div>
	 
    <div class="layui-form-item layui-hide">
	  <button class="layui-btn" lay-submit lay-filter="admin-plan-update" id="admin-plan-update">修改</button>
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
	,b = 'admin-plan-update'
	form.on("submit("+b+")", function(data){
		admin.cajax({
			method: 'update'
			,data: data.field
			,success: function(){
				parent.self.location.href='<%=basePath%>admin/plan/show.chtml?secret_key=${secret_key}&isTips=0'
			}
		});	  
		return false;
	});  
  })
  </script>
</body>
</html>