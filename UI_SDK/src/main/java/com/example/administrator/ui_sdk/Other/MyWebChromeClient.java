package com.example.administrator.ui_sdk.Other;

import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by Administrator on 2016/1/6.
 */
public class MyWebChromeClient extends WebChromeClient {


    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);

        //加载完成
        if (newProgress == 100) {
            Log.e("hello" , "加载完成");
        } else {
        //加载中处理的事件
            Log.e("hello" , "加载中...");
        }
    }
}
