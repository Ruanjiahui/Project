package com.ruan.project.View.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.ruan.project.Moudle.Item;
import com.ruan.project.Other.Adapter.LGAdapter;
import com.ruan.project.Other.DataBase.CreateDataBase;
import com.ruan.project.Other.DataBase.DatabaseOpera;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Soft on 2016/7/11.
 */
public class DeviceList extends BaseActivity implements AdapterView.OnItemClickListener {

    private View view = null;
    private ListView devicelistList = null;
    private ArrayList<Object> list = null;
    private LGAdapter adapter = null;

    private String title = "";
    private String FLAG = "";
    private ArrayList<Map<String, String>> map = null;

    /**
     * Start()
     */
    @Override
    public void init() {
        Bundle bundle = getIntent().getExtras();
        FLAG = bundle.getString("data");
        title = bundle.getString("flag");

        view = LayoutInflater.from(context).inflate(R.layout.devicelist, null);


        devicelistList = (ListView) view.findViewById(R.id.devicelistList);


        setTopColor(R.color.Blue);
        setTitle(title);
        setLeftTitleColor(R.color.White);
        setTopTitleColor(R.color.White);
        setRightTitleVisiable(false);


        list = new ArrayList<>();

        //一进来马上判断有没有设备数据库和设备表，如果没有则自动会创建数据库
        //第二个参数是数据库名称,第三个参数是数据库表的名称

        //（1）判断数据库是否存在不存在则创建数据库的设备表并且从服务器上面获取设备的数据
        //（2）数据库的表存在但是表里面没有数据  则是首先判断表里面有没有数据则从服务器上面获取数据
        //（3）本地数据库数据库里面有数据，则马上显示同时访问服务器是否有更新，有更新则将新的数据库获
        //     取到本地更新本地数据库，更新显示界面
        //如果返回false则说明没有该表，返回true则存在该表
        if (!new CreateDataBase().FirstDataBase(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.DeviceTableName)) {
            new DatabaseOpera(context).DataInert(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.DeviceTableName, "");
        } else {
            map = new DatabaseOpera(context).DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.DeviceTableName, "deviceTypeID", FLAG);
            //没有该表则自动创建表，并且从服务器上面获取设备的数据
            //从服务器上面获取的数据首先要存本地数据库，之后才显示到界面
            for (int i = 0; i < map.size(); i++) {
                list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), map.get(i).get("deviceType"), map.get(i).get("deviceModel"), ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), DensityUtil.dip2px(context, 60)));
            }
        }

        adapter = new LGAdapter(context, list, "ListView");
        devicelistList.setAdapter(adapter);

        devicelistList.setOnItemClickListener(this);


        setContent(view);
    }

    private Object getItem(Drawable drawable, String text, String subtitle, Drawable right, int height) {
        Item item = new Item();

        item.setListImage(drawable);
        item.setListright(right);
        item.setListText(text);
        item.setHeight(height);
        item.setListSubText(subtitle);

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
        CommonIntent.IntentActivity(context, Edit.class, map.get(position).get("deviceID") , DatabaseTableName.DeviceTableName);
    }
}
