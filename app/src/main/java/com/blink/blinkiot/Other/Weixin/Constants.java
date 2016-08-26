package com.blink.blinkiot.Other.Weixin;

public class Constants {
    // APP_ID 是第三方app和微信通信的openapi接口
    public static final String APP_ID = "wxbf1a3b91393434fd";
    //通过appid和appsecret获取access_token
    public static final String APP_Secret = "f8fbd9c6dab48760a686dfeddece0a11";

    public static String access_token = "";

    public static String openID = "";

    public final static String scope = "snsapi_userinfo";

    public final static String state = "carjob_wx_login";

    public static String code = "";

    public static int expires_in = 0;

    public static String refresh_token = "";

    public static class ShowMsgActivity {
        public static final String STitle = "showmsg_title";
        public static final String SMessage = "showmsg_message";
        public static final String BAThumbData = "showmsg_thumb_data";
    }
}
