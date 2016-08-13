package com.example.administrator.Abstract;

/**
 * Created by Administrator on 2016/8/13.
 */
public abstract class HttpClass {

    /**
     * 对象转http数据连接
     *
     * @param object
     * @return
     */
    protected abstract byte[] setObjectToByte(Object object, boolean SuperClass);


}
