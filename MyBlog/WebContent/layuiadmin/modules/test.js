/* layui自定义封装 */

layui.define(['jquery'], function(exports){ 
    var $ = layui.jquery;
    var obj = {
        say:function(){
			alert("hello word!");
		}
    };
    exports('test', obj);
});