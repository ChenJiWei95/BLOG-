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
  <title>管理员管理 操作</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
</head>
<body style="padding-right:20px">
  <div class="layui-form" lay-filter="layuiadmin-form-admin" id="layuiadmin-form-admin" style="padding: 20px 30px 0 0;">
    <div class="layui-hide">
		<label class="layui-form-label">ID</label>
		<div class="layui-input-block">
			<input type="text" name="id" lay-verify="id" autocomplete="off" class="layui-input">
		</div>
	</div> 
	<div class="layui-form-item">
      <label class="layui-form-label">账号</label>
      <div class="layui-input-inline">
        <input type="text" name="username" lay-verify="required" autocomplete="off" class="layui-input">
      </div>
    </div>
	<div class="layui-form-item">
      <label class="layui-form-label">用户名</label>
      <div class="layui-input-inline">
        <input type="text" name="name" lay-verify="required" autocomplete="off" class="layui-input">
      </div>
    </div> 
    <div class="layui-form-item">
      <label class="layui-form-label">手机</label>
      <div class="layui-input-inline">
        <input type="text" name="phone" lay-verify="phone" placeholder="请输入号码" autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label">邮箱</label>
      <div class="layui-input-inline">
        <input type="text" name="email" lay-verify="email" placeholder="请输入邮箱" autocomplete="off" class="layui-input">
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
      <label class="layui-form-label">角色</label>
      <div class="layui-input-block">
	        <select name="role_id">
			  <option value="-1">请选择权限角色</option>
	          <c:forEach begin="0" items="${roles}" step="1" var="Role" varStatus="varsta">
				<option value="${Role.id}">${Role.name}</option>
			  </c:forEach>
	        </select>
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label">启用状态</label>
      <div class="layui-input-inline">
        <select name="state">
        	<option value="01">禁用</option>
			<option value="00">启用</option>
        </select>
      </div>
    </div>
	<div class="layui-form-item">
      <label class="layui-form-label">描述信息</label>
      <div class="layui-input-inline">
        <textarea class="layui-textarea" name="desc" placeholder="请输入描述信息"></textarea>
      </div>
    </div>
    <div class="layui-form-item layui-hide">
      <input type="button" lay-submit lay-filter="LAY-user-admin-add" id="LAY-user-admin-add" value="确认">
      <input type="button" lay-submit lay-filter="LAY-user-admin-update" id="LAY-user-admin-update" value="确认">
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
	,f = 'layuiadmin-form-admin'
	,a = 'LAY-user-admin-add'
	,b = 'LAY-user-admin-update'
	,l = 'LAY-user-back-admin'; 
	form.on("submit("+a+")", function(data){
		cajax({
			method: 'add'
			,data: data.field  
		});
		return false;
	})
	,form.on("submit("+b+")", function(data){
		cajax({
			method: 'update'
			,data: data.field  
		});
		return false;
	})
	// 适用于表格
	function cajax(object){
		var index = parent.layer.getFrameIndex(window.name); 
		object.method != 'update' || object.method != 'add' || (parent.layer.msg("method参数有误："+object.method), parent.layer.close(index))
		var c = parent.layer.load(2);
		//执行 Ajax 后重载
		$.ajax({
			url: object.method + '.do'
			,type: 'post'	
			,data: object.data
			,dataType: "json"
			,success: function(data){
				data.code == '0' && ('function' == typeof object.success && object.success(data.data, data.msg), parent.layer.close(c), parent.layer.msg("操作成功！"), parent.layer.close(index), parent.table.reload(l)),
				data.code == '2' && ('function' == typeof object.error && object.error(data.data, data.msg), parent.layer.close(c), parent.layer.msg("操作失败！"+data.msg), parent.layer.close(index));
			} 
			,error: function(data){
				parent.layer.close(c),
				parent.layer.msg("服务器异常，操作失败！"+data.msg),
				'function' == typeof object.serverError && object.serverError(data, data.msg);
				parent.layer.close(index)
			}
		});	
	}
  })
  </script>
</body>
</html>