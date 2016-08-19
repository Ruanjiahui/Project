package com.blink.blinkiot.View.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.blink.blinkiot.Start.DeviceURL;
import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.ui_sdk.ItemClick;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.ui_sdk.View.SideListView;
import com.blink.blinkiot.Controllar.FragmentControl;
import com.blink.blinkiot.Moudle.UserDevice;
import com.blink.blinkiot.Other.Adapter.SideListViewAdapter;
import com.blink.blinkiot.Other.DataBase.DatabaseOpera;
import com.blink.blinkiot.Other.DatabaseTableName;
import com.blink.blinkiot.Other.DeviceCode;
import com.blink.blinkiot.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/2.
 */
public class SceneList extends BaseActivity implements AdapterView.OnItemClickListener, ItemClick {

    private View view = null;
    private SideListView sceneListView = null;

    private String sceneID = null;
    private String sceneName = null;

    private ArrayList<Object> ListObj = null;
    private UserDevice userDevice = null;
    private DatabaseOpera databaseOpera = null;
    private ArrayList<Object> list = null;

    private SideListViewAdapter adapter = null;

    /**
     * Start()
     */
    @Override
    public void init() {
        sceneID = getIntent().getStringExtra("flag");
        sceneName = getIntent().getStringExtra("data");

        databaseOpera = new DatabaseOpera(context);

        ListObj = databaseOpera.DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, null, "sceneID = ?", new String[]{sceneID}, "", "", "", "", UserDevice.class, true);

        view = LayoutInflater.from(context).inflate(R.layout.scenelist, null);

        setRightTitleVisiable(false);
        setTopColor(R.color.Blue);
        setTitle(sceneName);
        setTopTitleColor(R.color.White);
        setLeftTitleColor(R.color.White);

        sceneListView = (SideListView) view.findViewById(R.id.sceneListView);

        setContent(view);

        sceneListView.setOnItemClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (ListObj != null && ListObj.size() != 0) {
            list = new FragmentControl(context).setFragment1List(ListObj);
            if (adapter == null) {
                adapter = new SideListViewAdapter(context, list);
                sceneListView.setAdapter(adapter);
            } else
                adapter.RefreshData(list);
            adapter.setItemClick(this);
        }

    }

    /**
     * item的子控件点击事件
     *
     * @param position 子控件在列表中属于第几个
     * @param View     子控件的序号靠最左边为第0个
     */
    @Override
    public void OnClick(int position, int View) {
        sceneListView.ShowNormal();
        userDevice = (UserDevice) ListObj.get(position);
        switch (View) {
            //编辑点击事件
            case 0:
                CommonIntent.IntentActivity(context, DeviceEdit.class, userDevice.getDeviceID(), "edit");
                break;
            //删除点击事件
            case 1:
                //删除界面的item并且同时删除本地数据的数据和服务器上面的数据
                list.remove(position);
                adapter.RefreshData(list);
                //删除本地数据库的数据
                databaseOpera.DataDelete(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, "deviceMac = ? and userID = ?", new String[]{userDevice.getDeviceMac(), "123456"});
                //删除服务器的数据库的数据
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
        userDevice = (UserDevice) ListObj.get(position);
        if (DeviceCode.ONLINE == Integer.parseInt(userDevice.getDeviceOnline()))
            CommonIntent.IntentActivity(context, DeviceControl.class, userDevice.getDeviceMac(), String.valueOf(DeviceURL.Switch) , true);
        else
            Toast.makeText(context, getResources().getString(R.string.DeviceOnline), Toast.LENGTH_SHORT).show();
    }
}
