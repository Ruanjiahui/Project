package com.ruan.project.Other.UDP;

import com.ruan.project.Moudle.UserDevice;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/21.
 */
public class FormatData {

    public static String getUDP(String data) {
        DecimalFormat df = new DecimalFormat("0000");
        return "P" + df.format(data.length()) + data;
    }

    public static String getHttp(String data) {
        return data;
    }


    /**
     * 这个将字节数组转换成字符串
     *
     * @param data
     * @param length
     * @return
     */
    public static String getByteToString(byte[] data, int length) {
        return new String(data, 0, length);
    }


    /**
     * 将数据对象封装成http请求的格式
     *
     * @param Mac
     * @param dataSource
     * @return
     */
    public static String getPOSTControlDevice(String Mac, String dataSource) {
        String data = "appKey=123&appSecret=456&";

        data += "serviceID=" + Mac + "&";
        data += "dataSource=" + dataSource;

        return data;
    }

    /**
     * 将数据对象封装成http请求的格式
     *
     * @param Mac
     * @param dataSource
     * @return
     */
    public static String getGETControlDevice(String Mac, String dataSource) {
        String data = "?appKey=123&appSecret=456&";

        data += "serviceID=" + Mac + "&";
        data += "dataSource=" + dataSource;

        return data;
    }


    /**
     * 将设备的Mac地址封装成字符串准备上传
     *
     * @param map
     * @return
     */
    public static String getHttpPOSTUserDevice(ArrayList<UserDevice> map) {
        String data = "appKey=123&appSecret=456&MAC=";

        for (int i = 0; i < map.size(); i++) {
            data += map.get(i).getDeviceMac();
            if (i < map.size() - 1)
                data += ",";
        }
        return data;
    }

    /**
     * 将设备的Mac地址封装成字符串准备上传
     *
     * @param map
     * @return
     */
    public static String getHttpGETUserDevice(ArrayList<UserDevice> map) {
        String data = "appKey=123&appSecret=456&MAC=";

        for (int i = 0; i < map.size(); i++) {
            data += map.get(i).getDeviceMac();
            if (i < map.size() - 1)
                data += ",";
        }
        return data;
    }

}
