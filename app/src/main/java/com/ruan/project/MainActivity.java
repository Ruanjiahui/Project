package com.ruan.project;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.data_sdk.SystemUtil.SystemTool;
import com.example.administrator.ui_sdk.MyBaseActivity.NavActivity;
import com.ruan.project.Controllar.CheckOnline;
import com.ruan.project.Controllar.UdpOpera;
import com.ruan.project.Interface.UDPInterface;
import com.ruan.project.Other.HTTP.HttpURL;
import com.ruan.project.View.Fragment.Fragment1;
import com.ruan.project.View.Fragment.Fragment2;
import com.ruan.project.View.Fragment.Fragment3;
import com.ruan.project.View.Fragment.Fragment4;


public class MainActivity extends NavActivity implements UDPInterface.HandlerMac {
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
                isRefresh = true;
                intentFragment(new Fragment1());
                break;
            case 1:
                isRefresh = false;
                intentFragment(new Fragment2());
                break;
            case 2:
                isRefresh = false;
                intentFragment(new Fragment3());
                break;
            case 3:
                isRefresh = false;
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

        //只有当手机连接wifi情况下每次刷新才进行判断是不是在同一局域网里面
        if (SystemTool.isNetState(context) == 1)
            //扫描局域网的设备
            //刚进来也要判断设备是不是在同一局域网里面
            new UdpOpera(context).UDPDeviceScan(this);
        else {
            HttpURL.STATE = SystemTool.isNetState(context);
            CheckOnline();
        }
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

    /**
     * 这个方法获取Mac值
     * //0 储存接收的数据
     * //1 储存接收数据的长度
     * //2 储存接收的地址
     * //3 储存接收的端口
     *
     * @param position 标示
     * @param objects  这个Object数组里面包含一些列的设备信息
     */
    public static boolean isRefresh = true;
    @Override
    public void getMac(int position, Object[] objects) {
//        if (isRefresh) {
            HttpURL.STATE = 1;
            CheckOnline();
//        }
    }

    /**
     * 超时
     *
     * @param position
     * @param error
     */
    @Override
    public void Error(int position, int error) {
//        if (isRefresh) {
            if (SystemTool.isNetState(context) != 0)
                //如果手机可以连接网络的情况下说明只能用外网
                HttpURL.STATE = 2;
            CheckOnline();
//        }
    }

    private void CheckOnline() {
        //通过udp单播进行设备检测是否在线
        //如果有连接wifi则使用udp判断设备是否在线
        if (HttpURL.STATE == 1)
            new CheckOnline(this, getSupportFragmentManager()).UDPCheck();
        //通过云端进行设备检测是否在线
        //如果wifi没有连接则使用外网判断设备是否在线
        if (HttpURL.STATE == 2)
            new CheckOnline(this, getSupportFragmentManager()).HTTPCheck();
    }
}
