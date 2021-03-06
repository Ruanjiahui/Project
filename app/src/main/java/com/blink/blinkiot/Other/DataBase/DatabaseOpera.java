package com.blink.blinkiot.Other.DataBase;

import android.content.ContentValues;
import android.content.Context;

import com.blink.blinkiot.R;
import com.blink.blinkiot.Start.DeviceURL;
import com.example.administrator.data_sdk.Database.GetDatabaseData;

import java.util.ArrayList;
import java.util.Map;


/**
 * Created by Administrator on 2016/7/12.
 * <p/>
 * 数据库的所有操作包括增删改查等等操作
 */
public class DatabaseOpera {

    private Context context = null;
    private GetDatabaseData getDatabaseData = null;

    public DatabaseOpera(Context context) {
        this.context = context;
        getDatabaseData = new GetDatabaseData();
    }


    public void SceneInert(String db, String table_name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("scenePic", "");
        contentValues.put("sceneName", context.getResources().getString(R.string.Room));
        contentValues.put("sceneID", "0");

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("scenePic", "");
        contentValues1.put("sceneName", context.getResources().getString(R.string.LivingRoom));
        contentValues1.put("sceneID", "1");

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("scenePic", "");
        contentValues2.put("sceneName", context.getResources().getString(R.string.Kitchen));
        contentValues2.put("sceneID", "2");

        getDatabaseData.Insert(context, db, table_name, contentValues);
        getDatabaseData.Insert(context, db, table_name, contentValues1);
        getDatabaseData.Insert(context, db, table_name, contentValues2);
    }

    /**
     * 插数据的时候可以接受json
     *
     * @param db         数据库的名称
     * @param table_name 数据库的表的名称
     * @param json       插进去的数据
     */
    public void DataInert(String db, String table_name, String json) {
        //模拟一下数据添加
        ContentValues contentValues = new ContentValues();
        contentValues.put("deviceID", "RTL8711");
        contentValues.put("deviceName", context.getResources().getString(R.string.fourSocket));
        contentValues.put("deviceModel", "RTL8711");
        contentValues.put("deviceType", context.getResources().getString(R.string.Socket));
        contentValues.put("deviceTypeID", DeviceURL.Switch);
        contentValues.put("devicePic", "");
        //调用插数据的函数
        getDatabaseData.Insert(context, db, table_name, contentValues);


        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("deviceID", "RTL8722");
        contentValues1.put("deviceName", context.getResources().getString(R.string.RGBLight));
        contentValues1.put("deviceModel", "RTL8722");
        contentValues1.put("deviceType", context.getResources().getString(R.string.RGBLight));
        contentValues1.put("deviceTypeID", DeviceURL.RGBLight);
        contentValues1.put("devicePic", "");
        //调用插数据的函数
        getDatabaseData.Insert(context, db, table_name, contentValues1);

    }

    /**
     * 插数据的时候可以接受ContentValues
     *
     * @param db            数据库的名称
     * @param table_name    数据库的表的名称
     * @param contentValues 插进去的数据
     */
    public long DataInert(String db, String table_name, ContentValues contentValues) {
        return getDatabaseData.Insert(context, db, table_name, contentValues);
    }

    /**
     * 插数据的时候可以接受ContentValues
     *
     * @param db            数据库的名称
     * @param table_name    数据库的表的名称
     * @param contentValues 插进去的数据
     */
//    public void DataInert(String db, String table_name, ContentValues contentValues, boolean update, String key, String whereclause, String[] whereargs) {
//        if (update) {
//            if (key != null || !"".equals(key)) {
//                getDatabaseData.Insert2Update(context, db, table_name, key, contentValues.getAsString(key), contentValues, whereclause, whereargs);
//                return;
//            }
//        }
//        getDatabaseData.Insert(context, db, table_name, contentValues);
//    }

    /**
     * 插数据的时候可以接受ContentValues
     *
     * @param db            数据库的名称
     * @param table_name    数据库的表名称
     * @param contentValues 数据库插数据的对象
     * @param update        是否更新            true(自动判断更新获取插数据)false只是插数据
     * @param key           更新数据的set后面的变量
     * @param values        更新数据的数据
     * @param whereclause   where后面的变量
     * @param whereargs     where后面的数据
     */
    public int DataInert(String db, String table_name, ContentValues contentValues, boolean update, String key, String[] values, String whereclause, String[] whereargs) {
        if (update) {
            if (key != null || !"".equals(key)) {
                return getDatabaseData.Insert2Update(context, db, table_name, key, values, contentValues, whereclause, whereargs);
            }
        }
        return (int) getDatabaseData.Insert(context, db, table_name, contentValues);
    }

