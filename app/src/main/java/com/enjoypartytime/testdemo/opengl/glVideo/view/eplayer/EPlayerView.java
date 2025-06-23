package com.enjoypartytime.testdemo.opengl.glVideo.view.eplayer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import androidx.media3.common.Player;
import androidx.media3.common.VideoSize;
import androidx.media3.exoplayer.ExoPlayer;

import com.enjoypartytime.testdemo.opengl.glVideo.chooser.EConfigChooser;
import com.enjoypartytime.testdemo.opengl.glVideo.contextfactory.EContextFactory;
import com.enjoypartytime.testdemo.opengl.glVideo.filter.GlFilter;

/**
 * Created by sudamasayuki on 2017/05/16.
 */
public class EPlayerView extends GLSurfaceView implements Player.Listener {

    private final static String TAG = EPlayerView.class.getSimpleName();

    private final EPlayerRenderer renderer;
    private ExoPlayer player;

//    private float videoAspect = 1f;
//    private PlayerScaleType playerScaleType = PlayerScaleType.RESIZE_FIT_HEIGHT;

    public EPlayerView(Context context) {
        this(context, null);
    }

    public EPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setEGLContextFactory(new EContextFactory());
        setEGLConfigChooser(new EConfigChooser());

        renderer = new EPlayerRenderer(this);
        setRenderer(renderer);
    }

    public void setSimpleExoPlayer(ExoPlayer player) {
        if (this.player != null) {
            this.player.release();
        }

        this.player = player;
        this.player.addListener(this);
        this.renderer.setSimpleExoPlayer(player);
    }

    public void setGlFilter(GlFilter glFilter) {
        renderer.setGlFilter(glFilter);
    }

//    public void setPlayerScaleType(PlayerScaleType playerScaleType) {
//        this.playerScaleType = playerScaleType;
//        requestLayout();
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        int viewWidth = measuredWidth;
        int viewHeight = measuredHeight;

        //按比例变化
//        switch (playerScaleType) {
//            case RESIZE_FIT_WIDTH:
//                viewHeight = (int) (measuredWidth / videoAspect);
//                break;
//            case RESIZE_FIT_HEIGHT:
//                viewWidth = (int) (measuredHeight * videoAspect);
//                break;
//        }

//        Log.d(TAG, "onMeasure viewWidth = " + viewWidth + " viewHeight = " + viewHeight);

        setMeasuredDimension(viewWidth, viewHeight);

    }

    @Override
    public void onPause() {
        super.onPause();
        renderer.release();
    }

    /// ///////////////////////////////////////////////////////////////////////
    // Player.Listener
    @Override
    public void onVideoSizeChanged(VideoSize videoSize) {
//        int width = videoSize.width;
//        int height = videoSize.height;
//        float pixelWidthHeightRatio = videoSize.pixelWidthHeightRatio;
//        int unappliedRotationDegrees = videoSize.unappliedRotationDegrees;

        // Log.d(TAG, "width = " + width + " height = " + height + " unappliedRotationDegrees = " + unappliedRotationDegrees + " pixelWidthHeightRatio = " + pixelWidthHeightRatio);
//        videoAspect = ((float) width / height) * pixelWidthHeightRatio;
        // Log.d(TAG, "videoAspect = " + videoAspect);
        requestLayout();
    }

    @Override
    public void onRenderedFirstFrame() {
        // do nothing
    }
}
