package com.blink.blinkiot.Other.System;

import android.os.Handler;
import android.os.Message;

import com.example.administrator.data_sdk.SystemUtil.TimeTool;

/**
 * Created by Administrator on 2016/8/13.
 */
public class TimeRunnable implements Runnable {

    private ReceiverHandler receiverHandler = null;
    private ReceiverHandler.ReceiverTime receiverTime = null;
    private boolean stop = false;
    private String[] number = null;
    private String ACTION = null;
    private int FLAG = 0;

    public TimeRunnable(ReceiverHandler.ReceiverTime receiverTime, ReceiverHandler receiverHandler, String[] number
            , String ACTION, int FLAG) {
        this.number = number;
        this.receiverHandler = receiverHandler;
        this.receiverTime = receiverTime;
        this.ACTION = ACTION;
        this.FLAG = FLAG;
    }


    /**
     * Starts executing the active part of the class' code. This method is
     * called when a thread is started that has been created with a class which
     * implements {@code Runnable}.
     */
    @Override
    public void run() {
        while (!stop) {
            //实时监听时间是否到了
            //判断是否到了监听时间
            if (TimeTool.getTime24Hour().equals(number[0]) && TimeTool.getTimeMinuts().equals(number[1])) {
                stop = true;
                Message message = new Message();
                handler.sendMessage(message);
                return;
            }
            stop = receiverTime.Stop();
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            receiverHandler.Result(ACTION, FLAG);
            return false;
        }
    });

}
