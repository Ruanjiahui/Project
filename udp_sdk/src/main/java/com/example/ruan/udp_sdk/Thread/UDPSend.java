package com.example.ruan.udp_sdk.Thread;

import com.example.ruan.udp_sdk.UDPInterface;

/**
 * Created by Administrator on 2016/7/15.
 */
public class UDPSend implements Runnable {
    private UDPInterface.UDPSend uSend = null;
    private byte[] buffer = null;

    /**
     * 这个方法是发送UDP调用的方法
     *
     * @param uSend 发送信息的接口
     */
    public UDPSend(UDPInterface.UDPSend uSend , byte[] buffer) {
        this.uSend = uSend;
        this.buffer = buffer;
    }

    /**
     * Starts executing the active part of the class' code. This method is
     * called when a thread is started that has been created with a class which
     * implements {@code Runnable}.
     */
    @Override
    public void run() {
        uSend.Send(buffer);
    }
}
