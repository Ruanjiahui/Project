package com.ruan.project.View.Fragment;

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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.DeviceURL;
import com.example.administrator.HttpCode;
import com.example.administrator.Interface.Connect;
import com.example.administrator.Interface.Result;
import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.FileUtil.FileTool;
import com.example.administrator.data_sdk.SystemUtil.SystemTool;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.ui_sdk.View.MyImageView;
import com.example.administrator.ui_sdk.View.PullToRefreshView;
import com.example.administrator.ui_sdk.View.RreshLinearLayout;
import com.example.administrator.ui_sdk.View.RefreshSideListView;
import com.ruan.project.Controllar.CheckOnline;
import com.example.administrator.ui_sdk.ItemClick;
import com.ruan.project.Controllar.FragmentControl;
import com.ruan.project.Interface.DataHandler;
import com.ruan.project.Interface.PopWinOnClick;
import com.ruan.project.Moudle.Scene;
import com.ruan.project.Moudle.UserDevice;
import com.ruan.project.Moudle.Weather;
import com.ruan.project.Other.Adapter.LGAdapter;
import com.ruan.project.Other.Adapter.SideListViewAdapter;
import com.ruan.project.Other.DataBase.DatabaseOpera;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.Other.HTTP.HttpURL;
import com.ruan.project.Other.HTTP.HttpWeather;
import com.ruan.project.Other.System.NetWork;
import com.ruan.project.R;
import com.ruan.project.View.Activity.City;
import com.ruan.project.View.Activity.DeviceType;
import com.ruan.project.View.Activity.DeviceControl;
import com.ruan.project.View.Activity.DeviceEdit;
import com.ruan.project.View.MyPopWindow;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Soft on 2016/6/23.
 */
public class Fragment1 extends Fragment implements View.OnClickListener, ItemClick, AdapterView.OnItemClickListener, DataHandler, Animation.AnimationListener, PullToRefreshView.OnRefreshListener, Result.HttpString {

    private View view = null;
    private Activity activity = null;
    private RefreshSideListView slideListView;
    private ArrayList<Object> list = null;
    private ArrayList<Object> scenelist = null;
    private SideListViewAdapter adapter = null;
    private Context context = null;
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
    private RelativeLayout fragment1Background;
    private View bottomMain = null;
    private RefreshSideListView bottomListView = null;
    private TextView bottomText = null;
    private SideListViewAdapter sideListViewAdapter = null;
    private boolean isVisiable = false;

    private Animation StopanimationBottom = null;
    private Animation StopanimationBack = null;

    private Weather weather = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment1, null);
        context = getActivity();
        activity = (Activity) context;

        databaseOpera = new DatabaseOpera(context);
        fragmentControl = new FragmentControl(context);


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
        DensityUtil.setRelayoutSize(MainFind, DensityUtil.dip2px(context, 50), DensityUtil.dip2px(context, 50), BaseActivity.height / 5 * 4, 0, 0, DensityUtil.dip2px(context, 20), new int[]{RelativeLayout.ALIGN_PARENT_RIGHT});
        DensityUtil.setRelayoutSize(MainAdd, DensityUtil.dip2px(context, 50), DensityUtil.dip2px(context, 50), BaseActivity.height / 7 * 5, 0, 0, DensityUtil.dip2px(context, 20), new int[]{RelativeLayout.ALIGN_PARENT_RIGHT});
        DensityUtil.setRelHeight(bottomMain, BaseActivity.height / 2, new int[]{RelativeLayout.ALIGN_PARENT_BOTTOM});
        DensityUtil.setRelHeight(view, BaseActivity.height);
        DensityUtil.setRelHeight(fragment1Top, BaseActivity.height / 4);
        DensityUtil.setRelayoutSize(fragment1Logo, BaseActivity.width / 2, DensityUtil.dip2px(context, 20), DensityUtil.dip2px(context, 40), 0, 0, 0);

        if (HttpURL.Cityweather == null && HttpURL.CityName != null) {
            fragment1City.setText(HttpURL.CityName);
            //获取天气
            new HttpWeather(HttpURL.WethereURL + HttpURL.CityName, "c3070ac56cff43765b78f3fca4dc812a", this, 0);
            weather = new Weather();
        } else {
            if (HttpURL.Cityweather != null && HttpURL.Cityweather[0] != null) {
                fragment1City.setText(HttpURL.CityName);
                fragment1weather.setText(HttpURL.Cityweather[0] + "~" + HttpURL.Cityweather[1]);
            }
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDatabaseData("", null);

        if (adapter == null) {
            adapter = new SideListViewAdapter(context, list);
            slideListView.setAdapter(adapter);
            adapter.setItemClick(this);
        } else {
            adapter.RefreshData(list);
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
                bottomText.setText("场景");
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
                CommonIntent.IntentResActivity(activity, City.class, 001, 001);
                break;
        }
    }

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
                if (userDevice.getDeviceOnline().equals("2"))
                    CommonIntent.IntentActivity(context, DeviceControl.class, userDevice.getDeviceMac(), String.valueOf(DeviceURL.Switch));
                else
                    Toast.makeText(context, "设备不在线", Toast.LENGTH_SHORT).show();
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
        slideListView.ShowNormal();
        userDevice = (UserDevice) ListObj.get(position);
        switch (View) {
            //编辑点击事件
            case 0:
                CommonIntent.IntentActivity(context, DeviceEdit.class, userDevice.getDeviceMac(), "edit");
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
     * 获取数据的数据
     *
     * @param wherearg
     * @param whereargs
     */
    private void getDatabaseData(String wherearg, String[] whereargs) {
        ListObj = databaseOpera.DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, null, wherearg, whereargs, "", "", "", "", UserDevice.class, true);
        list = fragmentControl.setFragment1List(ListObj);
    }


    /**
     * 更新数据
     */
    private void ReData() {
        getDatabaseData("", null);
        adapter.RefreshData(list);
        //关闭动画
        mPullToRefreshView.setRefreshing(false);
    }

    /**
     * 更新数据的接口
     */
    @Override
    public void ReStartData() {
        ReData();
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
    private void stopAnimation() {
        StopanimationBottom = AnimationUtils.loadAnimation(context, R.anim.bottom_in);
        StopanimationBack = AnimationUtils.loadAnimation(context, R.anim.backgroudin);
        bottomMain.startAnimation(StopanimationBottom);
        fragment1Background.startAnimation(StopanimationBack);
        StopanimationBottom.setAnimationListener(this);
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
        new HttpWeather(HttpURL.WethereURL + HttpURL.CityName, "c3070ac56cff43765b78f3fca4dc812a", this, 0);
        weather = new Weather();
        new CheckOnline(context, this).UDPCheck();
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
        if (Error == HttpCode.TIMEOUT)
            Toast.makeText(context, "请求超时", Toast.LENGTH_SHORT).show();
    }
}
