package com.blink.blinkiot.View.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.HttpCode;
import com.example.administrator.Interface.HttpResult;
import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.ui_sdk.View.MyImageView;
import com.example.administrator.ui_sdk.View.PullToRefreshView;
import com.example.administrator.ui_sdk.View.RefreshSideListView;
import com.blink.blinkiot.Controllar.CheckOnline;
import com.example.administrator.ui_sdk.ItemClick;
import com.blink.blinkiot.Controllar.FragmentControl;
import com.blink.blinkiot.Interface.DataHandler;
import com.blink.blinkiot.Moudle.Device;
import com.blink.blinkiot.Moudle.Scene;
import com.blink.blinkiot.Moudle.UserDevice;
import com.blink.blinkiot.Moudle.Weather;
import com.blink.blinkiot.Other.Adapter.SideListViewAdapter;
import com.blink.blinkiot.Other.DataBase.DatabaseOpera;
import com.blink.blinkiot.Other.DatabaseTableName;
import com.blink.blinkiot.Other.DeviceCode;
import com.blink.blinkiot.Other.HTTP.HttpURL;
import com.blink.blinkiot.Other.HTTP.HttpWeather;
import com.blink.blinkiot.R;
import com.blink.blinkiot.View.Activity.City;
import com.blink.blinkiot.View.Activity.DeviceType;
import com.blink.blinkiot.View.Activity.DeviceControl;
import com.blink.blinkiot.View.Activity.DeviceEdit;

import java.util.ArrayList;

/**
 * Created by Soft on 2016/6/23.
 */
public class Fragment1 extends Fragment implements View.OnClickListener, ItemClick, AdapterView.OnItemClickListener, DataHandler, Animation.AnimationListener, PullToRefreshView.OnRefreshListener, HttpResult.HttpString {

    private View view = null;
    private Activity activity = null;
    private RefreshSideListView slideListView;
    private ArrayList<Object> list = null;
    private ArrayList<Object> scenelist = null;
    private SideListViewAdapter adapter = null;
    private static Context context = null;
    private RelativeLayout fragment1Top = null;
    private ImageView fragment1Logo = null;
    private PullToRefreshView mPullToRefreshView = null;
    private TextView fragment1weather = null;
    private TextView fragment1City = null;

    private UserDevice userDevice = null;
    private ArrayList<Object> ListObj = null;
    private ArrayList<Object> sceneListObj = null;
    private DatabaseOpera databaseOpera = null;
    private FragmentControl fragmentControl = null;
    private Scene scene = null;

    private MyImageView MainFind = null;
    private MyImageView MainAdd = null;
    private static RelativeLayout fragment1Background;
    private static View bottomMain = null;
    private RefreshSideListView bottomListView = null;
    private TextView bottomText = null;
    private SideListViewAdapter sideListViewAdapter = null;
    private static boolean isVisiable = false;

    private static Animation StopanimationBottom = null;
    private static Animation StopanimationBack = null;
    private static Animation.AnimationListener animationListener = null;

