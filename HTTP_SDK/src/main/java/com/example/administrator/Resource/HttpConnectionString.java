package com.example.administrator.Resource;

import android.util.Log;

import com.example.administrator.Abstract.HttpRequest;
import com.example.administrator.HttpCode;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/2/15.
 * <p/>
 * 这个一个网络链接的类
 * <p/>
 * 下面是HTTP请求的POST与GET请求的方法
 */
public class HttpConnectionString extends HttpRequest {


    private HttpConnectSource httpConnectSource = null;
    private HttpReadSource httpReadSource = null;
    private HttpURLConnection connection = null;

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
            URL url = new URL(uri);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // 将默认设置请求方式POST改成GET
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(20000);
            httpURLConnection.setReadTimeout(20000);//设置请求时间

//            connection.connect();//发送请求链接
            if (httpURLConnection.getResponseCode() == 200) {
                httpReadSource = new HttpReadSource(httpURLConnection);
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
    public String POST(String uri, byte[] data) {
        String result = null;
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
                return httpReadSource.getResult();
            }
        } catch (java.io.IOException e) {
            return HttpCode.TIMEOUT + "";
        }
        return result;
    }

    /**
     * 取消下载链接
     */
    @Override
    public void disConnection() {
        connection.disconnect();
    }
}
