;
layui.define(["admin", "table", "util"],
function(e) {
    var jquery = layui.$,
    table = (layui.admin, layui.table)
	,t_a = 'LAY-app-message-aticle'
	,t_b = 'LAY-app-message-direct'
	,t_c = 'LAY-app-message-sys'
	,t_d = 'LAY-app-message-all'
    adapter = (layui.element, {//数据暂存 适配 对准type
        all: {
            text: "全部消息",
            id: "LAY-app-message-all"
        },
        aticle: {
            text: "随笔",
            id: "LAY-app-message-aticle"
        },
        direct: {
            text: "留言",
            id: "LAY-app-message-direct"
        },
        sys: {
            text: "系统",
            id: "LAY-app-message-sys"
        }
    }),
    a = function(e) {//这里通过模板调用 e为默认传进来的数据
        return '<a href="detail.html?id=' + e.id + '">' + e.title
    };
    table.render({//全部消息
        elem: "#"+t_d,
        url: 'http://localhost:8080/MyBlog/api/test/message/all.do',
        page: !0,
        cols: [[{
            type: "checkbox",
            fixed: "left"
        },
        {
            field: "msg",
            title: "描述",
            minWidth: 300,
            templet: a
        },
        {
            field: "time",
            title: "时间",
            width: 170,
            templet: "<div>{{ layui.util.timeAgo(d.time) }}</div>"
        }]],
        skin: "line"
    }),
    table.render({//文章
        elem: "#"+t_a,
        url: 'http://localhost:8080/MyBlog/api/test/message/aticle.do',
        page: !0,
        cols: [[{
            type: "checkbox",
            fixed: "left"
        },
        {
            field: "msg",
            title: "描述",
            minWidth: 300,
            templet: a
        },
        {
            field: "time",
            title: "时间",
            width: 170,
            templet: "<div>{{ layui.util.timeAgo(d.time) }}</div>"
        }]],
        skin: "line"
    }),
    table.render({//留言
        elem: "#"+t_b,
        url: 'http://localhost:8080/MyBlog/api/test/message/direct.do',
        page: !0,
        cols: [[{
            type: "checkbox",
            fixed: "left"
        },
        {
            field: "msg",
            title: "描述",
            minWidth: 300,
            templet: a
        },
        {
            field: "time",
            title: "时间",
            width: 170,
            templet: "<div>{{ layui.util.timeAgo(d.time) }}</div>"
        }]],
        skin: "line"
    });
	table.render({//系统
        elem: "#"+t_c,
        url: 'http://localhost:8080/MyBlog/api/test/message/sys.do',
        page: !0,
        cols: [[{
            type: "checkbox",
            fixed: "left"
        },
        {
            field: "msg",
            title: "描述",
            minWidth: 300,
            templet: a
        },
        {
            field: "time",
            title: "时间",
            width: 170,
            templet: "<div>{{ layui.util.timeAgo(d.time) }}</div>"
        }]],
        skin: "line"
    });
    var active = {
        del: function(e, t) {
            var a = adapter[t],
            d = i.checkStatus(a.id),
            data = d.data;
            return 0 === s.length ? layer.msg("未选中行") : void layer.confirm("确定删除选中的数据吗？",
            function() {
				admin.req({
					url: 'http://localhost:8080/MyBlog/api/test/message/del.do'
					,type: 'post'
					,data: {data: JSON.stringify(data)}
					,dataType: "json"
					,done: function(data){
						layer.msg("删除成功", {
							icon: 1
						}),
						i.reload(a.id)
					}
				});
            })
        },
        ready: function(e, t) {
            var a = adapter[t],
            d = i.checkStatus(a.id),
            data = d.data;
            return 0 === data.length ? layer.msg("未选中行") : (admin.req({
				url: 'http://localhost:8080/MyBlog/api/test/message/ready.do'
				,type: 'post'
				,data: {data: JSON.stringify(data)}
				,dataType: "json"
				,done: function(data){
					layer.msg("标记已读成功", {
						icon: 1
					})
				}
			}), void i.reload(a.id))
        },
		readyAll: function(e, t) {
            var i = adapter[t]
			,d = i.checkStatus(a.id)
			,data = d.data;
			admin.req({
				url: 'http://localhost:8080/MyBlog/api/test/message/readyAll.do'
				,type: 'post'
				,data: {data: JSON.stringify(data)}
				,dataType: "json"
				,done: function(data){
					layer.msg(i.text + "：全部已读", {
						icon: 1
					})
				}
			});
        }
    };
    jquery(".LAY-app-message-btns .layui-btn").on("click",
    function() {
        var e = jquery(this),
        events = e.data("events"),
        type = e.data("type");
        active[events] && active[events].call(this, e, type)
    }),
    e("message", {})
});