package com.ruan.project.Other.DataBase;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/13.
 */
public class DataHandler {

    /**
     * 整理插入数据库的数据  插入用户设备表的数据
     * @param userID            用户ID
     * @param list              设备的数据
     * @param deviceName        设备的名称
     * @param deviceModel       设备的类型
     * @return
     */
    public static ContentValues getContentValues(String userID, ArrayList<Map<String, String>> list, String deviceName, String deviceModel) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("userID", userID);
        contentValues.put("deviceID", list.get(0).get("deviceID"));
        contentValues.put("devicePic", list.get(0).get("devicePic"));
        contentValues.put("deviceName", deviceName);
        contentValues.put("deviceRemarks", deviceModel);

        return contentValues;
    }

    /**
     * 封装数据库的数据
     * @param key
     * @param values
     * @return
     */
    public static ContentValues getContentValues(String key , String values){
        ContentValues contentValues = new ContentValues();

        contentValues.put(key, values);

        return contentValues;
    }
}
