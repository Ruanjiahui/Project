package com.ruan.project.View.Activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.SystemUtil.SystemTool;
import com.example.administrator.data_sdk.WIFI.WifiFactory;
import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.ui_sdk.MyCircleLoading;
import com.example.administrator.ui_sdk.View.CircleLoading;
import com.ruan.project.Other.AirKiss.AirKissCallBack;
import com.ruan.project.Other.AirKiss.AirkissConfig;
import com.ruan.project.Other.DataBase.DataHandler;
import com.ruan.project.Other.DataBase.DatabaseOpera;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.Other.System.NetWork;
import com.ruan.project.R;


/**
 * Created by Administrator on 2016/7/28.
 * <p/>
 * <p/>
 * AirKiss配置网络的类
 */
public class AirkissNetWork extends BaseActivity implements TextWatcher, AirKissCallBack, MyCircleLoading {

    private View view = null;

    private EditText wifiSSID;
    private EditText wifiPassword;
    private CircleLoading wifiConn;
    private boolean isClick = false;

    private AirkissConfig airkissConfig = null;

    /**
     * Start()
     */
    @Override
    public void init() {
        view = LayoutInflater.from(context).inflate(R.layout.airkiss, null);

        setTopColor(R.color.Blue);
        setTopTitleColor(R.color.White);
        setContentColor(R.color.WhiteSmoke);
        setRightTitleVisiable(false);
        setLeftTitleColor(R.color.White);
        setTitle("配置网络");


        airkissConfig = new AirkissConfig();

        wifiSSID = (EditText) view.findViewById(R.id.wifiSSID);
        wifiPassword = (EditText) view.findViewById(R.id.wifiPassword);
        wifiConn = (CircleLoading) view.findViewById(R.id.wifiConn);

        //判断当前是不是wifi状态
        if (SystemTool.isNetState(context) == NetWork.WIFI)
            wifiSSID.setText(WifiFactory.getWifiSSID(context));
        else
            wifiSSID.setText("Unknow network");

        setContent(view);

        wifiSSID.addTextChangedListener(this);
        wifiPassword.addTextChangedListener(this);
        wifiConn.setOnClickListener(this);


        wifiConn.setTime(60000);
        wifiConn.setSweepAngle(360);
        wifiConn.setClick(this);
    }

    /**
     * This method is called to notify you that, within <code>s</code>,
     * the <code>count</code> characters beginning at <code>start</code>
     * are about to be replaced by new text with length <code>after</code>.
     * It is an error to attempt to make changes to <code>s</code> from
     * this callback.
     *
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /**
     * This method is called to notify you that, within <code>s</code>,
     * the <code>count</code> characters beginning at <code>start</code>
     * have just replaced old text that had length <code>before</code>.
     * It is an error to attempt to make changes to <code>s</code> from
     * this callback.
     *
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    /**
     * This method is called to notify you that, somewhere within
     * <code>s</code>, the text has been changed.
     * It is legitimate to make further changes to <code>s</code> from
     * this callback, but be careful not to get yourself into an infinite
     * loop, because any changes you make will cause this method to be
     * called again recursively.
     * (You are not told where the change took place because other
     * afterTextChanged() methods may already have made other changes
     * and invalidated the offsets.  But if you need to know here,
     * you can use {@link #} in {@link #onTextChanged}
     * to mark your place and then look up from here where the span
     * ended up.
     *
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {
        if (wifiSSID.getText().toString().length() > 0 && wifiPassword.getText().toString().length() > 0) {
            isClick = true;
        } else {
            isClick = false;
        }
    }


    /**
     * 这个是链接成功触发的接口
     *
     * @param object
     */
    @Override
    public void Result(Object object) {
        Toast.makeText(context, "配置成功", Toast.LENGTH_SHORT).show();
        Applications.getInstance().removeOneActivity(this);
    }

    /**
     * 这个链接不成功触发的接口
     *
     * @param error
     */
    @Override
    public void Error(int error) {
        wifiConn.setStop(CircleLoading.END);
        Toast.makeText(context, "配置超时", Toast.LENGTH_SHORT).show();
    }

    /**
     * 这个是点击事件的接口
     *
     * @param v
     */
    @Override
    public void circleClick(View v) {
        if (isClick) {
            if (!wifiConn.getNowState()) {
                wifiConn.setStart();
//                Toast.makeText(context, "配置网络开始", Toast.LENGTH_SHORT).show();
                airkissConfig.StartAirKiss(wifiSSID.getText().toString(), wifiPassword.getText().toString(), this);
            } else {
                if (airkissConfig != null)
                    airkissConfig.StopAirKiss();
//                Toast.makeText(context, "取消配置网络", Toast.LENGTH_SHORT).show();
                wifiConn.setStop(CircleLoading.END);
            }
        } else {
            Toast.makeText(context, "请输入密码或者wifi名称", Toast.LENGTH_SHORT).show();
        }
    }
}
