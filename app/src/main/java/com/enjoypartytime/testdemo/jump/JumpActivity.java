package com.enjoypartytime.testdemo.jump;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.enjoypartytime.testdemo.R;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/21
 */
public class JumpActivity extends Activity {

    protected static final String TAG = "JumpActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);
        Log.d(TAG, "AAA-->:" + TAG + " -->onCreate");

        TextView tvJump = findViewById(R.id.tv_jump);
        tvJump.setOnClickListener(view -> {
            Intent intent = new Intent(JumpActivity.this, Jump2Activity.class);
            startActivity(intent);
        });
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "AAA-->:" + TAG + " -->onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "AAA-->:" + TAG + " -->onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "AAA-->:" + TAG + " -->onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "AAA-->:" + TAG + " -->onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "AAA-->:" + TAG + " -->onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "AAA-->:" + TAG + " -->onDestroy");
    }
}
