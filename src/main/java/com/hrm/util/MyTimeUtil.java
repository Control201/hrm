package com.hrm.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName MyTimeUtil
 * @Description 计算时间工具类
 * @Version 1.0
 **/
public class MyTimeUtil {
    public static final long VALID_TIME_3_MIN = 180000;  //3分钟,180s
    public static final long VALID_TIME_5_MIN = 300000;  //5分钟,500s
    public static final long VALID_TIME_10_MIN = 600000;  //10分钟,600s

    /**
     * @Description 获取当前时间，返回毫秒级时间
     * @Param []
     **/
    public static long getTime() {
        //在获取现在的时间
        Calendar calendar = Calendar.getInstance();
        Long date = calendar.getTime().getTime();            //获取毫秒时间
        return date;
    }

    /**
     * @Description 比较当前时间与指定时间timeComparedTo差值是否超过了validTime
     * @Param [timeComparedTo, validTime]
     **/
    public static boolean cmpTime(long timeComparedTo, long validTime) {
        //在获取现在的时间
        Calendar calendar = Calendar.getInstance();
        Long timeNow = calendar.getTime().getTime();            //获取毫秒时间
        if (timeNow - timeComparedTo > validTime) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @Description 根据生日计算年龄
     * @Param [birthDay]
     * @return int
     **/
    public static int getAgeByBirth(Date birthDay){
        int age = 0;
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }

    /**
     * @Description 将短时间格式时间转换为字符串 yyyy-MM-dd
     * @Param [dateDate]
     * @return java.lang.String
     **/
    public static String dateToStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * @Description 将短时间格式字符串转换为时间 yyyy-MM-dd
     * @Param [strDate]
     * @return java.util.Date
     **/
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static void main(String[] args) {
        System.out.println(MyTimeUtil.strToDate("2010-1-1").toString());
    }
}
