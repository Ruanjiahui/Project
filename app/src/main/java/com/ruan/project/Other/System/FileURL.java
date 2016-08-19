package com.ruan.project.Other.System;

import android.content.Context;
import android.os.Environment;

import com.example.administrator.data_sdk.FileUtil.FilePath;
import com.example.administrator.data_sdk.SystemUtil.SystemTool;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/16.
 */
public class FileURL {

    public static String SDCard = Environment.getExternalStorageDirectory().toString();

    public static String APKPath = SDCard + "/BLink/APK/";
    public static String APKName = "project.apk";

    public static String MD5 = "9aa4eece982bff8bd77141f1dbde82a7";

    private static String SystemURL = "data/data/";


    public static String CacheURL = "";

    public static ArrayList<String> getCacheURL(Context context) {
        ArrayList<String> URL = new ArrayList<String>();
        URL.add(SystemURL + SystemTool.getPackageName(context) + "/app_webview");
        URL.add(SystemURL + SystemTool.getPackageName(context) + "/cache");
        return URL;
    }
}
