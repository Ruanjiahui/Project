package com.example.administrator.http_sdk;

/**
 * Created by Administrator on 2016/2/16.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.administrator.Abstract.HttpUploadDown;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * * 实现文件上传的工具类
 */
public class UploadDown extends HttpUploadDown {
    private static final int TIME_OUT = 10 * 10000000; //超时时间
    private static final String CHARSET = "utf-8"; //设置编码


    private HttpConnectSource httpConnectSource = null;
    private HttpReadSource httpReadSource = null;

    /**
     * android上传文件到服务器
     *
     * @param file 需要上传的文件
     * @param uri  请求的rul
     * @param data 上传的数据
     * @param key  上传数据相对应的key
     * @return
     */
    @Override
    public String Upload(ArrayList<File> file, String uri, String data, String key) {
        String BOUNDARY = UUID.randomUUID().toString(); //边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; //内容类型
        try {
            httpConnectSource = new HttpConnectSource(uri + "?" + data);
            httpConnectSource.setUseCaches(false); //不使用缓存
            HttpURLConnection conn = httpConnectSource.getHttpURLConnection();

            //设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            if (file != null) {
                DataOutputStream dos = null;
                InputStream is = null;
                byte[] end_data = null;
                /** * 当文件不为空，把文件包装并且上传 */
                OutputStream outputSteam = conn.getOutputStream();

                dos = new DataOutputStream(outputSteam);

                for (int i = 0; i < file.size(); i++) {
                    File files = file.get(i);
                    if (files != null) {
                        StringBuffer sb = new StringBuffer();
                        sb.append(PREFIX);
                        sb.append(BOUNDARY);
                        sb.append(LINE_END);
                        /**
                         * 这里重点注意：
                         * name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                         * filename是文件的名字，包含后缀名的 比如:abc.png
                         */
                        sb.append("Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + files.getName() + "\"" + LINE_END);
                        sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
                        sb.append(LINE_END);
                        dos.write(sb.toString().getBytes());

                        is = new FileInputStream(files);
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        while ((len = is.read(bytes)) != -1) {
                            dos.write(bytes, 0, len);
                        }
                        is.close();
                        dos.write(LINE_END.getBytes());
                        end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                    }
                }

                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码 200=成功
                 * 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                if (res == 200) {
                    httpReadSource = new HttpReadSource(conn);
                    return httpReadSource.getResult();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 这个是实现上传字节流的方法是上传
     *
     * @param inputStream
     * @param uri
     * @param data
     * @param key
     * @return
     */
    @Override
    public String UploadInputStream(InputStream inputStream, String uri, String data, String key) {
        String BOUNDARY = UUID.randomUUID().toString(); //边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; //内容类型
        try {

            httpConnectSource = new HttpConnectSource(uri + "?" + data);
            httpConnectSource.setUseCaches(false); //不使用缓存
            HttpURLConnection conn = httpConnectSource.getHttpURLConnection();

            //设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            if (inputStream != null) {
                DataOutputStream dos = null;
                InputStream is = null;
                byte[] end_data = null;
                /** * 当文件不为空，把文件包装并且上传 */
                OutputStream outputSteam = conn.getOutputStream();

                dos = new DataOutputStream(outputSteam);

                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意：
                 * name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */
                sb.append("Content-Disposition: form-data; name=\"" + key + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());

                is = inputStream;
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();

                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码 200=成功
                 * 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                if (res == 200) {
                    httpReadSource = new HttpReadSource(conn);
                    return httpReadSource.getResult();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 这个是一个下载的方法
     *
     * @param uri
     * @return
     */
    @Override
    public Bitmap DownBitmap(String uri) {
        try {
            httpConnectSource = new HttpConnectSource(uri);
            httpConnectSource.setUseCaches(false); //不使用缓存
            HttpURLConnection conn = httpConnectSource.getHttpURLConnection();

            conn.connect();
            /**
             * 获取响应码 200=成功
             * 当响应成功，获取响应的流
             */
            int res = conn.getResponseCode();
            if (res == 200) {
                InputStream in = conn.getInputStream();

                Bitmap bitmap = BitmapFactory.decodeStream(in);
                return bitmap;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 这个是下载文件的实现方法
     *
     * @param uri
     * @return
     */
    @Override
    public InputStream DownFile(String uri) {
        try {
            httpConnectSource = new HttpConnectSource(uri);
            httpConnectSource.setUseCaches(false); //不使用缓存
            HttpURLConnection conn = httpConnectSource.getHttpURLConnection();

            conn.connect();
            /**
             * 获取响应码 200=成功
             * 当响应成功，获取响应的流
             */
            int res = conn.getResponseCode();
            if (res == 200) {
                InputStream in = conn.getInputStream();

                return in;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
