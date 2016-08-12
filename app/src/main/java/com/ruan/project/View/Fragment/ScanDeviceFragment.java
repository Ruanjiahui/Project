package com.ruan.project.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.ui_sdk.MyCircleLoading;
import com.example.administrator.ui_sdk.View.CircleLoading;
import com.example.administrator.ui_sdk.View.RefreshSideListView;
import com.example.administrator.ui_sdk.View.SideListView;
import com.ruan.project.Interface.UDPInterface;
import com.ruan.project.Moudle.Item;
import com.ruan.project.Other.Adapter.SideListViewAdapter;
import com.ruan.project.Other.UDP.ScanDevice;
import com.ruan.project.Other.UDP.UDPConfig;
import com.ruan.project.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/2.
 */
public class ScanDeviceFragment extends Fragment implements MyCircleLoading, UDPInterface.HandlerMac {

    private Context context = null;
    private View view = null;
    private CircleLoading scanConn;
    private TextView scanText;
    private RefreshSideListView scanSlideListView;
    private SideListViewAdapter adapter = null;
    private ArrayList<Object> list = null;

    //记录扫描当前状态
    private boolean scanState = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.scandevice, container, false);

//        context = getActivity();
//        list = new ArrayList<>();
//
//        scanConn = (CircleLoading) view.findViewById(R.id.scanConn);
//        scanText = (TextView) view.findViewById(R.id.scanText);
//        scanSlideListView = (RefreshSideListView) view.findViewById(R.id.scanSlideListView);
//
//
//        scanConn.setTime(5000);
//        scanConn.setSweepAngle(360);
//        scanConn.setClick(this);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new SideListViewAdapter(context, list);
    }

    /**
     * 这个是点击事件的接口
     *
     * @param v
     */
    @Override
    public void circleClick(View v) {
        if (!scanConn.getNowState()) {
            scanState = true;
            scanConn.setStart();
            ScanDevice();
            //进行周边扫描
        } else {
            scanState = false;
            scanConn.setStop(CircleLoading.END);
        }
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
        scanConn.setStop(CircleLoading.END);
    }


    private Object getItem(){
        Item item = new Item();

//        item.setHomeImage(ImageTransformation.Resouce2Drawable(context , R.c));

        return item;
    }
}
