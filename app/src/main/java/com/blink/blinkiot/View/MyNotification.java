package com.blink.blinkiot.View;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import com.blink.blinkiot.R;
import com.blink.blinkiot.Start.MainActivity;
import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;

/**
 * Created by Administrator on 2016/8/24.
 */
public class MyNotification {

    private Context context = null;
    private RemoteViews remoteViews = null;
    private int icon = 0;
    private String ticket = null;
    private String contentTxt = null;
    private String ContentTitle = null;
    private int msg_id = 0;

    public MyNotification(Context context, int msg_id) {
        this.context = context;
        this.msg_id = msg_id;
        init();
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setTickerText(String ticket) {
        this.ticket = ticket;
    }

    public void setContentText(String contentTxt) {
        this.contentTxt = contentTxt;
    }

    public void setContentTitle(String ContentTitle) {
        this.ContentTitle = ContentTitle;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void init() {
        // 在Android进行通知处理，首先需要重系统哪里获得通知管理器NotificationManager，它是一个系统Service。
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);
        // 通过Notification.Builder来创建通知，注意API Level
        // API11之后才支持
        Notification notify2 = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示，如果在那里需要更换更大的图片，可以使用setLargeIcon(Bitmap
                .setLargeIcon(ImageTransformation.Resouce2Bitmap(context, R.mipmap.ic_launcher))
                // icon)
                .setTicker(context.getResources().getString(R.string.app_name) + "-" + context.getResources().getString(R.string.NotifiTitle))// 设置在status
                // bar上显示的提示文字
                .setContentTitle(context.getResources().getString(R.string.NotifiMessTitle))// 设置在下拉status
                // bar后Activity，本例子中的NotififyMessage的TextView中显示的标题
                .setContentText(context.getResources().getString(R.string.NotifiMessContent))// TextView中显示的详细内容
                .setContentIntent(pendingIntent2) // 关联PendingIntent
//                .setNumber(1) // 在TextView的右方显示的数字，可放大图片看，在最右侧。这个number同时也起到一个序列号的左右，如果多个触发多个通知（同一ID），可以指定显示哪一个。
                .getNotification(); // 需要注意build()是在API level
        // 16及之后增加的，在API11中可以使用getNotificatin()来代替
        notify2.flags |= Notification.FLAG_AUTO_CANCEL;
        //设置手机震动
        //第一个，0表示手机静止的时长，第二个，0表示手机震动的时长
        //第三个，1000表示手机震动的时长，第四个，1000表示手机震动的时长
        //此处表示手机先震动1秒，然后静止1秒
//        long[] vibrates = {0, 0, 1000, 1000};
//        notify2.vibrate = vibrates;
        //设置手机的灯光
//        notify2.flags = Notification.FLAG_SHOW_LIGHTS;
        //如果不想进行那么多繁杂的这只，可以直接使用通知的默认效果
        //默认设置了声音，震动和灯光
        notify2.defaults = Notification.DEFAULT_ALL;


        manager.notify(msg_id, notify2);

        // 清除id为NOTIFICATION_FLAG的通知
//        manager.cancel(NOTIFICATION_FLAG);
    }

    public void show() {

    }
}
