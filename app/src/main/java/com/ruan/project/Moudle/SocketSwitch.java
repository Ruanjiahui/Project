package com.ruan.project.Moudle;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/7/29.
 */
public class SocketSwitch extends Switch {

    private String type = null;
    private int status = 0;
    private int jack = 0;
    private String product = null;
    private int result = 0;
    private String jackArray = null;


    public String getJackArray() {
        return jackArray;
    }

    public void setJackArray(String jackArray) {
        this.jackArray = jackArray;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getJack() {
        return jack;
    }

    public void setJack(int jack) {
        this.jack = jack;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    //jack表示插座的接口标识  ， status 表示当前状态
    private int jack0 = 0;
    private int status0 = 0;
    private int jack1 = 0;
    private int status1 = 0;
    private int jack2 = 0;
    private int status2 = 0;
    private int jack3 = 0;
    private int status3 = 0;
    private int jack4 = 0;
    private int status4 = 0;

    public int getJack0() {
        return jack0;
    }

    public void setJack0(int jack0) {
        this.jack0 = jack0;
    }

    public int getStatus0() {
        return status0;
    }

    public void setStatus0(int status0) {
        this.status0 = status0;
    }

    public int getJack1() {
        return jack1;
    }

    public void setJack1(int jack1) {
        this.jack1 = jack1;
    }

    public int getStatus1() {
        return status1;
    }

    public void setStatus1(int status1) {
        this.status1 = status1;
    }

    public int getJack2() {
        return jack2;
    }

    public void setJack2(int jack2) {
        this.jack2 = jack2;
    }

    public int getStatus2() {
        return status2;
    }

    public void setStatus2(int status2) {
        this.status2 = status2;
    }

    public int getJack3() {
        return jack3;
    }

    public void setJack3(int jack3) {
        this.jack3 = jack3;
    }

    public int getStatus3() {
        return status3;
    }

    public void setStatus3(int status3) {
        this.status3 = status3;
    }

    public int getJack4() {
        return jack4;
    }

    public void setJack4(int jack4) {
        this.jack4 = jack4;
    }

    public int getStatus4() {
        return status4;
    }

    public void setStatus4(int status4) {
        this.status4 = status4;
    }

    /**
     * 将json格式的数据解析成SocketSwitch对象
     *
     * @param json
     * @return
     */
    public void getMoudle(String json, int position) {

        //    {“type”:”getsocketswtich”,”jackArray”:[{”jack”:1,”status”:1}，{”jack”:2,”status”:0}，{”jack”:3,”status”:0}，{”jack”:4,”status”:1}]}
        try {
            if (position == 1)
                json = new JSONObject(json).getString("json");
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("jackArray");

            JSONObject obj1 = array.getJSONObject(0);
            setJack1(obj1.getInt("jack"));
            setStatus1(obj1.getInt("status"));

            JSONObject obj2 = array.getJSONObject(1);
            setJack2(obj2.getInt("jack"));
            setStatus2(obj2.getInt("status"));

            JSONObject obj3 = array.getJSONObject(2);
            setJack3(obj3.getInt("jack"));
            setStatus3(obj3.getInt("status"));

            JSONObject obj4 = array.getJSONObject(3);
            setJack4(obj4.getInt("jack"));
            setStatus4(obj4.getInt("status"));
        } catch (JSONException e) {
//            e.printStackTrace();
            Log.e("Ruan SocketSwitch", "该数据不是json格式" + e);
        }
    }

    /**
     * 将SocketSwitch对象封装成json格式的数据
     *
     * @return
     */
    public String setSocketSwtich(int status, int jack) {
        SocketSwitch socketSwitch = new SocketSwitch();
        socketSwitch.setProduct("PLUG");
        socketSwitch.setType("setsocketswtich");
        socketSwitch.setStatus(status);
        socketSwitch.setJack(jack);
        Gson gson = new Gson();
        return gson.toJson(socketSwitch);
    }


    /**
     * 将SocketSwitch对象封装成json格式的数据
     *
     * @return
     */
    public String getSocketSwtich() {
//        {”product”:”PLUG”,“type”:”getsocketswitch”}
        SocketSwitch socketSwitch = new SocketSwitch();
        socketSwitch.setProduct("PLUG");
        socketSwitch.setType("getsocketswtich");
        Gson gson = new Gson();
        return gson.toJson(socketSwitch);
    }

    /**
     * 判断控制开关的是否成功
     *
     * @param json
     * @return <>true</>成功<>false</>失败
     */
    public boolean getSocketSwtichReuslt(String json) {
        try {
            if (json != null) {
                JSONObject obj = new JSONObject(json);
                if (obj.getString("type").equals("setsocketswtich") && obj.getInt("result") == 0 || obj.getString("type").equals("getsocketswtich"))
                    return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
