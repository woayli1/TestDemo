package com.enjoypartytime.testdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.enjoypartytime.testdemo.canvas.CanvasActivity;
import com.enjoypartytime.testdemo.jump.JumpActivity;
import com.enjoypartytime.testdemo.media.MediaActivity;
import com.enjoypartytime.testdemo.okhttp.OkhttpActivity;
import com.enjoypartytime.testdemo.mvp.MvpActivity;
import com.enjoypartytime.testdemo.opengl.OpenglActivity;
import com.enjoypartytime.testdemo.shop.ShopActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


    }

}