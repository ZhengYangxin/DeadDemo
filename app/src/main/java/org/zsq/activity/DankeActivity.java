package org.zsq.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import org.zsq.playcamera.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能：
 * <p>
 * Created by danke on 2018/10/23.
 */
public class DankeActivity extends Activity {
    @BindView(R.id.largeLabel)
    LinearLayout largeLabel;
    @BindView(R.id.day_night_view)
    LinearLayout dayNightView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn)
    void shopdialog() {
    }
}
