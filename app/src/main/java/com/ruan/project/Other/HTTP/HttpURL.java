package com.ruan.project.Other.HTTP;

/**
 * Created by Administrator on 2016/7/21.
 */
public class HttpURL {

    public static String IP = "http://120.24.79.68:8060/";
    //请求判断设备是都在线
    public static String CheckOnline = IP + "dataManipulation/deviceState";

    //通过外网控制设备
    public static String ControlDevice = IP + "dataManipulation/forwardData";

    //这个标志当前网络状态
    // 0   当前没有网络状态
    // 1   当前是wifi状态
    // 2   当前是外网状态
    public static int STATE = 0;
}
