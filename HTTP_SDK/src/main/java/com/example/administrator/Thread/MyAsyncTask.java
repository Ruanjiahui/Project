package com.example.administrator.Thread;

import android.os.AsyncTask;

import com.example.administrator.Interface.HttpInterface;

/**
 * Created by Administrator on 2016/2/15.
 */
public class MyAsyncTask extends AsyncTask {

    private HttpInterface.HttpConnect httpConnect = null;
    private HttpInterface.DownUploadHandler.Upload httpHandler = null;
    private String flag = "";

    public MyAsyncTask(HttpInterface.HttpConnect httpConnect) {
        this.httpConnect = httpConnect;
    }

    public MyAsyncTask(HttpInterface.HttpConnect httpConnect, HttpInterface.DownUploadHandler.Upload httpHandler, String flag) {
        this.httpConnect = httpConnect;
        this.httpHandler = httpHandler;
        this.flag = flag;
    }

    //正在执行线程
    @Override
    protected Object doInBackground(Object[] params) {
        if ("File".equals(flag))
            httpConnect.UploadFile();
        else
            httpConnect.UploadInputStream();
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
        if (o != null) {
            if ("File".equals(flag))
                httpHandler.handlerUploadFile(o.toString());
            else
                httpHandler.handlerUploadBitmap(o.toString());
        }else{
            if ("File".equals(flag))
                httpHandler.handlerUploadFile(null);
            else
                httpHandler.handlerUploadBitmap(null);
        }
        super.onPostExecute(o);
    }
}
