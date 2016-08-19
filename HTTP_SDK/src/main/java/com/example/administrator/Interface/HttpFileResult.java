package com.example.administrator.Interface;

/**
 * Created by Administrator on 2016/8/15.
 */
public interface HttpFileResult {

    /**
     * 下载软件开始
     */
    public void FileStart(int FLAG);


    /**
     * 下载软件进行
     */
    public void FileNow(Object[] values , int FLAG);

    /**
     * 文件处理结束调用的接口
     *
     * @param bytes 返回的字节数组
     * @param FLAG  返回的标识
     */
    public void FileEnd(byte[] bytes, int FLAG);


    /**
     * 下载软件错误
     *
     * @param error
     * @param FLAG
     */
    public void FileError(int error, int FLAG);


}
