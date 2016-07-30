package com.ruan.project.Controllar;

import android.content.ContentValues;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.administrator.Interface.HttpInterface;
import com.example.administrator.data_sdk.Database.GetDatabaseData;
import com.example.administrator.http_sdk.HTTP;
import com.ruan.project.Interface.DataHandler;
import com.ruan.project.Interface.UDPInterface;
import com.ruan.project.MainActivity;
import com.ruan.project.Moudle.UserDevice;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.Other.HTTP.HttpURL;
import com.ruan.project.Other.JSON.jsonMac;
import com.ruan.project.Other.UDP.FormatData;
import com.ruan.project.Other.UDP.OnlineDeveice;
import com.ruan.project.R;
import com.ruan.project.View.Fragment.Fragment1;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/21.
 * <p/>
 * 检测在不在线分两种方式，
 * 第一种是局域网的 udp单播
 * 第二种是通过云端 判断
 */
public class CheckOnline implements UDPInterface.HandlerMac, HttpInterface.HttpHandler {

    private ArrayList<UserDevice> map = null;
    private Context context = null;
    private DataHandler dataHandler = null;
    private FragmentManager fragmentManager = null;
    private UserDevice userDevice = null;

    private String Online = "deviceOnline";
    private String Mac = "deviceMac";
    private String On = "2";
    private String UnOn = "1";


    public CheckOnline(Context context, FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.context = context;
        userDevice = new UserDevice();
        map = userDevice.getMoudle(FragmentDatabase.getUserDeviceData(context));
    }

    public CheckOnline(Context context, DataHandler dataHandler) {
        this.context = context;
        this.dataHandler = dataHandler;
        userDevice = new UserDevice();
        map = userDevice.getMoudle(FragmentDatabase.getUserDeviceData(context));
    }

    /**
     * 这个方式通过udp单播进行设备检测是否在线
     */
    public void UDPCheck() {
        UDPCheck(this);
    }

    /**
     * 这个方式通过http云端请求进行设备检测是否在线
     */
    public void HTTPCheck() {
//        map = FragmentDatabase.getUserDeviceData(context);
        new HTTP(this, HttpURL.CheckOnline, FormatData.getHttpPOSTUserDevice(map), 0);
    }


    /**
     * 内网检测设备是否在线
     *
     * @param handlerMac
     */
    public void UDPCheck(UDPInterface.HandlerMac handlerMac) {
        for (int i = 0; i < map.size(); i++) {
            new OnlineDeveice().Check(i, map.get(i).getDeviceIP(), Integer.parseInt(map.get(i).getDevicePORT()), map.get(i).getDeviceMac(), handlerMac, 1);
            //计时器，广播没一秒发送一次，总共发送1次
        }
    }

    /**
     * 这个方法获取Mac值
     * //0 储存接收的数据
     * //1 储存接收数据的长度
     * //2 储存接收的地址
     * //3 储存接收的端口
     *
     * @param position 标示
     * @param objects  这个Object数组里面包含一些列的设备信息
     */
    @Override
    public void getMac(int position, Object[] objects) {
        for (int i = 0; i < map.size(); i++) {
            if (position == i && objects[0] != null) {
                ContentValues contentValues = new ContentValues();
                //2代表在线1代表不在线
                contentValues.put(Online, On);
                //判断在线之后将在线状态修改到数据库
                new GetDatabaseData().Update(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, contentValues, Mac + " = ?", new String[]{map.get(position).getDeviceMac()});
            }
        }
        ReData();
    }

    /**
     * 超时
     *
     * @param position
     * @param error
     */
    @Override
    public void Error(int position, int error) {
        new CheckOnline(context, fragmentManager).HTTPCheck();
//        ContentValues contentValues = new ContentValues();
//        //2代表在线1代表不在线
//        contentValues.put("deviceOnline", "1");
//        //判断在线之后将在线状态修改到数据库
//        new GetDatabaseData().Update(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, contentValues, "deviceMac = ?", new String[]{map.get(position).get("deviceMac")});
//        ReData();
    }

    /**
     * 这个是处理Http返回来的结果
     *
     * @param position 请求的表示
     * @param result   请求返回的结果
     */
    @Override
    public void handler(int position, String result) {
        if (result != null) {
            ArrayList<String> list = jsonMac.getMacArrays(result);

            //首先将所有设备登录状态设置为不在线
            ContentValues contentValues = new ContentValues();
            //2代表在线1代表不在线
            contentValues.put(Online, UnOn);
            new GetDatabaseData().Update(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, contentValues, "", null);

            //如果返回数据里面有该设备就设置为在线状态
            for (int i = 0; i < list.size(); i++) {
                contentValues = new ContentValues();
                //2代表在线1代表不在线
                contentValues.put(Online, On);
                //判断在线之后将在线状态修改到数据库
                new GetDatabaseData().Update(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, contentValues, Mac + " = ?", new String[]{list.get(i)});
            }
            ReData();
        }
    }

    private void ReData() {
        if (fragmentManager != null) {
            if (MainActivity.isRefresh)
                intentFragment(fragmentManager);
        } else if (dataHandler != null)
            dataHandler.ReStartData();
    }

    /**
     * 跳转Fragment
     *
     * @param fragmentManager
     */
    private void intentFragment(FragmentManager fragmentManager) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.content, new Fragment1(), "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commitAllowingStateLoss();
    }
}
