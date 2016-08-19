package com.example.administrator.Thread;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.administrator.HttpCode;
import com.example.administrator.Interface.HttpConnect;
import com.example.administrator.Interface.HttpInterface;
import com.example.administrator.Interface.HttpResult;

/**
 * Created by Administrator on 2016/2/15.
 */
public class HttpRunnable implements Runnable {

    private HttpInterface.HttpConnect httpConnect = null;
    private HttpInterface.HttpHandler httpHandler = null;
    private int position = 0;

    public HttpRunnable(HttpInterface.HttpConnect httpConnect) {
        this.httpConnect = httpConnect;
    }

    @Deprecated
    public HttpRunnable(HttpInterface.HttpConnect httpConnect, HttpInterface.HttpHandler httpHandler) {
        this.httpConnect = httpConnect;
        this.httpHandler = httpHandler;
    }

    @Deprecated
    public HttpRunnable(HttpInterface.HttpConnect httpConnect, HttpInterface.HttpHandler httpHandler, int position) {
        this.httpConnect = httpConnect;
        this.httpHandler = httpHandler;
        this.position = position;
    }

    private HttpConnect.Http CHttp = null;
    private HttpResult.Http RHttp = null;
    private HttpResult.HttpString RHttpString = null;
    private HttpConnect.HttpString CHttpString = null;
    private int code = 0;

    /**
     * 这个是请求网络的重载方法
     *
     * @param CHttp
     * @param RHttp
     * @param code
     */
    public HttpRunnable(HttpConnect.Http CHttp, HttpResult.Http RHttp, int code) {
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
    public HttpRunnable(HttpConnect.HttpString CHttpString, HttpResult.HttpString RHttpString, int code) {
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
            if (httpHandler != null) {
                httpHandler.handler(position, (String) msg.obj);
            }


            if (RHttp != null) {
                if ((HttpCode.TIMEOUT + "").equals(new String((byte[])msg.obj , 0 , ((byte[])msg.obj).length))){
                    RHttp.onError(code, HttpCode.TIMEOUT);
                    return;
                }
                RHttp.onSucceful(code, (byte[]) msg.obj);
            }


            //判断返回结果为字符串的处理结果
            if (RHttpString != null) {
                if ((HttpCode.TIMEOUT + "").equals(msg.obj)) {
                    RHttpString.onError(code, HttpCode.TIMEOUT);
                    return;
                }
                RHttpString.onSucceful(code, (String) msg.obj);
            }
        }
    };
}
