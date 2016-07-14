package com.example.administrator.ui_sdk;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/21.
 */
public class Applications extends Application {

    private ArrayList<Activity> list = new ArrayList<>();
    private static Applications instance;

    private Applications() {

    }

    /**
     * 单例模式中获取唯一的 Application
     */
    public static Applications getInstance() {
        if (null == instance) {
            instance = new Applications();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        list.add(activity);
    }

    /**
     * 销毁全部activity
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        for (Activity activity : list) {
            activity.finish();
        }
        System.exit(0);
    }

    public void removeActivity() {
        for (int i = list.size() - 1; i >= 1; i--) {
            Activity activity = list.get(i);
            activity.finish();
            list.remove(i);
        }
    }

    public void removeOneActivity(Activity activity) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == activity) {
                list.get(i).finish();
                list.remove(i);
            }
        }
    }
}
