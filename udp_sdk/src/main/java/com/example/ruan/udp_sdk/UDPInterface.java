package com.example.ruan.udp_sdk;

import java.net.DatagramSocket;

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
        public byte[] Reviced();
    }

    public interface UDPHandler {

        public void Handler(byte[] buffer);
    }

}
