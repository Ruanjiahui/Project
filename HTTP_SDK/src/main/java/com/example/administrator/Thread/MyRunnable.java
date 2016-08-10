package com.example.administrator.Thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.administrator.Interface.Connect;
import com.example.administrator.Interface.HttpInterface;
import com.example.administrator.Interface.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2016/2/15.
 */
public class MyRunnable implements Runnable {

    private HttpInterface.HttpConnect httpConnect = null;
    private HttpInterface.HttpHandler httpHandler = null;
    private int position = 0;

    public MyRunnable(HttpInterface.HttpConnect httpConnect) {
        this.httpConnect = httpConnect;
    }

    @Deprecated
    public MyRunnable(HttpInterface.HttpConnect httpConnect, HttpInterface.HttpHandler httpHandler) {
        this.httpConnect = httpConnect;
        this.httpHandler = httpHandler;
    }

    @Deprecated
    public MyRunnable(HttpInterface.HttpConnect httpConnect, HttpInterface.HttpHandler httpHandler, int position) {
        this.httpConnect = httpConnect;
        this.httpHandler = httpHandler;
        this.position = position;
    }

    private Connect.Http CHttp = null;
    private Result.Http RHttp = null;
    private Result.HttpString RHttpString = null;
    private Connect.HttpString CHttpString = null;
    private int code = 0;

    /**
     * 这个是请求网络的重载方法
     *
     * @param CHttp
     * @param RHttp
     * @param code
     */
    public MyRunnable(Connect.Http CHttp, Result.Http RHttp, int code) {
        this.CHttp = CHttp;
        this.RHttp = RHttp;
        this.code = code;
    }

    /**
     * 这个是请求网络的重载方法
     *
     * @param CHttpString
     * @param RHttpString
     * @param code
     */
    public MyRunnable(Connect.HttpString CHttpString, Result.HttpString RHttpString, int code) {
        this.CHttpString = CHttpString;
        this.RHttpString = RHttpString;
        this.code = code;
    }


    @Override
    public void run() {
        if (httpConnect != null) {
            Message msg = new Message();
            msg.obj = httpConnect.connect();
            handler.sendMessage(msg);
        }
        if (CHttp != null) {
            Message msg = new Message();
            msg.obj = CHttp.connection();
            handler.sendMessage(msg);
        }
        if (CHttpString != null) {
            Message msg = new Message();
            msg.obj = CHttpString.connection();
            handler.sendMessage(msg);
        }
    }

    private Handler handler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            if (httpHandler != null)
                httpHandler.handler(position, (String) msg.obj);
            if (RHttp != null) {
                RHttp.onSucceful(code, (InputStream) msg.obj);
            }
            if (RHttpString != null) {
                RHttpString.onSucceful(code, (String) msg.obj);
            }
        }
    };
}
