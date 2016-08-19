package com.blink.blinkiot.Other.UDP;

import com.example.administrator.Interface.HttpInterface;
import com.example.administrator.http_sdk.HTTP;
import com.example.ruan.udp_sdk.UDP;
import com.example.ruan.udp_sdk.UDPListen;
import com.blink.blinkiot.Interface.UDPInterface;

/**
 * Created by Administrator on 2016/7/21.
 */
public class ControlDevice extends UDP implements UDPListen.UDPHandler {

    private String IP = null;
    private int PORT = 0;
    private UDPInterface.HandlerMac handlerMac = null;

    public ControlDevice(String IP, int PORT) {
        super();
        this.IP = IP;
        this.PORT = PORT;
    }

    /**
     * 通过udp控制 设备
     *
     * @param position 标识
     * @param control  传输的数据
     * @param count    发送的次数
     */
    public void UDPControl(int position, String control, UDPInterface.HandlerMac handlerMac, int count) {
        this.uSend(IP, PORT, control.getBytes(), count);
        this.uReviced(position, this);
        this.handlerMac = handlerMac;
    }

    /**
     * 通过云端控制设备
     *
     * @param URL        网址
     * @param dataSource 传输的数据
     */
    public void HTTPControl(String URL, String dataSource, HttpInterface.HttpHandler httpHandler, int position) {
        new HTTP(httpHandler, URL, dataSource, position);
    }

    /**
     * 通过云端控制设备
     *
     * @param URL         网址
     * @param httpHandler 处理接口
     */
    public void HTTPControl(String URL, HttpInterface.HttpHandler httpHandler, int position) {
        new HTTP(httpHandler, URL);
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
