package com.ruan.project;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.MyBaseActivity.NavActivity;
import com.ruan.project.Controllar.CheckUpdate;
import com.ruan.project.Controllar.FragmentControl;
import com.ruan.project.Moudle.REGlight;
import com.ruan.project.Moudle.User;
import com.ruan.project.Other.HTTP.HttpURL;
import com.ruan.project.Other.System.ReceiverHandler;
import com.ruan.project.Other.System.ReceiverURL;
import com.ruan.project.Other.System.RegisterTime;
import com.ruan.project.Other.System.WifiReceiver;
import com.ruan.project.Other.Utils.LocalHandle;
import com.ruan.project.Other.Utils.LocalUtils;
import com.ruan.project.View.Fragment.Fragment1;
import com.ruan.project.View.Fragment.Fragment2;
import com.ruan.project.View.Fragment.Fragment3;
import com.ruan.project.View.Fragment.Fragment4;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends NavActivity implements LocalHandle {
    private View view = null;
    private static Context context = null;
    private WifiReceiver wifiReceiver = null;
    public static boolean isRefresh = true;
    public static RegisterTime registerTime = null;
    private CheckUpdate checkUpdate = null;


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

        checkUpdate = new CheckUpdate(context);

        new LocalUtils(context).getCityName(this);
        HttpURL.CityName = User.getInstance().getUserCity();

        setNavContent(view);

        //首先实例化时间监听的广播
        registerTime = new RegisterTime(this);

        //注册网络发生改变的广播
        wifiReceiver = new WifiReceiver(getSupportFragmentManager(), false);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ReceiverURL.CONNECTIVITY_CHANGE);
        filter.addAction(ReceiverURL.RSSI_CHANGED);
        filter.addAction(ReceiverURL.STATE_CHANGE);
        filter.addAction(ReceiverURL.WIFI_STATE_CHANGED);
        //注册广播接收器
        registerReceiver(wifiReceiver, filter);

        setNavTxt(getResources().getColor(R.color.Blue), getResources().getColor(R.color.Grey));
        int[] Pic = new int[]{R.mipmap.homelink1, R.mipmap.homefind1, R.mipmap.homeshop1, R.mipmap.homeme1};
        int[] Pic1 = new int[]{R.mipmap.homelink, R.mipmap.homefind, R.mipmap.homeshop, R.mipmap.homeme};
        setNavPic(Pic, Pic1);

        setNav1(getResources().getString(R.string.Nav1));
        setNav2(getResources().getString(R.string.Nav2));
        setNav3(getResources().getString(R.string.Nav3));
        setNav4(getResources().getString(R.string.Nav4));


        intentFragment(new Fragment1());
        //检查更新操作
        checkUpdate.Update(0);
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


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 001) {
//            intentFragment(new Fragment1());
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MobclickAgent.onKillProcess(this);
        unregisterReceiver(wifiReceiver);
        if (null != LocalUtils.locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            LocalUtils.locationClient.onDestroy();
            LocalUtils.locationClient = null;
            LocalUtils.locationOption = null;
        }
        Applications.getInstance().onTerminate();
    }

    /**
     * 获取城市名称
     *
     * @param city
     */
    @Override
    public void getCity(String city) {
        HttpURL.CityName = city;
        if (isRefresh = true)
            intentFragment(new Fragment1());
    }

    private long startTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isRefresh = true) {
                if (Fragment1.onKeyDown()) {
                    if (System.currentTimeMillis() - startTime < 2000) {
                        Applications.getInstance().onTerminate();
                    }
                    Toast.makeText(context, getResources().getString(R.string.Exists), Toast.LENGTH_SHORT).show();
                    startTime = System.currentTimeMillis();
                }
            }
        }
        return true;
    }
}
