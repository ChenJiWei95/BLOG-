;
layui.define(["table", "form"],
function(t) {console.log("contlist");
    var e = layui.$,
    table = layui.table,
    n = layui.form;
    table.render({//文章
        elem: "#LAY-app-content-list",
        id: "LAY-app-content-list",
        url: "http://localhost:8080/MyBlog/api/test/aticle/list.do",
        cols: [[{
            type: "checkbox",
            fixed: "left"
        },
        {
            field: "id",
            width: 100,
            title: "文章ID",
            sort: !0
        },
        {
            field: "label",
            title: "文章标签",
            minWidth: 100
        },
        {
            field: "title",
            title: "文章标题"
        },
        {
            field: "create_time",
            title: "创建时间"
			,sort: !0
        },
        {
            field: "update_time",
            title: "修改时间",
            sort: !0
        }
		,{
			field: "comment_num"
			,title: "评论数"
			,sort: !0
		}
		,{
			field: "good_num"
			,title: "点赞数"
			,sort: !0
		}
		,{
			field: "visit_num"
			,title: "浏览数"
			,sort: !0
		}
		,{
            field: "url",
            title: "资源位置",   
        }]]
        ,page: !0
        /*
		,limit: 10
        ,limits: [10, 15, 20, 25, 30],
		*/
        ,text: "对不起，加载出现异常！"
    }),
    table.on("tool(LAY-app-content-list)",
    function(t) {
        var e = t.data;
        "del" === t.event ? layer.confirm("确定删除此文章？",
        function(e) {
            t.del(),
            layer.close(e)
        }) : "edit" === t.event && layer.open({
            type: 2,
            title: "编辑文章",
            content: "../../../views/app/content/listform.html?id=" + e.id,
            maxmin: !0,
            area: ["550px", "550px"],
            btn: ["确定", "取消"],
            yes: function(e, i) {
                var l = window["layui-layer-iframe" + e],
                a = i.find("iframe").contents().find("#layuiadmin-app-form-edit");
                l.layui.form.on("submit(layuiadmin-app-form-edit)",
                function(i) {
                    var l = i.field;
                    t.update({
                        label: l.label,
                        title: l.title,
                        author: l.author,
                        status: l.status
                    }),
                    n.render(),
                    layer.close(e)
                }),
                a.trigger("click")
            }
        })
    }),
    table.render({//标签加载
        elem: "#LAY-app-content-tags",
		id: "LAY-app-content-tags",
        url: "http://localhost:8080/MyBlog/api/test/tags.do",
        cols: [[
		{
			type: 'checkbox'
			,fixed: 'left'
		}
		,{
            field: "id",
            width: 100,
            title: "ID",
            sort: !0
        }
        ,{
            field: "tags",
            title: "标签名",
            minWidth: 100
        }
        ,{
			field: "msg",
            title: "描述"
        }]],
        text: "对不起，加载出现异常！"
    }),
    /*
	table.on("tool(LAY-app-content-tags)",
    function(t) {
        var i = t.data;
        if ("del" === t.event) layer.confirm("确定删除此分类？",
        function(e) {
            t.del(),
            layer.close(e)
        });
        else if ("edit" === t.event) {
            e(t.tr);
            layer.open({
                type: 2,
                title: "编辑分类",
                content: "../../../views/app/content/tagsform.html?id=" + i.id,
                area: ["450px", "200px"],
                btn: ["确定", "取消"],
                yes: function(e, i) {
                    var n = i.find("iframe").contents().find("#layuiadmin-app-form-tags"),
                    l = n.find('input[name="tags"]').val();
                    l.replace(/\s/g, "") && (t.update({
                        tags: l
                    }), layer.close(e))
                },
                success: function(t, e) {
                    var n = t.find("iframe").contents().find("#layuiadmin-app-form-tags").click();
                    n.find('input[name="tags"]').val(table.tags)
                }
            })
        }
    }),
	*/
    table.render({//评论
        elem: "#LAY-app-content-comm",
        url: "http://localhost:8080/MyBlog/api/test/comm.do",
        cols: [[{
            type: "checkbox",
            fixed: "left"
        },
        {
            field: "id",
            width: 100,
            title: "ID",
            sort: !0
        },
        {
            field: "reviewers",
            title: "评论者"
        },
        {
            field: "content",
            title: "评论内容"
        },
        {
            field: "commtime",
            title: "评论时间",
            minWidth: 100,
            sort: !0
        },
		{
            field: "title",
            title: "随笔标题"
        },
		{
            field: "reply_num",
            title: "回复数", 
			sort: !0
        },
		{
            field: "good_num",
            title: "点赞数", 
			sort: !0
        },
        {
            title: "操作",
            width: 150,
            align: "center",
            fixed: "right",
            toolbar: "#table-content-com"
        }]],
        page: !0,
        limit: 10,
        limits: [10, 15, 20, 25, 30],
        text: "对不起，加载出现异常！"
    }),
    table.on("tool(LAY-app-content-comm)",
    function(t) {
        t.data;
        "del" === t.event ? layer.confirm("确定删除此条评论？",
        function(e) {
            t.del(),
            layer.close(e)
        }) : "edit" === t.event && layer.open({
            type: 2,
            title: "编辑评论",
            content: "../../../views/app/content/contform.html",
            area: ["450px", "300px"],
            btn: ["确定", "取消"],
            yes: function(t, e) {
                var n = window["layui-layer-iframe" + t],
                l = "layuiadmin-app-comm-submit",
                a = e.find("iframe").contents().find("#" + l);
                n.layui.form.on("submit(" + l + ")",
                function(e) {
                    e.field;
                    i.reload("LAY-app-content-comm"),
                    layer.close(t)
                }),
                a.trigger("click")
            },
            success: function(t, e) {}
        })
    }),
    t("contlist", {})
});