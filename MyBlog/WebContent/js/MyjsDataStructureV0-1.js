	/*
	@fileName MyjsDataStructureV0-1.js
	@data 2018/10/8
	@author cjw
	@desc 数据结构
 	*/




	//结点实体 双链表实体对象 2018/9/29 
	function DBNode(d, pre, next){
		this.data = d;
		this.pre = pre;
		this.next = next;
		//当前data字段与传入字段比较，是否相等
		this.compareTo = function(d){
			return data == d;//只需比较值相等即可
		}
	}
	/*
	var linke = new DBLinkeTable();
	console.log("isEmpty:"+linke.isEmpty());
	linke.add(new DBNode(1));
	linke.add(new DBNode(2));
	linke.add(new DBNode(3));
	console.log("len:"+ linke.length);

	
	linke.forLastEach(item => console.log("data:"+item.data));
	console.log("remove:"+linke.remove(0).data);
	console.log("remove:"+linke.remove(0).data);
	console.log("first:"+linke.getFirst().data);
	*/
	//
	/*
	双链表 2018/9/29 
	this.length
	this.remove(d)
	this.add(d)
	this.isEmpty()
	this.getLast()
	this.getFirst();
	this.get(i)
	this.getIterater();
	this.forEach(fn)
	this.forLastEach(fn)
	*/
	function DBLinkedList(){
		//headNode 空指针，指向头结点pre=null；如果next=null则该链表为空
		//currentNode 当前结点也是尾结点
		var headNode, currentNode;
		headNode = new DBNode("", null, null);//空指针
		currentNode = headNode;				//当前结点
		var oneself = this;
		this.length = 0;
		this.remove = function(i){
			//i ==> 数字、DBNode、object
			console.log(this.length--);
			if(classof(i) === "Number")
				return removeByIndex(i);
			return remove(i);
		}
		//根据下标删除 @see this.remove
		function removeByIndex (i){
			var temp;
			if(i > oneself.length || i < 0){
				oneself.length++;
				$$.error("数组下标操出");
			}	
			
			if(i == oneself.length) //尾删除
			{
				console.log("尾删除"+oneself.length);
				currentNode.pre.next = null;
				temp = currentNode;
				currentNode = currentNode.pre;
				return temp;
			}
			temp = headNode;
			while((temp = temp.next) !== null && i > 0){
				i--;
			}
			console.log(temp);
			remove(temp);
			return temp;
		}
		//结点删除 @see this.remove
		function remove(n){
			//d如果是一个DBNode对象判断d中的data是否存在；如果是其他对象则判断链表中的数据是否存在data
			
			n.pre.next = n.next;
			n.next.pre = n.pre;
			return 1;
		}
		this.serch = function(d){
			//d ==> DBNode、object

		}
		//添加
		this.add = function(d){
			//d如果是一个DBNode对象-赋值给n，不是则创建一个DBNode对象
			var n = d instanceof DBNode ? d : new DBNode(d);
			this.length++;
			currentNode.next = n;
			n.pre = currentNode;
			currentNode = n;
		}
		//判断是否为空
		this.isEmpty = function(){ 
			return currentNode.pre === null && headNode.next === null;
		}
		//获取第一个结点
		this.getFirst = function(){
			return headNode.next;
		}
		//获取最后一个结点
		this.getLast = function(){
			return currentNode;
		}
		//根据下标获取元素
		this.get = function(i){
			console.log(this.length);
			if(i >= this.length || i < 0)
				$$.error("数组下标操出");
			var temp = headNode;
			while((temp = temp.next) !== null && i > 0){
				i--;
			}
			return temp;
		}
		//获取ItaratorLinke对象 如果node===undefined则从头结点开始 否则从所给元素开始的上一个或者下一个开始
		this.getIterater = function(node){
			return new ItaratorDBLinkedList(node === undefined ? headNode : node);
		}
		//顺序遍历
		this.forEach = function(fn){
			var it = this.getIterater();
			if(!this.isEmpty()){
				while(it.hasNext()){
					fn(it.next());
				}
			}
		}
		//倒叙遍历
		this.forLastEach = function(fn){
			var temp = currentNode;
			var p = new DBNode();
			temp.next = p;
			p.pre = temp;
			var it = this.getIterater(p);
			if(!this.isEmpty()){
				while(it.hasPre()){
					fn(it.pre());
				}
			}
		}
	}
	/*
		next()
		pre()
		hasNext()
		hasPre()
		传入的是第一个node
		1、从头开始遍历
			传入一个head指针
		2、自定义遍历将会从传入的对象进行相应方向的遍历
	*/
	function ItaratorDBLinkedList(node){
		var currentNode = node; 
		this.next = function (){
			return currentNode = currentNode.next;
		}
		this.pre = function (){
			return currentNode = currentNode.pre;
		}
		this.hasNext = function (){
			return currentNode.next !== null;
		}
		this.hasPre = function (){
			return currentNode.pre !== null && currentNode.pre.pre !== null;
		}
	}

	/*
	2018/10/8
	以下为单链表
	isEmpty()
	size()
	get(i)
	set(i, d)
	inser(i, d)
	lastInsert(d)
	remove(i)
	clear()
	search(d)
	contains(d)
	insertDifferrent(d)
	getFirst()
	removeFirst()
	toString()
	*/
	function SinglyNode(d, n){
		this.next = n;
		this.data = d;
	}
	function SinglyLinkedList(){
		var head = new SinglyNode();//创建空指针
		/*
		this.remove = function(d){ }
		*/
		this.isEmpty = function(){
			return head.next === null;
		}
		this.size = function(){
			var p = head.next;
			var len = 0;
			while(p != null){
				len++;
				p = p.next;
			}
			return len;
		}
		this.get = function(i){
			//通过下标获取
			var p = head.next;
			for(var j = 0; p !== null&&j < i; j++)
				p = p.next;
			return (i>=0 && p!==null) ? p.data : null;
		}
		//设置第i个位置的结点的数据
		this.set = function (i, d){
			var p = head.next;
			if(i+1 > size() && i < 0)
				$$.error("下标异常 -- 超出表范围");
			for(var j = 0; p !== null && j < i; j++)
				p = p.next;
			/*以上的循环获取第index节点*/
			p.data = d;	
		}
		this.insert = function(i, d){
			if(d == null)
				$$.error("x==null");
			/*if(index+1 > size() && index < 0)
				throw new RuntimeException("下标异常 -- 超出表范围");*/
			var p = head;
			for(var j = 0; p.next !== null && j < i; j++)
				p = p.next;
			/*以上的循环获取第index-1节点*/
			p.next = new SinglyNode(d, p.next); /*将index-1节点指向新节点并将新节点指向index+1节点*/
			return i;
		}
		//尾部插入
		this.lastInsert = function(d){
			return this.insert(this.size(), d);
		} 
		//格局下标删除
		this.remove = function(i){
			var p = head;
			for(var j = 0; p.next !== null&&j < i; j++) /*返回index-1节点*/
				p = p.next;
			if(i >= 0&&p.next !== null){
				var old = p.next.data;
				p.next = p.next.next;
				return old;
			}
			return null;
		}
		this.clear = function(){
			head.next = null;
		}
		this.search = function (d){
			//
			var p = head.next;
			for(var i = 0; p != null; i++){
				if(p.data == key)
					return i;
				p = p.next;
			}
			return -1;
		}
		this.contains = function(d){
			//
			return search(d) != -1;
		}
		this.insertDifferent = function(d){
			if(!this.contains(d))
				return this.lastInsert(d);
			return -1;
		}
		this.getFirst = function(){
			return head.next;
		}
		this.removeFirst = function(){
			var p = head.next;
			head.next = p.next;//将头指针指向第二个结点
			return p;
		}
		this.toString = function(){
			var line = "";
		
			for(var p = head.next; p != null; p = p.next){
				line += p.data;
				if(p.next != null)
					line += ",";
			}
			return line;
		}
	}


	/*
	2018/10/8
	栈
	接口：
	public abstract boolean isEmpty() ; 是否为空
	public abstract boolean push(E e) ; 装入	
	public abstract E peek() ; 获取顶部不弹出
	public abstract E pop() ; 弹出获取顶部
	*/
	function ArrayStack(a){
		var arr = a === undefined ? new Array() : a;
		this.isEmpty = function(){
			return arr.length === 0;
		}
		this.push = function(d){
			arr.push(d);
		}
		this.peek = function(){
			return arr[arr.length-1];
		}
		this.pop = function(){
			return arr.pop();
		}
		this.toString = function(){
			return arr;
		}
	}