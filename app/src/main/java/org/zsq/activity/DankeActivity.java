package org.zsq.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能：
 * <p>
 * Created by danke on 2018/10/23.
 */
public class DankeActivity extends Activity {
    @BindView(R.id.largeLabel)
    LinearLayout largeLabel;
    @BindView(R.id.keywordsflow)
    KeywordsFlow keywordsFlow;
    private String[] keywords;
    private ProgressBox progressBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my);
        ButterKnife.bind(this);
        refreshTags();
    }

    private void refreshTags() {
        initSearchHistory();
        keywordsFlow.setDuration(1500l);
        keywordsFlow.setOnItemClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String keyword = ((TextView) v).getText().toString();// 获得点击的标签
//                world_shopping_search_input.setText(keyword);
            }
        });
    }

    /**
     * 读取历史搜索记录
     */
    private void initSearchHistory() {
        progressBox = new ProgressBox(this, largeLabel);
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d("danke", "定时任务");
                NetworkUtils.get(progressBox, ConfigUrl.GET_INSTANCES, new TypeReference<List<InstanceResponseVO>>(){}.getType(), new BaseCallBackListen<List<InstanceResponseVO>>(DankeActivity.this) {
                    @Override
                    public void onResponse(List<InstanceResponseVO> data) {
                        List<String> list = new ArrayList<>();
                        if (data != null && !data.isEmpty()) {
                            for (InstanceResponseVO instanceResponseVO : data) {
                                InstanceResponseVO.MenuInstanceInfoBean menuInstanceInfo = instanceResponseVO.getMenuInstanceInfo();
                                if (menuInstanceInfo != null) {
                                    list.add(menuInstanceInfo.getName());
                                    Log.d("danke", menuInstanceInfo.getName());
                                }
                            }
                        }
                        keywords = list.toArray(new String[list.size()]);
                        // 添加
                        keywordsFlow.rubKeywords();
                        feedKeywordsFlow(keywordsFlow, keywords);
                        keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
                    }

                    @Override
                    public void onErrorResponse(String error) {
                        super.onErrorResponse(error);
                        keywordsFlow.rubKeywords();
                    }
                });
            }
        }, 0,5 * 1000);
    }

    private static void feedKeywordsFlow(KeywordsFlow keywordsFlow, String[] arr) {
        if (arr != null && arr.length > 0) {
            for (int i = 0; i < arr.length; i++) {
                String tmp = arr[i];
                keywordsFlow.feedKeyword(tmp);
            }
        }
    }
}
