package com.blink.blinkiot.Other;

import android.os.Handler;
import android.os.Message;


import com.blink.blinkiot.Start.TimerHandler;

import java.util.TimerTask;

/**
 * Created by Administrator on 2016/2/29.
 */
public class MyTimerTask extends TimerTask {

    private TimerHandler timerHandler = null;
    private int position = 0;

    public MyTimerTask(TimerHandler timerHandler , int position) {
        this.timerHandler = timerHandler;
        this.position = position;
    }

    public MyTimerTask() {
    }


    @Override
    public void run() {
//        Looper.prepare();
        if (timerHandler.timerRun(position) != null)
            handler.sendMessage(timerHandler.timerRun(position));
//        Looper.loop();
    }

    private Handler handler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            timerHandler.timerHandler(msg);
        }
    };
}
