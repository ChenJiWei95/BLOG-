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
  <title>机构管理</title>
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/style/admin.css" media="all">
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
						<button class="layui-btn manage-button" data-type="add">添加</button>  
					</div>
					<div class="layui-card-body">
						<div class="layui-collapse" id="show-manage">
						</div>	  	 
					</div>
					<!-- <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
					  <legend>常规使用：普通图片上传</legend>
					</fieldset>
					 
					<div class="layui-upload">
					  <button type="button" class="layui-btn" id="test1">上传图片</button>
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
	var oparate_active, initAjax;// 子页面调用 active 
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
			add: function(o){
				layer.open({
				  	type: 2
				  	,title: '添加'
				  	,content: 'save_or_update.chtml'
				  	,area: ['420px', '420px']
				  	,btn: ['确定', '取消']
				  	,yes: function(index, layero){
						layero.find(f).contents().find("#"+a).click(); 		// 子窗口 按钮触发点击
				  	},
				  	success: function(t, e) {
						//var iframe = t.find("iframe").contents().find("#"+c).click();
						// 初始化
						var iframe = t.find("iframe").contents().find("#"+c);
						iframe.find('input[name="id"]').val(index.util.randomId())
						,iframe.find('input[name="url"]').val("####")
						,iframe.find('input[name="tag_check"]').attr("disabled", "").addClass("layui-disabled")
						,iframe.find('input[name="url"]').attr("disabled", "").addClass("layui-disabled")
				  	}
				}); 
			}
		}
		,active = {
			add: function(obj){
				layer.open({
				  	type: 2
				  	,title: '添加'
				  	,content: 'save_or_update.chtml?relateId='+obj.data.id
				  	,area: ['420px', '420px']
				  	,btn: ['确定', '取消']
				  	,yes: function(index, layero){
						layero.find(f).contents().find("#"+a).click(); 		// 子窗口 按钮触发点击
				  	},
				  	success: function(t, e) {
						//var iframe = t.find("iframe").contents().find("#"+c).click();
						// 初始化
						var iframe = t.find("iframe").contents().find("#"+c);
						iframe.find('input[name="id"]').val(randomId())
						,iframe.find('input[name="tag_check"]').removeAttr("disabled").removeClass("layui-disabled")
				  	}
				}); 
			}
			,edit: function(obj){ 
				layer.open({
					type: 2,
					title: "编辑",
					content: "save_or_update.chtml?spread="+obj.spread.join(","),
					area: ['420px', '420px'],
					btn: ["确定", "取消"],
					yes: function(index, layero) {
						layero.find(f).contents().find("#"+b).click(); 	// 子窗口 按钮触发点击
					},
					success: function(t, e) {
						//var iframe = t.find("iframe").contents().find("#"+c).click();
						// 初始化
						var iframe = t.find("iframe").contents().find("#"+c);
						iframe.find('input[name="name"]').val(obj.data.label)
						,iframe.find('input[name="id"]').val(obj.data.id)
						,iframe.find('input[name="priority"]').val(obj.data.priority)
						,iframe.find('input[name="url"]').val(obj.data.url)
						,iframe.find('select[name="icon"]').val(obj.data.icon)
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
					url: 'remove.do'  
					,type: 'post'	
					,data: {id: obj.data.id}
					,dataType: "json"
					,success: function(data){
						obj.active.del();
						layer.msg("删除成功！")
					} 
					,error: function(data){
						layer.msg("服务器异常，删除失败！") 
					}
				});	
			}
			,show: function(data){
				layer.open({
			        type: 2
			        ,title: "查看"
			        ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
			        ,id: 'layerDemo'+'auto' //防止重复弹出
			        ,content: 'save_or_update.chtml'
			        ,area: ['420px', '420px']
			        ,btn: '关闭'
			        ,btnAlign: 'c' //按钮居中
			        ,shade: 0.3 //不显示遮罩
			        ,yes: function(){
			          layer.closeAll();
			        }
					,success: function(t, e) {
						//var iframe = t.find("iframe").contents().find("#"+c).click();
						// 初始化
						var iframe = t.find("iframe").contents().find("#"+c); 
						iframe.find('input[name="name"]').val(data.label)
						,iframe.find('input[name="id"]').val(data.id)
						,iframe.find('input[name="priority"]').val(data.priority)
						,iframe.find('select[name="icon"]').val(obj.data.icon)
						,iframe.find('input[name="url"]').val(data.url)
						,iframe.find('textarea[name="msg"]').val(data.msg)
						,iframe.find('input[name="create_time"]').val(data.create_time)
						,iframe.find('input[name="update_time"]').val(data.update_time)
						,iframe.find('input[name="tag_check"]').addClass("layui-hide")
						,iframe.find(".layui-hide").eq(0).removeClass("layui-hide").addClass("layui-form-item");
					}
				});
			}
		}  
		,tree = layui.tree_etc; 
		initAjax = function(){
			$.ajax(
				{ 
					url: 'init.do'
					,type: 'post'	
					,dataType: "json"
					,success: function(data){
						if(data.responseCode != "success") {
							layer.msg(data.responseMsg);
							return ;
						} 
						var tempSpread;
						if(data.spread != void 0 && "" != data.spread){
							tempSpread = data.spread.split('|');
						}
						console.log("data.spread"+data.spread);
						tree.render({//分支结构创建
							elem: '#show-manage'
							,data: data.data
							,spread: tempSpread
							,renderContent: !0
							,click: function(obj){
								// 点击layui-tree-txt 提示节点信息
								if(obj.type == 1){ 
									active['show'](obj.data);
								}
							}
							,operate: function(obj){
								//obj.type  obj.data  obj.active obj.elem
								console.log("回调operate");
								oparate_active = obj.active;
								console.log(obj);
								console.log(obj.active);
								'function' == typeof active[obj.type] ? active[obj.type](obj) : '';
							}
						}) 
					} 
					,error: function(data){
						layer.msg("服务器异常！") 
					}
				}
			);
		}
		initAjax();
		
		$('.layui-btn.manage-button').on('click', function(){
			var type = $(this).data('type');
			that_active[type] ? that_active[type].call(this) : '';
		});
		
		/* $('.layuiadmin-btn-list').on('click', function(){ 
			layer.msg('测试');
		}); */
		
		/* var upload = layui.upload; 
		//普通图片上传
		  var uploadInst = upload.render({
		    elem: '#test1'
		    ,url: 'editMovieInfo.do'
		    ,before: function(obj){
		      //预读本地文件示例，不支持ie8
		      obj.preview(function(index, file, result){
		        $('#demo1').attr('src', result); //图片链接（base64）
		      });
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
		  }); */
	}); 
	</script>
 </body>
</html>