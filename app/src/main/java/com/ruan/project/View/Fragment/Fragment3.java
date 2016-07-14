package com.ruan.project.View.Fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.ui_sdk.DensityUtil;
import com.ruan.project.Moudle.Item;
import com.ruan.project.Other.Adapter.LGAdapter;
import com.ruan.project.R;

import java.util.ArrayList;


/**
 * Created by Soft on 2016/6/23.
 */
public class Fragment3 extends Fragment {

    private Context context = null;

    private View view = null;
    private View fragment3Top = null;
    private ListView fragment3List = null;
    private ArrayList<Object> list = null;
    private RelativeLayout base_top_relative = null;
    private TextView base_top_text1 = null;
    private TextView base_top_title = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment3, container, false);

        context = getActivity();

        fragment3List = (ListView) view.findViewById(R.id.fragment3List);
        fragment3Top = view.findViewById(R.id.fragment3Top);
        base_top_relative = (RelativeLayout) view.findViewById(R.id.base_top_relative);
        base_top_text1 = (TextView) view.findViewById(R.id.base_top_text1);
        base_top_title = (TextView) view.findViewById(R.id.base_top_title);


        list = new ArrayList<>();
        list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), "家庭成员", ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), DensityUtil.dip2px(context, 60)));
        list.add(getItem(null, "-", null, DensityUtil.dip2px(context, 30)));
        list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), "帮助与支持", ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), DensityUtil.dip2px(context, 60)));
        list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), "意见反馈", ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), DensityUtil.dip2px(context, 60)));
        list.add(getItem(null, "-", null, DensityUtil.dip2px(context, 30)));
        list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), "系统设置", ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), DensityUtil.dip2px(context, 60)));


        fragment3Top.setBackgroundColor(context.getResources().getColor(R.color.Blue));
        base_top_title.setPadding(0 , DensityUtil.dip2px(context , 20) , 0 , 0);
        base_top_title.setText("商城");
        fragment3List.setAdapter(new LGAdapter(context, list, "ListView"));


        base_top_relative.setVisibility(View.GONE);
        base_top_text1.setVisibility(View.GONE);
        base_top_title.setTextColor(context.getResources().getColor(R.color.White));

        return view;
    }


    private Object getItem(Drawable drawable, String text, Drawable right, int height) {
        Item item = new Item();

        item.setListImage(drawable);
        item.setListright(right);
        item.setListText(text);
        item.setHeight(height);

        return item;
    }
}
