package com.ruan.project.Controllar;

import android.content.Context;
import android.os.Message;

import com.example.administrator.data_sdk.Database.GetDatabaseData;
import com.example.ruan.udp_sdk.UDP;
import com.ruan.project.Interface.UDPInterface;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.Other.MyTimerTask;
import com.ruan.project.Other.UDP.ScanDevice;
import com.example.ruan.udp_sdk.UDPInterface.UDPHandler;
import com.ruan.project.TimerHandler;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;

/**
 * Created by Administrator on 2016/7/19.
 */
public class UdpOpera implements TimerHandler, UDPHandler {

    private Timer timer = null;
    private UDPInterface.HandlerMac handlerMac = null;
    private UDPInterface.HandlerInfo handlerInfo = null;
    //记录循环发送次数
    private int count = 0;
    private ArrayList<String> mac = null;
    private Context context = null;
    //广播的发送的端口
    private int PORT = 3000;
    //广播发送的数据包
    private String data = "123";
    //广播发送间隔的时间
    private int time = 1000;

    public UdpOpera(Context context) {
        this.context = context;
    }

    /**
     * 扫描局域网中所有存在的设备   获取这些设备的IP，端口，和MAC地址
     *
     * @param handlerMac
     */
    public void UDPDeviceScan(UDPInterface.HandlerMac handlerMac) {
        mac = new ArrayList<>();
        //获取所有的设备ID
        //从数据库中获取设备的ID
        ArrayList<Map<String, String>> mapMac = new GetDatabaseData().QueryArray(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.DeviceTableName, new String[]{"deviceID"}, "", null, "", "", "", "");
        for (int i = 0; i < mapMac.size(); i++)
            mac.add(mapMac.get(i).get("deviceID"));

        this.handlerMac = handlerMac;
        //计时器，广播没一秒发送一次，总共发送10次
        timer = new Timer();
        timer.schedule(new MyTimerTask(this), 0, time);
    }

    @Override
    public void timerHandler(Message msg) {
        new ScanDevice(mac).Scanner(PORT, data, handlerMac);
        count++;
        if (count == 10) {
            timer.cancel();
        }
    }

    @Override
    public Message timerRun() {
        return new Message();
    }


    /**
     * 获取设备的信息
     *
     * @param IP          单播的IP
     * @param PORT        单播的端口
     * @param handlerInfo 单播处理接收数据的接口
     */
    public void UDPDeviceInfo(String IP, int PORT, byte[] buffer, UDPInterface.HandlerInfo handlerInfo) {
        this.handlerInfo = handlerInfo;
        UDP udp = new UDP();
        //单播发送数据包
        udp.uSend(IP, PORT, buffer);
        //监听设备返回的信息
        udp.uReviced(1, this);
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
        switch (position) {
            case 1:
                handlerInfo.getInfo(objects);
                break;
        }
    }

    /**
     * 接收出错或者超时
     *
     * @param position
     * @param error    0就是超时(30秒)
     */
    @Override
    public void Error(int position, int error) {

    }


}
