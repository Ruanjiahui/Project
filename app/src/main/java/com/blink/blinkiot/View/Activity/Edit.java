package com.blink.blinkiot.View.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.data_sdk.Database.GetDatabaseData;
import com.example.administrator.data_sdk.SystemUtil.TimeTool;
import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.blink.blinkiot.Other.DataBase.DataHandler;
import com.blink.blinkiot.Other.DataBase.DatabaseOpera;
import com.blink.blinkiot.Other.DatabaseTableName;
import com.blink.blinkiot.R;

import java.util.Map;

/**
 * Created by Administrator on 2016/7/13.
 */
public class Edit extends BaseActivity implements RadioGroup.OnCheckedChangeListener, NumberPicker.OnValueChangeListener {
    private View view = null;

    private EditText personEdit = null;

    private String content, FLAG;
    private RadioGroup personRadio = null;
    private RadioButton radioButton, radioButton1 = null;
    private LinearLayout personPick = null;
    private NumberPicker numberPicker1, numberPicker2, numberPicker3 = null;
    private RelativeLayout personRelative;
    private ImageView personLogo;
    private TextView OldText = null;
    private String birthday = "";
    private String hint = "";
    private String sex = null;
    private int sceneID = 0;

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


        personRelative = (RelativeLayout) view.findViewById(R.id.personRelative);
        personLogo = (ImageView) view.findViewById(R.id.personLogo);
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
        setRightTitle(getSystemText(R.string.SaveTitle));
        setTopTitleColor(R.color.White);

        switch (FLAG) {
            case "name":
                setTitle(getSystemText(R.string.EditName));
                personEdit.setVisibility(View.VISIBLE);
                hint = getSystemText(R.string.EditNameHint);
                setHint();
                break;
            case "height":
                setTitle(getSystemText(R.string.EditHeight));
                personEdit.setVisibility(View.VISIBLE);
                hint = getSystemText(R.string.EditNameHint);
                setHint();
                break;
            case "weight":
                setTitle(getSystemText(R.string.EditWeight));
                personEdit.setVisibility(View.VISIBLE);
                hint = getSystemText(R.string.EditWeightHint);
                setHint();
                break;
            case "birthday":
                setTitle(getSystemText(R.string.EditBirthday));
                personPick.setVisibility(View.VISIBLE);
                if (content != null && !"".equals(content) && content.length() != 0) {
                    int[] a = getbirthday(content);
                    year = a[0];
                    month = a[1];
                    day = a[2];
                    OldText.setText(TimeTool.getDayCount(year, month, day) + "");
                }
                NumberPicker();
                break;
            case "sex":
                setTitle(getSystemText(R.string.EditSex));
                personRadio.setVisibility(View.VISIBLE);
                if (getSystemText(R.string.SexMan).equals(content)) {
                    radioButton.setChecked(true);
                } else if (getSystemText(R.string.SexLady).equals(content)) {
                    radioButton1.setChecked(true);
                }
                break;
            //场景新建
            case "scene":
                setTitle(getSystemText(R.string.SceneName));
                hint = getSystemText(R.string.EditSceneHint);
                personRelative.setVisibility(View.VISIBLE);
                personEdit.setVisibility(View.VISIBLE);
                Map<String, String> map = new GetDatabaseData().Query(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.SceneName, new String[]{"max(sceneID)"}, "", null, "", "", "", "");
                if ("".equals(content)) {
                    if (map.get("max(sceneID)") != null)
                        sceneID = Integer.parseInt(map.get("max(sceneID)"));
                    sceneID += 1;
                    personEdit.setHint(hint);
                } else {
                    sceneID = Integer.parseInt(content);
                    hint = new GetDatabaseData().Query(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.SceneName, null, "sceneID = ?", new String[]{content}, "", "", "", "").get("sceneName");
                    personEdit.setText(hint);
                }
                break;
        }
        setContent(view);

        personRadio.setOnCheckedChangeListener(this);
    }

    private String getSystemText(int resid) {
        return getResources().getString(resid);
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
            case "scene":
                if (personEdit.getText().toString() == null || "".equals(personEdit.getText().toString()))
                    return;
                databaseOpera.DataInert(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.SceneName, DataHandler.getSceneContentValues(sceneID + "", personEdit.getText().toString()), true, "sceneID = ?", new String[]{sceneID + ""}, "sceneID = ?", new String[]{sceneID + ""});
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
                sex = getSystemText(R.string.SexMan);
                break;
            case R.id.radioButton1:
                sex = getSystemText(R.string.SexLady);
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
        OldText.setText(TimeTool.getDayCount(year, month, day) + "");
    }
}
