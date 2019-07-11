/** 

 */
;
layui.extend({
	setter: "config",
	navTree: "lib/navTree",
    admin: "lib/admin",
    view: "lib/view"
}).define(["setter", "admin"],
function(export_) {console.log("index");
    var config = layui.setter,
    element = layui.element, 
    admin, 
	page,
	view,
	util = {
		//获取链接中的参数
		getParameter: function(key){
			var query = window.location.search.substring(1);
			var vars = query.split("&");
			for (var i=0;i<vars.length;i++) {
				var pair = vars[i].split("=");
				if(pair[0] == key){return pair[1];}
			}
			return '';
		}
	},
    iframe = function(url, desc) {console.log("index iframe");//点击左栏 创建一个iframe窗口
        var flag, elements_ = selectElement("#LAY_app_tabsheader>li"),//query - $() 匹配元素
        attr = url.replace(/(^http(s*):)|(\?[\s\S]*$)/g, "");
		elements_.each(function(e) { 
            var element_ = selectElement(this),
            n = element_.attr("lay-id");
            n === url && (flag = !0, page.index = e)
        })
		desc = desc || "新标签页"
        if (config.pageTabs){
			console.log("是否存在顶栏标签 " + flag + " ");
			flag || (selectElement(LAY_app_body).append(['<div class="layadmin-tabsbody-item layui-show">'
				, '<iframe src="' + url + '" frameborder="0" class="layadmin-iframe"></iframe>'
				, "</div>"].join("")), page.index = elements_.length, element.tabAdd(layadmin_layout_tabs, {
				title: "<span>" + desc + "</span>",
				id: url,
				attr: attr
			}));
		}
        else {
			console.log("存在顶栏标签 则直接修改链接" + url);
            var u = admin.tabsBody(admin.tabsPage.index).find(".layadmin-iframe");
            u[0].contentWindow.location.href = url
        }
        element.tabChange(layadmin_layout_tabs, url),
        admin.tabsBodyChange(page.index, {
            url: url,
            text: desc
        })
    },
    LAY_app_body = "#LAY_app_body",
    layadmin_layout_tabs = "layadmin-layout-tabs",
    selectElement = layui.$;//query - $() 匹配元素
    selectElement(window);//query - $() 匹配元素
    layui.config({
        base: config.base + "modules/"
    }),
	//遍历对象
    layui.each(config.extend,
    function(a, i) {
        var n = {};
        n[i] = "{/}" + config.base + "lib/extend/" + i,
        layui.extend(n)
    });
	setTimeout(function(){
		admin = layui.admin,
		page = admin.tabsPage,//代表着一个标签页 
		view = layui.view;
		admin.screen() < 2 && admin.sideFlexible();
		view().autoRender()
	}, 300);
    layui.use("common"),
    export_("index", {
        openTabsPage: iframe//打开页面
		,util: util
    })
});