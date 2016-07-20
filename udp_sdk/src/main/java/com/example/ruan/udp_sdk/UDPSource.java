package com.example.ruan.udp_sdk;

import java.net.DatagramSocket;

/**
 * Created by Administrator on 2016/7/16.
 *
 * 这个是UDP传输协议的抽象类
 */
public abstract class UDPSource{

    /**
     * 这个方法是初始化udp链接
     */
    protected abstract void Connect();

    /**
     * 这个方法是发送信息
     * @param IP            IP地址
     * @param PORT          传输端口号
     */
    protected abstract void Send(String IP , int PORT , byte[] buffer);


    /**
     * 这个方法是接收信息
     *
     * @param position  请求的标示
     * @param handler   返回数据进行更新
     */
    protected abstract void Revice(int position , UDPInterface.UDPHandler handler);


    /**
     * 退出链接
     */
    protected abstract void unConnect();
}
