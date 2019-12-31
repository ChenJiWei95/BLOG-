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
  <link rel="stylesheet" href="<%=basePath%>css/basi.css" media="all"> 	
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
	
	.c-item {
		position: relative;
		border: 1px solid #dddddd59;
	}
	.item .c-item-text {
		text-align: center;
	}
	.item .c-item-amount {
		text-align: center;
	}
	.righttop-close { 
		width: 32px;
		height: 32px;
	    display: none;
	    position: absolute;
	    right: -3px;
	    top: -3px;
	}
	.down-icon, .updateFileName{
		width: 16px;
		height: 16px;
		display: none;
	}
	.updateFileName:hover{
		color: #707070 !important;
	}
	.down-icon:hover {
		background-image: url('<%=basePath%>resource/uploadImgIcon/download_hover.svg') !important;
	}
	
	.c-item:hover .righttop-close, .c-item:hover .down-icon, .c-item:hover .updateFileName { 
		display: inline-block;
	}
	.item-img {
		background-size: cover;
		height: 238px;
	}
	
	.pointer {cursor: pointer;} .pointer:hover {box-shadow: 1px 1px 20px -2px #ddd;}
	
	.default-color {color: #999 !important;}
	/* a:hover span, a:hover i {color: #555;} */
  </style>
 </head>
 <body layadmin-themealias="default">
 	<div class="myModal" click-event="myModalClick">
		<span class="close" click-event="myModalClick">×</span>
		<img class="myModalImg" alt="">
		<div class="desc"></div>
 	</div>
 	
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
				              <input type="text" id="fileName" placeholder="例如：test；该名称对外显示，不需要后缀名" autocomplete="off" class="layui-input">
				            </div>
				        </div>
						<button type="button" class="layui-btn c-button" id="test1">上传</button>
					</div>
					<div class="layui-card-body" style="overflow: hidden;">
						<%-- <c:forEach begin="0" items="${fileUpAndDowns}" step="1" var="FileUpAndDown" varStatus="varsta"> 
						<a href='download.do?fileName=${FileUpAndDown.actual_name}' title='${FileUpAndDown.file_name}' style="margin-right: 10px; margin-bottom: 10px; float: left; display: block; border: 1px solid #dad9d9; width: 59px; border-radius: 3px; padding: 21px 8px; padding-bottom: 8px;">
							<i class="layui-icon layui-icon-file" style="display: block; font-size: 38px; text-align: center; height: 29px; color: #999;"></i>
							<span>${FileUpAndDown.file_name}</span>
						</a>  
						</c:forEach> --%>
						<div class="layui-row layui-col-space20">
						  	<c:forEach begin="0" items="${fileUpAndDowns}" step="1" var="FileUpAndDown" varStatus="varsta">
					        <div class="layui-col-md3">
					          <div class="grid-demo grid-demo-bg1 pointer item c-item">
					            <i class="righttop-close" click-event="remove" style="background-image:url(<%=basePath%>resource/uploadImgIcon/rit-top-close.svg);" data-id="${FileUpAndDown.id}"></i>
								<div class="item-img" style="background-image:url(<%=basePath%>upload/${FileUpAndDown.actual_name});" click-event="pictureDetail">
								</div>
								<div class="default-color item-text c-item-text" id="fileName">
									<font>${FileUpAndDown.file_name}</font> 
									<!-- <i class="updateFileName layui-icon layui-icon-edit" title="修改文件名称" style=""></i> -->
									<i class="down-icon" click-event="download" title="点击下载图片" style="background-image:url(<%=basePath%>resource/uploadImgIcon/download.svg);" data-filename="${FileUpAndDown.actual_name}"></i>
								</div>
							  </div>
					        </div>
						  	</c:forEach>
						</div>
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
	}).use(["tree_etc", 'index', 'console', 'element', 'upload', 'util'], function() {
		
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
		,util = layui.util;
		// 点击事件
		util.clickEvent({
			remove: function(e){
				// 删除图片
				admin.cajax({
				  	method: 'remove'
				  	,data: {id: e.data("id")}
				  	,success: function(){
				  		e.parents(".layui-col-md3").eq(0).remove();
				  	}
			  	});
			}
			,pictureDetail: function(e){
				// 查看原图
				$(".myModalImg")[0].src = e.css("background-image").substring(5,e.css("background-image").length-2);
				//$(".myModal .desc").eq(0).text(e.attr("alt"));
				$(".myModal:first").addClass("pit-open-scale");
				$(".myModalImg:first").addClass("pit-open-scale"); 
			}
			,myModalClick: function (e) {// 点击关闭模态框
				if(e.attr("class").indexOf("myModalImg") == -1){
					$(".myModal:first").addClass("pit-close-scale");
					$(".myModal:first").removeClass("pit-open-scale");
					setTimeout(function(){
						$(".myModal:first").removeClass("pit-close-scale");
					}, 700);
				}
			}
			,download: function(e){
				self.location.href="download.do?fileName="+e.data("filename");
			}
		});
		 
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
		        return layer.msg(res.msg);
		      }
		      self.location.href="listview.chtml"
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