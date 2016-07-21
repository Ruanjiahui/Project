package com.ruan.project.Controllar;

import android.content.ContentValues;
import android.content.Context;

import com.example.administrator.data_sdk.Database.GetDatabaseData;
import com.ruan.project.Interface.UDPInterface;
import com.ruan.project.Other.DatabaseTableName;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/21.
 * <p/>
 * 检测在不在线分两种方式，
 * 第一种是局域网的 udp单播
 * 第二种是通过云端 判断
 */
public class CheckOnline implements UDPInterface.HandlerMac {

    private ArrayList<Map<String, String>> map = null;
    private Context context = null;


    public CheckOnline(Context context) {
        this.context = context;
    }

    /**
     * 这个方式通过udp单播进行设备检测是否在线
     */
    public void UDPCheck() {
        map = new UdpOpera(context).CheckOnline(this);
    }

    /**
     * 这个方式通过http云端请求进行设备检测是否在线
     */
    public void HTTPCheck(){

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
        for (int i = 0; i < map.size(); i++) {
            if (position == i && objects[0] != null) {
                ContentValues contentValues = new ContentValues();
                //2代表在线1代表不在线
                contentValues.put("deviceOnline", "2");
                //判断在线之后将在线状态修改到数据库
                new GetDatabaseData().Update(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, contentValues, "deviceMac = ?", new String[]{map.get(position).get("deviceMac")});
            }
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
        ContentValues contentValues = new ContentValues();
        //2代表在线1代表不在线
        contentValues.put("deviceOnline", "1");
        //判断在线之后将在线状态修改到数据库
        new GetDatabaseData().Update(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, contentValues, "deviceMac = ?", new String[]{map.get(position).get("deviceMac")});
    }
}
