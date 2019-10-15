<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>消息中心</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/style/admin.css" media="all"> 
</head>
<body>
  <div class="layui-fluid">   
    <div class="layui-card" id="C-admin-message">
      <div class="layui-tab layui-tab-brief">
        <ul class="layui-tab-title">
          <li class="layui-this">全部消息</li>
          <li>随笔消息
          	<c:if test="${artCount > 0}"> 
				<span class="layui-badge badge-art">${artCount}</span></li>
        	</c:if> 
		  <li>留言消息 
		  	<c:if test="${repCount > 0}"> 
				<span class="layui-badge badge-rep">${repCount}</span></li>
        	</c:if>
          </li>
          <li>系统消息
            <c:if test="${sysCount > 0}"> 
				<span class="layui-badge badge-sys">${sysCount}</span></li>
        	</c:if>
          </li>
        </ul>
        <div class="layui-tab-content">
          <div class="layui-tab-item layui-show">
            <div class="C-admin-message-btns" style="margin-bottom: 10px;">
              <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="all" data-events="del">删除</button>
              <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="all" data-events="ready">标记已读</button>
              <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="all" data-events="readyAll">全部已读</button>
            </div>
            <table id="C-admin-message-all" lay-filter="C-admin-message-all"></table>
          </div> 
		  <div class="layui-tab-item">
            <div class="C-admin-message-btns" style="margin-bottom: 10px;">
              <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="art" data-events="del">删除</button>
              <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="art" data-events="ready">标记已读</button>
              <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="art" data-events="readyAll">全部已读</button>
            </div>
            <table id="C-admin-message-article" lay-filter="C-admin-message-article"></table>
          </div> 
          <div class="layui-tab-item">
            <div class="C-admin-message-btns" style="margin-bottom: 10px;">
              <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="direct" data-events="del">删除</button>
              <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="direct" data-events="ready">标记已读</button>
              <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="direct" data-events="readyAll">全部已读</button>
            </div>
            <table id="C-admin-message-direct" lay-filter="C-admin-message-direct"></table>
          </div>
		  <div class="layui-tab-item">
            <div class="C-admin-message-btns" style="margin-bottom: 10px;">
              <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="sys" data-events="del">删除</button>
              <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="sys" data-events="ready">标记已读</button>
              <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="sys" data-events="readyAll">全部已读</button>
            </div>
            <table id="C-admin-message-sys" lay-filter="C-admin-message-sys"></table>
          </div>
        </div>
      </div> 
    </div>
  </div>
  <script src="<%=basePath%>layuiadmin/layui/layui.js"></script> 
  <script type="text/html" id="toTPL">
	<div class="layui-table-cell laytable-cell-1-0-1"><a href="detail.chtml?id={{ d.id }}" style="pointer: cursor; color: #5FB878;">{{ d.desc }}</a></div>
  </script>
  <script type="text/html" id="stateTPL">
  {{#  if(d.isRead === '00'){ }}
    <span style="color: #5FB878;">{{ '已读' }}</span> 
  {{#  } else { }}
    <span style="color: red;">{{ '未读' }}</span>
  {{#  } }}
  </script>
  <script>
  var table;
  layui.config({
    base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index','table', 'admin'], function(){
	var t_a = 'C-admin-message-article'
	,t_b = 'C-admin-message-direct'
	,t_c = 'C-admin-message-sys'
	,t_d = 'C-admin-message-all'
	,table = layui.table
	,admin = layui.admin
	,$ = layui.$
	,adapter = (layui.element, {//数据暂存 适配 对准type
        all: {
            text: "全部消息"
            ,id: "C-admin-message-all"
            ,type: '00'
            ,badge: void 0
        },
        art: {
            text: "随笔"
            ,id: "C-admin-message-article"
            ,type: '02'
            ,badge: 'badge-art'
        },
        direct: {
            text: "留言"
            ,id: "C-admin-message-direct"
            ,type: '03'
            ,badge: 'badge-rep'
        },
        sys: {
            text: "系统"
            ,id: "C-admin-message-sys"
            ,type: '04'
            ,badge: 'badge-sys'
        }
    })
    ,a = function(e) {// 这里通过模板调用 e为默认传进来的数据
        return '<a href="detail.html?id=' + e.id + '">' + e.title
    };
	var active = { // 事件
        del: function(e, t) {
            var l = adapter[t]
            ,checkData = table.checkStatus(l.id).data
            ,arr = []; 
            return 0 === checkData.length ? layer.msg("未选中行") : 
            layer.confirm("确定删除选中的数据吗？",
            function() {
            	for(var index in checkData){
				  	var data = {};
				  	data["id"] = checkData[index].id;
				  	arr[index] = data;
			  	}
            	admin.cajax({
					method: 'remove'
					,data: JSON.stringify(arr) 
					,success: function(){
					  	table.reload(l.id);
					}
				});
            })
        },
        ready: function(e, t) {
            var l = adapter[t],
            checkData = table.checkStatus(l.id).data
            ,arr = []
            data = {};
            return 0 === checkData.length ? layer.msg("未选中行") : 
            function() {
            	for(var index in checkData){
            		// 只手机未读id
            		checkData[index].isRead == '01' && (data = {}
				  	,data["id"] = checkData[index].id
				  	,arr.push(data)
				  	,console.log(checkData[index].content)
				  	,console.log(checkData[index].isRead))
			  	}
            	arr.length > 0 ? 
            	admin.cajax({
					method: 'ready'
					,data: JSON.stringify(arr) 
					,success: function(){
						table.reload(l.id);
						// badge != void 0
						// 根据id数量自减 如果为0则删除
						l.badge != void 0 && $("."+l.badge).text()-arr.length <= 0 
						  	? $("."+l.badge).remove() 
						    : $("."+l.badge).text($("."+l.badge).text()-arr.length);
					}
				}) : layer.msg("无需操作！")
            }();
        },
		readyAll: function(e, t) { 
			var l = adapter[t];	
            admin.cajax({
				method: 'readyAll'
				,data: {type: l.type}
				,success: function(){
					table.reload(l.id);
					// type == '00' 全部删除
					// 按类别清除提示
					l.type == '00' 
						? $.each($(".layui-badge"), function(i, item){item.remove()})
						: $("."+l.badge).remove()
				}
			});  
        }
    };
    
	table.render({//全部
        elem: "#"+t_d,
        url: 'list.do',
        page: !0,
        cols: [[
        	{type:"checkbox", fixed:"left"}
        	,{field:'id', title:'ID', align: 'center'}
			,{field:'desc', title:'描述', templet:'#toTPL', align: 'center'}
			,{field:'time', title:'时间', align: 'center'}
			,{field:'isRead', title:'状态', templet:'#stateTPL', align: 'center'}

        ]]
    }),
    table.render({//文章
        elem: "#"+t_a,
        url: 'article_list.do',
        page: !0,
        cols: [[
        	{type:"checkbox", fixed:"left"}
        	,{field:'id', title:'ID', align: 'center'}
			,{field:'desc', title:'描述', templet:'#toTPL', align: 'center'}
			,{field:'time', title:'时间', align: 'center'}
			,{field:'isRead', title:'状态', templet:'#stateTPL', align: 'center'}

        ]]
    }),
    table.render({//留言
        elem: "#"+t_b,
        url: 'direct_list.do',
        page: !0,
        cols: [[
        	{type:"checkbox", fixed:"left"}
        	,{field:'id', title:'ID', align: 'center'}
			,{field:'desc', title:'描述', templet:'#toTPL', align: 'center'}
			,{field:'time', title:'时间', align: 'center'}
			,{field:'isRead', title:'状态', templet:'#stateTPL', align: 'center'}

        ]]
    }),
	table.render({//系统
        elem: "#"+t_c,
        url: 'sys_list.do',
        page: !0,
        cols: [[
        	{type:"checkbox", fixed:"left"}
        	,{field:'id', title:'ID', align: 'center'}
			,{field:'desc', title:'描述', templet:'#toTPL', align: 'center'}
			,{field:'time', title:'时间', align: 'center'}
			,{field:'isRead', title:'状态', templet:'#stateTPL', align: 'center'}

        ]] 
    }), 
	$(".C-admin-message-btns .layui-btn").on("click",
	function() {
		var e = $(this),
		events = e.data("events"),
		type = e.data("type");
		active[events] && active[events].call(this, e, type)
	})
  });
  </script>
</body>
</html>

