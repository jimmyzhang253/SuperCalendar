# SuperCalendar
自定义的日历时间选择器，支持天、周、月、时间区间选择
1、也可使用SuperCalendar_V3.3.aar直接导入使用（Libs内部含有）
2、启动代码：
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
		
		
![guide](https://github.com/zj1014/SuperCalendar/blob/master/guide/1.png)
![guide](https://github.com/zj1014/SuperCalendar/blob/master/guide/2.png)
![guide](https://github.com/zj1014/SuperCalendar/blob/master/guide/3.png)
![guide](https://github.com/zj1014/SuperCalendar/blob/master/guide/4.png)
![guide](https://github.com/zj1014/SuperCalendar/blob/master/guide/5.png)
![guide](https://github.com/zj1014/SuperCalendar/blob/master/guide/6.png)