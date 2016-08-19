package com.blink.blinkiot.Other.System;

import android.content.Context;
import android.widget.Toast;

import com.blink.blinkiot.Moudle.TimeMoudle;
import com.blink.blinkiot.View.Control.SocketSwitchFragment;

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
    private TimeHandler timeHandler = null;

    public RegisterTime(Context context) {
        this.context = context;
        list = new HashMap<>();
        map = new HashMap<>();
    }

    public void registerTime(String[] time, String Action, int FLAG, TimeHandler timeHandler) {
        this.timeHandler = timeHandler;
        listenTime = new ListenTime(context, this, Action, FLAG);
        map.put("" + FLAG, listenTime);
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


    /**
     * 这个处理广播的接口
     *
     * @param ACTION
     * @param FLAG
     */
    @Override
    public void Result(String ACTION, int FLAG) {
        if (ACTION.equals(ReceiverAction.USER_TIME)) {
            switch (FLAG) {
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
            //定时结束意味着发送指令给设备
            timeHandler.Start(FLAG);
            unreisterTime(ACTION, FLAG);
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
