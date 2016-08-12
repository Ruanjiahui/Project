package com.ruan.project.View.Control;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.Interface.HttpInterface;
import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.JSON.JSONClass;
import com.example.ruan.udp_sdk.TimerHandler;
import com.ruan.project.Controllar.CallBack;
import com.ruan.project.Interface.UDPInterface;
import com.ruan.project.MainActivity;
import com.ruan.project.Moudle.SocketSwitch;
import com.ruan.project.Moudle.TimeMoudle;
import com.ruan.project.Moudle.UserDevice;
import com.ruan.project.Other.System.ReceiverAction;
import com.ruan.project.Other.System.ReceiverHandler;
import com.ruan.project.Other.UDP.FormatData;
import com.ruan.project.R;
import com.ruan.project.View.MyTimeDialog;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Administrator on 2016/7/29.
 */
public class SocketSwitchFragment extends Fragment implements UDPInterface.HandlerMac, HttpInterface.HttpHandler, View.OnClickListener, ReceiverHandler.TimeHandler {

    private ImageView button;
    private ImageView button2;
    private ImageView button3;
    private ImageView button4;
    private ImageView button5;

    private ImageView switchCell2;
    private TextView switchTime2;
    private ImageView switchCell3;
    private TextView switchTime3;
    private ImageView switchCell4;
    private TextView switchTime4;
    private ImageView switchCell5;
    private TextView switchTime5;

    public final static int FLAG1 = 001;
    public final static int FLAG2 = 002;
    public final static int FLAG3 = 003;
    public final static int FLAG4 = 004;


    private View view = null;
    private Context context = null;
    private Activity activity = null;
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
    private int jack = 999;
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

        context = getActivity();
        activity = (Activity) context;

        switchCell2 = (ImageView) view.findViewById(R.id.switchCell2);
        switchTime2 = (TextView) view.findViewById(R.id.switchTime2);
        switchCell3 = (ImageView) view.findViewById(R.id.switchCell3);
        switchTime3 = (TextView) view.findViewById(R.id.switchTime3);
        switchCell4 = (ImageView) view.findViewById(R.id.switchCell4);
        switchTime4 = (TextView) view.findViewById(R.id.switchTime4);
        switchCell5 = (ImageView) view.findViewById(R.id.switchCell5);
        switchTime5 = (TextView) view.findViewById(R.id.switchTime5);
        button = (ImageView) view.findViewById(R.id.button);
        button2 = (ImageView) view.findViewById(R.id.button2);
        button3 = (ImageView) view.findViewById(R.id.button3);
        button4 = (ImageView) view.findViewById(R.id.button4);
        button5 = (ImageView) view.findViewById(R.id.button5);

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
        callBack.setDeviceControl(IP, PORT, MAC, data, jack);


