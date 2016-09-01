package com.blink.blinkiot.Moudle;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/29.
 */
public class UserDevice extends Device implements Parcelable {

    public String userID = null;
    public String sceneID = null;
    public String deviceMac = null;
    public String deviceOnline = null;
    public String devicePORT = null;
    public String deviceIP = null;
    public String deviceRemarks = null;
    public String deviceOnlineStatus = null;
    public String deviceSocketFlag = null;

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
        deviceModel = in.readString();
        deviceType = in.readString();
        deviceTypeID = in.readString();
        deviceOnlineStatus = in.readString();
        deviceSocketFlag = in.readString();

    }

    public String getDeviceOnlineStatus() {
        return deviceOnlineStatus;
    }

    public void setDeviceOnlineStatus(String deviceOnlineStatus) {
        this.deviceOnlineStatus = deviceOnlineStatus;
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

    public String getDeviceSocketFlag() {
        return deviceSocketFlag;
    }

    public void setDeviceSocketFlag(String deviceSocketFlag) {
        this.deviceSocketFlag = deviceSocketFlag;
    }

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
        dest.writeString(deviceOnlineStatus);
        dest.writeString(deviceSocketFlag);
    }
}
