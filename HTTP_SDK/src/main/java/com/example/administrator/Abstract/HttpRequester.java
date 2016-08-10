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
     * @param loadClass
     * @return
     */
    public abstract InputStream GET(String uri, Class loadClass);


    /**
     * 这个是POST请求的方法(支持对象传参)
     *
     * @param uri
     * @param loadClass
     * @return
     */
    public abstract InputStream POST(String uri, Class loadClass);
}
