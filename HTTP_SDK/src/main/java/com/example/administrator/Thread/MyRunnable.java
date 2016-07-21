package com.example.administrator.Thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.administrator.Interface.HttpInterface;

/**
 * Created by Administrator on 2016/2/15.
 */
public class MyRunnable implements Runnable {

    private HttpInterface.HttpConnect httpConnect = null;
    private HttpInterface.HttpHandler httpHandler = null;

    public MyRunnable(HttpInterface.HttpConnect httpConnect) {
        this.httpConnect = httpConnect;
    }

    public MyRunnable(HttpInterface.HttpConnect httpConnect, HttpInterface.HttpHandler httpHandler) {
        this.httpConnect = httpConnect;
        this.httpHandler = httpHandler;
    }

    @Override
    public void run() {
        if (httpConnect != null) {
            Looper.prepare();
            Message msg = new Message();
            msg.obj = httpConnect.connect();

            if (httpHandler != null) {
                handler.sendMessage(msg);
            }
            Looper.loop();
        }
    }

    private Handler handler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);

            httpHandler.handler((String) msg.obj);
        }
    };
}
