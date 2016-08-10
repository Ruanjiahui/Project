package com.example.administrator.Abstract;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/3/14.
 * <p/>
 * 这个HTTP 请求的抽象类
 * 里面包含各种的请求方法
 */
@Deprecated
public abstract class HttpRequest {

    /**
     * 这个是GET请求的方法
     *
     * @param uri
     * @return
     */
    @Deprecated
    public abstract String GET(String uri);


    /**
     * 这个是POST请求的方法
     *
     * @param uri
     * @param data
     * @return
     */
    @Deprecated
    public abstract String POST(String uri, String data);


}
