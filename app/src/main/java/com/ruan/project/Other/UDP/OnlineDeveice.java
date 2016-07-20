package com.ruan.project.Other.UDP;

import com.example.ruan.udp_sdk.UDP;
import com.example.ruan.udp_sdk.UDPInterface;

/**
 * Created by Administrator on 2016/7/19.
 * <p/>
 * 检测设备在不在线
 */
public class OnlineDeveice extends UDP implements UDPInterface.UDPHandler {


    /**
     * 实例化对象
     */
    public OnlineDeveice() {
        super();
    }

    /**
     * 调用该方法可以检测该设备是否在线
     *
     * @param IP   设备的IP
     * @param PORT 设备的端口
     * @param msg  传输的数据包
     */
    public void Online(String IP, int PORT, String msg) {
        uSend(IP, PORT, msg.getBytes());
        uReviced(1, this);
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