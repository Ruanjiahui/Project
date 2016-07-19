package com.example.ruan.udp_sdk;

import java.net.DatagramSocket;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/15.
 */
public interface UDPInterface {

    public interface UDPSend {

        /**
         * 发送消息的接口
         */
        public void Send(byte[] buffer);
    }

    public interface UDPReviced {
        /**
         * 接收消息的接口
         *
         * @return
         */
        public Object[] Reviced();
    }

    public interface UDPHandler {

        public void Handler(Object[] objects);
    }

}
