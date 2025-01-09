package com.enjoypartytime.testdemo.opengl.camera.cameraXFilter;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.utils.ShaderManager;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.AttachPopupView;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/12/4
 * 照相机 + 滤镜
 */
public class CameraXFilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_x_filter);

        TextView tvClose = findViewById(R.id.tv_close);
        TextView tvFilter = findViewById(R.id.tv_filter);
        CameraXFilterView cameraXFilterView = findViewById(R.id.camera_x_view);

        AttachPopupView attachPopupView = new XPopup.Builder(this)
                .isViewMode(true)
                .hasShadowBg(false)
                .offsetX(50)
                .atView(tvFilter)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                .asAttachList(new String[]{"默认效果", "横向二分屏", "纵向二分屏", "底片效果", "黑白效果", "灰色效果",
                        },
                        new int[]{},
                        (position, text) -> {
                            int filterType = ShaderManager.CAMERA_SHADER_BASE;
                            if (position == 0) {
                                filterType = ShaderManager.CAMERA_SHADER_BASE;
                            } else if (position == 1) {
                                filterType = ShaderManager.CAMERA_SHADER_X_2;
                            } else if (position == 2) {
                                filterType = ShaderManager.CAMERA_SHADER_Y_2;
                            } else if (position == 3) {
                                filterType = ShaderManager.CAMERA_SHADER_NEGATIVE;
                            } else if (position == 4) {
                                filterType = ShaderManager.CAMERA_SHADER_BLACK_WHITE;
                            } else if (position == 5) {
                                filterType = ShaderManager.CAMERA_SHADER_GRAY;
                            }
                            cameraXFilterView.setFilterType(filterType);
                        }, 0, 0/*, Gravity.LEFT*/);

        tvClose.setOnClickListener(view -> finish());

        tvFilter.setOnClickListener(view -> attachPopupView.show());
    }

}
