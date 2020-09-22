package com.blog.jstl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class SimplePageTag extends SimpleTagSupport {
	/** 目标页 */
	private Integer pageIndex;
	/** 默认 10 */
	private Integer pageSize = 10;
	/** 关联class名称 */
	private String styleClass;
	/** 请求地址 */
	private String action;
	
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		/*
		 <div class="simp-async-page-btn" style="" click-event="callback" data-pageSize=10, data-index=0>
					<font style="color: #fff;">加载更多</font>
				</div>
		 */
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='"+styleClass+"' click-event=\"callback\" data-page-size="+pageSize+" data-action='"+action+"' data-index="+pageIndex+">")
		.append("<font style=\"color: #fff;\">加载更多</font>")
		.append("</div>");
		out.println(sb.toString());
	}
}
