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
  			<!-- 标题 -->
  <title>#title#</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/style/admin.css" media="all">
   
</head>
<body>
  <div class="layui-fluid">   
    <div class="layui-card">
      <div class="layui-form layui-card-header layuiadmin-card-header-auto">
        	 搜索栏组件配置
      </div>
      <div class="layui-card-body"> 
        <div style="padding-bottom: 10px;">
          <button class="layui-btn C-btn-saveorupdate c-button" data-type="search_add">添加</button>
		  <button class="layui-btn C-btn-saveorupdate c-button" data-type="search_del">编辑</button>
		  <button class="layui-btn C-btn-saveorupdate c-button" data-type="search_del">删除</button> 
        </div>
        <table id="C-admin-search-table" lay-filter="C-admin-search-table" ></table>  
      </div>
    </div>
    <div class="layui-card">
      <div class="layui-form layui-card-header layuiadmin-card-header-auto">
        	 表头数据组件配置
      </div>
      <div class="layui-card-body"> 
        <div style="padding-bottom: 10px;">
          <button class="layui-btn C-btn-saveorupdate c-button" data-type="table_add">添加</button>
		  <button class="layui-btn C-btn-saveorupdate c-button" data-type="table_edit">编辑</button>
		  <button class="layui-btn C-btn-saveorupdate c-button" data-type="table_del">删除</button> 
        </div>
        <table id="C-admin-table-table" lay-filter="C-admin-table-table" ></table>  
      </div>
    </div>
    <div class="layui-card">
      <div class="layui-form layui-card-header layuiadmin-card-header-auto">
        	 添加、编辑表单组件配置
      </div>
      <div class="layui-card-body"> 
        <div style="padding-bottom: 10px;">
          <button class="layui-btn C-btn-saveorupdate c-button" data-type="form_add">添加</button>
		  <button class="layui-btn C-btn-saveorupdate c-button" data-type="form_edit">编辑</button>
		  <button class="layui-btn C-btn-saveorupdate c-button" data-type="form_del">删除</button> 
        </div>
        <table id="C-admin-form-table" lay-filter="C-admin-form-table" ></table>  
      </div>
    </div>
    <div style="padding-top: 30px;"> <a href="javascript:;" layadmin-event="back" class="layui-btn layui-btn-primary layui-btn-sm">返回上级</a> </div>
  </div>
 <script src="<%=basePath%>layuiadmin/layui/layui.js"></script> 
  <script type="text/html" id="stateTPL">
  {{#  if(d.state === '01'){ }}
    <span style="color: red;">{{ '禁用' }}</span>
  {{#  } else { }}
    <span style="color: #5FB878;">{{ '启用' }}</span>
  {{#  } }}
  </script> 
  <script>
  var table;
  layui.config({
    base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'useradmin', 'table', 'admin'], function(){
    var $ = layui.$
    ,form = layui.form
    ,a = "C-admin-form_add"
	,b = 'C-admin-form-update'
	,e = 'C-btn-saveorupdate'
    ,f = 'iframe'
	,l_a = 'C-admin-search-table'
	,l_b = 'C-admin-table-table'
	,l_c = 'C-admin-form-table'
	,t = 'C-admin-form-form'
	,s = 'C-btn-search'
    ,admin = layui.admin;
    table = layui.table;
    
  	//监听搜索
    form.on('submit('+s+')', function(data){
      	var field = data.field;
      	//执行重载
      	table.reload(l, {
        	where: field
      	});
    });
  
    //事件
    var active = {
    	form_del: function(){
			var arr = []; 
			var checkStatus = table.checkStatus(l)
			,checkData = checkStatus.data; //得到选中的数据
			checkData.length == 0 ? layer.msg("请选中") :
				layer.confirm('确定删除吗？', function(data) {
			  		layer.close(layer.index);
			  		for(var index in checkData){
					  	var data = {};
					  	data["token"] = value
					  	,data["id"] = checkData[index].id;
					  	arr[index] = data;
				  	}
				  	admin.cajax({
					  	method: 'form_remove'
					  	,data: JSON.stringify(arr) 
					  	,success: function(){
					  		table.reload(l);
					  	}
				  	}); 	  
			  	});
		}
    	,table_del: function(){
			var arr = []; 
			var checkStatus = table.checkStatus(l)
			,checkData = checkStatus.data; //得到选中的数据
			checkData.length == 0 ? layer.msg("请选中") :
				layer.confirm('确定删除吗？', function(data) {
			  		layer.close(layer.index);
			  		for(var index in checkData){
					  	var data = {};
					  	data["token"] = value
					  	,data["id"] = checkData[index].id;
					  	arr[index] = data;
				  	}
				  	admin.cajax({
					  	method: 'table_remove'
					  	,data: JSON.stringify(arr) 
					  	,success: function(){
					  		table.reload(l);
					  	}
				  	}); 	  
			  	});
		}
    	,search_del: function(){
			var arr = []; 
			var checkStatus = table.checkStatus(l)
			,checkData = checkStatus.data; //得到选中的数据
			checkData.length == 0 ? layer.msg("请选中") :
				layer.confirm('确定删除吗？', function(data) {
			  		layer.close(layer.index);
			  		for(var index in checkData){
					  	var data = {};
					  	data["token"] = value
					  	,data["id"] = checkData[index].id;
					  	arr[index] = data;
				  	}
				  	admin.cajax({
					  	method: 'search_remove'
					  	,data: JSON.stringify(arr) 
					  	,success: function(){
					  		table.reload(l);
					  	}
				  	}); 	  
			  	});
		}
		,table_add: function(){
			layer.open({
				type: 2
				,title: '添加'
				,content: 'table_save_or_update.chtml?'
				,area: ['420px', '480px']
				,btn: ['确定', '取消']
				,yes: function(index, layero){
					layero.find(f).contents().find("#"+a).click();
				}
				,success: function(e, index) {
					var iframe = e.find(f).contents().find("#"+t);
					iframe.find('input[name="id"]').val(admin.randomId());
				}
			});
		}
		,form_add: function(){
			layer.open({
				type: 2
				,title: '添加'
				,content: 'form_save_or_update.chtml?'
				,area: ['420px', '480px']
				,btn: ['确定', '取消']
				,yes: function(index, layero){
					layero.find(f).contents().find("#"+a).click();
				}
				,success: function(e, index) {
					var iframe = e.find(f).contents().find("#"+t);
					iframe.find('input[name="id"]').val(admin.randomId());
				}
			});
		}
		,search_add: function(){
			layer.open({
				type: 2
				,title: '添加'
				,content: 'search_save_or_update.chtml?'
				,area: ['420px', '480px']
				,btn: ['确定', '取消']
				,yes: function(index, layero){
					layero.find(f).contents().find("#"+a).click();
				}
				,success: function(e, index) {
					var iframe = e.find(f).contents().find("#"+t);
					iframe.find('input[name="id"]').val(admin.randomId());
				}
			});
		}
		,search_del: function(){
			var checkStatus = table.checkStatus(l)
			,data = checkStatus.data; //得到选中的数据
			data.length == 0 ? layer.msg("请选中一项") : data.length > 1 ? layer.msg("只能选中一项") :
			layer.open({
                type: 2,
                title: "编辑",
                content: "search_save_or_update.chtml?"
               	,area: ["420px", "480px"]
                ,btn: ["确定", "取消"]
                ,yes: function(index, layero) {
                    layero.find(f).contents().find("#"+b).click();
                }
                ,success: function(e, index) {
					//这是渲染完之后调用 可以用于初始化
					var form = e.find(f).contents().find("#"+t);
					form.find('input[name="id"]').val(data[0].id)
					,form.find('input[name="name"]').val(data[0].name)
					,form.find('input[name="create_time"]').val(data[0].create_time)
					,form.find('input[name="update_time"]').val(data[0].update_time)
					,form.find('select[name="state"]').val(data[0].state)
					,form.find('textarea[name="desc"]').val(data[0].desc) 
				}
            })
		}
		,form_edit: function(){
			var checkStatus = table.checkStatus(l)
			,data = checkStatus.data; //得到选中的数据
			data.length == 0 ? layer.msg("请选中一项") : data.length > 1 ? layer.msg("只能选中一项") :
			layer.open({
                type: 2,
                title: "编辑",
                content: "form_save_or_update.chtml?"
               	,area: ["420px", "480px"]
                ,btn: ["确定", "取消"]
                ,yes: function(index, layero) {
                    layero.find(f).contents().find("#"+b).click();
                }
                ,success: function(e, index) {
					//这是渲染完之后调用 可以用于初始化
					var form = e.find(f).contents().find("#"+t);
					form.find('input[name="id"]').val(data[0].id)
					,form.find('input[name="name"]').val(data[0].name)
					,form.find('input[name="create_time"]').val(data[0].create_time)
					,form.find('input[name="update_time"]').val(data[0].update_time)
					,form.find('select[name="state"]').val(data[0].state)
					,form.find('textarea[name="desc"]').val(data[0].desc) 
				}
            })
		}
		,table_edit: function(){
			var checkStatus = table.checkStatus(l)
			,data = checkStatus.data; //得到选中的数据
			data.length == 0 ? layer.msg("请选中一项") : data.length > 1 ? layer.msg("只能选中一项") :
			layer.open({
                type: 2,
                title: "编辑",
                content: "table_save_or_update.chtml?"
               	,area: ["420px", "480px"]
                ,btn: ["确定", "取消"]
                ,yes: function(index, layero) {
                    layero.find(f).contents().find("#"+b).click();
                }
                ,success: function(e, index) {
					//这是渲染完之后调用 可以用于初始化
					var form = e.find(f).contents().find("#"+t);
					form.find('input[name="id"]').val(data[0].id)
					,form.find('input[name="name"]').val(data[0].name)
					,form.find('input[name="create_time"]').val(data[0].create_time)
					,form.find('input[name="update_time"]').val(data[0].update_time)
					,form.find('select[name="state"]').val(data[0].state)
					,form.find('textarea[name="desc"]').val(data[0].desc) 
				}
            })
		}
    };  
    table.render({//角色的加载
        elem: "#"+l_b,
        url: 'table_list.do',
        cols: [[
        	{type:"checkbox", fixed:"left"}
        	,{field:"id", title:"ID", width:180}
        	,{field:"name", title:"角色名", width:150}
        	,{field:'create_time', title:'创建时间', width:170, sort: !0}
			,{field:'update_time', title:'修改时间', width:170, sort: !0}
        	,{field:"state", title:"状态", templet: '#stateTPL', align: 'center'}
        	,{field:"desc", title:"具体描述"}
        ]],
        text: "对不起，加载出现异常！"
    }) 
    ,table.render({//角色的加载
        elem: "#"+l_a,
        url: 'search_list.do',
        cols: [[
        	{type:"checkbox", fixed:"left"}
        	,{field:"id", title:"ID", width:180}
        	,{field:"name", title:"角色名", width:150}
        	,{field:'create_time', title:'创建时间', width:170, sort: !0}
			,{field:'update_time', title:'修改时间', width:170, sort: !0}
        	,{field:"state", title:"状态", templet: '#stateTPL', align: 'center'}
        	,{field:"desc", title:"具体描述"}
        ]],
        text: "对不起，加载出现异常！"
    })
    ,table.render({//角色的加载
        elem: "#"+l_c,
        url: 'form_list.do',
        cols: [[
        	{type:"checkbox", fixed:"left"}
        	,{field:"id", title:"ID", width:180}
        	,{field:"name", title:"角色名", width:150}
        	,{field:'create_time', title:'创建时间', width:170, sort: !0}
			,{field:'update_time', title:'修改时间', width:170, sort: !0}
        	,{field:"state", title:"状态", templet: '#stateTPL', align: 'center'}
        	,{field:"desc", title:"具体描述"}
        ]],
        text: "对不起，加载出现异常！"
    }); 
    $('.layui-btn.'+e).on('click', function(){
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
    });
  });
  </script>
</body>
</html>

