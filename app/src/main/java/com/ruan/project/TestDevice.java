package com.ruan.project;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.DeviceURL;
import com.example.administrator.data_sdk.Database.LoadResouce;
import com.ruan.project.Moudle.UserDevice;
import com.ruan.project.Other.DeviceCode;
import com.ruan.project.Other.UDP.UDPConfig;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/18.
 */
public class TestDevice {

    public static ArrayList<ContentValues> getTestDeviceContent(Context context) {
        ArrayList<ContentValues> list = new ArrayList<>();

        list.add(getSocket(context));
        list.add(getRGBLight(context));

        return list;
    }

    private static ContentValues getSocket(Context context) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID", "123456");
        contentValues.put("deviceID", "8711");
        contentValues.put("deviceName", "模拟排插");
        contentValues.put("deviceMac", "11:11:11:11:11:11");
        contentValues.put("deviceOnline", String.valueOf(DeviceCode.ONLINE));
        contentValues.put("deviceOnlineStatus", DeviceCode.WIFI);
        return contentValues;
    }


    private static ContentValues getRGBLight(Context context) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID", "123456");
        contentValues.put("deviceID", "8722");
        contentValues.put("deviceName", "模拟RGB");
        contentValues.put("deviceMac", "22:22:22:22:22:22");
        contentValues.put("deviceOnline", String.valueOf(DeviceCode.ONLINE));
        contentValues.put("deviceOnlineStatus", DeviceCode.WIFI);
        return contentValues;
    }

}
