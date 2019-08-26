package com.landi.landicalendar.utils;

import com.landi.landicalendar.bean.CalendarBean;
import com.landi.landicalendar.bean.CalendarGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LandiDateUtils {

    public static class CalendarDateRange {
        private Date mStartDate;
        private Date mEndDate;

        public CalendarDateRange(Date startDate, Date endDate) {
            mStartDate = startDate;
            mEndDate = endDate;
        }

        public Date getStartDate() {
            return mStartDate;
        }

        public void setStartDate(Date startDate) {
            mStartDate = startDate;
        }

        public Date getEndDate() {
            return mEndDate;
        }

        public void setEndDate(Date endDate) {
            mEndDate = endDate;
        }
    }

    public static List<CalendarDateRange> getAllWeeksInMonth(int year, int month) {
        ArrayList<CalendarDateRange> weekDateRanges = new ArrayList<CalendarDateRange>();

        Calendar startCalendar = getFirstMondayInMonth(year, month);
        int maxWeeks = getMaxWeeks(year, month);
        Date startDate = startCalendar.getTime();
        Date endDate;
        for (int i = 0; i < maxWeeks; ++i) {
            if (startCalendar.get(Calendar.MONTH) != month) {
                break;
            }
            startCalendar.add(Calendar.DAY_OF_YEAR, 6);
            endDate = startCalendar.getTime();
            //添加一周
            CalendarDateRange weekDateRange = new CalendarDateRange(startDate, endDate);
            weekDateRanges.add(weekDateRange);
            //移到下一天
            startCalendar.add(Calendar.DAY_OF_YEAR, 1);
            startDate = startCalendar.getTime();
        }
        return weekDateRanges;
    }

    private static Calendar getFirstMondayInMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 每周从周一开始
        cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
        cal.setMinimalDaysInFirstWeek(7); // 设置每周最少为7天
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        return cal;
    }


    private static int getMaxWeeks(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 每周从周一开始
        cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
        cal.setMinimalDaysInFirstWeek(7); // 设置每周最少为7天
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }

    public static String[] getYearMonthDay(String time) {
        return time.split("-");
    }

    public static int getYearNow() {
        Date todayDate = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(todayDate);//设置当前日期
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonthNow() {
        Date todayDate = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(todayDate);//设置当前日期
        return calendar.get(Calendar.MONTH);
    }

    public static int getDayNow() {
        Date todayDate = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(todayDate);//设置当前日期
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /*********************月份*************/
    public static Calendar getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        // 获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        return cal;
    }

    /**
     * 获得该月最后一天
     *
     * @param month
     * @return
     */
    public static Calendar getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        // 获取某月最大天数
        if (month == 1) {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, 2, 1);
            cal.add(Calendar.DAY_OF_YEAR, -1);
        } else {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            int lastDay = 0;
            lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            // 设置日历中月份的最大天数
            cal.set(Calendar.DAY_OF_MONTH, lastDay);
        }
        return cal;
    }

    public static List<CalendarDateRange> getMonthList(int year) {
        List<CalendarDateRange> calendarDateRanges = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Calendar first = getFirstDayOfMonth(year, i);
            Calendar second = getLastDayOfMonth(year, i);
            CalendarDateRange calendarDateRange = new CalendarDateRange(first.getTime(), second.getTime());
            calendarDateRanges.add(calendarDateRange);
        }
        return calendarDateRanges;
    }

    /**
     * 默认一年
     *
     * @return
     */
    public static List<CalendarGroup> getDayList(int maxNum) {
        int year = LandiDateUtils.getYearNow();
        int month = LandiDateUtils.getMonthNow();
        List<CalendarGroup> calendarGroupList = new ArrayList<>();
        for (int i = 0; i < maxNum; i++) {
            List<CalendarBean> calendarBeanList = new ArrayList<>();
            CalendarGroup calendarGroup = new CalendarGroup();
            Calendar first = getFirstDayOfMonth(year, month);
            Calendar second = getLastDayOfMonth(year, month);
            int nullNum = first.get(Calendar.DAY_OF_WEEK);
            int maxDay = second.get(Calendar.DAY_OF_MONTH);
            for (int j = 0; j < nullNum - 1; j++) {
                calendarBeanList.add(new CalendarBean());
            }
            for (int z = 0; z < maxDay; z++) {
                CalendarBean calendarBean = new CalendarBean();
                calendarBean.setToday(LandiDateUtils.isToday(year, month, z + 1));
                calendarBean.setFuture(LandiDateUtils.isFuture(year, month, z + 1));
                calendarBean.setDay(z + 1);
                calendarBean.setMonth(month + 1);
                calendarBean.setYear(year);
                calendarBean.setStartDate((month + 1) + "." + (z + 1));
                calendarBean.setEndDate((month + 1) + "." + (z + 1));
                calendarBeanList.add(calendarBean);
            }
            calendarGroup.setTitle(year + "年" + (month + 1) + "月");
            calendarGroup.setCalendarBeanList(calendarBeanList);
            calendarGroupList.add(0, calendarGroup);
            if (month == 0) {
                year--;
                month = 11;
            } else {
                month--;
            }
        }
        return calendarGroupList;
    }

    public static List<CalendarGroup> getDayList(String startTime, String endTime) {
        String[] start = startTime.split("-");
        String[] end = endTime.split("-");
        int startYear = Integer.parseInt(start[0]);
        int startMonth = Integer.parseInt(start[1]) - 1;
        int endYear = Integer.parseInt(end[0]);
        int endMonth = Integer.parseInt(end[1]) - 1;
        int maxNum = getDifference(startTime, endTime);
        List<CalendarGroup> calendarGroupList = new ArrayList<>();
        for (int i = 0; i < maxNum; i++) {
            List<CalendarBean> calendarBeanList = new ArrayList<>();
            CalendarGroup calendarGroup = new CalendarGroup();
            Calendar first = getFirstDayOfMonth(startYear, startMonth);
            Calendar second = getLastDayOfMonth(startYear, startMonth);
            int nullNum = first.get(Calendar.DAY_OF_WEEK);
            int maxDay = second.get(Calendar.DAY_OF_MONTH);
            for (int j = 0; j < nullNum - 1; j++) {
                calendarBeanList.add(new CalendarBean());
            }
            for (int z = 0; z < maxDay; z++) {
                CalendarBean calendarBean = new CalendarBean();
                calendarBean.setToday(LandiDateUtils.isToday(startYear, startMonth, z + 1));
                calendarBean.setFuture(LandiDateUtils.isFuture(startYear, startMonth, z + 1));
                calendarBean.setDay(z + 1);
                calendarBean.setMonth(startMonth + 1);
                calendarBean.setYear(startYear);
                calendarBean.setStartDate((startMonth + 1) + "." + (z + 1));
                calendarBean.setEndDate((startMonth + 1) + "." + (z + 1));
                calendarBeanList.add(calendarBean);
            }
            calendarGroup.setTitle(startYear + "年" + (startMonth + 1) + "月");
            calendarGroup.setCalendarBeanList(calendarBeanList);
            calendarGroupList.add(calendarGroup);
            if (startMonth == 11) {
                startYear++;
                startMonth = 0;
            } else {
                startMonth++;
            }
        }
        return calendarGroupList;
    }

    /**
     * 获取相差的月数
     *
     * @param start
     * @param end
     * @return
     */
    public static int getDifference(String start, String end) {
        int result = 0;
        int month = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Calendar bef = Calendar.getInstance();
            Calendar aft = Calendar.getInstance();
            bef.setTime(sdf.parse(start));
            aft.setTime(sdf.parse(end));
            result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
            month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Math.abs(month + result);
    }

    public static boolean isToday(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) == month && calendar.get(Calendar.DAY_OF_MONTH) == day) {
            return true;
        }
        return false;
    }

    public static boolean isFuture(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.YEAR) < year ||
                (calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) == month && calendar.get(Calendar.DAY_OF_MONTH) < day) ||
                (calendar.get(Calendar.YEAR) <= year && calendar.get(Calendar.MONTH) < month)) {
            return true;
        }
        return false;
    }

    public static boolean isBefore(String startDate, String endDate) {
        boolean result = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date dateStart = sdf.parse(startDate);
            Date dateEnd = sdf.parse(endDate);
            Calendar startCalendar = Calendar.getInstance(Locale.CHINA);
            startCalendar.setTime(dateStart);
            Calendar endCalendar = Calendar.getInstance(Locale.CHINA);
            endCalendar.setTime(dateEnd);
            result = startCalendar.before(endCalendar);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isAfter(String startDate, String endDate) {
        boolean result = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date dateStart = sdf.parse(startDate);
            Date dateEnd = sdf.parse(endDate);
            Calendar startCalendar = Calendar.getInstance(Locale.CHINA);
            startCalendar.setTime(dateStart);
            Calendar endCalendar = Calendar.getInstance(Locale.CHINA);
            endCalendar.setTime(dateEnd);
            result = startCalendar.after(endCalendar);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isEquals(String startDate, String endDate) {
        boolean result = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date dateStart = sdf.parse(startDate);
            Date dateEnd = sdf.parse(endDate);
            Calendar startCalendar = Calendar.getInstance(Locale.CHINA);
            startCalendar.setTime(dateStart);
            Calendar endCalendar = Calendar.getInstance(Locale.CHINA);
            endCalendar.setTime(dateEnd);
            result = startCalendar.equals(endCalendar);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getNearlyStr(int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        if (days < 0) {
            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + days);
        } else {
            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) - days);
        }
        return getDay(c.getTime().getTime());
    }

    public static String getNearlyStrFuture(int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + days);
        return getDay(c.getTime().getTime());
    }

    public static String getDay(long time) {
        return new SimpleDateFormat("yyyy.MM.dd").format(time);
    }
}
