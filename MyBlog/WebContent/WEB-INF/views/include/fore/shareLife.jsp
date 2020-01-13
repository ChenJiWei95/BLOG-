<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
		<!-- 分享简要显示 -->
		<div class="share-done simple-done">
			<div class="nav-share-done simple-nav-done">
				<label class="label-sele-css">生活分享</label>
			</div>
			<div class="con-share-done simple-content-done">
				<div class="cle-f con-share-page">
					<ul class="nolist">
						<c:forEach begin="0" items="${lifes}" step="1" var="LifeShare" varStatus="varsta">
						<li>
							<img title="点击跳转" src="${LifeShare.pit_url}" alt="" width="302" height="207" data-id="${LifeShare.id}" click-event="toLife"/>
							<div class="desc"><div><div class="desc-title"> </div><div class="desc-con">${LifeShare.content}</div></div></div>
							<div class="interacte">
								<label>
									<i class="fa-browse"></i>
									<span>${LifeShare.brow_num}</span>
								</label>
								<label>
									<i class="fa-heart"></i>
									<span>${LifeShare.like_num}</span>
								</label>
								<label>
									<i class="fa-chat"></i>
									<span>${LifeShare.chat_num}</span>
								</label>
							</div>
						</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	