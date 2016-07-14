package com.ruan.project.Interface;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Administrator on 2016/5/5.
 */
public interface PopWinOnClick {

    /**
     * 弹出窗口的点击事件
     * @param parent    弹出窗口的所有组件
     * @param view
     * @param position  弹出窗口listview的个数
     * @param id
     */
    public void OnPopItemClick(AdapterView<?> parent, View view, int position, long id);
}
