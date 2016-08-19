package com.blink.blinkiot.Moudle;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/7/29.
 */
public class REGlight {

    private double Red = 0;
    private double Green = 0;
    private double Blue = 0;
    private double Aphla = 0;


    public double getRed() {
        return Red;
    }

    public void setRed(double red) {
        Red = red;
    }

    public double getGreen() {
        return Green;
    }

    public void setGreen(double green) {
        Green = green;
    }

    public double getBlue() {
        return Blue;
    }

    public void setBlue(double blue) {
        Blue = blue;
    }

    public double getAphla() {
        return Aphla;
    }

    public void setAphla(double aphla) {
        Aphla = aphla;
    }

    /**
     * 获取RGB对象的json格式的数据
     *
     * @return
     */
    public String setRGBColor() {
        JSONObject json = new JSONObject();
        try {
            json.put("type", "setRGBColor");
            json.put("red", getRed());
            json.put("blue", getBlue());
            json.put("green", getGreen());
            json.put("alpha", getAphla());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    /**
     * 获取json格式的数据
     *
     * @return
     */
    public String getRGBColor() {
        JSONObject json = new JSONObject();
        try {
            json.put("type", "getRGBColor");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    /**
     * 将json结构数据解析成对象
     *
     * @param json json格式的数据
     * @return
     */
    public REGlight getMoudle(String json) {
//        {"type":"getRGBColor","success":0,"red":"1.000000","blue":"1.000000","green":"1.000000","alpha":"1.000000"}
        try {
            JSONObject object = new JSONObject(json);
            setRed(Double.valueOf(object.getString("red")));
            setBlue(Double.valueOf(object.getString("blue")));
            setGreen(Double.valueOf(object.getString("green")));
            setAphla(Double.valueOf(object.getString("alpha")));
        } catch (JSONException e) {
            Log.e("Ruan REGlight", "该数据不是json格式");
        }
        return this;
    }
}
