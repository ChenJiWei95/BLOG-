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
  #link#	<!-- 导入link -->
  #style# 	<!-- 样式模板套用 -->
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
          	#serch# <!-- 查询表单项 -->
            <%-- <select name="rolename" lay-filter="LAY-user-adminrole-type">
              <option value="####">全部角色</option>
              <c:forEach begin="0" items="${roles}" step="1" var="Role" varStatus="varsta">
				<option value="${Role.id}">${Role.name}</option>
			  </c:forEach> 
            </select> --%>
          </div>
          <div class="layui-inline">
            <button class="layui-btn c-button" lay-submit lay-filter="C-btn-search">
              <i class="layui-icon layui-icon-search C-btn-search"></i>
            </button>
          </div>
        </div>
      </div>
      <div class="layui-card-body"> 
        <div style="padding-bottom: 10px;">
        	#button#	<!-- 按钮项 -->
          <!-- 
          <button class="layui-btn C-btn-saveorupdate c-button" data-type="add">添加</button>
		  <button class="layui-btn C-btn-saveorupdate c-button" data-type="edit">编辑</button>
		  <button class="layui-btn C-btn-saveorupdate c-button" data-type="del">删除</button> 
		  -->
        </div>
        <table id="C-admin-#sign#-table" lay-filter="C-admin-#sign#-table" ></table>  
      </div>
    </div>
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
    ,a = "C-admin-#sign#-add"
	,b = 'C-admin-#sign#-update'
	,e = 'C-btn-saveorupdate'
    ,f = 'iframe'
	,l = 'C-admin-#sign#-table'
	,m = 'LAY-user-adminrole-type'
	,t = 'layuiadmin-form-role'
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
			layer.prompt({
			  	formType: 1
			  	,title: '敏感操作，请验证口令'
			}, function(value, index){
				layer.close(index); // 必须放在靠前的位置，否则无法关闭
			  	
			  	for(var index in checkData){
				  	var data = {};
				  	data["token"] = value
				  	,data["id"] = checkData[index].id;
				  	arr[index] = data;
			  	}
			  	layer.confirm('确定删除吗？', function(data) {
			  		layer.close(layer.index);
				  	admin.cajax({
					  	method: 'remove'
					  	,data: JSON.stringify(arr) 
					  	,success: function(){
					  		table.reload(l);
					  	}
				  	}); 	  
			  	});
			}); 
			
		}
		,add: function(){
			layer.open({
				type: 2
				,title: '添加角色'
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
		,edit: function(){
			var checkStatus = table.checkStatus(l)
			,data = checkStatus.data; //得到选中的数据
			data.length == 0 ? layer.msg("请选中一项") : data.length > 1 ? layer.msg("只能选中一项") :
			layer.open({
                type: 2,
                title: "编辑角色",
                content: "save_or_update.chtml?type=1&id="+data[0].id,
                area: ["420px", "480px"],
                btn: ["确定", "取消"],
                yes: function(index, layero) {
                    layero.find(f).contents().find("#"+b).click();
                },
                success: function(e, index) {
                	console.log(data);
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
        elem: "#"+l,
        url: 'list.do',
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

