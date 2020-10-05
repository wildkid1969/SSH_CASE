package my.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestDate {

    public static void main(String[] args) {
        Date date1 = new Date(1593602914000L);
        Date date2 = new Date(1593614704000L);
        System.out.println(getBetweenDates(date1, date2));
        System.out.println(date1.compareTo(date2));

        Map<String, List<Course>> map = Maps.newLinkedHashMap();
        Course c = new Course();
        c.setCourseId(2003);
        c.setCourseName("FNT新秀营");
        List<Course> list = Lists.newArrayList(c);

        map.put("2020-07-01",list);
        map.put("2020-07-02",list);
        map.put("2020-07-03",list);
        System.out.println(map);
        System.out.println(getMouthLast(2023,12));

    }

    private static List<Date> getBetweenDates(Date start, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, 1);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    private static Date getMouthLast(int year, int month) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.YEAR, year);
        ca.set(Calendar.MONTH, month-1);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ca.getTime();
    }

    static class Course{
        private Integer courseId;
        private String courseName;

        public Integer getCourseId() {
            return courseId;
        }

        public void setCourseId(Integer courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        @Override
        public String toString() {
            return "Course{" +
                    "courseId=" + courseId +
                    ", courseName='" + courseName + '\'' +
                    '}';
        }
    }
}
