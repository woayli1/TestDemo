package com.enjoypartytime.testdemo.opengl.camerax.filter;

import com.enjoypartytime.testdemo.utils.ShaderManager;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/12/4
 * 灰色效果
 */
public class CameraXFilterGray extends BaseCameraXFilter {

    public CameraXFilterGray() {
        super();
    }

    @Override
    protected ShaderManager.Param getProgram() {
        return ShaderManager.getParam(ShaderManager.CAMERA_SHADER_GRAY);
    }
}