package com.ruan.project.View;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.data_sdk.SystemUtil.TimeTool;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.ruan.project.Moudle.Device;
import com.ruan.project.R;

/**
 * Created by Administrator on 2016/8/4.
 */
public class MyTimeDialog extends Dialog implements NumberPicker.OnValueChangeListener, View.OnClickListener {

    private Context context = null;
    private String[] number = null;

    protected MyTimeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);

        this.context = context;
        init();
    }

    /**
     * Creates a dialog window that uses a custom dialog style.
     * <p/>
     * The supplied {@code context} is used to obtain the window manager and
     * base theme used to present the dialog.
     * <p/>
     * The supplied {@code theme} is applied on top of the context's theme. See
     * <a href="{@docRoot}guide/topics/resources/available-resources.html#stylesandthemes">
     * Style and Theme Resources</a> for more information about defining and
     * using styles.
     *
     * @param context    the context in which the dialog should run
     * @param themeResId a style resource describing the theme to use for the
     *                   window, or {@code 0} to use the default dialog theme
     */
    public MyTimeDialog(Context context, int themeResId) {
        super(context, themeResId);

        this.context = context;
        init();
    }

    /**
     * Creates a dialog window that uses the default dialog theme.
     * <p/>
     * The supplied {@code context} is used to obtain the window manager and
     * base theme used to present the dialog.
     *
     * @param context the context in which the dialog should run
     */
    public MyTimeDialog(Context context) {
        this(context, 0);
    }

    private RelativeLayout timeRelayout;
    private TextView timeCancal;
    private TextView timeEnter;
    private NumberPicker numberPicker;
    private NumberPicker numberPicker1;

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.mytimedialog, null);

        number = new String[2];

        timeRelayout = (RelativeLayout) view.findViewById(R.id.timeRelayout);
        timeCancal = (TextView) view.findViewById(R.id.timeCancal);
        timeEnter = (TextView) view.findViewById(R.id.timeEnter);
        numberPicker = (NumberPicker) view.findViewById(R.id.numberPicker);
        numberPicker1 = (NumberPicker) view.findViewById(R.id.numberPicker1);

        DensityUtil.setRelWidth(timeRelayout , BaseActivity.width / 3 * 2);



        NumberPicker();

        this.setContentView(view);

        timeRelayout.setOnClickListener(this);
        timeCancal.setOnClickListener(this);
        timeEnter.setOnClickListener(this);
    }


    /**
     * 初始化数字选择器
     */
    private void NumberPicker() {
        //时
        numberPicker.setMaxValue(23);
        numberPicker.setMinValue(0);
        numberPicker.setValue(Integer.parseInt(TimeTool.getTime24Hour()));
        numberPicker.setOnValueChangedListener(this);
        //分
        numberPicker1.setMaxValue(59);
        numberPicker1.setMinValue(0);
        numberPicker1.setValue(Integer.parseInt(TimeTool.getTimeMinuts()));
        numberPicker1.setOnValueChangedListener(this);

        number[0] = String.valueOf(TimeTool.getTime24Hour());
        number[1] = String.valueOf(TimeTool.getTimeMinuts());
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.timeCancal:
                if (myTimeClick == null)
                    this.dismiss();
                else
                    myTimeClick.Cancal(position);
                break;
            case R.id.timeEnter:
                if (myTimeClick != null)
                    myTimeClick.Enter(position);
                break;
            case R.id.timeRelayout:
                this.dismiss();
                break;
        }
    }

    private MyTimeClick myTimeClick = null;
    private int position = 0;

    /**
     * 对话框点击事件 的外部接口
     *
     * @param myTimeClick
     */
    public void DialogClick(MyTimeClick myTimeClick , int position) {
        this.myTimeClick = myTimeClick;
        this.position = position;
    }

    /**
     * 获取两个数字选择器的接口
     *
     * @return
     */
    public String[] getNumber() {
        return number;
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
        switch (picker.getId()) {
            case R.id.numberPicker:
                number[0] = String.valueOf(newVal);
                break;
            case R.id.numberPicker1:
                number[1] = String.valueOf(newVal);
                break;
        }
    }

    /**
     * 点击事件的接口
     */
    public interface MyTimeClick {


        public void Enter(int position);


        public void Cancal(int position);

    }
}
