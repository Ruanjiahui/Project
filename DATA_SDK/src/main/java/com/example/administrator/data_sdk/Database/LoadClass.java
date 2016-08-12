package com.example.administrator.data_sdk.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/1.
 */
public abstract class LoadClass {


    /**
     * 将数据库Cursor游标封装成对象(这个获取对象的属性包括父类)
     *
     * @param cursor    数据库的游标（保存数据库的数据）
     * @param loadclass 封装的类的对象
     * @return
     */
    protected abstract ArrayList<Object> AnalysisCursors(Cursor cursor, Class loadclass);


    /**
     * 将数据库Cursor游标封装成对象(这个获取对象的属性不包括父类)
     *
     * @param cursor    数据库的游标（保存数据库的数据）
     * @param loadclass 封装的类的对象
     * @return
     */
    protected abstract ArrayList<Object> AnalysisCursor(Cursor cursor, Class loadclass);

    /**
     * 将对象的数据封装成可以插进数据库的数据(包括父类)
     *
     * @param loadClass 封装的类对象
     * @param objects   类对象实体类
     * @return
     */
    protected abstract ContentValues getContentValues(Class loadClass, Object objects);


    /**
     * 将对象的数据封装成可以插进数据库的数据(不包括父类)
     *
     * @param loadClass 封装的类对象
     * @param objects   类对象实体类
     * @return
     */
    protected abstract ContentValues getContentValue(Class loadClass, Object objects);


    /**
     * 将Map集合解析封装成对象
     *
     * @param map
     * @param loadClass
     * @return
     */
    protected abstract ArrayList<Object> AnalysisMap(ArrayList<Map<Object, Object>> map, Class loadClass);


}
