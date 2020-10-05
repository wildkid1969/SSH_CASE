package com.test.base;

import org.apache.commons.lang.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/*
 * @author  MengYa
 * @date  2020/4/16 12:24
 *
 */
public class TestDate {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date now = cal.getTime();

        System.out.println(now);

        Date date = DateUtils.addDays(now, 5);
        System.out.println(date);
        System.out.println(now.before(date));

        Calendar cal1 = Calendar.getInstance();
        cal1.set(1970, 0, 01, 0, 0, 0);
        System.out.println(cal1.getTime());

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(now);
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 59);
        date = cal2.getTime();
        System.out.println(date);

        Pattern p = Pattern.compile("^1[3-9]\\d{9}$");
        System.out.println("phone:"+p.matcher("00099999992").matches());

        long diff = date.getTime() - System.currentTimeMillis();
        Long days = diff / (1000 * 60 * 60 * 24);
        System.out.println(days.intValue());

        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
        System.out.println(sdf.format(date));

        System.out.println(new Date(-28800000));
        Date one = new Date(-28800000);
        System.out.println(one.getTime()==-28800000);

        Calendar cal3 = Calendar.getInstance();
        cal3.set(1970, 0, 01, 0, 0, 0);
        cal3.set(Calendar.MILLISECOND,0);
        Date firstYear = cal3.getTime();
        System.out.println(firstYear);

    }
}
