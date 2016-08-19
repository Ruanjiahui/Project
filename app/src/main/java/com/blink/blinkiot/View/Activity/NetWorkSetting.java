package com.blink.blinkiot.View.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.blink.blinkiot.Controllar.DeviceActivityControl;
import com.blink.blinkiot.Other.Adapter.LGAdapter;
import com.blink.blinkiot.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/2.
 */
public class NetWorkSetting extends BaseActivity implements AdapterView.OnItemClickListener {

    private View view = null;
    private ListView networksettingListView = null;

    private ArrayList<Object> list = null;
    private LGAdapter adapter = null;

    /**
     * Start()
     */
    @Override
    public void init() {

        view = LayoutInflater.from(context).inflate(R.layout.networksetting, null);

        setTopColor(R.color.Blue);
        setTopTitleColor(R.color.White);
        setLeftTitleColor(R.color.White);
        setRightTitleVisiable(false);
        setTitle("配置方式");

        networksettingListView = (ListView) view.findViewById(R.id.networksettingListView);


        list = new DeviceActivityControl(context).setNetWorkSettingList();

        adapter = new LGAdapter(context, list, "ListView");

        networksettingListView.setAdapter(adapter);

        networksettingListView.setOnItemClickListener(this);


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
        if (position == 3) {
            CommonIntent.IntentActivity(context, AirkissNetWork.class);
        }
//        if (position == 0) {
//            CommonIntent.IntentActivity(context, DeviceEdit.class);
//        }
    }
}
