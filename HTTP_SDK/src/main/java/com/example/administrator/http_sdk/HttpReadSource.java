package com.example.administrator.http_sdk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by Administrator on 2016/3/14.
 */
public class HttpReadSource {

    private HttpURLConnection httpURLConnection = null;
    private String result = null;
    private InputStream in = null;

    public HttpReadSource(HttpURLConnection httpURLConnection) {
        this.httpURLConnection = httpURLConnection;
    }

    /**
     * 获取网络请求返回来的数据
     * @return
     */
    public String getResult() {
        try {
            in = httpURLConnection.getInputStream();
            //下面对获取到的输入流进行读取
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            result = response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
