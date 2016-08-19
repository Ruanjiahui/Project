package com.example.administrator.Resource;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.administrator.Abstract.HttpDown;
import com.example.administrator.HttpCode;
import com.example.administrator.Interface.HttpFileResult;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/8/15.
 */
public class HttpDownResource extends HttpDown {

    private HttpConnectSource httpConnectSource = null;
    private HttpReadSource httpReadSource = null;


    /**
     * 这个下载文件的抽象方法
     *
     * @param url 下载的链接
     */
    @Override
    public byte[] Down(String url) {
        try {
            URL uri = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();
            urlConnection.setConnectTimeout(20000);
            urlConnection.setReadTimeout(20000);
            urlConnection.setUseCaches(false); //不使用缓存
            urlConnection.setRequestMethod("GET");

            urlConnection.connect();
            /**
             * 获取响应码 200=成功
             * 当响应成功，获取响应的流
             */
            int res = urlConnection.getResponseCode();
            if (res == 200) {
                httpReadSource = new HttpReadSource(urlConnection);
                return httpReadSource.getResultByte();
            }
        } catch (IOException e) {
            return (HttpCode.TIMEOUT + "").getBytes();
        }
        return null;
    }

    /**
     * 这个下载文件的抽象方法
     *
     * @param url            下载的链接
     * @param httpFileResult
     * @param FLAG
     */
    @Override
    public byte[] Down(String url, HttpFileResult httpFileResult, int FLAG) {
        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();
            urlConnection.setConnectTimeout(20000);
            urlConnection.setReadTimeout(20000);
            urlConnection.setUseCaches(false); //不使用缓存
            urlConnection.setRequestMethod("GET");

            urlConnection.connect();
            /**
             * 获取响应码 200=成功
             * 当响应成功，获取响应的流
             */
            int res = urlConnection.getResponseCode();
            if (res == 200) {
                httpReadSource = new HttpReadSource(urlConnection, httpFileResult, FLAG);
                return httpReadSource.getResultByte();
            }
        } catch (IOException e) {
            urlConnection.disconnect();
            return (HttpCode.TIMEOUT + "").getBytes();
        }
        return null;
    }
}
