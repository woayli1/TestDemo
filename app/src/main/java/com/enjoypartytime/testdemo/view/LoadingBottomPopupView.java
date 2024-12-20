package com.enjoypartytime.testdemo.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.transition.ChangeBounds;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;

import com.enjoypartytime.testdemo.R;
import com.google.android.material.transition.MaterialFade;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/12/19
 */
public class LoadingBottomPopupView extends BottomPopupView {

    private int bindLayoutId;

    public enum Style {
        Spinner, ProgressBar
    }

    private LoadingBottomPopupView.Style loadingStyle = LoadingBottomPopupView.Style.Spinner;
    private TextView tv_title;
    private View progressBar, spinnerView;

    public LoadingBottomPopupView(@NonNull Context context) {
        super(context);
    }

    /**
     * @param context      context
     * @param bindLayoutId layoutId 如果要显示标题，则要求必须有id为tv_title的TextView，否则无任何要求
     */
    public LoadingBottomPopupView(@NonNull Context context, int bindLayoutId) {
        super(context);
        this.bindLayoutId = bindLayoutId;
        addInnerContent();
    }

    @Override
    protected int getImplLayoutId() {
        return bindLayoutId != 0 ? bindLayoutId : R.layout._xpopup_center_impl_loading;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        tv_title = findViewById(R.id.tv_title);
        progressBar = findViewById(R.id.loadProgress);
        spinnerView = findViewById(R.id.loadview);

//        getPopupImplView().setElevation(10f);
//        if (bindLayoutId == 0) {
//            getPopupImplView().setBackground(XPopupUtils.createDrawable(Color.parseColor("#212121"), popupInfo.borderRadius));
//        }

        setup();
    }

    private boolean firstShow = true;

    @Override
    protected void onShow() {
        super.onShow();
        firstShow = false;
    }

    protected void setup() {
        post(() -> {
            if (!firstShow) {
                TransitionSet set = new TransitionSet().setDuration(getAnimationDuration())
                        .addTransition(new MaterialFade()).addTransition(new ChangeBounds());
                TransitionManager.beginDelayedTransition(bottomPopupContainer, set);
            }
            if (title == null || title.length() == 0) {
                XPopupUtils.setVisible(tv_title, false);
            } else {
                XPopupUtils.setVisible(tv_title, true);
                if (tv_title != null) tv_title.setText(title);
            }

            if (loadingStyle == Style.Spinner) {
                XPopupUtils.setVisible(progressBar, false);
                XPopupUtils.setVisible(spinnerView, true);
            } else {
                XPopupUtils.setVisible(progressBar, true);
                XPopupUtils.setVisible(spinnerView, false);
            }
        });
    }

    private CharSequence title;

    public LoadingBottomPopupView setTitle(CharSequence title) {
        this.title = title;
        setup();
        return this;
    }

    public LoadingBottomPopupView setStyle(LoadingBottomPopupView.Style style) {
        this.loadingStyle = style;
        setup();
        return this;
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
        firstShow = true;
    }
}