        switchCell2.setOnClickListener(this);
        switchCell3.setOnClickListener(this);
        switchCell4.setOnClickListener(this);
        switchCell5.setOnClickListener(this);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);

        setTimeStyle();


        return view;
    }


    private void setTimeStyle() {
        switchTime2.setText(TimeMoudle.getSwitch1()[0] + ":" + TimeMoudle.getSwitch1()[1]);
        switchTime3.setText(TimeMoudle.getSwitch2()[0] + ":" + TimeMoudle.getSwitch2()[1]);
        switchTime4.setText(TimeMoudle.getSwitch3()[0] + ":" + TimeMoudle.getSwitch3()[1]);
        switchTime5.setText(TimeMoudle.getSwitch4()[0] + ":" + TimeMoudle.getSwitch4()[1]);
        setSwitchDrawable(switchCell2, MainActivity.registerTime.getregisterTime(ReceiverAction.USER_TIME, FLAG1));
        setSwitchDrawable(switchCell3, MainActivity.registerTime.getregisterTime(ReceiverAction.USER_TIME, FLAG2));
        setSwitchDrawable(switchCell4, MainActivity.registerTime.getregisterTime(ReceiverAction.USER_TIME, FLAG3));
        setSwitchDrawable(switchCell5, MainActivity.registerTime.getregisterTime(ReceiverAction.USER_TIME, FLAG4));
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
            case R.id.switchCell2:
                jack = 1;
                data = socketSwitch.setSocketSwtich(getStatus(socketSwitch.getStatus1()), jack);
                getMyDialog(FLAG1);
                return;
            case R.id.switchCell3:
                jack = 2;
                data = socketSwitch.setSocketSwtich(getStatus(socketSwitch.getStatus2()), jack);
                getMyDialog(FLAG2);
                return;
            case R.id.switchCell4:
                jack = 3;
                data = socketSwitch.setSocketSwtich(getStatus(socketSwitch.getStatus3()), jack);
                getMyDialog(FLAG3);
                return;
            case R.id.switchCell5:
                jack = 4;
                data = socketSwitch.setSocketSwtich(getStatus(socketSwitch.getStatus4()), jack);
                getMyDialog(FLAG4);
                return;
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
        if (activity != null) {
            String json = FormatData.getByteToString((byte[]) objects[0], (int) objects[1]);
            socketSwitch = (SocketSwitch) new JSONClass().setJSONToClassDeclared(context, socketSwitch, SocketSwitch.class, json.substring(5, json.length()));
            SocketHandler(position, socketSwitch.getJackArray(), 0);
        }
    }


    /**
     * 控制界面的数据
     *
     * @param but
     * @param state
     */
    private void setButtonState(ImageView but, int state) {
        if (state == 0)
            but.setImageDrawable(getResources().getDrawable(R.mipmap.icon_close));
        else
            but.setImageDrawable(getResources().getDrawable(R.mipmap.icon_open));
    }

    /**
     * 超时
     *
     * @param position
     * @param error
     */
    @Override
    public void Error(int position, int error) {
        Toast.makeText(context, "操作超时", Toast.LENGTH_SHORT).show();
    }


    /**
     * 这个是处理Http返回来的结果
     *
     * @param position 请求的表示
     * @param result   请求返回的结果
     */
    @Override
    public void handler(int position, String result) {
        if (activity != null) {
            socketSwitch = (SocketSwitch) new JSONClass().setJSONToClassDeclared(context, socketSwitch, SocketSwitch.class, result);
            socketSwitch = (SocketSwitch) new JSONClass().setJSONToClassDeclared(context, socketSwitch, SocketSwitch.class, socketSwitch.getJson());
            SocketHandler(position, socketSwitch.getJackArray(), 1);
        }
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

        setTimeStyle();
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

    private String[] number = null;
    private MyTimeDialog myTimeDialog = null;
    private boolean registerState = false;

    private String[] getMyDialog(int position) {
        number = new String[2];
        myTimeDialog = new MyTimeDialog(context, R.style.dialog);
        myTimeDialog.DialogClick(new MyTimeDialog.MyTimeClick() {
            @Override
            public void Enter(int position) {
                number = myTimeDialog.getNumber();
                //获取当前时间监听的状态
                registerState = MainActivity.registerTime.getregisterTime(ReceiverAction.USER_TIME, position);
                //开始监听时间
                startTime(position);
                if (!registerState) {
                    //获取当前时间监听的状态
                    registerState = MainActivity.registerTime.getregisterTime(ReceiverAction.USER_TIME, position);
                    switch (position) {
                        case FLAG1:
                            TimeMoudle.setSwitch1(number);
                            switchTime2.setText(number[0] + ":" + number[1]);
                            setSwitchDrawable(switchCell2, registerState);
                            break;
                        case FLAG2:
                            TimeMoudle.setSwitch2(number);
                            switchTime3.setText(number[0] + ":" + number[1]);
                            setSwitchDrawable(switchCell3, registerState);
                            break;
                        case FLAG3:
                            TimeMoudle.setSwitch3(number);
                            switchTime4.setText(number[0] + ":" + number[1]);
                            setSwitchDrawable(switchCell4, registerState);
                            break;
                        case FLAG4:
                            TimeMoudle.setSwitch4(number);
                            switchTime5.setText(number[0] + ":" + number[1]);
                            setSwitchDrawable(switchCell5, registerState);
                            break;
                    }
                    myTimeDialog.dismiss();
                }

            }

            @Override
            public void Cancal(int position) {
                myTimeDialog.dismiss();
            }
        }, position);
        myTimeDialog.show();
        return number;
    }

    private void setSwitchDrawable(ImageView view, boolean isCheck) {
        if (isCheck)
            view.setImageDrawable(getResources().getDrawable(R.mipmap.cell2));
        else
            view.setImageDrawable(getResources().getDrawable(R.mipmap.cell3));
    }

    /**
     * 定时开始的操作
     *
     * @param position
     */
    private void startTime(int position) {
        ReceiverAction.USER_TIME = userDevice.getDeviceMac();
        if (!registerState)
            MainActivity.registerTime.registerTime(number, ReceiverAction.USER_TIME, position, this, IP, PORT, MAC, data, jack);
        else {
            Toast.makeText(context, "定时已取消", Toast.LENGTH_SHORT).show();
            MainActivity.registerTime.unreisterTime(ReceiverAction.USER_TIME, position);
        }

    }


    /**
     * 定时开始的操作发出指令
     * 这个进行设备数据的获取 参数分别是  IP  端口  数据  标识
     *
     * @param IP
     * @param PORT
     * @param MAC
     * @param data
     * @param jack
     */
    @Override
    public void Start(String IP, int PORT, String MAC, String data, int jack) {
        //这个进行设备数据的获取 参数分别是  IP  端口  数据  标识
        callBack.setDeviceControl(IP, PORT, MAC, data, jack);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity = null;
    }
}
