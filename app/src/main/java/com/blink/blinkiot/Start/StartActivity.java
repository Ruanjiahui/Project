package com.blink.blinkiot.Start;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.FileUtil.FileTool;
import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.data_sdk.SystemUtil.SystemTool;
import com.example.administrator.ui_sdk.Applications;
import com.blink.blinkiot.Controllar.FragmentControl;
import com.blink.blinkiot.R;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2016/8/18.
 */
public class StartActivity extends Activity {

    private LinearLayout StartPic = null;
    private Bitmap bitmapDrawable = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.start_main);
        Applications.getInstance().addActivity(this);


        StartPic = (LinearLayout) findViewById(R.id.StartPic);
        bitmapDrawable = ImageTransformation.Resouce2Bitmap(this, R.mipmap.start);
        StartPic.setBackground(ImageTransformation.Bitmap2Drawable(bitmapDrawable));

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        new Thread(new MyRunnable()).start();
    }

    private class MyRunnable implements Runnable {

        /**
         * Starts executing the active part of the class' code. This method is
         * called when a thread is started that has been created with a class which
         * implements {@code Runnable}.
         */
        private long start = 0;

        @Override
        public void run() {
            start = System.currentTimeMillis();
            //这个进行数据初始化的操作
            //初始化数据库
            FragmentControl.DataBaseHandler(StartActivity.this);
            while (System.currentTimeMillis() - start < 3000) {
                // 里面进行耗时操作
            }
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);
        }
    }


    private Handler handler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            if (FileTool.isFirstRunApplication(StartActivity.this))
                CommonIntent.IntentActivity(StartActivity.this, LeadActivity.class);
            else
                CommonIntent.IntentActivity(StartActivity.this, MainActivity.class);
            Applications.getInstance().removeOneActivity(StartActivity.this);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        MobclickAgent.onPause(this);
    }

}
