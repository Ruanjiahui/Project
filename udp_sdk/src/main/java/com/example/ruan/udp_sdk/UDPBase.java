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
public class UDPBase extends UDPSource implements UDPInterface.UDPReviced, UDPInterface.UDPSend , TimerHandler{


    private DatagramSocket datagramSocket = null;
    private DatagramPacket indatagramPacket = null;
    private DatagramPacket outdatagramPacket = null;
    private String IP = null;
    private int PORT = 0;
    private byte[] buffer = null;
    private int size = 5120;
    private Thread thread = null;
    private Timer timer = null;
    private UDPInterface.UDPHandler handler = null;
    private int position = 0;


    /**
     * 这个方法是初始化udp链接
     */
    @Override
    protected void Connect() {
        if (datagramSocket == null) {
            try {
                datagramSocket = new DatagramSocket();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 这个方法是发送信息
     *
     * @param IP   IP地址
     * @param PORT 传输端口号
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
    protected void Revice(int position , UDPInterface.UDPHandler handler) {
        this.position = position;
        this.handler = handler;
        thread = new Thread(new UDPReviced(position , this, handler));
        thread.start();
        timer = new Timer();
        timer.schedule(new MyTimerTask(this), 5000, 5000);
    }


    /**
     * 退出链接
     */
    @Override
    protected void unConnect() {
        if (datagramSocket != null)
            this.datagramSocket.close();
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
        try {
            buffer = new byte[size];
            indatagramPacket = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(indatagramPacket);
            //获取返回数据的长度
            objects[0] = buffer;
            objects[1] = indatagramPacket.getLength();
            objects[2] = indatagramPacket.getAddress().getHostName();
            objects[3] = indatagramPacket.getPort();
            timer.cancel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objects;
    }

    /**
     * 发送消息的接口
     */
    @Override
    public void Send(byte[] buffer) {
        try {
            outdatagramPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(IP), PORT);
            datagramSocket.send(outdatagramPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void timerHandler(Message msg) {
        thread.interrupt();
        timer.cancel();
        //超时
        handler.Error(position , 0);
    }

    @Override
    public Message timerRun() {
        return new Message();
    }
}
