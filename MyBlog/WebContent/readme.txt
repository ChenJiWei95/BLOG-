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

权限判断：夹是否显示	-- 1、有页面有权，显示  2、无页面权限，不显示
权限项	：说明		-- 属于管理员的权限项，一个管理对所属的页面都有一个专属的权限项，根据管理员id和页面项id去找
权限	：等级说明	-- 高级、二级、普通
权限	：说明		-- 每一级都有默认的权限设置，剩下的是上级管理的授权
权限	：等级模板

栏	：夹表	-- ID 名称 创建时间 改动时间 显示优先级 关联夹ID 备注   {可去除 资源表代替}
栏	：资源表-- ID 名称 创建时间 改动时间 显示优先级 链接（##/href） 关联夹ID 备注
角色表	：表    -- ID 
权限项	：表	-- ID 名称 创建时间 改动时间 关联页面项id 关联角色id 启用状态 备注   
管理员	：表	-- ID 名称 昵称 账号 密码 创建时间 改动时间 角色 phone email 关联角色id 备注
操作日志：表	-- ID 时间 描述
文章标签：表    -- ID 标签名称 创建时间 修改时间 备注
文章	：表    -- ID 创建时间 修改时间 文章名称 
管理员角色关联表： ID 管理员ID 角色ID

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
	表 ： id 标题（浏览通知、评论通知、点赞通知、私信）  时间 msg
	分栏 所有、通知、私信
	
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
	消息临时表 	ID 备注 时间   
	消息表 		ID 操作人 关联文章 关联的人 备注 内容 时间 type(信息类型-隐藏)
	消息被查看之后就会在临时表中删除已查看的消息
	0001 
	小猪				2019-06-27 16:30:12
		赞了小花的评论
	0002
	小猪				2019-06-27 16:30:12
		[java并发]
		这篇文章写得不错！
	0003
	小猪				2019-06-27 16:30:12
		[java并发]
		@小花：你好啊！
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
		'http://localhost:8080/MyBlog/api/test/message/direct.do', 私信
		'http://localhost:8080/MyBlog/api/test/message/notice.do', 管理信息
		'http://localhost:8080/MyBlog/api/test/message/aticle.do', 随笔信息
	
		
	
	




