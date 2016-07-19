package com.ruan.project.Other.UDP;

import com.example.ruan.udp_sdk.UDP;
import com.example.ruan.udp_sdk.UDPInterface;
import com.ruan.project.Interface.Other;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/18.
 */
public class ScanDevice extends UDP implements UDPInterface.UDPHandler {

    private static String ScanIP = "255.255.255.255";
    //创建一定的内存有专门存储Mac数值的链表
    private ArrayList<String> mac = null;
    private Other.HandlerMac handlerMac = null;



    /**
     * 实例化对象
     */
    public ScanDevice(ArrayList<String> mac) {
        super();
        this.mac = mac;
    }


    public void Scanner(int PORT, String msg, Other.HandlerMac handlerMac) {
        this.handlerMac = handlerMac;
        uSend(ScanIP, PORT, msg.getBytes());
        uReviced(this);
    }

    @Override
    public void Handler(Object[] objects) {
        //判断链表里面是不是存在相同的mac地址，不存在则添加，存在则不操作
        byte[] buffer = (byte[]) objects[0];
        int lenght = (int) objects[1];
        if (isEmpty(buffer, lenght)) {
            handlerMac.getMac(objects);
            mac.add(new String(buffer, 0, lenght));
            return;
        }
    }


    /**
     * 判断临时储存的链表里面是否存在同一mac地址
     *
     * @param buffer mac地址的字节数组
     * @param length 字节数组的有效长度
     * @return
     */
    private boolean isEmpty(byte[] buffer, int length) {
        for (int i = 0; i < mac.size(); i++) {
            if (new String(buffer, 0, length).equals(mac.get(i))) {
                return false;
            }
        }
        return true;
    }
}