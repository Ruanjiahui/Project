package com.tencent.mm.plugin.exdevice.jni;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public final class C2JavaExDevice
{
  public static final int AIR_KISS_CALL_BACK = 1;
  private static final String TAG = "MicroMsg.exdevice.C2JavaExDevice";
  public static C2JavaExDevice mInstance = null;
  private static Object mLock = new byte[1];
  private Handler mHandler;

  private void C2JavaExDevice()
  {
  }

  public static C2JavaExDevice getInstance()
  {
    if (mInstance == null)
      synchronized (mLock)
      {
        if (mInstance == null)
        {
          C2JavaExDevice localC2JavaExDevice1 = new C2JavaExDevice();
          mInstance = localC2JavaExDevice1;
          return localC2JavaExDevice1;
        }
        C2JavaExDevice localC2JavaExDevice2 = mInstance;
        return localC2JavaExDevice2;
      }
    return mInstance;
  }

  public static void onAirKissCallback(int paramInt1, int paramInt2)
  {
    Log.i("C2JavaExDevice", "onAirKissCallbackerrType = " + paramInt1 + "errCode =" + paramInt2);
    getInstance().mHandler.sendMessage(Message.obtain(getInstance().mHandler, 1, paramInt1, paramInt2));
  }

  public void setHandler(Handler paramHandler)
  {
    if (paramHandler == null)
      throw new IllegalArgumentException("null == aHandler");
    this.mHandler = paramHandler;
  }
}

/* Location:           \\psf\Home\Desktop\AndroidDecode\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.plugin.exdevice.jni.C2JavaExDevice
 * JD-Core Version:    0.6.0
 */