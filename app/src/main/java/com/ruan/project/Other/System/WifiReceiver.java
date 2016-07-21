package com.ruan.project.Other.System;

/**
 * Created by Administrator on 2016/7/21.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import com.ruan.project.Other.HTTP.HttpURL;

public class WifiReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)) {
        } else if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {//wifi连接上与否
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

            //判断wifi网络是否断开
            if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {
//                System.out.println("wifi网络连接断开");
                //接着wifi断开就是用外网
                HttpURL.STATE = 2;
                //判断是否连接上wifi网络
            } else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {
                //连接上wifi
                HttpURL.STATE = 1;
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
    }
}
