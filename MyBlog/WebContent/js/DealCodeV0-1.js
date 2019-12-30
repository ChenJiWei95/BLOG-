	//===================================================================
	//=============================处理代码=================================
	//===================================================================
	//异常点属性中的下划线影响
	//enctype="multipart/form-data"
	//代码不规范时
	function CommonDealCode(){// ```code 调用此
		var str
		,code	// 代码 
		,code_style	// 样式
		,endIndex
		,index
		,suport
		,codeBoxClassName
		,styleElement
		,codeElement
		,row
		;
		this.setInit = function (dealStr_, styleElementName, codeElementName, boxClassName){// isCode ture 通用代码
			str = dealStr_ ,// 清除回车 空格 制表符
			styleElement = document.getElementById(styleElementName),
			codeElement = document.getElementById(codeElementName),
			codeBoxClassName = boxClassName;
			this.start();
		}
		this.start = function (){
			//setInit(); // 初始化
			
			index = 0
			,suport = new DealCodeSuport()
			,row = 0
			,endIndex = str.indexOf("\n") === -1 ? str.length : str.indexOf("\n")
			,code_style = ''
			,code = ''
			;
			
			do{
				row++; // 行数递增
				var line = str.substring(index, endIndex).replace(/\'/g, '\\\'');// 解决css上引号冲突
				code += suport.getCode(row, space(line), suport.spanCode_text());
				code_style += suport.styleCode(row, 1, line.trim());
				index = endIndex+1;
			}
			while((endIndex = str.indexOf("\n", index)) != -1)
				 
			styleElement.innerHTML = code_style;
			codeElement.innerHTML = code;
		}
		function space(line){
			var space = '';
			for(var i = 0; i < line.length; i++){
				var c = line.charAt(i);
				if(c == " ") space += '&nbsp;';
				else if (c == "\t") space += '&nbsp;&nbsp;&nbsp;&nbsp;';
				else break;// 只找其他字符之前
			}
			return space;
		}
		function DealCodeSuport(){
			// 内容都会设置到css中的伪元素中 请见styleCode方法		例如:before{content:'</div>'} 
			this.spanCode_ = function (){
				return '<span></span>';
			}
			this.spanCode_red = function(){
				return '<span class="red"></span>';
			}
			this.spanCode_purple = function(){
				return '<span class="purple"></span>';
			}
			this.spanCode_text = function(){
				return '<span class="text"></span>';
			}
			this.spanCode_content = function(){
				return '<span class="content"></span>';
			}
			// row 行数 ； space 空格 ； element_cod 封装html代码 
			this.getCode = function (row,space,element_cod){
				return '<tr><td class="Serial">'+row+'</td>'+
							'<td class="row_code">'+
								space+
								element_cod+
							'</td></tr>';
			}
			this.styleCode = function (count,min_count,content){
				// 默认类名称是code_box
				return (codeBoxClassName == void 0 ? ".code_box" : codeBoxClassName) +" tr:nth-child("+count+") .row_code span:nth-child("+min_count+"):before{content:'"+content+"';}"
			}
		}
	}

	function DealCode(){
		var str		// 需显示的代码原文本
		,code		// 代码 
		,code_style	// 样式
		,min_count	// span 计数
		,count		// 空格符 	计数
		,row		// 行	计数
		,isEndLabel	// 是否是结尾
		,specialType_Label	// 标签标识 close 闭标签；end 结束标签； begin 开始标签	
		,code_span		// span代码
		,lock_count		// count锁 
		,selectIndex	// 当前索引
		,styleElement	// style id
		,codeElement	// tbody 标签 id	
		,isAttr 	// 是否为属性
		,util 		// 工具
		,suport		// DealCodeSuport
		,strategy	// DealCodeStrategy
		,codeBoxClassName
		,singlecharstrategy; // SingleCharStrategy
		function setInit(){
			code = '',
			code_style = '',
			min_count = undefined,
			code_span = '',
			count = 0,
			row = 0,
			isEndLabel = false,
			lock_count = false,
			isAttr = false,
			selectIndex = undefined,
			specialType_Label = undefined,
			util = new DealCodeUtil(), 
			suport = new DealCodeSuport(),
			strategy = new DealCodeStrategy(),
			singlecharstrategy = new SingleCharStrategy();
		}
		this.setInit = function (dealStr_, styleElementName, codeElementName, boxClassName, isCode){// isCode ture 通用代码
			if(isCode) {
				new CommonDealCode().setInit(dealStr_, styleElementName, codeElementName, boxClassName);
			}else {
				str = dealStr_.replace(/[\t]/g,"").replace(/[\r\n]/g,""),// 清除回车 空格 制表符
				styleElement = document.getElementById(styleElementName),
				codeElement = document.getElementById(codeElementName),
				codeBoxClassName = boxClassName;
				this.start();
			}
		}
		this.start = function (){
			setInit(); // 初始化
			util.dealLabelFristChar(0); // 启动编译
			styleElement.innerHTML = code_style;
			codeElement.innerHTML = code;
		}
		function DealCodeSuport(){
			// 内容都会设置到css中的伪元素中 请见styleCode方法		例如:before{content:'</div>'} 
			this.spanCode_ = function (){
				return '<span></span>';
			}
			this.spanCode_red = function(){
				return '<span class="red"></span>';
			}
			this.spanCode_purple = function(){
				return '<span class="purple"></span>';
			}
			this.spanCode_text = function(){
				return '<span class="text"></span>';
			}
			this.spanCode_content = function(){
				return '<span class="content"></span>';
			}
			// row 行数 ； space 空格 ； element_cod 封装html代码 
			this.getCode = function (row,space,element_cod){
				return '<tr><td class="Serial">'+row+'</td>'+
							'<td class="row_code">'+
								space+
								element_cod+
							'</td></tr>';
			}
			this.styleCode = function (count,min_count,content){
				// 默认类名称是code_box
				return (codeBoxClassName == void 0 ? ".code_box" : codeBoxClassName) +" tr:nth-child("+count+") .row_code span:nth-child("+min_count+"):before{content:'"+content+"';}"
			}
		}
		
		function DealCodeUtil(){ // 工具类
			this.isEnd = function (index){ // 标签是否无其他内容 并且结束  例如：<div></div>
				index = str.indexOf('>', index) + 1;
				var i_temp = index
				,char_temp = '';
				if(str.charAt(index) == '<'){
					i_temp++;  
					for(i_temp;;i_temp++){
						if(str.charAt(i_temp) == '/')
							return true;
						return false;
					}
				}
				return false;
			}
			this.attributeIntegrity = function (index){
				var char_temp = '',
				index_itemp = index + 1,
				max = str.length;
				for(index_itemp; index_itemp < max; index_itemp++){
					char_temp = str.charAt(index_itemp);
					if(char_temp == '='){
						var char_temp_temp;
						var index_itemp_itemp = index_itemp - 1;
						for(index_itemp_itemp;; index_itemp_itemp-- ){
							char_temp_temp = str.charAt(index_itemp_itemp);
							if(char_temp_temp == '"')
								return index_itemp_itemp + 1;
						}			
					}else if(char_temp == '>'){
						if(specialType_Label == "close")
							return index_itemp - 1;
						return index_itemp - 1;
					}
					continue;
				}
			}
			this.getSpance = function (count){
				var space = '';
				for(var i=0; i<count; i++){
					space += '&nbsp;&nbsp;';
				}
				
				//console.log("getSpance:"+count);
				return space;
			}
			this.dealLabel = function(index){ // 处理开始标签 和 闭标签
				var lock_count = 0;
				var max = str.length;
				var lock_equal = false; // 属性值获取　锁，防止属性有干扰符
				for(index; index < max; index++){
					char_ = str.charAt(index);
					if(char_ == '<'){
						selectIndex = index;
						min_count--;
					}else if(char_ == ' '){
						lock_equal = false; // 属性值获取结束 解锁
						index = singlecharstrategy.strategySpaceChar(selectIndex, index); // 处理属性值
						selectIndex = index + 1;
					}else if(!lock_equal && char_ == '='){// 有等于号之后就不能再出发等于号 知道属性值获取结束
						lock_equal = true; // 有等于号之后就不能再出发等于号 直到属性值获取结束
						singlecharstrategy.strategyEqualChar(selectIndex, index);
						selectIndex = index + 1;
					}else if(char_ == '>'){
						selectIndex = singlecharstrategy.strategyEndChar(selectIndex, index);
						return;
					}
				}
			}
			this.dealLabelFristChar = function (index){
				code_span = '';
				min_count = 1; // span计数器
				
				var i_temp = index;
				var char_temp;
				if(str.charAt(index) == '<'){
					i_temp += 1; 
					for(i_temp;;i_temp++){
						char_temp = str.charAt(i_temp);
						if(char_temp == '!'){// 注释代码
							strategy.dealCommentLabel(i_temp);
							break;
						}else if((char_temp <= 'z' && char_temp >= 'a') || (char_temp <= 'Z' && char_temp >= 'A')){// 大写字母的判断 解决了换行问题
							i_i_temp = str.indexOf('>', i_temp)-1;
							var char_char_temp;
							for(i_i_temp;;i_i_temp--){
								char_char_temp = str.charAt(i_i_temp);
								if((char_char_temp <= 'z' && char_char_temp >= 'a') || char_char_temp == "\'" || char_char_temp == '\"'){
									specialType_Label = "begin";
									strategy.dealBeginLabel(index);
									break;
								}
								else if(char_char_temp == ' '){
									continue;
								}else{
									specialType_Label = "close";
									strategy.dealCloseLabel(index);
									break;
								}
							}
							break;
						}else if(char_temp == ' '){
							continue;
						}
						else{
							specialType_Label = "end";
							strategy.dealEndLabel(index);
							break;
						}
					}
				}else{
					strategy.dealTextLabel(index);
				}
			}
		}

	
		function DealCodeStrategy(){
			this.dealEndLabel = function (beginIndex){// 处理结束标签
				row++; // 行数递增 // 行数递增
				var endIndex = str.indexOf(">", beginIndex) + 1;
				count--;
				//console.log("dealEndLabel "+"specialType_Label:"+specialType_Label+" "+str.substring(beginIndex, endIndex) + " count:"+count);
				code += suport.getCode(row, util.getSpance(lock_count ? count-1 : count), suport.spanCode_());  
				code_style += suport.styleCode(row, min_count, str.substring(beginIndex, endIndex));
				if(endIndex <= str.length-1)
					 util.dealLabelFristChar(endIndex);
			}
			this.dealBeginLabel = function (index){
				isEndLabel = util.isEnd(index);	// 是否为闭标签
				row++; // 行数递增 
				//console.log('dealBeginLabel 行数递增 isEndLabel:'+isEndLabel+' lock_count:'+lock_count+' count:'+count)
				if(!lock_count){
					count++;
				}
				if(isEndLabel)
					lock_count = true;	
				else
					lock_count = false;
				util.dealLabel(index);
			}
			this.dealCloseLabel = function (index){// 闭标签
				row++; // 行数递增 
				util.dealLabel(index);
			}
			this.dealTextLabel = function(index){	// 一般文本的处理
				row++; // 行数递增
				endIndex = str.indexOf("<", index);
				if(endIndex == -1)
					endIndex = str.length;
				code += suport.getCode(row, util.getSpance(count), suport.spanCode_text());
				code_style += suport.styleCode(row, min_count, str.substring(index, endIndex));
				if(endIndex <= str.length - 1)
					util.dealLabelFristChar(endIndex);
			}
			this.dealCommentLabel = function(index){// 注释代码
				row++; // 行数递增
				endIndex = str.indexOf("-->", index) + 3;
				code += suport.getCode(row, util.getSpance(lock_count ? count-1 : count), suport.spanCode_content());
				code_style += suport.styleCode(row, min_count, str.substring(index-1 , endIndex));
				if(endIndex<=str.length-1)
					util.dealLabelFristChar(endIndex);
			}
		}
		function SingleCharStrategy(){// 属性值的收集 和 标签前缀收集 例如:"attr"或<img
			this.strategySpaceChar = function (beginIndex, index){
				min_count++;
				if(isAttr){ // ture则为属性值 区分处理
					isAttr = false; 
					index = util.attributeIntegrity(index);
					var temp = str.substring(beginIndex, index+1).replace(/\'/g, '\\\'');// 解决了属性值中有冲突符干扰问题
					if(temp.lastIndexOf('/') == (temp.length - 1)) // 解决属性值拖泥带水带上了自闭标签的斜杠
						temp = temp.substring(0, temp.length-1);
					code_style += suport.styleCode(row, min_count, temp + ' ');// 解决了属性值中有冲突符干扰问题
					code_span += suport.spanCode_purple();
					
				}else{ // false则是标签前缀  例如：<img 或者属性名 onclick
					//console.log('strategySpaceChar isAttr:'+isAttr + " "+str.substring(beginIndex, index));
					code_style += suport.styleCode(row, min_count, str.substring(beginIndex, index) + ' '); 
					code_span += suport.spanCode_();
				}
				return index;
			}
			this.strategyEqualChar = function(beginIndex, index){// 属性名 和 分隔符等号的收集  例如：id=
				min_count++;
				//console.log("strategyEqualChar1 " + str.substring(beginIndex, index))
				code_style += suport.styleCode(row, min_count, str.substring(beginIndex, index) + ' '); // 属性名 
				code_span += suport.spanCode_red();
				min_count++;
				//console.log("strategyEqualChar2 " + str.substring(index, index+1))
				code_style += suport.styleCode(row, min_count, str.substring(index, index+1) + ' '); // 分隔符等号
				code_span += suport.spanCode_();
				isAttr = true;	
			}
			this.strategyEndChar = function (beginIndex, index){// 结尾处理 可能还有属性值没处理完   
				min_count++;
				
				if(isAttr){
					//console.log('strategyEndChar isAttr64:'+isAttr + " "+str.substring(beginIndex, index).replace(/\'/g, '\\\''));
					var temp = str.substring(beginIndex, index).replace(/\'/g, '\\\'');// "this.src=\'images/send.png\'"/
					if(temp.lastIndexOf('/') == (temp.length - 1)){ // 解决属性值拖泥带水带上了自闭标签的斜杠
						code_style += suport.styleCode(row, min_count, temp.substring(0, temp.length-1));// 解决了属性值中有冲突符干扰问题
						code_span += suport.spanCode_purple();
						min_count++;
						//console.log('strategyEndChar isAttr65:'+isAttr + " "+temp.substring(temp.length-1));   // 此处收集的是‘/’
						code_style += suport.styleCode(row, min_count, temp.substring(temp.length-1)); // 此处收集的是‘/’
						code_span += suport.spanCode_();
					}else {
						code_style += suport.styleCode(row, min_count, temp);// 解决了属性值中有冲突符干扰问题
						code_span += suport.spanCode_purple();
					}
					min_count++;
					//console.log('strategyEndChar isAttr66:'+isAttr + " "+str.substring(index, index+1));
					code_style += suport.styleCode(row, min_count, str.substring(index, index+1));   // 此处收集的是‘>’
					code_span += suport.spanCode_();
					isAttr = false;   
				}else{
					index_temp_ = beginIndex;
					if(specialType_Label == "close"){
						specialType_Label == "";
						index_temp_ = beginIndex - 1;
					}
					//console.log('strategyEndChar isAttr67:'+isAttr + " "+str.substring(index_temp_, index+1));
					code_style += suport.styleCode(row, min_count, str.substring(index_temp_, index+1)); // 此处收集的是‘/>’
					code_span += suport.spanCode_();
				}
				beginIndex = index + 1;
				if(isEndLabel){  
					isEndLabel = false;
					min_count++;
					endIndex = str.indexOf('>', beginIndex ) ;
					//console.log('strategyEndChar isAttr68:'+isAttr + " "+str.substring(beginIndex, endIndex+1));
					code_style += suport.styleCode(row, min_count, str.substring(beginIndex, endIndex+1)); // 此处收集的是‘</div>’
					beginIndex = endIndex + 1;
					code_span += suport.spanCode_();
				}
				code += suport.getCode(row, util.getSpance(specialType_Label == 'close' ? count : count-1), code_span);
				code_span = ''; 
				if(beginIndex<=str.length-1)// 寻找下一个开始标签
					util.dealLabelFristChar(beginIndex);
				return beginIndex;
			}
		} 
	}