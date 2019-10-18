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
 	<!-- The Modal -->
	<div class="myModal" blog-event="myModalClick">
		<span class="close"  blog-event="myModalClick">×</span>
		<img class="myModalImg" alt="" />
		<div class="desc"></div>
	</div>
	<div class="progress-bar">
		<div class="progress-bar-con"></div>
	</div>
	<div class="con">
		<!-- 导航栏 -->
		<div class="nav-done">
			<i class="web-icon"></i>
			<label class="label-sele-css" blog-event="nvaClick">首页</label>
			<label class="label-unsele-css" blog-event="nvaClick">标签</label>
			<label class="label-unsele-css" blog-event="nvaClick">生活</label>
			<label class="label-unsele-css" blog-event="nvaClick">邮箱我</label>
			<label class="label-unsele-css" blog-event="nvaClick" data-type="1">GitHub</label>
			<label class="label-unsele-css" blog-event="nvaClick">关于我</label>
		</div>
		<!-- 3D particle -->
		<div class="show-done"></div>
		<div class="web-tip simple-done shake_effect">
			<div class="nav-web-tip-done simple-nav-done">
				<label class="label-sele-css" ">站点信息</label>
			</div>
			<div class="con-web-tip-done simple-content-done">
				<div class="cle-f con-web-tip-page" style="padding: 4px 47px;">
					<i style="background: url(img/main/broad.png); width: 25px; height: 20px; float: left;"></i>&nbsp;<font class="breath_light" style="color: skyblue;">站点正在开发中请耐心等候。。。</font>
				</div>	
			</div>
		</div>
		<!-- 文章简要显示 -->
		<div class="article-done simple-done">
			<div class="nav-article-done simple-nav-done">
				<label class="label-sele-css" blog-event="nvaClick" ">最新随笔</label>
				<label class="label-unsele-css" blog-event="nvaClick" ">最热随笔</label>
			</div>
			<div class="con-article-done simple-content-done">
				<div class="cle-f con-article-page">
					<ul class="nolist">
						<li>
							<img src="img/main/exa-java.jpg" alt="" width="302" height="207" blog-event="pictureClick"/>
							<div class="desc" blog-event="to">
								<div>
									<div class="desc-title">
										java机密
									</div>
									<!--word-break: break-all; 超出 省略号-->
									<div class="desc-con">
										OOP（Object Oriented Programming）面向对象编程
									</div>
								</div>
							</div>
							<div class="interacte">
								<label>
									<i class="fa-browse" title="浏览 23"></i>
									<span>23</span>
								</label>
								<label>
									<i class="fa-heart" title="喜欢 2"></i>
									<span>2</span>
								</label>
								<label>
									<i class="fa-chat" title="评论 15"></i>
									<span>15</span>
								</label>
							</div>
						</li>
						<li>
							<img src="img/main/exa-thread.jpg" alt="" width="302" height="207" blog-event="pictureClick"/>
							<div class="desc"><div><div class="desc-title">java线程</div><div class="desc-con">轻量级进程，可并发执行的单位</div></div></div>
							<div class="interacte">
								<label>
									<i class="fa-browse"></i>
									<span>5</span>
								</label>
								<label>
									<i class="fa-heart"></i>
									<span>42</span>
								</label>
								<label>
									<i class="fa-chat"></i>
									<span>12</span>
								</label>
							</div>
						</li>
						<li>
							<img src="img/main/exa-jvm.jpg" alt="" width="302" height="207" blog-event="pictureClick"/>
							<div class="desc"><div><div class="desc-title">jvm之谈</div><div class="desc-con">结构 GC原理 加载机制 内存泄露、溢</div></div></div>
							<div class="interacte">
								<label>
									<i class="fa-browse"></i>
									<span>12</span>
								</label>
								<label>
									<i class="fa-heart"></i>
									<span>2</span>
								</label>
								<label>
									<i class="fa-chat"></i>
									<span>42</span>
								</label>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- 分享简要显示 -->
		<div class="share-done simple-done">
			<div class="nav-share-done simple-nav-done">
				<label class="label-sele-css" ">生活分享</label>
			</div>
			<div class="con-share-done simple-content-done">
				<div class="cle-f con-share-page">
					<ul class="nolist">
						<li>
							<img src="img/main/share-1.jpg" alt="" width="302" height="207" blog-event="pictureClick"/>
							<div class="desc"><div><div class="desc-title"> </div><div class="desc-con">描述一些什么</div></div></div>
							<div class="interacte">
								<label>
									<i class="fa-browse"></i>
									<span>33</span>
								</label>
								<label>
									<i class="fa-heart"></i>
									<span>11</span>
								</label>
								<label>
									<i class="fa-chat"></i>
									<span>12</span>
								</label>
							</div>
						</li>
						<li>
							<img src="img/main/share-2.jpg" alt="" width="302" height="207" blog-event="pictureClick"/>
							<div class="desc"><div><div class="desc-title"> </div><div class="desc-con">BBQ</div></div></div>
							<div class="interacte">
								<label>
									<i class="fa-browse"></i>
									<span>11</span>
								</label>
								<label>
									<i class="fa-heart"></i>
									<span>32</span>
								</label>
								<label>
									<i class="fa-chat"></i>
									<span>33</span>
								</label>
							</div>
						</li>
						<li>
							<img src="img/main/share-3.jpg" alt="" width="302" height="207" blog-event="pictureClick" "/>
							<div class="desc"><div><div class="desc-title"> </div><div class="desc-con">描述一些什么</div></div></div>
							<div class="interacte">
								<label>
									<i class="fa-browse"></i>
									<span>32</span>
								</label>
								<label>
									<i class="fa-heart"></i>
									<span>12</span>
								</label>
								<label>
									<i class="fa-chat"></i>
									<span>12</span>
								</label>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="bom" style="position: relative;">
			<div class="bom_container">
				<li style="color:#586069;list-style:none;padding:10px 245px;margin: auto; width: 336px;">
					Copyright ©2018 
					<span  style="color:#a71d5d;cursor:pointer;" title="作者邮箱">chenjiwey@163.com</span> 
					 | 粤ICP备
					<span  style="color:#a71d5d;cursor:pointer;" title="备案号">19034391</span>
				</li>
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