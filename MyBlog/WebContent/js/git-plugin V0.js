	/* ## S-GIT ## */
	/*
	草稿内容
	ul 
		li 
			ul
				li
	1行解析结果 stack [list-1]
	2行解析结果 stack [list-1, list-1-1]
	3行解析结果 stack [list-1]
	4行解析结果 stack [list-1, list-2-1]
	* 1 [childs]
		* 1-1 [childs]
	* 2
		* 2-1

	1行解析结果 stack [list-1]
	2行解析结果 stack [list-1, list-1-1]
	3行解析结果 stack [list-1, list-1-1, list-1-1-1]
	4行解析结果 stack [list-1, list-1-1]
	栈保存的对象 {listObject:, h:, }
	function dataStack(list, height){
		//
		if(list === undefined) list = null;
		if(height === undefined) height = null;
		this.listObject = list;
		this.h = height;
	}
	list保存的对象 
	function ItemData(t, c){
		if(t === undefined) t = null;
		if(c === undefined) c = null;
		this.text = t;
		this.childs = c;
	}
	最后返回一个list集合--包含整个圆点列表的数据
	stack = new ArrayStack();
	function test3(context){
		list = new ArrayList();
		stack.push(new dataStack(list, 0));
		next = context.next();
		t = test1(next);
		preItem;
		while(t[0]){
			//是圆点列表项
			//判断级别获取list并将项载入list
			//栈顶深度h与此时项深度相等则进行添加操作
			
			if(stack.peek().h === t[1]){
				preItem = new ItemData(t[2]);
				stack.peek().add(preItem);
			}
			//栈顶深度h 大于 此时深度，则需回退
			else if(stack.peek().h > t[1]){
				//回退-- 执行pop()方法删除栈顶并达到合适深度
				while(stack.peek().h !== t[0])
					stack.pop();
				//创建数据项 ItemData对象
				preItem = new ItemData(t[2]);
				stack.peek().add(preItem);
			}
			//栈顶深度h 小于 此时深度，则需创建list ；list值注入到上一个项的childs中并压入栈
			else {
				//
				list = new ArrayList();
				preItem.childs = list;
				stack.push(new dataStack(preItem.childs, t[1]));
				preItem = new ItemData(t[2]);
				stack.peek().add(preItem);
			}
			next = context.next();
			t = test1(next);
		}
	}
	* 1 
		* 1-1 
			* 1-1-1
		* 1-2

	ul 
		li
		li
	* jkk
	* jkk

	TitleNode
	OlNode {级别}
		LiNode
			[UlNode/OlNode]
	UlNode {级别}
		LiNode
			[UlNode/OlNode]
	GNode
		TextNode
		UrlNode
		ImgNode
	SepNode
	
	preNode

	必须有空格
		1、‘#’ # j
		2、‘*’ * j
	*/	
	/*
	test5
	function GitContext(str){
		arr = str.split("\n");
		this.currentLine = arr.shift();
		//将this.currentLine赋值为下一行
		this.next = function(){
			this.currentLine = arr.shift();
		}
		//将currentLine从新载入数组首位
		this.pre = function(){
			arr.unshift(this.currentLine);
		}
	}

	模拟分析
	# 11
	jkjdkf
	* 11
		* 11
	jjjj

	第一行 context.currentLine 责任链处理 结果：标题 最后操作：下移context.next()
	第二行 context.currentLine 责任链处理 结果：普通文本 最后操作：下移context.next()
	第三行 context.currentLine 责任链处理 结果：圆点列表
		特殊处理 
			每次处理的最后操作时context.next() 
			结束依据context.currentLine 不是圆点列表项 最后操作：不做下移等同于回退
	第五行 context.currentLine 责任链处理 结果：普通文本 最后操作：下移context.next()
	结束 context.currentLine === undefined
	*/

	//测试
	/*
	var test3Arr = arrStr.split("\n");
	console.log(test3Arr);
	resultList = test3(test3Arr);
	test4(resultList);
	resultList.forEach(item => {
		//
		console.log(item.html());
	});
	*/
	var $$ = {
		//$$对象中的暂缓数据
		tempData:undefined,
		//获取$$对象中的暂缓数据
		getTempData:function(){
			var temp = $$.tempData;
			$$.tempData = null;
			return temp;
		},
		cre:function(tag){
			return createElement(tag);
		},
		//异常
		nullError:function(obj, objName) {
			if(obj == undefined)
				throw new Error("参数" + objName + "不能为空 == " + obj);
		},
		//异常
		error:function(msg){
			throw new Error(msg);
		},
		//表单异步交互
		formAjax:function(data){
			$$.tempData = data;
			var str = "abcdefghijklmnopqrstuvwxyz";
			var result = "";
			for(var i = 0; i <8; i++){
				result += str.charAt(Math.floor(Math.random()*25));
			}
			var scri = $("body")[0].creEle("script");
			scri.innerHTML = 'var formAjaxData = $$.getTempData();' +
			'formAjaxData.callbackObj = "' + result + '";' +
			'var ' + result + ' = new FormAjax(formAjaxData);';
		},
		//表单验证
		formVerifi:function(data){
			new FormVerifi(data);
		},
		//工具库
		util:{
			isJSON:function(jsonStr){
				return (jsonStr.charAt(0) == '{' || jsonStr.charAt(0) == '[') ? true : false;
			},
			isNumber:function(field){
				return !(isNaN(field));
			},
			isBool:function(field){ //包括字符串的判断
				return field == "true" || field == "false" || $$.util.isBoolean(field);
			},
			isBoolean:function(bool){//只判断布尔对象不包括字符串
				if(!(typeof bool == "boolean"))
					return false;
				return String(bool) == "true" || String(bool) == "false"; 
			},
			isChinese:function(field){		//全中文
				return unicodeUtil.isChinese(field);
			},
			isString:function(field) {		//不是数字，不是bool对象
				return !$$.util.isNumber(field) && !$$.util.isBoolean(field);
			},
			isEnglish:function (c) {
				//是否为英文
				return c >= 'a' && c <= 'z'	|| c >= 'A' && c <= 'Z';
			},
			isHalfSymbol:function(c){
				//英文符号
				var code = c.charCodeAt(0);
				return code >= 33 && code <= 47
				|| code >= 59 && code <= 64
				|| code >= 91 && code <= 96
				|| code >= 124 && code <= 127;
			},
			isAllSymbol:function(c){
				//中文符号
				var code = c.charCodeAt(0);
				return code == 126 || code == 8216 || code == 8221 ||
					code == 12289 || code == 12290 || code == 12298 || 
					code == 12299 || code == 12304 || code == 12305  ||
					code == 65306 || code == 65307 || code == 65281 || 
					code == 65292 || code == 65311;
			},
			isNullReturn:function(obj, dobj){
				//如果obj未定义 则返回dobj 
				return obj == undefined ? dobj : obj;
			},
			date:{
			
			},
			//填充成指定位数的字符
			fillString:function(string, num, fillChar){
				//fillChar 默认为零
				//num      需要多少位的数字
				fillChar = fillChar == undefined ? "0" : fillChar;
				var temp = "";
				string = string + "";
				var len = num - string.length;
				for(var i = 0; i < len; i++) temp += fillChar;
				return temp + "" + string;
			},
			//去填充
			fillString_recover:function(string, fillChar){
				//fillChar 填充符 默认为零
				fillChar = fillChar == undefined ? "0" : fillChar;
				var len = string.length;
				for(var i = 0; i < len; i++){
					if(!(string.charAt(i) == fillChar))
						return string.substring(i, string.length);
				}
				return string; 
			},
			getRan:function(){
				return Num_ran5();
			},
			firstUpperCase:function(str){
				//首字母大写
				return str.charAt(0).toUpperCase() + str.substring(1);
			},
			firstLowercase:function(str){
				//首字母小写
				return str.charAt(0).toLowerCase() + str.substring(1);
			}, 
			inputInsert:function(target, str){
				// 向某一个输入框的特定位置输入特定字符串或者字符
				// 参数 target(输入框元素) 、 输入字符/字符串
				if (target.selectionStart || target.selectionStart == '0'){
					var startPos = target.selectionStart;
					var endPos = target.selectionEnd;
					target.value = target.value.substring(0, startPos) 
					+ str
					+ target.value.substring(endPos, target.value.length);
					target.selectionStart = startPos + 1;
					target.selectionEnd = endPos + 1;
				} else target.value += str;
			},
		},
		//json
		json:{
			toObject:function(jsonStr) {return Tokener.parseObject(jsonStr);},
			toString:function(jsonObj) {return Tokener.parseString(jsonObj);},
			toJSON:function(jsonObj) {return new ObjectParseJSON().parseJSON(jsonObj)},
		},
	}
	
	/*
		17/11/25
			
		size()		长度
		isEmpt()	是否为空
		add()		添加
		get(i)		获取
		remove(i)	删除
		remove(e)	删除
		toArray()	返回数组形式
		itarator()	获取一个遍历对象
		removeLast()删除最后一个
		removeFrist()	删除第一个
		getFrist()	获取第一个
		getLast()	获取最后一个
		concat()	拼接
		concatArray()  数组拼接
		replace() 	替换
		removeAll() 2017/12/28
		serch()     2017/12/28
			
			
		array_ 数组 如果为undefined则函数自个实例化
			
		itarator的使用案例	
			var it = list.itarator();
			while(it.hasNext()){
				alert(it.next());
			}
	*/
	function ArrayList(array_){
		var array;
		var length;
		setInit ();
		function setInit (){
			array = (array_ == undefined ? new Array() : array_);
			length = array.length;
		}
		this.size = function (){
			return length;
		}
		this.isEmpty = function (){
			return length == 0 ? true : false;
		}
		this.add = function (e){
			array[length] = e;
			length++;
			return true;
		}
		this.concat = function (list){
			if(list.size() != 0){
				tempArray = list.toArray();
				array = array.concat(tempArray);
				length += list.size();
				return true;
			}
			return false;
		}
		this.concatArray = function (arr){
			array = arr.length == 0 ? array : array.concat(arr);
			length += arr.length; 
		}
		this.get = function (i){
			return array[i];
		}
		this.remove = function (i){
			var removeBool = false;
			if(i >= length){
				return false;
			}
			array.splice(i, 1);
			length--;
			return true;
		}
		this.serch = function(e){
			if(e == undefined){
				return false;
			}
			for(var j = 0; j < length; j++){
				if(array[j] == e)
					return true;
			}
			return false;
		}
		this.removeAll = function(){
			length = 0;
			array = new Array();
		}
		this.removeElment = function (e){
			if(e == null || e == undefined){
				return false;
			}
			for(var j = 0; j < length; j++){
				if(array[j] == e){
					array.splice(j, 1);
					length --;
					return e;
				}
			}
			return false;
		}
		this.toArray = function (){
			return array;
		}
		this.toString = function (){
			var temp = "[";
			for(var i = 0; i < length; i++){
				temp += i > 0 ? "," : "";
				temp += array[i];
			}
			return temp + "]";  
		}
		this.itarator = function (){
			return new Itarator(array);
		}
		this.removeLast = function (){
			temp = array[length-1];
			array.splice(length - 1, 1);
			length--;
			return temp;
		}
		this.removeFrist = function (){
			temp = array[0];
			array.splice(0, 1);
			length--;
			return temp;
		}
		this.getFrist = function (){
			return length == 0 ? null : array[0];
		}
		this.getLast = function (){
			return length == 0 ? null : array[length-1];
		}
		this.replace = function (i, e){
			array.splice(i, 1, e);
			return true;
		}
		this.forEach = function (fn){
			for(var i in array){
				if(fn instanceof Function)
					fn(array[i]);
				else 
					new Exception("ArrayList", "forEach", "fn 不是一个function：" + fn);
			}
		}
		this.subList = function (fromIndex, toIndex){
			var arr__ = array.slice(fromIndex, toIndex);
			return new ArrayList(arr__);
		}
	}
	
	/*
		next()
		pre()
		hasNext()
		hasPre()
	
	*/
	function Itarator(array){
		var index;
		var length;
		init();
		function init(){
			index = 0;
			length = array.length;
		}
		this.next = function (){
			temp = array[index];
			index++;
			return temp;
		}
		this.pre = function (){
			temp = array[index];
			index--;
			return temp;
	
		}
		this.hasNext = function (){
			return index < length;
		}
		this.hasPre = function (){
			return index > -1;
		}
	}
	
	/**
	getName		通过函数名称获取方法名称
	getArgus	通过函数名称获取所传的参数 以数组形式返回
	*/
	Function.prototype.getName = function(aa){
		for(var i=0;i<arguments.length;i++){
			alert(arguments[i]);
		}
		return this.name || this.toString().match(/function\s*([^(]*)\(/)[1];
	}
	Function.prototype.getArgus = function(aa){
		return arguments;
	}
	
	/**
	 * extends 继承支持对象	V2
	 * 方法解析 ：
	 *	对继承的讲解
	 *		可以   this.say			引用方法 可以调用      | this.a  引用属性可以调用 
	 *		类似于 function say(){} 私有方法不能直接调用   | var a   私有属性不能调用 
	 *		
	 *	案例：
	 *		子类
     *
	 *		必要代码
	 *		子类名称.extends(父类名称); //参数不是字符串
	 *
	 * @author 威 
	 * 2018/3/19 下午16:30:25 
	 */
	Function.prototype.extends = function(superClassName){
		var Super_ = function(){};
		eval("Super_.prototype = " + superClassName.getName() + ".prototype;");
		eval(this.getName()+".prototype = new Super_();");
	}
	
	//快速在js中创建link标签
	function createLink(hrefPath){
		var link = document.createElement("link");
		link.attr("rel", "stylesheet");
		link.attr("type", "text/css");
		link.attr("href", hrefPath);
		document.head.creEle("link");
	}
	function createElement(tag){
		return document.createElement(tag);
	}
	
	/**
	* 2018/9/26 
	* 范例： "p.example" - 选择class名为example的p标签元素
	*		 "a[target]" - 选择有target属性的a标签元素
	*		 ""h2, h3""  - 选择h2，h3标签元素
	*		 "." - 返回对应的class类名的元素集
	*		 "#" - 返回对应的id名的元素（单个）
	*		 ""  - 返回对应标签名的元素集
 	* 更新： 修改为以querySelector为核心的元素选择
	* 方法: $$$(elementName)
	* 用途：根据id、class、元素名选取元素
	*/
	function $$$(elementName){
		return select(document, elementName);
		/*
		2018/9/26 废弃
		if(elementName.charAt(0) == "#"){
			return document.getElementById(elementName.substring(1, elementName.length));
		}else if(elementName.charAt(0) == "."){
			return document.getElementsByClassName(elementName.substring(1, elementName.length));

		}else
			return document.getElementsByTagName(elementName);
		*/
	}
	function select(e, eName){
		if(eName.charAt(0) == "#") return e.querySelector(eName);
		return e.querySelectorAll(eName);
	}
	/**
	更新：
	2018/9/25 getWidth、getHeight、getMarginLeft、getMarginRight、getMarginTop、getMarginBottom

	解析接口：
		creEle		给一个Node创建一个ChildNode并追加在Node的尾部
		creEleTop	给一个Node创建一个ChildNode并追加在Node的首部
	
	参数	
		creEle		tag	标签名
		creEleTop	tag	标签名

	案例：
		$(".self_layer_user_id")[0].creEle("Label").text("cjw1");
		$(".self_layer_user_id")[0].creEleTop("Label").text("cjw2");
		效果如下：
		<label class="self_layer_user_id">
			<label>cjw2</label>
			<label>cjw2</label>
		</label>
	
	*/
	Node.prototype.getWidth = function(){
		
		return this.offsetWidth;
	}
	Node.prototype.getHeight = function(){
		
		return this.offsetHeight;
	}
	Node.prototype.getMarginLeft = function(){
		
		return this.getBoundingClientRect().left;
	}
	Node.prototype.getMarginRight = function(){
		
		return this.getBoundingClientRect().right;
	}
	Node.prototype.getMarginTop = function(){
		
		return this.getBoundingClientRect().top;
	}
	Node.prototype.getMarginBottom = function(){
		
		return this.getBoundingClientRect().bottom;
	}
	//在尾部创建子元素
	Node.prototype.creEle = function(tag){
		var E = document.createElement(tag);
		this.appendChild(E);
		return E;
	}
	//在顶部创建子元素
	Node.prototype.creEleTop = function(tag){
		var newNode = document.createElement(tag);
		this.insertBefore(newNode, this.firstChild); //childNodes(0)
		return newNode;
	}
	//给一个元素添加属性或者获取属性
	Node.prototype.attr = function(attrName, Value){
		if(Value == undefined)
			return this.getAttribute(attrName);
		this.setAttribute(attrName, Value);
		return this;
	}
	//给一个元素添加文本或者获取文本值
	Node.prototype.text = function(text){
		if(text == undefined)
			return this.innerHTML;
		this.innerHTML = text;
		return this;
	}
	//删除一个元素自身
	Node.prototype.remove = function(){
		var temp = this;
		this.parentNode.removeChild(this);
		return temp;
	}
	//
	Node.prototype.removeAttr = function(attrName){
		this.removeAttribute(attrName);
	}
	//获取节点名称
	Node.prototype.tName = function (){
		return this.nodeName.toLowerCase();
	}
	//获取标签名为name的父元素 name为空则获取当前元素的父元素
	Node.prototype.parent = function (name){
		if(name != undefined){
			var currentNode = this;
			while((currentNode = currentNode.parentNode) != null){
				if(currentNode.tName() == name)
					return currentNode;
			}
			return null;
		}
		return this.nodeParent;
	}
	Node.prototype.isParentNode = function (parent){
		if(parent == undefined)
			return false;
		var currentNode = this;
		if(currentNode.parent(parent.tName()) == parent){
			return true;
		}
		return false;
	}
	/**
	解析接口：
		append		给一个Node添加一个兄弟节点并追加在Node的后面
		appendTop	给一个Node添加一个兄弟节点并追加在Node的前面
		insert		给一个Node插入一个ChildNode并追加在Node的尾部
		insertTop	给一个Node插入一个ChildNode并追加在Node的首部
	
	参数	
		append		htmlText	HTML代码
		appendTop	htmlText	HTML代码
		insert		htmlText	HTML代码
		insertTop	htmlText	HTML代码

	案例：
		$(".self_layer_user_id")[0].creEle("Label").text("cjw1");
		$(".self_layer_user_id")[0].creEleTop("Label").text("cjw2");
		效果如下：
		<label class="self_layer_user_id">
			<label>cjw2</label>
			<label>cjw2</label>
		</label>
	*/
	Node.prototype.append_ = function(htmlText){
		this.insertAdjacentHTML("afterEnd",htmlText);
		return this.nextElementSibling;
	}	
	Node.prototype.appendTop = function(htmlText){
		this.insertAdjacentHTML("beforeBegin",htmlText);
		return this.previousElementSibling;
	}
	Node.prototype.insert = function(htmlText){
		this.insertAdjacentHTML("beforeEnd",htmlText);
		return this.lastElementChild;
	}
	Node.prototype.insertTop = function(htmlText){
		this.insertAdjacentHTML("afterBegin",htmlText);
		return this.firstElementChild;
	}
	//
	Node.prototype.$ = function(elementName){
		if(elementName.charAt(0) == "#"){
			return this.getElementById(elementName.substring(1, elementName.length));
		}else if(elementName.charAt(0) == "."){
			return this.getElementsByClassName(elementName.substring(1, elementName.length));

		}else
			return this.getElementsByTagName(elementName);
	}
	//2018/9/26
	Node.prototype.addClass = function(className){
		//feb 21 bug
		//修复添加覆盖全部的问题
		var classNames = this.getAttribute("class");
		/*
		mar 2,2019 修复有null class的问题
		if (classNames == undefined || classNames.indexOf(className) == -1) {
			this.setAttribute("class", classNames + " " + className);
		}
		可能结果 class="null page_ctl_click"
		*/
		if(classNames == undefined || classNames == "" || classNames == null){
			this.setAttribute("class", className);
		} else if (classNames.indexOf(className) == -1) {
			this.setAttribute("class", classNames + " " + className);
		} 	
		return this;
	}
	//2018/9/26 删除整个class属性
	Node.prototype.removeClass = function(className){
		//feb 21 bug
		//删除指定class名称
		if(className == undefined) 
			this.removeAttribute("class");
		else {
			var classNames = this.getAttribute("class");
			var index; 
			if((index = classNames.indexOf(className)) != -1){
				
				this.setAttribute("class", classNames.substring(0, index === 0 ? 0 : index-1) 
					+ classNames.substring(index + className.length));
			}
		}
		return this;
	}

	
	
	/**
	为当前元素添加样式，样式的参数方式与页面中的参数形式一致
	参数 isFlag 为覆盖参数  默认不覆盖 false为覆盖 
	案例：e.css("border:1px solid #ccc; width:200px; height:200px;");
	2017/12/6
	*/
	Node.prototype.css = function(cssText, isFlag){
		if((isFlag === true) && (this.style.cssText.length > 0))
			cssText += this.style.cssText;
		this.style.cssText = cssText;
	}
	Node.prototype.removeCss = function(cssText, isFlag){
		this.style.cssText = '';
	}
	//单独设置样式 以key--value的形式设置样式
	Node.prototype.cssKV = function (styleKey, styleValue){
		if(styleValue == undefined)
			return this.style.getPropertyValue(styleKey);
		this.style.setProperty(styleKey, styleValue);
	}
	Node.prototype.removeCssKV = function (styleKey){
		this.style.removeProperty(styleKey);
	}
	//2018/9/27
	Element.prototype.first = function(){
		//console.log(this);
		return this.firstElementChild;
	}
	//2018/9/27
	Element.prototype.last = function(){
		return this.lastElmentChild;
	}
	//2018/9/28
	Element.prototype.childs = function(){
		return this.children;
	}
	
	Element.prototype.pre = function(){
		if(this.previousElementSibling) return this.previousElementSibling;
		var sib = this.previousSibling;
		while(sib && sib.nodeType !== 1) sib = sib.previousSibling;
		return sib;
	}
	Node.prototype.appendTo = function(e){
		e.appendChild(this);
		return this;
	}
	Element.prototype.appendTo = function(e){
		e.appendChild(this);
		return this;
	}

	Element.prototype.next = function(){
		if(this.nextElementSibling) return this.nextElementSibling;
		var sib = this.nextSibling;
		while(sib && sib.nodeType !== 1) sib = sib.nextSibling;
		return sib;
	}
	//对其他兄弟元素进行操作
	Element.prototype.siblings = function(){
		var nl = new NodeListC();//NodeListC 装元素的集合
		//获取父元素
		var p = this.parentNode;
		var next = p.first();
		//遍历装 剩下的
		while(next != null){
			nl.add(next);
			next = next.next()
		}
		nl.remove(this);	//删除自身
		return nl;
	}
	//可以完成DOM链式操作
	//返回所有子元素
	Element.prototype.end = function(){
		var parent = this.parentElement;
		return parent.children;
	}
	//返回当前元素的子元素的第一个
	Element.prototype.frist = function(){
		return this.firstElementChild;
	}
	//返回当前元素的子元素的最后一个
	/*
	Element.prototype.last = function(){
		return this.lastElementChild;
	}
	*/

	/**
		replaceAll 替换所有
	*/
	String.prototype.replaceAll = function(repChar, newChar){
		var startIndex = 0;
		var str = tempstr = this;
		while((startIndex = tempstr.indexOf(repChar, startIndex)) != -1){
			str = str.replace(repChar, newChar);
			startIndex++;
		}
		return str;
	}
	String.prototype.forEach = function(fn){
		for(var i = 0; i < this.length; i++){
			fn(this.charAt(i), i);
		}
	}

	/**
	 * 对HTMLCollection 进行属性添加
	 * 2018/9/26
	 *	eq
	 *	css
	 *	each
	 *	forEach
	 *
	 */
	/*
	通常，NodeList和HTMLCollection的实时性非常有用。但是，如果要在迭代一个NodeList对象时在文档
	中添加或者删除元素，首先会需要对NodeList对象生成一个静态的副本：
	var snapshot=Array.prototype.slice.call(nodelist,0);

	NodeList和HTMLCollection对象不是历史文档状态的一个静态快照，而通常是实时的，并且当文档变化
	时它们所包含的元素列表能随之改变
	

	*/
	//根据index找元素
	NodeList.prototype.eq = function (index){
		return this[index];
	}
	//设置元素集的样式
	NodeList.prototype.css = function (css){
		
	}
	//返回的是下标 传递的是下标
	NodeList.prototype.each = function (fn){
		for(var i = 0; i < this.length; i++){
			(fn(i))(i);
		}
	}
	//返回的是元素 传递的是元素
	NodeList.prototype.forEach = function (fn){
		for(var i = 0, len = this.length; i < len; i++){
			fn(this.eq(i));
		}
	}
	NodeList.prototype.last = function (){
		return this.eq(this.length-1);
	}

	NodeList.prototype.first = function (){
		//console.log(this);
		return this.eq(0);
	}
	NodeList.prototype.removeClass = function(className){
		this.forEach((item)=>{
			item.removeClass(className);
		});
	}
	NodeList.prototype.addClass = function(className){
		this.forEach((item)=>{
			item.addClass(className);
		});
	}

	HTMLCollection.prototype.eq = function (index){
		return this[index];
	}
	//设置元素集的样式
	HTMLCollection.prototype.css = function (css){
		
	}
	//返回的是下标
	HTMLCollection.prototype.each = function (fn){
		for(var i = 0; i < this.length; i++){
			(fn(i))(i);
		}
	}
	//返回的是元素
	HTMLCollection.prototype.forEach = function (fn){
		for(var i = 0; i < this.length; i++){
			fn(this.eq(i));
		}
	}
	HTMLCollection.prototype.last = function (){
		return this.eq(this.length-1);
	}

	HTMLCollection.prototype.first = function (){
		return this.eq(0);
	}
	HTMLCollection.prototype.removeClass = function(className){
		this.forEach((item)=>{
			item.removeClass(className);
		});
	}
	HTMLCollection.prototype.addClass = function(className){
		this.forEach((item)=>{
			item.addClass(className);
		});
	}
	HTMLCollection.prototype.remove = function(){
		var f;
		while((f = this.first()) != undefined)
			f.remove();
	}
	
	/**
	 * @Name NodeListC
	 * @Version V1
	 * @Date 2018/9/26
	 * @Desc(
	 *    用于收集兄弟元素并封装成此对象，实现批量删除removeClass方法
	 *    size				获取长度
	 *    add				添加元素进集
	 *    removeClass		给集里面的所有元素删除class属性
	 * )
	 * @Param
	 * @Renew
	 * @History
	 */
	//在Element.prototype.siblings中会用到
	function NodeListC(){
		var arr = [];
		this.size = function(){
			return arr.length;
		}
		//添加元素
		this.add = function(item){
			arr.push(item);
		}
		//删除数组中的元素
		this.remove = function(item){
			if(classof(item) == "Number")
				return arr.splice(item, 1);
			else{
				for(var i = 0; i < arr.length; i++){
					if(arr[i] == item){
						return arr.splice(i, 1);
					}
				}
			}	
		}
		
		/*
		案例：
		var testArr = ["sj", "sh", "sg", "sf"];
		function rep(str){
			var result = str.replace("s", "e");
			return result;
		}
		alert(testArr.map(rep));
		*/
		this.removeClass = function(className){
			for(var i = 0, len = arr.length; i < len; i++)
				arr[i].removeClass(className);
		}
		//mar 1,2019
		this.addClass = function(className){
			for(var i = 0, len = arr.length; i < len; i++)
				arr[i].addClass(className);
		}
		//mar 1,2019
		this.forEach = function(fn){
			for(var i = 0, len = arr.length; i < len; i++){
				fn(arr[i], i);
			}
		}
		/*
		mar 1,2019 异常 不能够完成命令
		//私有方法
		function removeC(item){

			//console.log(item instanceof Node);
			item.removeClass();
			return item;
		}
		//mar 1,2019
		function addC(className){
			item.addClass(className);
		}
		*/
		//给元素集设置样式
		this.css = function(css){}
	}
	

	var gTokener = new GTokener();
	//栈保存的对象 {list: 子集, h: 深度, } 用于圆点列表
	function DataStack(l, height){
		//
		if(l === undefined) l = null;
		if(height === undefined) height = null;
		this.list = l;
		this.h = height;
	}
	
	//list保存的对象 用于圆点列表 生成li标签
	function ItemData(oddOrEven, context, c){
		//console.log(context.currentLine);
		if(c === undefined) c = null;
		this.text = context.currentLine;		//项值
		this.childs = c;	//子集
		this.html = function(){
			var li = $$.cre("li");
			li.addClass(oddOrEven % 2 != 0 ? "li-odd" : "li-even")
			gTokener.done(context).appendTo(li);
			//$$.cre("pre").text().appendTo(li)
			if(this.childs !== null && this.childs.size() > 0){
				//html += '<ul class="ul-point">';
				var ul = $$.cre("ul").addClass("ul-point");
				this.childs.forEach(item => {
					//html += item.html();
					item.html().appendTo(ul);
				});
				ul.appendTo(li);
				//html += '</ul>';
			}
			return li;
			//return '<li>'+'<pre>'+this.text+'</pre>'+html+'</li>'
		}
	}
	
	var gitUtil = {
		//检测圆点列表
		test1:function(str){
			if(str.charAt(0) === "\t"){ 
				var i = 1;
				while(true){
					var c = str.charAt(i++);
					if(c === "\t")
						continue;
					else if(c === "*"){
						if(str.charAt(i++) === " ")
							return [true, i - 2, str.substring(i)];
					}
					else break;
				}
			}
			else if(str.charAt(0) === "*"){
				if(str.charAt(1) === " ")
					return [true, 0, str.substring(2)];
			} 
			return [false];	
		}
		,isSimpleDir: function(str){
			if(str.charAt(0) === "\t"){ 
				var i = 1;
				while(true){
					var c = str.charAt(i++);
					if(c === "\t")
						continue;
					else if(c === "-"){
						if(str.charAt(i++) === " ")
							return [true, i - 2, str.substring(i)];
					}
					else break;
				}
			}
			else if(str.charAt(0) === "-"){
				if(str.charAt(1) === " ")
					return [true, 0, str.substring(2)];
			} 
			return [false]; 
		}
		,firstSep	: "  ├─"
		,fullSep	: "  │"
		,lastSep	: "  └─"
		,fullSep2	: "    "
		,isLastOfFull: []
		,getFullSep: function(count){
			var str = '';
			
			for(var i = 0; i < count; i++){ 
				str += gitUtil.isLastOfFull[i] ? gitUtil.fullSep2 : gitUtil.fullSep; 
			}
			return str;
		}
	}
	
	//解析上下文context类  参数是未编译的文本
	function GitContext(str){
		arr = str.split("\n");
		this.currentLine = arr.shift(); //
		//将this.currentLine赋值为下一行
		this.next = function(){
			this.currentLine = arr.shift();
		}
		//将currentLine从新载入数组首位
		this.pre = function(line){
			// console.log("GitContext push " + line);
			if(line === undefined)
				arr.unshift(this.currentLine);
			else {arr.unshift(line); this.currentLine = arr.shift();}
		}
	}
	
	//解析管理类 2018/10/10
	/*
	使用案例 提供类名包括点符号
	new DefaultOp({
		textArea: ".t",
		runButton: ".bt",
		gitContain: ".git-cnt",
		gitContainIn: ".git-cnt .git-cnt-in",
	});
	*/	
	//自定义封装 触发事件 文本键盘事件 的处理 2018/10/12
	function DefaultOp(data){
		//初始化相关事件
		var textarea = $$$(data.textArea).eq(0);//文本
		var bt = $$$(data.runButton).eq(0);//执行按钮
		var git_cnt = $$$(data.gitContain).eq(0);//外容器
		var git_cnt_in_class = data.gitContainIn;//内容器 可删除
		//textarea.value = "";
		//点击按钮进行转换
		bt.onclick = function(e){
			var str = textarea.tName() == "textarea" ? textarea.value : textarea.text();
			if($(git_cnt_in_class).eq(0) !== undefined)
				$$$(git_cnt_in_class).eq(0).remove();
			$$.cre("div").addClass(data.gitContainInClass).appendTo(git_cnt);
			arr = new GitManage(str).getElements();
			arr.forEach(item => {
				item.appendTo($(git_cnt_in_class).eq(0));
			});
			//$(".git-cnt").eq(0).insert('<div class="git-cnt-in">' 
			//+ new GitManage(str).html() + "</div>");
			if($(git_cnt_in_class).eq(0) !== undefined 
				&& $$$(git_cnt_in_class).eq(0).first() !== undefined)
				$$$(git_cnt_in_class).eq(0).first().css("margin-top: 0px !important;");
			if(data.fn !== undefined) data.fn(e);
		}
		
		//textarea输入制表符
		textarea.onkeydown = function(e){
			if(e.keyCode === 9){
				//点击制表符并向其中添加制表符
				$$.util.inputInsert(e.target, "\t");
				return false;//返回false才不会切换焦点
			}
		}
	} 
	
	//.getElements() 返回一个元素集 将元素集插入特定容器即可 2018/10/12
	function GitManage(str){
		var self = this;
		var context = initContext();//初始化上下文对象
		//初始化责任链
		var gTok = new TitleTokener();
		gTok.setNext(new OrderNumTokener())
			.setNext(new SepLineTokener())
			.setNext(new PointTokener())
			.setNext(new TableTokener())
			.setNext(new SimpleDirTokener());
		function initContext(){
			return new GitContext(str);
		}
		//2018/10/12
		this.getElements = function(){
			var arr = new Array();
			try{
				while(context.currentLine !== undefined){
					arr.push(gTok.support(context));
					context.next();
				}
			}catch(err){
				console.log(err);
			}
			return arr;
		}
	}
	
	//责任链拦截项超类
	//将传入的值改为context 2018/10/10
	function GitTokener (){
		var next;
		this.setNext = function (n){
			next = n;
			return next;
		}
		this.support = function (context) {
			if(this.isSure(context)){
				return this.done(context);
			} else if (next != null){
				return next.support(context);
			} else {
				return this.fail(context);
			}
		}
		//是否属于当前编译
		this.isSure = function(context){return true;}
		//完成
		this.done = function(context){}
		//失败 无法编译
		this.fail = function(context){
			return gTokener.done(context);
		}
	}
	
	//标题
	TitleTokener.extends(GitTokener);
	function TitleTokener (){
		GitTokener.call(this);
		this.isSure = function(context){
			var str = context.currentLine;
			if(str.charAt(0) === "#"){
				for(var i=1; i < str.length; i++) {
					var c = str.charAt(i);
					if(c === "#")
						continue;
					else if (c === " ")
						break;
					else return false;
				}
				return true;
			}
			else return false;
		}
		this.done = function(context){
			var str = context.currentLine;
			var tag = "";
			//根据空格的位置计算标题码数
			var start = str.indexOf(" ");
			tag = "h"+start;
			var h = $$.cre(tag);
			return h.addClass("git-title").text(str.substring(start+1));
			//return '<'+tag+'>'+str.substring(start+1)+'</'+tag+'>';
		}
	}	
	
	//有序小标题
	// 1.
	OrderNumTokener.extends(GitTokener);
	function OrderNumTokener (){
		GitTokener.call(this);
		this.isSure = function(context){
			var str = context.currentLine;
			if($$.util.isNumber(str.charAt(0)) && str.charAt(1) === ".")
				return true;
			return false;
		}
		this.done = function(context){
			var ul = $$.cre("ul").addClass("nolist ul-ol");
			$$.cre("li").text(context.currentLine).appendTo(ul);
			//html += '<li>'+ context.currentLine +'</li>';
			context.next();
			while(this.isSure(context)){
				//html += '<li>'+ context.currentLine +'</li>';
				$$.cre("li").text(context.currentLine).appendTo(ul);
				context.next();
			}
			if (context.currentLine !== undefined) context.pre();
			return ul;
		}
	}

	//分隔线
	SepLineTokener.extends(GitTokener);
	function SepLineTokener (){
		GitTokener.call(this);
		this.isSure = function(context){
			var str = context.currentLine;
			if(str.charAt(0) === "-" && str.substring(0, 3) === "---")
				return true;
			return false;
		}
		this.done = function(context){
			var str = context.currentLine;
			var lastC = str.charAt(str.length-1);
			//判断尾数为几 否则默认为1
			var classPreDix = ($$.util.isNumber(lastC) ? lastC : 1);
			//最大只能为3
			return $$.cre("p").addClass("p-sep-line-" + (classPreDix > 4 ? 4 : classPreDix));
		}
	}	
	
	HTMLCodeTokener.extends(GitTokener);
	function HTMLCodeTokener (){
		GitTokener.call(this);
		this.isSure = function(context){
			var str = context.currentLine;
			
			if(str.charAt(0) === "-" && str.substring(0, 3) === "---")
				return true;
			return false;
		}
		this.done = function(context){
			var str = context.currentLine;
			var lastC = str.charAt(str.length-1);
			//判断尾数为几 否则默认为1
			var classPreDix = ($$.util.isNumber(lastC) ? lastC : 1);
			//最大只能为3
			return $$.cre("p").addClass("p-sep-line-" + (classPreDix > 4 ? 4 : classPreDix));
		}
	}
	
	//适配
	function TempContext(str){
		this.currentLine = str;
	}
	
	function SimpleDirItem(text){
		this.text = text;
		this.childs = []; 
	}
	
	// 简易目录
	/*
	效果：
	  ├─GitTokener 编译类
      │  ├─TitleTokener		标题
      │  ├─OrderNumTokener	有序列表
      │  ├─SepLineTokener	分割线
      │  ├─PointTokener		无序列表
      │  └─TableTokener		表格
      ├─GitManage git核心类
      └─GitContext git上下文对象
	输入：
		'- GitTokener 编译类\n'+
		'	- TitleTokener		标题\n'+ 
		'	- OrderNumTokener	有序列表\n'+ 
		'	- SepLineTokener	分割线\n'+  
		'	- PointTokener		无序列表\n'+ 
		'	- TableTokener		表格\n'+  
		'- GitManage git核心类\n'+ 
		'- GitContext git上下文对象\n'+ 
	 */
	SimpleDirTokener.extends(GitTokener);
	function SimpleDirTokener(){
		GitTokener.call(this);
		this.isSure = function(context){
			var str = context.currentLine;
			return gitUtil.isSimpleDir(str)[0];
		}
		this.done = function(context){
			var result = analy(context)
			,ul = $$.cre("ul").addClass("ul-point").attr("style", "list-style-type: none;"); 
			done2(-1, result, ul);
			return ul;
		}
		function analy(context){
			return analy1(0, context);
		}
		function analy1(preIndex_, context){
			var dirContainer = []
			,preIndex = preIndex_
			,t = gitUtil.isSimpleDir(context.currentLine);
			while(t[0]) {
				if(t[1] == preIndex){
					dirContainer.push(new SimpleDirItem(t[2]))
				} else if (t[1] > preIndex) {
					dirContainer[dirContainer.length - 1].childs = analy1(t[1], context);
				} else { 
					context.pre();
					return dirContainer;
				} 	
				context.next();
				if (context.currentLine !== undefined)
					t = gitUtil.isSimpleDir(context.currentLine);
				else break;
			}   
			if (context.currentLine !== undefined) context.pre();//当前行不符合则回退
			return dirContainer;
		}
		function done2(c, childs, ul){ 
			var count = c+1; 
			for(var i in childs){
				if(i == childs.length-1){ 
					model(true, gitUtil.lastSep);
				} else {
					model(false, gitUtil.firstSep);
				}
				//console.log(childs[i]);
				if(childs[i].childs.length > 0)
					done2(count, childs[i].childs, ul);
			} 
			function model(bool, sep) {
				gitUtil.isLastOfFull[count] = bool; 
				var li = $$.cre("li").addClass("simple-dir-li"); //li.attr("style", "text-align: center;");
				$$.cre("pre").text(gitUtil.getFullSep(count)+sep).addClass("simple-dir-text-odd").appendTo(li); 
				$$.cre("pre").text(childs[i].text).addClass("simple-dir-text-even").appendTo(li);
				li.appendTo(ul);
			}
		}
	}
	
	//小圆点列表 2018/10/10
	// 2019/12/7  ArrayStack换成普通数组
	PointTokener.extends(GitTokener);
	function PointTokener(){
		GitTokener.call(this);
		this.isSure = function(context){
			var str = context.currentLine;
			return gitUtil.test1(str)[0];
		}
		this.done = function(context){
			var result = analy(context);
			//var html = '<ul class="ul-point">';
			var ul = $$.cre("ul").addClass("ul-point");
			result.forEach(item => {
				//html += item.html();
				
				item.html().appendTo(ul);
			});

			return ul;
			//return html + "</ul>";
		}
		//解析圆点列表 返回list
		function analy(context){
			//var stack = new ArrayStack();//每一个深度的list栈
			var stack = [];//每一个深度的list栈
			var list = new ArrayList();//顶部圆点
			stack.push(new DataStack(list, 0));
			var preItem;
			var i = 0;
			var t = gitUtil.test1(context.currentLine);
			while(t[0]){
				//console.log("进行 "+t[1]);
				//是圆点列表项
				//判断级别获取list并将项载入list
				//栈顶深度h与此时项深度相等则进行添加操作
				
				if(stack[stack.length-1].h === t[1]){
					//console.log("存在深度 " + t[2]);
					preItem = new ItemData(t[1], new TempContext(t[2]));
					stack[stack.length-1].list.add(preItem);
				}
				//栈顶深度h 大于 此时深度，则需回退
				else if(stack[stack.length-1].h > t[1]){
					//console.log("回退 " + t[2]);
					//回退-- 执行pop()方法删除栈顶并达到合适深度
					while(stack[stack.length-1].h !== t[1])
						stack.pop();
					//创建数据项 ItemData对象
					preItem = new ItemData(t[1], new TempContext(t[2]));
					stack[stack.length-1].list.add(preItem);
				}
				//栈顶深度h 小于 此时深度，则需创建list ；list值注入到上一个项的childs中并压入栈
				else {
					//
					//console.log("深度加深，创建新的深度 "+ t[2]);
					_list = new ArrayList();
					preItem.childs = _list;
					stack.push(new DataStack(preItem.childs, t[1]));
					preItem = new ItemData(t[1], new TempContext(t[2]));
					stack[stack.length-1].list.add(preItem);
				}
				context.next();
				if (context.currentLine !== undefined)
					t = gitUtil.test1(context.currentLine);
				else break;
			}
			if (context.currentLine !== undefined) context.pre();//当前行不符合则回退
			return list;
		}
	}
	
	/*
	test6 加入表格
	|attr_name|desc
	|:---:|------
	|type|normal/definedwin
	|title|程序名称
	|layout|

	isSure
		temp = str
		if(temp.charAt(0) === "|"){
			context.next();
			str = context.currentLine;
			context.pre();
			context.pre(temp);
			return isSureOftest6(str);
		} else return false;
			
	isSureOftest6(str){
		//判断|:---:|------ 是否正确书写
		if(str.charAt(0) === "|"){
			str = str.substring(1);
			var arr = str.split("|");
			for(var i = 0; i < arr.length; i++){
				str = arr[i];
				var start = 0, end = str.length-1;
				if(str.charAt(0) === ":") start = 1;
				if(str.charAt(end) === ":") end -= 1;
				for(var j = start; j <= end; j++){
					if(str.charAt(j) !== "-") return false 
				}
			}
			
		}else return false;
		return true;
	}	
	var test6Str = "|---|------- ";
	console.log(isSureOftest6(test6Str));
	function isSureOftest6(str){
		str = str.trim();
		//判断|:---:|------ 是否正确书写
		if(str.charAt(0) === "|"){
			str = str.substring(1);
			var arr = str.split("|");
			console.log(arr);
			for(var i = 0; i < arr.length; i++){
				str = arr[i];
				var start = 0, end = str.length-1;
				if(str.charAt(0) === ":") start = 1;
				if(str.charAt(end) === ":") end -= 1;
				for(var j = start; j <= end; j++){
					if(str.charAt(j) !== "-") return false 
				}
			}
			
		}else return false;
		return true;
	}
	*/
	
	TableTokener.extends(GitTokener);
	function TableTokener(){
		GitTokener.call(this);
		this.isSure = function(context){
			var str = context.currentLine;
			temp = str;
			if(temp.charAt(0) === "|"){
				context.next();
				str = context.currentLine;
				context.pre();
				//console.log(context.currentLine);
				context.pre(temp);
				//console.log(context.currentLine);
				return _isSure(str);
			} else return false;
		}
		//检测第二行数据
		function _isSure(str){
			//判断|:---:|------ 是否正确书写
			if(str.charAt(0) === "|"){
				var alignArr
				str = str.substring(1);
				var arr = str.split("|");
				for(var i = 0; i < arr.length; i++){
					var align = "";
					str = arr[i];
					var start = 0, end = str.length-1;
					if(str.charAt(0) === ":") start = 1;
					if(str.charAt(end) === ":") end -= 1;
					for(var j = start; j <= end; j++){
						if(str.charAt(j) !== "-") return false 
					}
				}
			}else return false;
			return true;
		}
		//获取每一项的Align数据
		function _getAligns(str){
			//判断|:---:|------ 是否正确书写
			var alignArr = new Array();
			str = str.substring(1);
			var arr = str.split("|");
			for(var i = 0; i < arr.length; i++){
				var align = "";
				str = arr[i];
				var start = 0, end = str.length-1;
				if(str.charAt(0) === ":") align = "left";
				//console.log("str.charAt(end) "+str.charAt(end));
				if(str.charAt(end) === ":") align = (align == "left" ? "center" : "right");
				alignArr.push(align);
			}
			return alignArr;
		}
		this.done = function(context){
			var result = _done(context);
			return new SuperTableV2(result.array, 
					result.alignArray).html().addClass("commonly-table");
		}
		//处理 获取数据
		function _done(context){
			var arr = new Array();
			//console.log(context.currentLine);
			arr.push(context.currentLine.substring(1).split("|"));
			context.next();
			var alignArr = _getAligns(context.currentLine);
			context.next();
			while(context.currentLine !== undefined && 
				context.currentLine.charAt(0) === "|"){
				arr.push(context.currentLine.substring(1).split("|"));
				context.next();
			}
			if(context.currentLine !== undefined) context.pre();
			//console.log(arr);
			return {array: arr, alignArray: alignArr};
		}
	}
	function SuperTableV2(data, alignArr){
		this.headTrCode = function(text){
			return $$.cre("tr");
		}
		this.headThCode = function(text){
			return $$.cre("th").text(text);
		}
		this.trCode = function(text){
			return $$.cre("tr");
		}
		this.tdCode = function(text){
			return $$.cre("td").text(text);
		}
		this.html = function(){
			var headArr = data[0];
			var table = $$.cre("table");
			//var html = "<thead>";
			var thead = $$.cre("thead");
			var tr = $$.cre("tr").appendTo(thead);
			for(var i in headArr){
				$$.cre("th").text(headArr[i]).appendTo(tr);
			}
			thead.appendTo(table);
			var tbody = $$.cre("tbody");
			//console.log(alignArr);
			for(var i = 1; i < data.length; i++){
				var arr = data[i];
				var tr = $$.cre("tr").appendTo(tbody);
				for(var j = 0; j < alignArr.length; j++){
					//console.log("alignArr[j] " + alignArr[j]);
					if (i%2 === 0) tr.addClass("tr-odd");
					//console.log(arr[j]);
					var pre = gTokener.done(new TempContext(arr[j]));
					var th = $$.cre("td").attr("align", alignArr[j]);
					pre.appendTo(th);
					th.appendTo(tr);
				}
			}
			tbody.appendTo(table)
			return table;
		}
	}	
		
	//图片
	//文字链接、图片链接
	//一般编译
	//处理	 处理文本链接 图片链接
	GTokener.extends(GitTokener);
	function GTokener (){
		GitTokener.call(this);
		this.done = function(context){
			var str = context.currentLine;
			
			var i = 0;
			var c;
			var pre = $$.cre("pre").addClass("pre-line");
			try{
				if(typeof(str) != "string") throw "不是字符串，str:"+str;
				while(i < str.length){
				
					c = str.charAt(i++);
					if(c === "`") {
						c = str.charAt(i++);
						var line = "";
						while(c !== "`"){
							line += c;
							c = str.charAt(i++);
						}
						$$.cre("code").addClass("p-box").text(line).appendTo(pre);
					} else if(c === "[") { //[title](url) 文字链接   
						//[title](url) 文字链接 
						//<a class="a-url" target="_blank" href="http://baidu.com" title>示范链接</a>
						var sepLastIndex = str.indexOf("]", i);
						var a = $$.cre("a").addClass("a-url");
						a.attr("target", "_blank");
						if(sepLastIndex !== -1){
							$$.cre("span").text(str.substring(i , sepLastIndex)).appendTo(a);
							tempIndex = sepLastIndex;
							sepLastIndex = str.indexOf(")", sepLastIndex+1);
							a.attr("href", str.substring(tempIndex+2, sepLastIndex))
							//[title](url)(src "alt") 图片链接 判断其后是否跟着(src "alt")
							tempIndex = sepLastIndex;
							if(str.charAt(tempIndex+1) === "("){
								spaceIndex = str.indexOf(" ", tempIndex);
								sepLastIndex = str.indexOf(")", spaceIndex);
								$$.cre("img")
								.attr("alt", str.substring(spaceIndex+2, sepLastIndex-1))
								.attr("src", str.substring(tempIndex+2, spaceIndex))
								.appendTo(a);
							}
							i = sepLastIndex + 1;
						}else 
							$$.cre("span").text(c).appendTo(pre);
						a.appendTo(pre);
					} else if (c === "!") { // 图片 ![alt](src)
						c = str.charAt(i++);
						if(c === "["){
							// 图片 ![alt](src)
							//![百度](https://www.baidu.com/img/baidu_jgylogo3.gif)
							//<img alt="" src=""/>
							var sepLastIndex = str.indexOf("]", i);
							var alt,src;
							alt = str.substring(i , sepLastIndex);
							tempIndex = sepLastIndex;
							sepLastIndex = str.indexOf(")", sepLastIndex+1);
							src = str.substring(tempIndex+2, sepLastIndex);
							i = sepLastIndex + 1;
							$$.cre("img").attr("alt", alt).attr("src", src).attr("width", "80%").attr("click-event", "pictureDetail").appendTo(pre);
						}else $$.cre("span").text('!'+c).appendTo(pre);
					} else if (c === "*"){
						c = str.charAt(i++);
						if(c === "*"){
							var sepLastIndex = str.indexOf("*", i+1);
							$$.cre("font").addClass("font-strong")
							.text(str.substring(i, sepLastIndex)).appendTo(pre);
							i = sepLastIndex + 1;
						} else $$.cre("span").text(c).appendTo(pre);
						// 加粗 **content**
					}else 
						$$.cre("span").text(c).appendTo(pre);
				}
			} catch (err){
				console.log(err);
			}
			return pre;
		}
	}	
	/* ## E-GIT ## */