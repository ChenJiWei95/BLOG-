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
  <title>layuiAdmin 角色管理</title>
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
        <div class="layui-form-item">
          <div class="layui-inline">
            角色筛选
          </div>
          <div class="layui-inline">
            <select name="rolename" lay-filter="LAY-user-adminrole-type">
              <option value="-1">全部角色</option>
              <option value="0">管理员</option>
              <option value="1">超级管理员</option>
			  <!--后面的自动添加进来-->
            </select>
          </div>
        </div>
      </div>
      <div class="layui-card-body">
        <div style="padding-bottom: 10px;">
          <button class="layui-btn layuiadmin-btn-role" data-type="add">添加</button>
		  <button class="layui-btn layuiadmin-btn-role" data-type="edit">编辑</button>
		  <button class="layui-btn layuiadmin-btn-role" data-type="batchdel">删除</button>
        </div>
        <table id="LAY-user-back-role" lay-filter="LAY-user-back-role"></table>  
      </div>
    </div>
  </div>
 <script src="<%=basePath%>layuiadmin/layui/layui.js"></script>  
  <script>
  layui.config({
    base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'useradmin', 'table'], function(){
    var $ = layui.$
    ,form = layui.form
    ,a = "LAY-user-role-add"
    ,f = "iframe"
	,t = 'layuiadmin-form-role'
	,b = 'LAY-user-role-update'
	,l = 'LAY-user-back-role'
    ,table = layui.table;
    
    //搜索角色
    form.on('select(LAY-user-adminrole-type)', function(data){
		//执行重载
		table.reload('LAY-user-back-role', {
			where: {
				role: data.value
			}
		});
    });
  
    //事件
    var active = {
		batchdel: function(){
			var checkStatus = table.checkStatus('LAY-user-back-role')
			,checkData = checkStatus.data; //得到选中的数据
			checkData.length == 0 ? layer.msg("请选中一项") : checkData.length > 1 ? layer.msg("只能选中一项") :
			layer.confirm('确定删除吗？', function(index) {
				admin.req({
					url: 'remove.do'
					,type: 'post'
					,data: {data: JSON.stringify(checkData.field)}
					,dataType: "json"
					,done: function(data){
						layer.msg("添加成功！", {time: 2000}),
						parent.table.reload(l);
					}
				});
				table.reload(l);
				layer.msg('已删除');
			});
		},
		add: function(){
			layer.open({
				type: 2
				,title: '添加新角色'
				,content: 'save_or_update.chtml'
				,area: ['420px', '480px']
				,btn: ['确定', '取消']
				,yes: function(index, layero){
					layero.find(f).contents().find("#"+a).click();
				}
			});
		}
		,edit: function(){
			var checkStatus = table.checkStatus(l)
			,data = checkStatus.data; //得到选中的数据
			data.length == 0 ? layer.msg("请选中一项") : data.length > 1 ? layer.msg("只能选中一项") :
			layer.open({
                type: 2,
                title: "编辑角色",
                content: "save_or_update.chtml",
                area: ["420px", "480px"],
                btn: ["确定", "取消"],
                yes: function(index, layero) {
                    layero.find(f).contents().find("#"+b).click();
                },
                success: function(e, index) {
					//这是渲染完之后调用 可以用于初始化
					var form = e.find(f).contents().find("#"+t);
					form.find('input[name="id"]').val(data[0].id)
					,form.find('textarea[name="name"]').val(data[0].name)
					,form.find('.create_time').text(data[0].name)
					,form.find('.update_time').text(data[0].desc)
					,form.find('textarea[name="desc"]').val(data[0].desc)
					,form.find('textarea[name="limitData"]').val(data[0].priority)
					//补充选项框
				}
            })
		}
    }  
    table.render({
		elem: '#' + d
		,id: d
		,url: 'list.do'  
		,cols: [[
			{type: 'checkbox', fixed: 'left'}
			,{field:'id', title:'ID', width:80, fixed: 'left', unresize: true, sort: true}
			,{field:'typeCode', title:'类型代码', width:120, edit: 'text', sort: true}
			,{field:'typeName', title:'名称', width:150, edit: 'text', sort: true}
			,{field:'createTime', title:'创建时间', width:150, edit: 'text', sort: true}
			,{field:'updateTime', title:'修改时间', width:150, edit: 'text', sort: true}
			,{field:'typeVal', title:'值', width:80, edit: 'text'}
			,{field:'typeMsg', title:'备注', width:100}  
		]]
		,page: !0 
		,text: "对不起，加载出现异常！"
	});
    $('.layui-btn.layuiadmin-btn-role').on('click', function(){
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
    });
  });
  </script>
</body>
</html>

