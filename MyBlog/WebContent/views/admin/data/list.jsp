<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
  <meta name="Author" content="cjw">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <title>机构管理</title>
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/style/admin.css" media="all">
 </head>
 <body layadmin-themealias="default">
	<div class="layui-fluid">
		<div class="layui-row layui-col-space15">
			<div class="layui-col-md12"> 
				<div class="layui-card layui-circle">
					<div class="layui-card-header layuiadmin-card-header-auto">
						<button class="layui-btn layuiadmin-btn-data c-button" data-type="add">添加</button>
						<button class="layui-btn layuiadmin-btn-data c-button" data-type="edit">编辑</button>
						<button class="layui-btn layuiadmin-btn-data c-button" data-type="del">删除</button>
					</div>
					<div class="layui-card-body">
						<table class="layui-hide" id="LAY-app-set-data" lay-filter="LAY-app-set-data">
							
						</table> 	 
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script src="<%=basePath%>layuiadmin/layui/layui.js?t=1"></script>
	<script>
	var table;
	layui.config({
		base: '<%=basePath%>layuiadmin/' //静态资源所在路径
	}).extend({
		index: 'lib/index' //主入口模块
	}).use(["table", 'index', 'console'], function() {
		table = layui.table;
		var $ = layui.jquery
		,form = layui.form, addPage, editPage 
		,admin = layui.admin
		,a = 'set-data-form-add'
		,b = 'set-data-form-edit'
		,f = 'iframe'
		,t = 'layuiadmin-form-data'
		,l = 'LAY-app-set-data'
		,active = {
			add: function(){
				layer.open({
				  	type: 2
				  	,title: '数据字典操作 添加'
				  	,content: 'save_or_update.chtml'
				  	,area: ['420px', '480px']
				  	,btn: ['确定', '取消']
				  	,yes: function(index, layero){
						layero.find(f).contents().find("#"+a).click();
				  	}
					,success: function(e, index){
						var iframe = e.find(f).contents().find("#"+t);
						iframe.find('input[name="id"]').val(admin.randomId());
					}
				}); 
			}
			,edit: function(){
				var checkStatus = table.checkStatus(d)
				,data = checkStatus.data; //得到选中的数据
				data.length == 0 ? layer.msg("请选中一项") : data.length > 1 ? layer.msg("只能选中一项") : 
				layer.open({
						type: 2,
						title: "数据字典操作 修改",
						content: "save_or_update.chtml?id=" + data[0].id,
						area: ["450px", "200px"],
						btn: ["确定", "取消"],
						yes: function(index, layero) {
							layero.find(f).contents().find("#"+b).click(); 
						},
						success: function(t, e) {
							var iframe = t.find(f).contents().find("#"+t);
							iframe.find('input[name="name"]')[0].value = data[0].name
							,iframe.find('input[name="id"]')[0].value = data[0].id 
							,iframe.find('input[name="code"]')[0].value = data[0].code
							,iframe.find('input[name="value"]')[0].value = data[0].value
							,iframe.find('input["create_time"]')[0].value = (data[0].create_time)
							,iframe.find('input["update_time"]')[0].value = (data[0].update_time)
							,iframe.find('textarea[name="desc"]')[0].value = data[0].desc;
						}
				})
			}
			,del: function(t){
				var checkStatus = table.checkStatus(l)
				,checkData = checkStatus.data; //得到选中的数据
				checkData.length == 0 ? layer.msg("请选中") :
				layer.prompt({
				  	formType: 1
				  	,title: '敏感操作，请验证口令'
				}, function(value, index){
					layer.close(index); // 必须放在靠前的位置，否则无法关闭
				  	var arr = []; 
				  	for(var index in checkData){
					  	var data = {};
					  	data["token"] = value
					  	,data["id"] = checkData[index].id;
					  	arr[index] = data;
				  	}
				  	layer.confirm('确定删除吗？', function(data) {
					  	admin.cajax({
						  	method: 'remove'
						  	,data: JSON.stringify(arr) 
						  	,success: function(){
						  		table.reload(l);
						  		layer.close(layer.index);
						  	}
					  	}); 	  
				  	});
				}); 
			}
		};
		table.render({
			elem: '#' + l
			,url: 'list.do'  
			,cols: [[
				{type: 'checkbox', fixed: 'left'}
				,{field:'id', title:'ID', fixed: 'left', sort: true}
				,{field:'code', title:'类型代码', width:120, sort: true}
				,{field:'name', title:'名称', width:150, sort: true}
				,{field:'create_time', title:'创建时间', width:150, sort: true}
				,{field:'update_time', title:'修改时间', width:150, sort: true}
				,{field:'value', title:'值', width:80}
				,{field:'desc', title:'备注', width:100}  
			]]
			,page: !0 
			,text: "对不起，加载出现异常！"
		});
		$('.layui-btn.layuiadmin-btn-data').on('click', function(){
		  var type = $(this).data('type');
		  active[type] ? active[type].call(this) : '';
		});
	});
	 
	</script>
 </body>
</html>