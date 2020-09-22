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
  <title>网站设置</title>
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
          <div class="layui-card-header">网站设置</div>
          <div class="layui-card-body" pad15>
            
            <div class="layui-form" wid100 lay-filter="">
              <div class="layui-form-item">
                <label class="layui-form-label">网站名称</label>
                <div class="layui-input-block">
                  <input type="text" name="sitename" value="${website.sitename}" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">网站域名</label>
                <div class="layui-input-block">
                  <input type="text" name="domain" lay-verify="url" value="${website.domain}" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">首页地址</label>
                <div class="layui-input-block">
                  <input type="text" name="index_url" value="${website.index_url}" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">白名单</label>
                <div class="layui-input-block">
                  <textarea name="white_list" class="layui-textarea">${website.white_list}</textarea>
                  <%-- <input type="text" name="white_list" placeholder="无需权限即可访问的地址，多个以逗号‘,’隔开" value="${website.white_list}" class="layui-input"> --%>
                </div>
              </div>
               <div class="layui-form-item">
                <label class="layui-form-label">国际化设置</label>
                <div class="layui-input-block">
                  <textarea name="languages" class="layui-textarea">${website.languages}</textarea>
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">菜单展开项</label>
                <div class="layui-input-block">
                  <input type="text" name="spread" value="${website.spread}" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">缓存时间</label>
                <div class="layui-input-inline" style="width: 80px;">
                  <input type="text" name="cache_time" lay-verify="number" value="${website.cache_time}" class="layui-input">
                </div>
                <div class="layui-input-inline layui-input-company">分钟</div>
                <div class="layui-form-mid layui-word-aux">本地开发一般推荐设置为 0，线上环境建议设置为 10。</div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">最大文件上传</label>
                <div class="layui-input-inline" style="width: 80px;">
                  <input type="text" name="max_cache_size" lay-verify="number" value="${website.max_cache_size}" class="layui-input">
                </div>
                <div class="layui-input-inline layui-input-company">KB</div>
                <div class="layui-form-mid layui-word-aux">提示：1 M = 1024 KB</div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">上传资源位置</label>
                <div class="layui-input-block">
                  <input type="text" name="upload" placeholder="例如：d:/upload/" value="${website.upload}" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">秘钥</label>
                <div class="layui-input-block">
                  <input type="text" name="secret_key" placeholder="长度50" value="${website.secret_key}" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">上传文件类型</label>
                <div class="layui-input-block">
                  <input type="text" name="cache_type" value="${website.cache_type}" class="layui-input">
                </div>
              </div>
              
              <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">首页标题</label>
                <div class="layui-input-block">
                  <textarea name="title" class="layui-textarea">${website.title}</textarea>
                </div>
              </div>
              <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">META关键词</label>
                <div class="layui-input-block">
                  <textarea name="keywords" class="layui-textarea" placeholder="多个关键词用英文状态 , 号分割">${website.keywords}</textarea>
                </div>
              </div>
              <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">META描述</label>
                <div class="layui-input-block">
                  <textarea name="meta_desc" class="layui-textarea">${website.meta_desc}</textarea>
                </div>
              </div>
              <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">版权信息</label>
                <div class="layui-input-block">
                  <textarea name="copyright" class="layui-textarea">${website.copyright}</textarea>
                </div>
              </div>
              <div class="layui-form-item">
                <div class="layui-input-block">
                  <button class="layui-btn c-button" lay-submit lay-filter="set_website">确认保存</button>
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
  var token = top.token;
  layui.config({
    base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'set', 'form', 'admin'],function(){
	  var $ = layui.$;
	// 站点的设置
	layui.form.on("submit(set_website)",
	function(t) {
		layui.admin.cajax({
			method: 'setwebsite'
			,data: $.extend(t.field, {token : token})
		});
		return !1
	})
  });
  </script>
</body>
</html>