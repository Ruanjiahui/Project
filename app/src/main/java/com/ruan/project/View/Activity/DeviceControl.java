package com.ruan.project.View.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

import com.DeviceURL;
import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.ruan.project.Moudle.UserDevice;
import com.ruan.project.Other.DataBase.DatabaseOpera;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.R;
import com.ruan.project.View.Control.SocketSwitchFragment;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/20.
 */
public class DeviceControl extends BaseActivity {
    private ArrayList<Map<String, String>> map = null;
    public static Activity activity = null;
    private String deviceID = null;
    private View view = null;
    private UserDevice userDevice = null;
    private String flag = null;

    /**
     * Start()
     */
    @Override
    public void init() {
        activity = this;

        deviceID = getIntent().getExtras().getString("data");
        flag = getIntent().getExtras().getString("flag");

        view = LayoutInflater.from(context).inflate(R.layout.devicecontrol, null);

        setTopColor(R.color.Blue);
        setTopTitleColor(R.color.White);
        setTitle("设备控制");
        setRightTitleVisiable(false);
        setLeftTitleColor(R.color.White);


        map = new DatabaseOpera(context).DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, "deviceID = ?", new String[]{deviceID});

        userDevice = new UserDevice();
        userDevice.getUserDeviceMoudle(map);


        switch (Integer.parseInt(flag)) {
            case DeviceURL.Switch:
                intentFragment(SocketSwitchFragment.getInstance(userDevice));
                break;
        }
        //RGB灯控制
//        regLight = new REGlight();
//        regLight.setRed(1.000000);
//        regLight.setGreen(1.000000);
//        regLight.setBlue(1.000000);
//        regLight.setAphla(1.000000);
//        intentFragment(RGBLight.getInstance(userDevice));

        setContent(view);

    }

    /**
     * 跳转Fragment
     *
     * @param targetFragment
     */
    private void intentFragment(Fragment targetFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.deviceControlContent, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commitAllowingStateLoss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Applications.getInstance().removeOneActivity(this);

    }
}
