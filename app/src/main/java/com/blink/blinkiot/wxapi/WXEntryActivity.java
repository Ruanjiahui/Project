package com.blink.blinkiot.wxapi;


import com.blink.blinkiot.Moudle.User;
import com.blink.blinkiot.Other.DatabaseTableName;
import com.blink.blinkiot.Other.HTTP.HttpURL;
import com.blink.blinkiot.Other.System.FileURL;
import com.blink.blinkiot.Other.Weixin.Constants;
import com.blink.blinkiot.Other.Weixin.Token;
import com.blink.blinkiot.R;
import com.blink.blinkiot.Start.ActivityCode;
import com.example.administrator.HttpCode;
import com.example.administrator.Interface.HttpFileResult;
import com.example.administrator.Interface.HttpResult;
import com.example.administrator.data_sdk.Database.GetDatabaseData;
import com.example.administrator.data_sdk.FileUtil.FileTool;
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

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                break;
            default:
                break;
        }
    }

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
                break;
            default:
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
            //保存access_token到本地
            new Token(context).Saveaccess_token(result);
            //获取用户的信息
            new HTTP(this, HttpCode.GET, HttpURL.GetWXUserInfor + "access_token=" + Constants.access_token + "&openid=" + Constants.openID, null, 1, false);
            //获取用户的信息
        } else if (code == 1) {
            new Token(context).SaveUserInfo(result, this);
            user = User.getInstance();
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