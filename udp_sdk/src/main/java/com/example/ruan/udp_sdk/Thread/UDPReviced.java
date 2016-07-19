package com.example.ruan.udp_sdk.Thread;

import android.os.Handler;
import android.os.Message;

import com.example.ruan.udp_sdk.UDPInterface;


/**
 * Created by Administrator on 2016/7/15.
 */
public class UDPReviced implements Runnable {
    private UDPInterface.UDPReviced uReviced = null;
    private UDPInterface.UDPHandler uHandler = null;

    /**
     * 这个方法是接收信息调用的方法
     *
     * @param uReviced 接收信息的接口
     * @param uHandler 处理接收信息的接口
     */
    public UDPReviced(UDPInterface.UDPReviced uReviced, UDPInterface.UDPHandler uHandler) {
        this.uReviced = uReviced;
        this.uHandler = uHandler;
    }

    /**
     * Starts executing the active part of the class' code. This method is
     * called when a thread is started that has been created with a class which
     * implements {@code Runnable}.
     */
    @Override
    public void run() {
        while (true) {
            Message message = new Message();
            Object[] data = uReviced.Reviced();
            if (data != null) {
                message.obj = data;
                handler.sendMessage(message);
            }
        }
    }

    private Handler handler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            uHandler.Handler((Object[]) msg.obj);
        }
    };
}

