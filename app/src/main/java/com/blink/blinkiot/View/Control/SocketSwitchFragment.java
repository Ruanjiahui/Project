package com.blink.blinkiot.View.Control;

import android.app.Activity;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blink.blinkiot.Other.DatabaseTableName;
import com.blink.blinkiot.View.Activity.DeviceControl;
import com.blink.blinkiot.View.DialogClick;
import com.blink.blinkiot.View.MyShareDialog;
import com.example.administrator.Interface.HttpInterface;
import com.example.administrator.data_sdk.Database.GetDatabaseData;
import com.example.administrator.data_sdk.JSON.JSONClass;
import com.blink.blinkiot.Controllar.CallBack;
import com.blink.blinkiot.Interface.UDPInterface;
import com.blink.blinkiot.Start.MainActivity;
import com.blink.blinkiot.Moudle.SocketSwitch;
import com.blink.blinkiot.Moudle.TimeMoudle;
import com.blink.blinkiot.Moudle.UserDevice;
import com.blink.blinkiot.Other.System.ReceiverAction;
import com.blink.blinkiot.Other.System.ReceiverHandler;
import com.blink.blinkiot.Other.UDP.FormatData;
import com.blink.blinkiot.R;
import com.blink.blinkiot.View.MyTimeDialog;
import com.example.administrator.data_sdk.SystemUtil.SystemTool;


/**
 * Created by Administrator on 2016/7/29.
 */
public class SocketSwitchFragment extends Fragment implements UDPInterface.HandlerMac, HttpInterface.HttpHandler, View.OnClickListener, ReceiverHandler.TimeHandler, DialogClick {

    private ImageView button;
    private ImageView button2;
    private ImageView button3;
    private ImageView button4;
    private ImageView button5;

    private TextView socket1Title = null;
    private TextView socket2Title = null;
    private TextView socket3Title = null;
    private TextView socket4Title = null;
    private ImageView socketPic1 = null;
    private ImageView socketPic2 = null;
    private ImageView socketPic3 = null;
    private ImageView socketPic4 = null;

    private ImageView switchCell2;
    private TextView switchTime2;
    private ImageView switchCell3;
    private TextView switchTime3;
    private ImageView switchCell4;
    private TextView switchTime4;
    private ImageView switchCell5;
    private TextView switchTime5;

    private RelativeLayout socketBack = null;

    public final static int FLAG1 = 1;
    public final static int FLAG2 = 2;
    public final static int FLAG3 = 3;
    public final static int FLAG4 = 4;

    public final static int FLAG1Pic = 10;
    public final static int FLAG2Pic = 20;
    public final static int FLAG3Pic = 30;
    public final static int FLAG4Pic = 40;

    private String FLAGData1 = null;
    private String FLAGData2 = null;
    private String FLAGData3 = null;
    private String FLAGData4 = null;
    private String SocketName = null;


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
    //设备的ID
    private UserDevice userDevice = null;

    //当前点击是哪个按钮
    private int ON = 1;
    private int UnOn = 0;

    private boolean STATUS;
    private String[] nameVules = null;


    /**
     * Activity传输Fragment
     *
     * @param userDevice
     * @return
     */
    public static Fragment getInstance(UserDevice userDevice, boolean isTrue) {
        SocketSwitchFragment socketSwitchFragment = new SocketSwitchFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", userDevice);
        bundle.putBoolean("flag", isTrue);
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
        socketBack = (RelativeLayout) view.findViewById(R.id.socketBack);
        socket1Title = (TextView) view.findViewById(R.id.socket1Title);
        socket2Title = (TextView) view.findViewById(R.id.socket2Title);
        socket3Title = (TextView) view.findViewById(R.id.socket3Title);
        socket4Title = (TextView) view.findViewById(R.id.socket4Title);
        socketPic1 = (ImageView) view.findViewById(R.id.socketPic1);
        socketPic2 = (ImageView) view.findViewById(R.id.socketPic2);
        socketPic3 = (ImageView) view.findViewById(R.id.socketPic3);
        socketPic4 = (ImageView) view.findViewById(R.id.socketPic4);

        //获取Activuity传输的数据
        userDevice = getArguments().getParcelable("data");
        STATUS = getArguments().getBoolean("flag");
        //插座控制
        socketSwitch = new SocketSwitch();
        data = socketSwitch.getSocketSwtich();

        ReceiverAction.USER_TIME = userDevice.getDeviceMac();
        //如果是真的设备才进行请求
        if (STATUS) {
            //获取设备的数据
            IP = userDevice.getDeviceIP();
            PORT = Integer.parseInt(userDevice.getDevicePORT());
            MAC = userDevice.getDeviceMac();
            //设置加载框
            socketBack.setVisibility(View.VISIBLE);
            //一进来马上获取一下设备的实现的数据对界面进行更新
            callBack = new CallBack(this, this);
            //这个进行设备数据的获取 参数分别是  IP  端口  数据  标识
            callBack.setDeviceControl(IP, PORT, MAC, data, 999, userDevice.getDeviceOnlineStatus());
        }

        switchCell2.setOnClickListener(this);
        switchCell3.setOnClickListener(this);
        switchCell4.setOnClickListener(this);
        switchCell5.setOnClickListener(this);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        socketPic1.setOnClickListener(this);
        socketPic2.setOnClickListener(this);
        socketPic3.setOnClickListener(this);
        socketPic4.setOnClickListener(this);

        setNormalTimeStyle();

        return view;
    }


