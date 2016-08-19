package com.blink.blinkiot.View.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.data_sdk.FileUtil.FileTool;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.blink.blinkiot.Moudle.Item;
import com.blink.blinkiot.Other.Adapter.LGAdapter;
import com.blink.blinkiot.Other.HTTP.HttpURL;
import com.blink.blinkiot.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/15.
 */
public class MessageSetting extends BaseActivity implements AdapterView.OnItemClickListener {

    private View view = null;
    private ListView messageListView = null;
    private LGAdapter adapter = null;
    private ArrayList<Object> list = null;
    private ArrayList<String> Name = null;
    private boolean isCheck = false;
    private String WIFI = "WIFI";

    /**
     * Start()
     */
    @Override
    public void init() {

        setTopColor(R.color.Blue);
        setRightTitleVisiable(false);
        setTopTitleColor(R.color.White);
        setLeftTitleColor(R.color.White);
        setContentColor(R.color.WhiteSmoke);
        setTitle(getSystemText(R.string.MessageTitle));

        view = LayoutInflater.from(context).inflate(R.layout.messagesetting, null);

        messageListView = (ListView) view.findViewById(R.id.messageListView);

        list = new ArrayList<>();
        Name = new ArrayList<>();

        setContent(view);

        messageListView.setOnItemClickListener(this);

    }

    /**
     * 获取系统xml文字
     *
     * @param resid
     * @return
     */
    private String getSystemText(int resid) {
        return getResources().getString(resid);
    }


    private Object getItem(String title, String check) {
        Item item = new Item();

        item.setHeight(DensityUtil.dip2px(context, 50));
        item.setListText(title);
        item.setVisiable(true);
        if ("false".equals(check))
            item.setCheck(false);
        else
            item.setCheck(true);

        return item;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (adapter == null) {
            //添加配置文件的数据
            Name.add(FileTool.ReadProperties(context, HttpURL.ConfigName, WIFI));
            for (String value : Name)
                list.add(getItem(getSystemText(R.string.WifiUpdateTxt), value));

            adapter = new LGAdapter(context, list, "ListView");
            messageListView.setAdapter(adapter);
            return;
        }
        adapter.RefreshData(list);
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p/>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Item item = (Item) list.get(position);
        isCheck = getIsable(item.isCheck());
        item.setCheck(isCheck);
        //将信息写入配置文件
        FileTool.WriteProperties(context, HttpURL.ConfigName, WIFI, isCheck + "");
        //更新显示
        list.set(position, item);
        adapter.RefreshData(list);
    }

    private boolean getIsable(boolean isCheck) {
        if (isCheck)
            return false;
        return true;
    }
}
