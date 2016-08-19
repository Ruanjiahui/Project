package com.example.administrator.Resource;

import com.example.administrator.Abstract.HttpDown;
import com.example.administrator.Abstract.HttpUpload;
import com.example.administrator.HttpCode;
import com.example.administrator.HttpContent;
import com.example.administrator.http_sdk.HttpFile;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Administrator on 2016/8/15.
 */
public class HttpUploadResource extends HttpUpload {

    private HttpConnectSource httpConnectSource = null;
    private static final String CHARSET = "utf-8"; //设置编码


    /**
     * 这个是上传的抽象接口(使用表单上传)
     *
     * @param URL  上传的链接
     * @param list 上传数据的链表
     * @return 1上传成功0上传失败
     */
    @Override
    public byte[] UploadForm(String URL, ArrayList<HttpContent> list) {
        String BOUNDARY = UUID.randomUUID().toString(); //边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; //内容类型
        try {
            httpConnectSource = new HttpConnectSource(URL);
            httpConnectSource.setUseCaches(false); //不使用缓存
            HttpURLConnection conn = httpConnectSource.getHttpURLConnection();

            //设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            if (list != null) {
                DataOutputStream dos = null;
                InputStream is = null;
                byte[] end_data = null;
                /** * 当文件不为空，把文件包装并且上传 */
                OutputStream outputSteam = conn.getOutputStream();

                dos = new DataOutputStream(outputSteam);

                for (int i = 0; i < list.size(); i++) {
                    HttpContent httpFile = list.get(i);
                    if (httpFile != null) {
                        StringBuffer sb = new StringBuffer();
                        sb.append(PREFIX);
                        sb.append(BOUNDARY);
                        sb.append(LINE_END);
                        /**
                         * 这里重点注意：
                         * name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                         * filename是文件的名字，包含后缀名的 比如:abc.png
                         */
                        sb.append("Content-Disposition: form-data; name=\"" + httpFile.getKey() + "\"; filename=\"" + httpFile.getName() + "\"" + LINE_END);
                        sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
                        sb.append(LINE_END);
                        dos.write(sb.toString().getBytes());

                        byte[] bytes = httpFile.getValue();
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
                    return (HttpCode.SUCCESS + "").getBytes();
                }
            }
        } catch (IOException e) {
            return (HttpCode.POST + "").getBytes();
        }
        return (HttpCode.NULL + "").getBytes();
    }

    /**
     * 这个是上传的抽象接口
     *
     * @param URL  上传的链接
     * @param list 上传数据的链表
     * @return 1上传成功0上传失败
     */
    @Override
    public byte[] Upload(String URL, ArrayList<HttpContent> list) {
        return new byte[0];
    }
}
