package com.example.administrator.Thread;

import android.os.Handler;
import android.os.Message;

import com.example.administrator.Interface.TimerHandler;

import java.util.TimerTask;

/**
 * Created by Administrator on 2016/2/29.
 */
public class MyTimerTask extends TimerTask{

    private TimerHandler timerHandler = null;

    public MyTimerTask(TimerHandler timerHandler){
        this.timerHandler = timerHandler;
    }

    public MyTimerTask(){}


    @Override
    public void run() {
        handler.sendMessage(timerHandler.timerRun());
    }

    private Handler handler = new Handler(){

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            timerHandler.timerHandler(msg);
        }
    };
}
