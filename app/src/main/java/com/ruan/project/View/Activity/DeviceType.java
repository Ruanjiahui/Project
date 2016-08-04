package com.ruan.project.View.Activity;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.ruan.project.Controllar.DeviceActivityControl;
import com.ruan.project.Interface.UDPInterface;
import com.ruan.project.Moudle.Device;
import com.ruan.project.Other.Adapter.FragmentAdapter;
import com.ruan.project.Other.DataBase.DataHandler;
import com.ruan.project.Moudle.Item;
import com.ruan.project.Other.Adapter.LGAdapter;
import com.ruan.project.Other.DataBase.DatabaseOpera;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.R;
import com.ruan.project.View.Control.SocketSwitchFragment;
import com.ruan.project.View.Fragment.AddDeviceFragment;
import com.ruan.project.View.Fragment.ScanDeviceFragment;

import java.util.ArrayList;

/**
 * Created by Soft on 2016/7/9.
 */
public class DeviceType extends BaseActivity implements ViewPager.OnPageChangeListener {

    private View view = null;
    private View qq_Top = null;

    private ViewPager deviceTypeViewPager = null;
    private ArrayList<Fragment> list = null;
    private RelativeLayout base_top_relative = null;
    private TextView qqLeft;
    private TextView qqRight;


    /**
     * Start()
     */
    @Override
    public void init() {
        view = LayoutInflater.from(context).inflate(R.layout.device, null);

        setTileBar(0);

        deviceTypeViewPager = (ViewPager) view.findViewById(R.id.deviceTypeViewPager);
        qq_Top = view.findViewById(R.id.qq_Top);
        base_top_relative = (RelativeLayout) view.findViewById(R.id.base_top_relative);
        qqLeft = (TextView) view.findViewById(R.id.qqLeft);
        qqRight = (TextView) view.findViewById(R.id.qqRight);


        list = new ArrayList<>();
        list.add(new AddDeviceFragment());
        list.add(new ScanDeviceFragment());

        deviceTypeViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), list));
        deviceTypeViewPager.setCurrentItem(0);


        setContent(view);


        qqLeft.setText("添加");
        qqRight.setText("扫描");
        base_top_relative.setOnClickListener(this);
        deviceTypeViewPager.addOnPageChangeListener(this);
        qqLeft.setOnClickListener(this);
        qqRight.setOnClickListener(this);
    }

    @Override
    public void Click(View v) {
        switch (v.getId()) {
            case R.id.base_top_relative:
                Applications.getInstance().removeOneActivity(this);
                break;
            case R.id.qqLeft:
                deviceTypeViewPager.setCurrentItem(0);
                setSector(0);
                break;
            case R.id.qqRight:
                deviceTypeViewPager.setCurrentItem(1);
                setSector(1);
                break;
        }
    }

    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *
     * @param position             Position index of the first page currently being displayed.
     *                             Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
     * @param positionOffsetPixels Value in pixels indicating the offset from position.
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * This method will be invoked when a new page becomes selected. Animation is not
     * necessarily complete.
     *
     * @param position Position index of the new selected page.
     */
    @Override
    public void onPageSelected(int position) {
        setSector(position);
    }

    /**
     * Called when the scroll state changes. Useful for discovering when the user
     * begins dragging, when the pager is automatically settling to the current page,
     * or when it is fully stopped/idle.
     *
     * @param state The new scroll state.
     * @see ViewPager#SCROLL_STATE_IDLE
     * @see ViewPager#SCROLL_STATE_DRAGGING
     * @see ViewPager#SCROLL_STATE_SETTLING
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private void setSector(int state) {
        switch (state) {
            case 0:
                qqLeft.setTextColor(getResources().getColor(R.color.Blue1));
                qqRight.setTextColor(getResources().getColor(R.color.White));
                qqLeft.setBackground(getResources().getDrawable(R.drawable.qq_top_left_select));
                qqRight.setBackground(getResources().getDrawable(R.drawable.qq_top_right_unselect));
                break;
            case 1:
                qqLeft.setTextColor(getResources().getColor(R.color.White));
                qqRight.setTextColor(getResources().getColor(R.color.Blue1));
                qqLeft.setBackground(getResources().getDrawable(R.drawable.qq_top_left_unselect));
                qqRight.setBackground(getResources().getDrawable(R.drawable.qq_top_right_select));
                break;
        }
    }
}
