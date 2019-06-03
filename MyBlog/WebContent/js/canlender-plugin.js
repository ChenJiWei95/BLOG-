	/*
	canlender-plugin.js
	athor 威
	date
	desc
	*/
	/*S-canlender*/
	//负责向页面插入html
	//插入后的初始化高度设置
	//项点击事件
	function ManegeCanlender(canlender){
		var cdb = $(".current-day-bg").eq(0);
		var cBody = $(".calend_body").eq(0);
		var preTarget, currentTarget;
		
		//设置current-day-bg 的 top、left
		function setCurrentDayBg(){
			cdb.style.left = (currentTarget.getMarginLeft()-cBody.getMarginLeft() + 6) + "px";
			cdb.style.top = (currentTarget.getMarginTop()-cBody.getMarginTop() + 24 + 1) + "px";
			cdb.style.opacity = "1";
			currentTarget.addClass("target-month-day");
			if(preTarget != undefined){
				switch(preTarget.attr("data-type")){
					case "1" : preTarget.addClass("current-day"); break;
					case "2" : preTarget.addClass("no-current-month-day"); break;
					case "3" : preTarget.addClass("other-month-day"); break;
				}
			}
		}
		
		//点击日历项
		$(".calend").eq(0).onclick = function(e){
			//名称为td并且不是星期行
			if(e.target.tName() === "td" && e.target.attr("class") !== "week"){
				cdb.style.opacity = "0";
				preTarget = currentTarget;
				currentTarget = e.target;
				
				setCurrentDayBg();
			}
		}	
		//插入后的初始化高度设置
		function init(){
			if(canlender.getRow() > 5)
				$(".calend").eq(0).style.height = 118+19+18+21+7+"px";
			$(".calend_top p").eq(0).text(canlender.getYear()+"/"+canlender.getCurrentMonth()+"/" +canlender.getCurrentMonthDay());
		}
		this.doReay = function(){
			$(".cre_tab tbody")[0].insert(canlender.html());
			
			
			init();
			currentTarget = $("table td").eq(canlender.getCurrentMonthDay()+canlender.getStartWeek()+6);
			
			setTimeout(function(){
				setCurrentDayBg();
			}, 1000);
		}
	}
	function Canlender(){
		var today = new Date(); //实例化Date对象
		var year = today.getFullYear(); //当前年份-2018
		this.getYear = function(){
			return year;
		}
		var month = today.getMonth() + 1; //当前月份-1~
		this.getCurrentMonth = function (){
			return month;
		}
		var currentMonthDay = today.getDate(); //今天的日期 27
		this.getCurrentMonthDay = function (){
			return currentMonthDay;
		}
		
		//Math.ceil((currentMonthDayOfEnd+StartWeek)/7)向上取整获取日历行数
		var currentMonthDayOfEnd = new Date(year,month, 0).getDate(); 
		var startWeek = new Date(year,month - 1, 1).getDay(); //本月一日对应的是星期几--1234567
		this.getStartWeek = function(){
			return startWeek;
		}
		
		var row = Math.ceil((currentMonthDayOfEnd+startWeek)/7);//行数
		this.getRow = function(){
			return row;
		}

		var tempDay = 1;
		//处理第一行
		this.firstRow = function(){
			var calWeek = new CalWeek(); 
			calWeek.setWeek(startWeek);
			

			var html = "";
			var tempWeek;
			var preMonthDayOfEnd = new Date(year,month - 1, 0).getDate(); //获取上一个月有几天
			
			do
				html = this.otherMonthDayTdCss(preMonthDayOfEnd--) + html;
			while((tempWeek = calWeek.minus()) != 7);
				
			tempWeek = startWeek;
			calWeek.setWeek(startWeek);
			do
				html += tempDay !== currentMonthDay ? this.unCurrentDayTdCss(tempDay++) :  this.currentDayTdCss(tempDay++);	
			while((tempWeek = calWeek.add()) != 7);
			//console.log(this.trCss(html));
			return this.trCss(html);
		}
		//处理一般行
		this.gRow = function(){
			var html = "";
			var tempWeek = 7;
			while(tempWeek-- != 0)
				html += tempDay !== currentMonthDay ? this.unCurrentDayTdCss(tempDay++) :  this.currentDayTdCss(tempDay++);
			
			//console.log(this.trCss(html));
			return this.trCss(html);
		}
		this.lastRow = function(){
			var html = "";
			var tempWeek = 7;
			var flag = true;
			while(tempWeek-- != 0){
				if(flag){
					html += tempDay !== currentMonthDay ? this.unCurrentDayTdCss(tempDay++) :  this.currentDayTdCss(tempDay++);
				}else
					html += this.otherMonthDayTdCss(tempDay++);
				if(tempDay > currentMonthDayOfEnd) {
					flag = false;
					tempDay = 1;
				}
			}
			//console.log(this.trCss(html));
			return this.trCss(html);
		}

		this.html = function(){
			var html = this.firstRow();
			for(var i = 1; i < row-1; i++)
				html += this.gRow();
			html += this.lastRow();
			return html;
		}

		this.currentDayTdCss = function(data){
			return '<td class="current-day" data-type="1">' + data + '</td>'
		}
		this.unCurrentDayTdCss = function(data){
			return '<td class="no-current-day" data-type="2">' + data + '</td>'
		}
		this.otherMonthDayTdCss = function(data){
			return '<td class="other-month-day" data-type="3">' + data + '</td>';
		}
		this.trCss = function(data){
			return '<tr>' + data + '</tr>'
		}
	}
	//对周的计算
	function CalWeek(){
		var week;
		this.setWeek = function(i){
			if(classof(i) !== "Number")
				$$.error("i必须为数字：i class " + classof(i));
			week = i;
		}
		this.getWeek = function(){
			return week;
		}
		this.add = function(){
			if(++week > 7)
				return 1;
			return week;
		}
		this.minus = function(){
			if(--week < 1)
				return 7;
			return week;
		}
	}