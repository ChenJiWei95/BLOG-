<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <div class="layui-container cartlist-cnt" style="padding-top: 20px;">
  	<c:forEach begin="0" items="${articles}" step="1" var="Article" varStatus="varsta">
  	<div class="layui-card note-item" data-id="${Article.id}">
  		<div class="layui-card-header">
			<label class="name" style="display: block; float: left; font-size: 14px; font-weight: bold; color: #555;"
			>${Article.name}</label>
			<label style="display: block; float: right; color:#999;">
	  			${Article.update_time}<i class="layui-icon layui-icon-log" style="margin-left: 6px;position: unset"></i>
	  		</label> 
		</div>
  		<div class="layui-card-body" style="overflow: hidden;">
  			<!-- 内容中转 -->
  			<xmp class="content layui-hide">${Article.simp_desc}</xmp>
  			<!-- 编译之后的内容 --> 
  			<div class="mark_code" style="margin-bottom: 8px; height: 156px; "></div>
  			<label class="tags-value layui-hide">${Article.tags}</label>
  			<!-- 私有还是公开 暂存 -->
  			<label class="status layui-hide">${Article.status}</label>
  			<label class="item-tag"></label>
			
  		</div> 
	</div>
	</c:forEach>
  </div>