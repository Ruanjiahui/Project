package com.blink.blinkiot.View.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.data_sdk.SystemUtil.SystemTool;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.blink.blinkiot.R;

/**
 * Created by Administrator on 2016/8/16.
 */
public class AboutMe extends BaseActivity {

    private View view = null;

    private TextView aboutmeCode = null;

    /**
     * Start()
     */
    @Override
    public void init() {


        setTopColor(R.color.Blue);
        setRightTitleVisiable(false);
        setTopTitleColor(R.color.White);
        setLeftTitleColor(R.color.White);
        setContentColor(R.color.WhiteSmoke);
        setTitle(getResources().getString(R.string.AboutMe));

        view = LayoutInflater.from(context).inflate(R.layout.aboutme, null);

        aboutmeCode = (TextView) view.findViewById(R.id.aboutmeCode);

        aboutmeCode.setText(SystemTool.getVersionName(context));


        setContent(view);
    }
}
