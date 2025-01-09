package com.enjoypartytime.testdemo.opengl.camera.cameraXFilter.filter;

import com.enjoypartytime.testdemo.utils.ShaderManager;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/12/4
 * 纵向二分屏
 */
public class CameraXFilterY2 extends BaseCameraXFilter {

    public CameraXFilterY2() {
        super();
    }

    @Override
    protected ShaderManager.Param getProgram() {
        return ShaderManager.getParam(ShaderManager.CAMERA_SHADER_Y_2);
    }
}