package com.ruan.project;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.data_sdk.SystemUtil.SystemTool;
import com.example.administrator.ui_sdk.MyBaseActivity.NavActivity;
import com.ruan.project.Controllar.CheckOnline;
import com.ruan.project.Other.HTTP.HttpURL;
import com.ruan.project.View.Fragment.Fragment1;
import com.ruan.project.View.Fragment.Fragment2;
import com.ruan.project.View.Fragment.Fragment3;
import com.ruan.project.View.Fragment.Fragment4;


public class MainActivity extends NavActivity {
    private View view = null;
    private Context context = null;


    /**
     * 这个是四个导航的点击事件
     * 这个是抽象方法不需要在主类实现
     *
     * @param position
     */
    @Override
    public void setNavClick(int position) {
        switch (position) {
            case 0:
                intentFragment(new Fragment1());
                break;
            case 1:
                intentFragment(new Fragment2());
                break;
            case 2:
                intentFragment(new Fragment3());
                break;
            case 3:
                intentFragment(new Fragment4());
                break;
        }
    }

    /**
     * 这个也是一个抽象的方法不需要主类实现
     * 这个是程序的初始化的方法
     */
    @Override
    public void Nav() {
        context = this;
        view = LayoutInflater.from(this).inflate(R.layout.activity_main, null);

        setLeftTitleVisiable(false);
        setLeftImageVisiable(false);

        setTileBar(0);
        setNavColor(R.color.White);

        intentFragment(new Fragment1());

        setNavContent(view);

        HttpURL.STATE = SystemTool.isNetState(context);

        //通过udp单播进行设备检测是否在线
        //如果有连接wifi则使用udp判断设备是否在线
        if (HttpURL.STATE == 1)
            new CheckOnline(this , getSupportFragmentManager()).UDPCheck();
        //通过云端进行设备检测是否在线
        //如果wifi没有连接则使用外网判断设备是否在线
        if (HttpURL.STATE == 2)
            new CheckOnline(this , getSupportFragmentManager()).HTTPCheck();
    }

    /**
     * 跳转Fragment
     *
     * @param targetFragment
     */
    private void intentFragment(Fragment targetFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
