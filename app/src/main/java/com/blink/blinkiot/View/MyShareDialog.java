package com.blink.blinkiot.View;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blink.blinkiot.R;
import com.example.administrator.data_sdk.SystemUtil.TimeTool;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;

/**
 * Created by Administrator on 2016/8/4.
 */
public class MyShareDialog extends Dialog implements View.OnClickListener {

    private Context context = null;
    private String[] number = null;

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
    public MyShareDialog(Context context, int themeResId) {
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
    public MyShareDialog(Context context) {
        this(context, 0);
    }

    private RelativeLayout shareRelayout;
    private TextView shareText;
    private TextView shareCancal = null;
    private TextView shareEnter = null;
    private EditText shareEdit = null;

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.mysharedialog, null);

        number = new String[2];

        shareRelayout = (RelativeLayout) view.findViewById(R.id.shareRelayout);
        shareText = (TextView) view.findViewById(R.id.shareText);
        shareCancal = (TextView) view.findViewById(R.id.shareCancal);
        shareEnter = (TextView) view.findViewById(R.id.shareEnter);
        shareEdit = (EditText) view.findViewById(R.id.shareEdit);

        DensityUtil.setRelWidth(shareRelayout, BaseActivity.width / 3 * 2);

        this.setContentView(view);

        shareRelayout.setOnClickListener(this);
        shareCancal.setOnClickListener(this);
        shareEnter.setOnClickListener(this);
    }

    /**
     * 获取对话框编辑框的文字
     *
     * @return
     */
    public String getEditText() {
        return shareEdit.getText().toString();
    }

    /**
     * 设置编辑框的内容
     *
     * @param result
     */
    public void setEditText(String result) {
        if (result != null && result.length() > 0 && !"".equals(result))
            shareEdit.setText(result);
    }

    /**
     * 设置编辑框的hint内容
     *
     * @param result
     */
    public void setEditHint(String result) {
        if (result != null && result.length() > 0 && !"".equals(result))
            shareEdit.setHint(result);
    }

    /**
     * 将数据传输对话框进行显示
     *
     * @param result
     */
    public void setShareText(String result) {
        shareText.setVisibility(View.VISIBLE);
        shareEdit.setVisibility(View.GONE);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shareCancal:
                if (dialogClick == null)
                    this.dismiss();
                else
                    dialogClick.Cancal(position);
                break;
            case R.id.shareEnter:
                if (dialogClick != null)
                    dialogClick.Enter(position);
                break;
            case R.id.timeRelayout:
                this.dismiss();
                break;
        }
    }

    private DialogClick dialogClick = null;
    private int position = 0;

    /**
     * 对话框点击事件 的外部接口
     *
     * @param dialogClick
     */
    public void DialogClick(DialogClick dialogClick, int position) {
        this.dialogClick = dialogClick;
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
}
