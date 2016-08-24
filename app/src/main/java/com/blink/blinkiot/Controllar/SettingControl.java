package com.blink.blinkiot.Controllar;

import android.content.Context;

import com.blink.blinkiot.Moudle.Item;
import com.blink.blinkiot.Moudle.MessageConfig;
import com.blink.blinkiot.Other.HTTP.HttpURL;
import com.blink.blinkiot.R;
import com.example.administrator.data_sdk.FileUtil.FileTool;
import com.example.administrator.ui_sdk.DensityUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/22.
 */
public class SettingControl {

    private Context context;

    public SettingControl(Context context) {
        this.context = context;
    }

    /**
     * 获取消息设置的列表标题
     *
     * @return
     */
    public ArrayList<String> getMessageTitle() {
        ArrayList<String> Title = new ArrayList<>();

        Title.add(getSystemText(R.string.WifiUpdateTxt));

        return Title;
    }

    /**
     * 获取消息设置的列表初始值的列表
     *
     * @return
     */
    public MessageConfig getStartMessageValues() {

        MessageConfig messageConfig = new MessageConfig();
        messageConfig.setWIFI("true");

        return messageConfig;
    }

    public void setStartProprites(ArrayList<String> Title, MessageConfig messageConfig) {
        //配置文件操作
        //将信息写入配置文件
        //默认为wifi情况下自动更新软件
        if (!FileTool.getProperties(context, HttpURL.ConfigName)) {
            for (int i = 0; i < Title.size(); i++)
                switch (i) {
                    //设置更新状态
                    case 0:
                        FileTool.WriteProperties(context, HttpURL.ConfigName, Title.get(0), messageConfig.getWIFI());
                        break;
                }
        }
    }

    /**
     * 获取消息设置的显示列表数据
     *
     * @param Title
     * @param messageConfig
     * @return
     */
    public ArrayList<Object> getMessageList(ArrayList<String> Title, MessageConfig messageConfig) {
        ArrayList<Object> list = new ArrayList<>();
        //添加配置文件的数据
        for (int r = 0; r < Title.size(); r++)
            switch (r) {
                case 0:
                    messageConfig.setWIFI(FileTool.ReadProperties(context, HttpURL.ConfigName, Title.get(0)));
                    break;
            }
        //将配置文件的数据添加到显示列表里面
        for (int i = 0; i < Title.size(); i++) {
            switch (i) {
                case 0:
                    list.add(getItem(Title.get(i), messageConfig.getWIFI(), true, ""));
                    break;
            }
        }
        return list;
    }

    /**
     * 设置消息列表的显示样式
     *
     * @param title
     * @param check
     * @param isVisiable
     * @param RightTxt
     * @return
     */
    private Object getItem(String title, String check, boolean isVisiable, String RightTxt) {
        Item item = new Item();

        item.setHeight(DensityUtil.dip2px(context, 50));
        item.setListText(title);
        item.setVisiable(isVisiable);
        if (!isVisiable)
            item.setListRightText(RightTxt);
        if ("false".equals(check))
            item.setCheck(false);
        else
            item.setCheck(true);
        return item;
    }


    /**
     * 获取系统xml文字
     *
     * @param resid
     * @return
     */
    private String getSystemText(int resid) {
        return context.getResources().getString(resid);
    }


}
