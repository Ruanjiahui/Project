package com.example.administrator.data_sdk.FileUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.example.administrator.data_sdk.FileUtilAbstract.FileRequest;
import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * Created by Administrator on 2016/3/14.
 * <p/>
 * 这个类是管理所以关于文件的操作类
 * <p/>
 * 比如有
 * 文本的创建，文件夹的创建，文件的读取，文件的写入，文件的判断，文件夹的判断，配置文件的读取，配置文件的写入，配置文件的创建等等
 */
public class FileTool {

    //创建FileRequest对象
    private static FileRequest fileRequest = null;
    /**
     * 这个方法是创建文件
     *
     * @param path
     */
    public static void CreateFolder(String path) {
        //如果该路径存在此文件夹则不创建否则就创建
        if (!FilePath.FileFail(path)) {
            File file = new File(path);
            //创建文件夹
            file.mkdirs();
        }
    }

    /**
     * 这个方法是判断文件的是否存在进行创建和删除自动备份的功能
     * @param filename
     * @param path
     */
    public static void JudgeFile(String filename, String path) {
        //如果该文件不存在则自动创建该路径
        if (!FilePath.FileFail(path + "/" + filename)) {
            CreateFolder(path);
            CreateFolder(path + "/old");
            return;
        }
        //如果备份的文件夹被删掉下次复制文件的是否会自动创建
        if (!FilePath.FileFail(path + "/old"))
            CreateFolder(path + "/old");
        //否则则删除原来的文件
        //为了安全默认自动备份可以手动删除
        //前面一个是初始地址，后面一个是新地址
        //这里的自动备份功能只能默认保留上一个版本其他版本则在上一个版本中删除以及覆盖
        CopyFile(path + "/" + filename, path + "/old/" , filename);
        new File(path + "/" + filename).delete();
    }

    /**
     * 这个方法也是保存文件的
     * 传入参数是文件类型
     *
     * @param files
     * @param fileName
     * @param path
     */
    public static void saveFileByte(File files, String fileName, String path) {
        //创建文件夹
        //如果拥有则不创建，否则会自动创建
        //如果拥有文件就直接删除该文件
        //该方法自动实现上面的操作包括自动备份功能
        JudgeFile(fileName, path);

        try {
            InputStream inputStream = new FileInputStream(files);

            fileRequest = new FileSource();
            fileRequest.WriteByte(inputStream , path , fileName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 这个是保存文件的方法
     * 传入参数是字节流
     *
     * @param inputStream
     * @param fileName
     * @param path
     */
    public static void saveFileByte(InputStream inputStream, String fileName, String path) {
        //创建文件夹
        //如果拥有则不创建，否则会自动创建
        //如果拥有文件就直接删除该文件
        JudgeFile(fileName, path);

        fileRequest = new FileSource();
        fileRequest.WriteByte(inputStream, path, fileName);

    }


    /**
     * 这个是保存二进制的图片
     *
     * @param bitmap
     * @param fileName
     * @param path
     */
    public static void savePictureByte(Bitmap bitmap, String fileName, String path) {
        //创建文件夹
        //如果拥有则不创建，否则会自动创建
        //如果拥有文件就直接删除该文件
        JudgeFile(fileName, path);

        File file = new File(path, fileName);


        //将图片写成二进制字节的方式
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);

        //将图片写入文件中
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(byteArrayOutputStream.toByteArray());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 这个是读取二进制的图片
     *
     * @param fileName
     * @param path
     */
    public static Drawable readPictureByteDrawable(Context context, String fileName, String path) {
        //创建文件夹
        //如果拥有则不创建，否则会自动创建文件夹
        if (!FilePath.FileFail(path + "/" + fileName))
            CreateFolder(path);

        InputStream fs = null;
        BufferedInputStream bs = null;
        try {
            fs = new FileInputStream(path + "/" + fileName);
            bs = new BufferedInputStream(fs);

            return ImageTransformation.InputStream2Drawable(context , fs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bs.close();
                fs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 这个方法读取SD卡的图片返回类型是Bitmap
     * 该方法由于内存处理没有readPictureByteDrawable这个这么好
     * 所以被放弃使用
     *
     * @param fileName
     * @param path
     * @return
     */
    @Deprecated
    public static Bitmap readPictureByteBitmap(String fileName, String path) {
        //创建文件夹
        //如果拥有则不创建，否则会自动创建文件夹
        if (!FilePath.FileFail(path + "/" + fileName))
            CreateFolder(path);

        InputStream fs = null;
        BufferedInputStream bs = null;
        try {
            fs = new FileInputStream(path + "/" + fileName);
            bs = new BufferedInputStream(fs);

            return ImageTransformation.InputStream2Bitmap(fs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bs.close();
                fs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 这个是读取文件的内容的方法
     * @param fileName
     * @param path
     * @return
     */
    public static String readFileByte(String fileName, String path) {
        //创建文件夹
        //如果拥有则不创建，否则会自动创建文件夹
        if (!FilePath.FileFail(path + "/" + fileName))
            CreateFolder(path);

        try {
            File file = new File(path, fileName);
            FileInputStream inputStream = new FileInputStream(file);
            //获取字节流的长度
            int lenght = inputStream.available();

            byte[] b = new byte[lenght];
            inputStream.read(b);


            return new String(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 这个是复制文件
     *
     * @param oldPath
     * @param newPath
     */
    public static void CopyFile(String oldPath, String newPath , String newName) {
        try {
            //将原来的文件写入字节流当中
            InputStream inputStream = new FileInputStream(oldPath);

            fileRequest = new FileSource();
            fileRequest.WriteByte(inputStream , newPath , newName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件MD5
     *
     * @param path
     * @return
     * @throws FileNotFoundException
     */
    public static String getMd5ByFile(String path) throws FileNotFoundException {
        File file = new File(path);
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }
}
