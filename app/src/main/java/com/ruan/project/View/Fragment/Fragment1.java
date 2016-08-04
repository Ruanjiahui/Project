package com.ruan.project.View.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.SystemUtil.SystemTool;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.ui_sdk.View.MyImageView;
import com.example.administrator.ui_sdk.View.RreshLinearLayout;
import com.example.administrator.ui_sdk.View.RefreshSideListView;
import com.ruan.project.Controllar.CheckOnline;
import com.example.administrator.ui_sdk.ItemClick;
import com.ruan.project.Controllar.FragmentControl;
import com.ruan.project.Interface.DataHandler;
import com.ruan.project.Interface.PopWinOnClick;
import com.ruan.project.Moudle.Device;
import com.ruan.project.Moudle.Scene;
import com.ruan.project.Moudle.UserDevice;
import com.ruan.project.Other.Adapter.SideListViewAdapter;
import com.ruan.project.Other.DataBase.DatabaseOpera;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.Other.HTTP.HttpURL;
import com.ruan.project.Other.System.NetWork;
import com.ruan.project.R;
import com.ruan.project.View.Activity.DeviceType;
import com.ruan.project.View.Activity.DeviceControl;
import com.ruan.project.View.Activity.DeviceEdit;
import com.ruan.project.View.MyPopWindow;

import java.util.ArrayList;

/**
 * Created by Soft on 2016/6/23.
 */
public class Fragment1 extends Fragment implements View.OnClickListener, ItemClick, AdapterView.OnItemClickListener, ItemClick.RreshInterface, DataHandler, PopWinOnClick {

    private View view = null;
    private Activity activity = null;
    private RefreshSideListView sideListView;
    private ArrayList<Object> list = null;
    private SideListViewAdapter adapter = null;
    private Context context = null;
    private RelativeLayout fragment1Top = null;
    private ImageView fragment1Logo = null;
    private RreshLinearLayout myLinear = null;
    private MyPopWindow popWindow = null;

    private UserDevice userDevice = null;
    private ArrayList<Object> ListObj = null;
    private ArrayList<Object> sceneListObj = null;
    private DatabaseOpera databaseOpera = null;
    private FragmentControl fragmentControl = null;
    private Scene scene = null;

    private MyImageView MainFind = null;
    private MyImageView MainAdd = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment1, null);
        context = getActivity();
        activity = (Activity) context;

        databaseOpera = new DatabaseOpera(context);
        fragmentControl = new FragmentControl(context);

        //这里是获取用户设备表的数据，所以首先获取本地数据库的数据同时向服务器获取查询是否有更新数据，如果有更新数据则获取最新的数据
        //如果没有最新的数据则不进行任何的操作，如果本地没有数据库获取没有任何数据的话，就直接获取服务器上面的数据，之后插入本地数据库
        //获取数据库数据
        getDatabaseData("", null);


        sideListView = (RefreshSideListView) view.findViewById(R.id.slideListView);
        fragment1Top = (RelativeLayout) view.findViewById(R.id.fragment1Top);
        fragment1Logo = (ImageView) view.findViewById(R.id.fragment1Logo);
        myLinear = (RreshLinearLayout) view.findViewById(R.id.myLinear);
        MainFind = (MyImageView) view.findViewById(R.id.MainFind);
        MainAdd = (MyImageView) view.findViewById(R.id.MainAdd);

        adapter = new SideListViewAdapter(context, list);


        sideListView.setAdapter(adapter);

        adapter.setItemClick(this);
        sideListView.setOnItemClickListener(this);
        sideListView.setRreshClick(this);
        myLinear.setRreshClick(this);

        MainAdd.setOnClickListener(this);
        MainFind.setOnClickListener(this);
        DensityUtil.setRelayoutSize(MainFind, DensityUtil.dip2px(context, 50), DensityUtil.dip2px(context, 50), BaseActivity.height / 5 * 4, 0, 0, DensityUtil.dip2px(context, 20), new int[]{RelativeLayout.ALIGN_PARENT_RIGHT});
        DensityUtil.setRelayoutSize(MainAdd, DensityUtil.dip2px(context, 50), DensityUtil.dip2px(context, 50), BaseActivity.height / 7 * 5, 0, 0, DensityUtil.dip2px(context, 20), new int[]{RelativeLayout.ALIGN_PARENT_RIGHT});

        DensityUtil.setRelHeight(view, BaseActivity.height);
        DensityUtil.setRelHeight(fragment1Top, BaseActivity.height / 4);
        DensityUtil.setRelayoutSize(fragment1Logo, BaseActivity.width / 2, DensityUtil.dip2px(context, 20), DensityUtil.dip2px(context, 40), 0, 0, 0);

        return view;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.MainFind:
                list = getBottomList();
                //创建PopWindow的组件
                popWindow = new MyPopWindow(activity, list, BaseActivity.width / 2, 0);
                popWindow.setPopBackground(R.drawable.popcircle);
                popWindow.setPopListSector(R.drawable.popselector);
                popWindow.showPopupWindow(MainFind, MyPopWindow.UP, BaseActivity.width / 4, BaseActivity.height / 4);
                popWindow.setOnPopWinItemClick(this);
                break;
            case R.id.MainAdd:
                CommonIntent.IntentActivity(context, DeviceType.class);
                break;
        }
    }

    private ArrayList<Object> getBottomList() {
        DatabaseOpera databaseOpera = new DatabaseOpera(context);
        sceneListObj = databaseOpera.DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.SceneName, null, "", null, "", "", "", "", Scene.class, false);
        if (sceneListObj != null && sceneListObj.size() != 0)
            list = new FragmentControl(context).getFragment2List(sceneListObj);
        return list;
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
        userDevice = (UserDevice) ListObj.get(position - 1);
        if (position > 0 && userDevice.getDeviceOnline().equals("2"))
            CommonIntent.IntentActivity(context, DeviceControl.class, userDevice.getDeviceID());
        else
            Toast.makeText(context, "设备不在线", Toast.LENGTH_SHORT).show();
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
                databaseOpera.DataDelete(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, "deviceID = ? and userID = ?", new String[]{userDevice.getDeviceID(), "123456"});
                //删除服务器的数据库的数据
                break;
        }
    }

    private void getDatabaseData(String wherearg, String[] whereargs) {
        ListObj = databaseOpera.DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, null, wherearg, whereargs, "", "", "", "", UserDevice.class, true);
        list = fragmentControl.setFragment1List(ListObj);
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
        getDatabaseData("", null);
        adapter.RefreshData(list);
    }

    @Override
    public void RreshData() {
        HttpURL.STATE = SystemTool.isNetState(context);
        if (HttpURL.STATE == NetWork.WIFI)
            new CheckOnline(context, this).UDPCheck();
        //通过云端进行设备检测是否在线
        //如果wifi没有连接则使用外网判断设备是否在线
        if (HttpURL.STATE == NetWork.INTNET)
            new CheckOnline(context, this).HTTPCheck();
    }

    /**
     * 更新数据的接口
     */
    @Override
    public void ReStartData() {
        ReData();
        sideListView.setVisiableTopView();
    }

    /**
     * 弹出窗口的点击事件
     *
     * @param parent   弹出窗口的所有组件
     * @param view
     * @param position 弹出窗口listview的个数
     * @param id
     */
    @Override
    public void OnPopItemClick(AdapterView<?> parent, View view, int position, long id) {
        scene = (Scene) sceneListObj.get(position);
        getDatabaseData("sceneID = ?", new String[]{scene.getSceneID()});
        adapter.RefreshData(list);
        popWindow.disShow();
    }
}
