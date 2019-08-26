package com.landi.landicalendar.day;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.landi.landicalendar.CalendarListener;
import com.landi.landicalendar.R;
import com.landi.landicalendar.activity.BaseCalendarActivity;
import com.landi.landicalendar.bean.CalendarBean;
import com.landi.landicalendar.bean.CalendarGroup;
import com.landi.landicalendar.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.landi.landicalendar.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.landi.landicalendar.donkingliang.groupedadapter.layoutmanger.GroupedGridLayoutManager;
import com.landi.landicalendar.utils.LandiDateUtils;

import java.util.List;

/**
 * Created by zhangjie on 2019/3/13
 */
public class DayActivity extends BaseCalendarActivity implements View.OnClickListener {
    private ImageView back;
    private TextView tvTitle;
    private LinearLayout llWeek;
    private RecyclerView recyclerview;
    private DayListAdapter dayListAdapter;
    public static CalendarListener calendarListener;
    private boolean isInterval = false;//是否是区间选择

    private CalendarBean startCalendarBean = null;
    private CalendarBean endCalendarBean = null;
    private TextView tvRight;
    private ConstraintLayout clQuickSelect;
    private TextView today;
    private TextView lastSevenDays;
    private TextView lastThirtyDays;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_layout);
        initView();
    }

    private void initView() {
        tvRight = (TextView) findViewById(R.id.tv_right);
        back = (ImageView) findViewById(R.id.back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        llWeek = (LinearLayout) findViewById(R.id.ll_week);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        clQuickSelect = (ConstraintLayout) findViewById(R.id.cl_quick_select);
        today = (TextView) findViewById(R.id.today);
        lastSevenDays = (TextView) findViewById(R.id.last_seven_days);
        lastThirtyDays = (TextView) findViewById(R.id.last_thirty_days);
        isInterval = getIntent().getBooleanExtra("TYPE", false);
        if (isInterval) {
            clQuickSelect.setVisibility(View.VISIBLE);
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setOnClickListener(this);
        }
        back.setOnClickListener(this);
        dayListAdapter = new DayListAdapter(this);
        GroupedGridLayoutManager gridLayoutManager = new GroupedGridLayoutManager(this, 7, dayListAdapter);
        recyclerview.setLayoutManager(gridLayoutManager);
        recyclerview.setAdapter(dayListAdapter);
        dayListAdapter.setOnChildClickListener(this);
        loadData();
        today.setOnClickListener(this);
        lastSevenDays.setOnClickListener(this);
        lastThirtyDays.setOnClickListener(this);
    }

    private void loadData() {
        List<CalendarGroup> list = LandiDateUtils.getDayList(12);
        dayListAdapter.setGroupList(list);
        recyclerview.post(new Runnable() {
            @Override
            public void run() {
                recyclerview.scrollToPosition(dayListAdapter.getItemCount() - 1);
            }
        });
    }

    @Override
    public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition, int childPosition) {
        if (calendarListener != null) {
            CalendarBean calendarBean = dayListAdapter.getChild(groupPosition, childPosition);
            if (isInterval && calendarBean != null && !calendarBean.isFuture() && calendarBean.getDay() != -1) {
                if (startCalendarBean == null || (startCalendarBean != null && endCalendarBean != null)) {
                    startCalendarBean = calendarBean;
                    endCalendarBean = null;
                    dayListAdapter.setStartCalendarBean(startCalendarBean);
                    dayListAdapter.setEndCalendarBean(endCalendarBean);
                    dayListAdapter.notifyDataChanged();
                } else {
                    if (!startCalendarBean.equals(calendarBean)) {
                        endCalendarBean = calendarBean;
                        dayListAdapter.setEndCalendarBean(endCalendarBean);
                        dayListAdapter.notifyDataChanged();
                    }
                }
            } else {
                if (calendarBean != null && !calendarBean.isFuture() && calendarBean.getDay() != -1) {
                    calendarListener.onSelectDate(calendarBean);
                    finish();
                }
            }
        }
    }

    public static void setCalendarListener(CalendarListener calendarListener) {
        DayActivity.calendarListener = calendarListener;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.tv_right) {
            onSure();
        } else if (i == R.id.today) {
            quickChoose(0);
        } else if (i == R.id.last_seven_days) {
            quickChoose(6);
        } else if (i == R.id.last_thirty_days) {
            quickChoose(29);
        }
    }

    /**
     * 快速选择时间
     */
    private void quickChoose(int day) {
        if (day == 0) {
            startCalendarBean=new CalendarBean();
            startCalendarBean.setTime(LandiDateUtils.getNearlyStr(0));
            endCalendarBean = null;
            dayListAdapter.setStartCalendarBean(startCalendarBean);
            dayListAdapter.setEndCalendarBean(endCalendarBean);
            dayListAdapter.notifyDataChanged();
        }else {
            startCalendarBean=new CalendarBean();
            startCalendarBean.setTime(LandiDateUtils.getNearlyStr(day));
            endCalendarBean=new CalendarBean();
            endCalendarBean.setTime(LandiDateUtils.getNearlyStr(0));
            dayListAdapter.setStartCalendarBean(startCalendarBean);
            dayListAdapter.setEndCalendarBean(endCalendarBean);
            dayListAdapter.notifyDataChanged();
        }
    }

    /**
     * 双选时间确定
     */
    private void onSure() {
        if (calendarListener != null) {
            if (startCalendarBean == null) {
                Toast.makeText(DayActivity.this, "请选择时间", Toast.LENGTH_SHORT).show();
            } else {
                CalendarBean calendarBean = new CalendarBean();
                if (endCalendarBean == null) {
                    calendarBean.setStartDate(startCalendarBean.getTime());
                    calendarBean.setEndDate(startCalendarBean.getTime());
                } else {
                    calendarBean.setStartDate(startCalendarBean.getTime());
                    calendarBean.setEndDate(endCalendarBean.getTime());
                }
                calendarListener.onSelectDate(calendarBean);
                finish();
            }
        }
    }
}
