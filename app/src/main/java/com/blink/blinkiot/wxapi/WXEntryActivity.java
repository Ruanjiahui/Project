package com.blink.blinkiot.wxapi;


import com.blink.blinkiot.Moudle.User;
import com.blink.blinkiot.Other.DatabaseTableName;
import com.blink.blinkiot.Other.HTTP.HttpURL;
import com.blink.blinkiot.Other.System.FileURL;
import com.blink.blinkiot.Other.Weixin.Constants;
import com.blink.blinkiot.Other.Weixin.WXUser;
import com.blink.blinkiot.R;
import com.blink.blinkiot.Start.ActivityCode;
import com.blink.blinkiot.View.Activity.Login;
import com.example.administrator.HttpCode;
import com.example.administrator.Interface.HttpFileResult;
import com.example.administrator.Interface.HttpResult;
import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.Database.GetDatabaseData;
import com.example.administrator.data_sdk.Database.LoadClass;
import com.example.administrator.data_sdk.Database.LoadResouce;
import com.example.administrator.data_sdk.FileUtil.FileTool;
import com.example.administrator.data_sdk.JSON.JSONClass;
import com.example.administrator.data_sdk.JSON.JSONResouce;
import com.example.administrator.http_sdk.HTTP;
import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler, HttpResult.HttpString, HttpFileResult {

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    private View view = null;
    private RelativeLayout wxRel = null;
    private User user = null;

    /**
     * Start()
     */
    @Override
    public void init() {
        setTileBar(0);

        view = LayoutInflater.from(context).inflate(R.layout.wxlayout, null);

        wxRel = (RelativeLayout) view.findViewById(R.id.wxRel);

        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        // 将该app注册到微信
        api.registerApp(Constants.APP_ID);

        api.handleIntent(getIntent(), this);

        DensityUtil.setLinearSize(wxRel, BaseActivity.width, BaseActivity.height);


        setContent(view);
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//
//        setIntent(intent);
//        api.handleIntent(intent, this);
//    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                Toast.makeText(context, req.openId + "COMMAND_GETMESSAGE_FROM_WX", Toast.LENGTH_SHORT).show();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                Toast.makeText(context, req.openId + "COMMAND_SHOWMESSAGE_FROM_WX", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(context, req.openId + "req", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //获取微信的openID
//    Constants.openID = resp.openId;
//    SharedPreferences pref = context.getSharedPreferences("123", Context.MODE_APPEND);
//    SharedPreferences.Editor editor = pref.edit();
//    editor.putString("AAA", Constants.openID);
//    editor.commit();
//    wxRel.setVisibility(View.VISIBLE);
//    new HTTP(this, HttpCode.GET, HttpURL.GetAccess_token, null, 0, false);

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                SendAuth.Resp resp1 = (SendAuth.Resp) resp;
                Constants.code = resp1.code;
                wxRel.setVisibility(View.VISIBLE);
                //获取用户的Access_token
                new HTTP(this, HttpCode.GET, HttpURL.GetAccess_token + Constants.code + "&grant_type=authorization_code", null, 0, false);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Applications.getInstance().removeOneActivity(this);
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Toast.makeText(context, resp.openId + "ERR_AUTH_DENIED", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(context, resp.openId + "default", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 处理http成功返回的接口
     *
     * @param code   请求标识
     * @param result 请求返回的字节流
     */
    @Override
    public void onSucceful(int code, String result) {
        //获取用户的access_token
        if (code == 0) {
            try {
                JSONObject json = new JSONObject(result);
                Constants.access_token = json.getString("access_token");
                Constants.expires_in = json.getInt("expires_in");
                Constants.openID = json.getString("openid");
                Constants.refresh_token = json.getString("refresh_token");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //获取用户的信息
            new HTTP(this, HttpCode.GET, HttpURL.GetWXUserInfor + "access_token=" + Constants.access_token + "&openid=" + Constants.openID, null, 1, false);
            //获取用户的信息
        } else if (code == 1) {
            user = User.getInstance();
            try {
                JSONObject json = new JSONObject(result);
                user.setUserID(json.getString("openid"));
                user.setUserName(json.getString("nickname"));
                user.setUserSex(String.valueOf(json.getInt("sex")));
                user.setUserCity(json.getString("city"));
                user.setUserURL(json.getString("headimgurl"));
                user.setUserLoginStyle(ActivityCode.WEIXIN);
                user.setUserLogin(User.ONLINE);
                user.setUserImage("weixin" + user.getUserID() + ".png");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("userID", user.getUserID());
            contentValues.put("userName", user.getUserName());
            contentValues.put("userSex", user.getUserSex());
            contentValues.put("userCity", user.getUserCity());
            contentValues.put("userURL", user.getUserURL());
            contentValues.put("userLoginStyle", user.getUserLoginStyle());
            contentValues.put("userLogin", user.getUserLogin());
            contentValues.put("userImage", user.getUserImage());

            //将数据插进数据库
            new GetDatabaseData().Insert2Update(this, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName, "userID = ?", new String[]{user.getUserID()}, contentValues, "userID = ?", new String[]{user.getUserID()});
            new HTTP(this, HttpCode.DOWN, user.getUserURL(), null, 0);
        }
    }

    /**
     * 处理http连接出错的接口
     *
     * @param code  请求标识
     * @param Error 请求出错的标识
     */
    @Override
    public void onError(int code, int Error) {
        wxRel.setVisibility(View.GONE);
        Applications.getInstance().removeOneActivity(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Applications.getInstance().removeOneActivity(WXEntryActivity.this);
    }

    /**
     * 下载软件开始
     *
     * @param FLAG
     */
    @Override
    public void FileStart(int FLAG) {

    }

    /**
     * 下载软件进行
     *
     * @param values
     * @param FLAG
     */
    @Override
    public void FileNow(Object[] values, int FLAG) {

    }

    /**
     * 文件处理结束调用的接口
     *
     * @param bytes 返回的字节数组
     * @param FLAG  返回的标识
     */
    @Override
    public void FileEnd(byte[] bytes, int FLAG) {
        if (FLAG == 0) {
            FileTool.saveFileByte(bytes, user.getUserImage(), FileURL.LogoPath);
            wxRel.setVisibility(View.GONE);
            Applications.getInstance().removeOneActivity(this);
        }
    }

    /**
     * 下载软件错误
     *
     * @param error
     * @param FLAG
     */
    @Override
    public void FileError(int error, int FLAG) {
        wxRel.setVisibility(View.GONE);
        Applications.getInstance().removeOneActivity(this);
    }
}