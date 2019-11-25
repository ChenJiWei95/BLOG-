<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
  <meta name="Author" content="cjw">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <title>机构管理</title>
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/style/admin.css" media="all">
  <style>
	*{margin: 0; padding: 0;}
	.manage-button, .layui-card {border-radius: 5px;}
	.manage-button {background: #555;}
	a span {
	    overflow: hidden;
	    white-space: nowrap;
	    text-overflow: ellipsis;
	    display: block; 
	    text-align: center; 
	    font-size: 12px; 
	    color: #999;
    }
	a:hover {box-shadow: 1px 1px 20px -2px #ddd;} 
	/* a:hover span, a:hover i {color: #555;} */
  </style>
 </head>
 <body layadmin-themealias="default">
	<div class="layui-fluid">
		<div class="layui-row layui-col-space15">
			<div class="layui-col-md12"> 
				<div class="layui-card layui-circle">
					<div class="layui-card-header layuiadmin-card-header-auto">
						<!-- <from action="editMovieInfo.do">
							<input type="text" />
							<input file="file" name="fileName">
						</from> -->
						<div class="layui-inline">
				            <label class="layui-form-label">上传文件名</label>
				            <div class="layui-input-inline">
				              <input type="text" id="fileName" placeholder="例如：test.png；该名称对外显示" autocomplete="off" class="layui-input">
				            </div>
				        </div>
						<button type="button" class="layui-btn" id="test1">上传</button>
					</div>
					<div class="layui-card-body" style="overflow: hidden;">
						<c:forEach begin="0" items="${fileUpAndDowns}" step="1" var="FileUpAndDown" varStatus="varsta"> 
						<a href='download.do?fileName=${FileUpAndDown.actual_name}' title='${FileUpAndDown.file_name}' style="margin-right: 10px; margin-bottom: 10px; float: left; display: block; border: 1px solid #dad9d9; width: 59px; border-radius: 3px; padding: 21px 8px; padding-bottom: 8px;">
							<i class="layui-icon layui-icon-file" style="display: block; font-size: 38px; text-align: center; height: 29px; color: #999;"></i>
							<span>${FileUpAndDown.file_name}</span>
						</a>  
						</c:forEach>
					</div>
					<!-- <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
					  <legend>常规使用：普通图片上传</legend>
					</fieldset>
					 
					<div class="layui-upload">
					  <button type="button" class="layui-btn" id="test1">上传</button>
					  <div class="layui-upload-list">
					    <img class="layui-upload-img" id="demo1">
					    <p id="demoText"></p>
					  </div>
					</div> -->
				</div>
			</div>
		</div>
	</div>  
	<script src="<%=basePath%>layuiadmin/layui/layui.js?t=1"></script>
	<script>  
	layui.config({
		base: '<%=basePath%>layuiadmin/' // 静态资源所在路径
	}).extend({
		index: 'lib/index' // 主入口模块
		,tree_etc: 'modules/tree_etc'
	}).use(["tree_etc", 'index', 'console', 'element', 'upload'], function() {
		
		var layer  = layui.layer 
		,$ = layui.jquery
		,index = layui.index
		,form = layui.form
		,admin = layui.admin
		,f = 'iframe'
		,a = 'layuiadmin-branch-form-submit'
		,b = 'layuiadmin-branch-form-edit'
		,c = 'branch-form-tags'
		,d = 'LAY-app-set-branch'
		,that_active = {
			download: function(o){
				/* $.ajax({
	                url: 'download.do',
	                type:'post',
	                async:true,
	                success:function () {
	                	console.log("成功");
	                }
	            }); */
	            download();
			}
			,upload: function(){
				//editMovieInfo.do
					
			}
		}
		 
		function download() {
			var exportForm;
	        try {
	            exportForm = $("<form action='download.do' method='post'><input type='text' name='fileName' value='test.png' /></form>")
	            $(document.body).append(exportForm);
	            exportForm.submit();
	        } catch (e) {
	            console.log(e);
	        } finally {
	            exportForm.remove();
	        }
	    }
		
		$('.layui-btn.manage-button').on('click', function(){
			var type = $(this).data('type');
			that_active[type] ? that_active[type].call(this) : '';
		});
		
		/**/ 
		var upload = layui.upload; 
		//普通图片上传
		var uploadInst = upload.render({
		    elem: '#test1'
		    ,url: 'editMovieInfo.do'
		    ,data: {
		    	'fileName' : function(){
		    		return $("#fileName").val();
		    	}
		    }
		    ,before: function(obj){
		      //预读本地文件示例，不支持ie8
		      /* obj.preview(function(index, file, result){
		        $('#demo1').attr('src', result); //图片链接（base64）
		      }); */
		    }
		    ,done: function(res){
		      //如果上传失败
		      if(res.code > 0){
		        return layer.msg('上传失败');
		      }
		      //上传成功
		    }
		    ,error: function(){
		      //演示失败状态，并实现重传
		      var demoText = $('#demoText');
		      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
		      demoText.find('.demo-reload').on('click', function(){
		        uploadInst.upload();
		      });
		    }
		}); 
	}); 
	</script>
 </body>
</html>