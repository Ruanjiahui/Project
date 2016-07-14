package com.example.administrator.ui_sdk.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by Soft on 2016/7/9.
 * <p/>
 * 这个类实现侧滑实现删除，编辑的自定义listview类
 */
public class SideListView extends ListView {

    //获取屏幕宽度
    private int mScreenWidth = 0;

    //是否可以滑动
    private boolean isScroll = true;

    //侧滑布局是否显示
    //isShown is true 就是有显示
    //isShown is false 就是没有显示
    private boolean isShown;

    //按下的X，Y开始的坐标
    private int downX, downY = 0;
    //移动的X, Y的坐标
    private int nowX, nowY = 0;
    //移动的长度
    private int scroll = 0;

    //当前点击listview的item  布局
    private ViewGroup itemChildView = null;

    //当前显示的布局
    private LinearLayout.LayoutParams itemLinearLayout = null;
    //侧滑菜单的宽度
    private int sideWidth = 0;

    //获取点击该item的是第几个
    private int position = 0;


    public SideListView(Context context) {
        this(context, null);
    }

    public SideListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取屏幕宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;

        isScroll = true;
    }


    /**
     * 重写触摸事件
     * <p/>
     * 监控 ACTION_DOWN   ACTION_UP   ACTION_MOVE
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

//        if (!isScroll)
//            return false;
//        else {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return ActionDown(ev);
            case MotionEvent.ACTION_MOVE:
                return ActionMove(ev);
            case MotionEvent.ACTION_UP:
                ActionUp(ev);
                break;
        }
        return super.onTouchEvent(ev);
//        }
    }


    /**
     * 触摸 手指滑动的事件
     *
     * @param ev
     */
    private boolean ActionMove(MotionEvent ev) {
        nowX = (int) ev.getX();
        nowY = (int) ev.getY();

        //当横移动的幅度大于竖移动的时候才让侧滑菜单滑动
        if (Math.abs(nowX - downX) > Math.abs(nowY - downY) * 2 || isScroll) {
            isScroll = true;

             /*此段代码是为了避免我们在侧向滑动时同时触发ListView的OnItemClickListener时间*/
            MotionEvent cancelEvent = MotionEvent.obtain(ev);
            cancelEvent
                    .setAction(MotionEvent.ACTION_CANCEL
                            | (ev.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
            onTouchEvent(cancelEvent);

            //当手指从左往右滑动的时候才让界面滑动
            if (nowX < downX) {

                scroll = (nowX - downX) / 2;
                //当滑动距离大于侧滑菜单的宽度的时候设置最大为侧滑的宽度
                if (-scroll >= sideWidth)
                    scroll = -sideWidth;


                itemLinearLayout.leftMargin = scroll;
                itemChildView.getChildAt(0).setLayoutParams(itemLinearLayout);
            }

            return true;
        }
        return super.onTouchEvent(ev);

    }

    /**
     * 触摸 手指抬起来的事件
     *
     * @param ev
     */
    private void ActionUp(MotionEvent ev) {
        //当侧滑的长度大于侧滑菜单的宽度则就侧滑菜单全部显示否则隐藏
        if (-itemLinearLayout.leftMargin >= sideWidth / 2) {
            itemLinearLayout.leftMargin = -sideWidth;
            itemChildView.getChildAt(0).setLayoutParams(itemLinearLayout);
            isShown = true;
        } else {
            ShowNormal();
        }
        isScroll = false;
    }

    /**
     * 触摸 手指按下去的事件
     *
     * @param ev
     */
    private boolean ActionDown(MotionEvent ev) {
        if (isShown)
            ShowNormal();


        downX = (int) ev.getX();
        downY = (int) ev.getY();


        itemChildView = (ViewGroup) getChildAt(pointToPosition(downX, downY) - getFirstVisiblePosition());


        if (itemChildView == null)
            return false;

        sideWidth = 0;
        //获取侧滑菜单的宽度
        for (int i = 1; i < itemChildView.getChildCount(); i++)
            sideWidth += itemChildView.getChildAt(i).getLayoutParams().width;

        //获取显示界面的布局LinearLayout
        itemLinearLayout = (LinearLayout.LayoutParams) itemChildView.getChildAt(0).getLayoutParams();

        //重写显示界面的宽度
        itemLinearLayout.width = mScreenWidth;
        itemChildView.getChildAt(0).setLayoutParams(itemLinearLayout);

        return true;
    }


    /**
     * 恢复正常的显示样式
     */
    public void ShowNormal() {
        //界面滑动会坐标为0,0
        itemChildView.scrollTo(0, 0);
        //设置距离左边0
        itemLinearLayout.leftMargin = 0;
        itemChildView.getChildAt(0).setLayoutParams(itemLinearLayout);
        //将侧滑菜单设置为不显示
        isShown = false;
    }

    public int getPosition(){
        return position;
    }


    public void setSideMenu(boolean isScroll) {
        this.isScroll = isScroll;
    }
}
