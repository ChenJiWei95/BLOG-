;
layui.define(['util'],
function(e) { 
	var util = layui.util
	,$ = layui.$
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
	}) 
	
    e("myModelImg", {})
});