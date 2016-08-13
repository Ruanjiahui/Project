package com.ruan.project.Controllar;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.data_sdk.Database.CreateTable;
import com.example.administrator.data_sdk.Database.Database;
import com.example.administrator.data_sdk.Database.GetDatabaseData;
import com.example.administrator.data_sdk.Database.LoadResouce;
import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.ui_sdk.DensityUtil;
import com.ruan.project.Moudle.Item;
import com.ruan.project.Moudle.Scene;
import com.ruan.project.Moudle.Sort;
import com.ruan.project.Moudle.User;
import com.ruan.project.Moudle.UserDevice;
import com.ruan.project.Other.DataBase.CreateDataBase;
import com.ruan.project.Other.DataBase.DataHandler;
import com.ruan.project.Other.DataBase.DatabaseOpera;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.Other.HTTP.HttpURL;
import com.ruan.project.Other.System.NetWork;
import com.ruan.project.Other.Utils.ReadCityFile;
import com.ruan.project.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/14.
 */
public class FragmentControl {

    private Context context = null;

    public FragmentControl(Context context) {
        this.context = context;
    }

    public static void DataBaseHandler(Context context) {
        //判断是否存在用户表   如果没有存在则自动创建用户表
        new CreateDataBase().FirstDataBase(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName);
        //判断是否存在场景表   如果没有存在则自动创建场景表
        new CreateDataBase().FirstDataBase(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.SceneName);
        //模拟插个人数据库进去
        new DatabaseOpera(context).DataInert(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName, DataHandler.getContentValues("userID", "123456"), true, "userID = ?", new String[]{"123456"}, "userID = ?", new String[]{"123456"});
        //判断是否存在用户表   如果没有存在则自动创建用户表
        if (!new CreateDataBase().FirstDataBase(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.DeviceTableName)) {
            new DatabaseOpera(context).DataInert(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.DeviceTableName, "");
        }
        //判断是否存在城市表   如果没有存在则自动创建城市表
        if (!new CreateDataBase().FirstDataBase(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.CityName)) {
            ArrayList<Sort> list = ReadCityFile.readCity(context.getResources().openRawResource(R.raw.city));
            for (int i = 0; i < list.size(); i++) {
                Sort sort = list.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put("cityName", sort.getCityName());
                //添加数据
                new DatabaseOpera(context).DataInert(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.CityName, contentValues);
            }
        }

        //获取用户信息
        ArrayList<Object> list = new DatabaseOpera(context).DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName, "userID", "123456", User.class, false);
        User.setUser((User) list.get(0));
        //获取个人用户的数据
//        User.toModel(new DatabaseOpera(context).DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName, "", new String[]{}));
    }

    /**
     * 获取用户设备的数据
     *
     * @param context
     * @return
     */
    public static ArrayList<Map<String, String>> getDeviceData(Context context) {
        ArrayList<Map<String, String>> map = null;
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

    /**
     * 获取用户设备的全部信息
     *
     * @param context
     * @return
     */
    public static ArrayList<Object> getUserDeviceData(Context context) {
        //获取所有的设备ID
        //从数据库中获取设备的ID
        new CreateDataBase().FirstDataBase(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName);

        return new DatabaseOpera(context).DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, null, "", null, "", "", "", "", UserDevice.class, true);//.QueryArray(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, null, "", null, "", "", "", "");
    }

    /**
     * 通过数据库封装成listview的集合
     *
     * @param ListObj
     * @return
     */
    public ArrayList<Object> setFragment1List(ArrayList<Object> ListObj) {
        ArrayList<Object> list = new ArrayList<>();
        UserDevice userDevice;
        String online = null;
        if (ListObj != null && ListObj.size() > 0) {
            for (int i = 0; i < ListObj.size(); i++) {
                userDevice = (UserDevice) ListObj.get(i);
                if (userDevice.getDeviceOnline().equals("1")) {
                    online = "离线";
                    list.add(getItem(userDevice.getDeviceName(), userDevice.getDeviceRemarks(), ImageTransformation.Resouce2Drawable(context, R.mipmap.cooker), online, userDevice.getDeviceMac(), ImageTransformation.Resouce2Drawable(context, R.mipmap.unonline), DensityUtil.dip2px(context, 25)));
                    continue;
                } else if (userDevice.getDeviceOnline().equals("2")) {
                    online = "在线";
                    if (HttpURL.STATE == NetWork.WIFI)
                        list.add(getItem(userDevice.getDeviceName(), userDevice.getDeviceRemarks(), ImageTransformation.Resouce2Drawable(context, R.mipmap.cooker), online, userDevice.getDeviceMac(), ImageTransformation.Resouce2Drawable(context, R.mipmap.wifionline), DensityUtil.dip2px(context, 25)));
                    if (HttpURL.STATE == NetWork.INTNET)
                        list.add(getItem(userDevice.getDeviceName(), userDevice.getDeviceRemarks(), ImageTransformation.Resouce2Drawable(context, R.mipmap.cooker), online, userDevice.getDeviceMac(), ImageTransformation.Resouce2Drawable(context, R.mipmap.cloudonline), DensityUtil.dip2px(context, 25)));
                    if (HttpURL.STATE == NetWork.UNCONN) {
                        online = "离线";
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("deviceOnline", "1");
                        list.add(getItem(userDevice.getDeviceName(), userDevice.getDeviceRemarks(), ImageTransformation.Resouce2Drawable(context, R.mipmap.cooker), online, userDevice.getDeviceMac(), ImageTransformation.Resouce2Drawable(context, R.mipmap.unonline), DensityUtil.dip2px(context, 25)));
                        new GetDatabaseData().Update(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, contentValues, "deviceMac = ?", new String[]{userDevice.getDeviceMac()});
                    }
                    continue;
                }
            }
        }
        return list;
    }

    public ArrayList<Object> getFragment2List(ArrayList<Object> ListObj) {
        Scene scene = null;
        ArrayList<Object> list = new ArrayList<>();

        for (int i = 0; i < ListObj.size(); i++) {
            scene = (Scene) ListObj.get(i);
            list.add(getItem(scene.getSceneName(), context.getResources().getDrawable(R.mipmap.cooker)));
        }
        return list;
    }


    private Object getItem(String title, String subtitile, Drawable Image, String rightTitle, String rightTitle1, Drawable RightImage, int height) {
        Item item = new Item();
        item.setHomeImage(Image);
        item.setHomeRelative(height);
        item.setHomeRight(rightTitle);
        item.setHomeRight1(rightTitle1);
        item.setHomeRightImage(RightImage);
        item.setHomeSubText(subtitile);
        item.setHomeText(title);
        return item;
    }

    private Object getItem(String title, Drawable drawable) {
        Item item = new Item();
        item.setHomeText(title);
        item.setHomeImage(drawable);
        return item;
    }

}
