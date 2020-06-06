<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		<div class="layui-form layui-card-header layuiadmin-card-header-auto">
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">名称</label>
					<div class="layui-input-inline">
						<input type="text" name="Qu_name_lk_s" placeholder="请输入" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">	
					<label class="layui-form-label">类型</label>
					<div class="layui-input-inline">
						<select name="Qu_type_eq_s">
							<option value="-1">请选择类型</option>
							<c:forEach begin="0" items="${datas}" step="1" var="Data" varStatus="varsta">
								<option value="${Data.value}">${Data.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="layui-inline">
		            <button class="layui-btn layuiadmin-btn-admin c-button" lay-submit lay-filter="LAY-user-back-search">
		              <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
		            </button>
		     	</div>
			</div>
		</div>
					</div>
					<div class="layui-card-body">
						<div style="padding-bottom: 10px;">
							<button class="layui-btn layuiadmin-btn-data c-button" data-type="add">添加</button>
							<button class="layui-btn layuiadmin-btn-data c-button" data-type="edit">编辑</button>
							<button class="layui-btn layuiadmin-btn-data c-button" data-type="del">删除</button>
						</div>
						<table class="layui-hide" id="LAY-app-set-data" lay-filter="LAY-app-set-data">
							
						</table> 	 
					</div>
				</div>
			</div>
		</div>
	</div>
  <script type="text/html" id="descTPL">
  {{#  if(d.type == 'icon'){ }}
    <i class="layui-icon {{ d.value }}"></i>
  {{#  } else { }}
    {{ d.desc }} 
  {{#  } }}
  </script> 
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
		,s = 'LAY-user-back-search'
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
				  	,area: ['420px', '400px']
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
				var checkStatus = table.checkStatus(l)
				,data = checkStatus.data; //得到选中的数据
				console.log(data);
				data.length == 0 ? layer.msg("请选中一项") : data.length > 1 ? layer.msg("只能选中一项") : 
				layer.open({
						type: 2,
						title: "数据字典操作 修改",
						content: "save_or_update.chtml",
						area: ["420px", "400px"],
						btn: ["确定", "取消"],
						yes: function(index, layero) {
							layero.find(f).contents().find("#"+b).click(); 
						},
						success: function(layo, e) {
							console.log(data[0]);
							layui.util.formVal ({
								el: layo.find(f).contents().find("#"+t)
								,list:[
									{id: data[0].id}
									,{name: data[0].name}
									,{id: data[0].id}
									,{value: data[0].value}
									,{code: data[0].code}
									,{$type: data[0].type, type: 'select'}
									,{create_time: data[0].create_time}
									,{update_time: data[0].update_time}
									,{desc: data[0].desc, type: 'textarea'}
								] 
							});
							/* var iframe = layo.find(f).contents().find("#"+t);
							iframe.find('input[name="name"]')[0].value = data[0].name
							,iframe.find('input[name="id"]')[0].value = data[0].id
							,iframe.find('input[name="name"]')[0].value = data[0].name
							,iframe.find('input[name="value"]')[0].value = data[0].value
							,iframe.find('input[name="code"]')[0].value = data[0].code
							,iframe.find('select[name="type"]')[0].value = data[0].type
							,iframe.find('textarea[name="desc"]')[0].value = data[0].desc
							,iframe.find('input[name="create_time"]')[0].value = data[0].create_time
							,iframe.find('input[name="update_time"]')[0].value = data[0].update_time */
						}
				})
			}
			,del: function(t){
				var checkStatus = table.checkStatus(l)
				,checkData = checkStatus.data; //得到选中的数据
				checkData.length == 0 ? layer.msg("请选中") :
				
			  	layer.confirm('确定删除吗？', function(data) {
			  		var arr = [];
				  	for(var index in checkData){
					  	var data = {};
					  	data["id"] = checkData[index].id;
					  	arr[index] = data;
				  	}
				  	admin.cajax({
					  	method: 'remove'
					  	,data: JSON.stringify(arr) 
					  	,contentType: 'text/plain'
					  	,success: function(){
					  		table.reload(l);
					  		layer.close(layer.index);
					  	}
				  	}); 	  
			  	});
			}
		};
		//监听搜索
	    form.on('submit('+s+')', function(data){
	      var field = data.field;
	      
	      //执行重载
	      table.reload(l, {
	        where: field
	      });
	    });
		table.render({
			elem: '#' + l
			,url: 'list.do'  
			,limit: 20
			,page: !0
			,height: 'full-200'
			,method: 'post'
			,cols: [[
				{type: 'checkbox', fixed: 'left'}
				,{field:'name', title:'名称'}
				,{field:'value', title:'值'}
				,{field:'code', title:'代码'}
				,{field:'type', title:'类型'}
				,{field:'desc', title:'描述', templet: '#descTPL'}
				,{field:'create_time', title:'创建时间'}
				,{field:'update_time', title:'修改时间'} 
			]]
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