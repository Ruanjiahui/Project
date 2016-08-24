package com.blink.blinkiot.Controllar;

import android.content.ContentValues;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.administrator.Interface.HttpInterface;
import com.example.administrator.data_sdk.Database.GetDatabaseData;
import com.example.administrator.http_sdk.HTTP;
import com.blink.blinkiot.Interface.DataHandler;
import com.blink.blinkiot.Interface.UDPInterface;
import com.blink.blinkiot.Start.MainActivity;
import com.blink.blinkiot.Moudle.CheckMac;
import com.blink.blinkiot.Moudle.UserDevice;
import com.blink.blinkiot.Other.DatabaseTableName;
import com.blink.blinkiot.Other.DeviceCode;
import com.blink.blinkiot.Other.HTTP.HttpURL;
import com.blink.blinkiot.Other.System.NetWork;
import com.blink.blinkiot.Other.UDP.FormatData;
import com.blink.blinkiot.Other.UDP.OnlineDeveice;
import com.blink.blinkiot.R;
import com.blink.blinkiot.View.Fragment.Fragment1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/21.
 * <p/>
 * 检测在不在线分两种方式，
 * 第一种是局域网的 udp单播
 * 第二种是通过云端 判断
 */
public class CheckOnline implements UDPInterface.HandlerMac, HttpInterface.HttpHandler {

    private Context context = null;
    private DataHandler dataHandler = null;
    private FragmentManager fragmentManager = null;
    private UserDevice userDevice = null;


    private ArrayList<Object> ListObj = null;

    private String Online = "deviceOnline";
    private String OnlineStatus = "deviceOnlineStatus";
    private String IPs = "deviceIP";
    private String PORTs = "devicePORT";
    private String Mac = "deviceMac";
    private int[] isUser = null;
    //发出请求总数
    private int count = 0;
    private int total = 0;

    private int NORMAL = 0;
    private int UnNORMAL = 1;


    public CheckOnline(Context context, FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.context = context;
        ListObj = FragmentControl.getUserDeviceData(context);
        total = ListObj.size();
//        userDevice = new UserDevice();
//        map = userDevice.getMoudle();
    }

    public CheckOnline(Context context, DataHandler dataHandler) {
        this.context = context;
        this.dataHandler = dataHandler;
        ListObj = FragmentControl.getUserDeviceData(context);
        total = ListObj.size();
//        userDevice = new UserDevice();
//        map = userDevice.getMoudle(FragmentControl.getUserDeviceData(context));
    }

    /**
     * 这个方式通过udp单播进行设备检测是否在线
     */
    public void UDPCheck() {
        UDPCheck(this);
    }


    public void HTTPCheck(String Mac) {
        new HTTP(this, HttpURL.CheckOnline, FormatData.getHttpPOSTUserDevice(Mac), 1);
    }

    /**
     * 这个方式通过http云端请求进行设备检测是否在线
     */
    public void HTTPCheck() {
        //将所有的设备设置为不在线
        setUnOnline();
        if (ListObj != null && ListObj.size() != 0)
            new HTTP(this, HttpURL.CheckOnline, FormatData.getHttpPOSTUserDevice(ListObj), 0);
        ReData(UnNORMAL);
    }


    /**
     * 内网检测设备是否在线
     *
     * @param handlerMac
     */
    public void UDPCheck(UDPInterface.HandlerMac handlerMac) {
        //将所有的设备设置为不在线
        setUnOnline();
        isUser = new int[ListObj.size()];
        for (int i = 0; i < ListObj.size(); i++) {
            isUser[i] = 0;
            UserDevice userDevice = (UserDevice) ListObj.get(i);
            new OnlineDeveice().Check(i, userDevice.getDeviceIP(), Integer.parseInt(userDevice.getDevicePORT()), userDevice.getDeviceMac(), handlerMac, 1);
            //计时器，广播没一秒发送一次，总共发送1次
        }
        ReData(UnNORMAL);
    }

