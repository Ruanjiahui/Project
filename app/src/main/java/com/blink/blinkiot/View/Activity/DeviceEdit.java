package com.blink.blinkiot.View.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blink.blinkiot.Other.Adapter.LGAdapter;
import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.SystemUtil.SystemTool;
import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.ui_sdk.View.RefreshSideListView;
import com.blink.blinkiot.Controllar.FragmentControl;
import com.blink.blinkiot.Controllar.UdpOpera;
import com.blink.blinkiot.Interface.UDPInterface;
import com.blink.blinkiot.Moudle.Scene;
import com.blink.blinkiot.Moudle.UserDevice;
import com.blink.blinkiot.Other.Adapter.SideListViewAdapter;
import com.blink.blinkiot.Other.DataBase.DataHandler;
import com.blink.blinkiot.Other.DataBase.DatabaseOpera;
import com.blink.blinkiot.Other.DatabaseTableName;
import com.blink.blinkiot.Other.DeviceCode;
import com.blink.blinkiot.Other.System.NetWork;
import com.blink.blinkiot.R;

import java.util.ArrayList;


/**
 * Created by Soft on 2016/7/11.
 */
public class DeviceEdit extends BaseActivity implements TextWatcher, UDPInterface.HandlerMac, Animation.AnimationListener, AdapterView.OnItemClickListener {

    private View view = null;

    private Activity activity = null;
//    private Context context = null;

    private ImageView editLogo = null;
    private EditText editTitle = null;
    private EditText editSubTitle = null;
    private Button editBut = null;
    private ImageView editDrop = null;
    private ImageView editDelete = null;
    private EditText editScene = null;

    private String FLAG = "";
    //记录场景信息
    private String title, subtitle;
    private String tableName = null;

    private String databaseName = "";

    private String sceneID = "";

    private DatabaseOpera databaseOpera = null;
    private ArrayList<Object> sceneListObj = null;
    private ArrayList<Object> deviceListObj = null;
    private Scene scene = null;
    private UserDevice userDevice = null;

    private RelativeLayout deviceEditBackground;
    private View bottomMain = null;
    private ListView bottomListView = null;
    private TextView bottomText = null;
    private LGAdapter adapter = null;
    private boolean isVisiable = false;

    private ArrayList<Object> list = null;
    private Animation StopanimationBottom = null;
    private Animation StopanimationBack = null;

    public Context context = null;
    private Animation.AnimationListener animationListener = null;


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

        databaseOpera = new DatabaseOpera(context);


        if ("new".equals(tableName))
            databaseName = DatabaseTableName.DeviceTableName;
        else
            databaseName = DatabaseTableName.UserDeviceName;
        animationListener = this;

        list = new ArrayList<>();


        setTopColor(R.color.Blue);
        setTitle(getResources().getString(R.string.UpdataTitle));
        setRightTitleVisiable(false);
        setContentColor(R.color.GreySmoke);
        setLeftTitleColor(R.color.White);
        setTopTitleColor(R.color.White);

        deviceEditBackground = (RelativeLayout) view.findViewById(R.id.deviceEditBackground);
        editLogo = (ImageView) view.findViewById(R.id.editLogo);
        editTitle = (EditText) view.findViewById(R.id.editTitle);
        editSubTitle = (EditText) view.findViewById(R.id.editSubTitle);
        editBut = (Button) view.findViewById(R.id.editBut);
        editDrop = (ImageView) view.findViewById(R.id.editDrop);
        editScene = (EditText) view.findViewById(R.id.editScene);
        bottomMain = view.findViewById(R.id.bottomMain);
        bottomListView = (ListView) view.findViewById(R.id.bottomListView);
        bottomText = (TextView) view.findViewById(R.id.bottomText);
        editDelete = (ImageView) view.findViewById(R.id.editDelete);


        bottomMain.setVisibility(View.GONE);

        setContent(view);

