package com.blink.blinkiot.Other.UDP;

import com.example.ruan.udp_sdk.Thread.UDPReviced;
import com.example.ruan.udp_sdk.UDP;
import com.example.ruan.udp_sdk.UDPConfig;
import com.example.ruan.udp_sdk.UDPListen;
import com.blink.blinkiot.Interface.UDPInterface;

/**
 * Created by Administrator on 2016/7/19.
 * <p/>
 * 检测设备在不在线
 */
public class OnlineDeveice extends UDP implements UDPListen.UDPHandler {


    private UDPInterface.HandlerMac handlerMac = null;


    /**
     * 实例化对象
     */
    public OnlineDeveice() {
        super();
    }


    public void Check(int position, String IP, int PORT, String msg, UDPInterface.HandlerMac handlerMac, int count) {
        this.handlerMac = handlerMac;
        uReviced(position, this);
        uSend(IP, PORT, msg.getBytes(), count);
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
        //判断链表里面是不是存在相同的mac地址，不存在则添加，存在则不操作
        handlerMac.getMac(position, objects);
    }

    /**
     * 接收出错或者超时
     *
     * @param position
     * @param error    0就是超时(30秒)
     */
    @Override
    public void Error(int position, int error) {
        handlerMac.Error(position, error);
    }
}
