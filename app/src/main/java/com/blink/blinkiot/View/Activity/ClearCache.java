package com.blink.blinkiot.View.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.data_sdk.FileUtil.FileTool;
import com.example.administrator.data_sdk.SystemUtil.SystemTool;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.ui_sdk.MyCircleLoading;
import com.example.administrator.ui_sdk.View.MyShowMemory;
import com.blink.blinkiot.Other.System.FileURL;
import com.blink.blinkiot.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/15.
 */
public class ClearCache extends BaseActivity implements MyCircleLoading.ShowMemory{

    private View clearBut = null;
    private TextView circlebut = null;
    private View view = null;
    private MyShowMemory showMemory = null;

    /**
     * Start()
     */
    @Override
    public void init() {
        view = LayoutInflater.from(context).inflate(R.layout.clearcache, null);


        setTopColor(R.color.Blue);
        setRightTitleVisiable(false);
        setTopTitleColor(R.color.White);
        setLeftTitleColor(R.color.White);
        setContentColor(R.color.WhiteSmoke);
        setTitle(getSystemText(R.string.ClearCache));

        clearBut = view.findViewById(R.id.clearBut);
        circlebut = (TextView) view.findViewById(R.id.circlebut);
        showMemory = (MyShowMemory) view.findViewById(R.id.showMemory);


        circlebut.setText(getSystemText(R.string.ClearText));

        setContent(view);


        showMemory.setTextMemory(SystemTool.getSDTotalSize(this), SystemTool.getSDAvailableSize(this));


        clearBut.setOnClickListener(this);
        DensityUtil.setRelayoutSize(clearBut, DensityUtil.dip2px(context, 55), DensityUtil.dip2px(context, 55), BaseActivity.height / 4 * 3, 0, 0, 0, new int[]{RelativeLayout.CENTER_HORIZONTAL});

    }

    /**
     * 获取系统xml文字
     *
     * @param resid
     * @return
     */
    private String getSystemText(int resid) {
        return getResources().getString(resid);
    }


    @Override
    public void Click(View v) {
        switch (v.getId()) {
            case R.id.clearBut:
                ArrayList<String> Cache = FileURL.getCacheURL(this);
                for (int i = 0; i < Cache.size(); i++)
                    FileTool.deleteFolderFile(Cache.get(i), true);

                showMemory.setTextMemory(SystemTool.getSDTotalSize(this), SystemTool.getSDAvailableSize(this));
                showMemory.StartAnimation(this);
                break;
        }
    }

    /**
     * 动画结束
     */
    @Override
    public void AnimationEnd() {
        Toast.makeText(context, getResources().getString(R.string.DeleteFile), Toast.LENGTH_SHORT).show();
    }
}
