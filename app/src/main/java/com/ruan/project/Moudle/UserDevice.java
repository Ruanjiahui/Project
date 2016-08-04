package com.ruan.project.Moudle;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/29.
 */
public class UserDevice extends Device implements Parcelable {

    protected String userID = null;
    protected String sceneID = null;
    protected String deviceMac = null;
    protected String deviceOnline = null;
    protected String devicePORT = null;
    protected String deviceIP = null;
    protected String deviceRemarks = null;

    public UserDevice() {
    }

    protected UserDevice(Parcel in) {
        userID = in.readString();
        sceneID = in.readString();
        deviceName = in.readString();
        deviceID = in.readString();
        deviceMac = in.readString();
        devicePic = in.readString();
        deviceOnline = in.readString();
        devicePORT = in.readString();
        deviceIP = in.readString();
        deviceRemarks = in.readString();
        deviceModel= in.readString();
        deviceType= in.readString();
        deviceTypeID= in.readString();

    }

    public static final Creator<UserDevice> CREATOR = new Creator<UserDevice>() {
        @Override
        public UserDevice createFromParcel(Parcel in) {
            return new UserDevice(in);
        }

        @Override
        public UserDevice[] newArray(int size) {
            return new UserDevice[size];
        }
    };

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSceneID() {
        return sceneID;
    }

    public void setSceneID(String sceneID) {
        this.sceneID = sceneID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    public String getDevicePic() {
        return devicePic;
    }

    public void setDevicePic(String devicePic) {
        this.devicePic = devicePic;
    }

    public String getDeviceOnline() {
        return deviceOnline;
    }

    public void setDeviceOnline(String deviceOnline) {
        this.deviceOnline = deviceOnline;
    }

    public String getDevicePORT() {
        return devicePORT;
    }

    public void setDevicePORT(String devicePORT) {
        this.devicePORT = devicePORT;
    }

    public String getDeviceIP() {
        return deviceIP;
    }

    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }

    public String getDeviceRemarks() {
        return deviceRemarks;
    }

    public void setDeviceRemarks(String deviceRemarks) {
        this.deviceRemarks = deviceRemarks;
    }

    public void getUserDeviceMoudle(ArrayList<Map<String, String>> list) {
        setDeviceID(list.get(0).get("deviceID"));
        setDeviceIP(list.get(0).get("deviceIP"));
        setDeviceMac(list.get(0).get("deviceMac"));
        setDeviceName(list.get(0).get("deviceName"));
        setDeviceOnline(list.get(0).get("deviceOnline"));
        setDevicePic(list.get(0).get("devicePic"));
        setDevicePORT(list.get(0).get("devicePORT"));
        setDeviceRemarks(list.get(0).get("deviceRemarks"));
        setSceneID(list.get(0).get("sceneID"));
        setUserID(list.get(0).get("userID"));
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userID);
        dest.writeString(sceneID);
        dest.writeString(deviceName);
        dest.writeString(deviceID);
        dest.writeString(deviceMac);
        dest.writeString(devicePic);
        dest.writeString(deviceOnline);
        dest.writeString(devicePORT);
        dest.writeString(deviceIP);
        dest.writeString(deviceRemarks);
        dest.writeString(deviceModel);
        dest.writeString(deviceType);
        dest.writeString(deviceTypeID);
    }

    private ArrayList<UserDevice> list = null;

    /**
     * 将数据库的Map结构的数据封装对象
     *
     * @param map
     * @return
     */
    public ArrayList<UserDevice> getMoudle(ArrayList<Map<String, String>> map) {
        if (map.size() != 0) {
            list = new ArrayList<>();

            for (int i = 0; i < map.size(); i++) {
                UserDevice userDevice = new UserDevice();
                userDevice.setUserID(map.get(i).get("userID"));
                userDevice.setSceneID(map.get(i).get("sceneID"));
                userDevice.setDeviceRemarks(map.get(i).get("deviceRemarks"));
                userDevice.setDevicePORT(map.get(i).get("devicePORT"));
                userDevice.setDeviceID(map.get(i).get("deviceID"));
                userDevice.setDeviceIP(map.get(i).get("deviceIP"));
                userDevice.setDeviceMac(map.get(i).get("deviceMac"));
                userDevice.setDeviceName(map.get(i).get("deviceName"));
                userDevice.setDevicePic(map.get(i).get("devicePic"));
                userDevice.setDeviceOnline(map.get(i).get("deviceOnline"));
                list.add(userDevice);
            }
        }
        return list;
    }
}
