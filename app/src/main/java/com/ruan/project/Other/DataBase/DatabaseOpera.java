package com.ruan.project.Other.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.Nullable;

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

    /**
     * 插数据的时候可以接受json
     *
     * @param db         数据库的名称
     * @param table_name 数据库的表的名称
     * @param json       插进去的数据
     */
    public void DataInert(String db, String table_name, String json) {
        //模拟一下数据添加
        for (int i = 0; i < 10; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("deviceID", "1111111111111" + i);
            contentValues.put("deviceName", "设备" + i);
            contentValues.put("deviceModel", "000012" + i);
            contentValues.put("deviceType", "空调");
            contentValues.put("deviceTypeID", "111");
            contentValues.put("devicePic", "");
            //调用插数据的函数
            getDatabaseData.Insert(context, db, table_name, contentValues);
        }
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("deviceID", "1111111111111" + 111);
        contentValues1.put("deviceName", "设备" + 111);
        contentValues1.put("deviceModel", "000012" + 111);
        contentValues1.put("deviceType", "电饭煲");
        contentValues1.put("deviceTypeID", "222");
        contentValues1.put("devicePic", "");
        //调用插数据的函数
        getDatabaseData.Insert(context, db, table_name, contentValues1);

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("deviceID", "1111111111111" + 222);
        contentValues2.put("deviceName", "设备" + 222);
        contentValues2.put("deviceModel", "000012" + 222);
        contentValues2.put("deviceType", "洗衣机");
        contentValues2.put("deviceTypeID", "3333");
        contentValues2.put("devicePic", "");
        //调用插数据的函数
        getDatabaseData.Insert(context, db, table_name, contentValues2);

    }

    /**
     * 插数据的时候可以接受ContentValues
     *
     * @param db            数据库的名称
     * @param table_name    数据库的表的名称
     * @param contentValues 插进去的数据
     */
    public void DataInert(String db, String table_name, ContentValues contentValues) {
        getDatabaseData.Insert(context, db, table_name, contentValues);
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
     * @param db                数据库的名称
     * @param table_name        数据库的表名称
     * @param contentValues     数据库插数据的对象
     * @param update            是否更新            true(自动判断更新获取插数据)false只是插数据
     * @param key               更新数据的set后面的变量
     * @param values            更新数据的数据
     * @param whereclause       where后面的变量
     * @param whereargs         where后面的数据
     */
    public void DataInert(String db, String table_name, ContentValues contentValues, boolean update, String key, String[] values , String whereclause, String[] whereargs) {
        if (update) {
            if (key != null || !"".equals(key)) {
                getDatabaseData.Insert2Update(context, db, table_name, key, values, contentValues, whereclause, whereargs);
                return;
            }
        }
        getDatabaseData.Insert(context, db, table_name, contentValues);
    }

    /**
     * 获取设备的全部类别数据
     *
     * @param db         数据库的名称
     * @param table_name 数据库的表的名称
     */
    public ArrayList<Map<String, String>> DataQuerys(String db, String table_name) {
        return getDatabaseData.distinctQueryArray(context, db, table_name, new String[]{"deviceTypeID", "deviceType"}, "", null, "", "", "", "", true);
    }

    /**
     * 获取同一类别的全部型号数据
     *
     * @param db           数据库名称
     * @param table_name   数据库表的名称
     * @param key          查询的key
     * @param values       查询的values
     * @return
     */
    public ArrayList<Map<String, String>> DataQuerys(String db, String table_name, String key, String values) {
        return getDatabaseData.QueryArray(context, db, table_name, null, key + " = ?", new String[]{values}, "", "", "", "");
    }

    /**
     * 获取同一类别的全部型号数据
     *
     * @param db           数据库名称
     * @param table_name   数据库表的名称
     * @param whereclause          查询的key
     * @param whereargs       查询的values
     * @return
     */
    public ArrayList<Map<String, String>> DataQuerys(String db, String table_name, String whereclause, String[] whereargs) {
        return getDatabaseData.QueryArray(context, db, table_name, null, whereclause, whereargs, "", "", "", "");
    }

    /**
     * 获取同一类别的全部型号数据
     * @param db            数据库名称
     * @param table_name    数据库表的名称
     * @return
     */
    public ArrayList<Map<String , String>> DataQuery(String db , String table_name){
        return getDatabaseData.QueryArray(context , db , table_name , null , "" , null , "" , "" , "" , "");
    }

    /**
     * 删除数据库指定的数据，如果后面两个参数为空则说明删除整个表
     * @param db
     * @param table_name
     * @param whereclause
     * @param whereargs
     */
    public void DataDelete(String db , String table_name , String whereclause, String[] whereargs){
        getDatabaseData.Delete(context , db , table_name , whereclause , whereargs);
    }
}
