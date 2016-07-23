package com.ruan.project.Other.System;

/**
 * Created by Administrator on 2016/7/21.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.example.administrator.data_sdk.SystemUtil.SystemTool;
import com.ruan.project.Controllar.UdpOpera;
import com.ruan.project.Interface.UDPInterface;
import com.ruan.project.Other.HTTP.HttpURL;

public class WifiReceiver extends BroadcastReceiver implements UDPInterface.HandlerMac {

    private Context context = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
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
                //扫描局域网的设备
                //重写判断一下设备有没有在该局域网中
                new UdpOpera(context).UDPDeviceScan(this);

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
    @Override
    public void getMac(int position, Object[] objects) {
        //连接上wifi
        HttpURL.STATE = 1;
    }

    /**
     * 超时
     *
     * @param position
     * @param error
     */
    @Override
    public void Error(int position, int error) {
        //如果扫描超时则说明了不在同一个局域网里面
        if (SystemTool.isNetState(context) != 0)
            //如果手机可以连接网络的情况下说明只能用外网
            HttpURL.STATE = 2;
    }
}
