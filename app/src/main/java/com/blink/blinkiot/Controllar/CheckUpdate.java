package com.blink.blinkiot.Controllar;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.HttpCode;
import com.example.administrator.Interface.HttpFileResult;
import com.example.administrator.Interface.HttpResult;
import com.example.administrator.data_sdk.FileUtil.FileTool;
import com.example.administrator.data_sdk.SystemUtil.SystemTool;
import com.example.administrator.http_sdk.HTTP;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.ui_sdk.MyOnClickInterface;
import com.example.administrator.ui_sdk.View.MyDialog;
import com.blink.blinkiot.Moudle.Version;
import com.blink.blinkiot.Other.HTTP.HttpURL;
import com.blink.blinkiot.Other.System.FileURL;
import com.blink.blinkiot.Other.Utils.ParseXmlService;
import com.blink.blinkiot.R;

import java.io.ByteArrayInputStream;

/**
 * Created by Administrator on 2016/8/16.
 */
public class CheckUpdate implements MyOnClickInterface.Click, HttpFileResult, HttpResult.HttpString {

    private Context context = null;
    private MyDialog myDialog = null;
    private View view = null;
    private HTTP http = null;
    private final int DOWNXML = 0;
    private final int UPDATE = 1;
    private final int INSTALL = 2;
    private int Status = 0;
    private String wifiStatus = null;

    public CheckUpdate(Context context) {
        this.context = context;
    }

    public void Update(int Status) {
        this.Status = Status;
        //进行检查更新
        new HTTP(this, HttpCode.GET, HttpURL.UpdateURL, null, 0, false);

        if (!FileTool.getProperties(context, HttpURL.ConfigName))
            FileTool.WriteProperties(context, HttpURL.ConfigName, context.getResources().getString(R.string.WifiUpdateTxt), "true");
        wifiStatus = FileTool.ReadProperties(context, HttpURL.ConfigName, context.getResources().getString(R.string.WifiUpdateTxt));
    }

    /**
     * 显示对话框
     */
    public void ShowDialog(int FLAG, String Message, String Left, String Right) {
        myDialog = new MyDialog(context, R.style.mydialog, FLAG);
        myDialog.setHeight(BaseActivity.height / 6);
        myDialog.setWidth(BaseActivity.width / 3 * 2);
        myDialog.DialogState(Message, Left, Right);
        myDialog.setRightButColor(R.color.Blue);
        myDialog.setOnClick(this);
        if (STATE)
            //不允许返回
            myDialog.setCancelable(false);
        myDialog.show();
    }

    private String getSystemText(int resid) {
        return context.getResources().getString(resid);
    }


    /**
     * 取消对话框
     */
    public void dimiss() {
        myDialog.dismiss();
    }

    /**
     * 取消组件的显示
     *
     * @param view
     */
    public void setDisView(View view) {
        this.view = view;
    }


    private void disView() {
        if (view != null)
            view.setVisibility(View.GONE);
    }

    @Override
    public void OnRightClick(int FLAG) {
        switch (FLAG) {
            case UPDATE:
                //更新的操作就是下载软件
                new HTTP(this, UPDATE, HttpCode.DOWN, HttpURL.DownApplicationURL, null);
                break;
            case INSTALL:
                //安装软件
                SystemTool.Install(context, FileURL.APKPath, FileURL.APKName);
                return;
        }
        dimiss();
        if (STATE && FLAG == UPDATE) {
            ShowDialog(3, context.getResources().getString(R.string.DowningNow), "", "");
        }
    }

    @Override
    public void OnLeftClick(int FLAG) {
        if (!STATE)
            dimiss();
    }

    /**
     * 下载软件开始
     *
     * @param FLAG
     */
    @Override
    public void FileStart(int FLAG) {
        if (FLAG == UPDATE && "false".equals(wifiStatus))
            Toast.makeText(context, context.getResources().getString(R.string.StartDown), Toast.LENGTH_SHORT).show();
    }

    /**
     * 下载软件进行
     *
     * @param values
     * @param FLAG
     */
    @Override
    public void FileNow(Object[] values, int FLAG) {
        Log.e("Ruan", values[0] + "--" + values[1]);
    }

