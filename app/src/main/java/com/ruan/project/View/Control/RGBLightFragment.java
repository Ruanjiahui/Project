package com.ruan.project.View.Control;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.administrator.Interface.HttpInterface;
import com.ruan.project.Controllar.CallBack;
import com.ruan.project.Interface.UDPInterface;
import com.ruan.project.Moudle.REGlight;
import com.ruan.project.Moudle.UserDevice;
import com.ruan.project.R;

/**
 * Created by Administrator on 2016/7/30.
 */
public class RGBLightFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, UDPInterface.HandlerMac, HttpInterface.HttpHandler {

    private View view = null;

    private SeekBar seekBar;
    private SeekBar seekBar2;
    private SeekBar seekBar3;

    private REGlight reGlight = null;
    private CallBack callBack = null;
    //设备的ip
    private String IP = null;
    //设备的端口
    private int PORT = 0;
    //设备的Mac
    private String MAC = null;
    //传输的数据
    private String data = null;
    //判断是否是第一次获取的udp数据
    private boolean isVisiable = false;
    //设备的ID
    private UserDevice userDevice = null;


    /**
     * Activity传输Fragment
     *
     * @param userDevice
     * @return
     */
    public static Fragment getInstance(UserDevice userDevice) {
        RGBLightFragment rgbLight = new RGBLightFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", userDevice);
        rgbLight.setArguments(bundle);
        return rgbLight;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.rgblight, container, false);

        //获取Activuity传输的数据
        userDevice = getArguments().getParcelable("data");
        //获取设备的数据
        IP = userDevice.getDeviceIP();
        PORT = Integer.parseInt(userDevice.getDevicePORT());
        MAC = userDevice.getDeviceMac();

        reGlight = new REGlight();
        data = reGlight.getRGBColor();


        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        seekBar2 = (SeekBar) view.findViewById(R.id.seekBar2);
        seekBar3 = (SeekBar) view.findViewById(R.id.seekBar3);


        //一进来马上获取一下设备的实现的数据对界面进行更新
        callBack = new CallBack(this, this);
        //这个进行设备数据的获取 参数分别是  IP  端口  数据  标识
        callBack.setDeviceControl(IP, PORT, MAC, data, 999);


        seekBar.setOnSeekBarChangeListener(this);
        seekBar2.setOnSeekBarChangeListener(this);
        seekBar3.setOnSeekBarChangeListener(this);

        return view;
    }

    /**
     * Notification that the progress level has changed. Clients can use the fromUser parameter
     * to distinguish user-initiated changes from those that occurred programmatically.
     *
     * @param seekBar  The SeekBar whose progress has changed
     * @param progress The current progress level. This will be in the range 0..max where max
     *                 was set by {@link #(int)}. (The default value for max is 100.)
     * @param fromUser True if the progress change was initiated by the user.
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekBar:
                reGlight.setRed((double)progress / 100);
                reGlight.setGreen(0.50000);
                reGlight.setBlue(0.50000);
                reGlight.setAphla(1.00000);
                data = reGlight.setRGBColor();
                callBack.setDeviceControl(IP, PORT, MAC, data, 999);
                Log.e("Ruan seekBar", progress + "--");
                break;
            case R.id.seekBar2:
                Log.e("Ruan seekBar2", progress + "--");
                break;
            case R.id.seekBar3:
                Log.e("Ruan seekBar2", progress + "--");
                break;
        }
    }

    /**
     * Notification that the user has started a touch gesture. Clients may want to use this
     * to disable advancing the seekbar.
     *
     * @param seekBar The SeekBar in which the touch gesture began
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    /**
     * Notification that the user has finished a touch gesture. Clients may want to use this
     * to re-enable advancing the seekbar.
     *
     * @param seekBar The SeekBar in which the touch gesture began
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

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
        Log.e("Ruan", new String((byte[]) objects[0], 0, (int) objects[1]));
    }

    /**
     * 超时
     *
     * @param position
     * @param error
     */
    @Override
    public void Error(int position, int error) {
        Log.e("Ruan", "----超时");
    }

    /**
     * 这个是处理Http返回来的结果
     *
     * @param position 请求的表示
     * @param result   请求返回的结果
     */
    @Override
    public void handler(int position, String result) {

    }
}