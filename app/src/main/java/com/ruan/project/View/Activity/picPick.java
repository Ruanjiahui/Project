package com.ruan.project.View.Activity;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.example.administrator.data_sdk.Database.LoadResouce;
import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.data_sdk.SystemUtil.ImageMoudle;
import com.example.administrator.data_sdk.SystemUtil.SystemTool;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.ruan.project.Moudle.Item;
import com.ruan.project.Other.Adapter.LGAdapter;
import com.ruan.project.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/4.
 */
public class picPick extends BaseActivity {

    private View view = null;
    private GridView picPickGridView = null;
    private ArrayList<Object> ListObj = null;
    private ImageMoudle imageMoudle = null;
    private ArrayList<Object> list = null;
    private LGAdapter adapter = null;

    /**
     * Start()
     */
    @Override
    public void init() {
        view = LayoutInflater.from(context).inflate(R.layout.picpick, null);

        picPickGridView = (GridView) view.findViewById(R.id.picPickGridView);


        setContentColor(R.color.Black);
        setTopColor(R.color.Blue);
        setTopTitleColor(R.color.White);
        setTitle("图片选择");
        setRightTitleVisiable(false);
        setLeftTitleColor(R.color.White);

        list = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher)));

        adapter = new LGAdapter(context, list, "GridView");
        picPickGridView.setAdapter(adapter);

//        new Thread(new MyRunnable()).start();

        setContent(view);
    }


    private class MyRunnable implements Runnable {

        /**
         * Starts executing the active part of the class' code. This method is
         * called when a thread is started that has been created with a class which
         * implements {@code Runnable}.
         */
        @Override
        public void run() {
            Message message = new Message();
            message.obj = new LoadResouce().MapToObjects(SystemTool.LocalImage(context), picPick.class);
            handler.sendMessage(message);
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            ListObj = (ArrayList<Object>) msg.obj;
            list = setImageList(ListObj);
            adapter.RefreshData(list);
            return false;
        }
    });


    private ArrayList<Object> setImageList(ArrayList<Object> ListObj) {
        list = new ArrayList<>();
        for (int i = 0; i < ListObj.size(); i++) {
            imageMoudle = (ImageMoudle) ListObj.get(i);
            list.add(getItem(ImageTransformation.URL2Drawable(context, imageMoudle.getData())));
        }
        return list;
    }


    private Object getItem(Drawable drawable) {
        Item item = new Item();
        item.setGridCenterImage(drawable);
        return item;
    }
}
