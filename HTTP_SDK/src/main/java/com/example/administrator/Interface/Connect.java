package com.example.administrator.Interface;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/8/10.
 */
public interface Connect {

    public interface Http {

        /**
         * 连接网络的接口
         *
         * @return
         */
        public byte[] connection();

    }

    public interface HttpString {

        /**
         * 连接网络的接口
         *
         * @return
         */
        public String connection();

    }


}
