package com.example.administrator.http_sdk;

import com.example.administrator.Abstract.HttpRequest;
import com.example.administrator.Abstract.HttpRequester;
import com.example.administrator.Interface.Connect;

/**
 * Created by Administrator on 2016/8/13.
 */
public class Httpbyte implements Connect.Http {


    private String URL = null;
    private String Method = null;
    private int FLAG = 0;
    private Object object = null;
    private HttpRequester httpRequester = null;
    private HttpClassResource httpClass = null;
    private boolean SuperClass = false;

    public Httpbyte(String method, String URL, Object object, int FLAG, boolean SuperClass) {
        this.FLAG = FLAG;
        this.Method = method;
        this.object = object;
        this.URL = URL;
        this.SuperClass = SuperClass;
        httpRequester = new HttpConnectionByte();
        httpClass = new HttpClassResource();
    }


    /**
     * 连接网络的接口
     *
     * @return
     */
    @Override
    public byte[] connection() {
        return new byte[0];
    }
}
