package com.ruan.project.Controllar;

import android.content.Context;

import com.example.administrator.data_sdk.Database.CreateTable;
import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.ruan.project.Moudle.User;
import com.ruan.project.Other.DataBase.CreateDataBase;
import com.ruan.project.Other.DataBase.DataHandler;
import com.ruan.project.Other.DataBase.DatabaseOpera;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/14.
 */
public class FragmentDatabase {

    public static void DataBaseHandler(Context context){
        //判断是否存在用户表   如果没有存在则自动创建用户表
        new CreateDataBase().FirstDataBase(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName);
        //判断是否存在场景表   如果没有存在则自动创建场景表
        new CreateDataBase().FirstDataBase(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.SceneName);
        //模拟插个人数据库进去
        new DatabaseOpera(context).DataInert(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName, DataHandler.getContentValues("userID", "123456"), true, "userID = ?", new String[]{"123456"} , "userID = ?", new String[]{"123456"});
        //获取个人用户的数据
        User.toModel(new DatabaseOpera(context).DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName  , "" , new String[]{}));
    }

    /**
     * 获取用户设备的数据
     * @param context
     * @return
     */
    public static ArrayList<Map<String , String>> getDeviceData(Context context){
        ArrayList<Map<String , String>> map = null;
        //首先判断本地有没有数据库，没有则直接获取服务器的数据添加到本地数据库并且直接创建数据库
        if (new CreateDataBase().FirstDataBase(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName)) {
            //本地有数据库，
            //则获取本地数据库的数据，同时访问数据库的数据库将最新的数据库获取写到本地数据库进行更新
            map = new DatabaseOpera(context).DataQuery(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName);

        } else {
            //没有数据库这个时候已经创建完毕数据库
            //接下来就是从服务器上面获取数据
        }
        return map;
    }

}
