package com.example.administrator.http_sdk;

import com.example.administrator.Abstract.HttpRequester;
import com.example.administrator.HttpCode;
import com.example.administrator.Interface.HttpConnect;
import com.example.administrator.Resource.HttpConnectionByte;

/**
 * Created by Administrator on 2016/8/13.
 */
public class Httpbyte implements HttpConnect.Http {


    private String URL = null;
    private String Method = null;
    private Object object = null;
    private HttpRequester httpRequester = null;
    private HttpClassResource httpClass = null;
    private boolean SuperClass = false;

    public Httpbyte(String method, String URL, Object object, boolean SuperClass) {
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
        if (HttpCode.GET.equals(Method)) {
            return httpRequester.GET(URL);
        } else if (HttpCode.POST.equals(Method)) {
            return httpRequester.POST(URL, httpClass.setObjectToByte(object, SuperClass));
        }
        return null;
    }

    /**
     * 取消链接
     */
    public void disConnection() {
        httpRequester.disConnection();
    }
}
