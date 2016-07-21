package com.ruan.project.View.Fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.ui_sdk.View.RreshLinearLayout;
import com.example.administrator.ui_sdk.View.SideListView;
import com.example.ruan.udp_sdk.UDP;
import com.ruan.project.Controllar.CheckOnline;
import com.ruan.project.Controllar.FragmentDatabase;
import com.example.administrator.ui_sdk.ItemClick;
import com.ruan.project.Interface.DataHandler;
import com.ruan.project.Moudle.Item;
import com.ruan.project.Other.Adapter.SideListViewAdapter;
import com.ruan.project.Other.DataBase.DatabaseOpera;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.Other.HTTP.HttpURL;
import com.ruan.project.R;
import com.ruan.project.View.Activity.Device;
import com.ruan.project.View.Activity.DeviceControl;
import com.ruan.project.View.Activity.DeviceEdit;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Soft on 2016/6/23.
 */
public class Fragment1 extends Fragment implements View.OnClickListener, ItemClick, AdapterView.OnItemClickListener, ItemClick.RreshInterface , DataHandler {

    private View view = null;
    private SideListView sideListView;
    private ArrayList<Object> list = null;
    private SideListViewAdapter adapter = null;
    private Context context = null;
    private RelativeLayout base_top_relative = null;
    private TextView base_top_text1 = null;
    private TextView base_top_title = null;
    private View fragment1Top = null;
    private RreshLinearLayout myLinear = null;

    private ArrayList<Map<String, String>> map = null;

    private UDP udp = null;
    private String online = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment1, null);
        context = getActivity();

        list = new ArrayList<>();

        //初始化数据库
        FragmentDatabase.DataBaseHandler(context);


        //这里是获取用户设备表的数据，所以首先获取本地数据库的数据同时向服务器获取查询是否有更新数据，如果有更新数据则获取最新的数据
        //如果没有最新的数据则不进行任何的操作，如果本地没有数据库获取没有任何数据的话，就直接获取服务器上面的数据，之后插入本地数据库
        //获取数据库数据
        getDatabaseData();

        sideListView = (SideListView) view.findViewById(R.id.slideListView);
        fragment1Top = view.findViewById(R.id.fragment1Top);
        base_top_relative = (RelativeLayout) view.findViewById(R.id.base_top_relative);
        base_top_text1 = (TextView) view.findViewById(R.id.base_top_text1);
        base_top_title = (TextView) view.findViewById(R.id.base_top_title);
        myLinear = (RreshLinearLayout) view.findViewById(R.id.myLinear);


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
        sideListView.setRreshClick(this);
        myLinear.setRreshClick(this);


        DensityUtil.setHeight(view, BaseActivity.height);

        return view;
    }


    private Object getItem(String title, String subtitile, Drawable drawable, String rightTitle) {
        Item item = new Item();

        item.setSubtitle(subtitile);
        item.setTitle(title);
        item.setLogo(drawable);
        item.setRightTitle(rightTitle);

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
        if (position > 0)
            CommonIntent.IntentActivity(context, DeviceControl.class, map.get(0).get("deviceID"));
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
                CommonIntent.IntentActivity(context, DeviceEdit.class, map.get(position).get("deviceID"), "edit");
                break;
            //删除点击事件
            case 1:
                //删除界面的item并且同时删除本地数据的数据和服务器上面的数据
                list.remove(position);
                adapter.RefreshData(list);
                //删除本地数据库的数据
                new DatabaseOpera(context).DataDelete(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, "deviceID = ? and userID = ?", new String[]{map.get(position).get("deviceID"), "123456"});
                //删除服务器的数据库的数据
                break;
        }
    }

    private void getDatabaseData() {
        map = FragmentDatabase.getDeviceData(context);
        if (map != null)
            for (int i = 0; i < map.size(); i++) {
                if (map.get(i).get("deviceOnline").equals("1"))
                    online = "离线";
                else if (map.get(i).get("deviceOnline").equals("2"))
                    online = "在线";
                list.add(getItem(map.get(i).get("deviceName"), map.get(i).get("deviceMac"), ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), online));
            }
    }

    /**
     * 重写调用Fragment的时候启动这个方法
     */
    @Override
    public void onResume() {
        super.onResume();

        ReData();
    }

    /**
     * 更新数据
     */
    private void ReData() {
        list.clear();
        getDatabaseData();
        adapter.RefreshData(list);
    }

    @Override
    public void RreshData() {
        //通过udp单播进行设备检测是否在线
        //如果有连接wifi则使用udp判断设备是否在线
        if (HttpURL.STATE == 1)
            new CheckOnline(context , this).UDPCheck();
        //通过云端进行设备检测是否在线
        //如果wifi没有连接则使用外网判断设备是否在线
        if (HttpURL.STATE == 2)
            new CheckOnline(context , this).HTTPCheck();
    }

    /**
     * 更新数据的接口
     */
    @Override
    public void ReStartData() {
        ReData();
        sideListView.setVisiableTopView();
    }
}
