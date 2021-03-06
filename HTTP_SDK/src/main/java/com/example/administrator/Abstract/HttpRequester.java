package com.example.administrator.Abstract;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/8/10.
 */
public abstract class HttpRequester {

    /**
     * 这个是GET请求的方法(支持对象传参)
     *
     * @param uri
     * @return
     */
    public abstract byte[] GET(String uri);


    /**
     * 这个是POST请求的方法(支持对象传参)
     *
     * @param uri
     * @param data
     * @return
     */
    public abstract byte[] POST(String uri, byte[] data);


    /**
     * 取消链接的方法
     */
    public abstract void disConnection();

}
