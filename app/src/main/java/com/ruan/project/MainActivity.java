package com.ruan.project;


import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.DeviceURL;
import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.MyBaseActivity.NavActivity;
import com.ruan.project.Controllar.FragmentControl;
import com.ruan.project.Moudle.TimeMoudle;
import com.ruan.project.Other.System.ListenTime;
import com.ruan.project.Other.System.ReceiverAction;
import com.ruan.project.Other.System.ReceiverHandler;
import com.ruan.project.Other.System.RegisterTime;
import com.ruan.project.Other.System.WifiReceiver;
import com.ruan.project.View.Activity.DeviceControl;
import com.ruan.project.View.Control.SocketSwitchFragment;
import com.ruan.project.View.Fragment.Fragment1;
import com.ruan.project.View.Fragment.Fragment2;
import com.ruan.project.View.Fragment.Fragment3;
import com.ruan.project.View.Fragment.Fragment4;

public class MainActivity extends NavActivity {
    private View view = null;
    private static Context context = null;
    private WifiReceiver wifiReceiver = null;
    public static boolean isRefresh = true;
    private static ReceiverHandler receiverHandler = null;
    public static RegisterTime registerTime = null;


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
                if (!Fragment3.isFragment3) {
                    isRefresh = false;
                    intentFragment(new Fragment3());
                }
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


        //初始化数据库
        FragmentControl.DataBaseHandler(context);

        intentFragment(new Fragment1());

        setNavContent(view);

        //首先实例化时间监听的广播
        registerTime = new RegisterTime(this);

        //数据库没有数据库的时候不进行扫描工作
//        if (FragmentDatabase.getUserDeviceData(context) != null && FragmentDatabase.getUserDeviceData(context).size() != 0) {
//            Log.e("Ruan" , "error");
//            HttpURL.STATE = SystemTool.isNetState(context);
//            CheckOnline();
//        }
        //注册网络发生改变的广播
        wifiReceiver = new WifiReceiver(getSupportFragmentManager(), false);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.RSSI_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        //注册广播接收器
        registerReceiver(wifiReceiver, filter);

        setNavTxt(getResources().getColor(R.color.Blue), getResources().getColor(R.color.Grey));
        int[] Pic = new int[]{R.mipmap.homelink1, R.mipmap.homefind1, R.mipmap.homeshop1, R.mipmap.homeme1};
        int[] Pic1 = new int[]{R.mipmap.homelink, R.mipmap.homefind, R.mipmap.homeshop, R.mipmap.homeme};
        setNavPic(Pic, Pic1);

        setNav1("首页");
        setNav2("分类");
        setNav3("商城");
        setNav4("我的");

    }

    @Override
    public void NacClick(View v) {
        switch (v.getId()) {

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
                .commitAllowingStateLoss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wifiReceiver);
        Applications.getInstance().onTerminate();
//        registerTime.unreisterTime();
    }
}
