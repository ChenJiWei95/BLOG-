package com.blog.test;

public class Test0530 {
	private int valueRateRate = 2;
	private class Content implements Contents {  
        private int i = 11;  
        public int value() {  
        	// 此处证明作用第四点（非静态内部类对象有着指向其外部类对象的引用）
        	// return i * valueRateRate;  
        	// 如果内外两个类变量名相同 还可以这样区分引用外部类的变量
            return i * Test0530.this.valueRateRate;  
        }  
    }  
	private class GDestination implements Destination {  
        private String label;  
        private GDestination(String whereTo) {  
            label = whereTo;  
        }  
        public String readLabel() {  
            return label;  
        }  
    } 
    public Destination dest(String s) {  
    	class GDestination implements Destination {  
	        private String label;  
	        private GDestination(String whereTo) {  
	            label = whereTo;  
	        }  
	        public String readLabel() {  
	            return label;  
	        }  
	    } 	 
        return new GDestination(s);  
    }  
    public Contents cont() {  
        return new Content();  
    }  
    
    public static void main(String[] args) {  
    	Test0530 p = new Test0530();  
    	// 创建内部类的第一种方式：证明了作用中的第三点（隐蔽性） 
        /*Contents c = p.cont();  
        Destination d = p.dest("Beijing");  */
        
    	// 创建内部类的第二种方式：此时创建的话需要实例化的外部类对象，
    	// 为什呢？这里可以看作用的第四点（非静态内部类对象有着指向其外部类对象的引用）
    	Test0530.GDestination dest = p.new GDestination("Beijing");
    	
    	Goods3 g = new Goods3();
    	Contents c = g.cont();
    } 
}  
     
class Goods3 {  
    public Contents cont() {  
        return new Contents() {  
            private int i = 11;  
            public int value() {  
                return i;  
            }  
        };  
    }  
}  
interface Contents {  
    int value();  
}  
interface Destination {  
    String readLabel();  
} 
