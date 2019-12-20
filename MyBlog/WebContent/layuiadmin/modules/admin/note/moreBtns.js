;
layui.define(['laydate', "table", 'util'],
function(e) { 
	//console.log("调用 moreBtns--------------------------");
	var util = layui.util
	,laydate = layui.laydate
	,$ = layui.$
	;
    
	// 定义点击事件
	util.clickEvent({
		top: function(e){
	  		// 回到顶部
	  		timer=setInterval(function(){
                var top=document.documentElement.scrollTop || document.body.scrollTop;
                var speed=Math.floor(-top/6);
                document.documentElement.scrollTop=document.body.scrollTop=top+speed;
                returnTop=true;
                if(top==0){
                    clearInterval(timer);
                }
            },100) 
	  	}
	  	,search: function(e){// 打开模糊查询窗口
	  		//console.log('模糊查询');
	  		for(var i = 0; i < e.siblings().length; i++){
	  			e.siblings().eq(i).find(".li-cnt").removeClass('active-div');
	  			e.siblings().eq(i).find("i").removeClass('active-li');
	  		}
	  		e.find("i").eq(0).addClass('active-li');
	  		e.find(".li-cnt").addClass('active-div');
	  		return !1;
	  	}
	  	,moreOprateClose: function(e){// 关闭
	  		e.parents("li").eq(0).find(".li-cnt").removeClass('active-div');
	  		e.parents("li").eq(0).find("i").removeClass('active-li');
	  		return !1;
	  	}
	  	,selectByTab: function(){// 查询
	  		//console.log('selectByTab');
	  		$("#selectByTab").submit();
	  		return !1;
	  	}
	  	,selectByName: function(){// 查询
	  		//console.log('selectByName');
	  		$("#selectByName").submit();
	  		return !0;
	  	}
	})
    // 控制回到顶部按钮的显示与隐藏
 	setInterval(function(){
 		var top=document.documentElement.scrollTop || document.body.scrollTop;
 		if(top > 0) {
 			$('.li-top').eq(0).css('display', 'block');
 		} else {
 			$('.li-top').eq(0).css('display', 'none');
 		}
 	}, 200);
    
	// 获取时间  type：datetime
	laydate.render({
        elem: '#date'
        ,type: 'datetime'
    });
    laydate.render({
        elem: '#date1'
        ,type: 'datetime'
    });
	
    e("moreBtns", {})
});