package com.enjoypartytime.testdemo.opengl.webpDynamic.view;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.opengl.webpDynamic.render.WebpDynamicRender;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.imagepipeline.image.ImageInfo;

/**
 * Created by liuwei64 on 2018/3/16.
 */

public class WebPGLView extends GLSurfaceView {

    private DraweeHolder mDraweeHolder;

    private WebpDynamicRender dynamicRender;

    public WebPGLView(Context context) {
        super(context);
        init(context);
    }

    public WebPGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        GenericDraweeHierarchyBuilder hierarchyBuilder = new GenericDraweeHierarchyBuilder(getResources());
        mDraweeHolder = DraweeHolder.create(hierarchyBuilder.build(), context);
        mDraweeHolder.getTopLevelDrawable().setCallback(this);

        /* ** 以下代码可在外部实现 ** */
        setEGLContextClientVersion(2);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        setZOrderOnTop(true);

        setRenderer(new WebpDynamicRender());
        setRenderMode(WebPGLView.RENDERMODE_WHEN_DIRTY);

//        String uri = context.getResources().getResourceName(R.raw.webp_dynamic1);
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + R.raw.webp_dynamic1);
//        Uri uri = Uri.parse("android.resource://com.enjoypartytime.testdemo/raw/webp_dynamic1");
        setImageUri(uri);
        /* ** 以上代码可在外部实现 ** */
    }

    @Override
    public void setRenderer(Renderer renderer) {
        super.setRenderer(renderer);
        this.dynamicRender = (WebpDynamicRender) renderer;
    }

    @Override
    public void invalidateDrawable(@NonNull Drawable drawable) {
        dynamicRender.setDrawable(drawable);
        requestRender();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mDraweeHolder.onDetach();
    }

    @Override
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        mDraweeHolder.onDetach();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        mDraweeHolder.onAttach();
    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        mDraweeHolder.onAttach();
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        if (who == mDraweeHolder.getTopLevelDrawable()) {
            return true;
        }
        return super.verifyDrawable(who);
    }

    public void setImageUri(String imageUri) {
        Uri uri = Uri.parse(imageUri);
        setImageUri(uri);
    }

    public void setImageUri(Uri imageUri) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(imageUri)
                .setAutoPlayAnimations(true)
                .setOldController(mDraweeHolder.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, @Nullable final ImageInfo imageInfo, @Nullable Animatable animatable) {
                        queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                dynamicRender.setContentSize(imageInfo.getWidth(), imageInfo.getHeight());
                                requestRender();
                            }
                        });
                    }
                }).build();
        mDraweeHolder.setController(controller);
    }

    public DraweeHolder getDraweeHolder() {
        return mDraweeHolder;
    }

    public WebpDynamicRender getDynamicRender() {
        return dynamicRender;
    }

}
