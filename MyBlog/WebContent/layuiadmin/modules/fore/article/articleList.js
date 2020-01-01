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
		more: function(e){// 加载更多
	  		var data = {};
	  		data['page'] = e.data('page')+1;
	  		data['limit'] = 6;
	  		
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
					  		// 调用模板并插入
					  		laytpl(noteTpl.innerHTML).render(resultData[i], 
					  		function(html){
					  	    	$(".cartlist-cnt").eq(0).append(html);
					  	    });
				  		} /**/
				  		// 插入之后 进行编译 
				  		var startIndex = (data.page-1)*data.limit-1;
				  		var endIndex = startIndex + data.limit;
				  		console.log(startIndex + " " + endIndex);
				  		for(var n = startIndex+1; n <= endIndex; n++){
				  			console.log(n);
				  			var contentText = $(".content").eq(n).text();
				  			//console.log('创建 GitManage 定义渲染完后调用的');
			  		    	// 创建 GitManage 定义渲染完后调用的
			  		    	var gitManage_ = new GitManage(contentText);
			  		    	// 获取git组件渲染元素 并添加进对应的位置
			  		    	gitManage_.getElements().forEach(
			  		    	function(item) {
			  		        	var e = $(item);
			  		        	console.log(e);
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
				  	    		//console.log('codeStr:'+codeStr);
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
    		//console.log('codeStr:'+codeStr);
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
    	console.log(img);
    	console.log(img.attr('src'));
    	var path = img.attr('src');
    	if(path != void 0 && path.indexOf("http") == -1){
        	img.attr("src", base+'upload/'+path);
    	}
    }
    
   	currentCount = codeCount;// 定位此时代码修饰框位置 等待更多加载时使用
	
    e("articleList", {})
});