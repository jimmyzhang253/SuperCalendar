package com.landi.landicalendar;

import com.landi.landicalendar.bean.CalendarBean;

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
