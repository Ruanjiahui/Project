package com.blink.blinkiot.View.Fragment;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.blink.blinkiot.Other.DataBase.DatabaseOpera;
import com.blink.blinkiot.Other.DatabaseTableName;
import com.blink.blinkiot.Other.System.FileURL;
import com.blink.blinkiot.Start.ActivityCode;
import com.blink.blinkiot.View.Activity.Login;
import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.Database.Database;
import com.example.administrator.data_sdk.Database.GetDatabaseData;
import com.example.administrator.data_sdk.FileUtil.FileTool;
import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.blink.blinkiot.Moudle.Item;
import com.blink.blinkiot.Moudle.User;
import com.blink.blinkiot.Other.Adapter.LGAdapter;
import com.blink.blinkiot.R;
import com.blink.blinkiot.View.Activity.Help;
import com.blink.blinkiot.View.Activity.Setting;
import com.blink.blinkiot.View.Activity.Suggest;

import java.util.ArrayList;


/**
 * Created by Soft on 2016/6/23.
 */
public class Fragment4 extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private View view = null;
    private Context context = null;
    private RelativeLayout fragment4Top = null;
    private GridView fragment4Grid = null;
    private TextView fragment4ID = null;
    private ArrayList<Object> list = null;
    private ImageView fragment4Logo = null;

    //    private View fragmentOrigin = null;
    private User user = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment4, container, false);

        context = getActivity();

        fragment4Top = (RelativeLayout) view.findViewById(R.id.fragment4Top);
        fragment4Grid = (GridView) view.findViewById(R.id.fragment4Grid);
//        fragmentOrigin = view.findViewById(R.id.fragmentOrigin);
        fragment4Logo = (ImageView) view.findViewById(R.id.fragment4Logo);
        fragment4ID = (TextView) view.findViewById(R.id.fragment4ID);

        list = new ArrayList<>();
        list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.family), getResources().getString(R.string.Family), DensityUtil.dip2px(context, 120)));
        list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.help), getResources().getString(R.string.HelpTitle), DensityUtil.dip2px(context, 120)));
        list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.suggest), getResources().getString(R.string.SuggestTitle), DensityUtil.dip2px(context, 120)));
        list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.setting), getResources().getString(R.string.SystemSetting), DensityUtil.dip2px(context, 120)));

        DensityUtil.setHeight(fragment4Top, BaseActivity.height / 3);

        user = User.getInstance();

//        if ("true".equals(user.getUserLogin())) {
//            fragment4ID.setText(user.getUserName());
//        }


        fragment4Grid.setAdapter(new LGAdapter(context, list, "GridView"));
//        fragmentOrigin.setVisibility(View.GONE);


        fragment4Grid.setOnItemClickListener(this);
        fragment4Logo.setOnClickListener(this);

        return view;
    }


    private Object getItem(Drawable drawable, String text, int height) {
        Item item = new Item();

        item.setGridImage(drawable);
        item.setGridText(text);
        item.setHeight(height);

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
                break;
            case 1:
                CommonIntent.IntentActivity(context, Help.class);
                break;
            case 2:
                CommonIntent.IntentActivity(context, Suggest.class);
                break;
            case 3:
                CommonIntent.IntentActivity(context, Setting.class);
                break;
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment4Logo:
                if (user != null && User.ONLINE.equals(user.getUserLogin())){
                    user.setUserLogin(User.UNONLINE);
                    fragment4ID.setText(getResources().getString(R.string.fragment4LogoTitle));
                    fragment4Logo.setImageDrawable(ImageTransformation.Resouce2Drawable(context, R.mipmap.userlogo));
                    Toast.makeText(context, getResources().getString(R.string.LoginToast), Toast.LENGTH_SHORT).show();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("userLogin", User.UNONLINE);
                    new GetDatabaseData().Update(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName, contentValues, "userID = ?", new String[]{user.getUserID()});
                }else
//                    Toast.makeText(context , )
//                    CommonIntent.IntentActivity(context, Person.class);
//                else
                    CommonIntent.IntentActivity(context, Login.class, ActivityCode.ME);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //如果用户没有信息则显示默认
        if (user != null && User.ONLINE.equals(user.getUserLogin())) {
            fragment4ID.setText(user.getUserName());
            fragment4Logo.setImageDrawable(FileTool.readPictureByteDrawable(context, user.getUserImage(), FileURL.LogoPath));
        } else {
            fragment4ID.setText(getResources().getString(R.string.fragment4LogoTitle));
            fragment4Logo.setImageDrawable(ImageTransformation.Resouce2Drawable(context, R.mipmap.userlogo));
        }
    }
}
