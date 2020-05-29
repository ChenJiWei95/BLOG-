<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <div class="more-oprate" style="right: 30px; top: 30px;">
  	<ul class="nolist">
		<li title="查找" click-event="search"> 
			<i class="layui-icon layui-icon-search"></i>
			<form action="show.chtml?type=1" id="selectByName" method="post" enctype="application/x-www-form-urlencoded">
			<div class="li-cnt layui-form" action="show.chtml">
				<div style="width: 400px;">
				<span class="more-oprate-close" click-event="moreOprateClose">×</span>
				<div class="layui-form-item" style="margin-top: 20px;">
					<label class="layui-form-label">名称</label>
					<div class="layui-input-inline" style="width: 250px;">
						<input type="text" name="Qu_a#name_lk_s" placeholder="请日志名称" autocomplete="off" class="layui-input">
					</div> 
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">选择时间</label>
					<div class="layui-input-inline" style="width: 120px;">
						<input 
							type="text" 
							name="Qu_a#createDate_ge_s" 
							placeholder="请输入开始时间" 
							id="date" 
							autocomplete="off" 
							style="width: 120px;" 
							class="layui-input">
					</div> 
					<div class="layui-input-inline" style="width: 120px;">
						<input 
							type="text" 
							name="Qu_a#createDate_le_s" 
							placeholder="请输入结束时间" 
							id="date1" 
							autocomplete="off" 
							style="width: 120px;" 
							class="layui-input">
					</div>
				</div>  
				<div class="layui-form-item" style="margin-bottom: 20px;">
					<button 
						lay-submit 
						lay-filter="C-btn-search" 
						id="C-btn-search" 
						class="layui-btn c-button" 
						click-event="selectByName"
						style="float: right; margin-right: 60px;">确认</button>
				</div>
				</div>
			</div>
			</form>
		</li> 
		<li title="标签查找" click-event="search">
			<i class="layui-icon layui-icon-note"></i>
			<form action="show.chtml?type=2" method="post" id="selectByTab" enctype="application/x-www-form-urlencoded">
			<div class="li-cnt layui-form" action="show.chtml" style="">
				<div style="width: 400px;">
				<span class="more-oprate-close" click-event="moreOprateClose">×</span>
				<div class="layui-form-item">
					<label class="layui-form-label">筛选标签</label>
					<div class="layui-input-inline" style="width: 250px;"> 
						<c:forEach begin="0" items="${all}" step="1" var="Data" varStatus="varsta">
						   	 <input type="checkbox" name="${Data.id}" lay-skin="primary" title="${Data.name}">
						</c:forEach>
					</div>
				</div>
				<div class="layui-form-item" style="margin-bottom: 20px;">
					<button 
						lay-submit 
						lay-filter="C-btn-search2" 
						id="C-btn-search" 
						class="layui-btn c-button" 
						click-event="selectByTab"
						style="float: right; margin-right: 60px;">确认</button>
					<input type="submit" id="submit2" class="layui-hide"/>
				</div>
				</div>
			</div>
			</form>
		</li>
		<li title="添加" click-event="add">
			<i class="layui-icon layui-icon-add-circle-fine"></i>
		</li>
		<li title="TOP" class="li-top" click-event="top" style="/* display: none; */">
			<i class="layui-icon layui-icon-upload-circle"></i>
		</li>
	</ul>
  	 
  </div>