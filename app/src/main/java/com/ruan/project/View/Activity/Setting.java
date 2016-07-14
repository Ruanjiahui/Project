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
public class Setting extends BaseActivity {
    private View view = null;
    private ListView settingList = null;


    private ArrayList<Object> list = null;

    /**
     * Start()
     */
    @Override
    public void init() {
        view = LayoutInflater.from(context).inflate(R.layout.setting , null);

        settingList = (ListView) view.findViewById(R.id.settingList);

        setTopColor(R.color.Blue);
        setRightTitleVisiable(false);
        setTopTitleColor(R.color.White);
        setLeftTitleColor(R.color.White);

        list = new ArrayList<>();

        list.add(getItem("清理缓存" ,"111k" , ImageTransformation.Resouce2Drawable(context , R.mipmap.ic_launcher)));
        list.add(getItem("检查更新" ,"111k" , ImageTransformation.Resouce2Drawable(context , R.mipmap.ic_launcher)));
        list.add(getItem("消息设置" ,"111k" , ImageTransformation.Resouce2Drawable(context , R.mipmap.ic_launcher)));
        list.add(getItem("关于我们" ,"111k" , ImageTransformation.Resouce2Drawable(context , R.mipmap.ic_launcher)));

        settingList.setAdapter(new LGAdapter(context , list , "ListView"));
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
