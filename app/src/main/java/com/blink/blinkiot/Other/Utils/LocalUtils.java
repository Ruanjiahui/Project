package com.blink.blinkiot.Other.Utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by Administrator on 2016/8/11.
 */
public class LocalUtils implements AMapLocationListener {
    private Context context = null;
    public static AMapLocationClient locationClient = null;
    public static AMapLocationClientOption locationOption = null;
    private String cityName = null;
    private LocalHandle localHandle = null;

    public LocalUtils(Context context) {
        this.context = context;
    }

    public void getCityName(LocalHandle localHandle) {
        this.localHandle = localHandle;
        locationClient = new AMapLocationClient(context);
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为低功耗模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        // 设置定位监听
        locationClient.setLocationListener(this);
    }

    /**
     * 开始定位
     */
    public final static int MSG_LOCATION_START = 0;
    /**
     * 定位完成
     */
    public final static int MSG_LOCATION_FINISH = 1;
    /**
     * 停止定位
     */
    public final static int MSG_LOCATION_STOP = 2;

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (null != aMapLocation) {
            Message msg = mHandler.obtainMessage();
            msg.obj = aMapLocation;
            msg.what = MSG_LOCATION_FINISH;
            mHandler.sendMessage(msg);
        }
    }


    private Handler mHandler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_LOCATION_START:
                    break;
                //定位完成
                case MSG_LOCATION_FINISH:
                    AMapLocation loc = (AMapLocation) msg.obj;
                    cityName = getLocationStr(loc);
                    localHandle.getCity(cityName);
                    break;
                case MSG_LOCATION_STOP:
                    break;
                default:
                    break;
            }
        }

        ;
    };


    /**
     * 根据定位结果返回定位信息的字符串
     *
     * @param location
     * @return
     */
    public synchronized String getLocationStr(AMapLocation location) {
        if (null == location) {
            return null;
        }
        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
        if (location.getErrorCode() == 0) {
            return location.getCity();
        } else {
            //定位失败
            Toast.makeText(context, "定位失败", Toast.LENGTH_SHORT).show();
        }
        return "正在获取位置";
    }
}
