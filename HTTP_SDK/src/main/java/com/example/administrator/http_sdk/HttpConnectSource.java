package com.example.administrator.http_sdk;

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Administrator on 2016/3/14.
 */
public class HttpConnectSource {

    private HttpURLConnection httpURLConnection = null;
    private URL url = null;
    private String uri = null;

    public HttpConnectSource(String uri) {
        this.uri = uri;
        setHttpURLConnection();
    }

    public HttpURLConnection getHttpURLConnection() {
        return httpURLConnection;
    }

    /**
     * 默认设置以下方式
     * 也可以进行修改
     */
    private void setHttpURLConnection() {
        try {
            url = new URL(uri);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);//设置输入采用字节流方式
            httpURLConnection.setDoOutput(true);//设置输出采用字节流的方式
            httpURLConnection.setRequestMethod("POST");//设置GET请求
            httpURLConnection.setConnectTimeout(20000);
            httpURLConnection.setReadTimeout(20000);//设置请求时间
            httpURLConnection.setRequestProperty("Charset", "utf-8");//设置字符编码
        } catch (MalformedURLException e) {
            Log.e("Ruan", "访问地址错误, 请检查格式");
        } catch (IOException e) {
            Log.e("Ruan", "访问网址打开失败");
        }
    }

    /**
     * 设置输入采用字节流方式
     *
     * @param doInput
     */
    public void setDoInput(boolean doInput) {
        httpURLConnection.setDoInput(doInput);
    }

    /**
     * 设置输出采用字节流的方式
     *
     * @param doOutput
     */
    public void setDoOutput(boolean doOutput) {
        httpURLConnection.setDoOutput(doOutput);
    }

    /**
     * 设置请求方式
     *
     * @param requestMethod
     */
    public void setRequestMethod(String requestMethod) {
        try {
            httpURLConnection.setRequestMethod(requestMethod);
        } catch (ProtocolException e) {
            Log.e("Ruan", "请求方式错误");
        }
    }

    /**
     * 设置连接时间
     *
     * @param connectTimeout
     */
    public void setConnectTimeout(int connectTimeout) {
        httpURLConnection.setConnectTimeout(connectTimeout);
    }

    /**
     * 设置读取时间
     *
     * @param readTimeout
     */
    public void setReadTimeout(int readTimeout) {
        httpURLConnection.setReadTimeout(readTimeout);
    }

    /**
     * 设置字符编码
     *
     * @param requestProperty
     * @param code
     */
    public void setRequestProperty(String requestProperty, String code) {
        httpURLConnection.setRequestProperty(requestProperty, code);
    }

    /**
     * 不使用缓存
     *
     * @param useCaches
     */
    public void setUseCaches(boolean useCaches) {
        httpURLConnection.setUseCaches(useCaches);
    }
}
