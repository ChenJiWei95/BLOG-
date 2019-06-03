	/* S 联级树列表 */
	//传入json数据
	//[{text:"java基础" , child:[{text:"java基础篇1"}]}]
	function dealCasCade(str){
		var JSONArr = $$.json.toObject(str);
		JSONArr.forEach(item => {
			var root = new SuperCascade(manageT);
			root.setText(item.get("text"));
			if(item.get("child") !== undefined && item.get("child").length() > 0)
				dealCas(root, item.get("child"));
			manageT.insert(root);
		});
	}
	
	function dealCas(root, child){
		//处理子元素集
		child.forEach(data=>{
			var item = new SuperCascade(manageT);
			item.setText(data.get("text"));
			root.add(item);
			if(data.get("child") !== undefined && data.get("child").length() > 0)
				dealCas(item, data.get("child"));
		});
	}
	/**
	 * @Name SuperCascade
	 * @Version V1
	 * @Date 2018/9/24
	 * @Desc 级联对象
	 * @Constructor SuperCascade(ManageCcascade)
	 * @Method setText
	 * @Method html
	 * @Method add
	 * @Method remove
	 * @Method getChilds
	 * @Method getChildLength
	 * @Param
	 * @Renew
	 * @History
	 */
	function SuperCascade (manageCascade){
		this.isClick = false;	//是否已点击
		this.unClick = true;	//为空不允许点击（没有子级）
		
		var rangId = $$.util.getRan();
		manageCascade.register(rangId, this);//注册
	
		//标题
		var text;
		this.setText = function(text_){
			text = text_;
		}
		this.getText = function(){
			return text;
		}
	
		//子元素集
		var childList = new ArrayList();

		//图标 样式代码
		this.fixIcon = function (){
			var label = $$.cre("label").addClass("item-title-ico");
			if(!this.unClick)
				$$.cre("i").addClass("open").appendTo(label);
			return label;
			
		}
		//显示文本 样式代码
		this.textArea = function (){
			return $$.cre("label").addClass("item-title-text").text(this.getText());
			
		}
		//子元素区域 样式代码
		this.childArea = function (){
			var ul = $$.cre("ul").addClass("nolist");
			childList.forEach(function (item) {
				item.html().appendTo(ul);	
			});	
			return ul;
		}

		//生成html字符串
		this.html = function(){
			var li = $$.cre("li").addClass("show-content-li").attr("data-id", rangId);
			var label = $$.cre("label").addClass("item-title clearFloat").appendTo(li);
			this.fixIcon().appendTo(label);
			this.textArea().appendTo(label);
			label = $$.cre("label").addClass("item-child").appendTo(li);
			this.childArea().appendTo(label);
			return li;
		}

		this.add = function (child){
			this.unClick = false;
			childList.add(child);
		}
		this.remove = function (child){
			childList.remove(child);
		}
		this.getChilds = function(){
			return childList;
		}
		this.getChildLength = function(){
			return childList.size();
		}
	}
	/**
	 * @Name ManageCascade
	 * @Version V1
	 * @Date 2018/9/24
	 * @Desc 管理级联对象
	 * @Method register
	 * @Method insert
	 * @Param
	 * @Renew
	 * @History
	 */
	function ManageCascade(paramCilck) {
		var registerMap = new Map();		//dataID--Title对象   通过元素获取Title对象
		//var clickList = new ArrayList();	//点击过元素将会载入，再次点击将会从中删除
		//var isClickList = new ArrayList();	//能不能点击元素，有子元素可以点击
		
		//委托点击
		click ();
		function click (){
			$(".show-content").eq(0).onclick = function(e){
				//console.log(e.target.parent("li").attr("class"));
				//var p = e.target.parent("li"); 
				//open
				var currentE = e.target;
				//点击打开图标
				if(currentE.attr("class") == "open"){
					//p.$(".open").eq(0).style.transform = "rotate(0deg)";
					
					var p = e.target.parent("li");
					var t = registerMap.get(p.attr("data-id"));//获取一个一个SuperCascade对象
					//console.log("t,unClick:"+t + "," + t.unClick);
					//如果SuperCascade对象不为空或者允许点击
					if(t != undefined && !t.unClick){
						//console.log("t,unclick:" + t + "," + t.unClick);
						//是否已点击过
						if(t.isClick) {
							e.target.style.background = "url(../img/open.png)"
							strategyClose(p, t);
							t.isClick = false;
						}else {
							e.target.style.background = "url(../img/open_hover.png)"
							strategyOpen(p, t);	
							t.isClick = true;
						}
					}
				}	
				//点击方法
				if(paramCilck !== undefined)
					paramCilck(e);
			}
		}
		//传入一个label元素 和 一个SuperCascade对象
		function strategyOpen(p, t){
			p.$(".open").eq(0).style.transform = "rotate(0deg)";
			p.$(".item-child").eq(0).style.height = (t.getChildLength()+getLen(t))*25 + "px";
			refreshParentHeight(p.$(".item-child").eq(0), t.getChildLength());
		}
		function strategyClose(p, t){
			p.$(".open").eq(0).style.transform = "rotate(-90deg)";
			p.$(".item-child").eq(0).style.height = "0px";
			refreshParentHeightOnClose(p.$(".item-child").eq(0));
		}
		//传入一个id 和 一个SuperCascade对象
		this.register = function(id, t){
			registerMap.put(id, t);
		}
		this.insert = function (t){
			t.html().appendTo($(".show-content ul").eq(0));
		}
		//strategyOpen方法设置当前item-child的高，由于是固定的所以在打开子元素目录时不符合实时的//高度，因此要向父元素递归处理
		//打开的时候刷新
		function refreshParentHeight(childCnt, len){
			var p = childCnt.parent("label");
			while(p != undefined && p.attr("class") != "item-child")
				p = childCnt.parent("label");
			
			if(p != undefined){
				var t = registerMap.get(p.parent("li").attr("data-id"));
					
				p.style.height = (t.getChildLength() + len)*25 + "px";
				refreshParentHeight(p, len);
			}
		}
		//关闭的时候刷新
		function refreshParentHeightOnClose(childCnt){
			var p = childCnt.parent("label");
			while(p != undefined && p.attr("class") != "item-child")
				p = childCnt.parent("label");
			
			if(p != undefined){
				var t = registerMap.get(p.parent("li").attr("data-id"));
					
				p.style.height = t.getChildLength()*25 + "px";
				refreshParentHeight(p);
			}
		}
		//点击根元素时还要判断子元素是否打开，打开则长度加子元素*25 
		//此处返回额外长度
		function getLen(t){
			var childs = t.getChilds();
			var len = 0;
			childs.forEach(function(item){
				if(!item.unClick){
					if(item.isClick)
						len += item.getChildLength() + getLen(item);
				}
			});
			return len;
		}
	}

	/* E 联级树列表 */