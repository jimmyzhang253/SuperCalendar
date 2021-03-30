package com.jimmy.calendar.day;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.jimmy.calendar.R;
import com.jimmy.calendar.bean.CalendarBean;
import com.jimmy.calendar.bean.CalendarGroup;
import com.jimmy.calendar.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.jimmy.calendar.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.jimmy.calendar.utils.LandiDateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjie on 2019/3/11
 */
public class DayFutureListAdapter extends GroupedRecyclerViewAdapter {
    private CalendarBean startCalendarBean = null;
    private CalendarBean endCalendarBean = null;

    private List<CalendarGroup> groupList = new ArrayList<>();

    public DayFutureListAdapter(Context context) {
        super(context);
    }

    public void setGroupList(List<CalendarGroup> groupList) {
        this.groupList = groupList;
        notifyDataChanged();
    }

    public CalendarBean getChild(int groupPosition, int childPosition) {
        try {
            return groupList.get(groupPosition).getCalendarBeanList().get(childPosition);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupList.get(groupPosition).getCalendarBeanList().size();
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return false;
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return R.layout.item_calendar_head_layout;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return 0;
    }

    @Override
    public int getChildLayout(int viewType) {
        return R.layout.item_calendar_layout;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
        TextView textView = holder.get(R.id.tv_title);
        textView.setText(groupList.get(groupPosition).getTitle());
    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {

    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        CalendarBean calendarBean = groupList.get(groupPosition).getCalendarBeanList().get(childPosition);
        ConstraintLayout itemLayout = holder.get(R.id.item_layout);
        TextView position = holder.get(R.id.tv_position);
        holder.get(R.id.tv_date).setVisibility(View.GONE);
        itemLayout.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        if (calendarBean.getDay() != -1) {
            if (calendarBean.isToday()) {
                position.setText("今");
            } else {
                position.setText(String.valueOf(calendarBean.getDay()));
            }
        } else {
            position.setText("");
        }
        if (calendarBean.isFuture()) {
            position.setTextColor(mContext.getResources().getColor(R.color.text_color));
        } else if (calendarBean.isToday()) {
            position.setTextColor(mContext.getResources().getColor(R.color.calendar_main_color));
        } else {
            position.setTextColor(mContext.getResources().getColor(R.color.text_gray));
        }
        //区间选择判断
        if (calendarBean.getDay() != -1) {
            if (getStartCalendarBean() != null) {
                if (getEndCalendarBean() == null) {
                    //开始选择了起始时间
                    if (LandiDateUtils.isEquals(calendarBean.getTime(), getStartCalendarBean().getTime())) {
                        itemLayout.setBackgroundColor(mContext.getResources().getColor(R.color.calendar_main_color));
                        position.setTextColor(mContext.getResources().getColor(R.color.white));
                    }
                } else {
                    if (!LandiDateUtils.isBefore(calendarBean.getTime(), getStartCalendarBean().getTime())
                            && !LandiDateUtils.isAfter(calendarBean.getTime(), getEndCalendarBean().getTime())) {
                        itemLayout.setBackgroundColor(mContext.getResources().getColor(R.color.calendar_main_color));
                        position.setTextColor(mContext.getResources().getColor(R.color.white));
                    }
                }
            } else {
                itemLayout.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
            }
        }
    }

    public void setStartCalendarBean(CalendarBean startCalendarBean) {
        this.startCalendarBean = startCalendarBean;
    }

    public void setEndCalendarBean(CalendarBean endCalendarBean) {
        this.endCalendarBean = endCalendarBean;
    }

    public CalendarBean getStartCalendarBean() {
        return startCalendarBean;
    }

    public CalendarBean getEndCalendarBean() {
        return endCalendarBean;
    }
}
