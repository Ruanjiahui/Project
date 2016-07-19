package com.ruan.project.Controllar;

import android.content.Context;
import android.os.Message;

import com.example.administrator.data_sdk.Database.GetDatabaseData;
import com.ruan.project.Interface.Other;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.Other.MyTimerTask;
import com.ruan.project.Other.UDP.ScanDevice;
import com.ruan.project.TimerHandler;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;

/**
 * Created by Administrator on 2016/7/19.
 */
public class UdpOpera implements TimerHandler {

    private Timer timer = null;
    private Other.HandlerMac handlerMac = null;
    //记录循环发送次数
    private int count = 0;
    private ArrayList<String> mac = null;
    private Context context = null;

    public UdpOpera(Context context){
        this.context = context;
    }

    /**
     * 扫描局域网中所有存在的设备   获取这些设备的IP，端口，和MAC地址
     * @param handlerMac
     */
    public void UDPDeviceScan(Other.HandlerMac handlerMac){
        //获取所有的设备ID
        //从数据库中获取设备的ID
        ArrayList<Map<String, String>> mapMac = new GetDatabaseData().QueryArray(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.DeviceTableName, new String[]{"deviceID"}, "", null, "", "", "", "");
        for (int i = 0; i < mapMac.size(); i++)
            mac.add(mapMac.get(i).get("deviceID"));

        this.handlerMac = handlerMac;
        //计时器，广播没一秒发送一次，总共发送10次
        timer = new Timer();
        timer.schedule(new MyTimerTask(this), 0, 1000);
    }

    @Override
    public void timerHandler(Message msg) {
        new ScanDevice(mac).Scanner(3000, "123", handlerMac);
        count++;
        if (count == 10)
            timer.cancel();
    }

    @Override
    public Message timerRun() {
        return new Message();
    }
}
