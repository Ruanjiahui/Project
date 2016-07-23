package com.ruan.project.View.Activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.Interface.HttpInterface;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.ruan.udp_sdk.UDP;
import com.ruan.project.Interface.UDPInterface;
import com.ruan.project.Other.DataBase.DatabaseOpera;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.Other.HTTP.HttpData;
import com.ruan.project.Other.HTTP.HttpURL;
import com.ruan.project.Other.UDP.ControlDevice;
import com.ruan.project.Other.UDP.UDPData;
import com.ruan.project.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/20.
 */
public class DeviceControl extends BaseActivity implements UDPInterface.HandlerMac, HttpInterface.HttpHandler {
    private UDP udp = null;
    private ArrayList<Map<String, String>> map = null;
    private String deviceID = null;
    private String IP = null;
    private int PORT = 0;


    private View view = null;
    private ControlDevice controlDevice = null;

    /**
     * Start()
     */
    @Override
    public void init() {

        deviceID = getIntent().getExtras().getString("data");

        view = LayoutInflater.from(context).inflate(R.layout.devicecontrol, null);

        setTopColor(R.color.Blue);
        setTopTitleColor(R.color.White);
        setTitle("设备控制");
        setRightTitleVisiable(false);
        setLeftTitleColor(R.color.White);


//        new ColorPickerDialog(context , "请选择" , this).show();
        map = new DatabaseOpera(context).DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, "deviceID = ?", new String[]{deviceID});

        IP = map.get(0).get("deviceIP");
        PORT = Integer.parseInt(map.get(0).get("devicePORT"));

        controlDevice = new ControlDevice(IP, PORT);

        switch (HttpURL.STATE) {
            case 1:
                //通过udp进行控制
                controlDevice.UDPControl(1, new UDPData().setRGBColor("1.000000", "1.000000", "1.000000", "1.000000"), this);
                break;
            case 2:
                //通过云端进行控制
                controlDevice.HTTPControl(HttpURL.ControlDevice, HttpData.getControlDevice(map.get(0).get("deviceMac"), "123"), this, 0);
                break;
        }
        setContent(view);
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
        Log.e("Ruan", Arrays.toString(objects) + "--");
    }

    /**
     * 接收出错或者超时
     *
     * @param position
     * @param error    0就是超时(30秒)
     */
    @Override
    public void Error(int position, int error) {
        Log.e("Ruan", error + "--超时");
    }

    /**
     * 这个是处理Http返回来的结果
     *
     * @param position 请求的表示
     * @param result   请求返回的结果
     */
    @Override
    public void handler(int position, String result) {
        Log.e("Ruan", result + "--");
    }
}
