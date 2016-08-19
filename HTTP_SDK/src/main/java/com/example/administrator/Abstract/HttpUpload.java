package com.example.administrator.Abstract;

import com.example.administrator.HttpContent;
import com.example.administrator.http_sdk.HttpFile;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/15.
 */
public abstract class HttpUpload {


    /**
     * 这个是上传的抽象接口(使用表单上传)
     *
     * @param URL  上传的链接
     * @param list 上传数据的链表
     * @return 1上传成功0上传失败
     */
    public abstract byte[] UploadForm(String URL, ArrayList<HttpContent> list);


    /**
     * 这个是上传的抽象接口
     *
     * @param URL  上传的链接
     * @param list 上传数据的链表
     * @return 1上传成功0上传失败
     */
    public abstract byte[] Upload(String URL, ArrayList<HttpContent> list);
}
