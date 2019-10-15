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
  <title>设置我的资料</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="../../../layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="../../../layuiadmin/style/admin.css" media="all">
</head>
<body>

  <div class="layui-fluid">
    <div class="layui-row layui-col-space15">
      <div class="layui-col-md12">
        <div class="layui-card">
          <div class="layui-card-header">设置我的资料</div>
          <div class="layui-card-body" pad15>
            
            <div class="layui-form" lay-filter="">
              <div class="layui-form-item">
                <label class="layui-form-label">我的角色</label>
                <div class="layui-input-inline">
                  <select name="role_id">
					<option value="-1">请选择权限角色</option>
			        <c:forEach begin="0" items="${roles}" step="1" var="Role" varStatus="varsta">
					  <option value="${Role.id}">${Role.name}</option>
					</c:forEach>
			      </select>
                </div>
                <div class="layui-form-mid layui-word-aux">当前角色不可更改为其它角色</div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">用户名</label>
                <div class="layui-input-inline">
                  ${admin.username}
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">昵称</label>
                <div class="layui-input-inline">
                  <input type="text" name="name" value="${admin.name_}" lay-verify="nickname" autocomplete="off" placeholder="请输入昵称" class="layui-input">
                </div>
              </div> 
              <div class="layui-form-item">
                <label class="layui-form-label">手机</label>
                <div class="layui-input-inline">
                  <input type="text" name="cellphone" value="${admin.phone}" lay-verify="phone" autocomplete="off" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">邮箱</label>
                <div class="layui-input-inline">
                  <input type="text" name="email" value="${admin.email}" lay-verify="email" autocomplete="off" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">备注</label>
                <div class="layui-input-block">
                  <textarea name="remarks" placeholder="请输入内容" class="layui-textarea">${admin.desc}</textarea>
                </div>
              </div>
              <div class="layui-form-item">
                <div class="layui-input-block">
                  <button class="layui-btn c-button" lay-submit lay-filter="setmyinfo">确认修改</button>
                  <button type="reset" class="layui-btn c-button">重新填写</button>
                </div>
              </div>
            </div>
            
          </div>
        </div>
      </div>
    </div>
  </div>

  <script src="../../../layuiadmin/layui/layui.js"></script>  
  <script>
  layui.config({
    base: '../../../layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'set']);
  </script>
</body>
</html>