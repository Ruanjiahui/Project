package com.blink.blinkiot.View.Activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

import com.blink.blinkiot.Start.DeviceURL;
import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.blink.blinkiot.Moudle.UserDevice;
import com.blink.blinkiot.Other.DataBase.DatabaseOpera;
import com.blink.blinkiot.Other.DatabaseTableName;
import com.blink.blinkiot.R;
import com.blink.blinkiot.View.Control.RGBLightFragment;
import com.blink.blinkiot.View.Control.SocketSwitchFragment;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/20.
 */
public class DeviceControl extends BaseActivity {
    private ArrayList<Map<String, String>> map = null;
    public static Activity activity = null;
    private String deviceMac = null;
    private View view = null;
    private UserDevice userDevice = null;
    private String flag = null;
    private String tableName = null;
    private boolean status;

    /**
     * Start()
     */
    @Override
    public void init() {
        activity = this;

        deviceMac = getIntent().getExtras().getString("data");
        flag = getIntent().getExtras().getString("flag");
        status = getIntent().getExtras().getBoolean("status");

        view = LayoutInflater.from(context).inflate(R.layout.devicecontrol, null);

        setTopColor(R.color.Blue);
        setTopTitleColor(R.color.White);
        setTitle(getResources().getString(R.string.DeviceControlTitle));
        setRightTitleVisiable(false);
        setLeftTitleColor(R.color.White);

        if (status)
            tableName = DatabaseTableName.UserDeviceName;
        else
            tableName = DatabaseTableName.AnalogyName;

        map = new DatabaseOpera(context).DataQuerys(DatabaseTableName.DeviceDatabaseName, tableName, "deviceMac = ?", new String[]{deviceMac});

        userDevice = new UserDevice();
        userDevice.getUserDeviceMoudle(map);


        switch (Integer.parseInt(flag)) {
            case DeviceURL.Switch:
                intentFragment(SocketSwitchFragment.getInstance(userDevice , status));
                break;
            case DeviceURL.RGBLight:
                intentFragment(RGBLightFragment.getInstance(userDevice));
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
