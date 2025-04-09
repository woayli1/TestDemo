package com.enjoypartytime.testdemo.udp;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.enjoypartytime.testdemo.R;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private ExecutorService executorService;
    private DatagramSocket datagramSocket;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udp);

        executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            try {
                datagramSocket = new DatagramSocket(null);
                datagramSocket.setReuseAddress(true);
                datagramSocket.bind(new InetSocketAddress(RECEIVE_PORT));
                while (listenStatus) {
                    DatagramPacket datagramPacket = new DatagramPacket(new byte[512], 512);
                    datagramSocket.receive(datagramPacket); // 接收数据
                    byte[] arr = datagramPacket.getData(); // 获取数据
                    int len = datagramPacket.getLength(); // 获取有效长度
                    LogUtils.json(arr);
                    dealMsg(arr, len);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
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
//        LogUtils.d(bytesToHex(arr));
        LogUtils.json(transformCode(bytes));
    }

    private int[] transformCode(byte[] arr) {
        int[] ret = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ret[i] = Byte.toUnsignedInt(arr[i]);
        }
        return ret;
    }


//    private static String bytesToHex(byte[] bytes) {
//        StringBuilder sb = new StringBuilder();
//        for (byte aByte : bytes) {
//            String hex = Integer.toHexString(aByte & 0xFF);
//            if (hex.length() < 2) {
//                sb.append(0);
//            }
//            sb.append(hex);
//        }
//        return sb.toString();
//    }

    @Override
    protected void onStop() {
        super.onStop();

        listenStatus = false;

        executorService.execute(() -> {
            if (datagramSocket != null) {
                datagramSocket.disconnect();
                datagramSocket.close();
                datagramSocket = null;
            }
            executorService.shutdown();
        });
    }
}
