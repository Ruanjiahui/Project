package com.blink.blinkiot.View.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;


import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.blink.blinkiot.Controllar.DeviceActivityControl;
import com.blink.blinkiot.Interface.UDPInterface;
import com.blink.blinkiot.Moudle.Device;
import com.blink.blinkiot.Other.Adapter.LGAdapter;
import com.blink.blinkiot.Other.DataBase.DatabaseOpera;
import com.blink.blinkiot.Other.DatabaseTableName;
import com.blink.blinkiot.Other.UDP.ScanDevice;
import com.example.ruan.udp_sdk.UDPConfig;
import com.blink.blinkiot.R;

import java.util.ArrayList;

/**
 * Created by Soft on 2016/7/11.
 */
public class DeviceList extends BaseActivity implements AdapterView.OnItemClickListener, UDPInterface.HandlerMac {

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

    private RelativeLayout deviceListBack = null;

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
        deviceListBack = (RelativeLayout) view.findViewById(R.id.deviceListBack);


        setTopColor(R.color.Blue);
        setTitle(title);
        setLeftTitleColor(R.color.White);
        setTopTitleColor(R.color.White);
        setRightTitleVisiable(false);

        ListObj = databaseOpera.DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.DeviceTableName, "deviceTypeID", FLAG, Device.class, false);
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
        deviceListBack.setVisibility(View.VISIBLE);
        device = (Device) ListObj.get(position);
        //这里广播是广播设备的型号
        //计时器，广播没一秒发送一次，总共发送5次
        new ScanDevice().Scanner(UDPConfig.PORT, device.getDeviceID(), this, UDPConfig.count);
    }

    /**
     * 这个方法获取Mac值
     * //0 储存接收的数据
     * //1 储存接收数据的长度
     * //2 储存接收的地址
     * //3 储存接收的端口
     *
     * @param position 标示
     * @param objects  这个Object数组里面包含一些列的设备信息
     */
    @Override
    public void getMac(int position, Object[] objects) {
        deviceListBack.setVisibility(View.GONE);
        if (isEmtry(objects))
            CommonIntent.IntentActivity(context, ConfigList.class, device.getDeviceID());
        else
            CommonIntent.IntentActivity(context, AirkissNetWork.class, device.getDeviceID());
    }

    private boolean isEmtry(Object[] objects) {
        if (new String((byte[]) objects[0], 0, (int) objects[1]) != null)
            return true;
        return false;
    }

    /**
     * 超时
     *
     * @param position
     * @param error
     */
    @Override
    public void Error(int position, int error) {
        deviceListBack.setVisibility(View.GONE);
        CommonIntent.IntentActivity(context, AirkissNetWork.class, device.getDeviceID());
    }
}
