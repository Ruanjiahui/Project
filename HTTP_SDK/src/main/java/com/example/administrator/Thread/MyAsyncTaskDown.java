package com.example.administrator.Thread;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.administrator.Interface.HttpInterface;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/2/15.
 */
public class MyAsyncTaskDown extends AsyncTask {

    private HttpInterface.HttpConnect httpConnect = null;
    private HttpInterface.DownUploadHandler.Download httpHandler = null;
    private Object object = null;

    private String flag = null;

    public MyAsyncTaskDown(HttpInterface.HttpConnect httpConnect) {
        this.httpConnect = httpConnect;
    }

    public MyAsyncTaskDown(HttpInterface.HttpConnect httpConnect, HttpInterface.DownUploadHandler.Download httpHandler, String flag) {
        this.httpConnect = httpConnect;
        this.httpHandler = httpHandler;
        this.flag = flag;
    }

    //正在执行线程
    @Override
    protected Object doInBackground(Object[] params) {
        if ("Bitmap".equals(flag))
            object = httpConnect.downBitmap();
        else
            object = httpConnect.downFile();
        return null;
    }

    //被调用后立即执行，一般用来在执行后台任务前对UI做一些标记
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //此方法被执行，直接将进度信息更新到UI组件上。
    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    //更新主界面
    @Override
    protected void onPostExecute(Object o) {
        if ("Bitmap".equals(flag))
            httpHandler.handlerDownBitmap((Bitmap) object);
        else
            httpHandler.handlerDownFile((InputStream) o);
        super.onPostExecute(o);
    }
}
