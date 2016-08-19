package com.example.administrator.Resource;

import android.util.Log;

import com.example.administrator.Abstract.HttpRequester;
import com.example.administrator.HttpCode;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Created by Administrator on 2016/8/13.
 */
public class HttpConnectionByte extends HttpRequester {

    private HttpConnectSource httpConnectSource = null;
    private HttpReadSource httpReadSource = null;
    private HttpURLConnection connection = null;

    /**
     * 这个是GET请求的方法(支持对象传参)
     *
     * @param uri
     * @return
     */
    @Override
    public byte[] GET(String uri) {
        byte[] result = null;
        try {
            httpConnectSource = new HttpConnectSource(uri);
            // 将默认设置请求方式POST改成GET
            httpConnectSource.setRequestMethod("GET");
            connection = httpConnectSource.getHttpURLConnection();

            connection.connect();//发送请求链接
            if (connection.getResponseCode() == 200) {
                httpReadSource = new HttpReadSource(connection);
                return httpReadSource.getResultByte();
            }
        } catch (IOException e) {
            return (HttpCode.TIMEOUT + "").getBytes();
        }
        return result;
    }

    /**
     * 这个是POST请求的方法(支持对象传参)
     *
     * @param uri
     * @param data
     * @return
     */
    @Override
    public byte[] POST(String uri, byte[] data) {
        byte[] result = null;
        try {
            httpConnectSource = new HttpConnectSource(uri);
            httpConnectSource.setUseCaches(false); //不使用缓存
            connection = httpConnectSource.getHttpURLConnection();

            //创建输出字节对象z
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();

            connection.connect();//发送请求链接

            //判断如果请求码 等于200 则说明请求成功在下面获取返回来的数据
            //如果请求码 不等于200 则说明请求不成功
            if (connection.getResponseCode() == 200) {
                httpReadSource = new HttpReadSource(connection);
                return httpReadSource.getResultByte();
            }
        } catch (java.io.IOException e) {
            return (HttpCode.TIMEOUT + "").getBytes();
        }
        return result;
    }

    /**
     * 取消链接的方法
     */
    @Override
    public void disConnection() {
        connection.disconnect();
    }

}
