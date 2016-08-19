package com.example.administrator.Thread;

import android.os.AsyncTask;
import android.util.Log;

import com.example.administrator.HttpCode;
import com.example.administrator.Interface.HttpFileConnect;
import com.example.administrator.Interface.HttpFileResult;

/**
 * Created by Administrator on 2016/8/15.
 * <p/>
 * 字节流获取云端的线程
 */
public class HttpFileAsyncTask extends AsyncTask {

    private HttpFileConnect httpFileConnect = null;
    private HttpFileResult httpFileResult = null;
    private int FLAG = 0;

    public HttpFileAsyncTask(HttpFileConnect httpFileConnect, HttpFileResult httpFileResult, int FLAG) {
        this.httpFileConnect = httpFileConnect;
        this.httpFileResult = httpFileResult;
        this.FLAG = FLAG;
    }

    //正在执行线程
    @Override
    protected Object doInBackground(Object[] objects) {
        return httpFileConnect.connection();
    }

    //启动开始下载是第一个触发的方法
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        httpFileResult.FileStart(FLAG);
    }

    //更新主界面
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        //超时调用的接口
        if ((HttpCode.TIMEOUT + "").equals(new String((byte[])o , 0 , ((byte[]) o).length)))
            httpFileResult.FileError(HttpCode.TIMEOUT, FLAG);
            //其他错误调用的接口
        else if (o == null)
            httpFileResult.FileError(HttpCode.NULL, FLAG);
            //返回成功的接口
        else
            httpFileResult.FileEnd((byte[]) o, FLAG);
    }

    //此方法被执行，直接将进度信息更新到UI组件上。
    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }


    //取消下载的方法
    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    /**
     * 取消下载的操作
     */
    public void disConnection() {
        onCancelled();
    }
}
