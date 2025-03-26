package com.enjoypartytime.testdemo.udp;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.enjoypartytime.testdemo.R;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/24
 */
public class UdpActivity extends Activity {

    //接收端口号
    private final static int RECEIVE_PORT = 6454;

    //接收线程的循环标识
    private boolean listenStatus = true;

    private UdpReceiveThread udpReceiveThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udp);

        udpReceiveThread = new UdpReceiveThread();
        udpReceiveThread.start();
    }

    public class UdpReceiveThread extends Thread {
        @Override
        public void run() {
            try (DatagramSocket datagramSocket = new DatagramSocket(RECEIVE_PORT)) {
                while (listenStatus) {
                    DatagramPacket datagramPacket = new DatagramPacket(new byte[1024], 1024);
                    datagramSocket.receive(datagramPacket); // 接收数据

                    byte[] arr = datagramPacket.getData(); // 获取数据
                    int len = datagramPacket.getLength(); // 获取有效长度
                    dealMsg(arr, len);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void dealMsg(byte[] arr, int len) {
        //头部数据长度
        int title = 18;

        byte[] bytes = new byte[512];
        if (len - title >= 0) {
            System.arraycopy(arr, title, bytes, 0, len - title);
        }
        String msg = Arrays.toString(bytes);
        LogUtils.d(msg);
    }

    @Override
    protected void onStop() {
        super.onStop();
        listenStatus = false;
        udpReceiveThread.interrupt();
    }
}
