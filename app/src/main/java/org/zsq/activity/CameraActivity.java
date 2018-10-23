package org.zsq.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.arcsoft.library.FaceService;
import com.arcsoft.library.module.ArcsoftFace;
import com.arcsoft.library.module.FaceData;
import com.arcsoft.library.module.FaceResponse;
import com.qslll.library.ExpandingPagerFactory;
import com.qslll.library.fragments.ExpandingFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.zsq.adapter.ContentFragmentAdapter;
import org.zsq.app.DemoApplication;
import org.zsq.camera.CameraCallback;
import org.zsq.camera.CameraSurface;
import org.zsq.fragment.DishFragment;
import org.zsq.fragment.PersonFragment;
import org.zsq.playcamera.R;
import org.zsq.util.DisplayUtil;
import org.zsq.util.ImageUtil;
import org.zsq.view.popupwindow.ProductPopup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.kaelaela.verticalviewpager.VerticalViewPager;
import me.kaelaela.verticalviewpager.transforms.DefaultTransformer;

/**
 * create by zsq
 */
public class CameraActivity extends AppCompatActivity implements CameraCallback, ExpandingFragment.OnExpandingClickListener{
    private static final String TAG = "zsq";
    @BindView(R.id.camera_surfaceview)
    CameraSurface surfaceView;
    @BindView(R.id.surfaceView)
    SurfaceView surfaceViewRect;
    @BindView(R.id.vertical_viewpager)
    VerticalViewPager verticalViewpager;
    private SurfaceHolder surfaceHolder = null;
    private Paint m_Paint = null;
    private int m_nScreenWidth, m_nScreenHeight;
    private FaceService faceService;

    private long time = 0;
    private long detectionTime = 5000l;

    private boolean isFront = true;

