package com.ruan.project.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by Soft on 2016/7/9.
 */
public class RefreshListView extends ListView {

    //当前点击listview的item  布局
    private ViewGroup itemChildView = null;

    private View TopView = null;
    private View BottomView = null;

    private int Topheight = 0;

    //按下的X，Y开始的坐标
    private int downX, downY = 0;

    //当前显示的布局
    private LinearLayout.LayoutParams itemLinearLayout = null;

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    /**
     * 重写滑动触摸事件实现下拉刷新
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void ActionDown(MotionEvent ev){
        downX = (int) ev.getX();
        downY = (int) ev.getY();



    }


    /**
     * 恢复正常的显示样式
     */
    private void ShowNormal() {
        //界面滑动会坐标为0,0
        itemChildView.scrollTo(0, 0);
        //设置距离左边0
        itemLinearLayout.leftMargin = 0;
        itemChildView.setLayoutParams(itemLinearLayout);
        //将侧滑菜单设置为不显示
//        isShown = false;
    }
}
