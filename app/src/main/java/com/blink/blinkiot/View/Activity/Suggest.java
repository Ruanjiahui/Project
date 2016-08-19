package com.blink.blinkiot.View.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.blink.blinkiot.R;


/**
 * Created by Soft on 2016/7/11.
 */
public class Suggest extends BaseActivity {
    private View view = null;

    private TextView suggestContent = null;
    private TextView circlebut = null;
    private View suggestSend = null;


    /**
     * Start()
     */
    @Override
    public void init() {
        view = LayoutInflater.from(context).inflate(R.layout.suggest, null);


        suggestContent = (TextView) view.findViewById(R.id.suggestContent);
        circlebut = (TextView) view.findViewById(R.id.circlebut);
        suggestSend = view.findViewById(R.id.suggestSend);

        setContentColor(R.color.WhiteSmoke);
        setRightTitleVisiable(false);
        setTopColor(R.color.Blue);
        setTopTitleColor(R.color.White);
        setTitle(getResources().getString(R.string.SuggestTitle));
        setLeftTitleColor(R.color.White);


        DensityUtil.setRelHeight(suggestContent, BaseActivity.height / 3);

        setContent(view);

        circlebut.setText(getResources().getString(R.string.SendText));
        suggestSend.setOnClickListener(this);
        DensityUtil.setRelayoutSize(suggestSend, DensityUtil.dip2px(context, 55), DensityUtil.dip2px(context, 55), BaseActivity.height / 4 * 3, 0, 0, 0, new int[]{RelativeLayout.CENTER_HORIZONTAL});


    }


    @Override
    public void Click(View v) {
        switch (v.getId()) {
            case R.id.suggestSend:
                //反馈的接口
                break;
        }
    }
}
