package com.ruan.project.Other.HTTP;

import android.util.Log;

import com.example.administrator.Abstract.HttpRequest;
import com.example.administrator.Abstract.HttpRequester;
import com.example.administrator.http_sdk.HttpConnectSource;
import com.example.administrator.http_sdk.HttpReadSource;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by Administrator on 2016/8/10.
 */
public class HttpWeatherResouce extends HttpRequest {

    private HttpConnectSource httpConnectSource = null;
    private HttpReadSource httpReadSource = null;
    private String Apikey = null;

    public void setApikey(String Apikey) {
        this.Apikey = Apikey;
    }

    /**
     * 这个是GET请求的方法
     *
     * @param uri
     * @return
     */
    @Override
    public String GET(String uri) {
        String result = null;
        try {
            httpConnectSource = new HttpConnectSource(uri);
            // 将默认设置请求方式POST改成GET
            httpConnectSource.setRequestMethod("GET");
            httpConnectSource.setRequestProperty("apikey", Apikey);
            HttpURLConnection connection = httpConnectSource.getHttpURLConnection();

            connection.connect();//发送请求链接

            if (connection.getResponseCode() == 200) {
                httpReadSource = new HttpReadSource(connection);
                return httpReadSource.getResult();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 这个是POST请求的方法
     *
     * @param uri
     * @param data
     * @return
     */
    @Override
    public String POST(String uri, String data) {
        return null;
    }
}