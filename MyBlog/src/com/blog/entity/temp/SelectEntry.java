package com.blog.entity.temp;

public class SelectEntry extends CompEntity {
	
	public String toString() {
		// 输出自身特有的 super.toString() 继承公共的 
		// 规定option value ==》 id  label ==》 name 
		return super.toString(	"<select name=\"role_id\">" + 
								"        	<option value=\"-1\">请选择"+this.getLabel()+"</option>" + 
								"         	<c:forEach begin=\"0\" items=\"${"+this.getName()+"s}\" step=\"1\" var=\""+firstUp(this.getName())+"\" varStatus=\"varsta\">" + 
								"				<option value=\"${"+firstUp(this.getName())+".id}\">${\"+firstUp(this.getName())+\".name}</option>" + 
								"			</c:forEach>" + 
								"   	</select>");
	}
	private String firstUp(String str) {
		return str.toUpperCase().substring(0, 1) + str.substring(1);
	}
	public static void main(String[] args) {
		String str = "name";
		System.out.println(str.toUpperCase().substring(0, 1) + str.substring(1));
	}
}
