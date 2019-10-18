/*
左导航栏的生成
*/
;
layui.define("jquery",
function (e){console.log('navTree');
	var jquery = layui.$,
	modName = 'navTree',//模块名称
	self = {
		config: {},
		on: function(e, i) { 
            return layui.onevent.call(this, modName, e, i)
        }
	},
	navTree = function(e) { 
		var that = this;
		that.config = jquery.extend({}, that.config, self.config, e),
        that.render()
	};
	navTree.prototype.config = {//基本参数 默认配置
        data: {}
        //showCheckbox: !1,
        //showLine: !0,
        //key: "id",
        //checked: [],
        //spread: [],//展开 根据key
        //accordion: !1,
        //expandClick: !0,
        //isJump: !1,
        //renderContent: !1,//带有操作按钮 添加删除修改
        //showSearch: !1,
        //draggable: !1,
        //emptyText: "暂无数据"
    },
	navTree.prototype.render = function(){ 
		var that = this,
		config = that.config,
		data = config.data,
		//顶部元素传入tree创建item，
		root = jquery('<ul class="layui-nav layui-nav-tree" lay-shrink="all" id="LAY-system-side-menu" lay-filter="layadmin-system-side-menu"></ul>');
		that.tree(root);
		var outTarget = jquery(config.elem),
		logo = jquery('<div class="layui-logo" lay-href="'+(data.href?data.href:'javascript:;')+'"><span>'+data.desc+'</span></div>');
		outTarget.append(logo),
		outTarget.append(root)
	},
	navTree.prototype.tree = function (e, data) {
		var that = this,
		config = that.config,
		data = data || config.data.data;
		//遍历
		layui.each(data, function(e_, itemData) {
			var hasChilds = itemData.children && itemData.children.length > 0,
			li = jquery('<li data-name="'+itemData.dataName+'" class="layui-nav-item"><a href="javascript:;" lay-tips="'+(itemData.tips?itemData.tips:itemData.desc)+'" lay-direction="2"><i class="layui-icon '+itemData.icon+'"></i><cite>'+itemData.desc+'</cite><span class="layui-nav-more"></span></a></li>'),
			dl = jquery('<dl class="layui-nav-child"></dl>');
			hasChilds && (li.append(dl), that.child(dl, itemData.children));
			e.append(li)
		})
	},
	navTree.prototype.child = function (e, data) {
		var that = this,
		config = that.config; 
		//如果有child
		layui.each(data, function(e_, itemData) {
			var hasChilds = itemData.children && itemData.children.length > 0,
			dd = jquery('<dd><a '+(hasChilds?'href':'lay-href')+'="'+(itemData.href?(itemData.href=='####'?'javascript:;':(config.base+itemData.href)):'javascript:;')+'">'+itemData.desc+'</a></dd>'),
			dl = jquery('<dl class="layui-nav-child"></dl>');
			hasChilds && (dd.append(dl), that.child(dl, itemData.children)),
			e.append(dd)
		})
	},
	self.render = function(e) {//调用此开始渲染界面
		var self = new navTree(e)
	},
	e(modName, self)
})