<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <div class="layui-container cartlist-cnt" style="padding-top: 20px;">
  	<c:forEach begin="0" items="${notes}" step="1" var="Note" varStatus="varsta">
  	<c:if test='${Note.admin_id eq adminId or Note.status ne "01"}'>
  	<div class="layui-card note-item" data-id="${Note.id}">
  		<div class="layui-card-header">
			<label class="name" style="display: block; float: left; font-size: 14px; font-weight: bold; color: #555;"
			>${Note.name}</label>
			<c:if test="${Note.admin_id eq adminId}">
	  		<label style="display: block; float: right; ">
	  			<i class="layui-icon layui-icon-close" click-event="remove" style="font-size: 24px; cursor: pointer;"></i>
	  			<i class="layui-icon layui-icon-edit" click-event="update" style="cursor: pointer; margin-right: 30px; font-size: 24px"></i>
	  			<i class="layui-icon layui-icon-add-circle-fine" click-event="add" style="cursor: pointer; margin-right: 60px; font-size: 24px"></i>
	  		</label>
	  		</c:if>
		</div>
  		<div class="layui-card-body" style="overflow: hidden;">
  			<!-- 内容中转 -->
  			<xmp class="content layui-hide">${Note.content}</xmp>
  			<!-- 编译之后的内容 --> 
  			<div class="mark_code"></div>
  			<label class="tags-value layui-hide">${Note.tags}</label>
  			<label class="status layui-hide">${Note.status}</label>
  			<label class="item-tag"></label>
			<label style="display: block; float: right; ">
	  			上一次修改：<font class="update_date"><c:if test="${empty Note.update_date}">
	  			xxxx-xx-xx xx:xx:xx</c:if><c:if test="${not empty Note.update_date}">${Note.update_date}</c:if></font>
	  		</label>
	  		<label style="display: block; float: right; margin-right: 30px;">
	  			创建时间：<font class="create_date">${Note.create_date}</font>
	  		</label>
  		</div> 
	</div>
	</c:if>
	</c:forEach>
  </div>