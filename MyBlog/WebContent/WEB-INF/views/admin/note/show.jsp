<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>NoteEryDay</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/style/admin.css" media="all"> 
  <style>
  * {
    margin: 0;
    padding: 0;
  }
  body {
    font-size: 12px;
    font-family: "Microsoft YaHei", 宋体, "Segoe UI", "Lucida Grande", Helvetica, Arial, sans-serif, FreeSans, Arimo;
  }
  .item-body {
  	display: block;
  	width: 99%;
    height: 54px;
    margin-top: 2px;
    padding: 4px; 
  }
  .content {
    padding-top: 1px;
    height: 51px;
    overflow: hidden;
  }
  .item-tag{
  	display: block;
  	overflow: hidden;
  	float: left;
  }
  .item-tag-block {
  	margin-left: 4px;
    background: #ddd;
    border-radius: 4px;
    padding: 2px 4px;
    display: inline-block;
    color: #fff;
    float: left;
  }
  .layui-card-header .layui-icon { 
  	top: 40%;
  }
  </style>
</head>
<body>
  <div class="layui-container cartlist-cnt" style="padding-top: 20px;">
  	<c:forEach begin="0" items="${notes}" step="1" var="Note" varStatus="varsta">
  	<div class="layui-card note-item" data-id="${Note.id}">
  		<div class="layui-card-header">
			<label class="name" style="display: block; float: left; font-size: 14px; font-weight: bold; color: #555;"
			>${Note.name}</label>
	  		<label style="display: block; float: right; ">
	  			<i class="layui-icon layui-icon-close" blog-event="remove" style="font-size: 24px; cursor: pointer;"></i>
	  			<i class="layui-icon layui-icon-edit" blog-event="update" style="cursor: pointer; margin-right: 30px; font-size: 24px"></i>
	  			<i class="layui-icon layui-icon-add-circle-fine" blog-event="add" style="cursor: pointer; margin-right: 60px; font-size: 24px"></i>
	  		</label>
	  		 
	  		
		</div>
  		<div class="layui-card-body" style="overflow: hidden;">
  			<textarea name="content" style="border: none;" disabled placeholder="请输入" class="layui-textarea c-textarea mark_code content">${Note.content}</textarea>
  			<label class="item-tag">
				<c:forEach begin="0" items="${noteTabs}" step="1" var="NoteTabBrige" varStatus="varsta">
					<c:if test="${NoteTabBrige.note_id == Note.id}"><label class="item-tag-block">${NoteTabBrige.name}</label></c:if>
				</c:forEach>
			</label>
			<label style="display: block; float: right; ">
	  			上一次修改：<font class="update_date"><c:if test="${empty Note.update_date}">
	  			xxxx-xx-xx xx:xx:xx</c:if><c:if test="${not empty Note.update_date}">${Note.update_date}</c:if></font>
	  		</label>
	  		<label style="display: block; float: right; margin-right: 30px;">
	  			创建时间：<font class="create_date">${Note.create_date}</font>
	  		</label>
  		</div> 
	</div>
	</c:forEach>
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
  }).use(['index', 'useradmin', 'table', 'admin'], function(){
    var $ = layui.$
    ,form = layui.form
    ,a = "C-admin-note-add"
	,b = 'C-admin-note-update'
	,e = 'C-btn-saveorupdate'
    ,f = 'iframe'
	,l = 'C-admin-note-table'
	,t = 'C-admin-note-form'
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
		remove: function(target){
				layer.confirm('确定删除吗？', function(data) {
			  		layer.close(layer.index);
			  		var data = {}
			  		, arr = [];
				  	data["id"] = target.parents(".note-item").eq(0).data("id");
				  	console.log(target);
				  	console.log(target.parents(".note-item"));
				  	arr[0] = data;
				  	console.log("正在进行删除，输出data:");
				  	console.log(data);
				  	admin.cajax({
					  	method: 'remove'
					  	,contentType: 'text/plain'
					  	,data: JSON.stringify(arr) 
					  	,success: function(){
					  		self.location.href="show.chtml";
					  	}
				  	}); 	  
			  	});	
		}
		,add: function(){
			layer.open({
				type: 2
				,title: '添加'
				,content: 'save_or_update.chtml?type=0'
				,area: ['80%', '80%']
				,btn: ['确定', '取消']
				,yes: function(index, layero){
					layero.find(f).contents().find("#"+a).click();
					setTimeout(function(){
						self.location.href="show.chtml";
					}, 500);
				}
				,success: function(e, index) {
					var iframe = e.find(f).contents().find("#"+t);
					//iframe.find('input[name="id"]').val(admin.randomId());
				}
			});
		}
		,update: function(target){
			var note_item = target.parents(".note-item").eq(0);
			layer.open({
                type: 2
                ,title: "编辑"
                ,content: "save_or_update.chtml?type=1&id="+note_item.data("id")
               	,area: ["80%", "80%"]
                ,btn: ["确定", "取消"]
                ,yes: function(index, layero) {
                    layero.find(f).contents().find("#"+b).click();
                    setTimeout(function(){
						self.location.href="show.chtml";
					}, 500);
                }
                ,success: function(e, index) {
					//这是渲染完之后调用 可以用于初始化
					var iframe = e.find(f).contents().find("#"+t);
					iframe.find('input[name="id"]')[0].value = note_item.data("id")
					,iframe.find('input[name="name"]')[0].value = note_item.find(".name").text()
					,iframe.find('input[name="create_date"]')[0].value = note_item.find(".create_date").text()
					,iframe.find('input[name="update_date"]')[0].value = note_item.find(".update_date").text()
					,iframe.find('textarea[name="content"]')[0].value = note_item.find(".content").text()
				}
            })
		}
    }
    
    table.render({//加载
        elem: "#"+l,
        url: 'list.do',
        cols: [[
        	{type:"checkbox", fixed:"left"}
        	,{field:'id', title:'ID'}
			,{field:'name', title:'名称'}
			,{field:'create_date', title:'创建时间'}
			,{field:'update_date', title:'修改时间'}
			,{field:'content', title:'内容'}

        ]],
        text: "对不起，加载出现异常！"
    });
    $('.layui-btn.'+e).on('click', function(){
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
    });
    
    $("body").on("click", "*[blog-event]", //
    function() {
    	var e = $(this),
    	i = e.attr("blog-event");
    	active[i] && active[i].call(this, e)
   })
  });
  </script>
</body>
</html>

