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
  <title>文章管理</title>
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
         
      </div>
      <div class="layui-card-body">
        <div style="padding-bottom: 10px;">
          <button class="layui-btn C-btn-saveorupdate c-button" data-type="edit_content">编辑随笔</button>
          <button class="layui-btn C-btn-saveorupdate c-button" data-type="add">添加</button>
		  <button class="layui-btn C-btn-saveorupdate c-button" data-type="edit">编辑</button>
		  <button class="layui-btn C-btn-saveorupdate c-button" data-type="del">删除</button>
        </div>
        <table id="C-admin-article-table" lay-filter="C-admin-article-table" ></table>
      </div>
    </div>
  </div>
  <script src="<%=basePath%>layuiadmin/layui/layui.js"></script> 
  <script type="text/html" id="toTPL">
	<div class="layui-table-cell laytable-cell-1-0-1"><a href="detail.chtml?id={{ d.id }}" style="pointer: cursor; color: #5FB878;">点击前往</a></div>
  </script>
  <script>
  var table,token = top.token;
  layui.config({
    base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index', //主入口模块
  }).use(['index', 'table', 'admin', 'cutil'], function(){
    var $ = layui.$
    ,form = layui.form
    ,a = "C-admin-article-add"
	,b = 'C-admin-article-update'
	,e = 'C-btn-saveorupdate'
    ,f = 'iframe'
	,l = 'C-admin-article-table'
	,t = 'C-admin-article-form'
	,s = 'C-btn-search'
    ,admin = layui.admin;
    table = layui.table;
    
    console.log(token);
    
  	//监听搜索
    form.on('submit('+s+')', function(data){
      	var field = data.field;
      	//执行重载
      	table.reload(l, {
      		where: $.extend(field, {token: token})
      	});
    });
  
    //事件
    var active = {
		del: function(){
			var arr = []; 
			var checkStatus = table.checkStatus(l)
			,checkData = checkStatus.data; //得到选中的数据
			checkData.length == 0 ? layer.msg("请选中") :
				layer.confirm('确定删除吗？', function(data) {
			  		var fields='';
				  	for(var index in checkData){
				  		if(checkData[index].id != void 0) fields+=checkData[index].id+",";
				  	}
				  	admin.cajax({
					  	method: 'remove'
					  	,data: {ids: fields, token: token} 
					  	,success: function(){
					  		table.reload(l);
					  		layer.close(layer.index);
					  	}
				  	}); 	  
			  	});
		}
		,add: function(){
			layer.open({
				type: 2
				,title: '添加'
				,content: 'save_or_update.chtml?type=0'
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
		,edit_content: function(){
			console.log("---------------------------");
			var n = parent === self ? layui: top.layui;
			console.log(n.index.openTabsPage);	
			var checkStatus = table.checkStatus(l)
			,data = checkStatus.data; //得到选中的数据
			data.length == 0 ? layer.msg("请选中一项") : data.length > 1 ? layer.msg("只能选中一项") :
			n.index.openTabsPage("../article/edit_content.chtml?id="+data[0].id, "编辑["+data[0].name+"]");
			/*
			layer.open({
	                type: 2
	                ,title: "编辑"
	                ,content: "edit_content.chtml?type=1&id="+data[0].id
	               	,area: ["420px", "480px"]
	                ,btn: ["确定", "取消"]
	                ,yes: function(index, layero) {
	                    layero.find(f).contents().find("#"+b).click();
	                }
	                ,success: function(e, index) {
						//这是渲染完之后调用 可以用于初始化
						var iframe = e.find(f).contents().find("#"+t);
						iframe.find('input[name="id"]')[0].value = data[0].id
						,iframe.find('input[name="name"]')[0].value = data[0].name
					}
	            })
			*/
		}
		,edit: function(){
			var checkStatus = table.checkStatus(l)
			,data = checkStatus.data; //得到选中的数据
			data.length == 0 ? layer.msg("请选中一项") : data.length > 1 ? layer.msg("只能选中一项") :
			layer.open({
                type: 2
                ,title: "编辑"
                ,content: "save_or_update.chtml?type=1&id="+data[0].id
               	,area: ["420px", "480px"]
                ,btn: ["确定", "取消"]
                ,yes: function(index, layero) {
                    layero.find(f).contents().find("#"+b).click();
                }
                ,success: function(e, index) {
					//这是渲染完之后调用 可以用于初始化
					var iframe = e.find(f).contents().find("#"+t);
					iframe.find('input[name="id"]')[0].value = data[0].id
					,iframe.find('input[name="name"]')[0].value = data[0].name
					,iframe.find('input[name="create_time"]')[0].value = data[0].create_time
					,iframe.find('input[name="update_time"]')[0].value = data[0].update_time
					,iframe.find('input[name="pit_url"]')[0].value = data[0].pit_url
					,iframe.find('input[name="tags"]')[0].value = data[0].tags
					,iframe.find('textarea[name="simp_desc"]')[0].value = data[0].simp_desc

				}
            })
		}
    };
    layui.cutil.tableReq({
    	elem: "#"+l
    	,table: table
    	,data: {token: token}
    	,cols: [[
        	{type:"checkbox", fixed:"left"}
        	,{field:'id', title:'ID'}
			,{field:'name', title:'文章名称'}
			,{field:'create_time', title:'创建时间'}
			,{field:'update_time', title:'修改时间'}
			,{field:'pit_url', title:'图片'}
			,{field:'simp_desc', title:'描述'}

        ]]
    }); 
    $('.layui-btn.'+e).on('click', function(){
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
    });
  });
  </script>
</body>
</html>

