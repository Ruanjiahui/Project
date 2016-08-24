package com.blink.blinkiot.View.Activity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.blink.blinkiot.Interface.UDPInterface;
import com.blink.blinkiot.Moudle.Item;
import com.blink.blinkiot.Moudle.UserDevice;
import com.blink.blinkiot.Other.Adapter.LGAdapter;
import com.blink.blinkiot.Other.DataBase.DatabaseOpera;
import com.blink.blinkiot.Other.DatabaseTableName;
import com.blink.blinkiot.Other.UDP.ScanDevice;
import com.blink.blinkiot.Other.UDP.UDPConfig;
import com.blink.blinkiot.R;
import com.example.administrator.ui_sdk.MyCircleLoading;
import com.example.administrator.ui_sdk.View.CircleLoading;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/12.
 */
public class ConfigList extends BaseActivity implements UDPInterface.HandlerMac, AdapterView.OnItemClickListener, MyCircleLoading {

    private ListView configList = null;
    private View view = null;
    private LGAdapter adapter = null;
    private ArrayList<Object> list = null;
    public static Context context = null;

    private ArrayList<Object> ListObj = null;
    private UserDevice userDevice = null;
    private CircleLoading configNet = null;

    //这个获取扫描的对象数据
    private String data = null;

    /**
     * Start()
     */
    @Override
    public void init() {
        context = this;
        data = getIntent().getExtras().getString("data");

        setTitle(getResources().getString(R.string.SearchTitle));
        setTopColor(R.color.Blue);
        setTopTitleColor(R.color.White);
        setLeftTitleColor(R.color.White);
        setRightTitleVisiable(false);

        view = LayoutInflater.from(context).inflate(R.layout.configlist, null);

        configList = (ListView) view.findViewById(R.id.configList);
        configNet = (CircleLoading) view.findViewById(R.id.configNet);


        configList.setOnItemClickListener(this);
        configNet.setTextCircle("AirKiss");
        configNet.setClick(this);

        setContent(view);

    }

    @Override
    protected void onResume() {
        super.onResume();

        ListObj = null;
        ListObj = new DatabaseOpera(context).DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, null, "", null, "", "", "", "", UserDevice.class, true);

        list = new ArrayList<>();
        //这个广播是广播Blink所有的设备
        //计时器，广播没一秒发送一次，总共发送5次
        new ScanDevice().Scanner(UDPConfig.PORT, data, this, UDPConfig.count);
    }


    private Object getItem(String mac) {
        Item item = new Item();

        item.setListImage(ImageTransformation.Resouce2Drawable(context, R.mipmap.cooker));
        item.setListText(mac);

        return item;
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
        isMac(objects);
    }

    private void isMac(Object[] objects) {
        String mac = new String((byte[]) objects[0], 0, (int) objects[1]);
        if (ListObj != null && ListObj.size() > 0) {
            String mac1 = mac;
            for (int i = 0; i < ListObj.size(); i++) {
                userDevice = (UserDevice) ListObj.get(i);
                if (userDevice.getDeviceMac().equals(mac1)) {
                    setList();
                    return;
                }
            }
            list.add(getItem(mac));
        } else
            list.add(getItem(mac));
        setList();
    }

    /**
     * 这个设置listview显示
     */
    private void setList() {
        if (adapter == null) {
            adapter = new LGAdapter(context, list, "ListView");
            configList.setAdapter(adapter);
        } else
            adapter.RefreshData(list);
    }

    /**
     * 超时
     *
     * @param position
     * @param error
     */
    @Override
    public void Error(int position, int error) {
        Toast.makeText(context, getResources().getString(R.string.DeviceTimeout), Toast.LENGTH_SHORT).show();
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
        CommonIntent.IntentActivity(context, DeviceEdit.class, data, "new");
    }

    /**
     * 这个是点击事件的接口
     *
     * @param v
     */
    @Override
    public void circleClick(View v) {
        //跳转到Airkiss界面
        CommonIntent.IntentActivity(context, AirkissNetWork.class, data);
    }
}