        bottomMain.bringToFront();
        //监听editTitle输入事件
        deviceEditBackground.setOnClickListener(this);
        editTitle.addTextChangedListener(this);
        editBut.setOnClickListener(this);
        editDrop.setOnClickListener(this);
        editLogo.setOnClickListener(this);
        editDelete.setOnClickListener(this);

        DensityUtil.setRelHeight(bottomMain, BaseActivity.height / 2, new int[]{RelativeLayout.ALIGN_PARENT_BOTTOM});
    }

    private void setInit() {
        userDevice = new UserDevice();
        editTitle.setText(FLAG);
        //获取到数据
        if (deviceListObj != null && deviceListObj.size() != 0) {
            userDevice = (UserDevice) deviceListObj.get(0);
            editTitle.setText(userDevice.getDeviceName());
            sceneID = userDevice.getSceneID();
            if (userDevice.getDeviceModel() != null)
                editSubTitle.setText(userDevice.getDeviceModel());
            else
                editSubTitle.setText(userDevice.getDeviceRemarks());
        }

        //判断当场景数据不为空的时候进行处理
        if (sceneListObj != null && sceneListObj.size() != 0) {
            for (int i = 0; i < sceneListObj.size(); i++) {
                scene = (Scene) sceneListObj.get(i);
                //当用户设备表的场景ID等于场景表的ID时候就说明这个设备就是属于这个场景的
                if (scene.getSceneID().equals(sceneID))
                    editScene.setText(scene.getSceneName());
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

//        if (tableName.equals("edit")) {
        getDatabaseData("deviceID = ?", new String[]{FLAG});
//        }
        getBottomList();
        //设置初始化界面
        setInit();
    }

    private ArrayList<Object> getBottomList() {
        sceneListObj = databaseOpera.DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.SceneName, null, "", null, "", "", "", "", Scene.class, false);
        if (sceneListObj != null && sceneListObj.size() != 0)
            list = new FragmentControl(context).getBottomList(sceneListObj);
        return list;
    }

    /**
     * 获取数据的数据
     *
     * @param wherearg
     * @param whereargs
     */
    private void getDatabaseData(String wherearg, String[] whereargs) {
        deviceListObj = databaseOpera.DataQuerys(DatabaseTableName.DeviceDatabaseName, databaseName, null, wherearg, whereargs, "", "", "", "", UserDevice.class, true);
    }


    @Override
    public void Click(View v) {
        switch (v.getId()) {
            case R.id.editBut:
                //将设备添加到用户设备表
                title = editTitle.getText().toString();
                subtitle = editSubTitle.getText().toString();
                userDevice.setDeviceName(title);
                userDevice.setDeviceRemarks(subtitle);
                userDevice.setSceneID(sceneID);

                //配置完网络这个是否使用设备编辑，这个时候应当扫描周边设备获取在线设备
                if (tableName.equals("new")) {
                    if (SystemTool.isNetState(context) == NetWork.WIFI)
//                        //扫描局域网的设备
                        new UdpOpera(this).UDPDeviceScan(this, FLAG);
                    else
                        Toast.makeText(context, getResources().getString(R.string.ConnectWifi), Toast.LENGTH_SHORT).show();
                } else if (tableName.equals("edit")) {
                    Exists(userDevice, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName);
                }
                break;
            case R.id.editDrop:
                bottomText.setText(getResources().getString(R.string.SceneName));
                if (adapter == null) {
                    adapter = new LGAdapter(context, list, "ListView");
                    bottomListView.setAdapter(adapter);
                } else
                    adapter.RefreshData(list);
                if (!isVisiable) {
                    editBut.setVisibility(View.GONE);
                    startAnimation();
                } else
                    stopAnimation();
                bottomListView.setOnItemClickListener(this);
                break;
            case R.id.editLogo:
                CommonIntent.IntentActivity(context, picPick.class);
                break;
            case R.id.deviceEditBackground:
                if (isVisiable)
                    stopAnimation();
                break;
            case R.id.editDelete:
                editScene.setText("");
                editScene.setHint(getResources().getString(R.string.SelectScene));
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
     * you can use {@link #} in {@link #onTextChanged}
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

    /**
     * 这个方法获取Mac值
     * //0 储存接收的数据
     * //1 储存接收数据的长度
     * //2 储存接收的地址
     * //3 储存接收的端口
     *
     * @param position
     * @param objects  这个Object数组里面包含一些列的设备信息
     */
    @Override
    public void getMac(int position, Object[] objects) {
        //要是mac有数据则就说明是有新数据出现这个时候直接插入就行了
        if (objects != null) {
            String mac = new String((byte[]) objects[0], 0, (int) objects[1]);
            String IP = (String) objects[2];
            int PORT = (int) objects[3];
            userDevice.setDevicePORT(PORT + "");
            userDevice.setDeviceIP(IP);
            userDevice.setDeviceID(FLAG);
            userDevice.setDeviceMac(mac);
            userDevice.setDeviceOnline(String.valueOf(DeviceCode.ONLINE));
            userDevice.setDeviceOnlineStatus(DeviceCode.WIFI);
            Exists(userDevice, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName);
        }
    }


    /**
     * 退出页面将设置好的参数保存到数据库
     */
    private void Exists(UserDevice userDevice, String db, String Table_Name) {
        userDevice.setUserID("123456");
        ContentValues contentValues = DataHandler.getContentValue(context, UserDevice.class, userDevice, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName);
        databaseOpera.DataInert(db, Table_Name, contentValues, true, "deviceMac = ? and userID = ?", new String[]{userDevice.getDeviceMac(), "123456"}, "deviceMac = ? and userID = ?", new String[]{userDevice.getDeviceMac(), "123456"});
        Applications.getInstance().removeOneActivity(activity);
    }

    /**
     * 超时
     *
     * @param position
     */
    private boolean visiable = false;

    @Override
    public void Error(int position, int error) {
        if (!visiable) {
            Toast.makeText(context, getResources().getString(R.string.ConnectTimeout), Toast.LENGTH_SHORT);
            visiable = true;
        }
    }


    /**
     * 开始动画的操作
     */
    private void startAnimation() {
        bottomMain.setVisibility(View.VISIBLE);
        deviceEditBackground.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.bottom_out);
        Animation banAnimation = AnimationUtils.loadAnimation(context, R.anim.backgroudout);
        bottomMain.startAnimation(animation);
        deviceEditBackground.startAnimation(banAnimation);
        isVisiable = true;
    }

    /**
     * 停止动画的操作
     */
    private void stopAnimation() {
        StopanimationBottom = AnimationUtils.loadAnimation(context, R.anim.bottom_in);
        StopanimationBack = AnimationUtils.loadAnimation(context, R.anim.backgroudin);
        bottomMain.startAnimation(StopanimationBottom);
        deviceEditBackground.startAnimation(StopanimationBack);
        StopanimationBottom.setAnimationListener(animationListener);
        isVisiable = false;
    }

    /**
     * <p>Notifies the start of the animation.</p>
     *
     * @param animation The started animation.
     */
    @Override
    public void onAnimationStart(Animation animation) {

    }

    /**
     * <p>Notifies the end of the animation. This callback is not invoked
     * for animations with repeat count set to INFINITE.</p>
     *
     * @param animation The animation which reached its end.
     */
    @Override
    public void onAnimationEnd(Animation animation) {
        if (StopanimationBottom == animation) {
            editBut.setVisibility(View.VISIBLE);
            bottomMain.setVisibility(View.GONE);
            deviceEditBackground.setVisibility(View.GONE);
        }
    }

    /**
     * <p>Notifies the repetition of the animation.</p>
     *
     * @param animation The animation which was repeated.
     */
    @Override
    public void onAnimationRepeat(Animation animation) {

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
        scene = (Scene) sceneListObj.get(position);
        sceneID = scene.getSceneID();
        editScene.setText(scene.getSceneName());
        if (isVisiable)
            stopAnimation();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isVisiable)
                stopAnimation();
            else
                Applications.getInstance().removeOneActivity(this);
        }
        return false;
    }
}
