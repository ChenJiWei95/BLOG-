;
layui.define(['util', 'admin', 'laytpl'],
function(e) { 
	//console.log("调用 noteList--------------------------");
	var util = layui.util
	,f = 'iframe'
	,t = 'C-admin-note-form'
	,a = "C-admin-note-add"
	,b = 'C-admin-note-update'
	,admin = layui.admin
	,$ = layui.$
	,laytpl = layui.laytpl
	;
    
	// 定义点击事件
	util.clickEvent({
		pictureDetail: function(e){// 图片放大
			$(".myModalImg")[0].src = e.attr("src");
			$(".myModal .desc").eq(0).text(e.attr("alt"));
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
  		,remove: function(target){// 删除note
			layer.confirm('确定删除吗？', function(data) {
		  		layer.close(layer.index);
		  		var data = {}
		  		, arr = [];
			  	data["id"] = target.parents(".note-item").eq(0).data("id");
			  	arr[0] = data;
			  	admin.cajax({
				  	method: 'remove'
				  	,contentType: 'text/plain'
				  	,data: JSON.stringify(arr) 
				  	,success: function(){
				  		target.parents(".note-item").eq(0).remove();
				  		//self.location.href="show.chtml"+ queryStr == void 0 ? '' : '?' + queryStr;
				  	}
			  	}); 	  
		  	});	
		}
		,add: function(){// 添加note
			layer.open({
				type: 2
				,title: '添加'
				,content: 'save_or_update.chtml?type=0'
				,area: ['80%', '80%']
				,btn: ['确定', '取消']
				,yes: function(index, layero){
					layero.find(f).contents().find("#"+a).click();
					setTimeout(function(){
						self.location.href="show.chtml"+ queryStr == void 0 ? '' : '?' + queryStr;
					}, 500);
				}
				,success: function(e, index) {
					var iframe = e.find(f).contents().find("#"+t);
					//iframe.find('input[name="id"]').val(admin.randomId());
				}
			});
		}
		,update: function(target){// 修改note
			var note_item = target.parents(".note-item").eq(0);
			layer.open({
	            type: 2
	            ,title: "编辑"
	            ,content: "save_or_update.chtml?type=2&id="+note_item.data("id")
	           	,area: ["80%", "80%"]
	            ,btn: ["确定", "取消"]
	            ,yes: function(index, layero) {
	                layero.find(f).contents().find("#"+b).click();
	                setTimeout(function(){
						self.location.href="show.chtml"+ queryStr == void 0 ? '' : '?' + queryStr.replace(/#/g, '%23');// 替换# %23;
					}, 500);
	            }
	            ,success: function(e, index) {
					//这是渲染完之后调用 可以用于初始化
					var iframe = e.find(f).contents().find("#"+t);
					iframe.find('input[name="id"]')[0].value = note_item.data("id")
					,iframe.find('input[name="name"]')[0].value = note_item.find(".name").text()
					,iframe.find('input[name="create_date"]')[0].value = note_item.find(".create_date").text()
					,iframe.find('input[name="tags"]')[0].value = note_item.find(".tags-value").text()
					,iframe.find('select[name="status"]')[0].value = note_item.find(".status").text()
					,iframe.find('input[name="update_date"]')[0].value = note_item.find(".update_date").text()
				}
	        })
		}
		,noteMore: function(e){// 加载更多
	  		var data = {};
	  		data['page'] = e.data('page')+1;
	  		data['limit'] = 10;
	  		
	  		var queryArr = queryStr.split("&");
	  		for(var i = 0; i < queryArr.length; i++){
	  			var queryArr_ = queryArr[i].split("=");
	  			data[queryArr_[0]] = queryArr_[1];
	  		}
	  		
	  		admin.cajax({
			  	method: 'list'
			  	,data: data
			  	,success: function(resultData){
			  		e.data('page', data.page);
			  		try{
				  		// cartlist-cnt 插入到这个容器
				  		for(var i in resultData){
				  			//var tabCode = '';// 对标签进行确认 并生成html代码 最后定义为tabCode	
				  			//for(var j in noteTabs){
				  			//	if(noteTabs[j].note_id == resultData[i].id){
				  			//		tabCode += '<label class="item-tag-block">'+noteTabs[j].name+'</label>';
				  			//	}
				  			//}
					  		
				  			//resultData[i]['tabCode'] = tabCode;
				  			//eval("obj.p" + key + "='" + value + "'");
					  		// 调用模板并插入
					  		laytpl(noteTpl.innerHTML).render(resultData[i], 
					  		function(html){
					  	    	$(".cartlist-cnt").eq(0).append(html);
					  	    });
				  		} /**/
				  		// 插入之后 进行编译 
				  		var startIndex = (data.page-1)*data.limit-1;
				  		var endIndex = startIndex + data.limit;
				  		for(var n = startIndex+1; n <= endIndex; n++){
				  			var contentText = $(".content").eq(n).text();
			  		    	// 创建 GitManage 定义渲染完后调用的
			  		    	var gitManage_ = new GitManage(contentText);
			  		    	// 获取git组件渲染元素 并添加进对应的位置
			  		    	gitManage_.getElements().forEach(
			  		    	function(item) {
			  		        	var e = $(item);
			  		        	// 得到的item元素无法输出自身,此时是重组标签,这个标签会与item相同,完全是复制
			  		        	$(".mark_code").eq(n).append(e); 
			  		        });	
			  		    	
			  		    	// 生成标签<label class="item-tag-block">${NoteTabBrige.name}</label>
			  		    	var tagVal = $(".tags-value").eq(n).text();
			  		    	var tagArr = tagVal.split(',');
			  		    	for(var i in tagArr){
			  		    		if(tagArr[i] != '')
			  		    			$(".item-tag").eq(n).append('<label class="item-tag-block">'+tagArr[i]+'</label>');
			  		    	}
				  		}
				  		
				  		// git组件渲染完后 进行html代码的渲染
				  		// ##############如果有错乱就是渲染下标的问题 日后可以根据此来维护##############
				  		var tempCount = currentCount;
				  		for(var i = tempCount; i < codeCount; i++){
				  	    	var codeStr = codeArr[i];
				  	    	if(codeStr != void 0 && codeStr.trim() != ''){
				  	    		$('.code_box').eq(i).addClass("code-box"+i);
				  	    		$("<style id='code-css"+i+"'></style>").appendTo($("head")); // 创建样式标签
				  	    		var dealCode = new DealCode(); // 创建代码处理类
				  	        	dealCode.setInit(codeStr, 
				  	        	    'code-css'+i, 
				  	        	    'code'+i,
				  	        	    '.code-box'+i,
				  	        	  	$('.code_box').eq(i).data("type") == 'code' ? true : false); // 是否需要清空换行符和空格
				  	    	}
				  	    }    
				  	    currentCount = codeCount;
				  	    
				  	    
				  	    for(var i = imgCount; i < $('.git-piture').length; i++){
				  	    	parseImgPath(i)
				  	    }
				  	    imgCount = $('.git-piture').length;
			  		} catch(err){
			  			console.log(err);
			  		}
			  	}
		  	}); 
	  	} 
	}) 
	
	// 渲染git组件
    for(var i in $(".content")){
    	var contentText = $(".content").eq(i).text();
    	// 创建 GitManage 定义渲染完后调用的
    	var gitManage = new GitManage(contentText); 
    	// 获取git组件渲染元素 并添加进对应的位置
    	gitManage.getElements().forEach(
    	function(item) {
        	var e = $(item); 
        	// 得到的item元素无法输出自身,此时是重组标签,这个标签会与item相同,完全是复制
        	$(".mark_code").eq(i).append(e); 
        });
    	
    	// 生成标签<label class="item-tag-block">${NoteTabBrige.name}</label>
    	var tagVal = $(".tags-value").eq(i).text();
    	var tagArr = tagVal.split(',');
    	for(var n in tagArr){
    		if(tagArr[n] != '')
    			$(".item-tag").eq(i).append('<label class="item-tag-block">'+tagArr[n]+'</label>');
    	}
    }
    
    // git组件渲染完后 进行html代码的渲染
    for(var i = 0; i < codeCount; i++){
    	var codeStr = codeArr[i];
    	if(codeStr != void 0 && codeStr.trim() != ''){
    		//$('.code_box').eq(i).addClass("code-box"+i);
    		$("<style id='code-css"+i+"'></style>").appendTo($("head")); // 创建样式标签
    		var dealCode = new DealCode(); // 创建代码处理类
        	dealCode.setInit(codeStr, 
        	    'code-css'+i, 
        	    'code'+i,
        	    '.code-box'+i,
        	    $('.code_box').eq(i).data("type") == 'code'); // 是否需要清空换行符和空格; true 不清空
    	}
    }
    
    // 加载图片
    var imgCount = $('.git-piture').length;
    for(var i = 0; i < imgCount; i++){
    	parseImgPath(i)
    }
    
    // 给图片转换src
    function parseImgPath(i){
    	var img = $('.git-piture').eq(i);
    	var path = img.attr('src');
    	if(path != void 0 && path.indexOf("http") == -1){
        	img.attr("src", base+'upload/'+path);
    	}
    }
    
   	currentCount = codeCount;// 定位此时代码修饰框位置 等待更多加载时使用
	
    e("noteList", {})
});