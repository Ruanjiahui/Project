package com.ruan.project.View.Activity;

import android.util.Log;

import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.ruan.udp_sdk.UDP;
import com.example.ruan.udp_sdk.UDPInterface;
import com.ruan.project.Other.DataBase.DatabaseOpera;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/20.
 */
public class DeviceControl extends BaseActivity implements UDPInterface.UDPHandler{
    private UDP udp = null;
    private ArrayList<Map<String, String>> map = null;
    private String deviceID = null;

    /**
     * Start()
     */
    @Override
    public void init() {

        deviceID = getIntent().getExtras().getString("data");

        setTopColor(R.color.Blue);
        setTopTitleColor(R.color.White);
        setTitle("设备控制");
        setRightTitleVisiable(false);
        setLeftTitleColor(R.color.White);

        map = new DatabaseOpera(context).DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, "deviceID = ?", new String[]{deviceID});

//        Log.e("Ruan" , map.get(0).get("deviceIP") + "-" + Integer.parseInt(map.get(0).get("devicePORT")));
        udp = new UDP();
//        map.get(0).get("deviceIP")
        udp.uSend("192.160.25.116" , Integer.parseInt(map.get(0).get("devicePORT")) , "LB-Link".getBytes());
        udp.uReviced(1 , this);
    }

    /**
     * 处理接收消息的接口
     * //创建一个Object对象数组
     * //0 储存接收的数据
     * //1 储存接收数据的长度
     * //2 储存接收的地址
     * //3 储存接收的端口
     *
     * @param position 调用接口的表示
     * @param objects  返回的数组
     */
    @Override
    public void Handler(int position, Object[] objects) {
        Log.e("Ruan" , new String((byte[])objects[0] ,0 , (int)objects[1]) + "---");
    }

    /**
     * 接收出错或者超时
     *
     * @param position
     * @param error    0就是超时(30秒)
     */
    @Override
    public void Error(int position, int error) {
        Log.e("Ruan" ,  error + "--超时");
    }
}
