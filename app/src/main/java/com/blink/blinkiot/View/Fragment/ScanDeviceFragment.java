package com.blink.blinkiot.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.ui_sdk.MyCircleLoading;
import com.example.administrator.ui_sdk.View.CircleLoading;
import com.example.administrator.ui_sdk.View.RefreshSideListView;
import com.blink.blinkiot.Interface.UDPInterface;
import com.blink.blinkiot.Moudle.Item;
import com.blink.blinkiot.Other.Adapter.SideListViewAdapter;
import com.blink.blinkiot.Other.UDP.ScanDevice;
import com.blink.blinkiot.Other.UDP.UDPConfig;
import com.blink.blinkiot.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/2.
 */
public class ScanDeviceFragment extends Fragment implements UDPInterface.HandlerMac, View.OnClickListener {

    private Context context = null;
    private View view = null;
    private View scanConn;
    private TextView scanText;
    private RefreshSideListView scanSlideListView;
    private SideListViewAdapter adapter = null;
    private ArrayList<Object> list = null;
    private TextView circlebut = null;

    //记录扫描当前状态
    private boolean scanState = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.scandevice, container, false);

        context = getActivity();
//        list = new ArrayList<>();
//
        scanConn = view.findViewById(R.id.scanConn);
        circlebut = (TextView) view.findViewById(R.id.circlebut);
//        scanText = (TextView) view.findViewById(R.id.scanText);
//        scanSlideListView = (RefreshSideListView) view.findViewById(R.id.scanSlideListView);
//
//
//        scanConn.setTime(5000);
//        scanConn.setSweepAngle(360);
        scanConn.setOnClickListener(this);
        circlebut.setText(getResources().getString(R.string.ScanName));

        DensityUtil.setRelayoutSize(scanConn, DensityUtil.dip2px(context, 55), DensityUtil.dip2px(context, 55), BaseActivity.height / 4 * 3, 0, 0, 0, new int[]{RelativeLayout.CENTER_HORIZONTAL});

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new SideListViewAdapter(context, list);
    }

    private void ScanDevice() {
        //计时器，广播没一秒发送一次，总共发送5次
        new ScanDevice().Scanner(UDPConfig.PORT, UDPConfig.data, this, UDPConfig.count);
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
        if (scanState) {

        }
    }

    /**
     * 超时
     *
     * @param position
     * @param error
     */
    @Override
    public void Error(int position, int error) {
        scanState = false;
    }


    private Object getItem() {
        Item item = new Item();

//        item.setHomeImage(ImageTransformation.Resouce2Drawable(context , R.c));

        return item;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }
}
