package com.ruan.project.View.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.Database.GetDatabaseData;
import com.example.administrator.data_sdk.Database.LoadClass;
import com.example.administrator.data_sdk.SystemUtil.SystemTool;
import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.ruan.project.Controllar.UdpOpera;
import com.ruan.project.Interface.PopWinOnClick;
import com.ruan.project.Interface.UDPInterface;
import com.ruan.project.Moudle.Device;
import com.ruan.project.Moudle.Item;
import com.ruan.project.Moudle.Scene;
import com.ruan.project.Moudle.UserDevice;
import com.ruan.project.Other.DataBase.CreateDataBase;
import com.ruan.project.Other.DataBase.DataHandler;
import com.ruan.project.Other.DataBase.DatabaseOpera;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.Other.System.NetWork;
import com.ruan.project.R;
import com.ruan.project.View.MyPopWindow;

import java.util.ArrayList;
import java.util.Map;

import javax.security.auth.login.LoginException;


/**
 * Created by Soft on 2016/7/11.
 */
public class DeviceEdit extends BaseActivity implements TextWatcher, PopWinOnClick, UDPInterface.HandlerMac {

    private View view = null;

    private Activity activity = null;
    private Context context = null;

    private ImageView editLogo = null;
    private EditText editTitle = null;
    private EditText editSubTitle = null;
    private Button editBut = null;
    private ImageView editDrop = null;
    private TextView editScene = null;

    private String FLAG = "";
    //记录场景信息
    private String title, subtitle, sceneName;
    private String tableName = null;

    private ArrayList<Object> map = null;
    private MyPopWindow popWindow = null;

    private String databaseName = "";

    private String deviceID = "";
    private String sceneID = "";

    private DatabaseOpera databaseOpera = null;
    private ArrayList<Object> sceneListObj = null;
    private ArrayList<Object> deviceListObj = null;
    private Scene scene = null;
    private UserDevice userDevice = null;

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
        //配置适配器的数据
        map = new ArrayList<>();


        if (tableName.equals("edit")) {
            databaseName = DatabaseTableName.UserDeviceName;
        } else if (tableName.equals("new")) {
            databaseName = DatabaseTableName.DeviceTableName;
        }

        sceneListObj = databaseOpera.DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.SceneName, null, "", null, "", "", "", "", Scene.class, false);
        deviceListObj = databaseOpera.DataQuerys(DatabaseTableName.DeviceDatabaseName, databaseName, "deviceID", FLAG, UserDevice.class, true);


        setTopColor(R.color.Blue);
        setTitle("修改");
        setRightTitleVisiable(false);
        setContentColor(R.color.GreySmoke);
        setLeftTitleColor(R.color.White);
        setTopTitleColor(R.color.White);

        editLogo = (ImageView) view.findViewById(R.id.editLogo);
        editTitle = (EditText) view.findViewById(R.id.editTitle);
        editSubTitle = (EditText) view.findViewById(R.id.editSubTitle);
        editBut = (Button) view.findViewById(R.id.editBut);
        editDrop = (ImageView) view.findViewById(R.id.editDrop);
        editScene = (TextView) view.findViewById(R.id.editScene);


        //设置初始化界面
        setInit();


        setContent(view);

        //监听editTitle输入事件
        editTitle.addTextChangedListener(this);
        editBut.setOnClickListener(this);
        editDrop.setOnClickListener(this);
        editLogo.setOnClickListener(this);
    }

    private Object getItem(String title, Drawable drawable) {
        Item item = new Item();
        item.setHomeText(title);
        item.setHomeImage(drawable);
        return item;
    }

    private void setInit() {
        //获取到数据
        if (deviceListObj != null && deviceListObj.size() != 0) {
            userDevice = (UserDevice) deviceListObj.get(0);
            deviceID = userDevice.getDeviceID();
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
                map.add(getItem(scene.getSceneName(), getResources().getDrawable(R.mipmap.cooker)));
            }
        }
    }

    @Override
    public void Click(View v) {
        switch (v.getId()) {
            case R.id.editBut:
                //将设备添加到用户设备表
                title = editTitle.getText().toString();
                subtitle = editSubTitle.getText().toString();
                sceneName = editScene.getText().toString();

                userDevice.setDeviceName(title);
                userDevice.setDeviceRemarks(subtitle);

                //配置完网络这个是否使用设备编辑，这个时候应当扫描周边设备获取在线设备
                if (tableName.equals("new")) {
                    if (SystemTool.isNetState(context) == NetWork.WIFI)
                        //扫描局域网的设备
                        new UdpOpera(this).UDPDeviceScan(this);
                    else
                        Toast.makeText(context, "请链接wifi", Toast.LENGTH_SHORT).show();
                } else if (tableName.equals("edit")) {
                    Exists(userDevice, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName);
                }
                break;
            case R.id.editDrop:
                //创建PopWindow的组件
                popWindow = new MyPopWindow(activity, map, BaseActivity.width / 2, BaseActivity.height / 2);
                popWindow.showPopupWindow(editLogo, MyPopWindow.UP, BaseActivity.width / 4, BaseActivity.height / 4);
                popWindow.setPopBackground(R.color.White);
                popWindow.setPopBackground(R.drawable.popcircle);
                popWindow.setPopListBackground(R.color.White);
                popWindow.setPopListSector(R.drawable.itemsector);
                popWindow.setOnPopWinItemClick(this);
                break;
            case R.id.editLogo:
                CommonIntent.IntentActivity(context, picPick.class);
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
     * 弹出窗口的点击事件
     *
     * @param parent   弹出窗口的所有组件
     * @param view
     * @param position 弹出窗口listview的个数
     * @param id
     */
    @Override
    public void OnPopItemClick(AdapterView<?> parent, View view, int position, long id) {
        scene = (Scene) sceneListObj.get(position);
        editScene.setText(scene.getSceneName());
        sceneID = scene.getSceneID();
        userDevice.setSceneID(sceneID);
        popWindow.disShow();
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
        String mac = new String((byte[]) objects[0], 0, (int) objects[1]);
        String IP = (String) objects[2];
        int PORT = (int) objects[3];
        userDevice.setDevicePORT(PORT + "");
        userDevice.setDeviceIP(IP);
        userDevice.setDeviceMac(mac);
        userDevice.setDeviceOnline("2");
        Exists(userDevice, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName);
    }

    /**
     * 退出页面将设置好的参数保存到数据库
     */
    private void Exists(UserDevice userDevice, String db, String Table_Name) {
        userDevice.setUserID("123456");
        ContentValues contentValues = DataHandler.getContentValues(context, userDevice, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.UserDeviceName);
        databaseOpera.DataInert(db, Table_Name, contentValues, true, "deviceID = ? and userID = ?", new String[]{userDevice.getDeviceID(), "123456"}, "deviceID = ? and userID = ?", new String[]{userDevice.getDeviceID(), "123456"});
//        if (!"".equals(sceneID) && sceneID != null)
//            new DatabaseOpera(context).DataInert(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.SceneName, DataHandler.getSceneContentValues(sceneID, sceneName), true, "sceneID = ?", new String[]{sceneID}, "sceneID = ?", new String[]{sceneID});
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
            Toast.makeText(context, "连接超时,请重写设置", Toast.LENGTH_SHORT);
            visiable = true;
        }
    }
}
