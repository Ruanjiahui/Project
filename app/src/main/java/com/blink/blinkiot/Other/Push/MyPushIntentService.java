package com.blink.blinkiot.Other.Push;


//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
//import com.blink.blinkiot.Moudle.PushMessage;
//import com.blink.blinkiot.View.MyNotification;
//import com.example.administrator.data_sdk.JSON.JSONClass;
//import com.example.administrator.data_sdk.JSON.JSONResouce;
//import com.umeng.message.UmengIntentService;
//
//import org.android.agoo.client.BaseConstants;
//
//public class MyPushIntentService extends UmengIntentService {
//
//    private static int position = 0;
//    private String MESSAGE = "notification";
//
//    @Override
//    protected void onMessage(Context context, Intent intent) {
//        super.onMessage(context, intent);
//
//        //获取推送过来的信息
//        String msg = intent.getStringExtra(BaseConstants.MESSAGE_BODY);
//
//        PushMessage pushMessage = (PushMessage) new JSONClass().setJSONToClassDeclared(context, PushMessage.class, msg);
//        if (pushMessage == null || MESSAGE.equals(pushMessage.getDisplay_type()))
//            return;
//
//        new MyNotification(context, position);
//        position++;
//    }
//}