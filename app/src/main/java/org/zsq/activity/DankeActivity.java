package org.zsq.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;

import com.qslll.library.ExpandingPagerFactory;
import com.qslll.library.fragments.ExpandingFragment;

import org.zsq.VO.InstanceResponseVO;
import org.zsq.adapter.TravelViewPagerAdapter;
import org.zsq.playcamera.R;
import org.zsq.view.ProgressBox;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能：
 * <p>
 * Created by danke on 2018/10/23.
 */
public class DankeActivity extends AppCompatActivity implements ExpandingFragment.OnExpandingClickListener {
//    @BindView(R.id.largeLabel)
//    LinearLayout largeLabel;
//    @BindView(R.id.keywordsflow)
//    KeywordsFlow keywordsFlow;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private InstanceResponseVO[] keywords;
    private ProgressBox progressBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my);
        ButterKnife.bind(this);
        refreshTags();

        setupWindowAnimations();

        TravelViewPagerAdapter adapter = new TravelViewPagerAdapter(getSupportFragmentManager());
//        adapter.addAll(generateTravelList());
        viewPager.setAdapter(adapter);


        ExpandingPagerFactory.setupViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ExpandingFragment expandingFragment = ExpandingPagerFactory.getCurrentFragment(viewPager);
                if(expandingFragment != null && expandingFragment.isOpenend()){
                    expandingFragment.close();
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(!ExpandingPagerFactory.onBackPressed(viewPager)){
            super.onBackPressed();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Explode slideTransition = new Explode();
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
    }

    @Override
    public void onExpandingClick(View v) {
        //v is expandingfragment layout
    }

    private void refreshTags() {
//        initSearchHistory();
//        keywordsFlow.setDuration(1500l);
//        keywordsFlow.setOnItemClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                String keyword = ((TextView) v).getText().toString();// 获得点击的标签
////                world_shopping_search_input.setText(keyword);
//            }
//        });
    }

    /**
     * 读取历史搜索记录
     */
//    private void initSearchHistory() {
//        progressBox = new ProgressBox(this, largeLabel);
//        final Timer t = new Timer();
//        t.schedule(new TimerTask() {
//            @Override
//            public void run() {
////                keywords = new String[]{"danke", "didi", "outa"};
////                feedKeywordsFlow(keywordsFlow, keywords);
////                keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
//                Log.d("danke", "定时任务");
//                NetworkUtils.get(progressBox, ConfigUrl.GET_INSTANCES, new TypeReference<List<InstanceResponseVO>>(){}.getType(), new BaseCallBackListen<List<InstanceResponseVO>>(DankeActivity.this) {
//                    @Override
//                    public void onResponse(List<InstanceResponseVO> data) {
//                        keywords = data.toArray(new InstanceResponseVO[data.size()]);
//                        // 添加
//                        keywordsFlow.rubKeywords();
//                        feedKeywordsFlow(keywordsFlow, keywords);
//                        keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
//                    }
//
//                    @Override
//                    public void onErrorResponse(String error) {
//                        super.onErrorResponse(error);
//                        keywordsFlow.rubKeywords();
//                    }
//                });
//            }
//        }, 0,5 * 1000);
//    }

//    private static void feedKeywordsFlow(KeywordsFlow keywordsFlow, InstanceResponseVO[] arr) {
//        if (arr != null && arr.length > 0) {
//            for (int i = 0; i < arr.length; i++) {
//                InstanceResponseVO tmp = arr[i];
//                keywordsFlow.feedKeyword(tmp);
//            }
//        }
//    }
}
