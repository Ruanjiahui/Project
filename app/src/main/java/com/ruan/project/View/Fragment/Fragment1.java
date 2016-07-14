package com.ruan.project.View.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.View.SideListView;
import com.ruan.project.Interface.ItemClick;
import com.ruan.project.Moudle.Item;
import com.ruan.project.Moudle.User;
import com.ruan.project.Other.Adapter.SideListViewAdapter;
import com.ruan.project.Other.DataBase.CreateDataBase;
import com.ruan.project.Other.DataBase.DataHandler;
import com.ruan.project.Other.DataBase.DatabaseOpera;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.R;
import com.ruan.project.View.Activity.Device;
import com.ruan.project.View.Activity.Edit;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Soft on 2016/6/23.
 */
public class Fragment1 extends Fragment implements View.OnClickListener, ItemClick, AdapterView.OnItemClickListener {

    private View view = null;
    private SideListView sideListView;
    private ArrayList<Object> list = null;
    private SideListViewAdapter adapter = null;
    private Context context = null;
    private RelativeLayout base_top_relative = null;
    private TextView base_top_text1 = null;
    private TextView base_top_title = null;
    private View fragment1Top = null;

    private ArrayList<Map<String, String>> map = null;
//    private RelativeLayout fragment1Top = null;


    /**
     * fragment最开始运行的地方  相当于Activity oncreate
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        list = new ArrayList<>();

        //判断是否存在用户表   如果没有存在则自动创建用户表
        new CreateDataBase().FirstDataBase(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName);
        new DatabaseOpera(context).DataInert(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName, DataHandler.getContentValues("userID", "123456"), true, "userID = ?", new String[]{"123456"} , "userID = ?", new String[]{"123456"});
        User.toModel(new DatabaseOpera(context).DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName  , "" , new String[]{}));


        //这里是获取用户设备表的数据，所以首先获取本地数据库的数据同时向服务器获取查询是否有更新数据，如果有更新数据则获取最新的数据
        //如果没有最新的数据则不进行任何的操作，如果本地没有数据库获取没有任何数据的话，就直接获取服务器上面的数据，之后插入本地数据库

        //首先判断本地有没有数据库，没有则直接获取服务器的数据添加到本地数据库并且直接创建数据库
        if (new CreateDataBase().FirstDataBase(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName)) {
            //本地有数据库，
            //则获取本地数据库的数据，同时访问数据库的数据库将最新的数据库获取写到本地数据库进行更新
            map = new DatabaseOpera(context).DataQuery(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName);
            for (int i = 0; i < map.size(); i++) {
                list.add(getItem(map.get(i).get("deviceName"), map.get(i).get("deviceRemarks"), ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher)));
            }

        } else {
            //没有数据库这个时候已经创建完毕数据库
            //接下来就是从服务器上面获取数据
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment1, null);
        context = getActivity();

        sideListView = (SideListView) view.findViewById(R.id.slideListView);
        fragment1Top = view.findViewById(R.id.fragment1Top);
        base_top_relative = (RelativeLayout) view.findViewById(R.id.base_top_relative);
        base_top_text1 = (TextView) view.findViewById(R.id.base_top_text1);
        base_top_title = (TextView) view.findViewById(R.id.base_top_title);


        adapter = new SideListViewAdapter(context, list);
        fragment1Top.setBackgroundColor(context.getResources().getColor(R.color.Blue));
        base_top_title.setPadding(0, DensityUtil.dip2px(context, 20), 0, 0);
        base_top_text1.setPadding(0, DensityUtil.dip2px(context, 20), 0, 0);
        base_top_text1.setTextColor(context.getResources().getColor(R.color.White));
        base_top_text1.setText("添加设备");
        base_top_title.setText("首页");
        base_top_relative.setVisibility(View.GONE);
        base_top_title.setTextColor(context.getResources().getColor(R.color.White));
        sideListView.setAdapter(adapter);


        base_top_text1.setOnClickListener(this);
        adapter.setItemClick(this);
        sideListView.setOnItemClickListener(this);

        return view;
    }


    private Object getItem(String title, String subtitile, Drawable drawable) {
        Item item = new Item();

        item.setSubtitle(subtitile);
        item.setTitle(title);
        item.setLogo(drawable);

        return item;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_top_text1:
                CommonIntent.IntentActivity(context, Device.class);
                break;
        }
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
    }

    /**
     * item的子控件点击事件
     *
     * @param position 子控件在列表中属于第几个
     * @param View     子控件的序号靠最左边为第0个
     */
    @Override
    public void OnClick(int position, int View) {
        sideListView.ShowNormal();
        switch (View) {
            //编辑点击事件
            case 0:
                CommonIntent.IntentActivity(context, Edit.class, map.get(position).get("deviceID") , DatabaseTableName.UserDeviceName);
                break;
            //删除点击事件
            case 1:
                //删除界面的item并且同时删除本地数据的数据和服务器上面的数据
                list.remove(position);
                adapter.RefreshData(list);
                //删除本地数据库的数据
                new DatabaseOpera(context).DataDelete(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, "deviceID = ? and userID = ?", new String[]{map.get(position).get("deviceID") , "123456"});
                //删除服务器的数据库的数据
                break;
        }
    }

    /**
     * 重写调用Fragment的时候启动这个方法
     */
    @Override
    public void onResume() {
        super.onResume();

        list.clear();
        //更新界面的数据
        map = new DatabaseOpera(context).DataQuery(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName);
        for (int i = 0; i < map.size(); i++) {
            list.add(getItem(map.get(i).get("deviceName"), map.get(i).get("deviceRemarks"), ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher)));
        }
        adapter.RefreshData(list);
    }
}
