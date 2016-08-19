package com.ruan.project.Other.HTTP;

import com.example.administrator.Interface.HttpConnect;
import com.example.administrator.Interface.HttpResult;
import com.example.administrator.Thread.HttpRunnable;

/**
 * Created by Administrator on 2016/8/10.
 */
public class HttpWeather implements HttpConnect.HttpString {

    //创建请求方法的对象
    private HttpWeatherResouce httpRequest = null;
    private String URL = null;

    public HttpWeather(String URL, String apikey, HttpResult.HttpString RHttpString, int position) {
        //实例化对象
        httpRequest = new HttpWeatherResouce();
        httpRequest.setApikey(apikey);
        this.URL = URL;
        new Thread(new HttpRunnable(this, RHttpString, position)).start();
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
