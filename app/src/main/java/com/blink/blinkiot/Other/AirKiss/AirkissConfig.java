package com.blink.blinkiot.Other.AirKiss;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tencent.mm.plugin.exdevice.jni.C2JavaExDevice;
import com.tencent.mm.plugin.exdevice.jni.Java2CExDevice;

/**
 * Created by Administrator on 2016/7/28.
 */
public class AirkissConfig {

    private String wifiSSID = null;
    private String wifiPassword = null;
    private AirKissCallBack airKissCallBack = null;

    public AirkissConfig() {

    }

    /**
     * 开始启动链接Airkiss
     *
     * @param wifiSSID     wifi的SSID
     * @param wifiPassword wifi的密码
     */
    public void StartAirKiss(String wifiSSID, String wifiPassword, AirKissCallBack airKissCallBack) {
        this.wifiSSID = wifiSSID;
        this.wifiPassword = wifiPassword;
        this.airKissCallBack = airKissCallBack;

        try {
            Java2CExDevice.startAirKissWithInter(wifiPassword, wifiSSID, new byte[]{}, 60000L, 0, 5);
            C2JavaExDevice.getInstance().setHandler(this.handler);
        }catch (Exception e){
            airKissCallBack.Error(-1);
        }

    }

    /**
     * 停止AirKiss
     */
    public void StopAirKiss() {
        Java2CExDevice.stopAirKiss();
    }


    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                default:
                    airKissCallBack.Error(msg.what);
                    return false;
                case 1:
            }
            StopAirKiss();
            if (msg.arg1 == 0)
                airKissCallBack.Result(msg.what);
            airKissCallBack.Error(msg.what);
            return true;
        }
    });

}
