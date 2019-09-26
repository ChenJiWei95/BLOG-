searchs
cols
editjs ==> forms
forms 

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  			<!-- 标题 -->
  <title>#title#</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="<%=basePath%>layuiadmin/style/admin.css" media="all">
  #link#	<!-- 导入link -->
  #style# 	<!-- 样式模板套用 -->
</head>
<body>
  <div class="layui-fluid">
    <div class="layui-card">
      <div class="layui-form layui-card-header layuiadmin-card-header-auto">
        <div class="layui-form-item">
          <div class="layui-inline">
            	角色筛选
          </div>
          <div class="layui-inline">
          	#serch# <!-- 查询表单项 -->
            <%-- <select name="rolename" lay-filter="LAY-user-adminrole-type">
              <option value="####">全部角色</option>
              <c:forEach begin="0" items="${roles}" step="1" var="Role" varStatus="varsta">
				<option value="${Role.id}">${Role.name}</option>
			  </c:forEach> 
            </select> --%>
          </div>
          <div class="layui-inline">
            <button class="layui-btn c-button" lay-submit lay-filter="C-btn-search">
              <i class="layui-icon layui-icon-search C-btn-search"></i>
            </button>
          </div>
        </div>
      </div>
      <div class="layui-card-body"> 
        <div style="padding-bottom: 10px;">
          <button class="layui-btn C-btn-saveorupdate c-button" data-type="add">添加</button>
		  <button class="layui-btn C-btn-saveorupdate c-button" data-type="edit">编辑</button>
		  <button class="layui-btn C-btn-saveorupdate c-button" data-type="del">删除</button> 
        </div>
        <table id="C-admin-#sign#-table" lay-filter="C-admin-#sign#-table" ></table>  
      </div>
    </div>
  </div>
 <script src="<%=basePath%>layuiadmin/layui/layui.js"></script> 
  <script type="text/html" id="stateTPL">
  {{#  if(d.state === '01'){ }}
    <span style="color: red;">{{ '禁用' }}</span>
  {{#  } else { }}
    <span style="color: #5FB878;">{{ '启用' }}</span>
  {{#  } }}
  </script> 
  <script>
  var table;
  layui.config({
    base: '<%=basePath%>layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'useradmin', 'table', 'admin'], function(){
    var $ = layui.$
    ,form = layui.form
    ,a = "C-admin-#sign#-add"
	,b = 'C-admin-#sign#-update'
	,e = 'C-btn-saveorupdate'
    ,f = 'iframe'
	,l = 'C-admin-#sign#-table'
	,t = 'C-admin-#sign#-form'
	,s = 'C-btn-search'
    ,admin = layui.admin;
    table = layui.table;
    
  	//监听搜索
    form.on('submit('+s+')', function(data){
      	var field = data.field;
      	//执行重载
      	table.reload(l, {
        	where: field
      	});
    });
  
    //事件
    var active = {
		del: function(){
			var arr = []; 
			var checkStatus = table.checkStatus(l)
			,checkData = checkStatus.data; //得到选中的数据
			checkData.length == 0 ? layer.msg("请选中") :
			layer.prompt({
			  	formType: 1
			  	,title: '敏感操作，请验证口令'
			}, function(value, index){
				layer.close(index); // 必须放在靠前的位置，否则无法关闭
			  	
			  	for(var index in checkData){
				  	var data = {};
				  	data["token"] = value
				  	,data["id"] = checkData[index].id;
				  	arr[index] = data;
			  	}
			  	layer.confirm('确定删除吗？', function(data) {
			  		layer.close(layer.index);
				  	admin.cajax({
					  	method: 'remove'
					  	,data: JSON.stringify(arr) 
					  	,success: function(){
					  		table.reload(l);
					  	}
				  	}); 	  
			  	});
			}); 
		}
		,add: function(){
			layer.open({
				type: 2
				,title: '添加'
				,content: 'save_or_update.chtml?'
				,area: ['420px', '480px']
				,btn: ['确定', '取消']
				,yes: function(index, layero){
					layero.find(f).contents().find("#"+a).click();
				}
				,success: function(e, index) {
					var iframe = e.find(f).contents().find("#"+t);
					iframe.find('input[name="id"]').val(admin.randomId());
				}
			});
		}
		,edit: function(){
			var checkStatus = table.checkStatus(l)
			,data = checkStatus.data; //得到选中的数据
			data.length == 0 ? layer.msg("请选中一项") : data.length > 1 ? layer.msg("只能选中一项") :
			layer.open({
                type: 2,
                title: "编辑",
                content: "save_or_update.chtml?"
               	,area: ["420px", "480px"]
                ,btn: ["确定", "取消"]
                ,yes: function(index, layero) {
                    layero.find(f).contents().find("#"+b).click();
                }
                ,success: function(e, index) {
					//这是渲染完之后调用 可以用于初始化
					var form = e.find(f).contents().find("#"+t);
					form.find('input[name="id"]').val(data[0].id)
					,form.find('input[name="name"]').val(data[0].name)
					,form.find('input[name="create_time"]').val(data[0].create_time)
					,form.find('input[name="update_time"]').val(data[0].update_time)
					,form.find('select[name="state"]').val(data[0].state)
					,form.find('textarea[name="desc"]').val(data[0].desc) 
				}
            })
		}
    };  
    table.render({//角色的加载
        elem: "#"+l,
        url: 'list.do',
        cols: [[
        	{type:"checkbox", fixed:"left"}
        	,{field:"id", title:"ID", width:180}
        	,{field:"name", title:"角色名", width:150}
        	,{field:'create_time', title:'创建时间', width:170, sort: !0}
			,{field:'update_time', title:'修改时间', width:170, sort: !0}
        	,{field:"state", title:"状态", templet: '#stateTPL', align: 'center'}
        	,{field:"desc", title:"具体描述"}
        ]],
        text: "对不起，加载出现异常！"
    }); 
    $('.layui-btn.'+e).on('click', function(){
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
    });
  });
  </script>
</body>
</html>



<title>${title}</title>


删除代码：layer.confirm("确定删除吗？",function(data){layer.close(layer.index);admin.cajax({method:"remove",data:JSON.stringify(arr),success:function(){table.reload(l)}})});
删除代码：layer.prompt({formType:1,title:"敏感操作，请验证口令"},function(value,index){layer.close(index);var arr=[];for(var index in checkData){var data={};data["token"]=value,data["id"]=checkData[index].id;arr[index]=data}layer.confirm("确定删除吗？",function(data){admin.cajax({method:"remove",data:JSON.stringify(arr),success:function(){table.reload(l);layer.close(layer.index)}})})});
context{
	private String sign;
	private String title;
	privaet String key;
	private String desc;
	private List<item> items; 
	private componentSet searchSet;
	private componentSet ifreamBtnSet;
}

生成table表头的cols
item{
	private String field;
	private String title;
	private Integer width;
	private boolean sort;
	private String align;
	private String templet; 
}

componentSet {
	
}

searchSet extends componentSet{
	entry;
	toString 生成submit键
}

table: value label name disable placeholder html type(serch iframe)

entry{
	String value;
	String name;
	boolean disable;
	String label;
	String html;  如果有值 将会优先使用
}
btnEntry extends entry{
	toString 生成btn表单组件
}
itextEntry extends entry{
	String label_name = "input";
	String placeholder;
	
	toString 生成iframe text项表单组件
}
selectEntryOf extends entry{ 
	String label_name = "select";
	// forEach 中的 items ==> name+s组成    var ==》 name首字母大写
	toString 生成select表单组件
}
textareaEntry extends entry{
	String label_name = "textarea";
	toString 生成textarea表单组件
}





资源管理：基本功能		-- 对夹和页面的添加和删除，字典管理
资源管理：栏说明		-- 栏管理、数据字典
资源管理：字典--
资源管理：显示--
	操作按钮 夹获取焦点时对应夹操作（删除、添加（页面或夹））/页面获取焦点时对应操作（删除、修改页面）
	夹
	页面

角色管理：说明			-- 管理员对下级管理员的权限控制
角色管理：权限控制具体说明	-- 其实有这一栏（角色管理）权限的人，都能对下级管理进行管理，下级不能管理顶级，顶级可以管理所有
角色管理：栏说明		-- 用户、管理员
角色管理：说明	--
 
操作日志：范例	-- 某某给某某添加了某某权限
	
夹、页面：	-- 博客后台管理一级（夹）、二级（夹）、终端（页面）按钮
一级
	二级
	页面
		三级
		页面

权限判断：夹是否显示-- 1、有页面有权，显示  2、无页面权限，不显示
权限项	：说明		-- 属于管理员的权限项，一个管理对所属的页面都有一个专属的权限项，根据管理员id和页面项id去找
权限	：等级说明	-- 高级、二级、普通
权限	：说明		-- 每一级都有默认的权限设置，剩下的是上级管理的授权
权限	：等级模板

栏	：夹表	-- ID 名称 创建时间 改动时间 显示优先级 关联夹ID 备注   {可去除 资源表代替}
栏	：资源表-- ID 名称 创建时间 改动时间 显示优先级 链接（##/href） 关联夹ID 备注
# 菜单表 
# id name priority url create_time update_time msg
	CREATE TABLE menu(
		`id` VARCHAR(16) NOT NULL primary key COMMENT 'ID',
		`relate_id` VARCHAR(16) NULL COMMENT '上级菜单ID',
		`name` VARCHAR(50) NULL COMMENT '菜单名称',
		`priority` INT DEFAULT 5 COMMENT '显示优先级',
		`create_time` DATETIME NULL COMMENT '创建时间',
		`update_time` DATETIME NULL COMMENT '最新操作时间',
		`url` VARCHAR(100) NULL COMMENT '路径',
		`msg` VARCHAR(100) NULL COMMENT '简易描述，固定字数'
	)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '菜单表';  
# 网站后台基础信息表
# id name url update_time msg spread
	CREATE TABLE website_back_base(
		`id` VARCHAR(16) NOT NULL primary key COMMENT 'ID',
		`name` VARCHAR(50) NULL COMMENT '后台名称',
		`update_time` DATETIME NULL COMMENT '更新时间',
		`url` VARCHAR(100) NULL COMMENT '后台名称点击路径',
		`msg` VARCHAR(100) NULL COMMENT '简易描述'
		`spread` VARCHAR(100) NULL COMMENT '菜单管理展开记忆集'
	)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '网站后台基础信息表';  

文章概述表 article   

		文章ID 文章标题 最新操作时间 图片地址 简要描述 评论数 点赞数 浏览数   
		id article_name op_time pit_url simp_desc（固定字数） comment_count good_count browse_count  
	CREATE TABLE article(  
		`id` INT NOT NULL auto_increment primary key COMMENT '文章ID',  
		`article_name` VARCHAR(50) NULL COMMENT '文章名称',  
		`op_time` DATETIME NULL COMMENT '最新操作时间',	  
		`pit_url` VARCHAR(100) NULL COMMENT '图片路径',  
		`simp_desc` VARCHAR(100) NULL COMMENT '简易描述，固定字数',  
		`comment_count` INT NULL COMMENT '评论数',  
		`good_count` INT NULL COMMENT '点赞数',  
		`browse_count` INT NULL COMMENT '浏览数'  
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '文章概述表';  
	
文章详细表 article_infor   
		文章信息表ID 文章内容 文章概述表（外键）  
		id content_url article_id  
	CREATE TABLE article_infor(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '文章信息表ID',  
		`content_url` VARCHAR(50) NULL COMMENT '文章内容路径',  
		`article_id` INT COMMENT '文章概述表 外键',  
		FOREIGN KEY (`article_id`) REFERENCES article(`id`)  
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '文章详细表';  

#文章概述表&文章详细表
	CREATE TABLE a_infor_relation(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',  
		`a_id` INT NULL COMMENT '文章概述表id',  
		`a_infor_id` INT NULL COMMENT ''
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='文章概述表&文章详细表';

文章标签关联表 article_tag_relate   

		文章标签关联表ID 标签表（外键） 文章概述表（外键）  
		id article_tag_id article_id  
	CREATE TABLE article_tag_relate(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '文章标签关联表ID',  
		`article_tag_id` INT NULL COMMENT '标签表（外键）',  
		`article_id` INT NULL COMMENT '文章概述表（外键）',  
		FOREIGN KEY (`article_id`) REFERENCES article(`id`),  
		FOREIGN KEY (`article_tag_id`) REFERENCES article_tag(`id`)  
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '文章标签关联表';  	
  
文章操作时间表 article_opar   
	
		文章操作时间表ID 时间 文章概述表（外键）  
		id op_time article_id  
	CREATE TABLE article_opar(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '文章操作时间表ID',  
		`op_time` DATETIME NULL COMMENT '时间',  
		`article_id` INT NULL COMMENT '文章概述表（外键）',  
		FOREIGN KEY (`article_id`) REFERENCES article(`id`)  
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '文章操作时间表';  
	  
评论表  article_comment   
	
		评论表ID 评论人 时间 评论内容 点赞数 回复数 文章概述表（外键）  
		id name time content good_count reply_count article_id  
	CREATE TABLE article_comment(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '评论表ID',  
		`name` VARCHAR(20) NULL COMMENT '评论人',  
		`time` DATETIME NULL COMMENT '时间',  
		`content` VARCHAR(500) NULL COMMENT '评论内容',  
		`good_count` INT NULL COMMENT '点赞数',  
		`reply_count` INT NULL COMMENT '回复数',  
		`article_id` INT NULL COMMENT '文章概述表（外键）',   
		FOREIGN KEY (`article_id`) REFERENCES article(`id`)  
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '文章评论表';	  
  
子评论评论表 article_child_comment   
	
		子评论评论表ID 评论人 时间 评论内容 点赞数 评论表（外键）  
		id name time content good_id article_comment_id 
	CREATE TABLE article_child_comment(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '子评论评论表ID',  
		`name` VARCHAR(20) NULL COMMENT '评论人',  
		`time` DATETIME NULL COMMENT '时间',  
		`content` VARCHAR(500) NULL COMMENT '评论内容',   
		`good_id` INT NULL COMMENT '点赞数',   
		`article_comment_id` INT NULL COMMENT '评论表（外键）',  
		FOREIGN KEY (`article_comment_id`) REFERENCES article_comment(`id`)  
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '文章子评论表';  	
  
文章标签表 article_tag   
	
		标签表ID 标签名  
		id tag_name  
		通过标签获取文章概述表  
			select destint 文章概述表id from 标签表, 文章标签表 where 标签表.id = 文章标签表.标签表id  
	CREATE TABLE article_tag(    
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '文章标签表ID',  
		`tag_name` VARCHAR(30) NULL COMMENT '标签名'  
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '文章标签表';  
		  
生活分享概要表 life_share   
	
		生活分享概要表ID 时间 分享内容 权限字段表（all frends private）  
		id time content life_share_grant_fields_id 
	CREATE TABLE life_share(   
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '生活分享概要表ID',  
		`time` DATETIME NULL COMMENT '时间',  
		`content` VARCHAR(500) NULL COMMENT '分享内容',  
		`life_share_grant_fields_id` INT NULL COMMENT '权限字段表'  
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '生活分享概要表';  
	  
生活分享图片表 life_share_picture 
	  
		生活分享图片表ID url 生活分享概要表（外键）  
		id pit_url life_share_id  
	CREATE TABLE life_share_picture(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '生活分享图片表ID',  
		`url` VARCHAR(100) NULL COMMENT '分享图片路径',  
		`life_share_id` INT NULL COMMENT '生活分享概要表（外键）',  
		FOREIGN KEY (`life_share_id`) REFERENCES life_share(`id`)  
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '生活分享图片表';  
  
生活分享评论表 life_share_comment  
	
		生活分享评论表ID 评论人名称 评论内容 时间 生活分享概要表（外键）  
		id name content time life_share_id  
	CREATE TABLE life_share_comment(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '生活分享评论表ID',  
		`name` CHAR(8) NULL COMMENT '评论人名称',  
		`content` VARCHAR(500) NULL COMMENT '评论内容',  
		`time` DATETIME NULL COMMENT '时间',  
		`life_share_id` INT NULL COMMENT '生活分享概要表（外键）',  
		FOREIGN KEY (`life_share_id`) REFERENCES life_share(`id`)  
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '生活分享评论表';  
	   
生活分享评论子表 life_share_child_comment   
	
		生活分享评论子表ID 评论人名称 评论内容 时间 生活分享概要表（外键）  
		id name content time life_share_comment_id  
	CREATE TABLE life_share_child_comment(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '生活分享评论子表ID',  
		`name` CHAR(8) NULL COMMENT '评论人名称',  
		`content` VARCHAR(500) NULL COMMENT '评论内容',  
		`time` DATETIME NULL COMMENT '时间',  
		`life_share_comment_id` INT NULL COMMENT '生活分享概要表（外键）',  
		FOREIGN KEY (`life_share_comment_id`) REFERENCES life_share(`id`)
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '生活分享评论子表';  


 
#权限人和权限字段关系表 life_share_grant  
	
		#权限人和权限字段关系表ID 权限人表（外键） 权限字段表（外键）  
		#id life_share_grant_peo_id life_share_grant_fields_id  
	CREATE TABLE life_share_grant(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '权限人和权限字段关系表ID',  
		`life_share_grant_peo_id` INT NULL COMMENT '权限人表（外键）',  
		`life_share_grant_fields_id` INT NULL COMMENT '权限字段表（外键）',  
		FOREIGN KEY (`life_share_grant_peo_id`) REFERENCES life_share_grant_peo(`id`),  
		FOREIGN KEY (`life_share_grant_fields_id`) REFERENCES life_share_grant_fields(`id`)  
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='权限人和权限字段关系表';  
	  
#权限人表 life_share_grant_peo    生活分享权限人  
	
		#权限人和权限字段关系表ID 名称  
		#id name   
	CREATE TABLE life_share_grant_peo(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '权限人表ID',  
		`name` CHAR(10) NULL COMMENT '名称' 
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '权限人表';  
	  
#权限字段表 life_share_grant_fields   生活分享权限字段 all frends private 
	 
		#权限字段表ID 字段 生活分享概要表（外键）  
		#id fields life_share_id  
		#输入名字查找权限人表，获取权限字段表id，然后获取权限字段，将查询条件设置为all+字段  
	CREATE TABLE life_share_grant_fields(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '权限字段表ID',  
		`fields` CHAR(10) NULL COMMENT '字段',  
		`life_share_id` INT NULL COMMENT '生活分享概要表（外键）',  
		FOREIGN KEY (`life_share_id`) REFERENCES life_share(`id`) 
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='权限字段表'; 

#角色表	：表    -- ID name create_time update_time desc 
	CREATE TABLE role(  
		`id` VARCHAR(16) NOT NULL COMMENT 'ID',  
		`name` VARCHAR(30) NULL COMMENT '角色名',  
		`create_time` DATETIME NULL COMMENT '',  
		`update_time` DATETIME NULL COMMENT '',  
		`desc` VARCHAR(100) NULL COMMENT ''
	)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='角色表'; 
#权限项	：表	-- ID 名称 创建时间 改动时间 关联页面项id 关联角色id 启用状态 备注 
 	CREATE TABLE role_item(  
		`id` VARCHAR(16) NOT NULL COMMENT 'ID',  
		`name` VARCHAR(30) NULL COMMENT '权限项名',  
		`create_time` VARCHAR(30) NULL COMMENT '',  
		`update_time` VARCHAR(30) NULL COMMENT '',  
		`state` CHAR(2) NULL COMMENT '启用状态' DEFAULT '00',  
		`msg` VARCHAR(100) NULL COMMENT '备注',  
		`app_id` VARCHAR(16) NULL COMMENT '关联页面项id' 
	)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='权限项';
#角色&权限项：表 -- ID role_id limit_item_id
	CREATE TABLE role_item_brige(   
		`item_id` VARCHAR(16) NULL COMMENT '管理员ID',  
		`role_id` VARCHAR(16) NULL COMMENT '角色ID'
	)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='角色&权限项';

#管理员账户表	
#：表	-- ID 账号 密码 创建时间 修改时间 登录状态
	CREATE TABLE admin(  
		`id` VARCHAR(16) NOT NULL COMMENT 'ID',  
		`username` VARCHAR(20) NULL COMMENT '',  
		`password` VARCHAR(20) NULL COMMENT '',   
		`login_count` tinyint COMMENT '输入密码错误次数' DEFAULT 0,
		`state` CHAR(2) NULL COMMENT '启用状态' DEFAULT '00'
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='管理员账户表';  
#管理员角色关联表： ID 管理员ID 角色ID 
	CREATE TABLE admin_role_brige(   
		`admin_id` VARCHAR(16) NULL COMMENT '管理员信息ID',  
		`role_id` VARCHAR(16) NULL COMMENT '角色ID' 
	)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='管理员角色关联表';
#管理员信息	：表	-- ID 名称 昵称 创建时间 改动时间 phone email 关联角色id 管理员账户关联id 备注
	CREATE TABLE `C_BLOG`.`Untitled`  (
	  `id` varchar(16) NOT NULL COMMENT 'ID',
	  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名',
	  `name_` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称，回复评论时使用',
	  `create_time` varchar(30) NULL DEFAULT NULL,
	  `update_time` varchar(30) NULL DEFAULT NULL,
	  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
	  `email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
	  `admin_id` varchar(16) NULL DEFAULT NULL,
	  `role_id` varchar(16) NULL DEFAULT NULL COMMENT '关联角色id',
	  `desc` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
	  PRIMARY KEY (`id`) USING BTREE
	) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '管理员信息表' ROW_FORMAT = Compact; 
#管理员账户表&信息：-- ID admin_account_id admin_infor_id
	CREATE TABLE aaccount_ainfor_relation(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',  
		`admin_account_id` CHAR(10) NULL COMMENT '管理员ID',  
		`admin_infor_id` INT NULL COMMENT '角色ID',
		FOREIGN KEY (`admin_account_id`) REFERENCES admin_account(`id`),
		FOREIGN KEY (`admin_infor_id`) REFERENCES admin_infor(`id`)
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='管理员账户表&信息';
#操作日志：表	-- ID 时间 描述

#文章	：表    -- ID 创建时间 修改时间 文章名称 描述
	CREATE TABLE article(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
		`name` VARCHAR(40) NULL COMMENT '文章名称',
		`url` VARCHAR(50) NULL COMMENT '资源位置',
		`create_time` DATETIME NULL COMMENT '',
		`update_time` DATETIME NULL COMMENT '',
		`good` INT NULL COMMENT '',
		`reply` INT NULL COMMENT '',
		`view` INT NULL COMMENT '',
		`desc` VARCHAR(100) NULL COMMENT ''
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='文章表';

#文章标签：表    -- ID 标签名称 创建时间 修改时间 备注
	CREATE TABLE article_tag(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
		`name` VARCHAR(40) NULL COMMENT '文章名称',
		`create_time` DATETIME NULL COMMENT '',
		`update_time` DATETIME NULL COMMENT '', 
		`msg` VARCHAR(50) NULL COMMENT ''
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='文章标签表';
#文章标签关联	-- ID 文章id 标签id 
	CREATE TABLE article_tag_relation(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
		`article_id` INT NULL COMMENT '',
		`tag_id` INT NULL COMMENT ''
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='文章标签关联表';
	
#消息通知 表 ： 	-- ID content type 时间 isRead msg
#异常通知
#ID 操作人 关联文章 关联的人 备注 内容 时间 type(信息类型-隐藏) isRead（是否已读）
	CREATE TABLE message(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
		`article_id` INT NULL COMMENT '关联文章',
		`to` VARCHAR(30) NULL COMMENT '关联的人',
		`self` VARCHAR(30) NULL COMMENT '操作人',
		`type` VARCHAR(400) NULL COMMENT '',
		`content` CHAR(2) NULL COMMENT '',
		`time` DATETIME NULL COMMENT '', 
		`isRead` CHAR(2) NULL COMMENT '' DEFAULT '00',
		`msg` VARCHAR(100) NULL COMMENT ''
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='文章标签关联表';
	
#消息&用户
	CREATE TABLE aaccount_ainfor_relation(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',  
		`message_id` INT NULL COMMENT ' ',  
		`user_account_id` INT NULL COMMENT ' ',
		FOREIGN KEY (`message_id`) REFERENCES user_account(`id`),
		FOREIGN KEY (`user_account_id`) REFERENCES user_info(`id`)
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='消息&用户';
{
  "transcode": "050",
  "merchno": "zb2018091201",
  "dsorderid": "SYS20190710113452",
  "sign": "9136fd8d1d333cef5f7a3226b40594a3",
  "version": "0001",
  "ordersn": "SYS20190710113452",
  "idcard": "152825196605270023",
  "bankcard": "6228480876257129869",
  "mobile": "15184739306",
  "username": "乌兰其其格",
  "futureRateValue": "0.65",
  "fixAmount": "200",
  "methodname": "register"
}	
{
  "transcode": "050",
  "merchno": "dj2018120816161",
  "dsorderid": "EFB7B84596A1415F94A1A82EE74984DB",
  "sign": "66a74733e998c5d45443e751444057ba",
  "version": "0100",
  "ordersn": "7678e785f37a49138f1e1f8a36017255",
  "idcard": "42092119951017495x",
  "bankcard": "6225768653360949",
  "mobile": "15071255813",
  "username": "黄磊",
  "futureRateValue": "0.75",
  "fixAmount": "100",
  "methodname": "register"
}	

#用户 
	CREATE TABLE user_account(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
		`username` VARCHAR(30) NULL COMMENT '',
		`password` VARCHAR(30) NULL COMMENT '',
		`create_time` DATETIME NULL COMMENT '',
		`update_time` DATETIME NULL COMMENT ''
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户';
#信息
	CREATE TABLE user_info(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
		`name` VARCHAR(30) NULL COMMENT '昵称',
		`desc` VARCHAR(255) NULL COMMENT '',
		`msg` VARCHAR(255) NULL COMMENT '',
		`pit` VARCHAR(100) NULL COMMENT '头像地址'
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='信息';
#用户&信息
	CREATE TABLE u_account_infor_relation(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',  
		`user_account_id` INT NULL COMMENT ' ',  
		`user_infor_id` INT NULL COMMENT ' ',
		FOREIGN KEY (`user_account_id`) REFERENCES user_account(`id`),
		FOREIGN KEY (`user_infor_id`) REFERENCES user_info(`id`)
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户&信息';
#用户浏览权限 权限可以创建 文章设置关联权限
#ID name create_time update_time msg
	CREATE TABLE u_authority(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',  
		`create_time` DATETIME NULL COMMENT ' ',  
		`update_time` DATETIME NULL COMMENT ' ',  
		`msg` CHAR(100) NULL COMMENT ' '
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户权限';
#用户&权限
	CREATE TABLE u_user_authority_relation(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',  
		`user_account_id` INT NULL COMMENT ' ',  
		`authority_id` INT NULL COMMENT ' ',
		FOREIGN KEY (`user_account_id`) REFERENCES user_account(`id`),
		FOREIGN KEY (`authority_id`) REFERENCES u_authority(`id`)
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户&信息';
#文章&权限
	CREATE TABLE u_article_authority_relation(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',  
		`article_id` INT NULL COMMENT ' ',  
		`authority_id` INT NULL COMMENT ' ',
		FOREIGN KEY (`article_id`) REFERENCES article(`id`),
		FOREIGN KEY (`authority_id`) REFERENCES u_authority(`id`)
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='文章&权限';
#分享&权限
	CREATE TABLE u_share_authority_relation(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',  
		`share_id` INT NULL COMMENT ' ',  
		`authority_id` INT NULL COMMENT ' ',
		FOREIGN KEY (`share_id`) REFERENCES life_share(`id`),
		FOREIGN KEY (`authority_id`) REFERENCES u_authority(`id`)
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='分享&权限';
public
private
样式先加载出来 
分析需要什么数据字段
data-name 标识
desc 显示 名称
icon 图标
tips 
childs 子元素
	data-name
	desc
	href

明白tree是如何写的界面
config是如何被传入
其他处理不变
管理员管理
	如何实现权限：
	页面相当于一个模块系统，使用需要授权；一个页面有一个id以及页面相关字段
	管理员关联一个角色；角色根据设置（有默认设置）--生成附属权限项；权限项有【页面id、权限状态】
	打开的时候会去查找管理员的角色，根据角色所赋予的权限展示已授权的模块页面 
	管理员和角色有着一个中间表，admin_role_relate
角色管理
	有哪些页面的权限 页面相当于系统
	创建一个角色，等着
图片管理
	上传 编辑 删除	
	图片展示 链接
文章管理
	添加 编辑 删除
	文章ID 文章标题 最新操作时间  简要描述  内容url 状态（public，private） 评论数 点赞数 浏览数
	修改历史表 ： id 开始时间 结束时间 备注 文章id
文章评论
	删除 回复
	id 评论者 评论内容 评论时间 随笔标题 回复数 点赞数 查看原文
	表 ： id 评论者 评论内容 评论时间 随笔标题 回复数 点赞数
消息通知
	浏览（文章、）评论（文章） 点赞（文章） 留言
	表 ： id 标题 type 时间 isRead msg
	type 02随笔 03留言 04系统
	isRead 00已读 01未读 
	
	id msg 时间 type(信息类型-隐藏) 
	例如： 
		0001 小猪赞了小花的评论 2019-06-27 16:30:12 01
		0002 小猪评论了xxx文章 	2019-06-27 16:30:14 02 
		0003 小猪回复了小花 	2019-06-27 16:30:14 03
		0005 小鸟邮箱了你		2019-06-27 16:30:14 04
		0006 小鸟留言了你		2019-06-27 16:30:14 05
		0007 小鸟浏览了你		2019-06-27 16:30:14 06
		0008 超级管理员
	点击可以查看详细
	消息临时表 	ID 备注 时间 type  取消
	消息表 		ID 操作人 关联文章 关联的人 备注 内容 时间 type(信息类型-隐藏) isRead（是否已读）
	
	消息被查看之后就会在临时表中删除已查看的消息
	0001 
	小猪				2019-06-27 16:30:12
		赞了小花的评论
	0002
	小猪				2019-06-27 16:30:12
		[java并发]这篇文章写得不错！
	0003
	小猪				2019-06-27 16:30:12
		[java并发]@小花：你好啊！
	0006
	小鸟				2019-06-27 16:30:12
		感谢站长辛苦的劳作。
	0005
	小鸟				2019-06-27 16:30:12
		小鸟于2019-06-27 16:30:12向你发起了邮箱，请查收。
	0007
	小鸟				2019-06-27 16:30:12
		小鸟于2019-06-27 16:30:12浏览了xxx网页。
	
基本信息
	配置管理员昵称（用于回复评论时的名称）
	管理员信息表
	
根据消息中心的操作按钮样式进行更换
	
进度
	set 前后台打通
		branch ok
		data
			'http://localhost:8080/MyBlog/api/test/data/add.do'
			'http://localhost:8080/MyBlog/api/test/data/update.do'
			'http://localhost:8080/MyBlog/api/test/set/del.do'
	content
		list
			'http://localhost:8080/MyBlog/api/test/contenList/update_conten.do'
			'http://localhost:8080/MyBlog/api/test/contenList/del.do'
			'http://localhost:8080/MyBlog/api/test/branch/add.do'
			'http://localhost:8080/MyBlog/api/test/branch/update.do'
		
		tag
		comment
			'http://localhost:8080/MyBlog/api/test/contenList/comment_del.do'
			打开原文
	limits
		role
			'http://localhost:8080/MyBlog/api/test/role/add.do'
			'http://localhost:8080/MyBlog/api/test/role/update.do'
			'http://localhost:8080/MyBlog/api/test/role/del.do'
		admin
			'http://localhost:8080/MyBlog/api/test/admin/del.do'
			'http://localhost:8080/MyBlog/api/test/admin/add.do'
			'http://localhost:8080/MyBlog/api/test/admin/update.do'
		
	消息
		'http://localhost:8080/MyBlog/api/test/message/direct.do', 留言
		'http://localhost:8080/MyBlog/api/test/message/article.do', 随笔信息
		'http://localhost:8080/MyBlog/api/test/message/all.do', 所有信息
		'http://localhost:8080/MyBlog/api/test/message/sys.do', 系统信息
		
		'http://localhost:8080/MyBlog/api/test/message/ready.do'
		'http://localhost:8080/MyBlog/api/test/message/readyAll.do'
		'http://localhost:8080/MyBlog/api/test/message/del.do'
	
	对encryptData字段 的加密
	public static String encryptECB(String content, String key) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(Base64.decodeBase64(key.getBytes()), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptBytes = cipher.doFinal(content.getBytes("UTF-8"));
        return Base64.encodeBase64String(encryptBytes);
    }
	对encryptKey字段 的加密
	AES对称秘钥的加密
	public static String encrypt(String key, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] b = key.getBytes();
		byte[] b1 = cipher.doFinal(b);
		return Base64.encode(b1);
	}
	

	




