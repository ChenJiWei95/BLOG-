package com.blog.entity.temp;

public class Tst {
	private static TempContext context;
	public static void main(String[] args) {
		ComponentSet set = new SearchSet();
		ItextEntry text = new ItextEntry();
		text.setName("id");
		text.setDisable(true);
//		text.setInline(true);
		text.setLabel("ID");
		set.add(text);
		
		SelectEntry select = new SelectEntry();
		select.setName("state");
//		select.setInline(true);
		select.setLabel("状态");
		StringBuilder sb = new StringBuilder();
		sb.append("<select name=\"state\">");
		sb.append("<option value=\"01\">禁用</option>");
		sb.append("<option value=\"00\">启用</option>");
		sb.append("</select>");
		select.setHtml(sb.toString());
		set.add(select);
		
		context = new TempContext();
		context.setSearchSet(set);
		System.out.println(set.toString());
	}
}
