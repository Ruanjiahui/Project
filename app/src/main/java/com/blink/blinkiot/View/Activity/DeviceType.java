package com.blink.blinkiot.View.Activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.blink.blinkiot.View.DialogClick;
import com.blink.blinkiot.View.MyShareDialog;
import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.blink.blinkiot.Other.Adapter.FragmentAdapter;
import com.blink.blinkiot.R;
import com.blink.blinkiot.View.Fragment.AddDeviceFragment;
import com.blink.blinkiot.View.Fragment.ScanDeviceFragment;

import java.util.ArrayList;

/**
 * Created by Soft on 2016/7/9.
 */
public class DeviceType extends BaseActivity implements ViewPager.OnPageChangeListener, DialogClick {

    private View view = null;
    private View qq_Top = null;

    private ViewPager deviceTypeViewPager = null;
    private ArrayList<Fragment> list = null;
    private RelativeLayout base_top_relative = null;
    private TextView qqLeft;
    private TextView qqRight;
    private TextView deviceTypeShare = null;

    public static Activity activity = null;


    /**
     * Start()
     */
    @Override
    public void init() {
        activity = (Activity) context;
        view = LayoutInflater.from(context).inflate(R.layout.device, null);

        setTileBar(0);

        deviceTypeViewPager = (ViewPager) view.findViewById(R.id.deviceTypeViewPager);
        qq_Top = view.findViewById(R.id.qq_Top);
        base_top_relative = (RelativeLayout) view.findViewById(R.id.base_top_relative);
        qqLeft = (TextView) view.findViewById(R.id.qqLeft);
        qqRight = (TextView) view.findViewById(R.id.qqRight);
        deviceTypeShare = (TextView) view.findViewById(R.id.deviceTypeShare);


        list = new ArrayList<>();
        list.add(new AddDeviceFragment());
        list.add(new ScanDeviceFragment());

        deviceTypeViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), list));
        deviceTypeViewPager.setCurrentItem(0);


        setContent(view);


        qqLeft.setText(getResources().getString(R.string.DeviceName));
        qqRight.setText(getResources().getString(R.string.ScanName));
        base_top_relative.setOnClickListener(this);
        deviceTypeViewPager.addOnPageChangeListener(this);
        qqLeft.setOnClickListener(this);
        qqRight.setOnClickListener(this);
        deviceTypeShare.setOnClickListener(this);
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
            case R.id.deviceTypeShare:
//                getMyDialog();
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

    private MyShareDialog myShareDialog = null;

    /**
     * 弹出对话框
     *
     * @return
     */
    private void getMyDialog() {
        ClipboardManager clip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        myShareDialog = new MyShareDialog(context, R.style.dialog);
        myShareDialog.DialogClick(this, 0);
        ClipData data = clip.getPrimaryClip();
        if (data != null)
            myShareDialog.setEditText(String.valueOf(data.getItemAt(0).getText()));
        //如果是真实设置才允许设置闹铃
        myShareDialog.show();
    }

    @Override
    public void Enter(int position) {
        myShareDialog.dismiss();
    }

    @Override
    public void Cancal(int position) {
        myShareDialog.dismiss();
    }
}
