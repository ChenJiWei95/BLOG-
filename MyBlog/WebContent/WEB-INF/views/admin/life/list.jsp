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
  <title>LifeShare</title>
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
          <button class="layui-btn C-btn-saveorupdate c-button" data-type="add">添加</button>
		  <button class="layui-btn C-btn-saveorupdate c-button" data-type="edit">编辑</button>
		  <button class="layui-btn C-btn-saveorupdate c-button" data-type="del">删除</button>
        </div>
        <table id="C-admin-life-table" lay-filter="C-admin-life-table" ></table>
      </div>
    </div>
  </div>
  <script src="<%=basePath%>layuiadmin/layui/layui.js"></script> 
  <script type="text/html" id="toTPL">
	<div class="layui-table-cell laytable-cell-1-0-1"><a href="detail.chtml?id={{ d.id }}" style="pointer: cursor; color: #5FB878;">点击前往</a></div>
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
    ,a = "C-admin-life-add"
	,b = 'C-admin-life-update'
	,e = 'C-btn-saveorupdate'
    ,f = 'iframe'
	,l = 'C-admin-life-table'
	,t = 'C-admin-life-form'
	,s = 'C-btn-search'
    ,admin = layui.admin;
    table = layui.table;
    
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
				,content: 'save_or_update.chtml'
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
		,edit: function(){
			var checkStatus = table.checkStatus(l)
			,data = checkStatus.data; //得到选中的数据
			data.length == 0 ? layer.msg("请选中一项") : data.length > 1 ? layer.msg("只能选中一项") :
			layer.open({
                type: 2
                ,title: "编辑"
                ,content: "save_or_update.chtml"
               	,area: ["420px", "480px"]
                ,btn: ["确定", "取消"]
                ,yes: function(index, layero) {
                    layero.find(f).contents().find("#"+b).click();
                }
                ,success: function(e, index) {
					//这是渲染完之后调用 可以用于初始化
					var iframe = e.find(f).contents().find("#"+t);
					frame.find('input[name="id"]')[0].value = data[0].id
					,iframe.find('input[name="time"]')[0].value = data[0].time
					,iframe.find('input[name="pit_url"]')[0].value = data[0].pit_url
					,iframe.find('input[name="chat_num"]')[0].value = data[0].chat_num
					,iframe.find('input[name="brow_num"]')[0].value = data[0].brow_num
					,iframe.find('input[name="like_num"]')[0].value = data[0].like_num
					,iframe.find('textarea[name="content"]')[0].value = data[0].content

				}
            })
		}
    };
    layui.cutil.tableReq({//加载
        elem: "#"+l,
        cols: [[
        	{type:"checkbox", fixed:"left"}
			,{field:'time', title:'时间'}
			,{field:'pit_url', title:'图片路径'}
			,{field:'content', title:'内容'}
			,{field:'chat_num', title:'评论数'}
			,{field:'brow_num', title:'浏览数'}
			,{field:'like_num', title:'喜欢数'}

        ]],table: table,data: {token: token}
    });
    $('.layui-btn.'+e).on('click', function(){
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
    });
  });
  </script>
</body>
</html>

