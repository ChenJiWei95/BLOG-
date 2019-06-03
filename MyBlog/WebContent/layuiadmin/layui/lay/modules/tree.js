;
layui.define("form",
function(e) {
    "use strict";
    var i = layui.$,
    a = layui.form,
    n = {
        config: {},
        index: layui.atree ? layui.atree.index + 1e4: 0,
        set: function(e) {
            var a = this;
            return a.config = i.extend({},
            a.config, e),
            a
        },
        on: function(e, i) {
            return layui.onevent.call(this, l, e, i)
        }
    },
    r = function() {
        var e = this,
        i = e.config;
        return {
            getCheck: function() {
                return e.getCheck()
            },
            setCheck: function(i) {
                return e.setCheck(i)
            },
            config: i
        }
    },
    l = "tree",
    t = "layui-tree",
    s = "layui-tree-set",
    d = "layui-tree-iconClick",
    c = "layui-icon-addition",
    h = "layui-icon-subtraction",
    o = "layui-tree-entry",
    u = "layui-tree-main",
    p = "layui-tree-pack",
    f = "layui-tree-spread",
    y = "layui-tree-setLineShort",
    v = "layui-tree-showLine",
    C = "layui-tree-lineExtend",
    m = function(e) {
        var a = this;
        a.index = ++n.index,
        a.config = i.extend({},
        a.config, n.config, e),
        a.render()
    };
    m.prototype.config = {//基本参数 默认配置
        data: [],
        showCheckbox: !1,
        showLine: !0,
        key: "id",
        checked: [],
        spread: [],//展开 根据key
        accordion: !1,
        expandClick: !0,
        isJump: !1,
        renderContent: !1,//带有操作按钮 添加删除修改
        showSearch: !1,
        draggable: !1,
        emptyText: "暂无数据"
    },
    m.prototype.render = function() {//render的内置方法
        var e = this,
        n = e.config,
        r = i('<div class="layui-tree' + (n.showCheckbox ? " layui-form": "") + (n.showLine ? " layui-tree-line": "") + '"></div>');
        e.tree(r);
        var l = i(n.elem),
        s = l.next("." + t);
        return s[0] && s.remove(),
        n.showSearch && r.prepend('<input type="text" class="layui-input layui-tree-search" placeholder="请输入关键字进行过滤">'),
        e.elem = r,
        e.emptyText = i('<div class="layui-tree-emptyText">' + n.emptyText + "</div>"),
        l.html(e.elem),
        0 == e.elem.find(".layui-tree-set").length ? void e.elem.append(e.emptyText) : (n.draggable && e.drag(), n.showCheckbox && a.render("checkbox"), e.elem.find(".layui-tree-set").each(function() {
            i(this).parent(".layui-tree-pack")[0] || i(this).addClass("layui-tree-setHide"),
            !i(this).next()[0] && i(this).parents(".layui-tree-pack").eq(1).hasClass("layui-tree-lineExtend") && i(this).addClass(y),
            i(this).next()[0] || i(this).parents(".layui-tree-set").eq(0).next()[0] || i(this).addClass(y)
        }), void e.events())
    },
    m.prototype.tree = function(e, a) {//生成页面
        var n = this,
        r = n.config,
        l = a || r.data;
        layui.each(l,
        function(a, l) {
            function t(e) {
                return 0 != r.spread.length && i.inArray(e, r.spread) != -1
            }
            function d(e) {
                return 0 != r.checked.length && i.inArray(e, r.checked) != -1
            }
            var c = l.children && l.children.length > 0,
            h = i('<div class="layui-tree-pack" style="' + (t(l[r.key]) ? "display: block;": "") + '"></div>'),
            o = i(["<div " + (r.key ? 'data-key="' + (l[r.key] || "") + '"': "") + ' class="layui-tree-set' + (t(l[r.key]) ? " layui-tree-spread": "") + (d(l[r.key]) ? " layui-tree-checkedFirst": "") + '">', "<div " + (r.draggable && !l.fixed ? 'draggable="true"': "") + ' class="layui-tree-entry">', '<div class="layui-tree-main layui-inline">',
            function() {
                return r.showLine ? c ? '<span class="layui-tree-iconClick layui-tree-icon"><i class="layui-icon ' + (t(l[r.key]) ? "layui-icon-subtraction": "layui-icon-addition") + '"></i></span>': '<span class="layui-tree-iconClick"><i class="layui-icon layui-icon-file"></i></span>': '<span class="layui-tree-iconClick"><i class="layui-tree-iconArrow ' + (c ? "": "hide") + '"></i></span>'
            } (),
            function() {
                return r.showCheckbox ? '<input type="checkbox" name="layuiTreeCheck" lay-skin="primary" ' + (l.disabled ? "disabled": "") + ">": ""
            } (),
            function() {
                return r.isJump && l.href ? '<a href="' + l.href + '" target="_blank" class="layui-tree-txt">' + (l.label || "未命名") + "</a>": '<span class="layui-tree-txt">' + (l.label || "未命名") + "</span>"
            } (), "</div>",
            function() {
                return r.renderContent ? ['<div class="layui-btn-group layui-tree-btnGroup">', '<i class="layui-icon layui-icon-add-1"  data-type="add"></i>', '<i class="layui-icon layui-icon-edit" data-type="edit"></i>', '<i class="layui-icon layui-icon-delete" data-type="del"></i>', "</div>"].join("") : ""
            } (), "</div></div>"].join(""));
            c && (o.append(h), n.tree(h, l.children)),
            e.append(o),
            o.prev("." + s)[0] && o.prev().children(".layui-tree-pack").addClass("layui-tree-showLine"),
            c || o.parent(".layui-tree-pack").addClass("layui-tree-lineExtend"),
            n.spread(o, l),
            r.showCheckbox && n.checkClick(o, l),
            r.renderContent && n.operate(o, l)
        })
    },
    m.prototype.spread = function(e, i) {//点击事件
        var a = this,
        n = a.config,
        r = e.children("." + o),
        l = n.expandClick ? r.children("." + u) : r.find("." + d);
        l.on("click",
        function() {
            var a = e.children("." + p),
            r = l.children(".layui-icon")[0] ? l.children(".layui-icon") : l.find(".layui-tree-icon").children(".layui-icon"),
            t = "";
            if (a[0]) {
                if (e.hasClass(f)) e.removeClass(f),
                a.slideUp(200),
                r.removeClass(h).addClass(c),
                t = "close";
                else if (e.addClass(f), a.slideDown(200), r.addClass(h).removeClass(c), t = "open", n.accordion) {
                    var d = e.siblings("." + s);
                    d.removeClass(f),
                    d.children("." + p).slideUp(200),
                    d.find(".layui-tree-icon").children(".layui-icon").removeClass(h).addClass(c)
                }
            } else t = "normal";
            n.click && n.click({//回调click方法
                elem: e,
                state: t,//close、open
                data: i//key
            })
        })
    },
    m.prototype.checkClick = function(e, n) {//选中
        var r = this,
        l = r.config,
        t = e.children("." + o),
        d = t.children("." + u),
        c = !1;
        d.on("click", 'input[name="layuiTreeCheck"]+',
        function(r) {
            function t(e) {
                if (e.parents("." + s)[0]) {
                    var a = e.siblings().children("." + o),
                    n = e.parent("." + p),
                    r = 1;
                    a[0] && a.each(function(e, a) {
                        var n = i(a).find('input[name="layuiTreeCheck"]')[0];
                        0 != n.checked || n.disabled || (r = 0)
                    }),
                    1 == r && (n.prev().find('input[name="layuiTreeCheck"]')[0].checked = !0, t(n.parent("." + s)))
                }
            }
            layui.stope(r);
            var d = i(this).prev()[0].checked;
            if (!i(this).prev()[0].disabled) {
                if ("object" == typeof n.children || e.find("." + p)[0]) {
                    var h = e.find("." + p).find('input[name="layuiTreeCheck"]');
                    h.each(function(e, i) {
                        i.disabled || (i.checked = d)
                    }),
                    c = !0
                }
                if (d) t(e);
                else {
                    var u = e.parents("." + p).prev();
                    u.each(function(e, a) {
                        i(a).find('input[name="layuiTreeCheck"]')[0].checked = !1
                    })
                }
                l.oncheck && l.oncheck({//回调oncheck方法
                    elem: e,
                    checked: d,
                    data: n,
                    hasChild: c
                }),
                a.render("checkbox")
            }
        })
    },
    m.prototype.operate = function(e, n) {//回调方法operate
        var r = this,
        l = r.config,
        t = e.children("." + o),
        m = t.children("." + u);
        t.children(".layui-tree-btnGroup").on("click", ".layui-icon",
        function(t) {
            layui.stope(t);
            var u = i(this).data("type"),
            k = e.children("." + p),
            g = {
                data: n,
                type: u,
                elem: e
            };
            if ("add" == u) {
                k[0] || (l.showLine ? (m.find("." + d).addClass("layui-tree-icon"), m.find("." + d).children(".layui-icon").addClass(c).removeClass("layui-icon-file")) : m.find(".layui-tree-iconArrow").removeClass("hide"), e.append('<div class="layui-tree-pack"></div>'));
                var x = l.operate && l.operate(g),
                b = {};
                if (b.label = "newElemTree", b[l.key] = x, r.tree(e.children("." + p), [b]), l.showLine) if (k[0]) k.hasClass(C) || k.addClass(C),
                e.find("." + p).each(function() {
                    i(this).children("." + s).last().addClass(y)
                }),
                k.children("." + s).last().prev().hasClass(y) ? k.children("." + s).last().prev().removeClass(y) : k.children("." + s).last().removeClass(y),
                !e.parent("." + p)[0] && e.next()[0] && k.children("." + s).last().removeClass(y);
                else {
                    var w = e.siblings("." + s),
                    T = 1,
                    L = e.parent("." + p);
                    layui.each(w,
                    function(e, a) {
                        i(a).children("." + p)[0] || (T = 0)
                    }),
                    1 == T ? (w.children("." + p).addClass(v), w.children("." + p).children("." + s).removeClass(y), e.children("." + p).addClass(v), L.removeClass(C), L.children("." + s).last().children("." + p).children("." + s).last().addClass(y)) : e.children("." + p).children("." + s).addClass(y)
                }
                if (!l.showCheckbox) return;
                if (m.find('input[name="layuiTreeCheck"]')[0].checked) {
                    var q = e.children("." + p).children("." + s).last();
                    q.find('input[name="layuiTreeCheck"]')[0].checked = !0
                }
                a.render("checkbox")
            } else if ("edit" == u) {
                var A = m.children(".layui-tree-txt").html();
                m.children(".layui-tree-txt").html(""),
                m.append('<input type="text" class="layui-inline layui-tree-editInput">'),
                m.children(".layui-tree-editInput").val(A).focus();
                var S = function(e) {
                    var i = e.val().trim();
                    i = i ? i: "未命名",
                    e.remove(),
                    m.children(".layui-tree-txt").html(i),
                    g.data.label = i,
                    l.operate && l.operate(g)
                };
                m.children(".layui-tree-editInput").blur(function() {
                    S(i(this))
                }),
                m.children(".layui-tree-editInput").on("keydown",
                function(e) {
                    13 === e.keyCode && (e.preventDefault(), S(i(this)))
                })
            } else {
                if (l.operate && l.operate(g), g.status = "remove", !e.prev("." + s)[0] && !e.next("." + s)[0] && !e.parent("." + p)[0]) return e.remove(),
                void r.elem.append(r.emptyText);
                if (e.siblings("." + s).children("." + o)[0]) {
                    if (l.showCheckbox) {
                        var H = function(e) {
                            if (e.parents("." + s)[0]) {
                                var n = e.siblings("." + s).children("." + o),
                                r = e.parent("." + p).prev(),
                                l = r.find('input[name="layuiTreeCheck"]')[0],
                                t = 1,
                                d = 0;
                                0 == l.checked && (n.each(function(e, a) {
                                    var n = i(a).find('input[name="layuiTreeCheck"]')[0];
                                    0 != n.checked || n.disabled || (t = 0),
                                    n.disabled || (d = 1)
                                }), 1 == t && 1 == d && (l.checked = !0, a.render("checkbox"), H(r.parent("." + s))))
                            }
                        };
                        H(e)
                    }
                    if (l.showLine) {
                        var w = e.siblings("." + s),
                        T = 1,
                        L = e.parent("." + p);
                        layui.each(w,
                        function(e, a) {
                            i(a).children("." + p)[0] || (T = 0)
                        }),
                        1 == T ? (k[0] || (L.removeClass(C), w.children("." + p).addClass(v), w.children("." + p).children("." + s).removeClass(y)), e.next()[0] ? L.children("." + s).last().children("." + p).children("." + s).last().addClass(y) : e.prev().children("." + p).children("." + s).last().addClass(y), e.next()[0] || e.parents("." + s)[1] || e.parents("." + s).eq(0).next()[0] || e.prev("." + s).addClass(y)) : !e.next()[0] && e.hasClass(y) && e.prev().addClass(y)
                    }
                } else {
                    var E = e.parent("." + p).prev();
                    if (l.showLine) {
                        E.find("." + d).removeClass("layui-tree-icon"),
                        E.find("." + d).children(".layui-icon").removeClass(h).addClass("layui-icon-file");
                        var I = E.parents("." + p).eq(0);
                        I.addClass(C),
                        I.children("." + s).each(function() {
                            i(this).children("." + p).children("." + s).last().addClass(y)
                        })
                    } else E.find(".layui-tree-iconArrow").addClass("hide");
                    e.parents("." + s).eq(0).removeClass(f),
                    e.parent("." + p).remove()
                }
                e.remove()
            }
        })
    },
    m.prototype.drag = function() {
        var e = this,
        n = e.config;
        e.elem.on("dragstart", "." + o,
        function() {
            var e = i(this).parent("." + s),
            a = e.parents("." + s)[0] ? e.parents("." + s).eq(0) : "未找到父节点";
            n.dragstart && n.dragstart(e, a)
        }),
        e.elem.on("dragend", "." + o,
        function(r) {
            var r = r || event,
            l = r.clientY,
            t = i(this),
            u = t.parent("." + s),
            m = u.height(),
            k = u.offset().top,
            g = e.elem.find("." + s),
            x = e.elem.height(),
            b = e.elem.offset().top,
            w = x + b - 13,
            T = u.parents("." + s)[0],
            L = u.next()[0];
            if (T) var q = u.parent("." + p),
            A = u.parents("." + s).eq(0),
            S = A.parent("." + p),
            H = A.offset().top,
            E = u.siblings(),
            I = A.children("." + p).children("." + s).length;
            var j = function(r) {
                if (T || L || e.elem.children("." + s).last().children("." + p).children("." + s).last().addClass(y), !T) return void u.removeClass("layui-tree-setHide");
                if (1 == I) n.showLine ? (r.find("." + d).removeClass("layui-tree-icon"), r.find("." + d).children(".layui-icon").removeClass(h).addClass("layui-icon-file"), S.addClass(C), S.children("." + s).children("." + p).each(function() {
                    i(this).children("." + s).last().addClass(y)
                })) : r.find(".layui-tree-iconArrow").addClass("hide"),
                r.children("." + p).remove(),
                r.removeClass(f);
                else {
                    if (n.showLine) {
                        var l = 1;
                        layui.each(E,
                        function(e, a) {
                            i(a).children("." + p)[0] || (l = 0)
                        }),
                        1 == l ? (u.children("." + p)[0] || (q.removeClass(C), E.children("." + p).addClass(v), E.children("." + p).children("." + s).removeClass(y)), q.children("." + s).last().children("." + p).children("." + s).last().addClass(y), L || r.parents("." + s)[0] || r.next()[0] || q.children("." + s).last().addClass(y)) : !L && u.hasClass(y) && q.children("." + s).last().addClass(y)
                    }
                    if (n.showCheckbox) {
                        var t = function(e) {
                            if (e) {
                                if (!e.parents("." + s)[0]) return
                            } else if (!r[0]) return;
                            var n = e ? e.siblings().children("." + o) : E.children("." + o),
                            l = e ? e.parent("." + p).prev() : q.prev(),
                            d = l.find('input[name="layuiTreeCheck"]')[0],
                            c = 1,
                            h = 0;
                            0 == d.checked && (n.each(function(e, a) {
                                var n = i(a).find('input[name="layuiTreeCheck"]')[0];
                                0 != n.checked || n.disabled || (c = 0),
                                n.disabled || (h = 1)
                            }), 1 == c && 1 == h && (d.checked = !0, a.render("checkbox"), t(l.parent("." + s) || r)))
                        };
                        t()
                    }
                }
            };
            g.each(function() {
                if (0 != i(this).height()) {
                    if (l > k && l < k + m) return void(n.dragend && n.dragend("拖拽失败，拖拽在节点本身进行！"));
                    if (1 == I && l > H && l < k + m) return void(n.dragend && n.dragend("拖拽失败，拖拽在节点本身进行！"));
                    var a = i(this).offset().top;
                    if (l > a && l < a + 15) {
                        if (i(this).children("." + p)[0] || (n.showLine ? (i(this).find("." + d).eq(0).addClass("layui-tree-icon"), i(this).find("." + d).eq(0).children(".layui-icon").addClass(c).removeClass("layui-icon-file")) : i(this).find(".layui-tree-iconArrow").removeClass("hide"), i(this).append('<div class="layui-tree-pack"></div>')), i(this).children("." + p).append(u), j(A), n.showLine) {
                            var r = i(this).children("." + p).children("." + s);
                            if (u.children("." + p).children("." + s).last().addClass(y), 1 == r.length) {
                                var t = i(this).siblings("." + s),
                                h = 1,
                                f = i(this).parent("." + p);
                                layui.each(t,
                                function(e, a) {
                                    i(a).children("." + p)[0] || (h = 0)
                                }),
                                1 == h ? (t.children("." + p).addClass(v), t.children("." + p).children("." + s).removeClass(y), i(this).children("." + p).addClass(v), f.removeClass(C), f.children("." + s).last().children("." + p).children("." + s).last().addClass(y).removeClass("layui-tree-setHide")) : i(this).children("." + p).children("." + s).addClass(y).removeClass("layui-tree-setHide")
                            } else u.prev("." + s).hasClass(y) ? (u.prev("." + s).removeClass(y), u.addClass(y)) : (u.removeClass("layui-tree-setLineShort layui-tree-setHide"), u.children("." + p)[0] ? u.prev("." + s).children("." + p).children("." + s).last().removeClass(y) : u.siblings("." + s).find("." + p).each(function() {
                                i(this).children("." + s).last().addClass(y)
                            })),
                            i(this).next()[0] || u.addClass(y)
                        }
                        if (n.showCheckbox && i(this).children("." + o).find('input[name="layuiTreeCheck"]')[0].checked) {
                            var g = u.children("." + o);
                            g.find('input[name="layuiTreeCheck"]+').click()
                        }
                        return n.dragend && n.dragend("拖拽成功，进入目标节点内", u, i(this)),
                        !1
                    }
                    if (l < a) {
                        if (i(this).before(u), j(A), n.showLine) {
                            var x = u.children("." + p),
                            b = i(this).parents("." + s).eq(0),
                            T = b.children("." + p).children("." + s).last();
                            if (x[0]) {
                                u.removeClass(y),
                                x.children("." + s).last().removeClass(y);
                                var t = u.siblings("." + s),
                                h = 1;
                                layui.each(t,
                                function(e, a) {
                                    i(a).children("." + p)[0] || (h = 0)
                                }),
                                1 == h ? b[0] && (t.children("." + p).addClass(v), t.children("." + p).children("." + s).removeClass(y), T.children("." + p).children("." + s).last().addClass(y).removeClass(v)) : u.children("." + p).children("." + s).last().addClass(y),
                                !b.parent("." + p)[0] && b.next()[0] && T.removeClass(y)
                            } else b.hasClass(C) || b.addClass(C),
                            b.find("." + p).each(function() {
                                i(this).children("." + s).last().addClass(y)
                            });
                            b[0] || (u.addClass("layui-tree-setHide"), u.children("." + p).children("." + s).last().removeClass(y))
                        }
                        if (b[0] && n.showCheckbox && b.children("." + o).find('input[name="layuiTreeCheck"]')[0].checked) {
                            var g = u.children("." + o);
                            g.find('input[name="layuiTreeCheck"]+').click()
                        }
                        return n.dragend && n.dragend("拖拽成功，插入目标节点上方", u, i(this)),
                        !1
                    }
                    if (l > w) return e.elem.children("." + s).last().children("." + p).addClass(v),
                    e.elem.append(u),
                    j(A),
                    u.prev().children("." + p).children("." + s).last().removeClass(y),
                    u.addClass("layui-tree-setHide"),
                    u.children("." + p).children("." + s).last().addClass(y),
                    n.dragend && n.dragend("拖拽成功，插入最外层节点", u, e.elem),
                    !1
                }
            })
        })
    },
    m.prototype.events = function() {
        var e = this,
        a = e.config,
        n = e.elem.find(".layui-tree-checkedFirst");
        layui.each(n,
        function(e, a) {
            i(a).children("." + o).find('input[name="layuiTreeCheck"]+').trigger("click")
        }),
        e.elem.find(".layui-tree-search").on("keyup",
        function() {
            var n = i(this),
            r = n.val(),
            l = n.nextAll(),
            t = [];
            l.find(".layui-tree-txt").each(function() {
                var e = i(this).parents("." + o);
                if (i(this).html().indexOf(r) != -1) {
                    t.push(i(this).parent());
                    var a = function(e) {
                        e.addClass("layui-tree-searchShow"),
                        e.parent("." + p)[0] && a(e.parent("." + p).parent("." + s))
                    };
                    a(e.parent("." + s))
                }
            }),
            l.find("." + o).each(function() {
                var e = i(this).parent("." + s);
                e.hasClass("layui-tree-searchShow") || e.addClass("layui-hide")
            }),
            0 == l.find(".layui-tree-searchShow").length && e.elem.append(e.emptyText),
            a.onsearch && a.onsearch({
                elem: t
            })
        }),
        e.elem.find(".layui-tree-search").on("keydown",
        function() {
            i(this).nextAll().find("." + o).each(function() {
                var e = i(this).parent("." + s);
                e.removeClass("layui-tree-searchShow layui-hide")
            }),
            i(".layui-tree-emptyText")[0] && i(".layui-tree-emptyText").remove()
        })
    },
    m.prototype.getCheck = function() {
        var e = this,
        a = e.config,
        n = [];
        return e.elem.find(".layui-form-checked").each(function() {
            var e = i(this).parents("." + s)[0];
            a.key ? n.push([e, i(e).data("key")]) : n.push(e)
        }),
        n
    },
    m.prototype.setCheck = function(e) {
        var a = this;
        a.config;
        a.elem.find("." + s).each(function(a, n) {
            var r = i(this).data("key"),
            l = i(n).children("." + o).find('input[name="layuiTreeCheck"]+');
            if ("number" == typeof e) {
                if (r == e) return l[0].checked || l.click(),
                !1
            } else i.inArray(r, e) != -1 && (l[0].checked || l.click())
        })
    },
    n.render = function(e) {
        var i = new m(e);
        return r.call(i)
    },
    e(l, n)
});