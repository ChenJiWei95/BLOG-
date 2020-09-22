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
        <h2> 搜索栏组件配置</h2>
      </div>
      <div class="layui-card-body"> 
        <div style="padding-bottom: 10px;">
          <button class="layui-btn C-btn-saveorupdate c-button" data-type="search_add">添加</button>
		  <button class="layui-btn C-btn-saveorupdate c-button" data-type="search_edit">编辑</button>
		  <button class="layui-btn C-btn-saveorupdate c-button" data-type="search_del">删除</button> 
        </div>
        <table id="C-admin-search-table" lay-filter="C-admin-search-table" ></table>  
      </div>
    </div>
    <div class="layui-card">
      <div class="layui-form layui-card-header layuiadmin-card-header-auto">
        <h2> 表数据列名称配置</h2>
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
        <h2> 添加、编辑表单组件配置</h2>
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
  <script type="text/html" id="boolTPL">
  {{#  if(d.disable === '01'||d.hide === '01' || d.inline === '01' || d.sort === '01'){ }}
    <span style="color: red;">{{ '否' }}</span>
  {{#  } else { }}
    <span style="color: #5FB878;">{{ '是' }}</span>
  {{#  } }}
  </script> 
  <script type="text/html" id="typeTPL">
  {{#  if(d.type === '01'){ }}
    <span style="color: #5FB878;">{{ '搜索栏组件' }}</span>
  {{#  } else if(d.type === '03') { }}
    <span style="color: #5FB878;">{{ '普通操作按钮' }}</span>
  {{#  } else if(d.type === '02') { }}
    <span style="color: #5FB878;">{{ '表单组件' }}</span>
  {{#  } else if(d.type === '04') { }}
    <span style="color: #5FB878;">{{ '表单提交按钮' }}</span>
  {{#  } }}
  </script> 
  <script>
  var token = top.token;
  var table;
  layui.config({
    base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'useradmin', 'table', 'admin', 'cutil'], function(){
    var $ = layui.$
    ,form = layui.form
    ,a = "C-admin-temp-add"
    ,b = 'C-admin-temp-update'
	,e = 'C-btn-saveorupdate'
    ,f = 'iframe'
	,l_a = 'C-admin-search-table'
	,l_b = 'C-admin-table-table'
	,l_c = 'C-admin-form-table'
	,t = 'C-admin-temp-form'
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
			var checkStatus = table.checkStatus(l_c)
			,checkData = checkStatus.data; //得到选中的数据
			checkData.length == 0 ? layer.msg("请选中") :
				layer.confirm('确定删除吗？', function() {
			  		layer.close(layer.index);
			  		console.log(checkData);
			  		for(var index in checkData){
					  	var data = {};
					  	data["id"] = checkData[index].id;
					  	arr[index] = data;
				  	}
				  	admin.cajax({
					  	method: 'form_remove'
					  	,data: JSON.stringify(arr) 
					  	,success: function(){
					  		table.reload(l_c);
					  	}
				  	}); 	  
			  	});
		}
    	,table_del: function(){
			var arr = []; 
			var checkStatus = table.checkStatus(l_b)
			,checkData = checkStatus.data; //得到选中的数据
			checkData.length == 0 ? layer.msg("请选中") :
				layer.confirm('确定删除吗？', function() {
			  		layer.close(layer.index);
			  		console.log(checkData);
			  		for(var index in checkData){
					  	var data = {};
					  	data["id"] = checkData[index].id;
					  	arr[index] = data;
				  	}
				  	admin.cajax({
					  	method: 'table_remove'
					  	,data: JSON.stringify(arr) 
					  	,success: function(){
					  		table.reload(l_b);
					  	}
				  	}); 	  
			  	});
		}
    	,search_del: function(){
			var arr = []; 
			var checkStatus = table.checkStatus(l_a)
			,checkData = checkStatus.data; //得到选中的数据
			checkData.length == 0 ? layer.msg("请选中") :
				layer.confirm('确定删除吗？', function() {
			  		layer.close(layer.index);
			  		console.log(checkData);
			  		for(var index in checkData){
			  			console.log(index);
					  	var data = {};
					  	data["id"] = checkData[index].id;
					  	arr[index] = data;
				  	}
				  	admin.cajax({
					  	method: 'search_remove'
					  	,data: JSON.stringify(arr) 
					  	,success: function(){
					  		table.reload(l_a);
					  	}
				  	}); 	  
			  	});
		}
		,table_add: function(){
			layer.open({
				type: 2
				,title: '添加'
				,content: 'table_save_or_update.chtml'
				,area: ['420px', '480px']
				,btn: ['确定', '取消']
				,yes: function(index, layero){
					layero.find(f).contents().find("#"+a).click();
				}
				,success: function(e, index) {
					var iframe = e.find(f).contents().find("#"+t);
					iframe.find('input[name="id"]').val(admin.randomId())
					,iframe.find('input[name="c_id"]').val(${id})
				}
			});
		}
		,form_add: function(){
			layer.open({
				type: 2
				,title: '添加'
				,content: 'form_save_or_update.chtml'
				,area: ['420px', '480px']
				,btn: ['确定', '取消']
				,yes: function(index, layero){
					layero.find(f).contents().find("#"+a).click();
				}
				,success: function(e, index) {
					var iframe = e.find(f).contents().find("#"+t);
					iframe.find('input[name="id"]').val(admin.randomId())
					,iframe.find('input[name="c_id"]').val(${id})
				}
			});
		}
		,search_add: function(){
			layer.open({
				type: 2
				,title: '添加'
				,content: 'search_save_or_update.chtml'
				,area: ['420px', '480px']
				,btn: ['确定', '取消']
				,yes: function(index, layero){
					layero.find(f).contents().find("#"+a).click();
				}
				,success: function(e, index) {
					var iframe = e.find(f).contents().find("#"+t);
					iframe.find('input[name="id"]').val(admin.randomId())
					,iframe.find('input[name="c_id"]').val(${id})
				}
			});
		}
		,form_edit: function(){
			var checkStatus = table.checkStatus(l_c)
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
					,form.find('input[name="c_id"]').val(data[0].c_id)
					,form.find('input[name="name"]').val(data[0].name)
					,form.find('input[name="label"]').val(data[0].label)
					,form.find('input[name="value"]').val(data[0].value)
					,form.find('input[name="placeholder"]').val(data[0].placeholder)
					,form.find('input[name="type_date"]').val(data[0].type_date)
					,form.find('input[name="html"]').val(data[0].html)
					,form.find('select[name="disable"]').val(data[0].disable)
					,form.find('select[name="inline"]').val(data[0].inline)
					,form.find('select[name="hide"]').val(data[0].hide)
					,form.find('select[name="type"]').val(data[0].type)
					,form.find('textarea[name="desc"]').val(data[0].desc) 
				}
            })
		}
		,search_edit: function(){
			var checkStatus = table.checkStatus(l_a)
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
					,form.find('input[name="c_id"]').val(data[0].c_id)
					,form.find('input[name="name"]').val(data[0].name)
					,form.find('input[name="label"]').val(data[0].label)
					,form.find('input[name="value"]').val(data[0].value)
					,form.find('input[name="placeholder"]').val(data[0].placeholder)
					,form.find('input[name="type_date"]').val(data[0].type_date)
					,form.find('input[name="html"]').val(data[0].html)
					,form.find('select[name="disable"]').val(data[0].disable)
					,form.find('select[name="inline"]').val(data[0].inline)
					,form.find('select[name="hide"]').val(data[0].hide)
					,form.find('select[name="type"]').val(data[0].type)
					,form.find('textarea[name="desc"]').val(data[0].desc) 
				}
            })
		}
		,table_edit: function(){
			var checkStatus = table.checkStatus(l_b)
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
					,form.find('input[name="c_id"]').val(data[0].c_id)
					,form.find('input[name="field"]').val(data[0].field)
					,form.find('input[name="templet"]').val(data[0].templet)
					,form.find('input[name="width"]').val(data[0].width)
					,form.find('select[name="align"]').val(data[0].align)
					,form.find('select[name="sort"]').val(data[0].sort)
				} 
            })
		}
    };   
	var componentArrHead = [[
    	{type:"checkbox", fixed:"left"}
    	,{field:"name", title:"name"}
    	,{field:'label', title:'label'}
    	,{field:"value", title:"value"}
    	,{field:"placeholder", title:"placeholder"}
    	,{field:"html", title:"html"}
    	,{field:"type_date", title:"type_date"}
		,{field:'disable', title:'是否禁用', templet: '#boolTPL', align: 'center'}
    	,{field:"inline", title:"是否内联", templet: '#boolTPL', align: 'center'}
    	,{field:"hide", title:"是否隐藏", templet: '#boolTPL', align: 'center'}
    	,{field:"type", title:"组件类型", templet: '#typeTPL', align: 'center'}
    	,{field:"event", title:"事件关联"}
    	,{field:"desc", title:"描述"}
    ]];
    layui.cutil.tableReq({//角色的加载
        elem: "#"+l_b,
        url: 'table_list.do?id='+${id}, 
        cols: [[
        	{type:"checkbox", fixed:"left"}
        	,{field:"field", title:"field"}
        	,{field:'title', title:'title'}
			,{field:'align', title:'align'}
        	,{field:"templet", title:"templet"}
        	,{field:"width", title:"width"}
        	,{field:"sort", title:"sort", templet: '#boolTPL', align: 'center'}
        ]],table: table
    }) 
    ,layui.cutil.tableReq({//角色的加载
        elem: "#"+l_a,
        url: 'search_list.do?id='+${id},
        cols: componentArrHead,table: table
    })
    ,layui.cutil.tableReq({//角色的加载
        elem: "#"+l_c,
        url: 'form_list.do?id='+${id},
        cols: componentArrHead,table: table
    }); 
     
    $('.layui-btn.'+e).on('click', function(){
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
    });
  });
  </script>
</body>
</html>

