<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/jstl/custom.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title><my:text name="blog.index.managesys" /></title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/style/admin.css" media="all"> 
</head>
<body class="layui-layout-body">
  
  <div id="LAY_app">
    <div class="layui-layout layui-layout-admin">
      <div class="layui-header">
        <!-- 头部区域 -->
        <ul class="layui-nav layui-layout-left">
          <li class="layui-nav-item layadmin-flexible" lay-unselect>
            <a href="javascript:;" admin-event="flexible" title="侧边伸缩">
              <i class="layui-icon layui-icon-shrink-right" id="LAY_app_flexible"></i>
            </a>
          </li>
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="http://www.chenjiwey.cn:8080" target="_blank" title="前台">
              <i class="layui-icon layui-icon-website"></i>
            </a>
          </li>
          <li class="layui-nav-item" lay-unselect>
            <a href="javascript:;" admin-event="refresh" title="刷新">
              <i class="layui-icon layui-icon-refresh-3"></i>
            </a>
          </li>
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <input type="text" placeholder="<my:text name="blog.index.search"/>..." autocomplete="off" class="layui-input layui-input-search" admin-event="serach" lay-action="template/search.html?keywords="> 
          </li>
        </ul>
        <ul class="layui-nav layui-layout-right" lay-filter="layadmin-layout-right">
          
          <li class="layui-nav-item" lay-unselect>
            <a lay-href="<%=basePath%>admin/message/listview.chtml" admin-event="message" lay-text="消息中心">
              <i class="layui-icon layui-icon-notice"></i>  
              
              <!-- 如果有新消息，则显示小圆点 -->
              <span class="layui-badge-dot"></span>
            </a>
          </li>
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" admin-event="theme">
              <i class="layui-icon layui-icon-theme"></i>
            </a>
          </li>
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" admin-event="note">
              <i class="layui-icon layui-icon-note"></i>
            </a>
          </li>
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" admin-event="fullscreen">
              <i class="layui-icon layui-icon-screen-full"></i>
            </a>
          </li>
          <li class="layui-nav-item" lay-unselect>
            <a href="javascript:;">
              <cite>${name}</cite> 
            </a>
            <dl class="layui-nav-child">
              <dd><a lay-href="<%=basePath%>admin/administrators/info.chtml">
              	<my:text name="blog.index.baseInfor" />
              </a></dd>
              <dd><a lay-href="<%=basePath%>admin/administrators/password.chtml">
              	<my:text name="blog.index.changePass" />
              </a></dd>
              <c:forEach begin="0" items="${locals}" step="1" var="Local" varStatus="varsta">
                <dd style="text-align: center;">
					<a href="<%=basePath%>admin/main/listview.chtml?request_locale=${Local.lang}&token=${token}">
              		${Local.text}
              		</a>
              	</dd>
              </c:forEach>
              <hr>
              <dd admin-event="logout" style="text-align: center;"><a href="javascript:;">
              	<my:text name="blog.index.signout" />
              </a></dd>
            </dl>
          </li>
          
          <li class="layui-nav-item layui-hide-xs" lay-unselect>
            <a href="javascript:;" admin-event="about"><i class="layui-icon layui-icon-more-vertical"></i></a>
          </li>
          <li class="layui-nav-item layui-show-xs-inline-block layui-hide-sm" lay-unselect>
            <a href="javascript:;" admin-event="more"><i class="layui-icon layui-icon-more-vertical"></i></a>
          </li>
        </ul>
      </div>
      
      <!-- 侧边菜单 -->
      <div class="layui-side layui-side-menu">
        <div class="layui-side-scroll" id="nav-tree-cnt">
        </div>
      </div>

      <!-- 页面标签 -->
      <div class="layadmin-pagetabs" id="LAY_app_tabs">
        <div class="layui-icon layadmin-tabs-control layui-icon-prev" admin-event="leftPage"></div>
        <div class="layui-icon layadmin-tabs-control layui-icon-next" admin-event="rightPage"></div>
        <div class="layui-icon layadmin-tabs-control layui-icon-down">
          <ul class="layui-nav layadmin-tabs-select" lay-filter="layadmin-pagetabs-nav">
            <li class="layui-nav-item" lay-unselect>
              <a href="javascript:;"></a>
              <dl class="layui-nav-child layui-anim-fadein">
                <dd admin-event="closeThisTabs"><a href="javascript:;">
                	<my:text name="blog.index.closeCurrent" />
                </a></dd>
                <dd admin-event="closeOtherTabs"><a href="javascript:;">
                	<my:text name="blog.index.closeOther" />
                </a></dd>
                <dd admin-event="closeAllTabs"><a href="javascript:;">
                	<my:text name="blog.index.closeAll" />
                </a></dd>
              </dl>
            </li>
          </ul>
        </div>
        <div class="layui-tab" lay-unauto lay-allowClose="true" lay-filter="layadmin-layout-tabs">
          <ul class="layui-tab-title" id="LAY_app_tabsheader">
            <li lay-id="home/console.chtml" lay-attr="home/console.chtml" class="layui-this"><i class="layui-icon layui-icon-home"></i></li>
          </ul>
        </div>
      </div>
      
      
      <!-- 主体内容 -->
      <div class="layui-body" id="LAY_app_body">
        <div class="layadmin-tabsbody-item layui-show">
          <iframe src="<%=basePath%>adin/main/aly_control.chtml?token=${token}" frameborder="0" class="layadmin-iframe"></iframe>
        </div>
      </div>
      
      <!-- 辅助元素，一般用于移动设备下遮罩 -->
      <div class="layadmin-body-shade" admin-event="shade"></div>
    </div>
  </div>
  <script src="<%=basePath%>layuiadmin/layui/layui.js"></script>
  <script>
  var token = "";
  function refresh() {
	console.log("重新登录 刷新");
  	window.location.reload();
  }
  function test(){
	  alert("0");
  }
  var index;
  layui.config({
	base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
	index: 'lib/index' //主入口模块
	,navTree: 'lib/navTree'  /* */ 
	/*  */
	/* ,partcle: 'lib/partcle' */ 
  }).use(['navTree', 'layer'/* , 'cutil' */], function () {   
	// ********************************************
	// ***********index需在navTree渲染完后调用**********
	// ***********否则navTree将会点击无反应**************
	// ********************************************
	var navTree = layui.navTree
	,layer = layui.layer
	,$ = layui.$;
	function getParameter(key){
		var query = window.location.search.substring(1);
		var vars = query.split("&");
		for (var i=0;i<vars.length;i++) {
			var pair = vars[i].split("=");
			if(pair[0] == key){return pair[1];}
		}
		return '';
	}
	console.log(getParameter('token'));
	initAjax = function(){
		//console.log(layer);
		$.ajax(
			{ 
				url: 'init.do?token='+(token=getParameter('token'))
				,type: 'post'
				,dataType: "json"
				,success: function(data){
					if(data.code != "0") {
						layer.msg(data.responseMsg);
						return ;
					}   
					navTree.render({
						elem: '#nav-tree-cnt'
						,base: '<%=basePath%>'
						,data: {
							href: data.data.href
							,desc: data.data.desc
							,data: data.data.data
						}
					});
					
					//
					index = layui.index;
					layui.use('index',  function(){
						console.log("id: "+layui.setter.token)
					}) //必须在后面加载
				} 
				,error: function(data){
					layer.msg("服务器异常！")
				}
			}
		);	
		
		<%-- layui.cutil.cajax({
			method:'init',
			success:function(data){
				console.log(data);
				navTree.render({
					elem: '#nav-tree-cnt'
					,base: '<%=basePath%>'
					,data: {
						href: data.href
						,desc: data.desc
						,data: data.data 
					}
				});
				index = layui.index
				layui.use('index') //必须在后面加载
			}
		}); --%>
	}
	initAjax();
	
	
  });
  </script>

</body>
</html>
