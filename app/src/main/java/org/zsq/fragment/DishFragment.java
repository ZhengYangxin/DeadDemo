package org.zsq.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.zsq.playcamera.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * desc :
 * time  : 23/10/18.
 * author : pielan
 */

public class DishFragment extends Fragment {

    @BindView(R.id.content_list)
    RecyclerView contentList;
    Unbinder unbinder;
    @BindView(R.id.tv_notify)
    TextView notify;

    public DishFragment() {
    }

    public static DishFragment newInstance() {
        DishFragment fragment = new DishFragment();
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

//    private void initToolbar(View view) {
//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        toolbar.setTitle(getTitle());
//    }

    private void initRecyclerView(View view) {
//        contentList.setLayoutManager(new LinearLayoutManager(getActivity()));
//        contentList.setAdapter(new ContentAdapter());
//        notify.setText(getPosition() + "");
    }

    public String getTitle() {
        return getArguments().getString("title");
    }

    public int getPosition() {
        return getArguments().getInt("position");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}