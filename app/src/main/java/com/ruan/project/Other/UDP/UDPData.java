package com.ruan.project.Other.UDP;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.Format;

/**
 * Created by Administrator on 2016/7/21.
 */
public class UDPData {

    private JSONObject json = null;

    /**
     * 封装获取设备颜色json格式
     * @return
     */
    public String getRGBColor() {
        json = new JSONObject();
        try {
            json.put("type", "getRGBColor");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getLength(json + "");
    }

    /**
     * 设置设备的灯光颜色，
     * @param RED           红色值
     * @param BLUE          蓝色值
     * @param GREEN         绿色值
     * @param ALPHA         渐变值
     * @return
     */
    public String setRGBColor(String RED , String BLUE , String GREEN , String ALPHA){
        json = new JSONObject();
        try {
            json.put("type", "setRGBColor");
            json.put("red", RED);
            json.put("blue", BLUE);
            json.put("green", GREEN);
            json.put("alpha", ALPHA);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getLength(json + "");
    }


    private String getLength(String data){
        DecimalFormat df = new DecimalFormat("0000");
        return "P" + df.format(data.length()) + data;
    }

}
