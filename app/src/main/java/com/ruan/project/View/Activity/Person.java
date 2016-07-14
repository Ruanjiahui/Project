package com.ruan.project.View.Activity;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.ruan.project.Moudle.Item;
import com.ruan.project.Other.Adapter.LGAdapter;
import com.ruan.project.Other.DataBase.DatabaseOpera;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/13.
 */
public class Person extends BaseActivity implements AdapterView.OnItemClickListener {
    private View view = null;

    private ListView personList = null;
    private ArrayList<Object> list = null;
    private LGAdapter adapter = null;

    private ArrayList<Map<String, String>> map = null;

    /**
     * Start()
     */
    @Override
    public void init() {

        view = LayoutInflater.from(context).inflate(R.layout.person, null);

        personList = (ListView) view.findViewById(R.id.personList);

        setTopTitleColor(R.color.White);
        setTitle("信息");
        setLeftTitleColor(R.color.White);
        setTopColor(R.color.Blue);
        setRightTitleVisiable(false);
        setContentColor(R.color.GreySmoke);

        list = new ArrayList<>();

        map = new DatabaseOpera(context).DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName, "userID", "123456");

        getList();

        adapter = new LGAdapter(context, list, "ListView");
        personList.setAdapter(adapter);


        personList.setOnItemClickListener(this);

        setContent(view);
    }

    private void getList() {
        list.add(getItem(null, "账号", DensityUtil.dip2px(context, 50), map.get(0).get("userID")));
        list.add(getItem(null, "电话", DensityUtil.dip2px(context, 50), map.get(0).get("userPhone")));
        list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), "-", DensityUtil.dip2px(context, 30), ""));
        list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), "姓名", DensityUtil.dip2px(context, 50), map.get(0).get("userName")));
        list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), "-", DensityUtil.dip2px(context, 30), ""));
        list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), "性别", DensityUtil.dip2px(context, 50), map.get(0).get("userSex")));
        list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), "身高", DensityUtil.dip2px(context, 50), map.get(0).get("userHeight")));
        list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), "体重", DensityUtil.dip2px(context, 50), map.get(0).get("userWeight")));
        list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), "生日", DensityUtil.dip2px(context, 50), map.get(0).get("userBirthday")));

    }


    private Object getItem(Drawable drawable, String text, int height, String textRight) {
        Item item = new Item();

        item.setListright(drawable);
        item.setHeight(height);
        item.setListText(text);
        item.setListRightText(textRight);

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
            case 3:
                CommonIntent.IntentActivity(context, Edit.class, map.get(0).get("userName"), "name");
                break;
            case 5:
                CommonIntent.IntentActivity(context, Edit.class, map.get(0).get("userSex"), "sex");
                break;
            case 6:
                CommonIntent.IntentActivity(context, Edit.class, map.get(0).get("userHeight"), "height");
                break;
            case 7:
                CommonIntent.IntentActivity(context, Edit.class, map.get(0).get("userWeight"), "weight");
                break;
            case 8:
                CommonIntent.IntentActivity(context, Edit.class, map.get(0).get("userBirthday"), "birthday");
                break;
        }
    }

    /**
     * 当页面重新启动的时候，应该获取本地数据库的用户数据重新更新数据到界面
     */
    @Override
    protected void onRestart() {
        super.onRestart();

        map = new DatabaseOpera(context).DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName, "userID", "123456");
        list.clear();
        getList();
        adapter.RefreshData(list);
    }
}
