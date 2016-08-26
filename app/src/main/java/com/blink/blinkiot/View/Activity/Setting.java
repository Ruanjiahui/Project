package com.blink.blinkiot.View.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.blink.blinkiot.Moudle.User;
import com.blink.blinkiot.Other.DataBase.DatabaseOpera;
import com.blink.blinkiot.Other.DatabaseTableName;
import com.blink.blinkiot.Start.ActivityCode;
import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.Database.GetDatabaseData;
import com.example.administrator.data_sdk.FileUtil.FileTool;
import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.blink.blinkiot.Controllar.CheckUpdate;
import com.blink.blinkiot.Moudle.Item;
import com.blink.blinkiot.Moudle.Version;
import com.blink.blinkiot.Other.Adapter.LGAdapter;
import com.blink.blinkiot.Other.System.FileURL;
import com.blink.blinkiot.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Soft on 2016/7/11.
 */
public class Setting extends BaseActivity implements AdapterView.OnItemClickListener {
    private View view = null;
    private ListView settingList = null;
    private View settingBut = null;
    private TextView circlebut = null;
    public static Activity activity = null;


    private ArrayList<Object> list = null;
    private RelativeLayout settingRel = null;

    private LGAdapter adapter = null;

    private CheckUpdate checkUpdate = null;

    private User user = null;

    /**
     * Start()
     */
    @Override
    public void init() {
        activity = this;
        view = LayoutInflater.from(context).inflate(R.layout.setting, null);

        settingList = (ListView) view.findViewById(R.id.settingList);

        settingBut = view.findViewById(R.id.settingBut);
        circlebut = (TextView) view.findViewById(R.id.circlebut);
        settingRel = (RelativeLayout) view.findViewById(R.id.settingRel);
        settingBut.setVisibility(View.VISIBLE);


        checkUpdate = new CheckUpdate(context);

        setTopColor(R.color.Blue);
        setRightTitleVisiable(false);
        setTopTitleColor(R.color.White);
        setLeftTitleColor(R.color.White);
        setContentColor(R.color.WhiteSmoke);
        setTitle(getSystemText(R.string.SettingTitle));
        circlebut.setText(getSystemText(R.string.LoginBut));

        setContent(view);


        settingList.setOnItemClickListener(this);
        settingBut.setOnClickListener(this);
        settingRel.setOnClickListener(this);
        DensityUtil.setRelayoutSize(settingBut, DensityUtil.dip2px(context, 55), DensityUtil.dip2px(context, 55), BaseActivity.height / 4 * 3, 0, 0, 0, new int[]{RelativeLayout.CENTER_HORIZONTAL});

    }


    @Override
    protected void onResume() {
        super.onResume();

        long size = 0;
        list = new ArrayList<>();
        ArrayList<String> Cache = FileURL.getCacheURL(this);
        for (int i = 0; i < Cache.size(); i++)
            if (Cache.get(i) != null && Cache.get(i).length() > 0)
                size += FileTool.getFolderSize(new File(Cache.get(i)));

        Version version = Version.getVersionObj();
        list.add(getItem(getSystemText(R.string.ClearCache), FileTool.getFormatSize(size), ImageTransformation.Resouce2Drawable(context, R.mipmap.right)));
        if (!version.isUpdate()) {
            list.add(getItem(getSystemText(R.string.CheckUpdate), getSystemText(R.string.VersionName), ImageTransformation.Resouce2Drawable(context, R.mipmap.right)));
        } else {
            list.add(getItem(getSystemText(R.string.CheckUpdate), version.getVersion(), ImageTransformation.Resouce2Drawable(context, R.mipmap.right)));
        }
        list.add(getItem(getSystemText(R.string.MessageSetting), null, ImageTransformation.Resouce2Drawable(context, R.mipmap.right)));
        list.add(getItem(getSystemText(R.string.AboutMe), null, ImageTransformation.Resouce2Drawable(context, R.mipmap.right)));
        if (adapter == null) {
            adapter = new LGAdapter(context, list, "ListView");
            settingList.setAdapter(adapter);
        } else {
            adapter.RefreshData(list);
        }

        user = User.getInstance();
        if (user != null && User.ONLINE.equals(user.getUserLogin()))
            circlebut.setText(getSystemText(R.string.LoginButExists));
        else circlebut.setText(getSystemText(R.string.LoginBut));
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


    private Object getItem(String title, String subtitile, Drawable drawable) {
        Item item = new Item();

        item.setListSubText(subtitile);
        item.setListText(title);
        item.setListright(drawable);
        item.setHeight(DensityUtil.dip2px(context, 50));

        return item;
    }

    @Override
    public void Click(View v) {
        switch (v.getId()) {
            case R.id.settingBut:
                if (user != null && User.ONLINE.equals(user.getUserLogin())) {
                    user.setUserLogin(User.UNONLINE);
                    circlebut.setText(getSystemText(R.string.LoginBut));
                    Toast.makeText(context, getSystemText(R.string.LoginToast), Toast.LENGTH_SHORT).show();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("userLogin", User.UNONLINE);
                    new GetDatabaseData().Update(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName, contentValues, "userID = ?", new String[]{user.getUserID()});
                } else {
                    CommonIntent.IntentActivity(context, Login.class, ActivityCode.SETTING);
                }
                break;
        }
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
        switch (position) {
            case 0:
                CommonIntent.IntentActivity(context, ClearCache.class);
                break;
            case 1:
                settingRel.setVisibility(View.VISIBLE);
                //进行检查更新
                checkUpdate.Update(1);
                //设置成功后隐藏组件
                checkUpdate.setDisView(settingRel);
                break;
            case 2:
                CommonIntent.IntentActivity(context, MessageSetting.class);
                break;
            case 3:
                CommonIntent.IntentActivity(context, AboutMe.class);
                break;
        }
    }
}
