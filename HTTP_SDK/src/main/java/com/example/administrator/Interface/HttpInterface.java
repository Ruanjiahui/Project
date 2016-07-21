package com.example.administrator.Interface;

import android.graphics.Bitmap;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/2/15.
 */
public interface HttpInterface {

    public interface HttpConnect {

        //这个是Http链接的接口方法
        public String connect();

        //这个是上传图片的方法接口
        public String UploadFile();

        //这个是上传文件的方法接口
        public String UploadInputStream();

        //这个是下载图片的链接的接口方法
        public Bitmap downBitmap();

        //这个是下载文件的链接接口方法
        public InputStream downFile();
    }

    public interface HttpHandler {
        //这个是处理Http返回来的结果
        public void handler(String result);

    }

    public interface DownUploadHandler {


        public interface Upload{
            //这个是处理上传图片的处理方法
            public void handlerUploadBitmap(String result);

            //这个是是处理上传文件的处理方法
            public void handlerUploadFile(String result);
        }

        public interface Download {
            //这个是处理下载文件的处理方法
            public void handlerDownFile(InputStream inputStream);

            //这个是处理下载的图片的处理方法
            public void handlerDownBitmap(Bitmap bitmap);
        }
    }

}
