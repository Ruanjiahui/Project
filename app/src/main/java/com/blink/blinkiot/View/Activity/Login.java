package com.blink.blinkiot.View.Activity;

import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.blink.blinkiot.Moudle.User;
import com.blink.blinkiot.Other.Weixin.Constants;
import com.blink.blinkiot.Start.ActivityCode;
import com.blink.blinkiot.Start.MainActivity;
import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.blink.blinkiot.Other.Utils.SystemOperation;
import com.blink.blinkiot.R;
import com.example.administrator.ui_sdk.View.MyImageView;
import com.blink.blinkiot.wxapi.WXEntryActivity;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/1.
 */
public class Login extends BaseActivity {

    //    private Context context = null;
    public static Activity activity = null;
    private View view = null;
    private ImageView logindropImage = null;

    //    private EditText load_edit1, load_edit2 = null;
//    private ImageView load_image, load_image1 = null;
    //注册的按钮
//    private Button load_but1 = null;
    //登录的按钮
//    public static Button load_but = null;
    private ArrayList<Map<String, String>> map = null;
    private ArrayList<Object> list = null;
//    private MyPopWindow popWindow = null;

    private String id, password = null;


    private RelativeLayout base_top_relative = null;
    private TextView unLogin = null;
    private MyImageView QQLogin;
    private MyImageView WeixinLogin;
    private MyImageView WeiboLogin;
    private TextView base_top_title = null;
    private String FLAG = null;
//    public static View loginProgress = null;


//    @Override
//    public void onFocusChange(View v, boolean hasFocus) {
//        switch (v.getId()) {
//            case R.id.load_edit1:
//                if (hasFocus) {
//                    load_image.setImageResource(R.mipmap.head);
//                    load_image1.setImageResource(R.mipmap.password);
//                } else {
//                    load_image.setImageResource(R.mipmap.head_up);
//                    load_image1.setImageResource(R.mipmap.password_up);
//                }
//                break;
//            case R.id.load_edit2:
//                if (hasFocus) {
//                    load_image.setImageResource(R.mipmap.head_up);
//                    load_image1.setImageResource(R.mipmap.password_up);
//                } else {
//                    load_image.setImageResource(R.mipmap.head);
//                    load_image1.setImageResource(R.mipmap.password);
//                }
//                break;
//        }
//    }

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
        FLAG = getIntent().getStringExtra("data");


        setTileBar(0);

        view = LayoutInflater.from(context).inflate(R.layout.login, null);

        base_top_relative = (RelativeLayout) view.findViewById(R.id.base_top_relative);
        unLogin = (TextView) view.findViewById(R.id.unLogin);
        QQLogin = (MyImageView) view.findViewById(R.id.QQLogin);
        WeixinLogin = (MyImageView) view.findViewById(R.id.WeixinLogin);
        WeiboLogin = (MyImageView) view.findViewById(R.id.WeiboLogin);
        base_top_title = (TextView) view.findViewById(R.id.base_top_title);


//        load_edit1 = (EditText) view.findViewById(R.id.load_edit1);
//        load_edit2 = (EditText) view.findViewById(R.id.load_edit2);
//        load_image = (ImageView) view.findViewById(R.id.load_image);
//        load_image1 = (ImageView) view.findViewById(R.id.load_image1);
//        load_but = (Button) view.findViewById(R.id.load_but);
//        logindropImage = (ImageView) view.findViewById(R.id.logindropImage);
//        loginProgress = view.findViewById(R.id.loginProgress);
//        load_but1 = (Button) view.findViewById(R.id.load_but1);

        //设置初始化工作
        instance();

        DensityUtil.setWidth(view, width);
        setContent(view);

//        load_edit1.setOnFocusChangeListener(this);
//        load_edit2.setOnFocusChangeListener(this);
//        load_edit1.addTextChangedListener(this);
//        load_edit2.addTextChangedListener(this);
//        logindropImage.setOnClickListener(this);
//        load_but1.setOnClickListener(this);
//        load_but.setOnClickListener(this);
        DensityUtil.setRelayoutSize(unLogin, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, BaseActivity.height / 2 + DensityUtil.dip2px(context, 50), 0, 0, 0, new int[]{RelativeLayout.CENTER_HORIZONTAL});

        QQLogin.setOnClickListener(this);
        WeixinLogin.setOnClickListener(this);
        WeiboLogin.setOnClickListener(this);
        base_top_relative.setOnClickListener(this);
        unLogin.setOnClickListener(this);

        if (FLAG.equals(ActivityCode.GUIDE)) {
            unLogin.setVisibility(View.VISIBLE);
            base_top_relative.setVisibility(View.GONE);
            base_top_title.setVisibility(View.GONE);
        }
    }

    @Override
    public void Click(View v) {
        switch (v.getId()) {
            case R.id.base_top_relative:
                Applications.getInstance().removeOneActivity(this);
                break;
            case R.id.unLogin:
                CommonIntent.IntentActivity(context, MainActivity.class);
                break;
            case R.id.QQLogin:
                CommonIntent.IntentActivity(context, ThridLogin.class, ActivityCode.QQ);
                break;
            case R.id.WeixinLogin:
                // 通过WXAPIFactory工厂，获取IWXAPI的实例
                IWXAPI api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
                SendAuth.Req req = new SendAuth.Req();
                req.scope = Constants.scope;
                req.state = Constants.state;
                api.sendReq(req);

                CommonIntent.IntentActivity(context, WXEntryActivity.class);
                break;
            case R.id.WeiboLogin:
                CommonIntent.IntentActivity(context, ThridLogin.class, ActivityCode.WEIBO);
                break;
//            case R.id.load_but:
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
//                break;
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

    //    @Override
//    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//    }
//
//    @Override
//    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//    }

//    @Override
//    public void afterTextChanged(Editable s) {
//        if (load_edit1.getText().toString().length() > 0 && load_edit2.getText().toString().length() > 0) {
//            load_but.setClickable(true);
//            load_but.setEnabled(true);
//            load_but.setBackgroundResource(R.drawable.button_selector_red);
//        } else {
//            load_but.setClickable(false);
//            load_but.setEnabled(false);
//            load_but.setBackgroundResource(R.drawable.button_down_red);
//        }
//    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (FLAG.equals(ActivityCode.GUIDE) && Constants.code != null) {
            CommonIntent.IntentActivity(context, MainActivity.class);
            Applications.getInstance().removeOneActivity(this);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (FLAG.equals(ActivityCode.GUIDE)) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
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
//    @Override
//    public void OnPopItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//    }
}
