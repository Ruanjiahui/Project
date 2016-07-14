package com.ruan.project.View.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.ruan.project.Other.DataBase.CreateDataBase;
import com.ruan.project.Other.DataBase.DataHandler;
import com.ruan.project.Other.DataBase.DatabaseOpera;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.R;

import java.util.ArrayList;
import java.util.Map;


/**
 * Created by Soft on 2016/7/11.
 */
public class Edit extends BaseActivity implements TextWatcher {

    private View view = null;

    private Activity activity = null;
    private Context context = null;

    private ImageView editLogo = null;
    private TextView editTitle = null;
    private TextView editSubTitle = null;
    private Button editBut = null;

    private String FLAG = "";
    private ArrayList<Map<String, String>> list = null;
    private String title, subtitle;
    private String tableName = null;


    private String deviceID = "";

    /**
     * Start()
     */
    @Override
    public void init() {
        Bundle bundle = getIntent().getExtras();
        FLAG = bundle.getString("data");
        tableName = bundle.getString("flag");

        context = this;
        activity = (Activity) context;

        view = LayoutInflater.from(context).inflate(R.layout.edit, null);

        //判断数据库是否存在，存在则获取设备表的数据库、   通过设备的唯一ID获取到设备的信息 提供给用户是否修改备注和名称
        if (new CreateDataBase().FirstDataBase(context, DatabaseTableName.DeviceDatabaseName, tableName))
            list = new DatabaseOpera(context).DataQuerys(DatabaseTableName.DeviceDatabaseName, tableName, "deviceID = ?", new String[]{FLAG});


        setTopColor(R.color.Blue);
        setTitle("修改");
        setRightTitleVisiable(false);
        setContentColor(R.color.WhiteSmoke);
        setLeftTitleColor(R.color.White);
        setTopTitleColor(R.color.White);

        editLogo = (ImageView) view.findViewById(R.id.editLogo);
        editTitle = (TextView) view.findViewById(R.id.editTitle);
        editSubTitle = (TextView) view.findViewById(R.id.editSubTitle);
        editBut = (Button) view.findViewById(R.id.editBut);


        //获取到数据
        if (list != null) {
            deviceID = list.get(0).get("deviceID");
            editTitle.setText(list.get(0).get("deviceName"));
            if (list.get(0).get("deviceModel") != null)
                editSubTitle.setText(list.get(0).get("deviceModel"));
            else
                editSubTitle.setText(list.get(0).get("deviceRemarks"));
        }
        setContent(view);

        //监听editTitle输入事件
        editTitle.addTextChangedListener(this);
        editBut.setOnClickListener(this);
    }

    @Override
    public void Click(View v) {
        switch (v.getId()) {
            case R.id.editBut:
                //将设备添加到用户设备表
                title = editTitle.getText().toString();
                subtitle = editSubTitle.getText().toString();
                new DatabaseOpera(context).DataInert(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName, DataHandler.getContentValues("123456", list, title, subtitle), true, "deviceID = ? and userID = ?", new String[]{deviceID, "123456"}, "deviceID = ? and userID = ?", new String[]{deviceID, "123456"});
                Applications.getInstance().removeOneActivity(activity);
                break;
        }
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
     * you can use {@link Spannable#setSpan} in {@link #onTextChanged}
     * to mark your place and then look up from here where the span
     * ended up.
     *
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {
        //这里判断当标题输入框没有东西则按钮不允许点击，
        if (s.toString().length() == 0 || "".equals(s.toString()) || s.toString() == null) {
            editBut.setClickable(false);
            editBut.setEnabled(false);
            editBut.setBackground(context.getResources().getDrawable(R.drawable.button_down_blue));
        } else {
            editBut.setClickable(true);
            editBut.setEnabled(true);
            editBut.setBackground(context.getResources().getDrawable(R.drawable.button_selector_blue));
        }
    }
}