    private void setNormalTimeStyle() {
        nameVules = new String[4];
        setTimeStyle(FLAG1, TimeMoudle.getSwitch1(), MainActivity.registerTime.getregisterTime(ReceiverAction.USER_TIME, FLAG1));
        setTimeStyle(FLAG2, TimeMoudle.getSwitch2(), MainActivity.registerTime.getregisterTime(ReceiverAction.USER_TIME, FLAG2));
        setTimeStyle(FLAG3, TimeMoudle.getSwitch3(), MainActivity.registerTime.getregisterTime(ReceiverAction.USER_TIME, FLAG3));
        setTimeStyle(FLAG4, TimeMoudle.getSwitch4(), MainActivity.registerTime.getregisterTime(ReceiverAction.USER_TIME, FLAG4));
        if (userDevice.getDeviceSocketFlag() == null)
            return;
        nameVules = userDevice.getDeviceSocketFlag().split(",");
        socket1Title.setText(nameVules[0]);
        socket2Title.setText(nameVules[1]);
        socket3Title.setText(nameVules[2]);
        socket4Title.setText(nameVules[3]);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (socketBack.getVisibility() == View.GONE) {
            switch (v.getId()) {
                case R.id.button:
                    setSocketData(0, getStatus(socketSwitch.getStatus0()), button, false);
                    break;
                case R.id.button2:
                    setSocketData(1, getStatus(socketSwitch.getStatus1()), button2, false);
                    break;
                case R.id.button3:
                    setSocketData(2, getStatus(socketSwitch.getStatus2()), button3, false);
                    break;
                case R.id.button4:
                    setSocketData(3, getStatus(socketSwitch.getStatus3()), button4, false);
                    break;
                case R.id.button5:
                    setSocketData(4, getStatus(socketSwitch.getStatus4()), button5, false);
                    break;
                case R.id.switchCell2:
                    setTime(FLAG1);
                    return;
                case R.id.switchCell3:
                    setTime(FLAG2);
                    return;
                case R.id.switchCell4:
                    setTime(FLAG3);
                    return;
                case R.id.switchCell5:
                    setTime(FLAG4);
                    return;
                case R.id.socketPic1:
                    if (nameVules[0] == null)
                        nameVules[0] = getResources().getString(R.string.SocketDialogText1);
                    showDialog(FLAG1Pic, nameVules[0]);
                    return;
                case R.id.socketPic2:
                    if (nameVules[1] == null)
                        nameVules[1] = getResources().getString(R.string.SocketDialogText2);
                    showDialog(FLAG2Pic, nameVules[1]);
                    return;
                case R.id.socketPic3:
                    if (nameVules[2] == null)
                        nameVules[2] = getResources().getString(R.string.SocketDialogText3);
                    showDialog(FLAG3Pic, nameVules[2]);
                    return;
                case R.id.socketPic4:
                    if (nameVules[3] == null)
                        nameVules[3] = getResources().getString(R.string.SocketDialogText4);
                    showDialog(FLAG4Pic, nameVules[3]);
                    return;
            }
        }
    }

    private MyShareDialog myShareDialog = null;

    /**
     * 显示弹出框并且设置写入数据库
     *
     * @param position 标志
     * @param data     输入框的数据
     */
    private void showDialog(int position, String data) {
        FLAGData1 = socket1Title.getText().toString();
        FLAGData2 = socket2Title.getText().toString();
        FLAGData3 = socket3Title.getText().toString();
        FLAGData4 = socket4Title.getText().toString();
        myShareDialog = new MyShareDialog(context, R.style.dialog);
        myShareDialog.DialogClick(this, position);
        myShareDialog.setEditHint(getResources().getString(R.string.SocketDialogHint));
        if (data != null)
            myShareDialog.setEditText(data);
        //如果是真实设置才允许设置名称
        if (STATUS) {
            myShareDialog.show();
        }
    }


