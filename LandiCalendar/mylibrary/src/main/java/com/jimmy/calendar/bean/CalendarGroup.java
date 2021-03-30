package com.jimmy.calendar.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangjie on 2019/3/11
 */
public class CalendarGroup implements Serializable {
    private String title;
    private List<CalendarBean> calendarBeanList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public List<CalendarBean> getCalendarBeanList() {
        return calendarBeanList;
    }

    public void setCalendarBeanList(List<CalendarBean> calendarBeanList) {
        this.calendarBeanList = calendarBeanList;
    }
}
