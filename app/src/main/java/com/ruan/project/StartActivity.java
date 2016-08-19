package com.ruan.project;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.ruan.project.Controllar.FragmentControl;
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
