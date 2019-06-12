;
layui.define(["admin", "table", "util"],
function(e) {
    var jquery = layui.$,
    table = (layui.admin, layui.table),
    adapter = (layui.element, {//数据暂存 适配 对准type
        all: {
            text: "全部消息",
            id: "LAY-app-message-all"
        },
        notice: {
            text: "通知",
            id: "LAY-app-message-notice"
        },
        direct: {
            text: "私信",
            id: "LAY-app-message-direct"
        }
    }),
    a = function(e) {//这里通过模板调用 e为默认传进来的数据
        return '<a href="detail.html?id=' + e.id + '">' + e.title
    };
    table.render({//消息通知
        elem: "#LAY-app-message-all",
        url: 'http://localhost:8080/MyBlog/api/test/message/.do',
        page: !0,
        cols: [[{
            type: "checkbox",
            fixed: "left"
        },
        {
            field: "title",
            title: "标题内容",
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
    table.render({
        elem: "#LAY-app-message-notice",
        url: layui.setter.base + "json/message/notice.js",
        page: !0,
        cols: [[{
            type: "checkbox",
            fixed: "left"
        },
        {
            field: "title",
            title: "标题内容",
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
    table.render({
        elem: "#LAY-app-message-direct",
        url: layui.setter.base + "json/message/direct.js",
        page: !0,
        cols: [[{
            type: "checkbox",
            fixed: "left"
        },
        {
            field: "title",
            title: "标题内容",
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
            s = d.data;
            return 0 === s.length ? layer.msg("未选中行") : void layer.confirm("确定删除选中的数据吗？",
            function() {
                layer.msg("删除成功", {
                    icon: 1
                }),
                i.reload(a.id)
            })
        },
        ready: function(e, t) {
            var a = adapter[t],
            d = i.checkStatus(a.id),
            s = d.data;
            return 0 === s.length ? layer.msg("未选中行") : (layer.msg("标记已读成功", {
                icon: 1
            }), void i.reload(a.id))
        },
        readyAll: function(e, t) {
            var i = adapter[t];
            layer.msg(i.text + "：全部已读", {
                icon: 1
            })
        }
    };
    jquery(".LAY-app-message-btns .layui-btn").on("click",
    function() {
        var e = jquery(this),
        "events" = e.data("events"),
        type = e.data("type");
        active["events"] && active["events"].call(this, e, type)
    }),
    e("message", {})
});