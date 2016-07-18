package com.example.ruan.udp_sdk;

import android.util.Log;

import com.example.ruan.udp_sdk.Thread.UDPReviced;
import com.example.ruan.udp_sdk.Thread.UDPSend;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by Administrator on 2016/7/16.
 */
public class UDPBase extends UDPSource implements UDPInterface.UDPReviced, UDPInterface.UDPSend {


    private DatagramSocket datagramSocket = null;
    private DatagramPacket indatagramPacket = null;
    private DatagramPacket outdatagramPacket = null;
    private String IP = null;
    private int PORT = 0;
    private byte[] buffer = null;
    private int size = 10240;


    /**
     * 这个方法是初始化udp链接
     */
    @Override
    protected void Connect(int PORT) {
        if (datagramSocket == null) {
            try {
                datagramSocket = new DatagramSocket(PORT);
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
    protected void Revice(UDPInterface.UDPHandler handler) {
        new Thread(new UDPReviced(this, handler)).start();
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
    public byte[] Reviced() {
        try {
            buffer = new byte[size];
            indatagramPacket = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(indatagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
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
}