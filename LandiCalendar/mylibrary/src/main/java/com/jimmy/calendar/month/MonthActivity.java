package com.jimmy.calendar.month;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimmy.calendar.CalendarListener;
import com.jimmy.calendar.R;
import com.jimmy.calendar.activity.BaseCalendarActivity;
import com.jimmy.calendar.bean.CalendarBean;
import com.jimmy.calendar.bean.CalendarGroup;
import com.jimmy.calendar.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.jimmy.calendar.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.jimmy.calendar.donkingliang.groupedadapter.layoutmanger.GroupedGridLayoutManager;
import com.jimmy.calendar.utils.LandiDateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by zhangjie on 2019/3/12
 */
public class MonthActivity extends BaseCalendarActivity {
    private ImageView back;
    private TextView tvTitle;
    private RecyclerView listview;
    public static CalendarListener calendarListener;
    private MonthListAdapter groupedListAdapter;
    private int yearNow;
    private int monthNow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);
        initView();
    }

    private void initView() {
        yearNow = LandiDateUtils.getYearNow();
        monthNow = LandiDateUtils.getMonthNow();
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle = (TextView) findViewById(R.id.tv_title);
        listview = (RecyclerView) findViewById(R.id.listview);
        groupedListAdapter = new MonthListAdapter(this);
        listview.setAdapter(groupedListAdapter);
        GroupedGridLayoutManager gridLayoutManager = new GroupedGridLayoutManager(this, 4, groupedListAdapter);
        listview.setLayoutManager(gridLayoutManager);
        groupedListAdapter.setOnChildClickListener(this);
        loadData();
    }

    private void loadData() {
        int year = LandiDateUtils.getYearNow();
        List<CalendarGroup> monthGroupList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            CalendarGroup monthGroup = new CalendarGroup();
            List<CalendarBean> monthBeanList = new ArrayList<>();
            monthGroup.setTitle(year + "年");
            List<LandiDateUtils.CalendarDateRange> list = LandiDateUtils.getMonthList(year);
            for (LandiDateUtils.CalendarDateRange calendarDateRange : list) {
                CalendarBean monthBean = new CalendarBean();
                monthBean.setYear(year);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(calendarDateRange.getStartDate());
                monthBean.setStartDate((calendar1.get(Calendar.MONTH) + 1) + "." + calendar1.get(Calendar.DAY_OF_MONTH));
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(calendarDateRange.getEndDate());
                monthBean.setEndDate((calendar2.get(Calendar.MONTH) + 1) + "." + calendar2.get(Calendar.DAY_OF_MONTH));
                if (calendar1.get(Calendar.YEAR) > yearNow ||
                        (calendar1.get(Calendar.YEAR) == yearNow && calendar1.get(Calendar.MONTH) > monthNow)) {
                    monthBean.setFuture(true);
                }
                monthBeanList.add(monthBean);
            }
            monthGroup.setCalendarBeanList(monthBeanList);
            monthGroupList.add(0, monthGroup);
            year--;
        }
        groupedListAdapter.setGroupList(monthGroupList);
    }

    public static void setCalendarListener(CalendarListener calendarListener) {
        MonthActivity.calendarListener = calendarListener;
    }


    @Override
    public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition, int childPosition) {
        CalendarBean monthBean = groupedListAdapter.getChild(groupPosition, childPosition);
        if (monthBean != null && calendarListener != null && !monthBean.isFuture()) {
            calendarListener.onSelectDate(monthBean);
            finish();
        }
    }
}
