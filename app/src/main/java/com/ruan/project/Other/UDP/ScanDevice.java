package com.ruan.project.Other.UDP;


import android.util.Log;

import com.example.administrator.data_sdk.SystemUtil.SystemTool;
import com.example.ruan.udp_sdk.UDP;
import com.ruan.project.Interface.UDPInterface;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/7/18.
 */
public class ScanDevice extends UDP implements com.example.ruan.udp_sdk.UDPInterface.UDPHandler {

    //创建一定的内存有专门存储Mac数值的链表
    private ArrayList<String> mac = null;
    private UDPInterface.HandlerMac handlerMac = null;


    /**
     * 实例化对象
     */
    public ScanDevice() {
        super();
        this.mac = new ArrayList<>();
    }


    public void Scanner(int PORT, String msg, UDPInterface.HandlerMac handlerMac , int count) {
        this.handlerMac = handlerMac;
        uReviced(1, this);
        uSend(UDPConfig.IP, PORT, msg.getBytes() , count);
    }

    /**
     * 判断临时储存的链表里面是否存在同一mac地址
     *
     * @param buffer mac地址的字节数组
     * @param length 字节数组的有效长度
     * @return
     */
    private boolean isEmpty(byte[] buffer, int length) {
        if (mac.size() != 0) {
            for (int i = 0; i < mac.size(); i++) {
                if (new String(buffer, 0, length).equals(mac.get(i))) {
                    return false;
                }
            }
        }
        return true;
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
        byte[] buffer = (byte[]) objects[0];
        objects[1] = 17;
        int lenght = (int)objects[1];
        if (isEmpty(buffer, lenght)) {
            if (SystemTool.isMac(new String(buffer, 0, lenght))) {
                handlerMac.getMac(position, objects);
                mac.add(new String(buffer, 0, lenght));
            }
            return;
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
        handlerMac.Error(position, error);
    }
}