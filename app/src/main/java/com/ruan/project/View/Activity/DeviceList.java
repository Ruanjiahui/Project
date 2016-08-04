package com.ruan.project.View.Activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.ruan.project.Controllar.DeviceActivityControl;
import com.ruan.project.Moudle.Device;
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

    private DatabaseOpera databaseOpera = null;
    private Device device = null;
    private ArrayList<Object> ListObj = null;
    private DeviceActivityControl deviceActivityControl = null;

    /**
     * Start()
     */
    @Override
    public void init() {
        Bundle bundle = getIntent().getExtras();
        FLAG = bundle.getString("data");
        title = bundle.getString("flag");

        view = LayoutInflater.from(context).inflate(R.layout.devicelist, null);

        databaseOpera = new DatabaseOpera(context);
        deviceActivityControl = new DeviceActivityControl(context);

        devicelistList = (ListView) view.findViewById(R.id.devicelistList);


        setTopColor(R.color.Blue);
        setTitle(title);
        setLeftTitleColor(R.color.White);
        setTopTitleColor(R.color.White);
        setRightTitleVisiable(false);

        ListObj = databaseOpera.DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.DeviceTableName, "deviceTypeID", FLAG, Device.class , false);
        list = deviceActivityControl.setDeviceList(ListObj);

        adapter = new LGAdapter(context, list, "ListView");
        devicelistList.setAdapter(adapter);

        devicelistList.setOnItemClickListener(this);


        setContent(view);
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
        device = (Device) ListObj.get(position);
        CommonIntent.IntentActivity(context, DeviceEdit.class, device.getDeviceID(), "new");
//        CommonIntent.IntentActivity(context, AirkissNetWork.class);
//        CommonIntent.IntentActivity(context, NetWorkSetting.class);
    }
}
