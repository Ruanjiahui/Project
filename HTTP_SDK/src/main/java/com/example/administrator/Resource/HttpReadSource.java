package com.example.administrator.Resource;

import android.os.AsyncTask;
import android.util.Log;

import com.example.administrator.HttpCode;
import com.example.administrator.Interface.HttpFileResult;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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
    private HttpFileResult httpFileResult = null;
    private int FLAG = 0;

    public HttpReadSource(HttpURLConnection httpURLConnection) {
        this.httpURLConnection = httpURLConnection;
    }


    public HttpReadSource(HttpURLConnection httpURLConnection, HttpFileResult httpFileResult, int FLAG) {
        this.httpURLConnection = httpURLConnection;
        this.httpFileResult = httpFileResult;
        this.FLAG = FLAG;
    }


    /**
     * 获取网络请求返回来的数据
     *
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


    /**
     * 将输入流转字节数组
     *
     * @return
     */
    public byte[] getResultByte() {
        ByteArrayOutputStream output = null;
        byte[] buffer = null;
        try {
            in = httpURLConnection.getInputStream();
            output = new ByteArrayOutputStream();
            if (httpURLConnection.getContentLength() > 0)
                buffer = new byte[httpURLConnection.getContentLength()];
            else
                buffer = new byte[0];
            int n = 0;
            int length = 0;
            Object[] object = new Object[3];
            object[0] = buffer.length;

            if (httpURLConnection.getContentLength() == -1) {
                return (HttpCode.NULL + "").getBytes();
            }

            int count = 0;
//            while (count <= 10) {
//                if (in != null) {
//                    int numRead = in.read(buffer);
//                    length += numRead;
//                    object[1] = length;
//                    object[2] = numRead;
//                    if (httpFileResult != null)
//                        httpFileResult.FileNow(object, FLAG);
//                    if (numRead <= 0) {
//                        break;
//                    } else {
//                        output.write(buffer, 0, numRead);
//                    }
//                } else {
//                    break;
//                }
//            }

            while ((n = in.read(buffer)) != -1) {
                output.write(buffer, 0, n);
                length += n;
                object[1] = length;
                object[2] = n;
                if (httpFileResult != null)
                    httpFileResult.FileNow(object, FLAG);
            }
        } catch (IOException e) {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
            buffer = null;
            try {
                if (output != null)
                    output.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return (HttpCode.TIMEOUT + "").getBytes();
        }
        if (output != null)
            try {
                output.close();
                buffer = null;
            } catch (IOException e) {
                e.printStackTrace();
            }

        return output.toByteArray();
    }
}
