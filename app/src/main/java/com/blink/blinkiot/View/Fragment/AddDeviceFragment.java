package com.blink.blinkiot.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.data_sdk.CommonIntent;
import com.blink.blinkiot.Controllar.DeviceActivityControl;
import com.blink.blinkiot.Moudle.Device;
import com.blink.blinkiot.Other.Adapter.LGAdapter;
import com.blink.blinkiot.Other.DataBase.DatabaseOpera;
import com.blink.blinkiot.Other.DatabaseTableName;
import com.blink.blinkiot.R;
import com.blink.blinkiot.View.Activity.DeviceList;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/2.
 */
public class AddDeviceFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View view = null;
    private Context context = null;


    private ListView productList = null;
    private ArrayList<Object> list = null;
    private LGAdapter adapter = null;
    private DatabaseOpera databaseOpera = null;

    private ArrayList<Object> ListObj = null;
    private Device device = null;
    private DeviceActivityControl deviceActivityControl = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.adddevice, container, false);
        context = getActivity();

        productList = (ListView) view.findViewById(R.id.productList);
        //实例化链表
        databaseOpera = new DatabaseOpera(context);
        deviceActivityControl = new DeviceActivityControl(context);

        //获取列表数据
        ListObj = databaseOpera.DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.DeviceTableName, Device.class, new String[]{"deviceTypeID"}, "deviceType" , false);
        list = deviceActivityControl.setDeviceTypeList(ListObj);

        adapter = new LGAdapter(context, list, "ListView");
        productList.setAdapter(adapter);

        productList.setOnItemClickListener(this);

        return view;
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
        CommonIntent.IntentActivity(context, DeviceList.class, device.getDeviceTypeID(), device.getDeviceType());
    }
}
