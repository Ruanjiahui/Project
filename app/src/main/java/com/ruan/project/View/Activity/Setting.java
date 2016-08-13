package com.ruan.project.View.Activity;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;


import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.loc.bo;
import com.ruan.project.Controllar.FragmentControl;
import com.ruan.project.Moudle.Item;
import com.ruan.project.Other.Adapter.LGAdapter;
import com.ruan.project.R;

import java.util.ArrayList;

/**
 * Created by Soft on 2016/7/11.
 */
public class Setting extends BaseActivity implements AdapterView.OnItemClickListener {
    private View view = null;
    private ListView settingList = null;


    private ArrayList<Object> list = null;
    private RelativeLayout settingRel = null;

    //是否允许点击
    private boolean isClick = true;



    /**
     * Start()
     */
    @Override
    public void init() {
        view = LayoutInflater.from(context).inflate(R.layout.setting, null);

        settingList = (ListView) view.findViewById(R.id.settingList);


        settingRel = (RelativeLayout) view.findViewById(R.id.settingRel);

        setTopColor(R.color.Blue);
        setRightTitleVisiable(false);
        setTopTitleColor(R.color.White);
        setLeftTitleColor(R.color.White);
        setContentColor(R.color.WhiteSmoke);
        setTitle("设置");

        list = new ArrayList<>();

        list.add(getItem("清理缓存", "111k", ImageTransformation.Resouce2Drawable(context, R.mipmap.right)));
        list.add(getItem("检查更新", "最新版本", ImageTransformation.Resouce2Drawable(context, R.mipmap.right)));
        list.add(getItem("消息设置", null, ImageTransformation.Resouce2Drawable(context, R.mipmap.right)));
        list.add(getItem("关于我们", null, ImageTransformation.Resouce2Drawable(context, R.mipmap.right)));

        settingList.setAdapter(new LGAdapter(context, list, "ListView"));
        setContent(view);


        settingList.setOnItemClickListener(this);
    }


    private Object getItem(String title, String subtitile, Drawable drawable) {
        Item item = new Item();

        item.setListSubText(subtitile);
        item.setListText(title);
        item.setListright(drawable);
        item.setHeight(DensityUtil.dip2px(context, 50));

        return item;
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p/>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (isClick) {
            switch (position) {
                case 0:
                    break;
                case 1:
                    isClick = false;
                    settingRel.setVisibility(View.VISIBLE);
                    //进行检查更新

                    break;
            }
        }
    }
}
