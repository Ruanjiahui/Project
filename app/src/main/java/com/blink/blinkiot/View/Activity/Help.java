package com.blink.blinkiot.View.Activity;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;


import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.blink.blinkiot.Moudle.Item;
import com.blink.blinkiot.Other.Adapter.LGAdapter;
import com.blink.blinkiot.R;

import java.util.ArrayList;

/**
 * Created by Soft on 2016/7/11.
 */
public class Help extends BaseActivity {
    private View view = null;

    private ListView helpList = null;
    private ArrayList<Object> list = null;

    /**
     * Start()
     */
    @Override
    public void init() {
        view = LayoutInflater.from(context).inflate(R.layout.help, null);

        helpList = (ListView) view.findViewById(R.id.helpList);

        setTopColor(R.color.Blue);
        setRightTitleVisiable(false);
        setTopTitleColor(R.color.White);
        setLeftTitleColor(R.color.White);
        setTitle(getResources().getString(R.string.HelpTitle));


        list = new ArrayList<>();

        list.add(getItem(getResources().getString(R.string.UseHelp), null, ImageTransformation.Resouce2Drawable(context, R.mipmap.right)));
        list.add(getItem(getResources().getString(R.string.OnlineService), null, ImageTransformation.Resouce2Drawable(context, R.mipmap.right)));
        list.add(getItem(getResources().getString(R.string.TeamView), null, ImageTransformation.Resouce2Drawable(context, R.mipmap.right)));

        helpList.setAdapter(new LGAdapter(context, list, "ListView"));


        setContent(view);
    }

    private Object getItem(String title, String subtitile, Drawable drawable) {
        Item item = new Item();

        item.setListSubText(subtitile);
        item.setListText(title);
        item.setListright(drawable);
        item.setHeight(DensityUtil.dip2px(context, 50));

        return item;
    }
}
