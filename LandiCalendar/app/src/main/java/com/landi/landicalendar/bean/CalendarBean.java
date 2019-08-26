package com.landi.landicalendar.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * 一周的起始日期与结束日期
 * Created by zhangjie on 2019/3/11
 */
public class CalendarBean implements Serializable {
    private boolean isFuture = false;
    private boolean isToday = false;
    private String startDate;
    private String endDate;
    private int day = -1;
    private int year = -1;
    private int month = -1;
    private String time;

    public boolean isFuture() {
        return isFuture;
    }

    public void setFuture(boolean future) {
        isFuture = future;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        if (TextUtils.isEmpty(time)) {
            return getYear() + "." + getMonth() + "." + getDay();
        } else {
            return time;
        }
    }
}
