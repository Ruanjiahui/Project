package com.ruan.project.Controllar;

import android.util.Log;

import com.example.administrator.Interface.HttpInterface;
import com.ruan.project.Interface.UDPInterface;
import com.ruan.project.Other.HTTP.HttpURL;
import com.ruan.project.Other.System.NetWork;
import com.ruan.project.Other.UDP.ControlDevice;
import com.ruan.project.Other.UDP.FormatData;

/**
 * Created by Administrator on 2016/7/29.
 */
public class CallBack {

    private UDPInterface.HandlerMac handlerMac = null;
    private HttpInterface.HttpHandler httpHandler = null;

    public CallBack(UDPInterface.HandlerMac handlerMac, HttpInterface.HttpHandler httpHandler) {
        this.handlerMac = handlerMac;
        this.httpHandler = httpHandler;
    }

    /**
     * 对设备进行操作的方法
     *
     * @param IP       设备IP
     * @param PORT     设备PORT
     * @param data     操作的数据
     * @param position 操作标识
     */
    public void setDeviceControl(String IP, int PORT, String MAC , String data, int position) {
        ControlDevice controlDevice = new ControlDevice(IP, PORT);
        switch (HttpURL.STATE) {
            case NetWork.WIFI:
                //通过udp进行控制
                //防止丢包，所以发送5次数据包
                controlDevice.UDPControl(position, FormatData.getUDP(data), handlerMac, 1);
                break;
            case NetWork.INTNET:
                //通过云端进行控制
                controlDevice.HTTPControl(HttpURL.ControlDevice, FormatData.getPOSTControlDevice(MAC, data) , httpHandler, position);
                break;
        }
    }
}
