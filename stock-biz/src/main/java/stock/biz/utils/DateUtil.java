package stock.biz.utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	
	//2001-07-04T12:08:56.235-07或2001-07-04T12:08:56.235Z
    public static final String PATTERN_ISO8601_ONELETTER = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
    //2001-07-04T12:08:56.235-0700或2001-07-04T12:08:56.235Z
	public static final String PATTERN_ISO8601_TWOLETTER = "yyyy-MM-dd'T'HH:mm:ss.SSSXX";
	//2001-07-04T12:08:56.235-07:00或2001-07-04T12:08:56.235Z
	public static final String PATTERN_ISO8601_THREELETTER = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
	
	//2001-07-04T12:08:56.235-0700或2001-07-04T12:08:56.235+0000
	public static final String PATTERN_RFC822 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	
	public static final String DEFAULT_TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_TIMESTAMP_PATTERN_NO_SEONCDS = "yyyy-MM-dd HH:mm";
	public static final String DEFAULT_TIMESTAMP_PATTERN_NO_SEONCDS2 = "yyyy-MM-dd HHmm";
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
	public static final String DEFAULT_TIME_PATTERN_NO_SEONCDS = "HH:mm";
	
	public static final String DEFAULT_TIME_ZONE_ID = TimeZone.getDefault().getID();
	
	private static final DatatypeFactory dtf;
	
	static {
		 try {
			dtf = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Date parseTimestampWithoutSeconds(String s) {
		return parse(s, DEFAULT_TIMESTAMP_PATTERN_NO_SEONCDS);
	}
	
	public static Date parseTimestamp(String s) {
		return parse(s, DEFAULT_TIMESTAMP_PATTERN);
	}
	
	public static Date parseDate(String s) {
		return parse(s, DEFAULT_DATE_PATTERN);
	}
	
	public static Date parseTime(String s) {
		return parse(s, DEFAULT_TIME_PATTERN);
	}
	
	public static Date parseTimeWithoutSeconds(String s) {
		return parse(s, DEFAULT_TIME_PATTERN_NO_SEONCDS);
	}
	
	public static Date parse(String s, String pattern) {
		if(StringUtil.isNullorEmpty(s) || StringUtil.isNullorEmpty(pattern)) {
			return null;
		}
		
		try {
			return new SimpleDateFormat(pattern).parse(s);
		} catch (ParseException e) {
			//ignore
			return null;
		}
	}
	
	public static String formatTimestamp(Date date) {
		return format(date, DEFAULT_TIMESTAMP_PATTERN);
	}
	
	public static String formatTimestampWithoutSeconds(Date date) {
		return format(date, DEFAULT_TIMESTAMP_PATTERN_NO_SEONCDS);
	}
	
	public static String toDate(Date date) {
		return format(date, DEFAULT_DATE_PATTERN);
	}
	
	public static String toTime(Date date) {
		return format(date, DEFAULT_TIME_PATTERN);
	}
	
	public static String formatTimeWithoutSeconds(Date date){
		return format(date, DEFAULT_TIME_PATTERN_NO_SEONCDS);
	}
	
	public static String format(Date date, String pattern) {
		if(date == null || StringUtil.isNullorEmpty(pattern)) {
			return null;
		}
		return new SimpleDateFormat(pattern).format(date);
	}
	
	public static Timestamp GetCurrentTimeStamp()
	{
		return new Timestamp(new Date().getTime());
	}
	
	public static Date minInDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static java.sql.Date toSqlDate(Date date){
		return new java.sql.Date(date.getTime());
	}
	
	public static java.sql.Time toSqlTime(Date date){
		return new java.sql.Time(date.getTime());
	}
	
	public static Timestamp toSqlTimeStamp(Date date, Date time){
		Calendar tmp = Calendar.getInstance();
		tmp.setTime(time);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, tmp.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, tmp.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, tmp.get(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, tmp.get(Calendar.MILLISECOND));

		return new Timestamp(calendar.getTimeInMillis());
	}

	public static Timestamp toSqlTimeStamp(Date date){
		return new Timestamp(date.getTime());
	}

	public static Date toUtilDate(java.sql.Date date){
		return new Date (date.getTime());
	}
	
	public static XMLGregorianCalendar toXMLGregorianCalendar(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
	    XMLGregorianCalendar ret =  dtf.newXMLGregorianCalendar(
	    	calendar.get(Calendar.YEAR),
	        calendar.get(Calendar.MONTH) + 1,
	        calendar.get(Calendar.DAY_OF_MONTH),
	        calendar.get(Calendar.HOUR_OF_DAY),
	        calendar.get(Calendar.MINUTE),
	        calendar.get(Calendar.SECOND),
	        calendar.get(Calendar.MILLISECOND),
	        calendar.get(Calendar.ZONE_OFFSET)/(1000*60));
	    
	    return ret;
	}
	
	public static Date toDate(XMLGregorianCalendar xmlGregorianCalendar){
		return xmlGregorianCalendar.toGregorianCalendar().getTime();  
	}
	
	public static boolean isSameDay(Date day1, Date day2) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String ds1 = sdf.format(day1);
	    String ds2 = sdf.format(day2);
	    if (ds1.equals(ds2)) {
	        return true;
	    } else {
	        return false;
	    }
	}
	
    /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static long daysBetween(Date smdate,Date bdate)
    {          
        long time1 = minInDay(smdate).getTime();  
        long time2 = minInDay(bdate).getTime();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return between_days;           
    } 
}
