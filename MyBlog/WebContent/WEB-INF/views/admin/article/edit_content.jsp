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
  <title>随笔编辑 iframe 框</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/style/admin.css" media="all"> 	
  <link rel="stylesheet" href="<%=basePath%>css/git-plugin.css" media="all"> 	
</head>
<body>
  <div class="layui-fluid">
    <div class="layui-row layui-col-space15">
      <div class="layui-col-md12">
        <div class="layui-card"> 
          <div class="layui-card-body" pad15>
            <div class="layui-form"  lay-filter="C-admin-article-form" id="C-admin-article-form">  
              <div class="layui-hide">
                <label class="layui-form-label">ID</label>
                <div class="layui-input-inline">
                  <input type="text" name="id" disabled value="${id}" autocomplete="off" class="layui-input layui-disabled">
                </div>
              </div>
			  <div class="layui-form-item">
                <label class="layui-form-label">标题</label>
                <div class="layui-input-inline">
                  <input type="text" name="name" value="${name}" autocomplete="off" class="layui-input">
                </div>
              </div> 
              <div class="layui-form-item layui-form-text">
                <div class="layui-input-block">
                  <textarea name="mark_code" style="height: 600px;" placeholder="请输入内容" class="layui-textarea c-textarea mark_code">${mark_code}</textarea>
                </div>
              </div>
              <div class="layui-form-item"> 
                <div class="layui-input-block">
					<button class="layui-btn c-button" lay-submit lay-filter="edit_submit">提交</button>
					<button class="layui-btn C-btn-operate c-button" data-type="view">查看</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>  

  <script src="<%=basePath%>layuiadmin/layui/layui.js"></script>
  <script src="<%=basePath%>js/git-plugin V0.js"></script>
  <script>
  layui.config({
    base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'form', 'admin'], function(){
    form = layui.form
    ,$ = layui.$
	,admin = layui.admin
	,b = "edit_submit"
	,e = "C-btn-operate";
	form.on("submit("+b+")", function(data){
		admin.cajax({
			method: 'editcontent'
			,data: data.field
		});
		return !1;
	});
	active = {
		view: function(e){
			layer.open({
				title: '页面层'
				,type: '1'
				,shadeClose: true
				,area: ['80%', '80%']
				,content: '<div class="layui-fluid" style="background: #dddddd91; font-size: 0.8em;"><div class="layui-card"><div class="layui-card-body mark_code_cnt" pad15></div></div></div>'
				,success:function(){
					new GitManage($(".mark_code").eq(0).text()).getElements().forEach(function(item) {
						item.appendTo($$$(".mark_code_cnt").eq(0));
					});
				}
			});
			
		}
	}
	$('.layui-btn.'+e).on('click', function(){
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
    });
  });
  </script>
</body>
</html>