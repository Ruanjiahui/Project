package com.example.administrator.http_sdk;

import com.example.administrator.Abstract.HttpRequest;
import com.example.administrator.HttpCode;
import com.example.administrator.Interface.HttpConnect;
import com.example.administrator.Resource.HttpConnectionString;

/**
 * Created by Administrator on 2016/8/13.
 */
public class HttpString implements HttpConnect.HttpString {

    private String URL = null;
    private String Method = null;
    private Object object = null;
    private HttpRequest httpRequest = null;
    private HttpClassResource httpClass = null;
    private boolean SuperClass = false;

    public HttpString(String method, String URL, Object object, boolean SuperClass) {
        this.Method = method;
        this.object = object;
        this.URL = URL;
        this.SuperClass = SuperClass;
        httpRequest = new HttpConnectionString();
        httpClass = new HttpClassResource();
    }

    /**
     * 连接网络的接口
     *
     * @return
     */
    @Override
    public String connection() {
        if (HttpCode.GET.equals(Method)) {
            return httpRequest.GET(URL);
        } else if (HttpCode.POST.equals(Method)) {
            return httpRequest.POST(URL, httpClass.setObjectToByte(object, SuperClass));
        }
        return null;
    }

    public void disConnection(){
        httpRequest.disConnection();
    }
}
