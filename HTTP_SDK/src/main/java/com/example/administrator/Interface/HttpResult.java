package com.example.administrator.Interface;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/8/10.
 */
public interface HttpResult {


    public interface Http {

        /**
         * 处理http成功返回的接口
         *
         * @param code        请求标识
         * @param inputStream 请求返回的字节流
         */
        public void onSucceful(int code, byte[] inputStream);

        /**
         * 处理http连接出错的接口
         *
         * @param code  请求标识
         * @param Error 请求出错的标识
         */
        public void onError(int code, int Error);
    }


    public interface HttpString {

        /**
         * 处理http成功返回的接口
         *
         * @param code   请求标识
         * @param result 请求返回的字节流
         */
        public void onSucceful(int code, String result);

        /**
         * 处理http连接出错的接口
         *
         * @param code  请求标识
         * @param Error 请求出错的标识
         */
        public void onError(int code, int Error);
    }
}
