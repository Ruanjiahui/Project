package com.blink.blinkiot.Other.Weixin;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.blink.blinkiot.Moudle.User;
import com.blink.blinkiot.Other.DatabaseTableName;
import com.blink.blinkiot.Other.HTTP.HttpURL;
import com.blink.blinkiot.Other.System.FileURL;
import com.blink.blinkiot.Start.ActivityCode;
import com.example.administrator.HttpCode;
import com.example.administrator.Interface.HttpFileResult;
import com.example.administrator.Interface.HttpResult;
import com.example.administrator.data_sdk.Database.GetDatabaseData;
import com.example.administrator.data_sdk.FileUtil.FileTool;
import com.example.administrator.data_sdk.SystemUtil.SystemTool;
import com.example.administrator.http_sdk.HTTP;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/27.
 */
public class Token implements HttpResult.HttpString, HttpFileResult {

    private Context context = null;

    public Token(Context context) {
        this.context = context;
    }

    /**
     * 保存微信的access_token
     *
     * @param access_token
     * @param refresh_token
     * @param openID
     * @param filename
     * @param path
     */
    public static void SaveAccessToken(String access_token, String refresh_token, String openID, String filename, String path) {
        JSONObject json = new JSONObject();
        try {
            json.put("access_token", access_token);
            json.put("refresh_token", refresh_token);
            json.put("openID", openID);

            String str = SystemTool.EncodeStr(json.toString());
            FileTool.saveFileByte(str.getBytes(), filename, path);
        } catch (JSONException e) {
            Log.e("Ruan", "保存失败");
        }
    }

    /**
     * 获取保存本地的access_token
     *
     * @param filename
     * @param path
     */
    public static void ReadAccessToken(String filename, String path) {
        String str = FileTool.readFileByte(filename, path);
        if (str == null)
            return;
        try {
            JSONObject json = new JSONObject(SystemTool.Transcodingdecode(str));
            Constants.refresh_token = json.getString("refresh_token");
            Constants.access_token = json.getString("access_token");
            Constants.openID = json.getString("openID");
        } catch (JSONException e) {
            Log.e("Ruan", "读取失败");
        }
    }

    private User user = null;

    /**
     * 检测本地的access_token是否有效
     */
    public void CheckAccess_Token() {
        //获取本地的access_token
        ReadAccessToken(FileURL.getWEIXINTokenName, FileURL.getWEIXINTokenPath(context));
        if (Constants.access_token != null) {
            new HTTP(this, HttpCode.GET, HttpURL.CheckAccess_token + Constants.access_token + "&openid=" + Constants.openID, null, 0, false);
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
        //检测access_token是否有效的结果
        if (code == 0) {
            try {
                if ("ok".equals(new JSONObject(result).getString("errmsg")))
                    //检测有效
                    //直接获取用户的信息进行更新
                    getUserInfoURL();
                else
                    //检测无效
                    //通过refresh_token获取新的access_token
                    getRefreshToken();
            } catch (JSONException e) {
                return;
            }
        }
        //获取用户信息的结果
        if (code == 1) {
            //保存用户信息
            SaveUserInfo(result, this);
        }
        //获取新的access_token
        if (code == 2) {
            Saveaccess_token(result);
            //直接获取用户的信息进行更新
            getUserInfoURL();
        }
    }

    private void getUserInfoURL() {
        new HTTP(this, HttpCode.GET, HttpURL.GetWXUserInfor + "access_token=" + Constants.access_token + "&openid=" + Constants.openID, null, 1, false);
    }

    private void getRefreshToken() {
        new HTTP(this, HttpCode.GET, HttpURL.Refresh_Token + Constants.refresh_token, null, 2, false);
    }

    /**
     * 处理http连接出错的接口
     *
     * @param code  请求标识
     * @param Error 请求出错的标识
     */
    @Override
    public void onError(int code, int Error) {

    }

    public void Saveaccess_token(String result) {
        try {
            JSONObject json = new JSONObject(result);
            Constants.access_token = json.getString("access_token");
            Constants.expires_in = json.getInt("expires_in");
            Constants.openID = json.getString("openid");
            Constants.refresh_token = json.getString("refresh_token");
            //将access_token保存到本地
            Token.SaveAccessToken(Constants.access_token, Constants.refresh_token, Constants.openID, FileURL.getWEIXINTokenName, FileURL.getWEIXINTokenPath(context));
        } catch (JSONException e) {
        }
    }

    /**
     * 保存用户信息
     *
     * @param result
     * @param httpFileResult
     */
    public void SaveUserInfo(String result, HttpFileResult httpFileResult) {
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
        new GetDatabaseData().Insert2Update(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName, "userID = ?", new String[]{user.getUserID()}, contentValues, "userID = ?", new String[]{user.getUserID()});
        new HTTP(httpFileResult, HttpCode.DOWN, user.getUserURL(), null, 0);
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
        //保存获取下来头像
        if (FLAG == 0) FileTool.saveFileByte(bytes, user.getUserImage(), FileURL.LogoPath);

    }

    /**
     * 下载软件错误
     *
     * @param error
     * @param FLAG
     */
    @Override
    public void FileError(int error, int FLAG) {

    }
}