    /**
     * 点击前的设置
     *
     * @param jack
     * @param status
     * @param view
     * @param isClick
     */
    private void setSocketData(int jack, int status, View view, boolean isClick) {
        //将所有按钮设置为锁定状态
        if (STATUS) {
            setButtonState(false);
            setButtonClick(view, isClick);
            data = socketSwitch.setSocketSwtich(status, jack);
            //这个进行设备数据的获取 参数分别是  IP  端口  数据  标识
            callBack.setDeviceControl(IP, PORT, MAC, data, jack, userDevice.getDeviceOnlineStatus());
        } else
            SocketHandler(jack, null);
    }

    /**
     * 设置闹铃 还是  取消闹铃
     *
     * @param FLAG
     */
    private void setTime(int FLAG) {
        if (!MainActivity.registerTime.getregisterTime(ReceiverAction.USER_TIME, FLAG)) {
            getMyDialog(FLAG);
        } else {
            MainActivity.registerTime.unreisterTime(ReceiverAction.USER_TIME, FLAG);
            //设置闹铃的样式
            setTimeStyle(FLAG, null, false);
            Toast.makeText(context, "定时取消", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 控制按钮的样式
     *
     * @param view
     * @param click
     */
    private void setButtonClick(View view, boolean click) {
        view.setClickable(click);
        view.setEnabled(click);
        if (!click)
            socketBack.setVisibility(View.VISIBLE);
        else
            socketBack.setVisibility(View.GONE);
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
        setButtonState(true);
        if (activity != null) {
            if (objects != null) {
                String json = FormatData.getByteToString((byte[]) objects[0], (int) objects[1]);
                SocketSwitch socket = (SocketSwitch) new JSONClass().setJSONToClassDeclared(context, socketSwitch, SocketSwitch.class, json.substring(5, json.length()));
                if (getObjectNull(socket)) {
                    Toast.makeText(context, getResources().getString(R.string.DeviceControlTie), Toast.LENGTH_SHORT).show();
                    return;
                }
                socketSwitch = socket;
                if ("setsocketswtich".equals(socketSwitch.getType()) || "getsocketswtich".equals(socketSwitch.getType()))
                    SocketHandler(position, socketSwitch.getJackArray());
            }
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
        if (position == 999)
            //将按钮设置为可以点击状态
            setButtonState(false);
        else
            setButtonState(true);
        socketBack.setVisibility(View.GONE);
    }

    /**
     * 设置按钮点击状态为允许点击
     */
    private void setButtonState(boolean click) {
        setButtonClick(button, click);
        setButtonClick(button2, click);
        setButtonClick(button3, click);
        setButtonClick(button4, click);
        setButtonClick(button5, click);
    }


    /**
     * 这个是处理Http返回来的结果
     *
     * @param position 请求的表示
     * @param result   请求返回的结果
     */
    @Override
    public void handler(int position, String result) {
        setButtonState(true);
        if (activity != null) {
            SocketSwitch socket = (SocketSwitch) new JSONClass().setJSONToClassDeclared(context, socketSwitch, SocketSwitch.class, result);
            if (getObjectNull(socket)) {
                Toast.makeText(context, getResources().getString(R.string.DeviceControlTie), Toast.LENGTH_SHORT).show();
                return;
            }
            socketSwitch = socket;
            socket = (SocketSwitch) new JSONClass().setJSONToClassDeclared(context, socketSwitch, SocketSwitch.class, socketSwitch.getJson());
            if (getObjectNull(socket)) {
                Toast.makeText(context, getResources().getString(R.string.DeviceControlTie), Toast.LENGTH_SHORT).show();
                return;
            }
            socketSwitch = socket;
            if ("setsocketswtich".equals(socketSwitch.getType()) || "getsocketswtich".equals(socketSwitch.getType()))
                SocketHandler(position, socketSwitch.getJackArray());
        }
    }

    private boolean getObjectNull(Object object) {
        if (object == null)
            return true;
        return false;
    }


    private void SocketHandler(int position, String data) {
        if (position == 999) {
            socketSwitch.getMoudle(data);
            setButtonState(button2, socketSwitch.getStatus1());
            setButtonState(button3, socketSwitch.getStatus2());
            setButtonState(button4, socketSwitch.getStatus3());
            setButtonState(button5, socketSwitch.getStatus4());
        }
        if (position == 0) {
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
                    setButtonState(button2, getStatus(socketSwitch.getStatus1()));
                    socketSwitch.setStatus1(getStatus(socketSwitch.getStatus1()));
                    break;
                case 2:
                    setButtonState(button3, getStatus(socketSwitch.getStatus2()));
                    socketSwitch.setStatus2(getStatus(socketSwitch.getStatus2()));
                    break;
                case 3:
                    setButtonState(button4, getStatus(socketSwitch.getStatus3()));
                    socketSwitch.setStatus3(getStatus(socketSwitch.getStatus3()));
                    break;
                case 4:
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
        //更改闹铃的样式
        setNormalTimeStyle();
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

    /**
     * 弹出对话框
     *
     * @param FLAG
     * @return
     */
    private String[] getMyDialog(int FLAG) {
        number = new String[2];
        myTimeDialog = new MyTimeDialog(context, R.style.dialog);
        myTimeDialog.DialogClick(this, FLAG);
        //如果是真实设置才允许设置闹铃
        if (STATUS)
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
     * 定时开始的操作发出指令
     * 这个进行设备数据的获取 参数分别是  IP  端口  数据  标识
     */
    @Override
    public void Start(int FLAG) {
        setDataStart(FLAG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity = null;
    }

    /**
     * 四个闹铃的操作
     *
     * @param FLAG
     */
    private void setDataStart(int FLAG) {
        switch (FLAG) {
            case FLAG1:
                data = socketSwitch.setSocketSwtich(getStatus(socketSwitch.getStatus1()), FLAG);
                break;
            case FLAG2:
                data = socketSwitch.setSocketSwtich(getStatus(socketSwitch.getStatus2()), FLAG);
                break;
            case FLAG3:
                data = socketSwitch.setSocketSwtich(getStatus(socketSwitch.getStatus3()), FLAG);
                break;
            case FLAG4:
                data = socketSwitch.setSocketSwtich(getStatus(socketSwitch.getStatus4()), FLAG);
                break;
        }
        //这个进行设备数据的获取 参数分别是  IP  端口  数据  标识
        callBack.setDeviceControl(IP, PORT, MAC, data, FLAG, userDevice.getDeviceOnlineStatus());
    }

    /**
     * 设置闹铃的样式
     */
    private void setTimeStyle(int FLAG, String[] number, boolean isVisiable) {
        if (number == null) {
            number = new String[2];
            number[0] = "--";
            number[1] = "--";
        }
        switch (FLAG) {
            case FLAG1:
                TimeMoudle.setSwitch1(number);
                switchTime2.setText(number[0] + ":" + number[1]);
                setSwitchDrawable(switchCell2, isVisiable);
                break;
            case FLAG2:
                TimeMoudle.setSwitch2(number);
                switchTime3.setText(number[0] + ":" + number[1]);
                setSwitchDrawable(switchCell3, isVisiable);
                break;
            case FLAG3:
                TimeMoudle.setSwitch3(number);
                switchTime4.setText(number[0] + ":" + number[1]);
                setSwitchDrawable(switchCell4, isVisiable);
                break;
            case FLAG4:
                TimeMoudle.setSwitch4(number);
                switchTime5.setText(number[0] + ":" + number[1]);
                setSwitchDrawable(switchCell5, isVisiable);
                break;
        }
    }


    @Override
    public void Enter(int FLAG) {
        if (FLAG == FLAG1 || FLAG == FLAG2 || FLAG == FLAG3 || FLAG == FLAG4) {
            number = myTimeDialog.getNumber();
            //注册时间  注册开始监听
            MainActivity.registerTime.registerTime(number, ReceiverAction.USER_TIME, FLAG, this);
            setTimeStyle(FLAG, number, true);
            myTimeDialog.dismiss();
        } else {
            SocketName = myShareDialog.getEditText();
            setSocketName(FLAG, SocketName);
            //将名称设置到数据库
            myShareDialog.dismiss();
        }
    }

    @Override
    public void Cancal(int FLAG) {
        if (FLAG == FLAG1 || FLAG == FLAG2 || FLAG == FLAG3 || FLAG == FLAG4) {
            myTimeDialog.dismiss();
        } else {
            myShareDialog.dismiss();
        }
    }

    /**
     * 设置每个插孔的名称
     *
     * @param FLAG
     * @param data
     */
    private void setSocketName(int FLAG, String data) {
        switch (FLAG) {
            case FLAG1Pic:
                socket1Title.setText(data);
                FLAGData1 = data;
                break;
            case FLAG2Pic:
                socket2Title.setText(data);
                FLAGData2 = data;
                break;
            case FLAG3Pic:
                socket3Title.setText(data);
                FLAGData3 = data;
                break;
            case FLAG4Pic:
                socket4Title.setText(data);
                FLAGData4 = data;
                break;
        }
        String name = FLAGData1 + "," + FLAGData2 + "," + FLAGData3 + "," + FLAGData4;
        //将数据更新到数据库
        ContentValues contentValues = new ContentValues();
        contentValues.put("deviceSocketFlag", name);
        new GetDatabaseData().Update(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, contentValues, "deviceID = ?", new String[]{userDevice.getDeviceID()});
    }
}
