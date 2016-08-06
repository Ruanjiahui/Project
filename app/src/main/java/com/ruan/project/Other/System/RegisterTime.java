package com.ruan.project.Other.System;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.DeviceURL;
import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.ui_sdk.Applications;
import com.ruan.project.Moudle.TimeMoudle;
import com.ruan.project.View.Activity.DeviceControl;
import com.ruan.project.View.Control.SocketSwitchFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/5.
 */
public class RegisterTime implements ReceiverHandler {

    private Context context = null;
    private ListenTime listenTime = null;
    private Map<String, Map<String, Object>> list = null;
    private Map<String, Object> map = null;

    public RegisterTime(Context context) {
        this.context = context;
        list = new HashMap<>();
        map = new HashMap<>();
    }


    public void registerTime(String[] time, String Action, int position) {
        listenTime = new ListenTime(context, this, Action, position);
        map.put("" + position, listenTime);
        list.put(Action, map);
        listenTime.setListtenTime(time);
        listenTime.StartListen();
    }

    public boolean getregisterTime(String ACTION, int position) {
        if (list.get(ACTION) != null) {
            listenTime = (ListenTime) list.get(ACTION).get("" + position);
            if (listenTime != null)
                return listenTime.getListenTime(ACTION, position);
        }
        return false;
    }

    public void unreisterTime(String ACTION, int position) {
        if (list.get(ACTION) != null) {
            listenTime = (ListenTime) list.get(ACTION).get("" + position);
            if (listenTime != null) {
                listenTime.StopListen();
                list.get(ACTION).remove("" + position);
            }
        }
    }

//    public void unAllreigisterTime(){
//        for (int i = 0 ; i < list.size() ; i++){
//
//        }
//    }

    /**
     * 这个处理广播的接口
     *
     * @param STATE
     * @param position
     */
    @Override
    public void Result(String STATE, int position) {
        if (STATE.equals(ReceiverAction.USER_TIME)) {
            switch (position) {
                case SocketSwitchFragment.FLAG1:
                    TimeMoudle.setSwitch1(null);
                    break;
                case SocketSwitchFragment.FLAG2:
                    TimeMoudle.setSwitch2(null);
                    break;
                case SocketSwitchFragment.FLAG3:
                    TimeMoudle.setSwitch3(null);
                    break;
                case SocketSwitchFragment.FLAG4:
                    TimeMoudle.setSwitch4(null);
                    break;
            }
            Toast.makeText(context, "插排定时结束", Toast.LENGTH_SHORT).show();
            unreisterTime(STATE, position);
            if (Applications.getInstance().getActivityOnline(DeviceControl.activity)) {
                Applications.getInstance().removeOneActivity(DeviceControl.activity);
                CommonIntent.IntentActivity(context, DeviceControl.class, String.valueOf(ReceiverAction.USER_TIME), String.valueOf(DeviceURL.Switch));
            }
        }
    }

    /**
     * 监听失败的接口
     *
     * @param STATE
     * @param position
     */
    @Override
    public void Error(String STATE, int position) {

    }
}
