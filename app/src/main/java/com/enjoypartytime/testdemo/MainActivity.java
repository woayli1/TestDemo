package com.enjoypartytime.testdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.enjoypartytime.testdemo.canvas.CanvasActivity;
import com.enjoypartytime.testdemo.diyView.DiyViewActivity;
import com.enjoypartytime.testdemo.jump.JumpActivity;
import com.enjoypartytime.testdemo.lazyLoad.LazyLoadActivity;
import com.enjoypartytime.testdemo.media.MediaActivity;
import com.enjoypartytime.testdemo.okhttp.OkhttpActivity;
import com.enjoypartytime.testdemo.mvp.MvpActivity;
import com.enjoypartytime.testdemo.opengl.OpenglActivity;
import com.enjoypartytime.testdemo.shop.ShopActivity;
import com.enjoypartytime.testdemo.udp.UdpActivity;
import com.enjoypartytime.testdemo.utils.OtherUtil;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OtherUtil.getInstance();

        TextView tvJump = findViewById(R.id.tv_jump);
        tvJump.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, JumpActivity.class);
            startActivity(intent);
        });

        TextView tvOkHttp = findViewById(R.id.tv_ok_http);
        tvOkHttp.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, OkhttpActivity.class);
            startActivity(intent);
        });

        TextView tvMedia = findViewById(R.id.tv_media);
        tvMedia.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MediaActivity.class);
            startActivity(intent);
        });

        TextView tvMvp = findViewById(R.id.tv_mvp);
        tvMvp.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MvpActivity.class);
            startActivity(intent);
        });

        TextView tvShop = findViewById(R.id.tv_shop);
        tvShop.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ShopActivity.class);
            startActivity(intent);
        });

        TextView tvOpengl = findViewById(R.id.tv_opengl);
        tvOpengl.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, OpenglActivity.class);
            startActivity(intent);
        });

        TextView tvCanvas = findViewById(R.id.tv_canvas);
        tvCanvas.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CanvasActivity.class);
            startActivity(intent);
        });

        TextView tvDiyView = findViewById(R.id.tv_diy_view);
        tvDiyView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DiyViewActivity.class);
            startActivity(intent);
        });

        TextView tvLazyLoad = findViewById(R.id.tv_lazy_load);
        tvLazyLoad.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LazyLoadActivity.class);
            startActivity(intent);
        });

        TextView tvUdp = findViewById(R.id.tv_udp);
        tvUdp.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, UdpActivity.class);
            startActivity(intent);
        });

    }

}