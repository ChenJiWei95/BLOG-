# blog
  记录
# 目录
* [页面分析](#页面分析)
* [数据库分析](#数据库分析)
* [使用自定义封装的JDBC注意](#使用自定义封装的JDBC)
* [项目遇到的问题](#项目遇到的问题)
* [日志](#日志)

# 页面分析
	首页：
		以列表的形式展示文章概述项 根据时间排序
		对文章概述项进行分页
		对分页进行处理--页面选择、页面前后控制
		可前往文章查看页、标签汇总页、生活分享页、github传送入口、邮件发送、关于我
	标签页：
		展示相关标签的项
	文章查看页：
		显示基本数据（标题、浏览数、标签）
		点击可查看操作时间线
		显示文章详细内容
		评论入口 二级评论
		可前往首页、标签汇总页、点击标签可前往该标签的分类页（实质是首页）、生活分享页、github传送入口、邮件发送、关于我
	生活分享页：
		根据权限显示分享信息 
		评论入口 二级评论 
	关于我：
		个人简历

# 数据库分析  

管理员按钮权限表 admin_app

	CREATE TABLE `admin_app` (
	  `id` varchar(30) NOT NULL COMMENT 'ID', 
	  `admin_id` varchar(30) NULL COMMENT '', 
	  `app_id` varchar(30) NULL COMMENT '', 
	  `up_power` char(2) NULL COMMENT '修改权限', 
	  `del_power` char(2) NULL COMMENT '删除权限', 
	  `ins_power` char(2) NULL COMMENT '插入权限', 
	  PRIMARY KEY (`id`) USING BTREE
	) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '管理员按钮权限表' ROW_FORMAT = Compact;
	根据角色 添加权限的批量插入语句
		insert into `admin_app`(`id`, `admin_id`, `app_id`, `up_power`, `del_power`, `ins_power`)
		selete unix_timestamp(now()), #{adminId}, `app_id`, 00, 00, 00 from `role` where `role_id` = #{roleId}

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
  
权限人和权限字段关系表 life_share_grant  
	
		权限人和权限字段关系表ID 权限人表（外键） 权限字段表（外键）  
		id life_share_grant_peo_id life_share_grant_fields_id  
	CREATE TABLE life_share_grant(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '权限人和权限字段关系表ID',  
		`life_share_grant_peo_id` INT NULL COMMENT '权限人表（外键）',  
		`life_share_grant_fields_id` INT NULL COMMENT '权限字段表（外键）',  
		FOREIGN KEY (`life_share_grant_peo_id`) REFERENCES life_share_grant_peo(`id`),  
		FOREIGN KEY (`life_share_grant_fields_id`) REFERENCES life_share_grant_fields(`id`)  
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='权限人和权限字段关系表';  
	  
权限人表 life_share_grant_peo    生活分享权限人  
	
		权限人和权限字段关系表ID 名称  
		id name   
	CREATE TABLE life_share_grant_peo(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '权限人表ID',  
		`name` CHAR(10) NULL COMMENT '名称' 
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '权限人表';  
	  
权限字段表 life_share_grant_fields   生活分享权限字段 all frends private 
	 
		权限字段表ID 字段 生活分享概要表（外键）  
		id fields life_share_id  
		输入名字查找权限人表，获取权限字段表id，然后获取权限字段，将查询条件设置为all+字段  
	CREATE TABLE life_share_grant_fields(  
		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '权限字段表ID',  
		`fields` CHAR(10) NULL COMMENT '字段',  
		`life_share_id` INT NULL COMMENT '生活分享概要表（外键）',  
		FOREIGN KEY (`life_share_id`) REFERENCES life_share(`id`) 
	)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='权限字段表'; 

# 使用自定义封装的JDBC
	该封装 是跟mybatis结合的，主要是数据库的交互更简便   
	注意：  
		字段修饰符必须用private修饰
		字段名必须和数据库字段对应
		使用BaseInterface接口时不能使用set给Eq设置参数 统一用接口
	

# 项目遇到的问题    

* 1、  Error creating bean 
	with name 'userServiceImpl': Injection of resource dependencies failed;

	org.springframework.beans.factory.BeanCreationException: Error creating bean 
	with name 'userServiceImpl': Injection of resource dependencies failed; nested
	 exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No 
	qualifying bean of type [com.blog.dao.UserDao] found for dependency 
	[com.blog.dao.UserDao]: expected at least 1 bean which qualifies as autowire
	 candidate for this dependency. Dependency annotations: 
	 {@javax.annotation.Resource(shareable=true, lookup=, name=, description=, 
	 authenticationType=CONTAINER, type=class java.lang.Object, mappedName=)}
	 
	遇到 了一个依赖异常，也是mybatis在spring中未能创建bean的异常，因为配置的问题。  
	这个问题一开始遇到则进行了一次狂对比，跟之前项目。后来无果则静下心来，从原理出发，  
	得到配置文件的问题。如下  
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">  
		<property name="basePackage" value="com.blog.dao" />  
		<property name="sqlSessionFactory" ref="sqlSessionFactory" />  
	</bean>  
	property name="basePackage" 中value值填的是接口包名，跟映射xml无关  
	原理是此配置是将mybatis实现的接口注入到spring容器中
	学习链接：
	注解：https://blog.csdn.net/wchazm/article/details/82870973
	基础：https://www.cnblogs.com/honger/p/9519300.html
		 http://www.bubuko.com/infodetail-2042251.html
	
* 2、Invalid bound statement (not found): com.blog.dao.UserDao.getTest2
	
# 测试结果  

* 数据库的连接测试    7/9 User

	Creating a new SqlSession
	SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5a466a07] was 	not registered for synchronization because synchronization is not active
	JDBC Connection [com.mysql.jdbc.JDBC4Connection@259d9139] will not be managed by 	Spring
	==>  Preparing: SELECT * FROM user WHERE username=? 
	==> Parameters: root(String)
	<==    Columns: id, username, password
	<==        Row: 1, root, 123456
	<==      Total: 1
	Closing non transactional SqlSession 	[org.apache.ibatis.session.defaults.DefaultSqlSession@5a466a07]
	username:[id:1, username:root, password:123456]

# 日志

* 2020-mar-10  

	* 新增完善    
		1、标签管理页有点问题
			org.apache.ibatis.binding.BindingException: Invalid bound statement (not found): 			com.blog.dao.ATagDao.count
			解决 mapper文件中没有添加count查询
			
		2、AdministratorsControl.list方法中将admin的find查询方法将其改为多表查询并且Admin接收多个表
			改善了多处，详情见代码
		3、jdbcType String报错 查一下规范的写法
			归纳jdbcType都有哪些
			正确写法是什么
			解决：已经总结到了batis学习文本中 
			正确的写法是全大写
		4、com.blog.service.BasiService.count(String tableStatement, String eq)
			新增了此方法
	 		查询数量  
			tableStatement 自定义表
			eq 预置了‘where 1 = 1 and ’ ，eq的值，例如：‘id = 123456’
		5、娱乐城项目中的QueryHelper和MyBlog项目中的QueryHelper类同步和完善
			进行同步
			完善原有属性名替换新属性名方法（addCloumnAlias），修复bug 
		6、完善分页查询
			AdministratorsControl
	* 看看模板有什么可以改进   
	
	* 没有接口文档 难办，曾经的研究成果白费，又得重新看一遍。    
		比如QueryHelper：  
		获取where语句   
			_.buildAllQuery(page);
		自动绑定request中的参数  
			_.paramBind(request, page);
		属性名替换
			_.addCloumnAlias(key, value)
			key 前端传过来的属性名
			value 新的属性名，将会替换原有属性名
			例：_.addCloumnAlias("createDate", "create_date");
			防止前端的属性名和分隔符'_'冲突   
		前台传来的无效值  
			_.addDisableSelect（key, value）  
			key 前台传过来的属性名  
			value 属性名对应的值  
			比如说select选框什么都没选会传过来-1 此时是无效的，不加以处理会以-1为条件去查询
		条件运算符
			/** 等于 */
			eq, 
			/** 不等于 */
			ne, 
			/** 大于 */
			gt, 
			/** 小于 */
			lt, 
			/** 大于等于 */
			ge, 
			/** 小于等于 */
			le, 
			/** 相似 */
			lk,
			/** 类似于aaaa% */
			slk, 
			/** 包含 */
			in, 
			/** 为Null */
			is, 
			/** 不为Null */
			no;
			在Operator类中
		在前端传递查询条件  
			Qu_type_eq_s = ''
			每个语义以分割线隔开
			Qu 前缀
			tyep 属性
			eq 语义：=
			s 语义：字符串
		后台添加查询条件 
			page.addFilter(new Filter("a.id"
				, com.blog.Filter.Operator.eq
				, "b.admin_id"
				, Filter.IGNORE_TYPE)); // Qu_type_eq_s
			
			
			
			
			
			