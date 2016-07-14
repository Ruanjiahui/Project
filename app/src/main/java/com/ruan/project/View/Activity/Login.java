package com.ruan.project.View.Activity;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.ruan.project.Interface.PopWinOnClick;
import com.ruan.project.Other.Utils.SystemOperation;
import com.ruan.project.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/1.
 */
public class Login extends BaseActivity implements View.OnFocusChangeListener, TextWatcher, PopWinOnClick {

    //    private Context context = null;
    public static Activity activity = null;
    private View view = null;
    private ImageView logindropImage = null;

    private EditText load_edit1, load_edit2 = null;
    private ImageView load_image, load_image1 = null;
    //注册的按钮
//    private Button load_but1 = null;
    //登录的按钮
    public static Button load_but = null;
    private ArrayList<Map<String, String>> map = null;
    private ArrayList<Object> list = null;
//    private MyPopWindow popWindow = null;

    private String id, password = null;
//    public static View loginProgress = null;



    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.load_edit1:
                if (hasFocus) {
                    load_image.setImageResource(R.mipmap.head);
                    load_image1.setImageResource(R.mipmap.password);
                } else {
                    load_image.setImageResource(R.mipmap.head_up);
                    load_image1.setImageResource(R.mipmap.password_up);
                }
                break;
            case R.id.load_edit2:
                if (hasFocus) {
                    load_image.setImageResource(R.mipmap.head_up);
                    load_image1.setImageResource(R.mipmap.password_up);
                } else {
                    load_image.setImageResource(R.mipmap.head);
                    load_image1.setImageResource(R.mipmap.password);
                }
                break;
        }
    }

    private void instance() {
//        loginProgress.setVisibility(View.GONE);
//        CheckDatabase.FirstCheckDatabase(context);
//        GetDatabaseData getDatabaseData = new GetDatabaseData();
//        map = getDatabaseData.QueryArray(context, "BlueBooth", "User", new String[]{"mbtel"}, "", null, "", "", "", "");
//        list = new ArrayList<>();
//
//        if (map != null && map.size() > 0) {
//            for (int i = 0; i < map.size(); i++)
//                list.add(AdapterData.getMap(map.get(i).get("mbtel"), DensityUtil.dip2px(context, 50)));
//            load_edit1.setText(map.get(0).get("mbtel"));
//        } else {
//            list.add(AdapterData.getMap("", DensityUtil.dip2px(context, 50)));
//        }
    }


    /**
     * Start()
     */
    @Override
    public void init() {
        activity = (Activity) context;

        setTitle("登录");
        setTopColor(R.color.Blue);
        setRightTitleVisiable(false);
        setTopTitleColor(R.color.White);
        setLeftTitleColor(R.color.White);
        setContentColor(R.color.GreySmoke);

        view = LayoutInflater.from(context).inflate(R.layout.login, null);

        load_edit1 = (EditText) view.findViewById(R.id.load_edit1);
        load_edit2 = (EditText) view.findViewById(R.id.load_edit2);
        load_image = (ImageView) view.findViewById(R.id.load_image);
        load_image1 = (ImageView) view.findViewById(R.id.load_image1);
        load_but = (Button) view.findViewById(R.id.load_but);
//        logindropImage = (ImageView) view.findViewById(R.id.logindropImage);
//        loginProgress = view.findViewById(R.id.loginProgress);
//        load_but1 = (Button) view.findViewById(R.id.load_but1);

        //设置初始化工作
        instance();

        DensityUtil.setWidth(view, width);
        setContent(view);

        load_edit1.setOnFocusChangeListener(this);
        load_edit2.setOnFocusChangeListener(this);
        load_edit1.addTextChangedListener(this);
        load_edit2.addTextChangedListener(this);
//        logindropImage.setOnClickListener(this);
//        load_but1.setOnClickListener(this);
        load_but.setOnClickListener(this);
    }

    @Override
    public void Click(View v) {
        switch (v.getId()) {
            case R.id.load_but:
//                if (MainHandler.isConnection(context)) {
//                    load_but.setClickable(false);
//                    load_but.setEnabled(false);
//                    load_but.setBackground(getResources().getDrawable(R.drawable.button_down_red));
//                    loginProgress.setVisibility(View.VISIBLE);
//                    id = load_edit1.getText().toString();
//                    password = load_edit2.getText().toString();
//
//                    Server server = new Server();
//                    server.setOnline(1);
//                    server.setMbtel(id);
//                    server.setLaststate(1);
//                    server.setPassword(password);
//
//                    LoadModel loadModel = new LoadModel(context);
//                    //将server封装成ContentValues
//                    loadModel.UpdateServer(server);
//                    //实现登录的方法
//                    loadModel.LinkLoad(server);
//                }
                break;
//            case R.id.load_but1:
//                CommonIntent.IntentActivity(context, Register.class);
//                break;
//            case R.id.logindropImage:
//                popWindow = new MyPopWindow(activity, list, MainActivity.width - DensityUtil.dip2px(context, 80));
//                popWindow.showPopupWindow(logindropImage, 0, DensityUtil.dip2px(context, 20));
//                popWindow.setOnPopWinItemClick(this);
//                break;
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (load_edit1.getText().toString().length() > 0 && load_edit2.getText().toString().length() > 0) {
            load_but.setClickable(true);
            load_but.setEnabled(true);
            load_but.setBackgroundResource(R.drawable.button_selector_red);
        } else {
            load_but.setClickable(false);
            load_but.setEnabled(false);
            load_but.setBackgroundResource(R.drawable.button_down_red);
        }
    }

    /**
     * 整个屏幕的触摸事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //关闭软键盘
            SystemOperation.Closekeyboard(activity);
        }
        return super.onTouchEvent(event);
    }

//    @Override
//    public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (map != null && map.size() > 0)
//            load_edit1.setText(map.get(position).get("mbtel"));
//        popWindow.disShow();
//    }

    /**
     * 弹出窗口的点击事件
     *
     * @param parent   弹出窗口的所有组件
     * @param view
     * @param position 弹出窗口listview的个数
     * @param id
     */
    @Override
    public void OnPopItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}