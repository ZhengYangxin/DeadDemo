package org.zsq.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.TypeReference;
import com.qslll.library.ExpandingPagerFactory;
import com.qslll.library.fragments.ExpandingFragment;

import org.zsq.VO.InstanceResponseVO;
import org.zsq.VO.Travel;
import org.zsq.VO.UserResponseVO;
import org.zsq.adapter.TravelViewPagerAdapter;
import org.zsq.playcamera.R;
import org.zsq.util.BaseCallBackListen;
import org.zsq.util.ConfigUrl;
import org.zsq.util.NetworkUtils;
import org.zsq.view.ProgressBox;
import org.zsq.view.cloudtag.KeywordsFlow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * desc :
 * time  : 23/10/18.
 * author : pielan
 */

public class PersonFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.tv_notify)
    TextView notify;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private TravelViewPagerAdapter adapter;

    public PersonFragment() {
    }

    public static PersonFragment newInstance() {
        PersonFragment fragment = new PersonFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
//        initToolbar(view);
        unbinder = ButterKnife.bind(this, view);
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view) {
        setupWindowAnimations();

        adapter = new TravelViewPagerAdapter(getActivity().getSupportFragmentManager());
        ProgressBox progressBox = new ProgressBox(getActivity(), viewPager);
        NetworkUtils.get(progressBox, ConfigUrl.GET_USERS + ConfigUrl.param, new TypeReference<List<UserResponseVO>>(){}.getType(), new BaseCallBackListen<List<UserResponseVO>>(getActivity()) {
            @Override
            public void onResponse(List<UserResponseVO> data) {
                adapter.addAll(data);
            }

            @Override
            public void onErrorResponse(String error) {
                super.onErrorResponse(error);
            }
        });
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


//    @Override
//    public void onBackPressed() {
//        if(!ExpandingPagerFactory.onBackPressed(viewPager)){
//            super.onBackPressed();
//        }
//    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Explode slideTransition = new Explode();
        getActivity().getWindow().setReenterTransition(slideTransition);
        getActivity().getWindow().setExitTransition(slideTransition);
    }

    public String getTitle() {
        return getArguments().getString("title");
    }

    public int getPosition() {
        return getArguments().getInt("position");
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}