package org.zsq.view.cloudtag;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RelativeLayout;

import com.moos.library.CircleProgressView;

import org.zsq.playcamera.R;

import java.util.Random;

/**
 * 功能：
 * <p>
 * Created by danke on 2018/10/24.
 */
public class RecommandCircleView extends RelativeLayout {
    private CircleView txtCircle;
    private CircleProgressView circleProgressView;
    private OnClickListener itemClickListener;

    public RecommandCircleView(Context context) {
        super(context);
        initView(context, null);
    }

    public RecommandCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public RecommandCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public void initView(Context context, AttributeSet attrs) {
        inflate(context, R.layout.view_recommand_circle, this);
        txtCircle = (CircleView) findViewById(R.id.txt_circle);
        circleProgressView = (CircleProgressView) findViewById(R.id.progressView_circle);

        // CircleProgressView
        circleProgressView.setStartProgress(0);
        circleProgressView.setStartColor(Color.parseColor("#E78162"));
        circleProgressView.setEndColor(Color.parseColor("#F18A50"));
        circleProgressView.setCircleBroken(true);
        circleProgressView.setTrackWidth(0);
        circleProgressView.setProgressDuration(2000);
        circleProgressView.setTrackEnabled(true);
        circleProgressView.setFillEnabled(true);
        circleProgressView.setProgressTextVisibility(false);
        circleProgressView.startProgressAnimation();

        Random random = new Random();
        txtCircle.setBackgroundResource(R.drawable.text_view_border);
        txtCircle.setGravity(Gravity.CENTER);
        txtCircle.setOnClickListener(itemClickListener);
        txtCircle.setTextColor(Color.WHITE);
        txtCircle.setPadding(5, 5, 5, 5);
        txtCircle.setSingleLine(true);
        int r = random.nextInt(100);
        int g= random.nextInt(100);
        int b = random.nextInt(100);
        int mColor = Color.rgb(r, g, b);
        GradientDrawable myGrad = (GradientDrawable)txtCircle.getBackground();
        myGrad.setColor(mColor);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RecommandCircleView);
            for (int i = 0; i < ta.getIndexCount(); i++) {
                int attr = ta.getIndex(i);
                switch (attr) {
                }
            }
        }
    }

    public OnClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(OnClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setText(String keyword) {
        txtCircle.setText(keyword);
    }

    public void setTextColor(int color) {
        txtCircle.setTextColor(color);
    }

    public Paint getPaint() {
        return txtCircle.getPaint();
    }

    public CircleProgressView getProgressView() {
        return circleProgressView;
    }

    public void setEndProgress(float progess) {
        circleProgressView.setProgress(progess);
    }
}
