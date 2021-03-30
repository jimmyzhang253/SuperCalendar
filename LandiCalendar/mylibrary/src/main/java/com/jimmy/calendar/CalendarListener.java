package com.jimmy.calendar;

import com.jimmy.calendar.bean.CalendarBean;

/**
 * Created by zhangjie on 2019/3/11
 */
public interface CalendarListener {
    /**
     * 获取时间
     * @param weekBean
     */
    void onSelectDate(CalendarBean weekBean);

}
