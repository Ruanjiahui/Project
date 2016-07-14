package com.ruan.project.View;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;


import com.ruan.project.Interface.PopWinOnClick;
import com.ruan.project.R;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/2/23.
 */
public class MyPopWindow extends PopupWindow implements OnItemClickListener {

    private View conentView;

    private ListView listView = null;
    private PopWinOnClick popWinOnClick = null;

    public MyPopWindow(Activity activity, ArrayList<Object> list, int width) {
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popwindow, null);
        int h = activity.getWindowManager().getDefaultDisplay().getHeight();
        int w = activity.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(width);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
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

        listView = (ListView) conentView.findViewById(R.id.poplistview);
//        listView.setAdapter(new ListViewAdapter(activity, list));
        listView.setOnItemClickListener(this);
    }

    /**
     * 显示popupWindow
     *
     * @param parent    这个是点击组件
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void showPopupWindow(View parent , int width , int height) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
//            parent.getLayoutParams().width / 2
            this.showAsDropDown(parent, width, height , Gravity.CENTER);
        } else {
            this.dismiss();
        }
    }

    /**
     *  关闭popwin
     */
    public void disShow(){
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
        popWinOnClick.OnPopItemClick(parent , view , position , id);
    }
}