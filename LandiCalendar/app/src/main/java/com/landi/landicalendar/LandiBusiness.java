package com.landi.landicalendar;

import android.content.Context;
import android.content.Intent;

import com.landi.landicalendar.day.DayActivity;
import com.landi.landicalendar.day.DayFutureActivity;
import com.landi.landicalendar.month.MonthActivity;
import com.landi.landicalendar.week.WeekActivity;

/**
 * 时间选择器管理类
 * Created by zhangjie on 2019/3/11
 */
public class LandiBusiness {
    private static LandiBusiness instance = null;

    private LandiBusiness() {
    }

    public static LandiBusiness getInstance() {
        if (instance == null) {
            synchronized (LandiBusiness.class) {
                if (instance == null) {
                    instance = new LandiBusiness();
                }
            }
        }
        return instance;
    }

    /**
     * 启动
     *
     * @param context
     * @param listener
     */
    public void startCalendarActivity(Context context, CalendarType type, CalendarListener listener) {
        if (listener != null) {
            switch (type) {
                case DAY:
                    startDayActivity(context, listener);
                    break;
                case WEEK:
                    startWeekActivity(context, listener);
                    break;
                case MONTH:
                    startMonthsActvity(context, listener);
                    break;
                case DOUBLE_DAY:
                    startDayActivity(context, true, listener);
                    break;
            }

        }
    }

    /**
     * 启动未来式日期区间选择
     *
     * @param context
     * @param startTime
     * @param endTime
     * @param listener
     */
    public void startDayFutureActivity(Context context, String startTime, String endTime, CalendarListener listener) {
        DayFutureActivity.startDayFutureActivity(context, startTime, endTime, listener);
    }

    private void startDayActivity(Context context, CalendarListener listener) {
        Intent intent = new Intent(context, DayActivity.class);
        DayActivity.setCalendarListener(listener);
        context.startActivity(intent);
    }

    private void startDayActivity(Context context, boolean isInterval, CalendarListener listener) {
        Intent intent = new Intent(context, DayActivity.class);
        intent.putExtra("TYPE", isInterval);
        DayActivity.setCalendarListener(listener);
        context.startActivity(intent);
    }

    private void startWeekActivity(Context context, CalendarListener listener) {
        Intent intent = new Intent(context, WeekActivity.class);
        WeekActivity.setCalendarListener(listener);
        context.startActivity(intent);
    }

    private void startMonthsActvity(Context context, CalendarListener listener) {
        Intent intent = new Intent(context, MonthActivity.class);
        MonthActivity.setCalendarListener(listener);
        context.startActivity(intent);
    }

}
