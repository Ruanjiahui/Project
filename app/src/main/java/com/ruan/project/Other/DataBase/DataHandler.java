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
    public static ContentValues getContentValues(String userID, String sceneID , ArrayList<Map<String, String>> list, String deviceName, String deviceModel , String IP , String Mac , int Port , String online) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("userID", userID);
        contentValues.put("sceneID", sceneID);
        contentValues.put("deviceID", list.get(0).get("deviceID"));
        contentValues.put("devicePic", list.get(0).get("devicePic"));
        contentValues.put("deviceName", deviceName);
        contentValues.put("deviceRemarks", deviceModel);
        contentValues.put("deviceMac", Mac);
        contentValues.put("deviceIP", IP);
        contentValues.put("devicePORT", Port);
        contentValues.put("deviceOnline" , online);

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

    /**
     * 封装数据库的数据
     * @param sceneID
     * @param sceneName
     * @return
     */
    public static ContentValues getSceneContentValues(String sceneID , String sceneName){
        ContentValues contentValues = new ContentValues();

        contentValues.put("sceneID", sceneID);
        contentValues.put("sceneName", sceneName);

        return contentValues;
    }

    /**
     * 封装数据库的数据
     * @param Mac
     * @param IP
     * @param PORT
     * @return
     */
    public static ContentValues getContentValues(String Mac , String IP , int PORT){
        ContentValues contentValues = new ContentValues();

        contentValues.put("deviceID", Mac);
        contentValues.put("deviceIP", IP);
        contentValues.put("devicePORT", PORT);

        return contentValues;
    }

}