    /**
     * 获取设备的全部类别数据
     *
     * @param db         数据库的名称
     * @param table_name 数据库的表的名称
     */
    @Deprecated
    public ArrayList<Map<String, String>> DataQuerys(String db, String table_name) {
        return getDatabaseData.distinctQueryArray(context, db, table_name, new String[]{"deviceTypeID", "deviceType"}, "", null, "", "", "", "", true);
    }


    /**
     * 获取设备的全部类别数据(带有去重方法)
     *
     * @param db           数据库的名称
     * @param table_name   表名称
     * @param loadClass    封装的类
     * @param colums       获取的数目（如果为空则默认获取一项）
     * @param distinctType 去重的标识
     * @return
     */
    public ArrayList<Object> DataQuerys(String db, String table_name, Class loadClass, String[] colums, String distinctType, boolean SuperClass) {
        return getDatabaseData.distinctQueryArray(context, db, table_name, colums, "", null, "", "", "", "", true, distinctType, loadClass, SuperClass);
    }

    /**
     * 获取同一类别的全部型号数据
     *
     * @param db         数据库名称
     * @param table_name 数据库表的名称
     * @param key        查询的key
     * @param values     查询的values
     * @return
     */
    @Deprecated
    public ArrayList<Map<String, String>> DataQuerys(String db, String table_name, String key, String values) {
        return getDatabaseData.QueryArray(context, db, table_name, null, key + " = ?", new String[]{values}, "", "", "", "");
    }


    /**
     * 获取同一类别的全部型号数据
     *
     * @param db         数据库名称
     * @param table_name 数据库表的名称
     * @param key        查询的key
     * @param values     查询的values
     * @return
     */
    public ArrayList<Object> DataQuerys(String db, String table_name, String key, String values, Class loadClass, boolean SuperClass) {
        return getDatabaseData.QueryArray(context, db, table_name, null, key + " = ?", new String[]{values}, "", "", "", "", loadClass, SuperClass);
    }

    /**
     * 获取同一类别的全部型号数据
     *
     * @param db          数据库名称
     * @param table_name  数据库表的名称
     * @param whereclause 查询的key
     * @param whereargs   查询的values
     * @return
     */
    @Deprecated
    public ArrayList<Map<String, String>> DataQuerys(String db, String table_name, String whereclause, String[] whereargs) {
        return getDatabaseData.QueryArray(context, db, table_name, null, whereclause, whereargs, "", "", "", "");
    }


    /***
     * 获取同一个表的全部数据
     *
     * @param db            数据库名称
     * @param Table_Name    表名称
     * @param colums        列名称
     * @param selection     where后面的是字符串
     * @param selectionArgs where后面的变量
     * @param groupBy
     * @param having
     * @param orderBy
     * @param limit
     * @param loadclass     封装成的对象名称(类名)
     * @return
     */
    public ArrayList<Object> DataQuerys(String db, String Table_Name, String[] colums, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, Class loadclass, boolean SuperClass) {
        return getDatabaseData.QueryArray(context, db, Table_Name, colums, selection, selectionArgs, groupBy, having, orderBy, limit, loadclass, SuperClass);
    }

    /**
     * 获取同一类别的全部型号数据
     *
     * @param db         数据库名称
     * @param table_name 数据库表的名称
     * @return
     */
    public ArrayList<Map<String, String>> DataQuery(String db, String table_name) {
        return getDatabaseData.QueryArray(context, db, table_name, null, "", null, "", "", "", "");
    }

    /**
     * 删除数据库指定的数据，如果后面两个参数为空则说明删除整个表
     *
     * @param db
     * @param table_name
     * @param whereclause
     * @param whereargs
     */
    public void DataDelete(String db, String table_name, String whereclause, String[] whereargs) {
        getDatabaseData.Delete(context, db, table_name, whereclause, whereargs);
    }
}
