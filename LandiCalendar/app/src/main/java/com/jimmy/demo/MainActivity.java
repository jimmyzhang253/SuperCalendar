package com.jimmy.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jimmy.calendar.CalendarListener;
import com.jimmy.calendar.CalendarType;
import com.jimmy.calendar.LandiBusiness;
import com.jimmy.calendar.activity.BaseCalendarActivity;
import com.jimmy.calendar.bean.CalendarBean;
import com.jimmy.calendar.demo.R;

public class MainActivity extends BaseCalendarActivity implements View.OnClickListener {

    private Button day;
    private Button week;
    private Button month;
    private Button doubleTime;
    private Button doubleTimeFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        day = (Button) findViewById(R.id.day);
        week = (Button) findViewById(R.id.week);
        month = (Button) findViewById(R.id.month);
        doubleTime = (Button) findViewById(R.id.double_time);
        doubleTimeFuture = (Button) findViewById(R.id.double_time_future);
        day.setOnClickListener(this);
        week.setOnClickListener(this);
        month.setOnClickListener(this);
        doubleTime.setOnClickListener(this);
        doubleTimeFuture.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.day:
                LandiBusiness.getInstance().startCalendarActivity(this, CalendarType.DAY, new CalendarListener() {
                    @Override
                    public void onSelectDate(CalendarBean weekBean) {

                    }
                });
                break;
            case R.id.week:
                LandiBusiness.getInstance().startCalendarActivity(this, CalendarType.WEEK, new CalendarListener() {
                    @Override
                    public void onSelectDate(CalendarBean weekBean) {

                    }
                });
                break;
            case R.id.month:
                LandiBusiness.getInstance().startCalendarActivity(this, CalendarType.MONTH, new CalendarListener() {
                    @Override
                    public void onSelectDate(CalendarBean weekBean) {

                    }
                });
                break;
            case R.id.double_time:
                LandiBusiness.getInstance().startCalendarActivity(this, CalendarType.DOUBLE_DAY, new CalendarListener() {
                    @Override
                    public void onSelectDate(CalendarBean weekBean) {

                    }
                });
                break;
            case R.id.double_time_future:
                LandiBusiness.getInstance().startDayFutureActivity(this, "2019-06", "2020-05", new CalendarListener() {
                    @Override
                    public void onSelectDate(CalendarBean weekBean) {

                    }
                });
                break;
        }
    }
}
