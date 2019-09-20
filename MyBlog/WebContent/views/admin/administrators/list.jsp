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
  <title>管理员管理</title>
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
            <label class="layui-form-label">账户</label>
            <div class="layui-input-inline">
              <input type="text" name="loginname" placeholder="请输入" autocomplete="off" class="layui-input">
            </div>
          </div>
          <div class="layui-inline">
            <label class="layui-form-label">手机</label>
            <div class="layui-input-inline">
              <input type="text" name="telphone" placeholder="请输入" autocomplete="off" class="layui-input">
            </div>
          </div>
          <div class="layui-inline">
            <label class="layui-form-label">邮箱</label>
            <div class="layui-input-inline">
              <input type="text" name="email" placeholder="请输入" autocomplete="off" class="layui-input">
            </div>
          </div>
          <div class="layui-inline">
            <label class="layui-form-label">角色</label>
            <div class="layui-input-inline">
              <select name="role">
                <option value="0">管理员</option>
                <option value="1">超级管理员</option>
              </select>
            </div>
          </div>
          <div class="layui-inline">
            <button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-user-back-search">
              <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
            </button>
          </div>
        </div>
      </div>
      
      <div class="layui-card-body">
        <div style="padding-bottom: 10px;">
		  <button class="layui-btn layuiadmin-btn-admin" data-type="add">添加</button>
		  <button class="layui-btn layuiadmin-btn-admin" data-type="edit">编辑</button>
		  <button class="layui-btn layuiadmin-btn-admin" data-type="batchdel">删除</button>
        </div>
        
        <table id="LAY-user-back-admin" lay-filter="LAY-user-back-admin"></table>  
      </div>
    </div>
  </div>

 <script src="<%=basePath%>layuiadmin/layui/layui.js"></script>  
  <script>
  var table;
  layui.config({
    base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'useradmin', 'table'], function(){
    var $ = layui.$
    ,form = layui.form
    ,s = 'LAY-user-back-search'
	,l = 'LAY-user-back-admin'
    ,table = layui.table
	,a = 'LAY-user-admin-add'
	,b = 'LAY-user-admin-update'
	,f = 'layuiadmin-form-admin'
	,i = 'iframe';
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
		batchdel: function(){
			var checkStatus = table.checkStatus(l)
			,checkData = checkStatus.data; //得到选中的数据
			checkData.length == 0 ? layer.msg("请选中") :
			layer.prompt({
			  formType: 1
			  ,title: '敏感操作，请验证口令'
			}, function(value, index){
			  var data = {};
			  data["token"] = value
			  ,data["data"] = checkData;
			  layer.confirm('确定删除吗？', function(index) {
				admin.req({
					url: 'remove.do'
					,type: 'post'
					,data: {data: JSON.stringify(data)}
					,dataType: "json"
					,done: function(data){
						layer.msg("删除成功！", {time: 2000}),
						table.reload(l);
					} 
				});		 
			  });
			  layer.close(index);
			})
		}
		,add: function(){
			layer.open({
				type: 2
				,title: '添加管理员'
				,content: 'save_or_update.chtml'
				,area: ['420px', '420px']
				,btn: ['确定', '取消']
				,yes: function(index, layero){
					layero.find(i).contents().find("#"+a).click(); 
				}
			});
		}
		,edit: function(){
			var checkStatus = table.checkStatus(l)
			,data = checkStatus.data; //得到选中的数据
			data.length == 0 ? layer.msg("请选中一项") : data.length > 1 ? layer.msg("只能选中一项") :
			layer.open({
                type: 2
                ,title: "编辑角色"
                ,content: "save_or_update.chtml"
                ,area: ['420px', '420px']
                ,btn: ["确定", "取消"]
                ,yes: function(index, layero) {
                    layero.find(i).contents().find("#"+b).click();
                }
				,success: function(e, index) {
					var iframe = e.find(i).contents().find("#"+f);
					iframe.find('input[name="id"]').val(data[0].id) 
					,iframe.find('input[name="loginname"]').val(data[0].account)
					,iframe.find('input[name="phone"]').val(data[0].phone)
					,iframe.find('input[name="email"]').val(data[0].email) 
					,iframe.find('textarea[name="msg"]').val(data[0].msg) 
					//补充选择角色
				} 
            })
		}
    }  
    $('.layui-btn.layuiadmin-btn-admin').on('click', function(){
      var type = $(this).data('type');
      active[type] ? active[type].call(this) : '';
    });
	table.render({//管理员的加载
        elem: "#LAY-user-back-admin",
        url: 'list.do',
        cols: [[{
            type: "checkbox",
            fixed: "left"
        },
        {
            field: "id",
            width: 80,
            title: "ID",
            sort: !0
        },
        {
            field: "account",
            title: "账户"
        },
        {
            field: "phone",
            title: "手机"
        },
        {
            field: "email",
            title: "邮箱"
        },
        {
            field: "role",
            title: "角色"
        },
        {
            field: "create_time",
            title: "加入时间",
            sort: !0
        }
		,{
            field: "update_time",
            title: "修改时间",
            sort: !0
        }
		,{
            field: "msg",
            title: "描述", 
        }
		]],
        text: "对不起，加载出现异常！"
    });
	table.on("tool(LAY-user-back-admin)",
    function(e) {
        e.data;
        if ("del" === e.event) layer.prompt({
            formType: 1,
            title: "敏感操作，请验证口令"
        },
        function(t, index) {
            layer.close(index),
            layer.confirm("确定删除此管理员？",
            function(t) {
                console.log(e),
                e.del(),
                layer.close(t)
            })
        });
        else if ("edit" === e.event) {
            t(e.tr);
            layer.open({
                type: 2,
                title: "编辑管理员",
                content: "save_or_update.chtml",
                area: ["420px", "420px"],
                btn: ["确定", "取消"],
                yes: function(e, t) {
                    var l = window["layui-layer-iframe" + e],
                    r = "LAY-user-back-submit",
                    n = t.find("iframe").contents().find("#" + r);
                    l.layui.form.on("submit(" + r + ")",
                    function(t) {
                        t.field;
                        table.reload("LAY-user-front-submit"),
                        layer.close(e)
                    }),
                    n.trigger("click")
                },
                success: function(e, t) {}
            })
        }
    })
  });
  </script>
</body>
</html>

