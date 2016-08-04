package com.ruan.project.View;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;


import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.ui_sdk.View.RefreshSideListView;
import com.ruan.project.Interface.PopWinOnClick;
import com.ruan.project.Other.Adapter.LGAdapter;
import com.ruan.project.Other.Adapter.SideListViewAdapter;
import com.ruan.project.R;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/2/23.
 */
public class MyPopWindow extends PopupWindow implements OnItemClickListener {

    private View conentView;
    public static int UP = 0;
    public static int BOTTOM = 1;

    private ListView listView = null;
    private PopWinOnClick popWinOnClick = null;
    private LinearLayout popLinear = null;

    public MyPopWindow(Activity activity, ArrayList<Object> list, int width, int height) {
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popwin, null);
        int h = activity.getWindowManager().getDefaultDisplay().getHeight();
        int w = activity.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(width);
        // 设置SelectPicPopupWindow弹出窗体的高
        if (height == 0)
            this.setHeight(BaseActivity.height / 2);
        else
            this.setHeight(height);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);

        listView = (ListView) conentView.findViewById(R.id.popWinlistview);
        popLinear = (LinearLayout) conentView.findViewById(R.id.popLinear);
        listView.setAdapter(new SideListViewAdapter(activity, list));
        listView.setOnItemClickListener(this);
    }

    /**
     * 设置弹出框的背景颜色
     *
     * @param resid
     */
    public void setPopBackground(int resid) {
        popLinear.setBackgroundResource(resid);
    }

    /**
     * 设置弹出框listview点击事件的背景颜色
     *
     * @param resid
     */
    public void setPopListSector(int resid) {
        listView.setSelector(resid);
    }

    /**
     * 设置listview的背景颜色
     *
     * @param resid
     */
    public void setPopListBackground(int resid) {
        listView.setBackgroundResource(resid);
    }

    /**
     * 显示popupWindow
     *
     * @param parent 这个是点击组件
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void showPopupWindow(View parent, int location, int width, int height) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            if (location == BOTTOM)
                this.showAsDropDown(parent);
            if (location == UP) {
                this.showAtLocation(parent, Gravity.NO_GRAVITY, width, height);
            }
        } else {
            this.dismiss();
        }
    }

    /**
     * 关闭popwin
     */
    public void disShow() {
        if (this.isShowing())
            this.dismiss();
    }


    public void Popdismiss() {
        this.dismiss();
    }

    public void setOnPopWinItemClick(PopWinOnClick popWinOnClick) {
        this.popWinOnClick = popWinOnClick;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        popWinOnClick.OnPopItemClick(parent, view, position, id);
    }
}