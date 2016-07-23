package com.ruan.project.Other.HTTP;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/21.
 */
public class HttpData {

    /**
     * 将设备的Mac地址封装成字符串准备上传
     *
     * @param map
     * @return
     */
    public static String getUserDevice(ArrayList<Map<String, String>> map) {
        String data = "?appKey=123&appSecret=456&MAC=";

        for (int i = 0; i < map.size(); i++) {
            data += map.get(i).get("deviceMac");
            if (i < map.size() - 1)
                data += ",";
        }
        return data;
    }

    public static String getControlDevice(String Mac, String dataSource) {
        String data = "?appKey=123&appSecret=456&";

        data += "serviceID=" + Mac + "&";
        data += "dataSource=" + dataSource;

        return data;
    }

}
