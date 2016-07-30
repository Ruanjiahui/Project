package com.ruan.project.Other.AirKiss;

/**
 * Created by Administrator on 2016/7/28.
 */
public interface AirKissCallBack {

    /**
     * 这个是链接成功触发的接口
     *
     * @param object
     */
    public void Result(Object object);

    /**
     * 这个链接不成功触发的接口
     *
     * @param error
     */
    public void Error(int error);
}
