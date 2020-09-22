<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="my" uri="/WEB-INF/jstl/custom.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<!--time: 2018年11月21日 15:25:32-->
	<!--author: dbag-->
	<!--blog: https://github.com/9499574/layui-form-create-->
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>layui表单生成器-懒人专用</title>
	<link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
	<style>
	html,body{background-color: #f2f2f2}
	.layui-fluid{margin-top: 15px;}
	.content{min-height: 796px;}
	.nav{text-align: center;}
	.nav button{margin-bottom: 3px;width: 80px;}
	.layui-card-body .layui-btn+.layui-btn{margin-left: 0px;}
	.code-show{min-height: 454px;}
	.js-show{min-height: 200px;}
	.del-form{line-height: initial;position: absolute;right: 15px;top: 50%;margin-top: -15px;}
	.code .layui-form-itemed {
		border: 1px dashed #01aaed !important;
	}	
	.code .layui-form-item:hover {
		border: 1px dashed #01aaed;
	} 
	.code .layui-form-item {
		border: 1px dashed transparent;
	} 
	/* 
	.code .layui-form-item:nth-child(1)::after {
	    position: absolute;
	    content: "";
	    width: 30px;
	    height: 30px;
	    background-size: contain;
	    background-image: url(data:image/svg+xml;base64,PHN2ZyBmaWxsPSIjMDAwMDAwIiBoZWlnaHQ9IjI0IiB2aWV3Q…AxMnoiLz4gICAgPHBhdGggZD0iTTAgMGgyNHYyNEgweiIgZmlsbD0ibm9uZSIvPjwvc3ZnPg==);
	    background-repeat: no-repeat;
	    background-position: center center;
	    top: 0;
	   
	} */
	
	.delete-bt {
		position: absolute;
		right: 0px;
		width: 0;
		height: 0;
		border-style: solid;
		border-width: 0 30px 30px 0;
		border-color: transparent #67b2e4 transparent transparent;
		z-index: 999;
	}
	.delete-bt::after {
		position: absolute;
		content: "";
		width: 15px;
		height: 15px;
		background-size: contain;
		background-image: url(data:image/svg+xml;base64,PHN2ZyBmaWxsPSIjMDAwMDAwIiBoZWlnaHQ9IjI0IiB2aWV3Qm94PSIwIDAgMjQgMjQiIHdpZHRoPSIyNCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4gICAgPHBhdGggZD0iTTE5IDYuNDFMMTcuNTkgNSAxMiAxMC41OSA2LjQxIDUgNSA2LjQxIDEwLjU5IDEyIDUgMTcuNTkgNi40MSAxOSAxMiAxMy40MSAxNy41OSAxOSAxOSAxNy41OSAxMy40MSAxMnoiLz4gICAgPHBhdGggZD0iTTAgMGgyNHYyNEgweiIgZmlsbD0ibm9uZSIvPjwvc3ZnPg==);
		background-repeat: no-repeat;
		background-position: center center;
		top: 0;
		right: -28px;
	}
	</style>
</head>
<body>
<div class="layui-fluid" >
	<div class="layui-row layui-col-space10">
		<div class="layui-col-md1">
			<div class="layui-card nav click-but">
				<div class="layui-card-header">长</div>
				<div class="layui-card-body">
					<button class="layui-btn" data-size="block" data-type="text">输入框</button>
					<button class="layui-btn" data-size="block" data-type="password">密码框</button>
					<button class="layui-btn" data-size="block" data-type="select">选择框</button>
					<button class="layui-btn" data-size="block" data-type="checkbox_a">复选框</button>
					<button class="layui-btn" data-size="block" data-type="checkbox_b">开关</button>
					<button class="layui-btn" data-size="block" data-type="radio">单选框</button>
					<button class="layui-btn" data-size="block" data-type="textarea">文本域</button>
					<button class="layui-btn" data-size="block" data-type="submit">提交</button>
				</div>
			</div>
			<div class="layui-card nav">
				<div class="layui-card-header">短</div>
				<div class="layui-card-body">
					<button class="layui-btn" data-size="inline" data-type="text">输入框</button>
					<button class="layui-btn" data-size="inline" data-type="password">密码框</button>
					<button class="layui-btn" data-size="inline" data-type="select">选择框</button>
					<button class="layui-btn" data-size="inline" data-type="checkbox_a">复选框</button>
					<button class="layui-btn" data-size="inline" data-type="checkbox_b">开关</button>
					<button class="layui-btn" data-size="inline" data-type="radio">单选框</button>
					<button class="layui-btn" data-size="inline" data-type="textarea">文本域</button>
					<button class="layui-btn" data-size="block" data-type="submit">提交</button>
				</div>
			</div>

		</div>
		<div class="layui-col-md8">
			<div class="layui-card content">
				<div class="layui-card-header">表单-问题反馈QQ群: 925487043
					<button class="layui-btn layui-btn-sm layui-btn-danger del-form" data-type="del"> <i class="layui-icon">&#xe640;</i></button>
				</div>
				<div class="layui-card-body code">
					<form class="layui-form" action="" onsubmit="return false">
					</form>
				</div>
			</div>
		</div>
		<div class="layui-col-md3">
		
			<div class="layui-card">
				<div class="layui-card-header">设置</div>
				<div class="layui-card-body">
				</div>
			</div>
		</div>
	</div>
	<div class="layui-row">
		<div class="layui-col-md12">
			<div class="layui-card">
				<my:fm-generate-form />
			</div>
		</div>
	</div>
	<div class="layui-row">
		<div class="layui-card r-code-html">
			<div class="layui-card-header">html</div>
			<div class="layui-card-body">
				<textarea name=""  class="layui-textarea code-show"></textarea>
			</div>
		</div>
	</div>
	<div class="layui-row">
		<div class="layui-card r-code-js">
			<div class="layui-card-header">JS</div>
			<div class="layui-card-body">
				<textarea name=""  class="layui-textarea js-show"></textarea>
			</div>
		</div>
	</div>
</div> 
</body>
<script src="<%=basePath%>layuiadmin/layui/layui.js?t=1"></script>
<script>
	var token = top.token;
	var $
    layui.config({
        base: '<%=basePath%>layuiadmin/' //静态资源所在路径
    }).extend({
    	index: 'lib/index' //主入口模块 
        ,temp: 'admin/template/temp'
    }).use(['index', 'temp', 'jquery'],function(){
    	$ = layui.jquery;
    	/* layui.layer.open({
    		type: 1
    		,title: 'xx'
    		//,area: ['420px', '400px']
    		,btn: ['确定', '取消']
    		,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;"><i class="layui-icon layui-icon-logout"></i></div>'
    		,yes: function(index, layero){ 
				layui.layer.closeAll();
			}
    	}); */
    	
    	$('.code').on('click', '.layui-form-item', function(e){
    		//console.log($(this));
    		$('.code .delete-bt').remove();
    		$(this).prepend('<div class="delete-bt"></div>');
    		//$(".delete-bt").eq(0).addClass("layui-form-itemed");
    		$('.code .layui-form-item').removeClass("layui-form-itemed");
    		$(this).addClass("layui-form-itemed");
    		//$(this).toggleClass("layui-form-itemed"); 
    	});
    	$('.code').on('click', '.delete-bt', function(e){
    		$('.code .delete-bt').eq(0).parents(".layui-form-item").eq(0).remove();
    	});
    });
   /*  function removeDo(){  
    	// 
    	$('.code .delete-bt').eq(0).parents(".layui-form-item").eq(0).remove();
		//console.log($('.code .delete-bt').eq(0).parents(".layui-form-item").eq(0).find(".layui-form-label").eq(0).html());
	} */
</script>
</html>