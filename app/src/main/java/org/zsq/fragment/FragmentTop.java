package org.zsq.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.devspark.robototextview.widget.RobotoTextView;
import com.squareup.picasso.Picasso;

import org.zsq.VO.UserResponseVO;
import org.zsq.playcamera.R;

import java.io.File;

public class FragmentTop extends Fragment {

    static final String ARG_TRAVEL = "ARG_TRAVEL";
    UserResponseVO travel;
//    @BindView(R.id.image)
    ImageView image;
//    @BindView(R.id.title)
    RobotoTextView title;

    public static FragmentTop newInstance(UserResponseVO travel) {
        Bundle args = new Bundle();
        FragmentTop fragmentTop = new FragmentTop();
        args.putParcelable(ARG_TRAVEL, travel);
        fragmentTop.setArguments(args);
        return fragmentTop;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            travel = args.getParcelable(ARG_TRAVEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_front, container, false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        image = (ImageView) view.findViewById(R.id.image);
        title = (RobotoTextView) view.findViewById(R.id.title);
        if (travel != null) {
            UserResponseVO.UserInfoBean userInfo = travel.getUserInfo();
            String url = userInfo.getHeadImg();
            Picasso.with(getContext()).load(url).into(image);
            title.setText(userInfo.getName());
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
