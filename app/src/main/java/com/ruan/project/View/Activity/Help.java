package com.ruan.project.View.Activity;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;


import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.ruan.project.Moudle.Item;
import com.ruan.project.Other.Adapter.LGAdapter;
import com.ruan.project.R;

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
        view = LayoutInflater.from(context).inflate(R.layout.help , null);

        helpList = (ListView) view.findViewById(R.id.helpList);

        setTopColor(R.color.Blue);
        setRightTitleVisiable(false);
        setTopTitleColor(R.color.White);
        setLeftTitleColor(R.color.White);


        list = new ArrayList<>();

        list.add(getItem("使用帮助" ,"" , ImageTransformation.Resouce2Drawable(context , R.mipmap.ic_launcher)));
        list.add(getItem("在线客服" ,"" , ImageTransformation.Resouce2Drawable(context , R.mipmap.ic_launcher)));
        list.add(getItem("远程协助" ,"111k" , ImageTransformation.Resouce2Drawable(context , R.mipmap.ic_launcher)));

        helpList.setAdapter(new LGAdapter(context , list , "ListView"));


        setContent(view);
    }

    private Object getItem(String title, String subtitile, Drawable drawable) {
        Item item = new Item();

        item.setListSubText(subtitile);
        item.setListText(title);
        item.setListright(drawable);

        return item;
    }
}
