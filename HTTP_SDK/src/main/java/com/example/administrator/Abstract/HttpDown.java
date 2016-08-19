package com.example.administrator.Abstract;

import android.os.AsyncTask;

import com.example.administrator.Interface.HttpFileResult;

/**
 * Created by Administrator on 2016/8/15.
 */
public abstract class HttpDown {

    /**
     * 这个下载文件的抽象方法
     *
     * @param URL 下载的链接
     */
    public abstract byte[] Down(String URL);



    /**
     * 这个下载文件的抽象方法
     *
     * @param URL 下载的链接
     */
    public abstract byte[] Down(String URL , HttpFileResult httpFileResult , int FLAG);


}
