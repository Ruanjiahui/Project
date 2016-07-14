package com.example.administrator.ui_sdk;

import android.content.Context;

import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.ui_sdk.View.MyDialog;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/15.
 */
public class CreateDialog {

    public static int dialog_height = 0;
    public static MyDialog ListViewDialog(Context context, ArrayList<ListView_Object> list, int height, int count) {
        MyDialog dialog = new MyDialog(context, R.style.mydialog);
        dialog.setWidth(BaseActivity.width / 3 * 2);
        dialog.setHeight(height * count);
        dialog.DialogState("", list, 0, "", "");
        return dialog;
    }
}
