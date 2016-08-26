package com.example.ruan.udp_sdk.Thread;

import com.example.ruan.udp_sdk.UDPListen;

/**
 * Created by Administrator on 2016/7/15.
 */
public class UDPSend implements Runnable {
    private UDPListen.UDPSend uSend = null;
    private byte[] buffer = null;
    private int count = 0;

    /**
     * 这个方法是发送UDP调用的方法
     *
     * @param uSend 发送信息的接口
     */
    public UDPSend(UDPListen.UDPSend uSend, byte[] buffer, int count) {
        this.uSend = uSend;
        this.buffer = buffer;
        this.count = count;
    }

    /**
     * Starts executing the active part of the class' code. This method is
     * called when a thread is started that has been created with a class which
     * implements {@code Runnable}.
     */
    @Override
    public void run() {
        while (count > 0) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //每隔0.2发送一个
            uSend.Send(buffer);
            count--;
        }
    }
}
