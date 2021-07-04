package com.test.base;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @Author: mengya
 * @Date: 2021/4/27 17:09
 */
public class TestLocalDate {
    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        System.out.println(date);
        System.out.println(date.isLeapYear());

        LocalDate plusYears = date.plusYears(1);
        LocalDate plusMonths = date.plusMonths(1);
        LocalDate plusWeeks = date.plusWeeks(1);
        LocalDate plusDays = date.plusDays(1);

        System.out.println(plusYears+", "+plusMonths+", "+plusWeeks+", "+plusDays);

        LocalDate someDate = LocalDate.of(2021, Month.JANUARY, 15);
        Period period = Period.between(someDate, date);
        System.out.println(period.getDays());


        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(dateTime);
        String format = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(format);

        LocalDateTime plusDays1 = dateTime.plusDays(1);
        System.out.println(plusDays1);

        LocalDateTime plus = dateTime.plus(1, ChronoUnit.WEEKS);
        System.out.println(plus);

        Date toDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(toDate);

        LocalDateTime toLocalDateTime = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println(toLocalDateTime);

        YearMonth yearMonth = YearMonth.of(2021, Month.FEBRUARY);
        System.out.println(yearMonth.lengthOfMonth());


    }
}
