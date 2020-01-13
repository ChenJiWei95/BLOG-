package com.blog.util;

public class StringUtil {
	/**
	 * 取小数点 四舍五入
	 * <p>	 
	 * @param number
	 * @param count
	 * @return
	 * String
	 * @see
	 * @since 1.0
	 */
	public static String format(Double number, int count){
		return format(String.valueOf(number), count);
	}
	/**
	 * 四舍五入 将有小数点的字符串按照定义的小数点数量处理并返回
	 * 适合纯数字
	 * <p>	 
	 * @param number
	 * @param count
	 * @return
	 * String
	 * @see
	 * @since 1.0
	 */
	public static String format(String number, int count){
		int index = 0;
		if((index = number.indexOf(".")) != -1){
			if(count == 0){ // 没有小数点
				return number.substring(0, index);
			}
			StringBuilder result = new StringBuilder();
			for(int i = index+1; i < number.length(); i++){
				
				if(count == i-(index+1)){
					boolean isFullTen = Integer.parseInt(number.substring(i, i+1)) >= 5;
					return isFullTen ? fullTen(number.substring(0, i)) : number.substring(0, i);}
			}
		}
		return number;
	}
	// 满十进一  四舍五入 
	public static String fullTen(String number){
		int full2 = 1;
		int full1 = 0;
		StringBuilder newNumber = new StringBuilder();
		for(int i = number.length()-1; i >= 0; i--){
			System.out.println("i:"+i);
			char numStr = number.charAt(i); 
			if(numStr >= '0' && numStr <= '9'){// 是数字
				int b = Integer.parseInt(String.valueOf(numStr))+full2;
				full2 = 0;
				if((full1 = b-10) >= 0){// 算出个位数
					full2 = b/10;
					System.out.println("b:"+b);
					System.out.println("full2:"+full2);
				} else full1 = b;
				newNumber.append(full1);
			}else newNumber.append(".");
		}
		StringBuilder newNumber2 = new StringBuilder();
		for(int i = newNumber.length()-1; i >= 0; i--){
			newNumber2.append(newNumber.charAt(i));
		}
		return newNumber2.toString();
	}	
}
