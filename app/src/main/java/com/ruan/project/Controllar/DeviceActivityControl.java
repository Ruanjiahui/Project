package com.ruan.project.Controllar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.ui_sdk.DensityUtil;
import com.ruan.project.Moudle.Device;
import com.ruan.project.Moudle.Item;
import com.ruan.project.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/1.
 */
public class DeviceActivityControl {

    private Context context = null;

    public DeviceActivityControl(Context context) {
        this.context = context;
    }

    /**
     * 这个是设置设备列表的样式     这个是不同类别的(添加设备)
     *
     * @param ListObj
     * @return
     */
    public ArrayList<Object> setDeviceTypeList(ArrayList<Object> ListObj) {
        Device device = null;
        ArrayList<Object> list = new ArrayList<>();
        for (int i = 0; i < ListObj.size(); i++) {
            device = (Device) ListObj.get(i);
            list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.cooker), device.getDeviceType(), ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), DensityUtil.dip2px(context, 60), device.getDeviceTypeID()));
        }
        return list;
    }

    /**
     * 这个也是设置设备列表的样式，这个同一类别的(添加设备下一级菜单)
     *
     * @param ListObj
     * @return
     */
    public ArrayList<Object> setDeviceList(ArrayList<Object> ListObj) {
        Device device = null;
        ArrayList<Object> list = new ArrayList<>();
        for (int i = 0; i < ListObj.size(); i++) {
            device = (Device) ListObj.get(i);
            list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.cooker), device.getDeviceType(), device.getDeviceModel(), ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), DensityUtil.dip2px(context, 60)));
        }
        return list;
    }


    /**
     * 这个是设置配置方式样式     （Airkiss，蓝牙，wifi，等等）
     *
     * @return
     */
    public ArrayList<Object> setNetWorkSettingList() {
        ArrayList<Object> list = new ArrayList<>();
        String[] setting = new String[]{"WIFI", "二维码", "蓝牙", "Airkiss"};
        for (int i = 0; i < setting.length; i++) {
            switch (i) {
                case 0:
                    list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.wifi), setting[i], ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), DensityUtil.dip2px(context, 60), i + ""));
                    break;
                case 1:
                    list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.scan), setting[i], ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), DensityUtil.dip2px(context, 60), i + ""));
                    break;
                case 2:
                    list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.bluetooth), setting[i], ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), DensityUtil.dip2px(context, 60), i + ""));
                    break;
                case 3:
                    list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.airkiss), setting[i], ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), DensityUtil.dip2px(context, 60), i + ""));
                    break;
            }
        }
        return list;
    }


    /**
     * 这个是设置设备列表的样式     这个是不同类别的(添加设备)
     *
     * @param drawable
     * @param text
     * @param right
     * @param height
     * @param FLAG
     * @return
     */
    private Object getItem(Drawable drawable, String text, Drawable right, int height, String FLAG) {
        Item item = new Item();

        item.setListImage(drawable);
//        item.setListright(right);
        item.setListText(text);
        item.setHeight(height);
        item.setFLAG(FLAG);

        return item;
    }


    /**
     * 这个也是设置设备列表的样式，这个同一类别的(添加设备下一级菜单)
     *
     * @param drawable
     * @param text
     * @param subtitle
     * @param right
     * @param height
     * @return
     */
    private Object getItem(Drawable drawable, String text, String subtitle, Drawable right, int height) {
        Item item = new Item();

        item.setListImage(drawable);
//        item.setListright(right);
        item.setListText(text);
        item.setHeight(height);
        item.setListSubText(subtitle);

        return item;
    }
}
