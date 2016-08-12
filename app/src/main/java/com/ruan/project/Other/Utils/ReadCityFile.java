package com.ruan.project.Other.Utils;

import com.ruan.project.Moudle.Sort;
import com.ruan.project.View.Activity.City;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/9.
 */
public class ReadCityFile {

    /**
     * 读取raw文件的city的数据
     *
     * @param inputStream
     * @return
     */
    public static ArrayList<Sort> readCity(InputStream inputStream) {
        ArrayList<Sort> list = new ArrayList<>();
        StringBuffer sb = null;
        try {
            InputStream in = inputStream;

            InputStreamReader inputStreamReader = new InputStreamReader(in, "utf-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            sb = new StringBuffer();
            int line;

            while ((line = reader.read()) != -1) {
                if (line == 13 || line == 10) {
                    if (line == 10)
                        continue;
                    Sort sort = new Sort();
                    sort.setCityName(sb + "");
                    list.add(sort);
                    sb.delete(0, sb.length());
                    continue;
                }
                if (line == 65279)
                    continue;
                sb.append((char) line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
