package com.example.administrator.data_sdk.SystemUtil;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/15.
 * <p/>
 * 这个类是实现系统的操作
 */
public class SystemTool {

    // 以下是获得版本信息的工具方法
    private static PackageInfo PackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

    /**
     * 判断手机是否链接网络
     *
     * @param context
     * @return
     */
    public static boolean isNetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    /**
     * 判断当前网络类型
     *
     * @param context
     * @return
     */
    public static int isNetState(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                //当前wifi网络状态
                return 1;
            } else {
//                if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                //当前移动数据网络状态
                return 2;
            }
        } else {
            //当前没有网络
            return 0;
        }
    }


    //版本名

    public static String getVersionName(Context context) {
        return PackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return PackageInfo(context).versionCode;
    }

    /**
     * 获取软件的应用的包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        return PackageInfo(context).packageName;
    }


    /**
     * 显示弹出框
     *
     * @param context
     * @param content
     */
    public static void showToast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }


    /**
     * 解析Base64的转码
     *
     * @param msg
     * @return
     */
    public static String Transcodingdecode(String msg) {
        return new String(Base64.decode(msg.getBytes(), Base64.DEFAULT));
    }

    /**
     * 加密Base64的转码
     *
     * @param s
     * @return
     */
    public static String EncodeStr(String s) {
        return new String(Base64.encode(s.getBytes(), Base64.DEFAULT));
    }


    /**
     * 系统打印
     *
     * @param msg
     */
    public static void Log(String msg) {
        Log.e("Ruan", msg);
    }


    /**
     * 扫描全盘的图片获取的路径格式是jpeg
     *
     * @param context
     * @return
     */
    public static ArrayList<Map<Object, Object>> LocalImage(Context context) {
        // 指定要查询的uri资源
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        // 获取ContentResolver
        ContentResolver contentResolver = context.getContentResolver();
        // 查询的字段
        String[] projection = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA, MediaStore.Images.Media.SIZE};
        // 条件
        String selection = MediaStore.Images.Media.MIME_TYPE + "=?";
        // 条件值(這裡的参数不是图片的格式，而是标准，所有不要改动)
        String[] selectionArgs = {"image/jpeg"};
        // 排序
        String sortOrder = MediaStore.Images.Media.DATE_MODIFIED + " desc";
        // 查询sd卡上的图片
        Cursor cursor = contentResolver.query(uri, projection, selection,
                selectionArgs, sortOrder);
        ArrayList<Map<Object, Object>> imageList = new ArrayList<>();
        if (cursor != null) {
            Map<Object, Object> imageMap = null;
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                imageMap = new HashMap<>();
                // 获得图片的id
                imageMap.put("imageID", cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media._ID)));
                // 获得图片显示的名称
                imageMap.put("imageName", cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
                // 获得图片的信息
                imageMap.put(
                        "imageInfo",
                        "" + cursor.getLong(cursor
                                .getColumnIndex(MediaStore.Images.Media.SIZE) / 1024)
                                + "kb");
                // 获得图片所在的路径(可以使用路径构建URI)
                imageMap.put("data", cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA)));
                imageList.add(imageMap);
            }
            // 关闭cursor
            cursor.close();
        }
        return imageList;
    }

    /**
     * 判断Mac地址的合法性
     *
     * @param Mac
     * @return
     */
    public static boolean isMac(String Mac) {
        int num = 2;
        if (Mac.length() == 17) {
            for (int i = 0; i < Mac.length(); i++) {
                if (num == i) {
                    if (Mac.charAt(i) == ':' || Mac.charAt(i) == '-') {
                        num += 3;
                    } else
                        return false;
                    continue;
                }
                if ((Mac.charAt(i) >= '0' && Mac.charAt(i) <= '9') || (Mac.charAt(i) >= 'A' && Mac.charAt(i) <= 'F') || (Mac.charAt(i) >= 'a' && Mac.charAt(i) <= 'f')) {
                } else
                    return false;
            }
            return true;
        }
        return false;
    }
}
