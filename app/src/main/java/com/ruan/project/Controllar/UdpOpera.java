package com.ruan.project.Controllar;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.example.administrator.data_sdk.Database.GetDatabaseData;
import com.example.ruan.udp_sdk.UDP;
import com.ruan.project.Interface.UDPInterface;
import com.ruan.project.Other.DataBase.CreateDataBase;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.Other.MyTimerTask;
import com.ruan.project.Other.UDP.OnlineDeveice;
import com.ruan.project.Other.UDP.ScanDevice;
import com.example.ruan.udp_sdk.UDPInterface.UDPHandler;
import com.ruan.project.Other.UDP.UDPConfig;
import com.ruan.project.TimerHandler;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;

/**
 * Created by Administrator on 2016/7/19.
 */
public class UdpOpera implements UDPHandler {

    private UDPInterface.HandlerInfo handlerInfo = null;
    //统计检测设备是否在线
    private Context context = null;

    public UdpOpera(Context context) {
        this.context = context;
    }

    /**
     * 扫描局域网中所有存在的设备   获取这些设备的IP，端口，和MAC地址
     *
     * @param handlerMac
     */
    public void UDPDeviceScan(UDPInterface.HandlerMac handlerMac , String data) {
        //计时器，广播没一秒发送一次，总共发送5次
        new ScanDevice().Scanner(UDPConfig.PORT, data, handlerMac, UDPConfig.count);
    }

    /**
     * 获取设备的信息
     *
     * @param IP          单播的IP
     * @param PORT        单播的端口
     * @param handlerInfo 单播处理接收数据的接口
     * @param count       单播的次数
     */
    public void UDPDeviceInfo(String IP, int PORT, byte[] buffer, UDPInterface.HandlerInfo handlerInfo, int count) {
        this.handlerInfo = handlerInfo;
        UDP udp = new UDP();
        //单播发送数据包
        udp.uSend(IP, PORT, buffer, count);
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
