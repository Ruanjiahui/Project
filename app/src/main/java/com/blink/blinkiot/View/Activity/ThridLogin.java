//package com.example.ruan.myapplication;
//
//import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
//import android.view.Menu;
//import android.view.MenuItem;
//
//import com.example.ruan.myapplication.KugouLayout;
//import com.example.ruan.myapplication.R;
//
//
//public class MainActivity extends ActionBarActivity {
//
//    KugouLayout kugouLayout;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        kugouLayout = new KugouLayout(this);
//        kugouLayout.attach(this);
//        kugouLayout.addHorizontalScrollableView(findViewById(R.id.horizontalScrollView));
//        kugouLayout.setLayoutCloseListener(new KugouLayout.LayoutCloseListener() {
//            @Override
//            public void onLayoutClose() {
//                finish();
//            }
//        });
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        switch (id){
//            case R.id.action_normal_anim:
//                kugouLayout.setAnimType(KugouLayout.NORMAL_ANIM);
//                return true;
//            case R.id.action_rebound_anim:
//                kugouLayout.setAnimType(KugouLayout.REBOUND_ANIM);
//                return true;
//            case R.id.action_always_rebound_anim:
//                kugouLayout.setAnimType(KugouLayout.ALWAYS_REBOUND);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//
//}

package com.blink.blinkiot.View.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.blink.blinkiot.Other.WeiBo.AccessTokenKeeper;
import com.blink.blinkiot.Other.WeiBo.Constants;
import com.blink.blinkiot.Other.WeiBo.WeiboInfo;
import com.blink.blinkiot.Start.ActivityCode;
import com.example.administrator.ui_sdk.Applications;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

public class ThridLogin extends Activity implements WeiboAuthListener {

    private AuthInfo mAuthInfo = null;
    private SsoHandler mSsoHandler = null;
    private String FLAG = null;
    private Oauth2AccessToken mAccessToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Applications.getInstance().addActivity(this);
        FLAG = getIntent().getStringExtra("data");

        switch (FLAG) {
            case ActivityCode.QQ:
                break;
            case ActivityCode.WEIBO:
                WEIBO();
                break;
        }
    }

    /**
     * 微博实例化
     */
    private void WEIBO() {
        // 创建微博实例
        //mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        // 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
        mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        mSsoHandler = new SsoHandler(this, mAuthInfo);
        mSsoHandler.authorize(this);
    }


    @Override
    public void onComplete(Bundle bundle) {
// 从 Bundle 中解析 Token
        mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
        //从这里获取用户输入的 电话号码信息
        String phoneNum = mAccessToken.getPhoneNum();
        if (mAccessToken.isSessionValid()) {
            // 显示 Token
//            updateTokenView(false);
            // 保存 Token 到 SharedPreferences
            AccessTokenKeeper.writeAccessToken(this, mAccessToken);
            new WeiboInfo(this , mAccessToken);
//            Toast.makeText(this,
//                    "授权成功", Toast.LENGTH_SHORT).show();
        } else {
            // 以下几种情况，您会收到 Code：
            // 1. 当您未在平台上注册的应用程序的包名与签名时；
            // 2. 当您注册的应用程序包名与签名不正确时；
            // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
            String code = bundle.getString("code");
            String message = "授权失败";
            if (!TextUtils.isEmpty(code)) {
                message = message + "\nObtained the code: " + code;
            }
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onWeiboException(WeiboException e) {
    }

    @Override
    public void onCancel() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Applications.getInstance().removeOneActivity(this);
    }
}

