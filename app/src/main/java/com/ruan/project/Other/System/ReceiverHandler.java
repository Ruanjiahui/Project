package com.ruan.project.Other.System;

/**
 * Created by Administrator on 2016/8/4.
 */
public interface ReceiverHandler {

    /**
     * 这个处理广播的接口
     *
     * @param STATE
     */
    public void Result(String STATE, int position);


    /**
     * 监听失败的接口
     *
     * @param STATE
     */
    public void Error(String STATE, int position);

}
