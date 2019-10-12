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
                  <textarea name="mark_code" style="height: 600px;" placeholder="请输入内容" class="layui-textarea">${mark_code}</textarea>
                </div>
              </div>
              <div class="layui-form-item"> 
                <div class="layui-input-block">
					<button class="layui-btn c-button" lay-submit lay-filter="edit_submit">提交</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>  

  <script src="<%=basePath%>layuiadmin/layui/layui.js"></script>
  <script>
  layui.config({
    base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'form', 'admin'], function(){
    form = layui.form
	,admin = layui.admin
	,b = "edit_submit";
	form.on("submit("+b+")", function(data){
		admin.cajax({
			method: 'editcontent'
			,data: data.field
		});
		return !1;
	}); 	
  });
  </script>
</body>
</html>