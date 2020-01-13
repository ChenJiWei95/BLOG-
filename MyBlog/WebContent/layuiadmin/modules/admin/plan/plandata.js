;
layui.define(function(e) {
	console.log("plandata");
	layui.use(["admin", "carousel"],
			function() {
		var e = layui.$,
		t = (layui.admin, layui.carousel),
		a = layui.element,
		i = layui.device();
		e(".layadmin-carousel").each(function() {
			var a = e(this);
			t.render({
				elem: this,
				width: "100%",
				arrow: "none",
				interval: a.data("interval"),
				autoplay: a.data("autoplay") === !0,
				trigger: i.ios || i.android ? "click": "hover",
						anim: a.data("anim")
			})
		}),
		a.render("progress")
	}),
	layui.use(["admin", "carousel", "echarts"],
    function() {
        var e = layui.$,
        t = layui.admin,
        a = layui.carousel,
        i = layui.echarts,
        l = [],
        n = [{
            title: {
                text: "最近一周完成情况",
                x: "center",
                textStyle: {
                    fontSize: 14
                }
            },
            tooltip: {
                trigger: "axis",
                formatter: "{b}日完成量：{c}%"
            },
            xAxis: [{
                type: "category",
                data: weekDate
            }],
            yAxis: [{
                type: "value",
                axisLabel: {  
                	show: true,  
                	interval: 'auto',  
                 	formatter: '{value} %'
               	},  
                show: true
            }],
            series: [{
                type: "line",
                data: planPercent
            }]
        },
        {
            title: {
                textStyle: {
                    fontSize: 14
                }
            },
            legend: {
                data: ['最近一周每日完成量', '最近一周每日总量']
            },
            tooltip: { 
            	trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#6a7985'
                    }
                }
            },
            xAxis: [{
                type: "category",
                data: weekDate
            }],
            yAxis: [{
                type: "value"
            }],
            series: [{
                type: "line",
                name: '最近一周每日完成量',
                stack: '最近一周每日完成量',
                data: planCount
            },
            {
                type: "line",
                name: '最近一周每日总量',
                stack: '最近一周每日总量',
                data: planAllCount
            }]
        }],
        r = e("#LAY-index-dataview").children("div"),
        o = function(e) {
            l[e] = i.init(r[e], layui.echartsTheme),
            l[e].setOption(n[e]),
            t.resize(function() {
                l[e].resize()
            })
        };
        if (r[0]) {
            o(0);
            var d = 0;
            a.on("change(LAY-index-dataview)",
            function(e) {
                o(d = e.index)
            }),
            layui.admin.on("side",
            function() {
                setTimeout(function() {
                    o(d)
                },
                300)
            }),
            layui.admin.on("hash(tab)",
            function() {
                layui.router().path.join("") || o(d)
            })
        }
    })
    e("plandata", {})
});