    //判断这个是不是强制更新的标识
    public static boolean STATE = false;

    /**
     * 文件处理结束调用的接口
     *
     * @param bytes 返回的字节数组
     * @param FLAG  返回的标识
     */
    private int[] localVersion = null;
    private int[] nowVersion = null;


    @Override
    public void FileEnd(byte[] bytes, int FLAG) {
        //下载完成软件
        if (FLAG == UPDATE) {
            FileTool.saveFileByte(bytes, FileURL.APKName, FileURL.APKPath);
            //判断apk的MD5值是否一样
            if (FileURL.MD5.equals(FileTool.getMd5ByFile(FileURL.APKPath, FileURL.APKName)))
                //安装软件
                ShowDialog(INSTALL, getSystemText(R.string.InstallMessageText), getSystemText(R.string.InstallLeftButText), getSystemText(R.string.InstallRightButText));
            else
                Toast.makeText(context, context.getResources().getString(R.string.MD5Fail), Toast.LENGTH_SHORT).show();
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
        if (Status == 0 && wifiStatus != null) {
            if (wifiStatus.equals("true")) {
                //更新的操作就是下载软件
                new HTTP(this, UPDATE, HttpCode.DOWN, HttpURL.DownApplicationURL, null);
                return;
            }
        }
        if (FLAG == UPDATE)
            ShowDialog(UPDATE, getSystemText(R.string.ReUpdateMessageText), getSystemText(R.string.UpdateLeftButText), getSystemText(R.string.UpdateRightButText));
    }


    public void disHttp() {
        http.disConnectionByte();
    }


    private int[] SubString(String code) {
        int[] versionCode = new int[3];
        int m = 0;
        String var = "";
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '.') {
                versionCode[m] = Integer.parseInt(var);
                var = "";
                m++;
            } else {
                var += code.charAt(i);
            }
        }
        versionCode[m] = Integer.parseInt(var);
        return versionCode;
    }

    private boolean isUpdate() {
        //如果前面两个版本都不一样的话一定是强制更新
        if (localVersion[0] < nowVersion[0] || localVersion[1] < nowVersion[1]) {
            STATE = true;
            return true;
        } else if (localVersion[0] == nowVersion[0] && localVersion[1] == nowVersion[1]) {
            if (localVersion[2] < nowVersion[2]) {
                STATE = false;
                return true;
            } else {
                STATE = false;
                return false;
            }
        } else {
            return false;
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
        if (result != null && result.length() != 0) {
            //解析xml文件
            Version version = Version.getVersionObj();
            //解析XML文件用的方法是dom
            if (new ParseXmlService().parseXml(new ByteArrayInputStream(result.getBytes()), version) == null) {
                Toast.makeText(context, context.getResources().getString(R.string.NetError), Toast.LENGTH_SHORT).show();
                return;
            }
            //判断版本号是不是大于本地的版本号
            if (!version.getVersion().equals(SystemTool.getVersionName(context))) {
                HttpURL.DownApplicationURL = version.getApkdownload();
                FileURL.MD5 = version.getMd5();
                //获取本地版本的版本号
                localVersion = SubString(SystemTool.getVersionName(context));
                nowVersion = SubString(version.getVersion());

                //如果前面两个版本都一样的话一定是强制更新
                version.setUpdate(isUpdate());
                //不用更新
                if (!isUpdate()) {
                    if (Status == 1)
                        Toast.makeText(context, context.getResources().getString(R.string.Version), Toast.LENGTH_SHORT).show();
                    disView();
                    return;
                }

                if (Status == 0 && wifiStatus != null) {
                    if (wifiStatus.equals("true")) {
                        //更新的操作就是下载软件
                        new HTTP(this, UPDATE, HttpCode.DOWN, HttpURL.DownApplicationURL, null);
                        return;
                    }
                }
                //如果wifi情况下不提示框
                ShowDialog(UPDATE, getSystemText(R.string.UpdateMessageText), getSystemText(R.string.UpdateLeftButText), getSystemText(R.string.UpdateRightButText));
                disView();
                return;
            }
            disView();
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

    }
}
