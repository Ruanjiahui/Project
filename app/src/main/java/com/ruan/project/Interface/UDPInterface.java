package com.ruan.project.Interface;

/**
 * Created by Administrator on 2016/7/19.
 */
public interface UDPInterface {


    public interface HandlerMac {

        /**
         * 这个方法获取Mac值
         * //0 储存接收的数据
         * //1 储存接收数据的长度
         * //2 储存接收的地址
         * //3 储存接收的端口
         *
         * @param objects 这个Object数组里面包含一些列的设备信息
         */
        public void getMac(Object[] objects);


        /**
         * 超时
         * @param position
         */
        public void Error(int position);
    }

    public interface HandlerInfo {

        /**
         * 这个方法获取设备信息
         *
         * @param objects 这个Object数组里面包含一些列的设备信息
         */
        public void getInfo(Object[] objects);
    }
}
