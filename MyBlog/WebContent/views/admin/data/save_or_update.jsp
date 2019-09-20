<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>机构管理 iframe 框</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/style/admin.css" media="all">
</head>
<body style="padding-right: 20px;">  
  <div class="layui-form" lay-filter="source-data-form" id="source-data-form" style="padding: 20px 30px 0 0;">
    <div class="layui-form-item">
      <label class="layui-form-label">ID</label>
      <div class="layui-input-inline"> 
        <input type="text" name="id" disabled placeholder="不允许操作" autocomplete="off" class="layui-input layui-disabled">
      </div>
    </div>
	<div class="layui-form-item">
      <label class="layui-form-label">名称</label>
      <div class="layui-input-inline">
        <input type="text" name="type_name" lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label">代码</label>
      <div class="layui-input-inline">
        <input type="text" name="type_code" lay-verify="type_code" autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label">创建时间</label>
      <div class="layui-input-inline">
        <input type="text" name="create_time" disabled placeholder="创建时间" autocomplete="off" class="layui-input layui-disabled">
      </div>
    </div>
	<div class="layui-form-item">
      <label class="layui-form-label">修改时间</label>
      <div class="layui-input-inline">
        <input type="text" name="update_time" disabled placeholder="修改时间" autocomplete="off" class="layui-input layui-disabled">
      </div>
    </div> 
	<div class="layui-form-item">
      <label class="layui-form-label">值</label>
      <div class="layui-input-inline">
        <input type="text" name="type_value" lay-verify="type_value" autocomplete="off" class="layui-input">
      </div>
    </div>	 
	<div class="layui-form-item">
      <label class="layui-form-label">描述信息</label>
      <div class="layui-input-inline">
        <textarea class="layui-textarea" name="msg" placeholder="请输入描述信息"></textarea>
      </div>
    </div>
    <div class="layui-form-item layui-hide">
      <button lay-submit lay-filter="set-data-form-add" id="set-data-form-add">确认添加</button>
	  <button lay-submit lay-filter="set-data-form-edit" id="set-data-form-edit">确认编辑</button>
	</div>
  </div>  
  <script src="<%=basePath%>layuiadmin/layui/layui.js"></script>  
  <script>
  layui.config({
    base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index'], function(){
		var table = layui.table
		,$ = layui.$
		,a = 'set-data-form-add'
		,b = 'set-data-form-edit';
		//添加
		form.on("submit("+a+")", function(data){
			var index = parent.layer.getFrameIndex(window.name); 
			//执行 Ajax 后重载 
			admin.req({
				url: 'add.do'
				,type: 'post'	
				,data: {data: JSON.stringify(data.field)}
				,dataType: "json"
				,done: function(data){
					layer.msg("添加成功！", {time: 2000})
				} 
				,fail: function(data){
					layer.msg("添加失败！", {time: 2000})
				}
			});			  
			parent.layer.close(index);
			return !1;
		})
		//编辑
		,form.on("submit("+b+")", function(data){
			var index = parent.layer.getFrameIndex(window.name);
			data.field['id'] = param;
			//console.log(JSON.stringify(data.field));
			//执行 Ajax 后重载
			$.req({
				url: 'update.do'
				,type: 'post'	
				,data: {data: JSON.stringify(data.field)}
				,done: function(data){
					layer.msg("修改成功！", {time: 2000});
				}
				,fail: function(data){
					layer.msg("修改失败！", {time: 2000})
				}
			});	
			parent.layer.close(index);
			return !1;
		})
  })
  </script>
</body>
</html>