package org.zsq.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.TypeReference;

import org.zsq.VO.InstanceResponseVO;
import org.zsq.playcamera.R;
import org.zsq.util.BaseCallBackListen;
import org.zsq.util.ConfigUrl;
import org.zsq.util.NetworkUtils;
import org.zsq.view.ProgressBox;
import org.zsq.view.cloudtag.KeywordsFlow;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * desc :
 * time  : 23/10/18.
 * author : pielan
 */

public class DishFragment extends Fragment {

    @BindView(R.id.largeLabel)
    LinearLayout largeLabel;
    @BindView(R.id.keywordsflow)
    KeywordsFlow keywordsFlow;
    private InstanceResponseVO[] keywords = new InstanceResponseVO[10];
    private ProgressBox progressBox;
    Unbinder unbinder;

    private String tempPhone = "";

    public DishFragment() {
    }

    public static DishFragment newInstance() {
        DishFragment fragment = new DishFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dish, container, false);
        unbinder = ButterKnife.bind(this, view);
        refreshTags();

        return view;
    }

    private void refreshTags() {
//        initSearchHistory();
        keywordsFlow.setDuration(1500l);
        keywordsFlow.setOnItemClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String keyword = ((TextView) v).getText().toString();// 获得点击的标签
//                world_shopping_search_input.setText(keyword);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 读取历史搜索记录
     */
    public void initSearchHistory() {
        Log.d("danke", "定时任务");
        synchronized (keywords) {
            keywordsFlow.rubKeywords();
            if (tempPhone.equals(ConfigUrl.param)) {
                // 添加
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                    keywordsFlow.rubKeywords();
                        feedKeywordsFlow(keywordsFlow, keywords);
                        keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
                    }
                });

            } else {
                NetworkUtils.get(progressBox, ConfigUrl.GET_INSTANCES + ConfigUrl.param, new TypeReference<List<InstanceResponseVO>>(){}.getType(), new BaseCallBackListen<List<InstanceResponseVO>>(getActivity()) {
                    @Override
                    public void onResponse(List<InstanceResponseVO> data) {
                        keywords = data.toArray(new InstanceResponseVO[data.size()]);
                        // 添加
                        keywordsFlow.rubKeywords();
                        feedKeywordsFlow(keywordsFlow, keywords);
                        keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
                        tempPhone = ConfigUrl.param;
                    }

                    @Override
                    public void onErrorResponse(String error) {
                        super.onErrorResponse(error);
                        if (keywordsFlow != null) {
                            keywordsFlow.rubKeywords();
                        }
                    }
                });
            }
        }

        /*progressBox = new ProgressBox(getActivity(), largeLabel);
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
//                keywords = new String[]{"danke", "didi", "outa"};
//                feedKeywordsFlow(keywordsFlow, keywords);
//                keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);


            }
        }, 0,10 * 1000);*/
    }

    private static void feedKeywordsFlow(KeywordsFlow keywordsFlow, InstanceResponseVO[] arr) {
        if (arr != null && arr.length > 0) {
            for (int i = 0; i < arr.length; i++) {
                InstanceResponseVO tmp = arr[i];
                keywordsFlow.feedKeyword(tmp);
            }
        }
    }
}