/** layui-v2.5.6 MIT License By https://www.layui.com */
 ;layui.define("jquery",function(e){"use strict";var t=layui.$,i={
     /*
		描述：
		重定向 -- 重定向的方式有很多种可以用self.location.href="show.chtml"这种方式重定向
		此处方法使用的是表单重定向
	参数介绍：
		action
		method = data.method || 'post';
		enctype = data.enctype || 'application/x-www-form-urlencoded';
		query 预设条件 
	例子：
		redirectByForm({
	        		action: 'show.chtml'
	        		,query: queryStr
	        	})
	*/ 
	redirectByForm: function (data){
		//data.action
		data.method = data.method || 'post';
		data.enctype = data.enctype || 'application/x-www-form-urlencoded';
		//data.query
		var form = layui.$('<form>', {
			action:	data.action
			,method: data.method 
			,enctype: data.enctype
			,style:"display: none"
		});
		var strArr = data.query.split('&');
		for(var index in strArr){
			var strArr_ = strArr[index].split('=');
			layui.$('<input>', {
				type:'input' ,value:strArr_[1] ,name:strArr_[0]
			}).appendTo(form);
		}
		form.appendTo('body');
		form.submit();
	}		
	/*
	描述：
		表单赋值
	参数介绍：
		el
		
		list
			type	表单类型 可选值【input(默认) , select , textarea】
			另一个属性	例如表单项‘<input name = 'pass' value = '123456' >’ 这个属性的写法为：pass = ‘123456’
	注意：
		list属性中除了type和一个非固定属性外不能有其他属性否则异常
	例子：
		layui.util.formVal ({
			el: layui.$.find(i).contents().find("#"+f)
			,list:[
				{id: data[0].id}
				,{desc: data[0].desc, type: 'textarea'}
			] 
		});
	*/ 
	,formVal:function (data){ 
	    	layui.each(data.list, function(index, item){ 
	    		var type = item.type || 'input';
	    		var temp = i.copy(item);
	    		delete temp.type;
	    		var keys = Object.keys(temp);
	    		if(keys.length != 1){ 
	    			console.err("内容异常"+JSON.stringify(item));
	    			return;
	    		} 
	    		data.el.find(type+'[name="'+keys[0]+'"]').val(item[keys[0]])
	    	}); 
	}    
	// 浅拷贝  和引用对象脱离引用关联 【对象的拷贝】
	,copy:function(obj){
		var newobj = {};
		for ( var attr in obj) {
			newobj[attr] = obj[attr];
		}
		return newobj;
	}   
	/*
		描述：
			ajax封装--适用于表单提交，提交过程有刷新提示、结果提示
		固定规格
	 		方式			:post
	 		返回数据		:json
	 	参数介绍：
	 		url			:请求地址
	 		data		:数据
	 		loadStyle	:加载样式 见layui官网文档
	 		success		:function	请求成功，返回状态成功
	 		error		:function	请求成功，返回状态失败
	 		serverError	:function	请求失败，服务器异常
	*/
	// 默认 application/x-www-form-urlencoded; charset=UTF-8
	,formAjax: function(object){
		object.isHints == void 0 && (object.isHints = !0);	// 	ture则提示 加载提示和结果提示
		object.loadStyle = object.loadStyle || 2;			//	加载提示的样式 默认为2
		object.contentType = object.contentType || 'application/x-www-form-urlencoded';
		object.dataType = object.dataType || 'json';					// 返回 数据默认json
		object.type = object.type || 'post';							// 默认请求方式post
		
		//object.method != 'update' || object.method != 'add' || (parent.layer.msg("method参数有误："+object.method), parent.layer.close(index))
		var c = object.isHints && layer.load(object.loadStyle);
		//执行 Ajax 后重载
		layui.$.ajax({
			url: object.url
			,type: object.type	
			,contentType: object.contentType
			,data: object.data
			,dataType: object.dataType
			,success: function(data){
				data.code == '0' && ('function' == typeof object.success && object.success(data.data, data.msg), object.isHints && (layer.close(c), parent.layer.msg(data.msg, object.layCallback))),
				data.code == '2' && ('function' == typeof object.error && object.error(data.data, data.msg), object.isHints && (layer.close(c), layer.msg(data.msg)));
			} 
			,error: function(data){
				object.isHints && layer.close(c),
				layer.msg("服务器异常，操作失败！"),
				'function' == typeof object.serverError && object.serverError(data, data.msg);
			}
		});
	} 
	// pitureE: $('.pit-piture') ;  count: 0
	// 将不完整的路径替换成有效路径 ，资源位置默认在上传位置
	// 注意，使用时要定义一个全局的变量base
	,parseImgPath: function(pitureE, count){
		console.log(pitureE);
	 	var imgCount = pitureE.length;
	 	for(var i = count; i < imgCount; i++){
	 		var img = pitureE.eq(i);
	     	console.log(img);
	     	console.log(img.attr('src'));
	     	var path = img.attr('src');
	     	if(path != void 0 && path.indexOf("http") == -1){
	         	img.attr("src", base+'upload/'+path);
	     	}
	     }
	 	return imgCount;
	 }
	 ,parseFloat: function(str){
	 	var index = 0;
	 	if((index = str.indexOf(".")) != -1){
	 		return str.substring(0, index+3);
	 	}
	 	return str+".00";
	 }
	 ,iframeAjax: function (object) {// 使用完后 需要关掉自身
	 	try {
	     	object.dataType = object.dataType || 'json';
	     	object.type = object.type || 'post';
	     	object.contentType = object.contentType || 'application/x-www-form-urlencoded';
				if(parent.layer == void 0) throw 'parent页面中的layer对象为undefied';
	     	var index = parent.layer.getFrameIndex(window.name); 
				//object.method != 'update' || object.method != 'add' || (parent.layer.msg("method参数有误："+object.method), parent.layer.close(index))
				var c = parent.layer.load(2);
				//执行 Ajax 后重载
				layui.$.ajax({
					url: object.url
					,type: object.type	
					,contentType: object.contentType
					,data: object.data
					,dataType: object.dataType
					,success: function(data){
						data.code == '0' && ('function' == typeof object.success && object.success(data.data, data.msg), parent.layer.close(c), parent.layer.msg(data.msg), parent.layer.close(index), object.id && parent.table.reload(object.id)),
						data.code == '2' && ('function' == typeof object.error && object.error(data.data, data.msg), parent.layer.close(c), parent.layer.msg(data.msg), parent.layer.close(index));
					} 
					,error: function(data){
						parent.layer.close(c),
						parent.layer.msg("服务器异常，操作失败！"+data.msg),
						'function' == typeof object.serverError && object.serverError(data, data.msg);
						parent.layer.close(index)
					}
				});	
	 	} catch(err) {
	  	 	alert("错误信息："+err);
	 	}
	}
	//contentType: 'application/json;charset=UTF-8'
	,CAjax: function(object){
		object.contentType = object.contentType || 'application/json';	// 默认application/json
		i.formAjax(object);
		
		//object.method != 'update' || object.method != 'add' || (parent.layer.msg("method参数有误："+object.method), parent.layer.close(index))
		/*var c = object.isHints && layer.load(object.loadStyle);
		//执行 Ajax 后重载
		layui.$.ajax({
			url: object.url
			,type: object.type	
			,contentType: object.contentType
			,data: object.data
			,dataType: object.dataType
			,success: function(data){
				data.code == '0' && ('function' == typeof object.success && object.success(data.data, data.msg), object.isHints && (layer.close(c), parent.layer.msg(data.msg, object.layCallback))),
				data.code == '2' && ('function' == typeof object.error && object.error(data.data, data.msg), object.isHints && (layer.close(c), layer.msg(data.msg)));
			} 
			,error: function(data){
				object.isHints && layer.close(c),
				layer.msg("服务器异常，操作失败！"),
				'function' == typeof object.serverError && object.serverError(data, data.msg);
			}
		});*/
		
	} 
	,addSubCtrlbtn: function(callback){
		// 加减控件
		// 回调方法
		// 返回type【add、sub】 value e【$(this)】 
		this.shopClick({
			subtraction: function(e){
				if(isNaN(e.next().val())){
					layer.msg("不是数字，已置零");
					e.next().val(0);
					return;
				}
				if(e.next().val() > 0){
					e.next().val(Number(e.next().val())-1);
				} 
				'function' == typeof callback && callback({type: 'sub', value: e.next().val(), e: e})
			}
			,addition: function(e){
				if(isNaN(e.prev().val())){
					layer.msg("不是数字，已置零");
					e.prev().val(0);
					return;
				}
				e.prev().val(Number(e.prev().val())+1);
				'function' == typeof callback && callback({type: 'add', value: e.prev().val(), e: e})
			}
		});
	}
	,clickEvent: function(active){
		// 类似addSubCtrlbtn方法使用的方式调用
		// 需要标明shop-clcik
		layui.$("body").on('click', '*[click-event]', function(){
			var o = layui.$(this)
			,layClick = o.attr('click-event');
			return 'function' == typeof active[layClick] && active[layClick].call(this, o);
		});
	}
	,layHref: function(){
		layui.$("body").on('click', "*[lay-href]",
	    function() {//打开一个标签页
	    	var t = layui.$(this);
	    	location.href = t.attr("lay-href");
	    })
	}
    ,fixbar:function(e){var i,n,a="layui-fixbar",o="layui-fixbar-top",r=t(document),l=t("body");e=t.extend({showHeight:200},e),e.bar1=e.bar1===!0?"&#xe606;":e.bar1,e.bar2=e.bar2===!0?"&#xe607;":e.bar2,e.bgcolor=e.bgcolor?"background-color:"+e.bgcolor:"";var c=[e.bar1,e.bar2,"&#xe604;"],u=t(['<ul class="'+a+'">',e.bar1?'<li class="layui-icon" lay-type="bar1" style="'+e.bgcolor+'">'+c[0]+"</li>":"",e.bar2?'<li class="layui-icon" lay-type="bar2" style="'+e.bgcolor+'">'+c[1]+"</li>":"",'<li class="layui-icon '+o+'" lay-type="top" style="'+e.bgcolor+'">'+c[2]+"</li>","</ul>"].join("")),g=u.find("."+o),s=function(){var t=r.scrollTop();t>=e.showHeight?i||(g.show(),i=1):i&&(g.hide(),i=0)};t("."+a)[0]||("object"==typeof e.css&&u.css(e.css),l.append(u),s(),u.find("li").on("click",function(){var i=t(this),n=i.attr("lay-type");"top"===n&&t("html,body").animate({scrollTop:0},200),e.click&&e.click.call(this,n)}),r.on("scroll",function(){clearTimeout(n),n=setTimeout(function(){s()},100)}))},countdown:function(e,t,i){var n=this,a="function"==typeof t,o=new Date(e).getTime(),r=new Date(!t||a?(new Date).getTime():t).getTime(),l=o-r,c=[Math.floor(l/864e5),Math.floor(l/36e5)%24,Math.floor(l/6e4)%60,Math.floor(l/1e3)%60];a&&(i=t);var u=setTimeout(function(){n.countdown(e,r+1e3,i)},1e3);return i&&i(l>0?c:[0,0,0,0],t,u),l<=0&&clearTimeout(u),u},timeAgo:function(e,t){var i=this,n=[[],[]],a=(new Date).getTime()-new Date(e).getTime();return a>26784e5?(a=new Date(e),n[0][0]=i.digit(a.getFullYear(),4),n[0][1]=i.digit(a.getMonth()+1),n[0][2]=i.digit(a.getDate()),t||(n[1][0]=i.digit(a.getHours()),n[1][1]=i.digit(a.getMinutes()),n[1][2]=i.digit(a.getSeconds())),n[0].join("-")+" "+n[1].join(":")):a>=864e5?(a/1e3/60/60/24|0)+"天前":a>=36e5?(a/1e3/60/60|0)+"小时前":a>=18e4?(a/1e3/60|0)+"分钟前":a<0?"未来":"刚刚"},digit:function(e,t){var i="";e=String(e),t=t||2;for(var n=e.length;n<t;n++)i+="0";return e<Math.pow(10,t)?i+(0|e):e},toDateString:function(e,t){var i=this,n=new Date(e||new Date),a=[i.digit(n.getFullYear(),4),i.digit(n.getMonth()+1),i.digit(n.getDate())],o=[i.digit(n.getHours()),i.digit(n.getMinutes()),i.digit(n.getSeconds())];return t=t||"yyyy-MM-dd HH:mm:ss",t.replace(/yyyy/g,a[0]).replace(/MM/g,a[1]).replace(/dd/g,a[2]).replace(/HH/g,o[0]).replace(/mm/g,o[1]).replace(/ss/g,o[2])},escape:function(e){return String(e||"").replace(/&(?!#?[a-zA-Z0-9]+;)/g,"&amp;").replace(/</g,"&lt;").replace(/>/g,"&gt;").replace(/'/g,"&#39;").replace(/"/g,"&quot;")},event:function(e,n,a){var o=t("body");return a=a||"click",n=i.event[e]=t.extend(!0,i.event[e],n)||{},i.event.UTIL_EVENT_CALLBACK=i.event.UTIL_EVENT_CALLBACK||{},o.off(a,"*["+e+"]",i.event.UTIL_EVENT_CALLBACK[e]),i.event.UTIL_EVENT_CALLBACK[e]=function(){var i=t(this),a=i.attr(e);"function"==typeof n[a]&&n[a].call(this,i)},o.on(a,"*["+e+"]",i.event.UTIL_EVENT_CALLBACK[e]),n}};!function(e,t,i){"$:nomunge";function n(){a=t[l](function(){o.each(function(){var t=e(this),i=t.width(),n=t.height(),a=e.data(this,u);(i!==a.w||n!==a.h)&&t.trigger(c,[a.w=i,a.h=n])}),n()},r[g])}var a,o=e([]),r=e.resize=e.extend(e.resize,{}),l="setTimeout",c="resize",u=c+"-special-event",g="delay",s="throttleWindow";r[g]=250,r[s]=!0,e.event.special[c]={setup:function(){if(!r[s]&&this[l])return!1;var t=e(this);o=o.add(t),e.data(this,u,{w:t.width(),h:t.height()}),1===o.length&&n()},teardown:function(){if(!r[s]&&this[l])return!1;var t=e(this);o=o.not(t),t.removeData(u),o.length||clearTimeout(a)},add:function(t){function n(t,n,o){var r=e(this),l=e.data(this,u)||{};l.w=n!==i?n:r.width(),l.h=o!==i?o:r.height(),a.apply(this,arguments)}if(!r[s]&&this[l])return!1;var a;return e.isFunction(t)?(a=t,n):(a=t.handler,void(t.handler=n))}}}(t,window),e("util",i)});
