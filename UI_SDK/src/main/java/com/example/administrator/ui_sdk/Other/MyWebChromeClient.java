package com.example.administrator.ui_sdk.Other;

import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.administrator.ui_sdk.MyWebClient;

/**
 * Created by Administrator on 2016/1/6.
 */
public class MyWebChromeClient extends WebChromeClient {

    private MyWebClient myWebClient = null;

    public MyWebChromeClient(MyWebClient myWebClient) {
        this.myWebClient = myWebClient;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (myWebClient != null) {
            //加载完成
            if (newProgress == 100) {
                myWebClient.Ending();
            } else {
                //加载中处理的事件
                myWebClient.Loading();
            }
        }
    }
}
