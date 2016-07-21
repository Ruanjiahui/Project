package com.example.administrator.http_sdk;

import com.example.administrator.Abstract.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Created by Administrator on 2016/2/15.
 * <p/>
 * 这个一个网络链接的类
 * <p/>
 * 下面是HTTP请求的POST与GET请求的方法
 */
public class HttpConnection extends HttpRequest {


    private HttpConnectSource httpConnectSource = null;
    private HttpReadSource httpReadSource = null;

    /**
     * 下面是 HTTP  GET请求的方法
     *
     * @param uri
     * @return
     */
    @Override
    public String GET(String uri) {
        String result = null;
        try {
            httpConnectSource = new HttpConnectSource(uri);
            // 将默认设置请求方式POST改成GET
            httpConnectSource.setRequestMethod("GET");
            HttpURLConnection connection = httpConnectSource.getHttpURLConnection();

            connection.connect();//发送请求链接

            if (connection.getResponseCode() == 200) {
                httpReadSource = new HttpReadSource(connection);
                return httpReadSource.getResult();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 这个是HTTP  POST请求的方法
     *
     * @param uri
     * @param data
     * @return
     */
    @Override
    public String POST(String uri, String data) {
        String result = null;
        try {
            httpConnectSource = new HttpConnectSource(uri);
            httpConnectSource.setUseCaches(false); //不使用缓存
            HttpURLConnection connection = httpConnectSource.getHttpURLConnection();

            //创建输出字节对象z
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();

            connection.connect();//发送请求链接

            //判断如果请求码 等于200 则说明请求成功在下面获取返回来的数据
            //如果请求码 不等于200 则说明请求不成功
            if (connection.getResponseCode() == 200) {
                httpReadSource = new HttpReadSource(connection);
                return httpReadSource.getResult();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
