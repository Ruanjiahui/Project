package com.example.administrator.Abstract;

import android.graphics.Bitmap;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/14.
 * <p/>
 * 这是一个上传和下载的抽象类
 */
public abstract class HttpUploadDown {

    /**
     * 这是一个上传的抽象方法
     *
     * @param file
     * @param uri
     * @param data
     * @param key
     * @return
     */
    public abstract String Upload(ArrayList<File> file, String uri, String data, String key);

    /**
     * 这个是上传字节流的抽象方法
     * @param inputStream
     * @param uri
     * @param data
     * @param key
     * @return
     */
    public abstract String UploadInputStream(InputStream inputStream , String uri , String data , String key);

    /**
     * 这个是一个下载图片的抽象方法
     *
     * @param uri
     * @return
     */
    public abstract Bitmap DownBitmap(String uri);

    /**
     * 这个是一个下载文件的抽象方法
     *
     * @param uri
     * @return
     */
    public abstract InputStream DownFile(String uri);
}
