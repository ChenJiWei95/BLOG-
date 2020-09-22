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
  <link rel="stylesheet" href="<%=basePath%>css/basi.css" media="all"> 	
</head>
<body>
  <div class="myModal" click-event="myModalClick">
		<span class="close" click-event="myModalClick">×</span>
		<img class="myModalImg" alt="">
		<div class="desc"></div>
  </div>
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
  <script type="text/html" id="showImg">
	<img alt="{{ d.name }}" src="<%=basePath%>{{d.path}}" width="40px" click-event="pictureDetail">
  </script>
  <script>
  var token = top.token;
  var table;
  layui.config({
    base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'useradmin', 'table', 'admin', 'upload', 'util', 'cutil'], function(){
    var $ = layui.$
    ,form = layui.form
    ,a = "C-admin-aimg-add"
	,b = 'C-admin-aimg-update'
	,e = 'C-btn-saveorupdate'
    ,f = 'iframe'
	,l = 'C-admin-aimg-table'
	,t = 'C-admin-aimg-form'
	,s = 'C-btn-search'
	,util = layui.util
	,upload = layui.upload
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
  	
    util.clickEvent({
    	pictureDetail: function(e){
			// 查看原图
			$(".myModalImg")[0].src = e.context.currentSrc;
			$(".myModal:first").addClass("pit-open-scale");
			$(".myModalImg:first").addClass("pit-open-scale"); 
		}
	    ,myModalClick: function (e) {// 点击关闭模态框
			if(e.attr("class").indexOf("myModalImg") == -1){
				$(".myModal:first").addClass("pit-close-scale");
				$(".myModal:first").removeClass("pit-open-scale");
				setTimeout(function(){
					$(".myModal:first").removeClass("pit-close-scale");
				}, 700);
			}
		}
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
					layui.util.formVal ({
						el: e.find(f).contents().find("#"+t)
						,list:[
							{id: data[0].id}
							,{name: data[0].name}
							,{create_time: data[0].create_time}
							,{update_time: data[0].update_time}
							,{path: data[0].path}
							,{desc: data[0].desc, type: 'textarea'}
						] 
					});
				}
            })
		}
    };
    layui.cutil.tableReq({//加载
    	elem: "#"+l,
    	data: {token: token}
        ,cols: [[
        	{type:"checkbox", fixed:"left"}
			,{field:'name', title:'名称'}
			,{field:'path', title:'图示', templet:'#showImg'}
			,{field:'create_time', title:'创建时间'}
			,{field:'update_time', title:'修改时间'}
			,{field:'desc', title:'备注'}

        ]],table: table
    });
    $('.layui-btn.'+e).on('click', function(){
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
    });
    
	//普通图片上传
    var uploadInst = upload.render({
	    elem: '#upload'
	    ,url: 'upload.do?token='+token
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

