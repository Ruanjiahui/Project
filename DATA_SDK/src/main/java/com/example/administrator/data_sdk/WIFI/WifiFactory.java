package com.example.administrator.data_sdk.WIFI;

import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * Created by Administrator on 2016/7/18.
 */
public class WifiFactory {

    private static WifiManager wifiManager = null;

    public synchronized static WifiManager getInstance(Context context) {
        if (wifiManager == null) {
            wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        }
        return wifiManager;
    }
}
