package com.blink.blinkiot.Start;

import android.content.ContentValues;
import android.content.Context;

import com.blink.blinkiot.Other.DeviceCode;
import com.blink.blinkiot.R;

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
        contentValues.put("deviceID", "RTL8711");
        contentValues.put("deviceName", context.getResources().getString(R.string.Sockets));
        contentValues.put("deviceMac", "11:11:11:11:11:11");
        contentValues.put("deviceOnline", String.valueOf(DeviceCode.ONLINE));
        contentValues.put("deviceOnlineStatus", DeviceCode.WIFI);
        contentValues.put("deviceSocketFlag", "S1,S2,S3,S4");
        return contentValues;
    }


    private static ContentValues getRGBLight(Context context) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID", "123456");
        contentValues.put("deviceID", "RTL8722");
        contentValues.put("deviceName", context.getResources().getString(R.string.RGBs));
        contentValues.put("deviceMac", "22:22:22:22:22:22");
        contentValues.put("deviceOnline", String.valueOf(DeviceCode.ONLINE));
        contentValues.put("deviceOnlineStatus", DeviceCode.WIFI);
        return contentValues;
    }

}
