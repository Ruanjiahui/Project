package com.example.ruan.udp_sdk;



/**
 * Created by Administrator on 2016/7/15.
 */
public class UDP {

    private UDPSource udpBase = null;


    /**
     * 实例化对象
     */
    public UDP() {
        udpBase = new UDPBase();
        //初始化链接
        udpBase.Connect();
    }

    /**
     * 外部调用的函数接口 接收数据
     *
     * @param position      接收数据的标识
     * @param handler
     */
    public void uReviced(int position , UDPListen.UDPHandler handler) {
        udpBase.Revice(position , handler);
    }

    /**
     * 外部调用的函数接口    发送数据
     * @param IP            发送IP
     * @param PORT          发送的远程端口
     * @param buffer
     */
    public void uSend(String IP, int PORT , byte[] buffer , int count) {
        udpBase.Send(IP, PORT, buffer , count);
    }

    /**
     * 关闭链接
     */
    public void unClose(){
        udpBase.unConnect();
    }

}
