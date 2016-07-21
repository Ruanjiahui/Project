package com.ruan.project.Other.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/21.
 */
public class jsonMac {


    /**
     * 解析http云端数据
     * @param data
     * @return
     */
    public static ArrayList<String> getMacArrays(String data) {
        ArrayList<String> list = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(data);
            JSONArray array = json.getJSONArray("list");
            for (int i = 0; i < array.length(); i++) {
                list.add(array.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

}
