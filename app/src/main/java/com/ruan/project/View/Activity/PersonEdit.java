package com.ruan.project.View.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.data_sdk.SystemUtil.TimeTool;
import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.ruan.project.Other.DataBase.DataHandler;
import com.ruan.project.Other.DataBase.DatabaseOpera;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.R;

/**
 * Created by Administrator on 2016/7/13.
 */
public class PersonEdit extends BaseActivity implements RadioGroup.OnCheckedChangeListener, NumberPicker.OnValueChangeListener {
    private View view = null;

    private EditText personEdit = null;

    private String content, FLAG;
    private RadioGroup personRadio = null;
    private RadioButton radioButton, radioButton1 = null;
    private LinearLayout personPick = null;
    private NumberPicker numberPicker1, numberPicker2, numberPicker3 = null;
    private TextView OldText = null;
    private String birthday = "";
    private String hint = "";
    private String sex = null;

    private int year = 1900, month = 1, day = 1;

    private int[] getbirthday(String day) {
        String str = "";
        int[] a = new int[3];
        int m = 0;
        for (int i = 0; i < day.length(); i++) {
            if (day.charAt(i) == '-') {
                a[m] = Integer.parseInt(str);
                m++;
                str = "";
            } else {
                str += day.charAt(i);
            }
        }
        a[m] = Integer.parseInt(str);
        return a;
    }

    /**
     * Start()
     */
    @Override
    public void init() {
        Bundle bundle = getIntent().getExtras();
        content = bundle.getString("data");
        FLAG = bundle.getString("flag");

        view = LayoutInflater.from(context).inflate(R.layout.personedit, null);

        personEdit = (EditText) view.findViewById(R.id.personEdit);
        personRadio = (RadioGroup) view.findViewById(R.id.personRadio);
        radioButton = (RadioButton) view.findViewById(R.id.radioButton);
        radioButton1 = (RadioButton) view.findViewById(R.id.radioButton1);
        personPick = (LinearLayout) view.findViewById(R.id.personPick);
        numberPicker1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
        numberPicker2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
        numberPicker3 = (NumberPicker) view.findViewById(R.id.numberPicker3);
        OldText = (TextView) view.findViewById(R.id.OldText);

        setContentColor(R.color.GreySmoke);
        setLeftTitleColor(R.color.White);
        setRightTitleColor(R.color.White);
        setTopColor(R.color.Blue);
        setTopTitleColor(R.color.White);

        switch (FLAG) {
            case "name":
                personEdit.setVisibility(View.VISIBLE);
                hint = "请输入你的姓名";
                setHint();
                break;
            case "height":
                personEdit.setVisibility(View.VISIBLE);
                hint = "请输入你的身高";
                setHint();
                break;
            case "weight":
                personEdit.setVisibility(View.VISIBLE);
                hint = "请输入你的体重";
                setHint();
                break;
            case "birthday":
                personPick.setVisibility(View.VISIBLE);
                if (content != null && !"".equals(content) && content.length() != 0) {
                    int[] a = getbirthday(content);
                    year = a[0];
                    month = a[1];
                    day = a[2];
                    OldText.setText(TimeTool.getDayCount(year , month , day) + "");
                }
                NumberPicker();
                break;
            case "sex":
                personRadio.setVisibility(View.VISIBLE);
                if ("男".equals(content)) {
                    radioButton.setChecked(true);
                } else if ("女".equals(content)) {
                    radioButton1.setChecked(true);
                }
                break;
        }
        setContent(view);

        personRadio.setOnCheckedChangeListener(this);
    }

    /**
     * 初始化编辑框的内容
     */
    private void setHint() {
        if (!"".equals(content))
            personEdit.setText(content);
        else
            personEdit.setHint(hint);
    }

    /**
     * 初始化数字选择器
     */
    private void NumberPicker() {
        //年
        numberPicker1.setMaxValue(2016);
        numberPicker1.setMinValue(1900);
        numberPicker1.setValue(year);
        numberPicker1.setOnValueChangedListener(this);
        //月
        numberPicker2.setMaxValue(12);
        numberPicker2.setMinValue(1);
        numberPicker2.setValue(month);
        numberPicker2.setOnValueChangedListener(this);
        //日
        numberPicker3.setMaxValue(TimeTool.getDay(year, month));
        numberPicker3.setMinValue(1);
        numberPicker3.setValue(day);
        numberPicker3.setOnValueChangedListener(this);

    }

    @Override
    public void setRightTextClick(View v) {
        DatabaseOpera databaseOpera = new DatabaseOpera(context);
        switch (FLAG) {
            case "name":
                databaseOpera.DataInert(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName, DataHandler.getContentValues("userName", personEdit.getText().toString()), true, "userID = ?", new String[]{"123456"}, "userID = ?", new String[]{"123456"});
                break;
            case "height":
                databaseOpera.DataInert(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName, DataHandler.getContentValues("userHeight", personEdit.getText().toString()), true, "userID = ?", new String[]{"123456"}, "userID = ?", new String[]{"123456"});
                break;
            case "weight":
                databaseOpera.DataInert(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName, DataHandler.getContentValues("userWeight", personEdit.getText().toString()), true, "userID = ?", new String[]{"123456"}, "userID = ?", new String[]{"123456"});
                break;
            case "birthday":
                birthday = year + "-" + month + "-" + day;
                databaseOpera.DataInert(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName, DataHandler.getContentValues("userBirthday", birthday), true, "userID = ?", new String[]{"123456"}, "userID = ?", new String[]{"123456"});
                break;
            case "sex":
                databaseOpera.DataInert(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserTableName, DataHandler.getContentValues("userSex", sex), true, "userID = ?", new String[]{"123456"}, "userID = ?", new String[]{"123456"});
                break;
        }
        Applications.getInstance().removeOneActivity(this);
    }

    /**
     * <p>Called when the checked radio button has changed. When the
     * selection is cleared, checkedId is -1.</p>
     *
     * @param group     the group in which the checked radio button has changed
     * @param checkedId the unique identifier of the newly checked radio button
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButton:
                sex = "男";
                break;
            case R.id.radioButton1:
                sex = "女";
                break;
        }
    }

    /**
     * Called upon a change of the current value.
     *
     * @param picker The NumberPicker associated with this listener.
     * @param oldVal The previous value.
     * @param newVal The new value.
     */
    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        if (picker == numberPicker1) {
            year = newVal;
        } else if (picker == numberPicker2) {
            month = newVal;
        } else if (picker == numberPicker3) {
            day = newVal;
        }
        numberPicker3.setMaxValue(TimeTool.getDay(year, month));
        OldText.setText(TimeTool.getDayCount(year , month , day) + "");
    }
}
