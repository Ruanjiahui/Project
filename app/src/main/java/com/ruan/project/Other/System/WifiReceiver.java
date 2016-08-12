package com.ruan.project.Other.System;

/**
 * Created by Administrator on 2016/7/21.
 * <p/>
 * 监听网络发生改变的广播
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.data_sdk.SystemUtil.SystemTool;
import com.ruan.project.Controllar.CheckOnline;
import com.ruan.project.Controllar.FragmentControl;
import com.ruan.project.Other.HTTP.HttpURL;
import com.ruan.project.Other.UDP.UDPConfig;

public class WifiReceiver extends BroadcastReceiver {

    private Context context = null;
    private FragmentManager fragmentManager = null;
    private boolean isFirst = false;

    public WifiReceiver(FragmentManager fragmentManager, boolean isFirst) {
        this.fragmentManager = fragmentManager;
        this.isFirst = isFirst;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


        HttpURL.STATE = SystemTool.isNetState(context);
        if (isFirst) {
            if (wifiNetInfo.isConnected()) {
                if (intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)) {
                } else if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {//wifi连接上与否

                    NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

                    //判断wifi网络是否断开
                    if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {
//                System.out.println("wifi网络连接断开");
                        //接着wifi断开就是用外网
                        HttpURL.STATE = SystemTool.isNetState(context);
                        //如果是第一次进入软件就不进行内外网切换
                        CheckOnline();
                        //判断是否连接上wifi网络
                    } else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {

                        //连接上wifi
                        HttpURL.STATE = NetWork.WIFI;
                        //扫描局域网的设备
                        //重写判断一下设备有没有在该局域网中
                        //如果是第一次进入软件就不进行内外网切换
                        CheckOnline();

                        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                        WifiInfo wifiInfo = wifiManager.getConnectionInfo();


                        //获取当前wifi名称
//                System.out.println("连接到网络 " + wifiInfo.getSSID());

                    }

                } else if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {//wifi打开与否
                    int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);

                    if (wifistate == WifiManager.WIFI_STATE_DISABLED) {
//                System.out.println("系统关闭wifi");
                    } else if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
//                System.out.println("系统开启wifi");
                    }
                }
            } else if (mobNetInfo.isConnected()) {
                HttpURL.STATE = NetWork.INTNET;
                CheckOnline();
            } else {
                HttpURL.STATE = NetWork.UNCONN;
                CheckOnline();
            }
        } else {
            //如果是第一次访问软件就是直接用云端访问判断设备是否在线
            isFirst = true;
            new CheckOnline(context, fragmentManager).HTTPCheck();
        }
    }

    /**
     * 网络发生改变的时候重写判断设备是否在线
     */
    private void CheckOnline() {
        if (FragmentControl.getUserDeviceData(context) != null && FragmentControl.getUserDeviceData(context).size() != 0) {
            if (HttpURL.STATE == NetWork.WIFI)
                new CheckOnline(context, fragmentManager).UDPCheck();
            //通过云端进行设备检测是否在线
            //如果wifi没有连接则使用外网判断设备是否在线
            if (HttpURL.STATE == NetWork.INTNET)
                new CheckOnline(context, fragmentManager).HTTPCheck();
            if (HttpURL.STATE == NetWork.UNCONN)
                new CheckOnline(context, fragmentManager).DeviceUnOnline();
        }
    }
}
