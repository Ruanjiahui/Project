package com.ruan.project.Moudle;

/**
 * Created by Administrator on 2016/8/17.
 */
public class Version {

    private String apkinfo = null;
    private String version = null;
    private String apkdownload = null;
    private String md5 = null;
    private boolean update = false;

    private static Version versionObj = null;

    public static Version getVersionObj() {
        if (versionObj == null) {
            versionObj = new Version();
            synchronized (versionObj) {
            }
        }
        return versionObj;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public String getApkinfo() {
        return apkinfo;
    }

    public void setApkinfo(String apkinfo) {
        this.apkinfo = apkinfo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApkdownload() {
        return apkdownload;
    }

    public void setApkdownload(String apkdownload) {
        this.apkdownload = apkdownload;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
