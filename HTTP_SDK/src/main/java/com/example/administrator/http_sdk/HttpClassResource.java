package com.example.administrator.http_sdk;

import com.example.administrator.Abstract.HttpClass;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/8/13.
 */
public class HttpClassResource extends HttpClass {


    public byte[] setObject(Object object, boolean SuperClass) {
        return setObjectToByte(object, SuperClass);
    }

    /**
     * 将对象转换成连接的操作方法
     *
     * @param fields
     * @param object
     * @return
     */
    private byte[] setOperatData(Field[] fields, Object object) {
        String data = "";
        try {
            //通过循环将属性的值一一转换成连接的方式
            for (Field field : fields) {
                //设置可以操作的权限
                field.setAccessible(true);
                if ("".equals(field.get(object)) && field.get(object) != null) {
                    data += field.getName() + "=" + field.get(object) + "&";
                }
            }
            data = data.substring(0, data.length() - 1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return data.getBytes();
    }


    /**
     * 对象转http数据连接
     *
     * @param object
     * @param SuperClass
     * @return
     */
    @Override
    protected byte[] setObjectToByte(Object object, boolean SuperClass) {
        Class loadClass = object.getClass();
        Field[] fields = null;
        if (!SuperClass)
            //获取对象的属性
            fields = loadClass.getDeclaredFields();
        else
            fields = loadClass.getFields();

        return setOperatData(fields, object);
    }
}