    private Weather weather = null;
    //标识设备的状态值
    private boolean status;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment1, null);
        context = getActivity();
        activity = (Activity) context;

        databaseOpera = new DatabaseOpera(context);
        fragmentControl = new FragmentControl(context);

        animationListener = this;


        scenelist = new ArrayList<>();
        list = new ArrayList<>();

        //这里是获取用户设备表的数据，所以首先获取本地数据库的数据同时向服务器获取查询是否有更新数据，如果有更新数据则获取最新的数据
        //如果没有最新的数据则不进行任何的操作，如果本地没有数据库获取没有任何数据的话，就直接获取服务器上面的数据，之后插入本地数据库
        //获取数据库数据
        fragment1Background = (RelativeLayout) view.findViewById(R.id.fragment1Background);
        slideListView = (RefreshSideListView) view.findViewById(R.id.slideListView);
        fragment1Top = (RelativeLayout) view.findViewById(R.id.fragment1Top);
        fragment1Logo = (ImageView) view.findViewById(R.id.fragment1Logo);
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.myLinear);
        MainFind = (MyImageView) view.findViewById(R.id.MainFind);
        MainAdd = (MyImageView) view.findViewById(R.id.MainAdd);
        bottomMain = view.findViewById(R.id.bottomMain);
        bottomListView = (RefreshSideListView) view.findViewById(R.id.bottomListView);
        bottomText = (TextView) view.findViewById(R.id.bottomText);
        fragment1weather = (TextView) view.findViewById(R.id.fragment1weather);
        fragment1City = (TextView) view.findViewById(R.id.fragment1City);


        bottomMain.setVisibility(View.GONE);


        fragment1City.setOnClickListener(this);
        slideListView.setOnItemClickListener(this);
        mPullToRefreshView.setOnRefreshListener(this);
        fragment1Background.setOnClickListener(this);
        MainAdd.setOnClickListener(this);
        MainFind.setOnClickListener(this);
        DensityUtil.setRelayoutSize(MainFind, DensityUtil.dip2px(context, 55), DensityUtil.dip2px(context, 55), BaseActivity.height / 5 * 4, 0, 0, DensityUtil.dip2px(context, 20), new int[]{RelativeLayout.ALIGN_PARENT_RIGHT});
        DensityUtil.setRelayoutSize(MainAdd, DensityUtil.dip2px(context, 55), DensityUtil.dip2px(context, 55), BaseActivity.height / 3 * 2 + DensityUtil.dip2px(context, 10), 0, 0, DensityUtil.dip2px(context, 20), new int[]{RelativeLayout.ALIGN_PARENT_RIGHT});
        DensityUtil.setRelHeight(bottomMain, BaseActivity.height / 2, new int[]{RelativeLayout.ALIGN_PARENT_BOTTOM});
        DensityUtil.setRelHeight(view, BaseActivity.height);
        DensityUtil.setRelHeight(fragment1Top, BaseActivity.height / 4);
        DensityUtil.setRelayoutSize(fragment1Logo, BaseActivity.width / 2, DensityUtil.dip2px(context, 20), DensityUtil.dip2px(context, 40), 0, 0, 0);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getDatabaseData("", null);

        if (adapter == null) {
            adapter = new SideListViewAdapter(context, list);
            slideListView.setAdapter(adapter);
            adapter.setItemClick(this);
        } else {
            adapter.RefreshData(list);
        }

        if (HttpURL.CityName != null && HttpURL.Cityweather == null) {
            fragment1City.setText(HttpURL.CityName);
            //获取天气
            new HttpWeather(HttpURL.WethereURL + HttpURL.CityName, HttpURL.WeatherID, this, 0);
            weather = new Weather();
        } else {
            if (HttpURL.Cityweather != null && HttpURL.Cityweather[0] != null) {
                fragment1City.setText(HttpURL.CityName);
                fragment1weather.setText(HttpURL.Cityweather[0] + "~" + HttpURL.Cityweather[1]);
            }
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment1Background:
                if (isVisiable)
                    stopAnimation();
                break;
            case R.id.MainFind:
                bottomText.setText(getResources().getString(R.string.SceneName));
                getBottomList();
                if (sideListViewAdapter == null) {
                    sideListViewAdapter = new SideListViewAdapter(context, scenelist);
                    bottomListView.setAdapter(sideListViewAdapter);
                } else
                    sideListViewAdapter.RefreshData(scenelist);
                if (!isVisiable)
                    startAnimation();
                else
                    stopAnimation();
                bottomListView.setOnItemClickListener(this);
                break;
            case R.id.MainAdd:
                CommonIntent.IntentActivity(context, DeviceType.class);
                break;
            case R.id.fragment1City:
                CommonIntent.IntentActivity(activity, City.class);
                break;
        }
    }

    /**
     * 获取场景列表的数据
     *
     * @return
     */
    private ArrayList<Object> getBottomList() {
        DatabaseOpera databaseOpera = new DatabaseOpera(context);
        sceneListObj = databaseOpera.DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.SceneName, null, "", null, "", "", "", "", Scene.class, false);
        if (sceneListObj != null && sceneListObj.size() != 0)
            scenelist = fragmentControl.getFragment2List(sceneListObj);
        return scenelist;
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
        switch (parent.getId()) {
            case R.id.bottomListView:
                scene = (Scene) sceneListObj.get(position);
                getDatabaseData("sceneID = ?", new String[]{scene.getSceneID()});
                adapter.RefreshData(list);
                if (isVisiable)
                    stopAnimation();
                break;
            case R.id.slideListView:
                userDevice = (UserDevice) ListObj.get(position);
                if (DeviceCode.ONLINE == Integer.parseInt(userDevice.getDeviceOnline())) {
                    //获取设备的信息
                    ArrayList<Object> ListDevice = databaseOpera.DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.DeviceTableName, null, "deviceID = ?", new String[]{userDevice.getDeviceID()}, "", "", "", "", Device.class, false);
                    if (ListDevice == null && ListDevice.size() <= 0)
                        return;
                    Device device = (Device) ListDevice.get(0);
                    CommonIntent.IntentActivity(context, DeviceControl.class, userDevice.getDeviceMac(), device.getDeviceTypeID(), status);
                } else
                    Toast.makeText(context, getResources().getString(R.string.DeviceOnline), Toast.LENGTH_SHORT).show();
                break;
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
        //如果是真实设备则允许侧滑点击
        if (status) {
            slideListView.ShowNormal();
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
    }

    /**
     * 获取数据的数据
     *
     * @param wherearg
     * @param whereargs
     */
    private void getDatabaseData(String wherearg, String[] whereargs) {
        ListObj = databaseOpera.DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, null, wherearg, whereargs, "", "", "", "", UserDevice.class, true);
        status = true;
        //如果获取用户设备为空则说明没有用户设备这个时候应该显示模拟设备，将当前的状态设置为模拟状态否则就是真实状态
        if (ListObj.size() <= 0 || ListObj == null) {
            status = false;
            ListObj = databaseOpera.DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.AnalogyName, null, wherearg, whereargs, "", "", "", "", UserDevice.class, true);
        }
        list = fragmentControl.setFragment1List(ListObj);
    }


    /**
     * 更新数据
     */
    private void ReData(int position) {
        getDatabaseData("", null);
        adapter.RefreshData(list);
        if (position == 0)
            //关闭动画
            mPullToRefreshView.setRefreshing(false);
    }

    /**
     * 更新数据的接口
     */
    @Override
    public void ReStartData(int position) {
        ReData(position);
    }

    /**
     * 开始动画的操作
     */
    private void startAnimation() {
        bottomMain.setVisibility(View.VISIBLE);
        fragment1Background.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.bottom_out);
        Animation banAnimation = AnimationUtils.loadAnimation(context, R.anim.backgroudout);
        bottomMain.startAnimation(animation);
        fragment1Background.startAnimation(banAnimation);
        isVisiable = true;
    }

    /**
     * 停止动画的操作
     */
    private static void stopAnimation() {
        StopanimationBottom = AnimationUtils.loadAnimation(context, R.anim.bottom_in);
        StopanimationBack = AnimationUtils.loadAnimation(context, R.anim.backgroudin);
        bottomMain.startAnimation(StopanimationBottom);
        fragment1Background.startAnimation(StopanimationBack);
        StopanimationBottom.setAnimationListener(animationListener);
        isVisiable = false;
    }

    /**
     * <p>Notifies the start of the animation.</p>
     *
     * @param animation The started animation.
     */
    @Override
    public void onAnimationStart(Animation animation) {

    }

    /**
     * <p>Notifies the end of the animation. This callback is not invoked
     * for animations with repeat count set to INFINITE.</p>
     *
     * @param animation The animation which reached its end.
     */
    @Override
    public void onAnimationEnd(Animation animation) {
        if (StopanimationBottom == animation) {
            bottomMain.setVisibility(View.GONE);
            fragment1Background.setVisibility(View.GONE);
        }
    }

    /**
     * <p>Notifies the repetition of the animation.</p>
     *
     * @param animation The animation which was repeated.
     */
    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    /**
     * 这个刷新的接口
     */
    @Override
    public void onRefresh() {
        //获取天气
        new HttpWeather(HttpURL.WethereURL + HttpURL.CityName, HttpURL.WeatherID, this, 0);
        weather = new Weather();
        //下拉刷新如果是真实状态则允许UDP请求否则则不允许请求
        if (status)
            new CheckOnline(context, this).UDPCheck();
        else
            ReData(0);
    }

    /**
     * 处理http成功返回的接口
     *
     * @param code   请求标识
     * @param result 请求返回的字节流
     */
    @Override
    public void onSucceful(int code, String result) {
        weather.setJson(result);
        HttpURL.Cityweather = weather.getWeather();
        if (HttpURL.Cityweather != null && HttpURL.Cityweather[0] != null)
            fragment1weather.setText(HttpURL.Cityweather[0] + "~" + HttpURL.Cityweather[1]);
    }

    /**
     * 处理http连接出错的接口
     *
     * @param code  请求标识
     * @param Error 请求出错的标识
     */
    @Override
    public void onError(int code, int Error) {
//        if (Error == HttpCode.TIMEOUT)
//            Toast.makeText(context, context.getResources().getString(R.string.HttpError), Toast.LENGTH_SHORT).show();
    }


    public static boolean onKeyDown() {
        if (isVisiable == true) {
            stopAnimation();
            return false;
        }
        return true;
    }
}
