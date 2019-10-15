/** layuiAdmin.std-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;
layui.define(function(e) {console.log("common");
    var i = (layui.$, layui.layer, layui.laytpl, layui.setter, layui.view, layui.admin);
    i.events.logout = function() { 
        layui.admin.cajax({
			method: 'logout' 
			,success: function(){
				location.href = "../login.chtml"
			}
		}) 
    },
    e("common", {})
});