package com.example.ruan.udp_sdk;

import android.os.Message;
import android.util.Log;

import com.example.ruan.udp_sdk.Thread.MyTimerTask;
import com.example.ruan.udp_sdk.Thread.UDPReviced;
import com.example.ruan.udp_sdk.Thread.UDPSend;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Timer;

/**
 * Created by Administrator on 2016/7/16.
 */
public class UDPBase extends UDPSource implements UDPListen.UDPReviced, UDPListen.UDPSend, TimerHandler {


    private DatagramSocket datagramSocket = null;
    private String IP = null;
    private int PORT = 0;
    private byte[] buffer = null;
    private int size = 5120;
    private Thread thread = null;
    private Timer timer = null;
    private UDPListen.UDPCallback callback = null;
    private int position = 0;
    private MyTimerTask myTimerTask = null;


    /**
     * 这个方法是初始化udp链接
     */
    @Override
    protected void Connect() {
        try {
            if (datagramSocket == null)
                datagramSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * 这个方法是发送信息
     *
     * @param IP     发送的IP
     * @param PORT   发送的端口
     * @param buffer 发送的数据
     */
    @Override
    protected void Send(String IP, int PORT, byte[] buffer) {
        this.IP = IP;
        this.PORT = PORT;
        new Thread(new UDPSend(this, buffer)).start();
    }


    /**
     * 这个方法是接收信息
     */
    @Override
    protected void Revice(int position, UDPListen.UDPCallback callback) {
        this.position = position;
        this.callback = callback;
        thread = new Thread(new UDPReviced(position, this, callback));
        thread.start();
    }


    /**
     * 退出链接
     */
    @Override
    protected void unConnect() {
        if (datagramSocket != null) {
            this.datagramSocket.close();
            datagramSocket = null;
        }
    }

    /**
     * 接收消息的接口
     *
     * @return
     */
    @Override
    public Object[] Reviced() {
        //创建一个Object对象数组
        //0 储存接收的数据
        //1 储存接收数据的长度
        //2 储存接收的地址
        //3 储存接收的端口
        Object[] objects = new Object[4];
        if (datagramSocket != null) {
            try {
                buffer = new byte[size];
                DatagramPacket indatagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(indatagramPacket);
                //获取返回数据的长度
                objects[0] = buffer;
                objects[1] = indatagramPacket.getLength();
                objects[2] = indatagramPacket.getAddress().getHostName();
                objects[3] = indatagramPacket.getPort();
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
            } catch (IOException e) {
                CloseUDP();
            }
            CloseUDP();
        }
        return objects;
    }

    /**
     * 发送消息的接口
     */
    @Override
    public void Send(byte[] buffer) {
        try {
            DatagramPacket outdatagramPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(IP), PORT);
            datagramSocket.send(outdatagramPacket);
        } catch (Exception e) {
            CloseUDP();
        }
    }

    @Override
    public void SendReults() {
        timer = new Timer();
        myTimerTask = new MyTimerTask(this);
        timer.schedule(myTimerTask, UDPConfig.time, UDPConfig.time);
    }

    @Override
    public void timerHandler(Message msg) {
        CloseUDP();
        //超时
        callback.CallError(position, 0);
    }

    @Override
    public Message timerRun() {
        return new Message();
    }


    private void CloseUDP() {
//        if (datagramSocket != null) {
//            datagramSocket.close();
//            datagramSocket = null;
//        }
        if (thread != null)
            thread.interrupt();
        if (timer != null)
            timer.cancel();
    }
}
