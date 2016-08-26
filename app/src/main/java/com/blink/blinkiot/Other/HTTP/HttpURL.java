package com.blink.blinkiot.Other.HTTP;

import com.blink.blinkiot.Other.Weixin.Constants;

/**
 * Created by Administrator on 2016/7/21.
 */
public class HttpURL {

    //这个是区分当前是什么版本
    //如果是TEST为0这个是调试版本
    //如果TEST为1这个是正式版本
//    public static int TEST = 0;

    public static String IP = "http://120.24.79.68:8060/";
    //请求判断设备是都在线
    public static String CheckOnline = IP + "dataManipulation/deviceState";

    //通过外网控制设备
    public static String ControlDevice = IP + "dataManipulation/forwardData";

    //获取天气的链接
    public static String WethereURL = "http://apis.baidu.com/heweather/weather/free?city=";

    //必联网上商城的网址
    public static String ShopURL = "http://blink.tmall.com";

    //更新软件的链接
    public static String UpdateURL = "http://app.b-link.net.cn/iot/Android/version.xml";

    //下载软件的链接
    public static String DownApplicationURL = "http://122.228.72.146/imtt.dd.qq.com/16891/BEE7E8FCBBDD77D7B21FD876D21D3203.apk?mkey=57b2e13a4974a542&f=1c58&c=0&fsname=com.example.microclass_3.1_8.apk&csr=4d5s&p=.apk";

    //配置文件的名称
    public static String ConfigName = "config.properties";

    //获取微信access_token
//    public static String GetAccess_token = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + Constants.APP_ID + "&secret=" + Constants.APP_Secret;
    //获取微信用户信息
    public static String GetWXUserInfor = "https://api.weixin.qq.com/sns/userinfo?";
    //获取微信的access_token
//    https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxbf1a3b91393434fd&secret=f8fbd9c6dab48760a686dfeddece0a11&code=011DIdAc0nrodH1W90Bc0qZ7Ac0DIdA7&grant_type=authorization_code
    public static String GetAccess_token = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Constants.APP_ID  + "&secret=" + Constants.APP_Secret + "&code=";

    //这个标志当前网络状态
    // 0   当前没有网络状态
    // 1   当前是wifi状态
    // 2   当前是外网状态
    public static int STATE = 0;

    public static String[] Cityweather = null;
    public static String CityName = null;
    public static String WeatherID = "c3070ac56cff43765b78f3fca4dc812a";
}
