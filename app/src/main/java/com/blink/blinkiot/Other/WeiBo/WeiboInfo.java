package com.blink.blinkiot.Other.WeiBo;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

/**
 * Created by Administrator on 2016/8/25.
 */
public class WeiboInfo implements RequestListener {

    private Context context = null;
    private UsersAPI usersAPI = null;
    private Oauth2AccessToken Token = null;

    public WeiboInfo(Context context, Oauth2AccessToken Token) {
        this.context = context;
        this.Token = Token;
        getUserInfo();
    }


    private void getUserInfo() {
        usersAPI = new UsersAPI(context, Constants.APP_KEY, Token);
        long uid = Long.parseLong(Token.getUid());
//        Toast.makeText(context , uid + "--" , Toast.LENGTH_SHORT).show();
        usersAPI.show(uid, this);
    }

    @Override
    public void onComplete(String response) {
        Toast.makeText(context , response + "--" , Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(response)) {
            // 调用 User#parse 将JSON串解析成User对象
            WeiboUser user = WeiboUser.parse(response);

            Toast.makeText(context , user.screen_name , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onWeiboException(WeiboException e) {
//        Toast.makeText(context , e.toString() + "**", Toast.LENGTH_SHORT).show();
//        AccessTokenKeeper.writeAccessToken(this, mAccessToken);
        SharedPreferences pref = context.getSharedPreferences("123", Context.MODE_APPEND);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("AAA", e.toString() );
        editor.commit();
    }
}
