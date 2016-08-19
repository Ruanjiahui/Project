package com.ruan.project.View.Activity;

import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.HttpCode;
import com.example.administrator.Interface.HttpResult;
import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.FileUtil.FileTool;
import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.http_sdk.HTTP;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.loc.bo;
import com.ruan.project.Controllar.CheckUpdate;
import com.ruan.project.Controllar.FragmentControl;
import com.ruan.project.Moudle.Item;
import com.ruan.project.Moudle.Version;
import com.ruan.project.Other.Adapter.LGAdapter;
import com.ruan.project.Other.HTTP.HttpURL;
import com.ruan.project.Other.System.FileURL;
import com.ruan.project.R;

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


    private ArrayList<Object> list = null;
    private RelativeLayout settingRel = null;

    private LGAdapter adapter = null;

    private CheckUpdate checkUpdate = null;

    /**
     * Start()
     */
    @Override
    public void init() {
        view = LayoutInflater.from(context).inflate(R.layout.setting, null);

        settingList = (ListView) view.findViewById(R.id.settingList);

        settingBut = view.findViewById(R.id.settingBut);
        circlebut = (TextView) view.findViewById(R.id.circlebut);
        settingRel = (RelativeLayout) view.findViewById(R.id.settingRel);
        settingBut.setVisibility(View.GONE);


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