    /**
     * 设备不在线
     */
    public void DeviceUnOnline() {
        setUnOnline();
        ReData(NORMAL);
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
        isUser[position] = 1;
        UserDevice userDevice = (UserDevice) ListObj.get(position);
        if (objects != null) {
            if (objects[0] != null) {
                HttpURL.STATE = NetWork.WIFI;
//                for (int i = 0; i < ListObj.size(); i++) {
//                    if (position == i) {
//                        UserDevice userDevice = (UserDevice) ListObj.get(position);
                count++;
                setOnline(userDevice.getDeviceMac(), DeviceCode.WIFI, (String) objects[2], (int) objects[3]);
//                    }
//                }
                if (total == count)
                    ReData(NORMAL);
            } else {
//                UserDevice userDevice = (UserDevice) ListObj.get(position);
                HTTPCheck(userDevice.getDeviceMac());
            }
        } else {
//            UserDevice userDevice = (UserDevice) ListObj.get(position);
            HTTPCheck(userDevice.getDeviceMac());
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
        if (isUser[position] == 0) {
            UserDevice userDevice = (UserDevice) ListObj.get(position);
            HTTPCheck(userDevice.getDeviceMac());
        }
    }

    private void setOnline(String mac, String status, String IP, int PORT) {
        ContentValues contentValues = new ContentValues();
        //2代表在线1代表不在线
        contentValues.put(Online, DeviceCode.ONLINE);
        contentValues.put(OnlineStatus, status);
        contentValues.put(IPs, IP);
        contentValues.put(PORTs, PORT);
        //判断在线之后将在线状态修改到数据库
        new GetDatabaseData().Update(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, contentValues, Mac + " = ?", new String[]{mac});
    }

    private void setUnOnline() {
        //首先将所有设备登录状态设置为不在线
        ContentValues contentValues = new ContentValues();
        //2代表在线1代表不在线
        contentValues.put(Online, DeviceCode.UNONLINE);
        new GetDatabaseData().Update(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, contentValues, "", null);
    }

    /**
     * 这个是处理Http返回来的结果
     *
     * @param position 请求的表示
     * @param result   请求返回的结果
     */
    @Override
    public void handler(int position, String result) {
        if (result != null && result.length() > 0) {
            CheckMac checkMac = getJSON(result);
            if (checkMac != null && checkMac.getList() != null) {
                //如果返回数据里面有该设备就设置为在线状态
                for (int i = 0; i < checkMac.getList().size(); i++) {
                    for (int r = 0; r < ListObj.size(); r++) {
                        UserDevice userDevice = (UserDevice) ListObj.get(r);
                        if (checkMac.getList().get(i).toLowerCase().equals(userDevice.getDeviceMac().toLowerCase())) {
                            setOnline(userDevice.getDeviceMac(), DeviceCode.CLOUD, userDevice.getDeviceIP(), Integer.parseInt(userDevice.getDevicePORT()));
                            break;
                        }
                    }
                }
            }
        }
        count++;
        if (total == count)
            ReData(NORMAL);
    }

    private void ReData(int FLAG) {
        if (fragmentManager != null) {
            if (MainActivity.isRefresh)
                intentFragment(fragmentManager);
        } else if (dataHandler != null)
            dataHandler.ReStartData(FLAG);
    }

    /**
     * 跳转Fragment
     *
     * @param fragmentManager
     */
    private void intentFragment(FragmentManager fragmentManager) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.content, new Fragment1(), "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commitAllowingStateLoss();
    }


    private CheckMac getJSON(String result) {
        CheckMac checkMac = new CheckMac();
        try {
            JSONObject jsonObject = new JSONObject(result);
            checkMac.setCode(jsonObject.getInt("code"));
            JSONArray array = jsonObject.getJSONArray("list");
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++)
                list.add(array.getString(i));
            checkMac.setList(list);
        } catch (JSONException e) {
            return null;
        }
        return checkMac;
    }
}
