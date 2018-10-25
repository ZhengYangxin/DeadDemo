package org.zsq.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.qslll.library.ExpandingPagerFactory;
import com.qslll.library.fragments.ExpandingFragment;
import com.squareup.picasso.Picasso;

import org.zsq.VO.UserInfoBeanVO;
import org.zsq.adapter.ContentFragmentAdapter;
import org.zsq.fragment.DishFragment;
import org.zsq.fragment.PersonFragment;
import org.zsq.playcamera.R;
import org.zsq.util.ConfigUrl;
import org.zsq.util.NetworkUtils;
import org.zsq.view.popupwindow.ProductPopup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.kaelaela.verticalviewpager.VerticalViewPager;
import me.kaelaela.verticalviewpager.transforms.DefaultTransformer;

public class InfoActivity extends AppCompatActivity implements ExpandingFragment.OnExpandingClickListener {

    private static final String EXTRA_PHONE = "EXTRA_PHONE";
    @BindView(R.id.vertical_viewpager)
    VerticalViewPager verticalViewpager;
    @BindView(R.id.iv_user)
    ImageView ivUser;
    private DishFragment dishFragment;
    private PersonFragment personFragment;
    private ProductPopup mProductPopup;


    public static Intent newInstance(Context context, String phone) {
        Intent intent = new Intent(context, InfoActivity.class);
        intent.putExtra(EXTRA_PHONE, phone);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);
        initViewPager();
        mProductPopup  = new ProductPopup(this);
        String phone = getIntent().getStringExtra(EXTRA_PHONE);
        if (phone != null) {
            ConfigUrl.param = phone;
            getUserData();
        }

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

    private void getUserData() {
        NetworkUtils.get(ConfigUrl.GET_USER + ConfigUrl.param, UserInfoBeanVO.class, new NetworkUtils.CallBackListen<UserInfoBeanVO>() {
            @Override
            public void onResponse(UserInfoBeanVO data) {
                Picasso.with(InfoActivity.this).load(data.getHeadImg()).into(ivUser);
            }

            @Override
            public void onErrorResponse(String error) {

            }
        });
    }

    @Override
    public void onExpandingClick(View v) {
        ViewPager viewPager = personFragment.getViewPager();
        if (viewPager != null) {
            ExpandingPagerFactory.onBackPressed(viewPager);
        }
    }

    @OnClick(R.id.btn_shop_car)
    void shopCar() {
        // 购买商品弹出框
        mProductPopup.initUserData();
        mProductPopup.showPopupWindow();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
