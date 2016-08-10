package com.ruan.project.Controllar;

import android.content.ContentValues;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.Interface.HttpInterface;
import com.example.administrator.data_sdk.Database.GetDatabaseData;
import com.example.administrator.http_sdk.HTTP;
import com.google.gson.Gson;
import com.ruan.project.Interface.DataHandler;
import com.ruan.project.Interface.UDPInterface;
import com.ruan.project.MainActivity;
import com.ruan.project.Moudle.CheckMac;
import com.ruan.project.Moudle.UserDevice;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.Other.HTTP.HttpURL;
import com.ruan.project.Other.System.NetWork;
import com.ruan.project.Other.UDP.FormatData;
import com.ruan.project.Other.UDP.OnlineDeveice;
import com.ruan.project.R;
import com.ruan.project.View.Fragment.Fragment1;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/21.
 * <p/>
 * 检测在不在线分两种方式，
 * 第一种是局域网的 udp单播
 * 第二种是通过云端 判断
 */
public class CheckOnline implements UDPInterface.HandlerMac, HttpInterface.HttpHandler {

    //    private ArrayList<UserDevice> map = null;
    private Context context = null;
    private DataHandler dataHandler = null;
    private FragmentManager fragmentManager = null;
    private UserDevice userDevice = null;


    private ArrayList<Object> ListObj = null;

    private String Online = "deviceOnline";
    private String Mac = "deviceMac";
    private String On = "2";
    private String UnOn = "1";
    private boolean udporhttp = false;


    public CheckOnline(Context context, FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.context = context;
        ListObj = FragmentControl.getUserDeviceData(context);
//        userDevice = new UserDevice();
//        map = userDevice.getMoudle();
    }

    public CheckOnline(Context context, DataHandler dataHandler) {
        this.context = context;
        this.dataHandler = dataHandler;
        ListObj = FragmentControl.getUserDeviceData(context);
//        userDevice = new UserDevice();
//        map = userDevice.getMoudle(FragmentControl.getUserDeviceData(context));
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
        if (ListObj != null && ListObj.size() != 0)
            new HTTP(this, HttpURL.CheckOnline, FormatData.getHttpPOSTUserDevice(ListObj), 0);
    }


    /**
     * 内网检测设备是否在线
     *
     * @param handlerMac
     */
    public void UDPCheck(UDPInterface.HandlerMac handlerMac) {
        for (int i = 0; i < ListObj.size(); i++) {
            UserDevice userDevice = (UserDevice) ListObj.get(i);
            new OnlineDeveice().Check(i, userDevice.getDeviceIP(), Integer.parseInt(userDevice.getDevicePORT()), userDevice.getDeviceMac(), handlerMac, 1);
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
        if (objects != null) {
            if (objects[0] != null) {
                HttpURL.STATE = NetWork.WIFI;
                setUnOnline();
                for (int i = 0; i < ListObj.size(); i++) {
                    if (position == i) {
                        UserDevice userDevice = (UserDevice) ListObj.get(i);
                        setOnline(userDevice.getDeviceMac());
                    }
                }
                ReData();
            } else
                HTTPCheck();
        } else
            HTTPCheck();
    }

    /**
     * 超时
     *
     * @param position
     * @param error
     */
    @Override
    public void Error(int position, int error) {
        HTTPCheck();
    }

    private void setOnline(String mac) {
        ContentValues contentValues = new ContentValues();
        //2代表在线1代表不在线
        contentValues.put(Online, On);
        //判断在线之后将在线状态修改到数据库
        new GetDatabaseData().Update(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, contentValues, Mac + " = ?", new String[]{mac});
    }

    private void setUnOnline() {
        //首先将所有设备登录状态设置为不在线
        ContentValues contentValues = new ContentValues();
        //2代表在线1代表不在线
        contentValues.put(Online, UnOn);
        new GetDatabaseData().Update(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, contentValues, "", null);
    }

    /**
     * 这个是处理Http返回来的结果
     *
     * @param position 请求的表示
     * @param result   请求返回的结果
     */
    @Override
    public void handler(int position, String result) {
        setUnOnline();
        if (result != null) {
            HttpURL.STATE = NetWork.INTNET;
            Gson gson = new Gson();
            CheckMac checkMac = gson.fromJson(result, CheckMac.class);
            if (checkMac != null) {
                //如果返回数据里面有该设备就设置为在线状态
                for (int i = 0; i < checkMac.getList().size(); i++) {
                    for (int r = 0 ; r < ListObj.size() ; r++) {
                        userDevice = (UserDevice) ListObj.get(r);
                        if (checkMac.getList().get(i).equals(userDevice.getDeviceMac())) {
                            setOnline(userDevice.getDeviceMac());
                            break;
                        }
                    }
                }
            }
        }
        ReData();
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
