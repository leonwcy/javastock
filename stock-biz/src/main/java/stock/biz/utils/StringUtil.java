package stock.biz.utils;

import java.util.regex.Pattern;

public class StringUtil {
	
	public static String trim(String str){
		return trim(str, " ");
	}

	public static String trim(String str,String trimedStr)
	{
		if(str == null) return str;
		
		String patternStr = Pattern.quote(trimedStr);
		StringBuilder regex =  new StringBuilder().append("^(?:").append(patternStr)
				.append(")+|(?:").append(patternStr).append(")+$");
		
		return str.replaceAll(regex.toString(), "");
	}
	
	public static boolean isNullorEmpty(String str)
	{
		  return isNullorEmpty(str, true);
	}
	
	public static boolean equals(String s1, String s2) {
		if(isNullorEmpty(s1) && isNullorEmpty(s2)) {
			return true;
		}
		if(s1 != null && s1.equals(s2)) {
			return true;
		}
		return false;
	}
	
	public static boolean isNullorEmpty(String str, boolean doTrim)
	{
		  if(str==null)
		  {
			  return true;
		  }
		  
		  if(doTrim) {
			  str = trim(str);
		  }
		  if(str.length() == 0)
		  {
			  return true;
		  }
		  
		  return false;
	}
	
	public static boolean contains(String[] strs, String value)
	{
		if(strs == null) return false;
		
		for(int i =0; i<strs.length;i++)
		{
			if(strs[i].equals(value)) 
			{
				return true;
			}
		}
		return false;
	}
	
	public static String emptyToNull(String flightNo) {
		if(flightNo != null && flightNo.isEmpty()) {
			return null;
		}
		
		return flightNo;
	}
	
	public static String getEmailPrefix(String email)
	{
		if(StringUtil.isNullorEmpty(email)) return "";
		
		 int index = email.indexOf("@");
		 if(index>0)
		 {
			 return email.substring(0,index);
		 }else{
			 return email;
		 }
	}
}
