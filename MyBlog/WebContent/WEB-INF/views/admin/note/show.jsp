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
  <link rel="stylesheet" href="<%=basePath%>css/git-plugin.css" media="all">  	
  <link rel="stylesheet" href="<%=basePath%>css/basi.css" media="all"> 	
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
  .more-oprate li{
  
  }
  
  .more-oprate li > i, .more-oprate li > .layui-icon{
  	font-size: 28px;
  	cursor: pointer; 
    line-height: 39px;
    margin: 4px;
  }
  </style>
</head>
<body>
  <div class="more-oprate" style="right: 30px; top: 30px;">
  	<ul class="nolist">
		<li title="查找">
			<i class="layui-icon layui-icon-search"></i>
			<form action="show.chtml?type=1" method="post" enctype="application/x-www-form-urlencoded">
			<div class="li-cnt layui-form" action="show.chtml" style=" overflow: hidden; height: 200px;">
				<div class="layui-form-item" style="margin-top: 20px;">
					<label class="layui-form-label">名称</label>
					<div class="layui-input-inline" style="width: 250px;">
						<input type="text" name="Qu_name_lk_s" placeholder="请日志名称" autocomplete="off" class="layui-input">
					</div> 
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">选择时间</label>
					<div class="layui-input-inline" style="width: 120px;">
						<input 
							type="text" 
							name="Qu_createDate_ge_s" 
							placeholder="请输入开始时间" 
							id="date" 
							autocomplete="off" 
							style="width: 120px;" 
							class="layui-input">
					</div> 
					<div class="layui-input-inline" style="width: 120px;">
						<input 
							type="text" 
							name="Qu_createDate_le_s" 
							placeholder="请输入结束时间" 
							id="date1" 
							autocomplete="off" 
							style="width: 120px;" 
							class="layui-input">
					</div>
				</div>  
				<div class="layui-form-item" style="margin-bottom: 20px;">
					<button 
						lay-submit 
						lay-filter="C-btn-search" 
						id="C-btn-search" 
						class="layui-btn c-button" 
						style="float: right; margin-right: 60px;">确认</button>
					<input type="submit" id="submit" class="layui-hide"/>
				</div>
			</div>
			</form>
		</li> 
		<li title="标签查找" >
			<i class="layui-icon layui-icon-note"></i>
			<form action="show.chtml?type=2" method="post" enctype="application/x-www-form-urlencoded">
			<div class="li-cnt layui-form" action="show.chtml" style=" overflow: hidden; height: 300px;">
				<div class="layui-form-item">
					<label class="layui-form-label">筛选标签</label>
					<div class="layui-input-inline" style="width: 250px;"> 
						<c:forEach begin="0" items="${all}" step="1" var="Data" varStatus="varsta">
						   	 <input type="checkbox" name="${Data.id}" lay-skin="primary" title="${Data.name}">
						</c:forEach>
					</div>
				</div>
				<div class="layui-form-item" style="margin-bottom: 20px;">
					<button 
						lay-submit 
						lay-filter="C-btn-search2" 
						id="C-btn-search" 
						class="layui-btn c-button" 
						style="float: right; margin-right: 60px;">确认</button>
					<input type="submit" id="submit2" class="layui-hide"/>
				</div>
			</div>
			</form>
		</li>
		<li title="TOP" class="li-top">
			<i class="layui-icon layui-icon-upload-circle"></i>
			<div class="li-cnt" style="height: 36px;">
			</div>
		</li>
	</ul>
  	 
  </div>
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
  			<div class="content layui-hide">${Note.content}</div>
  			<div class="mark_code"></div>
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
  <script src="<%=basePath%>js/git-plugin V0.js"></script>
  <script id="demo" type="text/html">
  {{# 
	new GitManage(d).getElements().forEach(function(item) { 
		d["item"] = $(item); }}
		<pre class="{{ d.item.className }}"> 
			{{ d.item.innerHTML }}
		</pre>
  {{# }); }}
  
  </script> 
  <script>
  var $;
  layui.config({
    base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'useradmin', 'table', 'admin', 'laytpl', 'laydate'], function(){
    var form = layui.form
    ,laydate = layui.laydate
    ,a = "C-admin-note-add"
	,b = 'C-admin-note-update'
	,e = 'C-btn-saveorupdate'
    ,f = 'iframe'
	,l = 'C-admin-note-table'
	,t = 'C-admin-note-form'
	,s = 'C-btn-search'
	,s2 = 'C-btn-search2'
    ,admin = layui.admin
    ,laytpl = layui.laytpl
    ,table = layui.table;
    $ = layui.$;
    /* var getTpl = demo.innerHTML;
    laytpl(getTpl).render($(".content").eq(0).text(), function(html){
    	console.log("html");
    	console.log(html);
    	$(".mark_code").eq(0).innerHTML = html;
    	//console.log($(".mark_code").eq(0));
    }); */
    for(var i in $(".content")){
    	new GitManage($(".content").eq(i).text()).getElements().forEach(function(item) {
        	var e = $(item);
        	//console.log(e);
        	$(".mark_code").eq(i).append("<"+e.prop("tagName")+" class="+e.attr("class")+">"+e.html()+"</"+e.prop("tagName")+">"); 
        });
    }
        
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
                ,content: "save_or_update.chtml?type=2&id="+note_item.data("id")
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
				}
            })
		}
    }
    
    laydate.render({
        elem: '#date'
        ,type: 'datetime'
    });
    laydate.render({
        elem: '#date1'
        ,type: 'datetime'
    });
    
    form.on("submit("+s+")", function(data){
		/* admin.chtml({
			method: 'show'
			,contentType: 'application/x-www-form-urlencoded'
			,data: data.field  
		}); */
		$("#submit").click();
		return false;
	}) 
    form.on("submit("+s2+")", function(data){ 
		$("#submit2").click();
		return false;
	}) 
    
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

