package com.example.administrator.http_sdk;

import android.graphics.Bitmap;

import com.example.administrator.Abstract.HttpRequest;
import com.example.administrator.Abstract.HttpUploadDown;
import com.example.administrator.Interface.HttpInterface;
import com.example.administrator.Thread.MyAsyncTask;
import com.example.administrator.Thread.MyAsyncTaskDown;
import com.example.administrator.Thread.MyRunnable;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/19.
 */
public class HTTP implements HttpInterface.HttpConnect {

    private String url = null;
    private String data = null;
    private ArrayList<File> file = null;
    private String key = null;

    private InputStream inputStream = null;

    //创建请求方法的对象
    private HttpRequest httpRequest = null;
    //创建下载上传的对象
    private HttpUploadDown httpUploadDown = null;

    /**
     * POST请求的方法
     *
     * @param httpHandler
     * @param url
     * @param data
     */
    public HTTP(HttpInterface.HttpHandler httpHandler, String url, String data) {
        this.url = url;
        this.data = data;
        //实例化对象
        httpRequest = new HttpConnection();
        new Thread(new MyRunnable(this, httpHandler)).start();
    }

    /**
     * GET请求的方法
     *
     * @param httpHandler
     * @param url
     */
    public HTTP(HttpInterface.HttpHandler httpHandler, String url) {
        this.url = url;
        //实例化对象
        httpRequest = new HttpConnection();
        new Thread(new MyRunnable(this, httpHandler)).start();
    }

    /**
     * 上传图片的方法
     *
     * @param httpHandler
     * @param file
     * @param url
     * @param data
     */
    public HTTP(HttpInterface.DownUploadHandler.Upload httpHandler, ArrayList<File> file, String url, String data, String key) {
        this.file = file;
        this.url = url;
        this.data = data;
        this.key = key;
        httpUploadDown = new UploadDown();
        new MyAsyncTask(this, httpHandler , "File").execute();
    }

    /**
     * 上传图片的方法
     *
     * @param httpHandler
     * @param inputStream
     * @param url
     * @param data
     */
    public HTTP(HttpInterface.DownUploadHandler.Upload httpHandler, InputStream inputStream, String url, String data, String key) {
        this.inputStream = inputStream;
        this.url = url;
        this.data = data;
        this.key = key;
        httpUploadDown = new UploadDown();
        new MyAsyncTask(this, httpHandler , "").execute();
    }

    /**
     * 下载图片
     *
     * @param httpHandler
     * @param url
     * @param data
     * @param flag
     */
    public HTTP(HttpInterface.DownUploadHandler.Download httpHandler, String url, String data, String flag) {
        this.url = url;
        this.data = data;
        httpUploadDown = new UploadDown();
        if ("Bitmap".equals(flag))
            new MyAsyncTaskDown(this, httpHandler , flag).execute();
        else
            new MyAsyncTaskDown(this, httpHandler , flag).execute();
    }

    @Override
    public String connect() {
        //如果数据为空则是GET请求
        if (data == null) {
            return httpRequest.GET(url);
        } else {
            //如果数据不为空则分两种清空
            //第一种判断Array<File>是不是为空，空则是POST请求
            return httpRequest.POST(url, data);
        }
    }


    //实现上传文件的方法
    @Override
    public String UploadFile() {
        return httpUploadDown.Upload(file, url, data, key);
    }

    //实现上传文件的字节流
    @Override
    public String UploadInputStream() {
        return httpUploadDown.UploadInputStream(inputStream, url, data, key);
    }

    //实现下载图片的方法
    @Override
    public Bitmap downBitmap() {
        return httpUploadDown.DownBitmap(url);
    }

    //实现下载字节流的方法
    @Override
    public InputStream downFile() {
        return httpUploadDown.DownFile(url);
    }
}
