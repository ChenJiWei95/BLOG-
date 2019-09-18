<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
  <meta name="Author" content="cjw">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <title>æºæç®¡ç</title>
  <link rel="stylesheet" href="../../layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="../../layuiadmin/style/admin.css" media="all">
  <style>
	*{margin: 0; padding: 0;}
	.manage-button, .layui-card {border-radius: 5px;}
	.manage-button {background: #555;}

  </style>
 </head>
 <body layadmin-themealias="default">
	<div class="layui-fluid">
		<div class="layui-row layui-col-space15">
			<div class="layui-col-md12"> 
				<div class="layui-card layui-circle">
					<div class="layui-card-header layuiadmin-card-header-auto">
						<input type="button" class="layui-btn manage-button" value="æ·»å "/> 
					</div>
					<div class="layui-card-body">
						<div class="layui-collapse" id="show-manage">
						</div>	  	 
					</div>
				</div>
			</div>
		</div>
	</div>  
	<script src="../../layuiadmin/layui/layui.js?t=1"></script>
	<script>
	var oparate_active;// å­é¡µé¢è°ç¨ active
	var initAjax;
	layui.config({
		base: '../../layuiadmin/' // éæèµæºæå¨è·¯å¾
	}).extend({
		index: 'lib/index' // ä¸»å¥å£æ¨¡å
		,tree_etc: 'modules/tree_etc'
	}).use(["tree_etc", 'index', 'console', 'element'], function() {
		
		var layer  = layui.layer
		,$ = layui.jquery
		,form = layui.form
		,admin = layui.admin
		,f = 'iframe'
		,a = 'layuiadmin-branch-form-submit'
		,b = 'layuiadmin-branch-form-edit'
		,c = 'branch-form-tags'
		,d = 'LAY-app-set-branch'
		,active = {
			add: function(obj){
				layer.open({
				  	type: 2
				  	,title: 'æ·»å åæ¯'
				  	,content: 'branchform.html?relateId='+obj.data.id
				  	,area: ['420px', '420px']
				  	,btn: ['ç¡®å®', 'åæ¶']
				  	,yes: function(index, layero){
						layero.find(f).contents().find("#"+a).click(); 		// å­çªå£ æé®è§¦åç¹å»
				  	},
				  	success: function(t, e) {
						//var iframe = t.find("iframe").contents().find("#"+c).click();
						// åå§å
						var iframe = t.find("iframe").contents().find("#"+c);
						iframe.find('input[name="id"]').val(randomId())
						,iframe.find('input[name="tag_check"]').removeAttr("disabled").removeClass("layui-disabled")
				  	}
				}); 
			}
			,edit: function(obj){
				console.log("=========================================");
				console.log(obj.spread);
				layer.open({
					type: 2,
					title: "ç¼è¾å½ååæ¯",
					content: "branchform.html?spread="+obj.spread.join(","),
					area: ['420px', '420px'],
					btn: ["ç¡®å®", "åæ¶"],
					yes: function(index, layero) {
						layero.find(f).contents().find("#"+b).click(); 	// å­çªå£ æé®è§¦åç¹å»
					},
					success: function(t, e) {
						//var iframe = t.find("iframe").contents().find("#"+c).click();
						// åå§å
						var iframe = t.find("iframe").contents().find("#"+c);
						iframe.find('input[name="name"]').val(obj.data.label)
						,iframe.find('input[name="id"]').val(obj.data.id)
						,iframe.find('input[name="priority"]').val(obj.data.priority)
						,iframe.find('input[name="url"]').val(obj.data.url)
						,iframe.find('textarea[name="msg"]').val(obj.data.msg)
						,iframe.find('input[name="create_time"]').val(obj.data.create_time)
						,iframe.find('input[name="update_time"]').val(obj.data.update_time)
						,iframe.find('input[name="create_time"]').attr("disabled", "")
						,iframe.find('input[name="update_time"]').attr("disabled", "")
						,iframe.find('input[name="tag_check"]').attr("disabled", "").addClass("layui-disabled")
					}
				})
			}
			,del: function(obj){  
				$.ajax({
					url: 'http://localhost:8080/MyBlog/api/test/branch/del.do'
					,type: 'post'	
					,data: {data: JSON.stringify(obj.data)}
					,dataType: "json"
					,success: function(data){
						obj.active.del();
						layer.msg("å é¤æåï¼")
					} 
					,error: function(data){
						layer.msg("æå¡å¨å¼å¸¸ï¼å é¤å¤±è´¥ï¼") 
					}
				});		
			}
		}  
		,tree = layui.tree_etc
		,data=[{// æ¨¡ææ°æ®
				label:'èµæºç®¡ç'
				,id:1
				,isTab:!0
				,children:[{
					label:'æºæç®¡ç'
					,id:23
				}
				,{
					label:'æ°æ®å­å¸'
					,isTab:!0
					,id:24
				}]
			}
			,{
				label:'æéç®¡ç'
				,id:2
				,isTab:!0
				,children:[{
					label:'è®¿å®¢ç¨æ·'
					,id:25
				}
				,{
					label:'åå°ç®¡çå'
					,id:26
				}]
		}]; 
		initAjax = function(){
			$.ajax(
				{
					url: 'http://localhost:8080/MyBlog/api/test/branch/init.do'
					,type: 'post'	
					,dataType: "json"
					,success: function(data){
						if(data.responseCode != "success") {
							layer.msg(data.responseMsg);
							return ;
						}
						console.log(data.spread);
						tree.render({//åæ¯ç»æåå»º
							elem: '#show-manage'
							,data: data.data
							,spread: data.spread
							,renderContent: !0
							,click: function(obj){
								// ç¹å»layui-tree-txt æç¤ºèç¹ä¿¡æ¯
								if(obj.type == 1){ 
									layer.msg('typeï¼' + obj.type + 'ï¼ç¶æï¼'+ obj.state + '<br>èç¹æ°æ®ï¼' + JSON.stringify(obj.data))
								}
							}
							,operate: function(obj){
								//obj.type  obj.data  obj.active obj.elem
								console.log("åè°operate");
								oparate_active = obj.active;
								console.log(obj);
								console.log(obj.active);
								'function' == typeof active[obj.type] ? active[obj.type](obj) : '';
							}
						}) 
					} 
					,error: function(data){
						layer.msg("æå¡å¨å¼å¸¸ï¼") 
					}
				}
			);
		}
		initAjax();
		function randomId(){
			var date = new Date();
			function full(num){// å¡«å
				if(num>9)
					return num;
				return "0"+num;
			} 
			var _date = {
				year : date.getFullYear(),
				month : full(date.getMonth() + 1),
				date : full(date.getDate())
			};
			return _date.year+_date.month+_date.date+(date.getTime().toString().substring(5));
		}
	}); 
	</script>
 </body>
</html>