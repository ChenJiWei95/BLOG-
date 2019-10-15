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
  <title>图片管理</title>
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
          <button class="layui-btn C-btn-saveorupdate c-button" id="upload" data-type="upload" style="margin-right:10px;">上传</button>
		  <button class="layui-btn C-btn-saveorupdate c-button" data-type="edit">编辑</button>
		  <button class="layui-btn C-btn-saveorupdate c-button" data-type="del">删除</button>
        </div>
        <table id="C-admin-aimg-table" lay-filter="C-admin-aimg-table" ></table>
      </div>
    </div>
  </div>
  <script src="<%=basePath%>layuiadmin/layui/layui.js"></script> 
  <script type="text/html" id="toTPL">
	<div class="layui-table-cell laytable-cell-1-0-1"><a href="detail.chtml?id={{ d.id }}" style="pointer: cursor; color: #5FB878;">点击前往</a></div>
  </script>
  <script>
  var table;
  layui.config({
    base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'useradmin', 'table', 'admin', 'upload'], function(){
    var $ = layui.$
    ,form = layui.form
    ,a = "C-admin-aimg-add"
	,b = 'C-admin-aimg-update'
	,e = 'C-btn-saveorupdate'
    ,f = 'iframe'
	,l = 'C-admin-aimg-table'
	,t = 'C-admin-aimg-form'
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
		del: function(){
			var arr = []; 
			var checkStatus = table.checkStatus(l)
			,checkData = checkStatus.data; //得到选中的数据
			checkData.length == 0 ? layer.msg("请选中") :
				layer.confirm('确定删除吗？', function(data) {
			  		layer.close(layer.index);
			  		for(var index in checkData){
					  	var data = {};
					  	data["id"] = checkData[index].id;
					  	arr[index] = data;
				  	}
				  	admin.cajax({
					  	method: 'remove'
					  	,data: JSON.stringify(arr) 
					  	,success: function(){
					  		table.reload(l);
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
					iframe.find('input[name="id"]')[0].value = data[0].id
					,iframe.find('input[name="name"]')[0].value = data[0].name
					,iframe.find('input[name="create_time"]')[0].value = data[0].create_time
					,iframe.find('input[name="update_time"]')[0].value = data[0].update_time
					,iframe.find('input[name="path"]')[0].value = data[0].path
					,iframe.find('textarea[name="desc"]')[0].value = data[0].desc

				}
            })
		}
    };
    table.render({//加载
        elem: "#"+l,
        url: 'list.do',
        cols: [[
        	{type:"checkbox", fixed:"left"}
        	,{field:'id', title:'ID'}
			,{field:'name', title:'名称'}
			,{field:'create_time', title:'创建时间'}
			,{field:'update_time', title:'修改时间'}
			,{field:'path', title:'路径'}
			,{field:'desc', title:'备注'}

        ]],
        text: "对不起，加载出现异常！"
    });
    $('.layui-btn.'+e).on('click', function(){
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
    });
    
    var upload = layui.upload; 
	//普通图片上传
    var uploadInst = upload.render({
	    elem: '#upload'
	    ,url: 'editMovieInfo.do'
	    ,before: function(obj){
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){
	    	  // 展示所上传的图片
	        //$('#demo1').attr('src', result); //图片链接（base64）
	      });
	    }
	    ,done: function(data){ 
	    	var index = parent.layer.getFrameIndex(window.name); 
	    	data.code == '0' && (parent.layer.msg("操作成功！"), parent.layer.close(index), table.reload(l)),
			data.code == '2' && (parent.layer.msg("操作失败！"+data.msg), parent.layer.close(index));
	    }
	    ,error: function(){
	      //演示失败状态，并实现重传
	      var demoText = $('#demoText');
	      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
	      demoText.find('.demo-reload').on('click', function(){
	        uploadInst.upload();
	      });
	    }
	});  
  });
  </script>
</body>
</html>

