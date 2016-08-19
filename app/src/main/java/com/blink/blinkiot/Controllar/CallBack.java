package com.blink.blinkiot.Controllar;

import com.example.administrator.Interface.HttpInterface;
import com.blink.blinkiot.Interface.UDPInterface;
import com.blink.blinkiot.Other.DeviceCode;
import com.blink.blinkiot.Other.HTTP.HttpURL;
import com.blink.blinkiot.Other.UDP.ControlDevice;
import com.blink.blinkiot.Other.UDP.FormatData;

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
     * @param IP   设备IP
     * @param PORT 设备PORT
     * @param data 操作的数据
     * @param FLAG 操作标识
     */
    public void setDeviceControl(String IP, int PORT, String MAC, String data, int FLAG, String OnlineStatus) {
        ControlDevice controlDevice = new ControlDevice(IP, PORT);
        switch (OnlineStatus) {
            case DeviceCode.WIFI:
                //通过udp进行控制
                //防止丢包，所以发送5次数据包
                controlDevice.UDPControl(FLAG, FormatData.getUDP(data), handlerMac, 1);
                break;
            case DeviceCode.CLOUD:
                //通过云端进行控制
                controlDevice.HTTPControl(HttpURL.ControlDevice, FormatData.getPOSTControlDevice(MAC, data), httpHandler, FLAG);
                break;
        }
    }
}
