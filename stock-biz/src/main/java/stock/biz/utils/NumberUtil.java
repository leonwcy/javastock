package stock.biz.utils;

public class NumberUtil {
	
	public static final Integer toInteger(String s){
		return toInteger(s, null);
	}
	
	public static final Integer toInteger(String s, Integer deafaultValue){
		try {
			return Integer.parseInt(s);
		}catch(Exception e) {
			return deafaultValue;
		}
	}
	
	public static final Long toLong(String s){
		return toLong(s, null);
	}
	
	public static final Long toLong(String s, Long deafaultValue){
		try {
			return Long.parseLong(s);
		}catch(Exception e) {
			return deafaultValue;
		}
	}

}
