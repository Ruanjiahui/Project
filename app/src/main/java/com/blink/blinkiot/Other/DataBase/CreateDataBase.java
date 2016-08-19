package com.blink.blinkiot.Other.DataBase;

import android.content.Context;

import com.example.administrator.data_sdk.Database.CheckDatabase;
import com.example.administrator.data_sdk.Database.Database;
import com.example.administrator.data_sdk.Database.Establish;
import com.blink.blinkiot.Other.DatabaseTableName;

/**
 * Created by Administrator on 2016/7/12.
 * <p/>
 * 这个类是专门用来创建数据表的
 * 通过Database.Check提供的接口实现自动创建数据库表
 */
public class CreateDataBase implements Database.Check {

    private Establish establish = null;
    private boolean state = false;

    /**
     * 检查数据库是否存在
     *
     * @param context
     * @param database
     * @param tablename
     * @return false则数据表没有存在，true则存在
     */
    public boolean FirstDataBase(Context context, String database, String tablename) {
        CheckDatabase.CheckData(context, database, tablename, this);
        return state;
    }


    /**
     * 自动创建表的接口
     *
     * @param database 数据库的名称
     * @param table    数据库的表名称
     * @param state    数据库的表是否存在
     * @return 返回一个创建数据的对象
     */
    @Override
    public Establish CreateTable(String database, String table, boolean state) {
        //如果state 返回为true说明已经存在数据库表，所以没有必要重新建表
        this.state = state;
        if (state)
            return null;
        else {
            //创建设备表
            if (table.equals(DatabaseTableName.DeviceTableName)) {
                establish = getDevice();
            } else if (table.equals(DatabaseTableName.UserTableName)) {
                establish = getUser();
            } else if (table.equals(DatabaseTableName.UserDeviceName)) {
                establish = getUserDevice();
            } else if (table.equals(DatabaseTableName.SceneName)) {
                establish = getScene();
            } else if (table.equals(DatabaseTableName.CityName)) {
                establish = getCity();
            } else if (table.equals(DatabaseTableName.ConfigName)) {
                establish = getConfig();
            } else if (table.equals(DatabaseTableName.AnalogyName)) {
                establish = getAnalogyUserDevice();
            }
            return establish;
        }
    }


//    设备ID
//    设备名称
//    设备型号
//    设备类别
//    设备类别ID
//    设备图片

    /**
     * 设备表
     *
     * @return
     */
    private Establish getDevice() {
        Establish establish = new Establish();

        establish.put("deviceID", "varchar(20) not null");
        establish.put("deviceName", "varchar(100)");
        establish.put("deviceModel", "varchar(50)");
        establish.put("deviceType", "varchar(50)");
        establish.put("deviceTypeID", "varchar(50)");
        establish.put("devicePic", "varchar(100)");
        return establish;
    }


//    用户ID
//    用户名称
//    用户密码
//    用户性别
//    用户生日
//    用户身高
//    用户体重
//    用户电话
//    用户登陆状态

    /**
     * 用户表
     *
     * @return
     */
    private Establish getUser() {
        Establish establish = new Establish();

        establish.put("userID", "varchar(20) not null");
        establish.put("userName", "varchar(100)");
        establish.put("userPassword", "varchar(100)");
        establish.put("userSex", "varchar(2)");
        establish.put("userBirthday", "varchar(20)");
        establish.put("userHeight", "varchar(10)");
        establish.put("userWeight", "varchar(10)");
        establish.put("userPhone", "varchar(11)");
        establish.put("userLogin", "varchar(5)");
        establish.put("userCity", "varchar(10)");

        return establish;
    }

//    用户ID
//    设备ID
//    设备图片
//    设备名称
//    设备备注
//    场景ID
//    设备Ip
//    设备端口
//    设备Mac

    /**
     * 用户设备表
     *
     * @return
     */
    private Establish getUserDevice() {
        Establish establish = new Establish();

        establish.put("userID", "varchar(20) not null");
        establish.put("deviceID", "varchar(50)");
        establish.put("devicePic", "varchar(50)");
        establish.put("deviceName", "varchar(50)");
        establish.put("deviceRemarks", "varchar(50)");
        establish.put("sceneID", "varchar(50)");
        establish.put("deviceIP", "varchar(50)");
        establish.put("devicePORT", "varchar(10)");
        establish.put("deviceMac", "varchar(50)");
        establish.put("deviceOnline", "varchar(10)");
        establish.put("deviceOnlineStatus", "varchar(10)");


        return establish;
    }


    /**
     * 模拟用户设备表
     *
     * @return
     */
    private Establish getAnalogyUserDevice() {
        Establish establish = new Establish();

        establish.put("userID", "varchar(20) not null");
        establish.put("deviceID", "varchar(50)");
        establish.put("devicePic", "varchar(50)");
        establish.put("deviceName", "varchar(50)");
        establish.put("deviceRemarks", "varchar(50)");
        establish.put("sceneID", "varchar(50)");
        establish.put("deviceIP", "varchar(50)");
        establish.put("devicePORT", "varchar(10)");
        establish.put("deviceMac", "varchar(50)");
        establish.put("deviceOnline", "varchar(10)");
        establish.put("deviceOnlineStatus", "varchar(10)");


        return establish;
    }

    /**
     * 场景表
     *
     * @return
     */
    private Establish getScene() {
        Establish establish = new Establish();

        establish.put("sceneName", "varchar(50)");
        establish.put("sceneID", "varchar(50)");
        establish.put("scenePic", "varchar(50)");

        return establish;
    }

    /**
     * 城市表
     *
     * @return
     */
    private Establish getCity() {
        Establish establish = new Establish();

        establish.put("cityName", "varchar(50)");

        return establish;
    }

    /**
     * 城市表
     *
     * @return
     */
    private Establish getConfig() {
        Establish establish = new Establish();

        establish.put("wifi", "varchar(10)");
        establish.put("Name", "varchar(10)");

        return establish;
    }

}
