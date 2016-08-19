package com.blink.blinkiot.Other.System;

/**
 * Created by Administrator on 2016/8/4.
 */
public interface ReceiverHandler {

    /**
     * 这个处理广播的接口
     *
     * @param ACTION
     */
    public void Result(String ACTION, int FLAG);


    /**
     * 监听失败的接口
     *
     * @param STATE
     */
    public void Error(String STATE, int position);


    public interface TimeHandler {


        /**
         * 这个进行设备数据的获取 参数分别是  IP  端口  数据  标识
         */
        public void Start(int FLAG);

    }


    public interface ReceiverTime {


        public boolean Stop();
    }

}
