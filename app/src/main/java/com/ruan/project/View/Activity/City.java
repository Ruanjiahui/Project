package com.ruan.project.View.Activity;

import android.content.ContentValues;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.Interface.HttpInterface;
import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.Database.GetDatabaseData;
import com.example.administrator.http_sdk.HTTP;
import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.ui_sdk.Other.PinyinUntil;
import com.example.administrator.ui_sdk.SorViewInterface;
import com.example.administrator.ui_sdk.View.SortView;
import com.ruan.project.Moudle.Sort;
import com.ruan.project.Other.Adapter.SortListViewAdpter;
import com.ruan.project.Other.DataBase.DatabaseOpera;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.Other.HTTP.HttpURL;
import com.ruan.project.R;
import com.ruan.project.View.Fragment.Fragment1;

import java.util.ArrayList;

/**
 * Created by 19820 on 2016/5/8.
 */
public class City extends BaseActivity implements SorViewInterface, AdapterView.OnItemClickListener {

    private View view = null;

    private SortView sortTitle = null;
    private TextView Sorttext = null;
    private ListView SortlistView = null;
    private EditText sortEdit = null;
    private ArrayList<Object> city = null;

    private ArrayList<Object> list = null;
    private String[] b = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};
    private int[] c = new int[b.length];
    private int position = 1;


    private int flag = 0;

    /**
     * Start()
     */
    @Override
    public void init() {
        flag = getIntent().getExtras().getInt("data");


        setTitle("城市列表");
        setTopColor(R.color.Blue);
        setTopTitleColor(R.color.White);
        setLeftTitleColor(R.color.White);
        setRightTitle("搜索");
        setRightTitleColor(R.color.White);


        city = new DatabaseOpera(context).DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.CityName, null, "", null, "", "", "", "", Sort.class, false);

        view = LayoutInflater.from(context).inflate(R.layout.city, null);


        sortTitle = (SortView) view.findViewById(R.id.sortTitle);
        Sorttext = (TextView) view.findViewById(R.id.Sorttext);
        SortlistView = (ListView) view.findViewById(R.id.SortlistView);
        sortEdit = (EditText) view.findViewById(R.id.sortEdit);


        instance();

        setContent(view);

    }

    private void instance() {
        list = new ArrayList<>();

        for (int r = 0; r < b.length; r++) {
            for (int i = 0; i < city.size(); i++) {
                Sort sort = (Sort) city.get(i);
                if (sort.getCityName() == null)
                    continue;
                if (b[r].equals(PinyinUntil.getSpells(sort.getCityName()))) {
                    if (c[r] == 0)
                        c[r] = position;
                    Sort sortMoudle = new Sort();
                    sortMoudle.setCityName(sort.getCityName());
                    sortMoudle.setCityTitle(PinyinUntil.getSpells(sort.getCityName()));
                    list.add(sortMoudle);
                    position++;
                }
            }
        }


        sortTitle.setTextView(Sorttext);
        SortlistView.setAdapter(new SortListViewAdpter(list, this));
        sortTitle.setOnItemClick(this);
        SortlistView.setOnItemClickListener(this);
    }


    @Override
    public void setRightTextClick(View v) {
        super.setRightTextClick(v);
        String citySearch = sortEdit.getText().toString();
        if (citySearch == null && citySearch.length() == 0 && "".equals(citySearch)) {
            Toast.makeText(context, "城市名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //搜索功能
        city = new DatabaseOpera(context).DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.CityName, null, "cityName = ?", new String[]{citySearch}, "", "", "", "", Sort.class, false);
        if (city != null && city.size() > 0) {
            Sort sort = (Sort) city.get(0);
            HttpURL.CityName = sort.getCityName();
            HttpURL.Cityweather = null;
            DataInert(sort.getCityName());
            CommonIntent.SetActivity(activity, new String[]{}, flag);
            Applications.getInstance().removeOneActivity(this);
        } else {
            Toast.makeText(context, "没有这个城市", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 这个点击事件就是右边滑动的字母的滑动时间
     *
     * @param s       返回滑动到的字母
     * @param postion 返回滑动到第几个字母
     */
    @Override
    public void onItemClick(String s, int postion) {
        if (c[postion] != 0)
            SortlistView.setSelection(c[postion] - 1);
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
        Sort sortMoudle = (Sort) list.get(position);
        HttpURL.CityName = sortMoudle.getCityName();
        DataInert(sortMoudle.getCityName());
        Applications.getInstance().removeOneActivity(this);
    }

    private void DataInert(String city) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("userCity", city);
        new GetDatabaseData().Insert2Update(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName, "userID = ?", new String[]{"123456"}, contentValues, "userID = ?", new String[]{"123456"});

    }
}
