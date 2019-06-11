<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
  <meta name="Author" content="cjw">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <title>机构管理</title>
  <link rel="stylesheet" href="../../layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="../../layuiadmin/style/admin.css" media="all">
  <style>
	*{margin: 0; padding: 0;}
	.manage-button, .layui-card {border-radius: 5px;}
	.manage-button {background: #555;}

  </style>
 </head>
 <body layadmin-themealias="default">
	<div class="layui-fluid">
		<div class="layui-row layui-col-space15">
			<div class="layui-col-md12"> 
				<div class="layui-card layui-circle">
					<div class="layui-card-header layuiadmin-card-header-auto">
						<input type="button" class="layui-btn manage-button" value="添加"/> 
					</div>
					<div class="layui-card-body">
						<div class="layui-collapse" id="show-manage">
						</div>	  	 
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="update" style="padding: 30px; display: none;">
		<form class="layui-form" action="/api/test/json.do">
			<div class="layui-form-item">
				<label class="layui-form-label">名称</label>
				<div class="layui-input-block">
					<input type="text" name="title" lay-verify="title" autocomplete="off" placeholder="请输入标题" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">创建时间</label>
				<div class="layui-input-block">
					<input type="text" lay-verify="title" disabled autocomplete="off" placeholder="请输入标题" class="layui-input layui-disabled">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">修改时间</label>
				<div class="layui-input-block">
					<input type="text" lay-verify="title" disabled autocomplete="off" placeholder="请输入标题" class="layui-input layui-disabled">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">优先级</label>
				<div class="layui-input-block">
					<input type="text" name="priority" lay-verify="title" autocomplete="off" placeholder="请输入标题" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">链接</label>
				<div class="layui-input-block">
					<input type="text" name="url" lay-verify="title" autocomplete="off" placeholder="请输入标题" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">备注</label>
				<div class="layui-input-block">
					<textarea placeholder="请输入内容" class="layui-textarea" name="msg"></textarea>
				</div>
			</div> 
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit lay-filter="update_submit">确认修改</button>
				</div>
			</div>
		</form>
	</div>
	<div id="add" style="padding: 30px; display: none;">
		<form class="layui-form" action>
			<div class="layui-form-item">
				<label class="layui-form-label">名称</label>
				<div class="layui-input-block">
					<input type="text" name="title" lay-verify="title" autocomplete="off" placeholder="请输入标题" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">创建时间</label>
				<div class="layui-input-block">
					<input type="text" lay-verify="title" disabled autocomplete="off" placeholder="请输入标题" class="layui-input layui-disabled">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">修改时间</label>
				<div class="layui-input-block">
					<input type="text" lay-verify="title" disabled autocomplete="off" placeholder="请输入标题" class="layui-input layui-disabled">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">优先级</label>
				<div class="layui-input-block">
					<input type="text" name="priority" lay-verify="title" autocomplete="off" placeholder="请输入标题" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">链接</label>
				<div class="layui-input-block">
					<input type="text" name="url" lay-verify="title" autocomplete="off" placeholder="请输入标题" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">备注</label>
				<div class="layui-input-block">
					<textarea placeholder="请输入内容" class="layui-textarea" name="msg"></textarea>
				</div>
			</div> 
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit lay-filter="add_submit">确认添加</button>
				</div>
			</div>
		</form>
	</div>
	<script src="../../layuiadmin/layui/layui.js?t=1"></script>
	<script>
	
	layui.config({
		base: '../../layuiadmin/' //静态资源所在路径
	}).extend({
		index: 'lib/index' //主入口模块
	}).use(["tree", 'index', 'console', 'element'], function() {
		var layer  = layui.layer
		,$ = layui.jquery
		,form = layui.form
		,update = layer.open({
			type: 1
			,title: '修改'
			,shadeClose: true
			,shade: 0.8
			,maxmin: !0
			,area: ['200','526']
			,content: $('#update')
			,btnAlign: 'r'
			,anim: 5
			,cancel: function(index, layero){
				layer.close(index); 
				return false;
			}
			/*
			,yes: function(){
				layer.msg('修改成功', {
					time: 2000, //2s后自动关闭 
				});
			}
			*/
		})
		/*
		,add = layer.open({
			type: 1
			,title: '添加'
			,shadeClose: true
			,shade: 0.8
			,maxmin: !0
			,area: ['200','526']
			,content: $('#add')
			,btnAlign: 'c'
			,cancel: function(index, layero){
				layer.close(index);
				return false;
			}
			
		})
		*/
		,del = layer.confirm('您是如何看待前端开发？', {
			btn: ['确认删除','取消'] //按钮
		}, function(){
			layer.msg('删除成功', {
				time: 2000, //2s后自动关闭 
			});
		}, function(){
			layer.msg('已取消删除', {
				time: 2000, //2s后自动关闭 
			});
		})
		,tree = layui.tree
		,data=[{
				label:'资源管理'
				,id:1
				,children:[{
					label:'机构管理'
					,id:23
				}
				,{
					label:'数据字典'
					,id:24
				}]
			}
			,{
				label:'权限管理'
				,id:2
				,children:[{
					label:'访客用户'
					,id:25
				}
				,{
					label:'后台管理员'
					,id:26
				}]
		}];
		tree.render({
			elem: '#show-manage'
			,data: data
			//,key: 'id'
			,renderContent: !0
			,click: function(obj){
				layer.msg('状态：'+ obj.state + '<br>节点数据：' + JSON.stringify(obj.data))
			}
			,operate: function(obj){
				//obj.type 
				//删除 整个删除 添加 修改
				layer.msg("操作："+obj.type) 
				console.log(obj);
				return false;
			}
		})
		,form.on('submit(update_submit)', function(data){
			var fileds = data.field; 
			$.ajax({
				type: 'post'
				,contentType: 'application/json'
				,url: $('#update-form').attr("action")
				,data: JSON.stringify(fileds)
				,success: function(data){
					layer.msg("修改成功"+data, {
						time: 2000, //2s后自动关闭 
					});
				}
				,error: function(data){
					layer.msg("修改失败", {
						time: 2000, //2s后自动关闭 
					});
				}
			});
			return false;
		})
		,form.render()
	});
	 
	</script>
 </body>
</html>