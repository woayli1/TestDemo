package com.enjoypartytime.testdemo.opengl.camera.cameraXFilter.filter;

import com.enjoypartytime.testdemo.utils.ShaderManager;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/12/4
 * 底片效果
 */
public class CameraXFilterNegative extends BaseCameraXFilter {

    public CameraXFilterNegative() {
        super();
    }

    @Override
    protected ShaderManager.Param getProgram() {
        return ShaderManager.getParam(ShaderManager.CAMERA_SHADER_NEGATIVE);
    }
}