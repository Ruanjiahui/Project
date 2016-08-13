package com.example.administrator.http_sdk;

import android.graphics.Bitmap;

import com.example.administrator.Abstract.HttpRequest;
import com.example.administrator.Abstract.HttpUploadDown;
import com.example.administrator.Interface.Connect;
import com.example.administrator.Interface.HttpInterface;
import com.example.administrator.Interface.Result;
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
    //请求的标示
    private int position = 0;


    /**
     * 这个是访问网络的HTTP请求返回来的是字节数组
     *
     * @param RHttp      处理方法的接口
     * @param method     请求的方法
     * @param URL        请求连接
     * @param object     请求的数据对象
     * @param FLAG       请求的标识
     * @param SuperClass 数据对象是否是继承对象
     */
    public HTTP(Result.Http RHttp, String method, String URL, Object object, int FLAG, boolean SuperClass) {
        new Thread(new MyRunnable(new Httpbyte(method, URL, object, FLAG, SuperClass), RHttp, FLAG)).start();
    }

    /**
     * 这个是访问网络的HTTP请求返回来的是字符串
     *
     * @param RHttpString 处理方法的接口
     * @param method      请求的方法
     * @param URL         请求连接
     * @param object      请求的数据对象
     * @param FLAG        请求的标识
     * @param SuperClass  数据对象是否是继承对象
     */
    public HTTP(Result.HttpString RHttpString, String method, String URL, Object object, int FLAG, boolean SuperClass) {
        new Thread(new MyRunnable(new HttpString(method, URL, object, FLAG, SuperClass), RHttpString, FLAG)).start();
    }

    /**
     * POST请求的方法
     *
     * @param httpHandler
     * @param url
     * @param data
     */
    public HTTP(HttpInterface.HttpHandler httpHandler, String url, String data, int position) {
        this.url = url;
        this.data = data;
        this.position = position;
        //实例化对象
        httpRequest = new HttpConnectionString();
        new Thread(new MyRunnable(this, httpHandler, position)).start();
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
        httpRequest = new HttpConnectionString();
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
        new MyAsyncTask(this, httpHandler, "File").execute();
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
        new MyAsyncTask(this, httpHandler, "").execute();
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
            new MyAsyncTaskDown(this, httpHandler, flag).execute();
        else
            new MyAsyncTaskDown(this, httpHandler, flag).execute();
    }

    @Override
    public String connect() {
        //如果数据为空则是GET请求
        if (data == null) {
            return httpRequest.GET(url);
        } else {
            //如果数据不为空则分两种清空
            //第一种判断Array<File>是不是为空，空则是POST请求
            return httpRequest.POST(url, data.getBytes());
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
