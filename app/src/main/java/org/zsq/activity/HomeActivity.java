package org.zsq.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.arcsoft.library.database.module.Face;
import com.squareup.picasso.Picasso;

import org.apmem.tools.layouts.FlowLayout;
import org.zsq.playcamera.R;
import org.zsq.util.NetworkUtils;
import org.zsq.view.ProgressBox;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/2.
 */

public class HomeActivity extends BaseActivity {
    FlowLayout iconList;
    @BindView(R.id.msc_toolbar)
    Toolbar mscToolbar;
    @BindView(R.id.thingsGridContainer)
    FlowLayout thingsGridContainer;
    @BindView(R.id.btn_shop_car)
    FloatingActionButton btnShopCar;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private ProgressBox progressBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        iconList = (FlowLayout) findViewById(R.id.thingsGridContainer);
        titleString = "主页";

        showItem();

        final TextView txt_get = (TextView) findViewById(R.id.tv_get);
        Button button = (Button) findViewById(R.id.btn_get);
        progressBox = new ProgressBox(this, drawerLayout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkUtils.get(progressBox,"http://10.1.130.61:8080/user/1", String.class, new NetworkUtils.CallBackListen<String>() {

                    @Override
                    public void onResponse(String data) {
                        txt_get.setText(data);
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
            }
        });
    }

    @OnClick(R.id.btn_shop_car)
    void shopCar() {
        Toast.makeText(getBaseContext(), "购物车", Toast.LENGTH_LONG).show();
    }

    private void showItem() {
        int i = 0;
        while (true) {
            Face face = new Face(this, i);
            if (!TextUtils.isEmpty(face.getName())) {
                LinearLayout thing_item = (LinearLayout) getLayoutInflater().inflate(R.layout.home_thing_container, iconList, false);
                TextView tv2 = (TextView) thing_item.findViewById(R.id.thingLabel);
                ImageView iv = (ImageView) thing_item.findViewById(R.id.thingTypeIcon);
                Picasso.with(this).load(new File(face.getPath())).placeholder(R.drawable.head)
                        .error(R.drawable.head)
                        .into(iv);
                tv2.setText(face.getName());
                thing_item.setId(i);
                thing_item.setOnClickListener(editClickListener);
                i++;
                iconList.addView(thing_item);
            } else
                break;
        }

        LinearLayout thing_item = (LinearLayout) getLayoutInflater().inflate(R.layout.home_add, iconList, false);
        thing_item.setOnClickListener(addClickListener);
        iconList.addView(thing_item);
    }

    private View.OnClickListener addClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(HomeActivity.this, EnrollActivity.class);
            startActivityForResult(intent, 1000);
        }
    };

    private View.OnClickListener editClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(HomeActivity.this, EnrollActivity.class);
            intent.putExtra("offset", v.getId());
            startActivityForResult(intent, 1000);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                boolean isUpdate = data.getBooleanExtra("isUpdate", false);
                if (isUpdate) {
                    iconList.removeAllViews();
                    showItem();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                onPermission();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    1000);
        } else {
            startCameraActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCameraActivity();
                } else {
                    Toast.makeText(HomeActivity.this, "由于相机权限未打开,无法进行识别。请去设置界面手动打开", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void startCameraActivity() {
        Intent intent = new Intent(HomeActivity.this, CameraActivity.class);
        startActivity(intent);
    }
}
