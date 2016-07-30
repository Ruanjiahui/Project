package com.tencent.mm.plugin.exdevice.jni;

public final class Java2CExDevice {

    static {
        System.loadLibrary("stlport_shared");
        System.loadLibrary("wechatxlog");
        System.loadLibrary("wechataccessory");
        Java2CExDevice.onCreate();
        Java2CExDevice.initBluetoothAccessoryLib();
    }

    public static native void closeBluetoothAccessoryLib();

    public static native int createChannel(long paramLong, LongWrapper paramLongWrapper);

    public static native void destroyChannel(long paramLong);

    public static native void initBluetoothAccessoryLib();

    public static native void onBluetoothConnected(long paramLong);

    public static native void onBluetoothDisconnected(long paramLong);

    public static native void onBluetoothError(long paramLong, int paramInt);

    public static native void onBluetoothRecvData(long paramLong, byte[] paramArrayOfByte);

    public static native void onBluetoothSendDataCompleted(long paramLong);

    public static native void onBluetoothSessionCreated(long paramLong1, long paramLong2, long paramLong3);

    public static native void onCreate();

    public static native void setChannelSessionKey(long paramLong, byte[] paramArrayOfByte);

    public static native int startAirKiss(String paramString1, String paramString2, byte[] paramArrayOfByte, long paramLong);

    public static native int startAirKissWithInter(String paramString1, String paramString2, byte[] paramArrayOfByte, long paramLong, int paramInt1, int paramInt2);

    public static native int startChannelService(long paramLong);

    public static native int startTask(long paramLong, short paramShort, AccessoryCmd paramAccessoryCmd, byte[] paramArrayOfByte);

    public static native void stopAirKiss();

    public static native void stopChannelService(long paramLong);

    public static native void stopTask(long paramLong);

    public static class AccessoryCmd {
        public long channelID;
        public int reqCmdID;
        public int respCmdID;
    }

    public static class LongWrapper {
        public long value;
    }
}

/* Location:           \\psf\Home\Desktop\AndroidDecode\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.plugin.exdevice.jni.Java2CExDevice
 * JD-Core Version:    0.6.0
 */