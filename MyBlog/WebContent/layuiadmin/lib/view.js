/** layuiAdmin.std-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;
layui.define(["laytpl", "layer"],
function(e) {
    var t = layui.jquery,
    a = layui.laytpl,
    n = layui.layer,
    r = layui.setter,
    o = (layui.device(), layui.hint()),
    i = function(e) {
        return new d(e)
    },
    s = "LAY_app_body",
    d = function(e) {
        this.id = e,
        this.container = t("#" + (e || s))
    };
    i.loading = function(e) {  //载入
		//console.log("view i.loading");
        e.append(this.elemLoad = t('<i class="layui-anim layui-anim-rotate layui-anim-loop layui-icon layui-icon-loading layadmin-loading"></i>'))
    },
    i.removeLoad = function() {  //删除
		//console.log("view i.removeLoad");
        this.elemLoad && this.elemLoad.remove()
    },
    i.exit = function(e) {
		//console.log("view i.exit");
        layui.data(r.tableName, {
            key: r.request.tokenName,
            remove: !0
        }),
        e && e()
    },
    i.req = function(e) {//ajax请求
		//console.log("view i.req");
        var a = e.success,// success 初始化完毕
        n = (e.error, r.request),
        o = r.response,
        s = function() {
            return r.debug ? "<br><cite>URL：</cite>" + e.url: ""
        };
        if (e.data = e.data || {},
        e.headers = e.headers || {},
        n.tokenName) {
            var d = "string" == typeof e.data ? JSON.parse(e.data) : e.data;
            e.data[n.tokenName] = n.tokenName in d ? e.data[n.tokenName] : layui.data(r.tableName)[n.tokenName] || "",
            e.headers[n.tokenName] = n.tokenName in e.headers ? e.headers[n.tokenName] : layui.data(r.tableName)[n.tokenName] || ""
        }
        return delete e.success,
        delete e.error,
        t.ajax(t.extend({
            type: "post",
            dataType: "json", 
            success: function(t) {// done 成功回调
                var n = o.statusCode;
				// 判断 数据状态正常
                if (t[o.statusName] == n.ok)"function" == typeof e.done && e.done(t);
                // 判断 登录状态
				else if (t[o.statusName] == n.logout) i.exit();
                else {
					console.log("success error");
                    var r = ["<cite>Error：</cite> " + (t[o.msgName] || "返回状态码异常"), s()].join("");
                    layer.msg(a);
                }
                "function" == typeof a && a(t)
            },
            error: function(o, t) {
                var a = ["请求异常，请重试<br><cite>错误信息：</cite>" + t, s()].join("");
                //i.error(a),
				layer.msg(a);
				'function' == typeof e.fail && e.fail(o);
                "function" == typeof a && a(res);
            }
        },
        e))
    },
    i.popup = function(e) {
		//console.log("view i.popup 默认提示框 根据传进来的值定制");
        var a = e.success,
        r = e.skin;
        return delete e.success,
        delete e.skin,
        n.open(t.extend({
            type: 1,
            title: "提示",
            content: "",
            id: "LAY-system-view-popup",
            skin: "layui-layer-admin" + (r ? " " + r: ""),
            shadeClose: !0,
            closeBtn: !1,
            success: function(e, r) {
				//console.log("初始化 。。。 ");
                var o = t('<i class="layui-icon" close>&#x1006;</i>');
                e.append(o),
                o.on("click",
                function() {
                    n.close(r)
                }),
                "function" == typeof a && a.apply(this, arguments)
            }
        },
        e))
    },
    i.error = function(e, a) {
		//console.log("view i.error 错误框定制");
        return i.popup(t.extend({
            content: e,
            maxWidth: 300,
            offset: "t",
            anim: 6,
            id: "LAY_adminError"
        },
        a))
    },
    d.prototype.render = function(e, a) {
		//console.log("view d.render");
        var n = this;
        layui.router();
        return e = r.views + e + r.engine,
        t("#" + s).children(".layadmin-loading").remove(),
        i.loading(n.container),
        t.ajax({
            url: e,
            type: "get",
            dataType: "html",
            data: {
                v: layui.cache.version
            },
            success: function(e) {
                e = "<div>" + e + "</div>";
                var r = t(e).find("title"),
                o = r.text() || (e.match(/\<title\>([\s\S]*)\<\/title>/) || [])[1],
                s = {
                    title: o,
                    body: e
                };
                r.remove(),
                n.params = a || {},
                n.then && (n.then(s), delete n.then),
                n.parse(e),
                i.removeLoad(),
                n.done && (n.done(s), delete n.done)
            },
            error: function(e) {
                return i.removeLoad(),
                n.render.isError ? i.error("请求视图文件异常，状态：" + e.status) : (404 === e.status ? n.render("template/tips/404") : n.render("template/tips/error"), void(n.render.isError = !0))
            }
        }),
        n
    },
    d.prototype.parse = function(e, n, r) {
		//console.log("view d.parse");
        var s = this,
        d = "object" == typeof e,
        l = d ? e: t(e),
        u = d ? e: l.find("*[template]"),
        c = function(e) {
            var n = a(e.dataElem.html()),
            o = t.extend({
                params: p.params
            },
            e.res);
            e.dataElem.after(n.render(o)),
            "function" == typeof r && r();
            try {
                e.done && new Function("d", e.done)(o)
            } catch(i) {
                console.error(e.dataElem[0], "\n存在错误回调脚本\n\n", i)
            }
        },
        p = layui.router();
        l.find("title").remove(),
        s.container[n ? "after": "html"](l.children()),
        p.params = s.params || {};
        for (var y = u.length; y > 0; y--) !
        function() {
            var e = u.eq(y - 1),
            t = e.attr("lay-done") || e.attr("lay-then"),
            n = a(e.attr("lay-url") || "").render(p),
            r = a(e.attr("lay-data") || "").render(p),
            s = a(e.attr("lay-headers") || "").render(p);
            try {
                r = new Function("return " + r + ";")()
            } catch(d) {
                o.error("lay-data: " + d.message),
                r = {}
            }
            try {
                s = new Function("return " + s + ";")()
            } catch(d) {
                o.error("lay-headers: " + d.message),
                s = s || {}
            }
            n ? i.req({
                type: e.attr("lay-type") || "get",
                url: n,
                data: r,
                dataType: "json",
                headers: s,
                success: function(a) {
                    c({
                        dataElem: e,
                        res: a,
                        done: t
                    })
                }
            }) : c({
                dataElem: e,
                done: t
            })
        } ();
        return s
    },
    d.prototype.autoRender = function(e, a) {
		//console.log("view d.autoRender");
        var that = this;
        t(e || "body").find("*[template]").each(function(e, a) {
            var r = t(this);
            that.container = r,
            that.parse(r, "refresh")
        })
    },
    d.prototype.send = function(e, t) {//console.log("view d.send");
        var n = a(e || this.container.html()).render(t || {});
        return this.container.html(n),
        this
    },
    d.prototype.refresh = function(e) {//console.log("view d.refresh");
        var that = this,
        next = that.container.next(),
        attr = next.attr("lay-templateid");
        return that.id != attr ? that: (that.parse(that.container, "refresh",
        function() {
            that.container.siblings('[lay-templateid="' + that.id + '"]:last').remove(),
            "function" == typeof e && e()
        }), that)
    },
    d.prototype.then = function(e) {//console.log("view d.then");
        return this.then = e,
        this
    },
    d.prototype.done = function(e) {//console.log("view d.done");
        return this.done = e,
        this
    },
    e("view", i)
});