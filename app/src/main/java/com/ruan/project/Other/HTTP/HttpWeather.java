package com.ruan.project.Other.HTTP;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.administrator.Abstract.HttpRequest;
import com.example.administrator.Interface.Connect;
import com.example.administrator.Interface.HttpInterface;
import com.example.administrator.Interface.Result;
import com.example.administrator.Thread.MyRunnable;
import com.example.administrator.http_sdk.HttpConnection;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/8/10.
 */
public class HttpWeather implements Connect.HttpString {

    //创建请求方法的对象
    private HttpWeatherResouce httpRequest = null;
    private String URL = null;

    public HttpWeather(String URL, String apikey, Result.HttpString RHttpString, int position) {
        //实例化对象
        httpRequest = new HttpWeatherResouce();
        httpRequest.setApikey(apikey);
        this.URL = URL;
        new Thread(new MyRunnable(this, RHttpString, position)).start();
    }

    /**
     * 连接网络的接口
     *
     * @return
     */
    @Override
    public String connection() {
        return httpRequest.GET(URL);
    }
}