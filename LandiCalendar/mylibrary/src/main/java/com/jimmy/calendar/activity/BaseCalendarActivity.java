package com.jimmy.calendar.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.jimmy.calendar.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.jimmy.calendar.donkingliang.groupedadapter.holder.BaseViewHolder;

/**
 * Created by zhangjie on 2019/3/11
 */
public class BaseCalendarActivity extends Activity implements GroupedRecyclerViewAdapter.OnChildClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setFullScreenEnable(false);
    }

    @Override
    public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition, int childPosition) {

    }

    /**
     * 设置全屏并且显示状态栏
     *
     * @param hideFlags
     */
    public void setFullScreenEnable(boolean hideFlags) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            if (hideFlags) {
                // 布局占用状态栏，并隐藏状态栏，不影响导航栏
                params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            } else {
                params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
            // 全屏布局，状态栏和导航栏覆盖在布局上
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.setAttributes(params);
        }
    }
}
