;
layui.define(["table", "form"],
function(e) {
    var t = layui.$,
    table = layui.table;
    layui.form;
    table.render({
        elem: "#LAY-user-manage",
        url: layui.setter.base + "json/useradmin/webuser.js",
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
            field: "username",
            title: "用户名",
            minWidth: 100
        },
        {
            field: "avatar",
            title: "头像",
            width: 100,
            templet: "#imgTpl"
        },
        {
            field: "phone",
            title: "手机"
        },
        {
            field: "email",
            title: "邮箱"
        },
        {
            field: "sex",
            width: 80,
            title: "性别"
        },
        {
            field: "ip",
            title: "IP"
        },
        {
            field: "jointime",
            title: "加入时间",
            sort: !0
        },
        {
            title: "操作",
            width: 150,
            align: "center",
            fixed: "right",
            toolbar: "#table-useradmin-webuser"
        }]],
        page: !0,
        limit: 30,
        height: "full-220",
        text: "对不起，加载出现异常！"
    }),
    table.on("tool(LAY-user-manage)",
    function(e) {
        e.data;
        if ("del" === e.event) layer.prompt({
            formType: 1,
            title: "敏感操作，请验证口令"
        },
        function(t, table) {
            layer.close(table),
            layer.confirm("真的删除行么",
            function(t) {
                e.del(),
                layer.close(t)
            })
        });
        else if ("edit" === e.event) {
            jquery(e.tr);
            layer.open({
                type: 2,
                title: "编辑用户",
                content: "../../../views/user/user/userform.html",
                maxmin: !0,
                area: ["500px", "450px"],
                btn: ["确定", "取消"],
                yes: function(e, t) {
                    var l = window["layui-layer-iframe" + e],
                    r = "LAY-user-front-submit",
                    n = t.find("iframe").contents().find("#" + r);
                    l.layui.form.on("submit(" + r + ")",
                    function(t) {
                        t.field;
                        table.reload("LAY-user-front-submit"),
                        layer.close(e)
                    }),
                    n.trigger("click")
                },
                success: function(e, t) {}
            })
        }
    }),
    table.render({//管理员的加载
        elem: "#LAY-user-back-manage",
        url: 'http://localhost:8080/MyBlog/api/test/admin.do',
        cols: [[{
            type: "checkbox",
            fixed: "left"
        },
        {
            field: "id",
            width: 80,
            title: "ID",
            sort: !0
        },
        {
            field: "account",
            title: "登录名"
        },
        {
            field: "phone",
            title: "手机"
        },
        {
            field: "email",
            title: "邮箱"
        },
        {
            field: "role",
            title: "角色"
        },
        {
            field: "create_time",
            title: "加入时间",
            sort: !0
        }
		,{
            field: "update_time",
            title: "修改时间",
            sort: !0
        }
		,{
            field: "msg",
            title: "描述", 
        }
		]],
        text: "对不起，加载出现异常！"
    }),
    table.on("tool(LAY-user-back-manage)",
    function(e) {
        e.data;
        if ("del" === e.event) layer.prompt({
            formType: 1,
            title: "敏感操作，请验证口令"
        },
        function(t, index) {
            layer.close(index),
            layer.confirm("确定删除此管理员？",
            function(t) {
                console.log(e),
                e.del(),
                layer.close(t)
            })
        });
        else if ("edit" === e.event) {
            t(e.tr);
            layer.open({
                type: 2,
                title: "编辑管理员",
                content: "../../views/user/administrators/adminform.html",
                area: ["420px", "420px"],
                btn: ["确定", "取消"],
                yes: function(e, t) {
                    var l = window["layui-layer-iframe" + e],
                    r = "LAY-user-back-submit",
                    n = t.find("iframe").contents().find("#" + r);
                    l.layui.form.on("submit(" + r + ")",
                    function(t) {
                        t.field;
                        table.reload("LAY-user-front-submit"),
                        layer.close(e)
                    }),
                    n.trigger("click")
                },
                success: function(e, t) {}
            })
        }
    }),
    table.render({//角色的加载
        elem: "#LAY-user-back-role",
        //url: layui.setter.base + "json/useradmin/role.js",
        url: 'http://localhost:8080/MyBlog/api/test/role.do',
        cols: [[{
            type: "checkbox",
            fixed: "left"
        },
        {
            field: "id",
            width: 80,
            title: "ID",
            sort: !0
        },
        {
            field: "rolename",
            title: "角色名"
        },
        {
            field: "limits",
            title: "拥有权限"
        },
        {
            field: "descr",
            title: "具体描述"
        }]],
        text: "对不起，加载出现异常！"
    }),
    /*
	table.on("tool(LAY-user-back-role)",
    function(e) {
        e.data;
        if ("del" === e.event) layer.confirm("确定删除此角色？",
        function(t) {
            e.del(),
            layer.close(t)
        });
        else if ("edit" === e.event) {
            t(e.tr);
            layer.open({
                type: 2,
                title: "编辑角色",
                content: "roleform.html",
                area: ["500px", "480px"],
                btn: ["确定", "取消"],
                yes: function(e, t) {
                    var l = window["layui-layer-iframe" + e],
                    r = t.find("iframe").contents().find("#LAY-user-role-submit");
                    l.layui.form.on("submit(LAY-user-role-submit)",
                    function(t) {
                        t.field;
                        table.reload("LAY-user-back-role"),
                        layer.close(e)
                    }),
                    r.trigger("click")
                },
                success: function(e, t) {}
            })
        }
    }),
	*/
    e("useradmin", {})
});