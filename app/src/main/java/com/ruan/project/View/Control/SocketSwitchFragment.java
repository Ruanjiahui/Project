package com.ruan.project.View.Control;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.Interface.HttpInterface;
import com.ruan.project.Controllar.CallBack;
import com.ruan.project.Interface.UDPInterface;
import com.ruan.project.Moudle.SocketSwitch;
import com.ruan.project.Moudle.UserDevice;
import com.ruan.project.Other.UDP.FormatData;
import com.ruan.project.R;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Administrator on 2016/7/29.
 */
public class SocketSwitchFragment extends Fragment implements UDPInterface.HandlerMac, HttpInterface.HttpHandler, View.OnClickListener {

    private Button button;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;


    private View view = null;
    private SocketSwitch socketSwitch = null;
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
//    private boolean isVisiable = false;
    //设备的ID
    private UserDevice userDevice = null;

    //当前点击是哪个按钮
    private int jack = 0;
    private int ON = 1;
    private int UnOn = 0;


    /**
     * Activity传输Fragment
     *
     * @param userDevice
     * @return
     */
    public static Fragment getInstance(UserDevice userDevice) {
        SocketSwitchFragment socketSwitchFragment = new SocketSwitchFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", userDevice);
        socketSwitchFragment.setArguments(bundle);
        return socketSwitchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.socketswitch, container, false);

        button = (Button) view.findViewById(R.id.button);
        button2 = (Button) view.findViewById(R.id.button2);
        button3 = (Button) view.findViewById(R.id.button3);
        button4 = (Button) view.findViewById(R.id.button4);
        button5 = (Button) view.findViewById(R.id.button5);

        //获取Activuity传输的数据
        userDevice = getArguments().getParcelable("data");
        //获取设备的数据
        IP = userDevice.getDeviceIP();
        PORT = Integer.parseInt(userDevice.getDevicePORT());
        MAC = userDevice.getDeviceMac();

        //插座控制
        socketSwitch = new SocketSwitch();
        data = socketSwitch.getSocketSwtich();

        //一进来马上获取一下设备的实现的数据对界面进行更新
        callBack = new CallBack(this, this);
        //这个进行设备数据的获取 参数分别是  IP  端口  数据  标识
        callBack.setDeviceControl(IP, PORT, MAC, data, 999);


        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);

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
            case R.id.button:
                jack = 0;
                setButtonClick(button, false);
                data = socketSwitch.setSocketSwtich(getStatus(socketSwitch.getStatus0()), jack);
                break;
            case R.id.button2:
                jack = 1;
                setButtonClick(button2, false);
                data = socketSwitch.setSocketSwtich(getStatus(socketSwitch.getStatus1()), jack);
                break;
            case R.id.button3:
                jack = 2;
                setButtonClick(button3, false);
                data = socketSwitch.setSocketSwtich(getStatus(socketSwitch.getStatus2()), jack);
                break;
            case R.id.button4:
                jack = 3;
                setButtonClick(button4, false);
                data = socketSwitch.setSocketSwtich(getStatus(socketSwitch.getStatus3()), jack);
                break;
            case R.id.button5:
                jack = 4;
                setButtonClick(button5, false);
                data = socketSwitch.setSocketSwtich(getStatus(socketSwitch.getStatus4()), jack);
                break;
        }
        //这个进行设备数据的获取 参数分别是  IP  端口  数据  标识
        callBack.setDeviceControl(IP, PORT, MAC, data, jack);
    }

    private void setButtonClick(View view, boolean click) {
        view.setClickable(click);
        view.setEnabled(click);
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
        String json = FormatData.getByteToString((byte[]) objects[0], (int) objects[1]);
        if (socketSwitch.getSocketSwtichReuslt(json.substring(5, json.length()))) {
            SocketHandler(position, json.substring(5, json.length()), 0);
        }
    }


    /**
     * 控制界面的数据
     *
     * @param but
     * @param state
     */
    private void setButtonState(Button but, int state) {
        if (state == 0)
            but.setText("关");
        else
            but.setText("开");
    }

    /**
     * 超时
     *
     * @param position
     * @param error
     */
    @Override
    public void Error(int position, int error) {

    }

    /**
     * 这个是处理Http返回来的结果
     *
     * @param position 请求的表示
     * @param result   请求返回的结果
     */
    @Override
    public void handler(int position, String result) {
//        result = result.replace("\\" , "");
        if (socketSwitch.getSocketSwtichReuslt(result))
            SocketHandler(position, result, 1);
    }


    private void SocketHandler(int position, String data, int state) {
        if (position == 999) {
            socketSwitch.getMoudle(data, state);
            setButtonState(button2, socketSwitch.getStatus1());
            setButtonState(button3, socketSwitch.getStatus2());
            setButtonState(button4, socketSwitch.getStatus3());
            setButtonState(button5, socketSwitch.getStatus4());
        }
        if (position == 0) {
            setButtonClick(button, true);
            socketSwitch.setStatus1(getStatus(socketSwitch.getStatus0()));
            socketSwitch.setStatus2(getStatus(socketSwitch.getStatus0()));
            socketSwitch.setStatus3(getStatus(socketSwitch.getStatus0()));
            socketSwitch.setStatus4(getStatus(socketSwitch.getStatus0()));
            setButtonState(button2, socketSwitch.getStatus1());
            setButtonState(button3, socketSwitch.getStatus2());
            setButtonState(button4, socketSwitch.getStatus3());
            setButtonState(button5, socketSwitch.getStatus4());
        }
        if (position != 999 && position != 0) {
            switch (position) {
                case 1:
                    setButtonClick(button2, true);
                    setButtonState(button2, getStatus(socketSwitch.getStatus1()));
                    socketSwitch.setStatus1(getStatus(socketSwitch.getStatus1()));
                    break;
                case 2:
                    setButtonClick(button3, true);
                    setButtonState(button3, getStatus(socketSwitch.getStatus2()));
                    socketSwitch.setStatus2(getStatus(socketSwitch.getStatus2()));
                    break;
                case 3:
                    setButtonClick(button4, true);
                    setButtonState(button4, getStatus(socketSwitch.getStatus3()));
                    socketSwitch.setStatus3(getStatus(socketSwitch.getStatus3()));
                    break;
                case 4:
                    setButtonClick(button5, true);
                    setButtonState(button5, getStatus(socketSwitch.getStatus4()));
                    socketSwitch.setStatus4(getStatus(socketSwitch.getStatus4()));
                    break;
            }
        }
        //如果全部相同就是说明全部是关或者全部是开 ，如果有一个不相同那就是总开关就是关
        if (socketSwitch.getStatus1() == socketSwitch.getStatus2() && socketSwitch.getStatus2() == socketSwitch.getStatus3() && socketSwitch.getStatus3() == socketSwitch.getStatus4()) {
            if (socketSwitch.getStatus1() == ON) {
                setButtonState(button, ON);
                socketSwitch.setStatus0(ON);
            } else {
                setButtonState(button, UnOn);
                socketSwitch.setStatus0(UnOn);
            }
        } else {
            setButtonState(button, UnOn);
            socketSwitch.setStatus0(UnOn);
        }
    }

    /**
     * 控制开关
     *
     * @param status
     * @return
     */
    private int getStatus(int status) {
        if (status == 0)
            return 1;
        return 0;
    }
}
