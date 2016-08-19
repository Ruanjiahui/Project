package com.example.administrator.http_sdk;

import android.os.AsyncTask;

import com.example.administrator.Abstract.HttpDown;
import com.example.administrator.Abstract.HttpUpload;
import com.example.administrator.HttpCode;
import com.example.administrator.HttpContent;
import com.example.administrator.Interface.HttpFileConnect;
import com.example.administrator.Interface.HttpFileResult;
import com.example.administrator.Resource.HttpDownResource;
import com.example.administrator.Resource.HttpUploadResource;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/15.
 */
public class HttpFile implements HttpFileConnect {

    private String Method = null;
    private String URL = null;

    private HttpUpload httpUpload = null;
    private HttpDown httpDown = null;
    private ArrayList<HttpContent> list = null;
    private HttpFileResult httpFileResult = null;
    private int FLAG = 0;


    public HttpFile(String Method, String URL, ArrayList<HttpContent> list) {
        this.Method = Method;
        this.URL = URL;
        this.list = list;
        httpDown = new HttpDownResource();
        httpUpload = new HttpUploadResource();
    }

    public HttpFile(HttpFileResult httpFileResult, int FLAG, String Method, String URL, ArrayList<HttpContent> list) {
        this.Method = Method;
        this.URL = URL;
        this.list = list;
        this.httpFileResult = httpFileResult;
        this.FLAG = FLAG;
        httpDown = new HttpDownResource();
        httpUpload = new HttpUploadResource();
    }


    /**
     * 这个发起链接的接口
     */
    @Override
    public byte[] connection() {
        if (HttpCode.DOWN.equals(Method)) {
            if (httpFileResult != null)
                return httpDown.Down(URL, httpFileResult, FLAG);
            return httpDown.Down(URL);
        } else if (HttpCode.UPLOAD.equals(Method)) {
            return httpUpload.Upload(URL, list);
        }
        return null;
    }

}
