package com.blink.blinkiot.View.Activity;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.blink.blinkiot.Controllar.SettingControl;
import com.blink.blinkiot.Moudle.MessageConfig;
import com.example.administrator.data_sdk.CommonIntent;
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
    private boolean isCheck = false;
    private ArrayList<String> Title = null;
    public static Activity activity = null;

    private SettingControl settingControl = null;
    private MessageConfig messageConfig = null;

    /**
     * Start()
     */
    @Override
    public void init() {
        activity = this;

        setTopColor(R.color.Blue);
        setRightTitleVisiable(false);
        setTopTitleColor(R.color.White);
        setLeftTitleColor(R.color.White);
        setContentColor(R.color.WhiteSmoke);
        setTitle(getResources().getString(R.string.MessageTitle));

        view = LayoutInflater.from(context).inflate(R.layout.messagesetting, null);

        messageListView = (ListView) view.findViewById(R.id.messageListView);

        settingControl = new SettingControl(this);

        list = new ArrayList<>();
        messageConfig = settingControl.getStartMessageValues();
        Title = settingControl.getMessageTitle();

        setContent(view);

        messageListView.setOnItemClickListener(this);
        //设置开始默认的参数
        settingControl.setStartProprites(Title, messageConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter == null) {
            list = settingControl.getMessageList(Title, messageConfig);

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
        switch (position) {
            //设置是否wifi情况下自动更新
            case 0:
                isCheck = getIsable(item.isCheck());
                item.setCheck(isCheck);
                //将信息写入配置文件
                FileTool.WriteProperties(context, HttpURL.ConfigName, Title.get(position), isCheck + "");
                break;
        }
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
