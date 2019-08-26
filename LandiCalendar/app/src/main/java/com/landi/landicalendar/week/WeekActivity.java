package com.landi.landicalendar.week;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.landi.landicalendar.CalendarListener;
import com.landi.landicalendar.R;
import com.landi.landicalendar.activity.BaseCalendarActivity;
import com.landi.landicalendar.bean.CalendarBean;
import com.landi.landicalendar.bean.CalendarGroup;
import com.landi.landicalendar.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.landi.landicalendar.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.landi.landicalendar.donkingliang.groupedadapter.layoutmanger.GroupedGridLayoutManager;
import com.landi.landicalendar.utils.LandiDateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by zhangjie on 2019/3/11
 */
public class WeekActivity extends BaseCalendarActivity {

    private WeekListAdapter groupedListAdapter;
    private RecyclerView listview;
    private ImageView back;
    private TextView tvTitle;
    public static CalendarListener calendarListener;
    private int yearNow;
    private int monthNow;
    private int dayNow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week_layout);
        initView();
    }

    private void initView() {
        yearNow = LandiDateUtils.getYearNow();
        monthNow = LandiDateUtils.getMonthNow();
        dayNow = LandiDateUtils.getDayNow();
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle = (TextView) findViewById(R.id.tv_title);
        listview = (RecyclerView) findViewById(R.id.listview);
        groupedListAdapter = new WeekListAdapter(this);
        listview.setAdapter(groupedListAdapter);
        GroupedGridLayoutManager gridLayoutManager = new GroupedGridLayoutManager(this, 4, groupedListAdapter);
        listview.setLayoutManager(gridLayoutManager);
        groupedListAdapter.setOnChildClickListener(this);
        loadData();
    }

    private void loadData() {
        List<CalendarGroup> groupList = new ArrayList<>();
        int year = LandiDateUtils.getYearNow();
        int month = LandiDateUtils.getMonthNow();
        for (int i = 0; i < 12; i++) {
            CalendarGroup weekGroup = new CalendarGroup();
            weekGroup.setTitle(year + "年" + (month + 1) + "月");
            List<CalendarBean> weekBeanList = new ArrayList<>();
            List<LandiDateUtils.CalendarDateRange> list = LandiDateUtils.getAllWeeksInMonth(year, month);
            if (list != null && list.size() > 0) {
                for (LandiDateUtils.CalendarDateRange weekDateRange : list) {
                    CalendarBean weekBean = new CalendarBean();
                    weekBean.setYear(year);
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(weekDateRange.getStartDate());
                    weekBean.setStartDate((calendar1.get(Calendar.MONTH) + 1) + "." + calendar1.get(Calendar.DAY_OF_MONTH));
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(weekDateRange.getEndDate());
                    weekBean.setEndDate((calendar2.get(Calendar.MONTH) + 1) + "." + calendar2.get(Calendar.DAY_OF_MONTH));
                    if (calendar1.get(Calendar.YEAR) > yearNow ||
                            (calendar1.get(Calendar.YEAR) == yearNow && calendar1.get(Calendar.MONTH) == monthNow && calendar1.get(Calendar.DAY_OF_MONTH) > dayNow) ||
                            (calendar1.get(Calendar.YEAR) >= yearNow && calendar1.get(Calendar.MONTH) > monthNow)) {
                        weekBean.setFuture(true);
                    }
                    weekBeanList.add(weekBean);
                }
            }
            weekGroup.setCalendarBeanList(weekBeanList);
            groupList.add(0, weekGroup);
            if (month == 0) {
                year--;
                month = 11;
            } else {
                month = month - 1;
            }
        }
        groupedListAdapter.setGroupList(groupList);
        listview.post(new Runnable() {
            @Override
            public void run() {
                listview.scrollToPosition(groupedListAdapter.getItemCount()-1);
            }
        });
    }

    @Override
    public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition, int childPosition) {
        if (calendarListener != null) {
            CalendarBean weekBean = groupedListAdapter.getChild(groupPosition, childPosition);
            if (weekBean != null && !weekBean.isFuture()) {
                calendarListener.onSelectDate(weekBean);
                finish();
            }
        }
    }

    public static void setCalendarListener(CalendarListener calendarListener) {
        WeekActivity.calendarListener = calendarListener;
    }
}
