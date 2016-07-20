package com.ruan.project.View.Activity;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.ruan.project.Controllar.UdpOpera;
import com.ruan.project.Interface.UDPInterface;
import com.ruan.project.Other.DataBase.DataHandler;
import com.ruan.project.Moudle.Item;
import com.ruan.project.Other.Adapter.LGAdapter;
import com.ruan.project.Other.DataBase.CreateDataBase;
import com.ruan.project.Other.DataBase.DatabaseOpera;
import com.ruan.project.Other.DatabaseTableName;
import com.ruan.project.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Soft on 2016/7/9.
 */
public class Device extends BaseActivity implements AdapterView.OnItemClickListener, UDPInterface.HandlerMac{

    private View view = null;
    private ListView productList = null;
    private ArrayList<Object> list = null;
    private LGAdapter adapter = null;
    private DatabaseOpera databaseOpera = null;

    /**
     * Start()
     */
    @Override
    public void init() {
        view = LayoutInflater.from(context).inflate(R.layout.device, null);

        setTopColor(R.color.Blue);
        setTitle("添加设备");
        setRightTitleVisiable(false);
        setTopTitleColor(R.color.White);
        setLeftTitleColor(R.color.White);

        productList = (ListView) view.findViewById(R.id.productList);
        //实例化链表
        list = new ArrayList<>();
        databaseOpera = new DatabaseOpera(this);

        //一进来马上判断有没有设备数据库和设备表，如果没有则自动会创建数据库
        //第二个参数是数据库名称,第三个参数是数据库表的名称

        //（1）判断数据库是否存在不存在则创建数据库的设备表并且从服务器上面获取设备的数据
        //（2）数据库的表存在但是表里面没有数据  则是首先判断表里面有没有数据则从服务器上面获取数据
        //（3）本地数据库数据库里面有数据，则马上显示同时访问服务器是否有更新，有更新则将新的数据库获
        //     取到本地更新本地数据库，更新显示界面
        //如果返回false则说明没有该表，返回true则存在该表
        if (!new CreateDataBase().FirstDataBase(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.DeviceTableName)) {
            new DatabaseOpera(context).DataInert(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.DeviceTableName, "");
        } else {
            //从数据中获取数据出来
            ArrayList<Map<String, String>> map = new DatabaseOpera(context).DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.DeviceTableName);
            for (int i = 0; i < map.size(); i++)
                list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), map.get(i).get("deviceType"), ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), DensityUtil.dip2px(context, 60), map.get(i).get("deviceTypeID")));
        }
//        new CreateDataBase().FirstDataBase(context, DatabaseTableName.DeviceDatabaseName, DatabaseTableName.DeviceTableName);
        //从数据库里面获取数据进行处理
//        ListViewData();



        adapter = new LGAdapter(context, list, "ListView");
        productList.setAdapter(adapter);

        productList.setOnItemClickListener(this);

        //扫描局域网的设备
//        new UdpOpera(this).UDPDeviceScan(this);


        setContent(view);
    }

    private Object getItem(Drawable drawable, String text, Drawable right, int height, String FLAG) {
        Item item = new Item();

        item.setListImage(drawable);
        item.setListright(right);
        item.setListText(text);
        item.setHeight(height);
        item.setFLAG(FLAG);

        return item;
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
        CommonIntent.IntentActivity(context, DeviceList.class, ((Item) list.get(position)).getFLAG(), ((Item) list.get(position)).getListText());
    }

    /**
     * 这个方法获取Mac值
     *
     * @param objects 这个Object数组里面包含一些列的设备信息
     */
    @Override
    public void getMac(Object[] objects) {
        //创建一个Object对象数组
        //0 储存接收的数据
        //1 储存接收数据的长度
        //2 储存接收的地址
        //3 储存接收的端口
        //要是mac有数据则就说明是有新数据出现这个时候直接插入就行了
        String mac = new String((byte[]) objects[0], 0, (int) objects[1]);
        String IP = (String) objects[2];
        int PORT = (int) objects[3];
        databaseOpera.DataInert(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.DeviceTableName, DataHandler.getContentValues(mac, IP, PORT));
        //通过获取到设备的mac和IP端口之后可以对设备进行单播获取该设备的更多的设备信息
//        new UdpOpera(context).UDPDeviceInfo(IP, PORT, "123".getBytes(), this);

        //重写获取设备的信息
//        ListViewData();
        //检测搜索到新设备进行界面的更新
        adapter.RefreshData(list);
    }

    /**
     * 超时
     *
     * @param position
     */
    @Override
    public void Error(int position) {
    }

//    private void ListViewData() {
//        list = new ArrayList<>();
//        //获取不同类别的设备
//        ArrayList<Map<String, String>> map = new DatabaseOpera(context).DataQuerys(DatabaseTableName.DeviceDatabaseName, DatabaseTableName.DeviceTableName);
//        //没有该表则自动创建表，并且从服务器上面获取设备的数据
//        //从服务器上面获取的数据首先要存本地数据库，之后才显示到界面
//        //for (int i = 0; i < map.size(); i++)
//        list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), "空调", ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), DensityUtil.dip2px(context, 60), "1"));
//        list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), "电视", ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), DensityUtil.dip2px(context, 60), "2"));
//        list.add(getItem(ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), "电饭煲", ImageTransformation.Resouce2Drawable(context, R.mipmap.ic_launcher), DensityUtil.dip2px(context, 60), "3"));
//
//    }

}
