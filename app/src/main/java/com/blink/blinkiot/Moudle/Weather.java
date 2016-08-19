package com.blink.blinkiot.Moudle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/10.
 */
public class Weather {

    private String[] tmp = null;
    private boolean isfirst = false;

    public void setJson(String json) {
        if (!isfirst && json != null) {
            isfirst = true;
            tmp = new String[2];
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray array = jsonObject.getJSONArray("HeWeather data service 3.0");
                jsonObject = (JSONObject) array.get(0);
                array = jsonObject.getJSONArray("daily_forecast");
                jsonObject = (JSONObject) array.get(0);
                jsonObject = new JSONObject(jsonObject.getString("tmp"));
                tmp[0] = jsonObject.getString("min");
                tmp[1] = jsonObject.getString("max");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String[] getWeather() {
        return tmp;
    }
}
