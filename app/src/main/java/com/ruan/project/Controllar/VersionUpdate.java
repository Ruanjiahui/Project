package com.ruan.project.Controllar;

import android.content.Context;

import com.example.administrator.data_sdk.SystemUtil.SystemTool;

/**
 * Created by Administrator on 2016/8/13.
 */
public class VersionUpdate {


    private Context context = null;
    private int Version = 0;
    private String VersionName = null;
    private int newVersion = 0;
    private String newVersionName = null;

    public VersionUpdate(Context context) {
        this.context = context;
    }

    /**
     * 获取本地的版本号
     */
    public void CheckVersion() {
        Version = SystemTool.getVersionCode(context);
    }

    /**
     * 获取本地的版本名称
     */
    public void CheckVersionName() {
        VersionName = SystemTool.getVersionName(context);
    }


    /**
     * 判断是否需要更新
     *
     * @param newVersion 新版本的版本号
     * @param oldVersion 旧版本的版本号
     * @return false则不需要更新true则需要更新
     */
    public boolean isUpdate(int newVersion, int oldVersion) {
        if (newVersion > oldVersion)
            return true;
        return false;
    }


    /**
     * 获取云端的新版本的数据包
     *
     * @param URL
     */
    public void getNewVersion(String URL) {

    }

}
