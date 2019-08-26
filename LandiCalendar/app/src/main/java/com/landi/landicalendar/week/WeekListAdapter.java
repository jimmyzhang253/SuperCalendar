package com.landi.landicalendar.week;

import android.content.Context;
import android.widget.TextView;

import com.landi.landicalendar.R;
import com.landi.landicalendar.bean.CalendarBean;
import com.landi.landicalendar.bean.CalendarGroup;
import com.landi.landicalendar.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.landi.landicalendar.donkingliang.groupedadapter.holder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjie on 2019/3/11
 */
public class WeekListAdapter extends GroupedRecyclerViewAdapter {

    private List<CalendarGroup> groupList = new ArrayList<>();

    public WeekListAdapter(Context context) {
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
        CalendarBean weekBean = groupList.get(groupPosition).getCalendarBeanList().get(childPosition);
        TextView position = holder.get(R.id.tv_position);
        TextView date = holder.get(R.id.tv_date);
        position.setText("第" + (childPosition + 1) + "周");
        if (weekBean.isFuture()) {
            position.setTextColor(mContext.getResources().getColor(R.color.text_gray));
        } else {
            position.setTextColor(mContext.getResources().getColor(R.color.text_color));
        }
        date.setText(weekBean.getStartDate() + "-" + weekBean.getEndDate());
    }
}
