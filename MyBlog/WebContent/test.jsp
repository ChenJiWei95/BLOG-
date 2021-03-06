<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
  <meta name="Author" content="cjw">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <title>首页</title>
  <link type="text/css" rel="stylesheet" href="<%=basePath%>css/MyCss.css" />
  <link type="text/css" rel="stylesheet" href="<%=basePath%>css/basi.css" />
  <link type="text/css" rel="stylesheet" href="<%=basePath%>css/zoom.css" />
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/style/admin.css" media="all">
  <style>=
	.progress-bar {position: fixed;}
	.progress-bar {margin-left: 4px; margin-right: 4px;}
	.progress-bar > .progress-bar-con {position: relative; height: 3px; border-radius: 1px; background: #000; width: 4%;}
 
 
	.con {width: 1073px; position: relative; margin: auto;}
	.con > div {margin-top: 10px; margin-bottom: 10px;  position: relative; background: #fff; border-radius: 4px; overflow: hidden; padding: 10px;}
	.con > div:nth-child(1) {margin-top: 5px}
	.con > .nav-done {padding: 5px 7px; overflow: hidden; }
	.web-icon {background: url(img/pho.jpg); width: 32px; height: 32px; float: left;     background-size: cover; border-radius: 3px; box-shadow: 1px 1px 4px -1px rgba(0,0,0,0.7);}
	.con > .nav-done > label {line-height: 29px; padding:0 4px; color: #555;}
	/* 3D微粒显示 */
	.con > .show-done {height: 398px; padding: 5px; overflow: hidden;}
	.con > .show-done canvas {position: absolute; top: 0; left: 0; width: 100%; height: 100%;}
	/* 文章简要显示 */
	.con > .simple-done > div {margin-left: 10px; margin-right: 10px;}
	.con > .simple-done > .simple-nav-done {height: 35px; border-bottom: 1px solid #e6e6e6; margin-left: 30px; margin-right: 30px;}
	.con > .simple-done > .simple-nav-done > label, 
	.nav-done > label {display: block; line-height: 34px; float: left; font-size: 14px; margin-left: 20px; cursor: pointer;}
	.con > .simple-done > .simple-nav-done > label:nth-child(1), 
	.nav-done > label:nth-child(1) {margin-left:0px;}
	.label-sele-css {border-bottom: 2px solid #000;}
	.label-unsele-css {border-bottom: 2px solid #0000;}
	.con > .simple-done > .simple-content-done > div {margin: auto;}
	.con > .simple-done > .simple-content-done > div > ul > li  {width: 302px; height: 287px; margin-left: 20px; margin-right: 20px; box-shadow: 0 1px 1px rgba(0,0,0,0.7); float: left; margin-top: 10px; margin-bottom: 10px; border-radius: 3px; overflow: hidden; cursor: pointer;}
	.con > .simple-done > .simple-content-done > div > ul > li:hover  {box-shadow:1px 1px 4px rgba(0,0,0,0.7)}
	.con > .simple-done > .simple-content-done > div > ul > li > img {display: block;}
	.con > .simple-done > .simple-content-done > div > ul > li > .desc {height: 50px;}
	.con > .simple-done > .simple-content-done > div > ul > li > .desc > div {padding: 5px; padding: 8px 12px;}
	.con > .simple-done > .simple-content-done > div > ul > li > .desc > div > .desc-title {font-size: 14px; font-weight: bold; color: #000;}
	.con > .simple-done > .simple-content-done > div > ul > li > .desc > div > .desc-con {color: #8B8B8C;}
	.con > .simple-done > .simple-content-done > div > ul > li > .interacte {height: 17px; background: #f6f6f6; border-top: 1px solid #e7e7e7; color: #8B8B8C; position: relative; padding: 6px;}
	.con > .simple-done > .simple-content-done > div > ul > li > .interacte label:first-child {margin-left:0!important;}
	.con > .simple-done > .simple-content-done > div > ul > li > .interacte label {float: left; margin-left: 18px;}
	.con > .simple-done > .simple-content-done > div > ul > li > .interacte label i{width: 19px; height: 19px; float: left; margin-right: 8px;}
	.con > .simple-done > .simple-content-done > div > ul > li > .interacte label .fa-browse {background: url(img/main/fa-browse.png); }
	.con > .simple-done > .simple-content-done > div > ul > li > .interacte label .fa-chat {background: url(img/main/fa-chat.png);}
	.con > .simple-done > .simple-content-done > div > ul > li > .interacte label .fa-heart {background: url(img/main/fa-heart.png);}

	/*   */
	.bom {margin-top: 8px; position: relative; padding: 0!important;}
	.bom > .bom_container { margin: auto; background: #fff; border-radius: 4px; }
	.bom > .bom_container > li {color: #586069; list-style: none; padding: 10px 245px;}
	.bom > .bom_container > li > span {color: #a71d5d; cursor: pointer;}

	.load-css {box-shadow: 5px 7px 2px rgba(0, 0, 0, 0.25); padding: 3px; color: #fff; font-size: 14px; z-index: 20; position: absolute; background: #a9dca9; width: 
		130px;}
	.load-css > i {background: url(img/ref2.png); background-repeat: no-reperat; width: 32px; margin-left: 10px; float: left; }
	.load-css > span {display: block; margin-left: 50px;}
	
	/* S Model */
	.myModal > .myModalImg {border-radius: 5px; cursor: pointer; transition: 0.3s; }
	
	.myModal {display: none; position: fixed; z-index: 1; padding-top: 100px; left: 0; top: 0; width: 100%; height: 100%; overflow: auto; background-color: rgba(0,0,0,0.9); }
	.myModal > .myModalImg { margin: auto; display: block; }
	.myModal > .desc { margin: auto; display: block; text-align: center; color: #ccc; padding: 10px 0; }
	.pit-open-scale {display: block !important; animation-name: picture_open_zoom; animation-duration: 0.8s; }
	.pit-close-scale { display: block !important; animation-name: picture_close_zoom; animation-duration: 0.8s; }
	@keyframes picture_open_zoom {
		from {transform: scale(0.1); opacity: 0.5;} 
		to {transform: scale(1); opacity: 1;}
	}
	@keyframes picture_close_zoom {
		from {transform: scale(1); opacity: 1;} 
		to {transform: scale(0.1); opacity: 0;}
	}
	/* The Close Button */
	.close { position: absolute; top: 15px; right: 35px; color: #f1f1f1; font-size: 40px; font-weight: bold; transition: 0.3s; }
	.close:hover,
	.close:focus { color: #bbb; text-decoration: none; cursor: pointer; }
	@media only screen and (max-width: 500px){
		.myModal {
			width: 100%;
		}
	}
	/* E Model */
  </style>
 </head>
 <body>
  	<div class="layui-container">  
	    <!-- 导航栏 -->
		 
	  移动设备、平板、桌面端的不同表现：
	  <div class="layui-row">
	    <div class="layui-col-xs6 layui-col-sm6 layui-col-md4">
	      移动：6/12 | 平板：6/12 | 桌面：4/12
	    </div>
	    <div class="layui-col-xs6 layui-col-sm6 layui-col-md4">
	      移动：6/12 | 平板：6/12 | 桌面：4/12
	    </div>
	    <div class="layui-col-xs4 layui-col-sm12 layui-col-md4">
	      移动：4/12 | 平板：12/12 | 桌面：4/12
	    </div>
	    <div class="layui-col-xs4 layui-col-sm7 layui-col-md8">
	      移动：4/12 | 平板：7/12 | 桌面：8/12
	    </div>
	    <div class="layui-col-xs4 layui-col-sm5 layui-col-md4">
	      移动：4/12 | 平板：5/12 | 桌面：4/12
	    </div>
	  </div>
  </div>	 
	<script src="<%=basePath%>layuiadmin/layui/layui.js"></script>
	<script>
	layui.config({
		base: '<%=basePath%>layuiadmin/' //静态资源所在路径
	}).extend({
		index: 'lib/index' //主入口模块
		,partcle: 'lib/partcle'
	}).use(['partcle'], 
	function () { 
		var $ = layui.$
		,F = {
			pictureClick: function (e) {
				console.log(e.attr("src"));
				$(".myModalImg")[0].src = e.attr("src");
				$(".myModal .desc").eq(0).text(e.next().children("div").children(".desc-con").text());
				$(".myModal:first").addClass("pit-open-scale");
				$(".myModalImg:first").addClass("pit-open-scale");
			}
			,myModalClick: function (e) {
				if(e.attr("class").indexOf("myModalImg") == -1){
					$(".myModal:first").addClass("pit-close-scale");
					$(".myModal:first").removeClass("pit-open-scale");
					setTimeout(function(){
						$(".myModal:first").removeClass("pit-close-scale");
					}, 700);
				}
			}
			,nvaClick: function (e) {
				e.siblings().removeClass("label-sele-css");
				e.removeClass("label-unsele-css").addClass("label-sele-css");
				if(e.attr("data-type") == 1)
					self.location = "https://github.com/ChenJiWei95";
				else
					alert("正在码出来，轻耐心等待！");
			}
		};
		layui.partcle.render();  
	
		var i = 4;
		var timer = setInterval(function(){
			i += parseInt(Math.random() * (1000 - 2 + 1) + 2);
			$(".progress-bar-con").eq(0).css("width", i+"%");
			if(i >= 97){
				$(".progress-bar-con").eq(0).css("width", "100%");
				$(".progress-bar-con").eq(0).addClass("dialog-in-scale");
				//为什么动画dialog-in-scale最后透明度是0却又恢复原样
				setTimeout(function(){$(".progress-bar-con").eq(0).css("opacity", 0)}, 800);
				clearInterval(timer);
			}
		}, Math.random() * 30);
		
		$("body").on("click", "*[blog-event]", //
		function() {
			var e = $(this),
			i = e.attr("blog-event");
			F[i] && F[i].call(this, e)
		})
	});
	
	
	</script>
 </body>
</html>