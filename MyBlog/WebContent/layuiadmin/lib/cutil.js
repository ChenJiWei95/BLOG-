;
layui.define(
function(e) { 
	var a = layui.$
	;
	i = { 
			token:function(){
				return i.getParameter('token');
			}
			,getParameter: function(key){
				var query = window.location.search.substring(1);
				var vars = query.split("&");
				for (var i=0;i<vars.length;i++) {
					var pair = vars[i].split("=");
					if(pair[0] == key){return pair[1];}
				}
				return '';
			}
			,
			// 随机id
			randomId: function(){
				var date = new Date();
				function full(num){// 填充
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
			},
			// layui表格请求， 可以写得更少，主要作用是统一配置token
			tableReq: function(data){
	//			data.table
	//			data.cols
				data.url = data.url || 'list.do',
				//data.url = data.url+(data.url.indexOf('?')!=-1?'&'+tokenStr:'?'+tokenStr)
				data.limit = data.limit || 20,
				data.page = data.page || !0,
				data.method = data.method || 'post',
				data.height = data.height || 'full-200',
				data.text = data.text || "对不起，加载出现异常！";
				data.table.render({//加载
			        elem: data.elem,
			        url: data.url,
			        limit: data.limit,
			        page: data.page,
			        where: data.data,
			        method: data.method,
			        height: data.height, 
			        cols: data.cols,
			        text: data.text
			    })
			},
			// action index pageSize 标签属性 
			// method data success 其他传入参数
			// 一个简单的分页控件
			simpleAsyncPageBtn: function(object){
				if(object == void 0) throw new Error("参数不能为："+object);
				this.clickEvent({
		    		asyncCallback: function(e){ 
		    			i.cajax({
		    				method: (object.method = object.method || e.data("action")),
		    				data: a.extend(object.data=object.data||{},{page: e.data("index"), limit: e.data("pageSize"), token: token,}),
		    				success: function(data){
		    					object.success && 'function' == typeof object.success && object.success(data);
		    				}
		    			});/*  */
		    		}
		    	});
			},
			simplePageBtn: function(){
				this.clickEvent({
		    		callback: function(e){ 
						i.redirectByForm({
							action: e.data("action")
				        	,query: 'page='+e.data("index")+'&limit='+e.data("pageSize")+'&token='+token
						})
		    		}
		    	});
			},
			
		/*
		描述：
			重定向 -- 重定向的方式有很多种可以用self.location.href="show.chtml"这种方式重定向
			此处方法使用的是表单重定向
			就是完成跳转，又采用post请求的方式 传递参数
			运用的地方暂时没记录
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
			
			var strArr = (data.query+'&token='+token).split('&');
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
		注意：type相同时可以添加前缀$符号表示 如 ‘{$type: data[0].type, type: 'select'}’
			2、list属性中除了type和一个非固定属性外不能有其他属性否则异常
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
		    			alert("内容异常"+JSON.stringify(item)+"，长度必须大于1");
		    			return;
		    		} 
		    		data.el.find(type+'[name="'+keys[0].replace("$", "")+'"]').val(item[keys[0]])
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
			object.data["token"] = token;
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
	        	object.data["token"] = token;
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
		// admin也有一个，主要是为了在首页的冲突而设计
		// 不适合有iframe窗口的调用
		,cajax: function (object) {
			object.contentType = object.contentType || 'application/x-www-form-urlencoded';
			var index = parent.layer.getFrameIndex(window.name);
			//object.method != 'update' || object.method != 'add' || (parent.layer.msg("method参数有误："+object.method), parent.layer.close(index))
			var c = parent.layer.load(2);
			//执行 Ajax 后重载
			a.ajax({
				url: object.method + '.do'
				,type: 'post'	
				,contentType: object.contentType//'application/json'
				,data: a.extend(object.data=object.data||{}, {token: token})
				,dataType: "json"
				,success: function(data){
					data.code == '0' && ('function' == typeof object.success && object.success(data.data, data.msg), parent.layer.close(c), parent.layer.msg(data.msg), parent.layer.close(index), object.id && parent.table.reload(object.id)),
					data.code == '2' && ('function' == typeof object.error && object.error(data.data, data.msg), parent.layer.close(c), parent.layer.msg(data.msg), parent.layer.close(index));
				} 
				,error: function(data){
					parent.layer.close(c),
					parent.layer.msg("服务器异常，操作失败！"+data.msg),
					'function' == typeof object.serverError && object.serverError(data, data.msg);
					parent.layer.close(index)/**/
				}
			});	
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
	};
    e("cutil", i)
});
/*
描述：
 	封装在String类型里的一个获取自定义时间串的方法
参数介绍：
	在特定串中可以夹杂其他字符，例如：{1}-{2} {3}:{4} 最后得到结果如案例
 	特定串所代表含义：{0} 年，{1} 月，{2} 日，{3} 时，{4} 分，{5} 秒
例子：
	var time = '2020-5-30 7:13:28';
	time.formatTime() // 2020年05月30日 07时13分28秒
	time.formatTime('{1}-{2} {3}:{4}')	// 05-30 07:13
*/
(proto => {
	function formatTime(template = '{0}年{1}月{2}日 {3}时{4}分{5}秒'){ 
		var arr = this.match(/\d+/g);
		return template.replace(/\{(\d+)}/g, (_, n) => {  
			var item = arr[n]; 
			item.length < 2 ? item  = '0' + item : null;
			return item;
		})
	}
	proto.formatTime = formatTime;
})(String.prototype);
