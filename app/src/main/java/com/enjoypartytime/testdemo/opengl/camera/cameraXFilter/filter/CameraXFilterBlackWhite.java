package com.enjoypartytime.testdemo.opengl.camera.cameraXFilter.filter;

import com.enjoypartytime.testdemo.utils.ShaderManager;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/12/4
 * 黑白效果
 */
public class CameraXFilterBlackWhite extends BaseCameraXFilter {

    public CameraXFilterBlackWhite() {
        super();
    }

    @Override
    protected ShaderManager.Param getProgram() {
        return ShaderManager.getParam(ShaderManager.CAMERA_SHADER_BLACK_WHITE);
    }
}