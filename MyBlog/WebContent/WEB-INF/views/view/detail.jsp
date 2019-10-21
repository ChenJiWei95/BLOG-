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
  <title>随笔分类</title>
  <link type="text/css" rel="stylesheet" href="<%=basePath%>css/MyCss.css" />
  <link type="text/css" rel="stylesheet" href="<%=basePath%>css/basi.css" />
  <link type="text/css" rel="stylesheet" href="<%=basePath%>css/git-plugin.css" />
  <script src="<%=basePath%>js/MyjsV0-8.js"></script>
  <style>
	
	.api-content {margin: auto;}
	.api-content > .api-content-right, .api-content > .api-content-left {border-radius: 4px; background: #fff; float:left; height: 100%; padding: 4px;}
	

	/* 联级树组件 */
	.show-content-li, .show-content-li .item-title-text {cursor: pointer;}

	.show-content-li .item-title {height: 25px; line-height: 25px;}

	.show-content-li .item-title-ico,
	.show-content-li .item-title-text {float: left;}
	.show-content-li .item-title-ico {margin-top: 5px; width:16px; height:16px;}
	.show-content-li .item-title-text {margin-left: 2px; color: #888;}
	.show-content-li .item-title-text:hover {color: #555;}

	.show-content-li .item-title-ico > i { transition: all 0.2s ease-out; transform: rotate(-90deg); width:16px; height:16px;}
	.show-content-li:active .show-content-li .item-title-ico > i {}
	.show-content-li .item-title-ico > .open {background: url(<%=basePath%>img/open.png); cursor: pointer;}
	.show-content-li .item-title-ico > .open:hover {background: url(<%=basePath%>img/open_hover.png);}
	
	.show-content-li .item-child {padding-left: 12px; height: 0; transition: all 0.2s ease-out; overflow: hidden;}

	/* api 左边界面 导航 */
	.api-content > .api-content-left { width: 166px;}
	/* api 右边界面 内容 */
	.api-content > .api-content-right {margin-left: 4px;}
	.api-content > .api-content-right > div {background:#fff; padding: 20px; border-radius: 3px; min-width: 916px;}
	.api-content > .api-content-right > div > div { border-bottom: 1px solid #d1d5da; margin-top: 10px; padding: 10px; position: relative; overflow: hidden; font-size: 14px;}
	.api-content > .api-content-right > div > div:nth-child(1) {margin-top: 0px !important;}
	/* 随笔文章头部 */
	.page-top div:nth-child(1) {margin-bottom: 10px; }
	.page-top .page-top-infor > span:first-child {margin-left: 0px !important;}
	.page-top .page-top-infor > span {margin-left: 10px; color: #555; font-size: 12px;}
	.page-top .page-top-infor > span a:first-child {margin-left: 0px !important;}
	.page-top div:last-child {float: right; font-size: 12px; color: #555; position: absolute; right: 18px; top: 18px;}

	/*S-canlender*/
	.calend{background: #fff;  position: relative;  z-index: 5;  margin: 0px auto; font-size: 12px; font-family: -webkit-pictograph; margin-top: 12px;}
	.calend > .calend_top{width: 236px;  height: 25px;  background: #555;  text-align: center;  line-height: 29px;  position: relative;}

	.calend > .calend_top p {color: #fff;  font-size: 14px; line-height: 29px;}
	.calend > .calend_top .calend-top-ico {width: 16px;  height: 16px; background: url(<%=basePath%>img/calend.png); left: 66px; top: 5px; position: absolute;}
	.calend > .calend_body{padding: 4px;  width: 218px; background: #fff;  position: relative; min-height: 125px; }
	.calend > .calend_body table {width: 212px; text-align:center; position: absolute; z-index: 2;}
	.calend > .calend_body table tbody tr td{color: #555; width: 28px;  height: 12px;  line-height: 18px; cursor: pointer; padding: 2px 10px; transform:1s; -moz-transition:1s; -webkit-transition:1s;}
	.calend > .calend_body table tbody tr .current-day {color: red;}
	.calend > .calend_body table tbody tr .no-current-day {}
	.calend > .calend_body table tbody tr .other-month-day {color: #ccc;}
	.calend > .calend_body table tbody tr .target-month-day {color: #fff;}

	.calend > div > .current-day-bg {width: 20px; height: 20px; background: #555; border-radius: 50%; z-index: 1; position: absolute; top: 0; left: 0; transform:1s; -moz-transition:1s; -webkit-transition:1s; opacity: 0.5;}
	/* 切换页按钮 */
	.calend > .lr-bt {}
	.calend > .lr-bt div i {width: 16px;  height: 16px;  cursor: pointer; }
	.calend > .lr-bt div {position: absolute;  top: 4px;   z-index:2; }
	.calend > .lr-bt .l-bt {left: 4px; }
	.calend > .lr-bt .l-bt i {background: url(<%=basePath%>img/r1.png); }
	/*.lr-bt .l-bt i:hover {background: url(); }*/
	.calend > .lr-bt .r-bt {right: 4px; }
	.calend > .lr-bt .r-bt i {background: url(<%=basePath%>img/l1.png); }
	/*.lr-bt .r-bt i:hover {background: url(); }*/
	/*E-canlender*/

	/* 切换页按钮 */
	.body-content-show > .lr-bt {}
    
	.body-content-show > .lr-bt div i {width: 64px; height: 64px; cursor: pointer;}
	.body-content-show > .lr-bt div {position: absolute; top: 400px;  z-index:2;}
	.body-content-show > .lr-bt .l-bt {left: 20px;}
	.body-content-show > .lr-bt .l-bt i {background: url(<%=basePath%>img/pre.png);}
	.body-content-show > .lr-bt .l-bt i:hover {background: url(<%=basePath%>img/pre_hover.png);}
	.body-content-show > .lr-bt .r-bt {right: 20px;}
	.body-content-show > .lr-bt .r-bt i {background: url(<%=basePath%>img/next.png);}
	.body-content-show > .lr-bt .r-bt i:hover {background: url(<%=basePath%>img/next_hover.png);}
  </style>
 </head>
 <body>
	<!-- 隐藏栏 -->
	<div class="more-oprate">
		<ul class="nolist">
			<li title="查找">
				<i class="serch_ico"></i>
				<div class="li-cnt">
					<label class="input-lable">
						<input type="text" class="serch-input"/> 
					</label>
					<label>
						<i class="serch-click-bt-ico"></i>
					</label>
				</div>
			</li>
			<li title="TOP" class="li-top"><label class="show-label">TOP</label>
				<div class="li-cnt">
					<label class="msg-label">点击返回顶部</label>
				</div>
			</li>
		</ul>
	</div>
	<!-- S-左栏 -->
	<div class="body-nav">
		<div class="body-nav-in">
			<!-- 个人信息 -->
			<div class="nav-ifnor">
				<label>
					<img src="<%=basePath%>img/pho.jpg" width="80" height="80" alt="chenjiwei"/>
				</label>
				<label>
					指尖的美
				</label>
				<label class="clearFloat">
					浏览数量：1
				</label>
				<label class="clearFloat">
					<i onclick="link.open('https://github.com/ChenJiWei95');" class="github" title="https://github.com/ChenJiWei95"></i>
					<i class="email" title="chenJiWey@163.com"></i>
				</label>
			</div>
			<!--S-canlender-->
			<div class="calend clearFloat">
				<div class="calend_top">
					<i class="calend-top-ico"></i>
					<p class="p_inner"></p>
				</div>
				<div class="calend_body">
					<table class='cre_tab' cellspacing="0" cellpadding="0">
						<tbody>
							<tr>
								<td class="week">日</td>
								<td class="week">一</td>
								<td class="week">二</td>
								<td class="week">三</td>
								<td class="week">四</td>
								<td class="week">五</td>
								<td class="week">六</td>
							</tr>
						</tbody>
					</table>
				</div>

				<div class="lr-bt">
					<div class="l-bt"><i></i></div>
					<div class="r-bt"><i></i></div>
				</div>
				<div>
					<div class="current-day-bg"></div>
				</div>
			</div>
			<!--E-canlender-->
			<!-- 导航栏 -->
			<div class="nav-list">
				<ul class="nolist">
					<li>首页</li>
					<li>Java</li>
					<li>HTML</li>
					<li>JavaScript</li>
					<li>其他</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- E-左栏 -->
	<!-- S body -->
	<div class="body">
		<div class="body-con">
			<!-- 导航 -->
			<div class="body-nav-no">
				
			</div>
			<div class="body-content">
				<!-- 动态内容展示 -->
				<div class="body-content-show" style="position: relative;">
					<div class="body-content-show-in" style="position: relative;">
						<ul class="nolist">
							<li>
								<div>
									<!-- 标题 -->
									<label class="item-tilte clearFloat">
										<label>JavaScript API</label>
										<label>2018年9月18日 23:16</label>
									</label>
									<!-- 内容 -->
									<label class="item-body">
										<label><img src="<%=basePath%>img/pho.jpg" width="50px" height="50px" alt="chenjiwei"/></label>
										<label><br><br>修改了$$.ajax函数</label>
									</label>
									<!-- 相关 标签 -->
									<label class="item-tag">
										<label>javaScript</label>
									</label>

									<label class="item-comment">
										<label class="item-comment-count">0</label>
										<label>评论：</label>
									</label>
								</div>
							</li>
						</ul>
					</div>
					<!-- S-选页组件 -->
					<div class="select-page">
						<a href="javascript:;">1</a>
						<a href="javascript:;">2</a>
					</div>
					<div class="lr-bt">
						<div class="l-bt"><i></i></div>
						<div class="r-bt"><i></i></div>
					</div>
					<!-- E-选页组件 -->
				</div>
				
				<!--S -  foot-->
				<div class="foot" style="position: relative;">
					<div class="bom_container">
						<li style="color:#586069;list-style:none;padding:10px 245px;">Copyright ©2018 <span  style="color:#a71d5d;cursor:pointer;" title="作者介绍">chen</span> Powered By <span  style="color:#a71d5d;cursor:pointer;" title="程序目录">My Blog</span> Version 1.0.0</li>
					</div>
				</div>
				<!--E - foot-->
			</div>
		</div>
	</div>
	<!-- E body -->
	<script src="<%=basePath%>js/canlender-plugin.js"></script>
	<script src="<%=basePath%>js/git-plugin V0.js"></script>
	<script>
	/* S 日历 */
	var manageCanlender = new ManegeCanlender(new Canlender());
	manageCanlender.doReay();
	/* E 日历 */

	

	//每次窗口的变化
	var bodyContentShow = $(".body-content-show").eq(0);
	var bodyNavNo = $(".body-nav-no").eq(0);
	var bodyNav = $(".body-nav").eq(0);
	var moreOprate = $(".more-oprate").eq(0);
	initAndUpdate();
	window.onresize = function(e){
		initAndUpdate();
	}
	function initAndUpdate(){
		document.body.style.minHeight = window.innerHeight+"px";
		//对导航条的初始化和随着窗口的变动进行更新
		var marginLeft = bodyNavNo.getMarginLeft();
		var width = bodyNavNo.clientWidth;
		
		bodyNav.style.width = width + "px";
		bodyNav.style.left = marginLeft + "px";
		bodyNav.style.display = "block";

		//更多操作
		//$(".more-oprate").eq(0).style.top = 100 + "px";
		//alert(bodyContentShow.getBoundingClientRect().right);
		$(".more-oprate").eq(0).style.left = 
			$(".body-content-show").eq(0).getBoundingClientRect().right + 4 
			+ "px";
		
		//$(".more-oprate").eq(0).style.display = "block";
		$(".body-nav-in").eq(0).style.height = (window.innerHeight-16)+"px";
		//$(".api-content-left").eq(0).style.minHeight = window.innerHeight - 、、、、//$(".foot").eq(0).getHeight() - 18+"px"; 
		$(".body-content-show-in").eq(0).style.minHeight = window.innerHeight - $(".foot").eq(0).getHeight() - 18+"px";
	}
	
	//alert($(".foot").eq(0).getHeight());
	//只进行一次初始化
	//alert(bodyContentShow.getMarginRight() - bodyContentShow.getWidth());
	//$(".select-page").eq(0).style.right = (bodyContentShow.getMarginRight() - bodyContentShow.getWidth() - $(".select-page").eq(0).getWidth()) + "px";
	//$(".select-page").eq(0).style.top =  (bodyContentShow.getHeight() - 38) + "px"
	
	liEvent_2.extends(LiEventSuper);
	function liEvent_2(data){
		LiEventSuper.call(this, data);

		this.overEventCss = function (i){
			data.liElements[i].css("background:#555; color:#fff;", false); 
		}
		//当前移出样式设置
		this.outEventCss = function (i){
			data.liElements[i].css("background:#fff; color:#555;", false); 
		}
		//e {i:preIndex}
		this.preClickFun = function (i){
			data.liElements[i].css("background:#fff; color:#555;", false);
		}
		//设置当前的元素 
		//e {i:i}
		this.nowClickFun = function (i){
			data.liElements[i].css("background:#555; color:#fff;", false);
			ajaxC({
				url:"",
				data:{code:data.codes[i]},
				success:function(msg){
				
				},	
				error:function(msg){
				
				},
			});
		}
	}
	//左栏选择内容
	new liEvent_2({
		liElements:$(".nav-list li"),
		initIndex: 1,
		codes:["0000", "0001", "0002", "0003", "0004", "0005"],
	}).doReady();
	ajax({
		url:"https://www.easy-mock.com/mock/5bc1dcd352815755b2b7b2cf/nba/myblog",
		type:"get",
		success:function(e){
			var json = $$.json.toObject(e)
			console.log(e);
			console.log(json.toString());
		},
	});
	</script>
 </body>
</html>