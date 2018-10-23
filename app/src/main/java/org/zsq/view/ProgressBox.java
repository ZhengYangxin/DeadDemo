package org.zsq.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.zsq.playcamera.R;

import app.dinus.com.loadingdrawable.LoadingView;

public class ProgressBox implements IProgressBox {
    private Activity context;

    private LayoutInflater inflater;

    private ViewGroup globalContainerView;

    private View progressView;
    private LoadingView loadingView;

    public ProgressBox(Activity context, ViewGroup globalContainerView) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.globalContainerView = globalContainerView;
        this.initView();
    }

    /**
     * 初始化界面组件
     **/
    private void initView() {
        progressView = inflater.inflate(R.layout.progress_popup, null);
        loadingView = (LoadingView) progressView.findViewById(R.id.day_night_view);
        globalContainerView.addView(progressView);
    }

    private void showBox() {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressView.bringToFront();
                progressView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void hideBox() {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressView.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void show() {
        this.showBox();
    }

    public void hide() {
        this.hideBox();
    }
    @Override
    public void show(Activity context) {
        this.showBox();
    }

}