    private DishFragment dishFragment;
    private PersonFragment personFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        faceService = ((DemoApplication) getApplication()).getFaceService();
        initUI();
        init();
        initViewPager();
    }

    private void initViewPager() {
        dishFragment = DishFragment.newInstance();
        personFragment = PersonFragment.newInstance();
//        viewPager.setPageTransformer(true, new ZoomOutTransformer());
//        viewPager.setPageTransformer(false, new StackTransformer());
        verticalViewpager.setPageTransformer(true, new DefaultTransformer());

        String title = "ContentFragment";
        verticalViewpager.setAdapter(new ContentFragmentAdapter.Holder(getSupportFragmentManager())
                .add(dishFragment)
                .add(personFragment)
                .set());
        //If you setting other scroll mode, the scrolled fade is shown from either side of display.
        verticalViewpager.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    @OnClick(R.id.btn_shop_car)
    void shopCar() {
        // 购买商品弹出框
        ProductPopup mProductPopup = new ProductPopup(this);
        mProductPopup.showPopupWindow();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        surfaceView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        if (surfaceView != null) {
            surfaceView.releaseCamera();
        }
        super.onDestroy();
    }

    public void init() {
        Point p = DisplayUtil.getScreenMetrics(this);
        m_nScreenWidth = p.x;
        m_nScreenHeight = p.y;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.camera, menu);
        return true;
    }

    @OnClick(R.id.btn_image)
    void changeRotate() {
        clearUI();
        isFront = !isFront;
        surfaceView.setUseFront(isFront);
        surfaceView.resetCamera();
    }


    private void initUI() {
        //shutterBtn = (ImageButton)findViewById(R.id.btn_shutter);
        surfaceView.setCallback(this);

        //设置为后置摄像头
        surfaceView.setUseFront(isFront);

        surfaceViewRect.setZOrderOnTop(true);

        surfaceHolder = surfaceViewRect.getHolder();

        surfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        m_Paint = new Paint();
        m_Paint.setAntiAlias(true);//去锯齿
        m_Paint.setStyle(Paint.Style.STROKE);//空心
        m_Paint.setARGB(255, 253, 122, 18);
        m_Paint.setStrokeWidth(4.0f);
        m_Paint.setTextSize(40);
    }

    public void faceOut(ArcsoftFace result, float score, String name, int orientation) {
        if (result != null) {
            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR); //清楚掉上一次的画框。
                    Rect r = result.getRect();
                    Log.e("Tag", "faceArea=" + r.left + ", " + r.top + ", " + r.right + ", " + r.bottom);
                    r = ImageUtil.rotateRact(r, orientation, m_nScreenHeight, m_nScreenWidth);
                    Log.e("Tag", "faceArea2=" + r.left + ", " + r.top + ", " + r.right + ", " + r.bottom);
                    canvas.drawRect(r, m_Paint);
                    canvas.drawText("" + score, r.left, r.top - 20, m_Paint);
                    canvas.drawText(name, r.left, r.top - 60, m_Paint);
                    if (score > 0.6) {
                        dishFragment.initSearchHistory();
                    }
                }
            } catch (Exception Ex) {

            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
//            if (result.size() > 0) {
//                showText("人脸检测：有 " + result.size() + " 个人。检测耗时：" + nEclipseTime + " ms");
//            }
        }
    }

    private void clearUI() {

        Canvas canvas = null;
        try {

            surfaceViewRect.setZOrderOnTop(true);
            canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR); //清楚掉上一次的画框。
            }
        } catch (Exception Ex) {
        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Canvas canvas = null;
        try {

            surfaceViewRect.setZOrderOnTop(true);
            canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR); //清楚掉上一次的画框。
                Rect r = new Rect((int) event.getX() - 50, (int) event.getY() - 50, (int) event.getX() + 50, (int) event.getY() + 50);
                canvas.drawRect(r, m_Paint);
            }
        } catch (Exception Ex) {

        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
        return super.onTouchEvent(event);
    }


    @Override
    public void onPreviewFrame(final byte[] bytes, final Camera camera) {
        long mtime = System.currentTimeMillis();
        if ((mtime - time) < detectionTime) {
            return;
        } else {
            Log.e("Tag", "width = " + m_nScreenWidth + "   " + m_nScreenHeight +
                    "  w =" + camera.getParameters().getPreviewSize().width
                    + "  h = " + camera.getParameters().getPreviewSize().height);
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(1, info);
            Log.e("Tag", "Camera degree = " + info.orientation);
            int width = camera.getParameters().getPreviewSize().width;
            int height = camera.getParameters().getPreviewSize().height;
            start(bytes, width, height, info.orientation);
            detectionTime = (System.currentTimeMillis() - mtime) + 500;
            time = mtime;
            /*
            switch (info.orientation) {
                case 90:
                    start(rotateYUV420Degree90(bytes, width, height), height, width);
                    break;
                case 180:
                    start(rotateYUV420Degree180(bytes, width, height), width, height);
                    break;
                case 270:
                    start(rotateYUV420Degree270(bytes, width, height), height, width);
                    break;
                default:
                    start(bytes, width, height);
                    break;
            }
            */
        }
    }

    private void start(byte[] data, int width, int height, int orientation) {
//        EventBus.getDefault().post(new FaceData(data, width, height, orientation));
        faceService.cameraRecognize(new FaceData(data, width, height, orientation));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FaceResponse event) {
        if (event.getCode() != 0) {
            clearUI();
            Log.e("Tag", "错误码：" + event.getCode());
        } else {
            Log.e("Tag", "成功：" + event.getType());
            switch (event.getType()) {
                case DETECTION: {
                    if (event.getList() == null || event.getList().size() <= 0) {
                        clearUI();
                    }
                    break;
                }
                case MATCH: {
                    Log.e("TAG", "match : " + event.getScore());
                    faceOut(event.getFace(), event.getScore(), event.getName(), event.getOrientation());
                    break;
                }
            }

        }
    }

    @Override
    public void onExpandingClick(View v) {
        ViewPager viewPager = personFragment.getViewPager();
        if (viewPager != null) {
            ExpandingPagerFactory.onBackPressed(viewPager);
        }
    }
}
