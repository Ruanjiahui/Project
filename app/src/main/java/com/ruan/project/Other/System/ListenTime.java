package com.ruan.project.Other.System;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.administrator.data_sdk.SystemUtil.TimeTool;

/**
 * Created by Administrator on 2016/8/4.
 */
public class ListenTime {


    private Context context = null;
    private String[] time = null;
    private boolean stop = false;
    private ReceiverHandler receiverHandler = null;
    private String ACTION = "";
    private int position = 0;

    public ListenTime(Context context, ReceiverHandler receiverHandler, String ACTION, int position) {
        this.context = context;
        this.receiverHandler = receiverHandler;
        this.ACTION = ACTION;
        this.position = position;
    }

    /**
     * 设置监听时间的接口
     *
     * @param time
     */
    public void setListtenTime(String[] time) {
        this.time = time;
    }


    /**
     * 获取该广播是否执行
     *
     * @param ACTION
     * @param position
     * @return
     */
    public boolean getListenTime(String ACTION, int position) {
        if (ACTION.equals(this.ACTION) && position == this.position)
            return true;
        return false;
    }

    /**
     * 开始监听
     */
    public void StartListen() {
        stop = false;
        if (time != null && time.length != 0)
            new Thread(new TimeRunnable()).start();
        else
            receiverHandler.Error(ACTION, position);
    }


    /**
     * 停止监听
     */
    public void StopListen() {
        stop = true;
    }

    private class TimeRunnable implements Runnable {

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
                if (TimeTool.getTime24Hour().equals(time[0]) && TimeTool.getTimeMinuts().equals(time[1])) {
                    Message message = new Message();
                    handler.sendMessage(message);
                    //停止线程
                    StopListen();
                }
            }
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            receiverHandler.Result(ACTION, position);
            return false;
        }
    });
}
