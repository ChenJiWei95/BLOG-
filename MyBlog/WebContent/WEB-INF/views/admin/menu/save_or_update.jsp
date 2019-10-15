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
  <!--<link rel="stylesheet" href="../../layuiadmin/style/admin.css" media="all">-->

</head>
<body style="padding-right=20px">
	<div class="layui-form" lay-filter="branch-form-tags" id="branch-form-tags" style="padding:  20px 30px 0 0; text-align: center; background: #fff">
		<div class="layui-hide">
			<label class="layui-form-label">ID</label>
			<div class="layui-input-block">
				<input type="text" name="id" lay-verify="id" autocomplete="off" class="layui-input">
			</div>
		</div> 
		<div class="layui-form-item">
			<label class="layui-form-label">名称</label>
			<div class="layui-input-block">
				<input type="text" name="name" lay-verify="name" autocomplete="off" placeholder="请输入" class="layui-input">
			</div>
		</div> 
		<div class="layui-form-item">
			<label class="layui-form-label">创建时间</label>
			<div class="layui-input-block">
				<input type="text" name="create_time" lay-verify="title" autocomplete="off" disabled class="layui-input layui-disabled">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">修改时间</label>
			<div class="layui-input-block">
				<input type="text" name="update_time" lay-verify="title"  autocomplete="off" disabled class="layui-input layui-disabled">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">图标</label>
			<div class="layui-input-inline">
			  <select name="icon">
				<option value="">请选图标</option>
				<option value="layui-icon-app">应用</option>
				<option value="layui-icon-component">资源</option>
				<option value="layui-icon-user">权限</option>
				<option value="layui-icon-home">主页</option>
				<option value="layui-icon-console">控制</option>
				<option value="layui-icon-usernamee">用户名</option>
				<option value="layui-icon-auz">授权</option>
				<option value="layui-icon-set">设置</option>
				<option value="layui-icon-util">工具</option>
				<option value="layui-icon-layouts">模板扩展</option>
			  </select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">优先级</label>
			<div class="layui-input-block">
				<input type="text" name="priority" lay-verify="number" autocomplete="off" placeholder="请输入" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">链接</label>
			<div class="layui-input-inline">
				<input type="text" name="url" id="url" lay-verify="title" autocomplete="off" placeholder="请输入" class="layui-input">
			</div>
			<div class="layui-input-inline">
				<input type="checkbox" id="tag_check" lay-filter="tag_check" name="tag_check" title="设置为菜单">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">备注</label>
			<div class="layui-input-block">
				<textarea placeholder="请输入内容" class="layui-textarea" name="msg"></textarea>
			</div>
		</div>  
		<div class="layui-form-item layui-hide">
			<button lay-submit lay-filter="layuiadmin-branch-form-submit" id="layuiadmin-branch-form-submit">确认添加</button>
			<button lay-submit lay-filter="layuiadmin-branch-form-edit" id="layuiadmin-branch-form-edit">确认编辑</button>
		</div>
	</div>

  <script src="<%=basePath%>layuiadmin/layui/layui.js"></script>  
  <script>
  layui.config({
    base: '<%=basePath%>layuiadmin/' // 静态资源所在路径
  }).extend({
    index: 'lib/index' // 主入口模块
  }).use(['index', 'form'], function(){
		var table = layui.table
		,$ = layui.$
		,admin = layui.admin
		,a = 'layuiadmin-branch-form-submit'
		,b = 'layuiadmin-branch-form-edit'
		,d = 'tag_check'
		,index = layui.index
		,relateId = index.util.getParameter("relateId")
		,form = layui.form
		,clickOne = !0;			// 只允许点击一次
		
		// 
		form.on("checkbox("+d+")", function(data){
			console.log(data);
			if(this.checked) $("#url").addClass("layui-disabled"), $("#url").attr("disabled", ""), $("#url").val("####");
			else $("#url").removeClass("layui-disabled"), $("#url").removeAttr("disabled"), $("#url").val("");
		});
		
		// 添加
		form.on("submit("+a+")", function(data){
			if(clickOne){
				clickOne = !1;
				layer.msg("添加中...", {time: 16000}); 
				var index = parent.layer.getFrameIndex(window.name)
				,dataTemp = data.field;
				dataTemp["relate_id"] = relateId;
				$.ajax({
					url: 'add.do'
					,type: 'post'	
					,data: dataTemp
					,dataType: "json"
					,success: function(data){
						console.log("请求成功，" + data);
						// data 返回的数据 传入add中生成新功能
						var b = {};
						b["label"] = data.name;//补充
						b["key"] = data.id;//补充key 需要后台返回id	
						console.log('success add'); 
						if(relateId == void 0 || relateId == '')
							parent.initAjax();
						else
							parent.oparate_active.add(data);
						parent.layer.msg("添加成功！"); 
						parent.layer.close(index);
					}	
					,error: function(data){
						parent.layer.msg("系统异常，添加失败！");
						parent.layer.close(index);
					}
				});	
			}
			return !1;
		})
		// 编辑
		,form.on("submit("+b+")", function(data){
			var spread = index.util.getParameter("spread")
			,dataTemp = data.field;
			dataTemp["spread"] = spread;
			if(clickOne){
				clickOne = !1;
				layer.msg("修改中...", {time: 16000}); 
				var index_ = parent.layer.getFrameIndex(window.name); 
				console.log(JSON.stringify(data.field));
				// 执行 Ajax 后重载
				$.ajax({
					url: 'update.do'
					,type: 'post'	
					,data: dataTemp
					,success: function(data){
						parent.oparate_active.edit(data);
						parent.layer.msg("修改成功！", {time: 2000}); 
						parent.initAjax();
						parent.layer.close(index_);
					}
					,error: function(data){
						parent.layer.msg("系统异常，修改失败！", {time: 2000}); 
						parent.layer.close(index_);
					}
				});	
			}
			return !1;
		}); 
		
  })
  
  
  </script>
</body>
</html>