package com.blink.blinkiot.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyCircleLoading;
import com.example.administrator.ui_sdk.MyWebClient;
import com.example.administrator.ui_sdk.View.CircleWaiting;
import com.example.administrator.ui_sdk.View.MyWebView;
import com.blink.blinkiot.Other.HTTP.HttpURL;
import com.blink.blinkiot.R;


/**
 * Created by Soft on 2016/6/23.
 */
public class Fragment3 extends Fragment implements MyWebClient, MyCircleLoading.Waiting {

    private Context context = null;
    public static boolean isFragment3 = false;

    private View view = null;
    private View fragment3Top = null;
    private RelativeLayout base_top_relative = null;
    private TextView base_top_text1 = null;
    private TextView base_top_title = null;

    private MyWebView fragment3WebView = null;

    private CircleWaiting fragment3Wait = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment3, container, false);

        context = getActivity();
        //证明当前页面处于fragment3页面
        isFragment3 = true;

        fragment3Top = view.findViewById(R.id.fragment3Top);
        base_top_relative = (RelativeLayout) view.findViewById(R.id.base_top_relative);
        base_top_text1 = (TextView) view.findViewById(R.id.base_top_text1);
        base_top_title = (TextView) view.findViewById(R.id.base_top_title);
        fragment3WebView = (MyWebView) view.findViewById(R.id.fragment3WebView);
        fragment3Wait = (CircleWaiting) view.findViewById(R.id.fragment3Wait);


        fragment3Top.setBackgroundColor(context.getResources().getColor(R.color.Blue));
        base_top_title.setPadding(0, DensityUtil.dip2px(context, 20), 0, 0);
        base_top_title.setText(getResources().getString(R.string.Nav3));


        base_top_relative.setVisibility(View.GONE);
        base_top_text1.setVisibility(View.GONE);
        base_top_title.setTextColor(context.getResources().getColor(R.color.White));

        fragment3Wait.setVisibility(View.VISIBLE);
        fragment3WebView.setWebView(HttpURL.ShopURL, this);
        fragment3Wait.setAnimationState(this);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isFragment3 = false;
    }

    /**
     * 正在加载
     */
    @Override
    public void Loading() {
        fragment3Wait.setStart();
    }

    /**
     * 加载结束
     */
    @Override
    public void Ending() {
        fragment3Wait.setStop(CircleWaiting.END);
    }

    /**
     * 当前动画是否结束的接口
     *
     * @param state true已经结束false还在进行中
     */
    @Override
    public void AnimationState(boolean state) {
        if (state) {
            fragment3Wait.setVisibility(View.GONE);
        }
    }
}
