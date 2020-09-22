package com.blog.test;

public class Example { 
	int x = 0;
	public class Runner implements Runnable{
		public void run(){
			int current = 0;
			for(int i = 0; i < 4; i++){
				current = x;
				System.out.print(current + ", ");
				x = current + 2;
			}
		}
		
	}
	public int method(int i){
		return 0;
	}
	
	public void go(){
		Runnable r1 = new Runner();
		new Thread(r1).start();
		new Thread(r1).start();
	}
//	long a[] = new long[5];
	public static void main(String[] args){
//		new Example().go();

//		System.out.println(a[4]);
		int i = 10;
		char[] ch = {'a', 'b', 'c'};
		char c = 'b';
		String str = "str";
		boolean flag = false;
		StringBuilder sb = new StringBuilder("adfd");
		Integer integer = 12;
		new Example().method(i, ch, c, str, sb, flag, integer);
		System.out.println(i+" "+j+" ");
		System.out.println(ch);
		System.out.println(c);
		System.out.println(str);
		System.out.println(sb);
		System.out.println(flag);
		System.out.println(integer);
	}
	static int j = 10;
	public void method(int x, char[] ch, char c, String str, StringBuilder sb, boolean flag, Integer integer){
		ch[0] = 'g';
		x = x*2;
		j = j * 2;
		c = 'c'; 
		str = "good";
		sb.append("goods");
		flag = true;
		integer = 14;
	}
	
	
}
class Example2 extends Example{
	public int method(int i) {
		return 0;
	}
	public void method() {
	}
}
