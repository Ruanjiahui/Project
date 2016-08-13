package com.ruan.project.Other.System;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.administrator.data_sdk.SystemUtil.TimeTool;
import com.example.ruan.udp_sdk.TimerHandler;

/**
 * Created by Administrator on 2016/8/4.
 */
public class ListenTime implements ReceiverHandler.ReceiverTime {


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
            new Thread(new TimeRunnable(this , receiverHandler ,  time , ACTION , position)).start();
        else
            receiverHandler.Error(ACTION, position);
    }


    /**
     * 停止监听
     */

    public void StopListen() {
        stop = true;
        Stop();
    }

    @Override
    public boolean Stop() {
        return stop;
    }
}
