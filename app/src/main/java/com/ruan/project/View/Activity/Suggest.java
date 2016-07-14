package com.ruan.project.View.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.ruan.project.R;


/**
 * Created by Soft on 2016/7/11.
 */
public class Suggest extends BaseActivity {
    private View view = null;

    private TextView suggestContent = null;

    /**
     * Start()
     */
    @Override
    public void init() {
        view = LayoutInflater.from(context).inflate(R.layout.suggest, null);


        suggestContent = (TextView) view.findViewById(R.id.suggestContent);

        setContentColor(R.color.WhiteSmoke);
        setRightTitleVisiable(false);
        setTopColor(R.color.Blue);
        setTopTitleColor(R.color.White);
        setTitle("意见反馈");
        setLeftTitleColor(R.color.White);


        DensityUtil.setRelHeight(suggestContent , BaseActivity.height / 3);


        setContent(view);

    }
}
