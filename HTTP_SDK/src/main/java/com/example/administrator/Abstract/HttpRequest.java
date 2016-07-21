package com.example.administrator.Abstract;

/**
 * Created by Administrator on 2016/3/14.
 *
 * 这个HTTP 请求的抽象类
 * 里面包含各种的请求方法
 */
public abstract class HttpRequest {

    /**
     * 这个是GET请求的方法
     * @param uri
     * @return
     */
    public abstract String GET(String uri);


    /**
     * 这个是POST请求的方法
     * @param uri
     * @param data
     * @return
     */
    public abstract String POST(String uri , String data);
}
