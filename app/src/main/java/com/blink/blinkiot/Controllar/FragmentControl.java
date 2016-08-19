package com.blink.blinkiot.Controllar;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.administrator.data_sdk.Database.GetDatabaseData;
import com.example.administrator.data_sdk.FileUtil.FileTool;
import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.data_sdk.SystemUtil.SystemTool;
import com.example.administrator.ui_sdk.DensityUtil;
import com.blink.blinkiot.Moudle.Item;
import com.blink.blinkiot.Moudle.Scene;
import com.blink.blinkiot.Moudle.Sort;
import com.blink.blinkiot.Moudle.User;
import com.blink.blinkiot.Moudle.UserDevice;
import com.blink.blinkiot.Other.DataBase.CreateDataBase;
import com.blink.blinkiot.Other.DataBase.DataHandler;
import com.blink.blinkiot.Other.DataBase.DatabaseOpera;
import com.blink.blinkiot.Other.DatabaseTableName;
import com.blink.blinkiot.Other.DeviceCode;
import com.blink.blinkiot.Other.HTTP.HttpURL;
import com.blink.blinkiot.Other.System.NetWork;
import com.blink.blinkiot.Other.Utils.ReadCityFile;
import com.blink.blinkiot.R;
import com.blink.blinkiot.Start.TestDevice;

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
        //判断是否存在用户表   如果没有存在则自动创建用户表
        if (!new CreateDataBase().FirstDataBase(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.AnalogyName)) {
            ArrayList<ContentValues> list = TestDevice.getTestDeviceContent(context);
            for (int i = 0; i < list.size(); i++)
                new DatabaseOpera(context).DataInert(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.AnalogyName, list.get(i));
        }

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


        //配置文件操作
        //将信息写入配置文件
        //默认为wifi情况下自动更新软件
        if (!FileTool.getProperties(context, HttpURL.ConfigName))
            FileTool.WriteProperties(context, HttpURL.ConfigName, "WIFI", true + "");

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
        HttpURL.STATE = SystemTool.isNetState(context);
        if (ListObj != null && ListObj.size() > 0) {
            for (int i = 0; i < ListObj.size(); i++) {
                userDevice = (UserDevice) ListObj.get(i);
                if (DeviceCode.UNONLINE == Integer.parseInt(userDevice.getDeviceOnline())) {
                    online = context.getResources().getString(R.string.UNONLINE);
                    list.add(getItem(userDevice.getDeviceName(), userDevice.getDeviceRemarks(), ImageTransformation.Resouce2Drawable(context, R.mipmap.cooker), online, userDevice.getDeviceMac(), ImageTransformation.Resouce2Drawable(context, R.mipmap.unonline), DensityUtil.dip2px(context, 25)));
                    continue;
                } else if (DeviceCode.ONLINE == Integer.parseInt(userDevice.getDeviceOnline())) {
                    online = context.getResources().getString(R.string.ONLINE);
                    if (DeviceCode.WIFI.equals(userDevice.getDeviceOnlineStatus()))
                        list.add(getItem(userDevice.getDeviceName(), userDevice.getDeviceRemarks(), ImageTransformation.Resouce2Drawable(context, R.mipmap.cooker), online, userDevice.getDeviceMac(), ImageTransformation.Resouce2Drawable(context, R.mipmap.wifionline), DensityUtil.dip2px(context, 25)));
                    else if (DeviceCode.CLOUD.equals(userDevice.getDeviceOnlineStatus()))
                        list.add(getItem(userDevice.getDeviceName(), userDevice.getDeviceRemarks(), ImageTransformation.Resouce2Drawable(context, R.mipmap.cooker), online, userDevice.getDeviceMac(), ImageTransformation.Resouce2Drawable(context, R.mipmap.cloudonline), DensityUtil.dip2px(context, 25)));
                    else if (HttpURL.STATE == NetWork.UNCONN) {
                        online = context.getResources().getString(R.string.UNONLINE);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("deviceOnline", String.valueOf(DeviceCode.UNONLINE));
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
