package net.itw.wcms.toolkit;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
//import org.apache.log4j.Logger;
import org.springframework.util.Assert;

public class DateTimeUtils {
//    public static final Logger log = Logger.getLogger(DateTimeUtils.class);

    public final static String FORMAT_yyyy_MM_dd = "yyyy-MM-dd";
    public final static String FORMAT_yyyy_M_d = "yyyy-M-d";

    public final static String FORMAT_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    
    public final static String FORMAT_yyyy_nian_MM_yue_mm_ri = "yyyy年MM月dd日";
    
    public final static String FORMAT_yyyy_nian_M_yue_m_ri = "yyyy年M月d日";
    
    public static void main(String[] args) {
/*        System.out.println(convertChar2ChineseChar("2008-10-20"));
        System.out.println(convertChar2ChineseChar("2008-11-21"));
        System.out.println(convertChar2ChineseChar("2008-01-29"));*/
        String dateStr = "2015-1-1 21:21:28";
        String dateStr2 = "2015-1-2 1:21:28";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try 
        {
            Date date2 = format.parse(dateStr2);
            Date date = format.parse(dateStr);
            
            System.out.println("两个日期的差距：" + differentDays(date,date2));
            System.out.println("两个日期的差距：" + differentDaysByMillisecond(date,date2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    /**日期显示中文
     * 例：
     * 2008-10-20->二〇〇八年十月二十日
     * 2008-11-21->二〇〇八年十一月二十一日
     * 2008-01-29->二〇〇八年一月二十九日
     * @param strDate yyyy-MM-dd
     * @return 中文显示日期
     */
    public static String convertChar2ChineseChar(String strDate) {
        if (StringUtils.isEmpty(strDate)) {
            return "";
        }
        String year = strDate.substring(0, 4);
        String month = strDate.substring(5, 7);
        String day = strDate.substring(8, 10);
        String ret = convertChar2ChineseOne(year) + "年"
            + convertChar2ChineseTwo(month) + "月"
            + convertChar2ChineseTwo(day) + "日";
        return ret;
    }
    
    private static String convertChar2ChineseTwo(String str) {
        int num = Integer.parseInt(str);
        Assert.isTrue(num >= 0 && num <= 99);
        if (num < 10) {
            return convertChar2ChineseOne("" + num);
        }
        int tenNum = Integer.parseInt(str.substring(0, 1));//十位
        int singleNum = Integer.parseInt(str.substring(1));//个位
        String ten = "十";
        if (tenNum > 1) {
            ten = convertChar2ChineseOne("" + tenNum) + ten;
        }
        String single = "";
        if (singleNum > 0) {
            single = convertChar2ChineseOne("" + singleNum);
        }
        return ten + single;
    }
    
    private static String convertChar2ChineseOne(String str) {
        str = str.replace('0', '〇');
        str = str.replace('1', '一');
        str = str.replace('2', '二');
        str = str.replace('3', '三');
        str = str.replace('4', '四');
        str = str.replace('5', '五');
        str = str.replace('6', '六');
        str = str.replace('7', '七');
        str = str.replace('8', '八');
        str = str.replace('9', '九');
        return str;
    }
    
    /**
     * Date转换到Calendar
     * @param date 要转换的Date
     * @return Calendar
     */
    public static Calendar date2Calendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 设置指定的Calendar“时、分、妙”为零
     * @param calendar Calendar
     */
    public static void setTimeZero(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }

    /**
     * 得到当前时间的字符串yyyy-MM-dd
     * @return String
     */
    public static String now2StrDate() {
        return now2Str(FORMAT_yyyy_MM_dd);
    }

    /**
     * 得到当前时间的字符串yyyy-MM-dd HH:mm:ss
     * @return String
     */
    public static String now2StrDateTime() {
        return now2Str(FORMAT_yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 得到当前时间的字符串
     * @param format 字符串格式
     * @return String
     * @see org.apache.commons.lang.time.DateFormatUtils.DateFormatUtils#format(Date, String)
     */
    public static String now2Str(String format) {
        return DateFormatUtils.format(new Date(), format);
    }
    
    /**
     * 得到当前时间的字符串yyyyMMddHHmmss
     * @return
     */
    public static String nowStrDateTime() {
        String date = DateTimeUtils.now2StrDateTime();
        date = date.replace(" ", "");
        date = date.replace("-", "");
        return date.replace(":", "");
    }
    
    /**
     * Date转换到字符串yyyy-MM-dd
     * @param date Date
     * @return String yyyy-MM-dd
     * @see org.apache.commons.lang.time.DateFormatUtils.DateFormatUtils#format(Date, String)
     */
    public static String date2StrDate(Date date) {
        return DateFormatUtils.format(date, FORMAT_yyyy_MM_dd);
    }
    
    /**
     * Date转换到字符串
     * @param date
     * @param format
     * @return
     */
    public static String date2StrDate(Date date, String format) {
        return DateFormatUtils.format(date, format);
    }

    /**
     * Date转换到字符串yyyy-MM-dd HH:mm:ss
     * @param date Date
     * @return String yyyy-MM-dd HH:mm:ss
     * @see org.apache.commons.lang.time.DateFormatUtils.DateFormatUtils#format(Date, String)
     */
    public static String date2StrDateTime(Date date) {
        return DateFormatUtils.format(date, FORMAT_yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * Calendar转换到字符串yyyy-MM-dd
     * @param calendar Calendar
     * @return String yyyy-MM-dd
     * @see org.apache.commons.lang.time.DateFormatUtils.DateFormatUtils#format(Date, String)
     */
    public static String calendar2StrDate(Calendar calendar) {
        return date2StrDate(calendar.getTime());
    }

    /**
     * Calendar转换到字符串yyyy-MM-dd HH:mm:ss
     * @param calendar Calendar
     * @return String yyyy-MM-dd HH:mm:ss
     * @see org.apache.commons.lang.time.DateFormatUtils.DateFormatUtils#format(Date, String)
     */
    public static String calendar2StrDateTime(Calendar calendar) {
        return date2StrDateTime(calendar.getTime());
    }

    /**
     * 字符串yyyy-MM-dd转换到Calendar类型
     * @param dateStr yyyy-MM-dd
     * @return Calendar
     */
    public static Calendar strDate2Calendar(String dateStr) {
        return str2Calendar(dateStr, FORMAT_yyyy_MM_dd);
    }
    
    /**
     * 字符串yyyy-MM-dd转换到Date类型
     * @param dateStr yyyy-MM-dd
     * @return Date
     */
    public static Date strDate2Date(String dateStr) {
        return str2Date(dateStr, FORMAT_yyyy_MM_dd);
    }

    /**
     * 字符串yyyy-MM-dd HH:mm:ss转换到Calendar类型
     * @param dateStr yyyy-MM-dd HH:mm:ss
     * @return Calendar
     */
    public static Calendar strDateTime2Calendar(String dateStr) {
        return str2Calendar(dateStr, FORMAT_yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 字符串yyyy-MM-dd HH:mm:ss转换到Date类型
     * @param dateStr yyyy-MM-dd HH:mm:ss
     * @return Date
     */
    public static Date strDateTime2Date(String dateStr) {
        return str2Date(dateStr, FORMAT_yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 字符串转换到Date类型
     * @param dateStr 需要转换的字符串
     * @param format 转换格式
     * @return Date
     */
    public static Date str2Date(String dateStr, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        Date date = dateFormat.parse(dateStr, new ParsePosition(0));
        return date;
    }

    /**
     * 字符串转换到Calendar类型
     * @param dateStr 需要转换的字符串
     * @param format 转换格式
     * @return Calendar
     */
    public static Calendar str2Calendar(String dateStr, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(str2Date(dateStr, format));
        return calendar;
    }
    
    /** 
     *  得到当前日期的Calendar类型
     * @return Calendar;
     */
    public static Calendar now2Calendar() {
        return Calendar.getInstance();
    }
    
    /** 
     *  得到当前日期的下一天
     * @return Calendar;
     */   
    public static String getNextDay(String dateTime) {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpledate.parse(dateTime);
        } catch (ParseException ex) {
            System.out.println("日期格式不符合要求：" + ex.getMessage());
            return null;
        }
        now.setTime(date);
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH) + 1;
        now.set(year, month, day);
        String time = simpledate.format(now.getTime());
        return time;
    }
   
    /**
    * 计算指定日期的上一天
    * 
    * @param dateTime
    * @日期，格式为：yyyy-MM-dd
    * @return
    */
    public static String getBeforeDay(String dateTime) {
       Calendar now = Calendar.getInstance();
       SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
       Date date = null;
       try {
           date = simpledate.parse(dateTime);
       } catch (ParseException ex) {
           System.out.println("日期格式不符合要求：" + ex.getMessage());
           return null;
       }
       now.setTime(date);
       int year = now.get(Calendar.YEAR);
       int month = now.get(Calendar.MONTH);
       int day = now.get(Calendar.DAY_OF_MONTH) - 1;
       now.set(year, month, day);
       String time = simpledate.format(now.getTime());
       return time;
    }    
    /**：
     * 2008-10-20->2008年10月20日
     */
    public static String convertChar2ChineseChar2(String strDate) {
        if (StringUtils.isEmpty(strDate)) {
            return "";
        }
        String year = strDate.substring(0, 4);
        String month = strDate.substring(5, 7);
        String day = strDate.substring(8, 10);
        String ret = year + "年" + month + "月" + day + "日";
        return ret;
    }
    
    public static String formatDate(String str){
    	  SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
    	     SimpleDateFormat sf2 =new SimpleDateFormat("yyyy-MM-dd");
    	     String sfstr = "";
    	     try {
    	      sfstr = sf2.format(sf1.parse(str));
    	  } catch (ParseException e) {
    	   e.printStackTrace();
    	  }
    	  return sfstr;
	 }
    
	/**
	 * 得到n天前的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static String getDateBefore(int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		now.add(Calendar.DATE,  - day);
		return DateFormatUtils.format(now.getTime(), FORMAT_yyyy_MM_dd_HH_mm_ss);
	}
	
	/**
	 * 得到n天前的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static String getMonthBefore(int month) {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		now.add(Calendar.MONTH,  - month);
		return DateFormatUtils.format(now.getTime(), FORMAT_yyyy_MM_dd_HH_mm_ss);
	}
	
	/**
     * date2比date1多的天数
     * @param date1    
     * @param date2
     * @return    
     */
	public static int differentDays(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) // 同一年
		{
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
				{
					timeDistance += 366;
				} else // 不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2 - day1);
		} else // 不同年
		{
			System.out.println("判断day2 - day1 : " + (day2 - day1));
			return day2 - day1;
		}
	}
	
	/**
	 * 通过时间秒毫秒数判断两个时间的间隔
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDaysByMillisecond(Date date1, Date date2) {
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
		return days;
	}
}
