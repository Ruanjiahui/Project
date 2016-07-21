package com.ruan.project.View.Activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.ui_sdk.View.ColorPickerDialog;
import com.example.ruan.udp_sdk.UDP;
import com.example.ruan.udp_sdk.UDPInterface;
import com.ruan.project.Other.DataBase.DatabaseOpera;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.Other.UDP.UDPData;
import com.ruan.project.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/20.
 */
public class DeviceControl extends BaseActivity implements UDPInterface.UDPHandler , ColorPickerDialog.OnColorChangedListener{
    private UDP udp = null;
    private ArrayList<Map<String, String>> map = null;
    private String deviceID = null;


    private View view = null;

    /**
     * Start()
     */
    @Override
    public void init() {

        deviceID = getIntent().getExtras().getString("data");

        view = LayoutInflater.from(context).inflate(R.layout.devicecontrol , null);

        setTopColor(R.color.Blue);
        setTopTitleColor(R.color.White);
        setTitle("设备控制");
        setRightTitleVisiable(false);
        setLeftTitleColor(R.color.White);


        new ColorPickerDialog(context , "请选择" , this).show();


        map = new DatabaseOpera(context).DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, "deviceID = ?", new String[]{deviceID});

//        Log.e("Ruan" , map.get(0).get("deviceIP") + "-" + Integer.parseInt(map.get(0).get("devicePORT")));
//        udp = new UDP();
//        map.get(0).get("deviceIP")
        Log.e("Ruan" , new UDPData().setRGBColor("1.000000" , "1.000000" , "1.000000" , "1.000000"));
//        udp.uSend("172.24.192.1" , 9999 , new UDPData().setRGBColor("1.000000" , "1.000000" , "1.000000" , "1.000000").getBytes());
//        udp.uReviced(1 , this);

        setContent(view);
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

    /**
     * 回调函数
     *
     * @param color 选中的颜色
     */
    @Override
    public void colorChanged(int color) {

    }
}
