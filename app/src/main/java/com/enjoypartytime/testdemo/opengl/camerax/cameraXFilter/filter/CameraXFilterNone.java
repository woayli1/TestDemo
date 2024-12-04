package com.enjoypartytime.testdemo.opengl.camerax.cameraXFilter.filter;

import com.enjoypartytime.testdemo.utils.ShaderManager;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/12/4
 * 基础滤镜
 */
public class CameraXFilterNone extends BaseCameraXFilter {

    public CameraXFilterNone() {
        super();
    }

    @Override
    protected ShaderManager.Param getProgram() {
        return ShaderManager.getParam(ShaderManager.CAMERA_SHADER_BASE);
    }
}
