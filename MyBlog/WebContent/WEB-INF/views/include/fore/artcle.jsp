<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
		<!-- 导航栏 -->
		<div class="article-done simple-done">
			<div class="nav-article-done simple-nav-done">
				<label class="label-sele-css" blog-event="nvaClick" data-page="con-1"  data-type="2">最新随笔</label>
				<label class="label-unsele-css" blog-event="nvaClick" data-page="con-2" data-type="2">最热随笔</label>
			</div>
			<div class="con-article-done simple-content-done" style="height: 307px;">
				<div class="cle-f con-article-page" id="con-1">
					<ul class="nolist"> 
						<c:forEach begin="0" items="${articles}" step="1" var="Article" varStatus="varsta">
						<li>
							<img title="点击跳转至${Article.name}" data-id="${Article.id}" class="upload-piture" src="${Article.pit_url}" alt="" width="302" height="207" click-event="toDetails"/>
							<div class="desc"><div><div class="desc-title">${Article.name}</div><div class="desc-con">${Article.simp_desc}</div></div></div>
							<div class="interacte">
								<label>
									<i class="fa-browse"></i>
									<span>${Article.browse_num}</span>
								</label>
								<label>
									<i class="fa-heart"></i>
									<span>${Article.like_num}</span>
								</label>
								<label>
									<i class="fa-chat"></i>
									<span>${Article.chat_num}</span>
								</label>
							</div>
						</li>
						</c:forEach> 
					</ul>
				</div>
				<div class="cle-f con-article-page layui-hide" id="con-2"></div>
			</div>
		</div>