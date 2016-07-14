package com.ruan.project.View.Fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.Database.GetDatabaseData;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.View.SideListView;
import com.ruan.project.Interface.ItemClick;
import com.ruan.project.Moudle.Item;
import com.ruan.project.Other.Adapter.SideListViewAdapter;
import com.ruan.project.Other.DataBase.DataHandler;
import com.ruan.project.Other.DataBase.DatabaseOpera;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.R;
import com.ruan.project.View.Activity.Edit;

import java.util.ArrayList;
import java.util.Map;


/**
 * Created by Soft on 2016/6/23.
 */
public class Fragment2 extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, ItemClick {

    private View view = null;

    private Context context = null;

    private View fragment2Top = null;
    private RelativeLayout base_top_relative = null;
    private TextView base_top_text1 = null;
    private TextView base_top_title = null;
    private SideListView sideListView = null;
    private SideListViewAdapter adapter = null;

    private ArrayList<Object> list = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();

        view = inflater.inflate(R.layout.fragment2, container, false);

        sideListView = (SideListView) view.findViewById(R.id.slideListView1);
        fragment2Top = view.findViewById(R.id.fragment2Top);
        base_top_relative = (RelativeLayout) view.findViewById(R.id.base_top_relative);
        base_top_text1 = (TextView) view.findViewById(R.id.base_top_text1);
        base_top_title = (TextView) view.findViewById(R.id.base_top_title);

        fragment2Top.setBackgroundResource(R.color.Blue);
        base_top_title.setPadding(0, DensityUtil.dip2px(getActivity(), 20), 0, 0);
        base_top_text1.setPadding(0, DensityUtil.dip2px(getActivity(), 20), 0, 0);
        base_top_title.setText("场景");
        base_top_title.setTextColor(getResources().getColor(R.color.White));
        base_top_relative.setVisibility(View.GONE);
        base_top_text1.setText("新建场景");
        base_top_text1.setTextColor(getResources().getColor(R.color.White));

        list = new ArrayList<>();

        base_top_text1.setOnClickListener(this);
        sideListView.setOnItemClickListener(this);

        return view;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_top_text1:
                CommonIntent.IntentActivity(context, Edit.class, "", "scene");
                break;
        }
    }

    private ArrayList<Map<String, String>> map = null;

    @Override
    public void onStart() {
        super.onStart();

        //首先清空列表的数据
        list.clear();
        //从数据库中获取列表的数据
        map = new DatabaseOpera(context).DataQuery(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.SceneName);
        for (int i = 0; i < map.size(); i++)
            list.add(getItem(map.get(i).get("sceneName")));
        //判断适配器是不是空值，如果是空值则新建否则直接刷新数据即可
        if (adapter == null) {
            adapter = new SideListViewAdapter(context, list);
            sideListView.setAdapter(adapter);
        } else {
            adapter.RefreshData(list);
        }
        adapter.setItemClick(this);

    }

    private Object getItem(String title) {
        Item item = new Item();
        item.setTitle(title);
        return item;
    }

    /**
     * item的子控件点击事件
     *
     * @param position 子控件在列表中属于第几个
     * @param View     子控件的序号靠最左边为第0个
     */
    @Override
    public void OnClick(int position, int View) {
        sideListView.ShowNormal();
        switch (View) {
            case 0:
                CommonIntent.IntentActivity(context, Edit.class, map.get(position).get("sceneID"), "scene");
                break;
            case 1:
                //删除界面的item并且同时删除本地数据的数据和服务器上面的数据
                list.remove(position);
                adapter.RefreshData(list);
                //删除本地数据库的数据
                new DatabaseOpera(context).DataDelete(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.SceneName, "sceneID = ?", new String[]{map.get(position).get("sceneID")});
                new GetDatabaseData().Update(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, DataHandler.getContentValues("sceneID", null), "sceneID = ?", new String[]{map.get(position).get("sceneID")});
//                new DatabaseOpera(context).DataDelete(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, "sceneID = ?", new String[]{map.get(position).get("sceneID")});
                //删除服务器的数据库的数据
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

    }
}
