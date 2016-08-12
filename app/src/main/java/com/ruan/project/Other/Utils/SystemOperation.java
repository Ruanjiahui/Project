package com.ruan.project.Other.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/12.
 */
public class SystemOperation {

    /**
     * 关闭软键盘
     *
     * @param activity
     */
    public static void Closekeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
