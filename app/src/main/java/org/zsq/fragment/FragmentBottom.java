package org.zsq.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.zsq.VO.UserResponseVO;
import org.zsq.event.AddShopCarEvent;
import org.zsq.playcamera.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static org.zsq.fragment.FragmentTop.ARG_TRAVEL;


public class FragmentBottom extends Fragment {


    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.iv_stars)
    ImageView ivStars;
    @BindView(R.id.btn_invite)
    Button btnInvite;
    Unbinder unbinder;
    private UserResponseVO travel;

    public static FragmentBottom newInstance(UserResponseVO travel) {
        Bundle args = new Bundle();
        FragmentBottom fragmentBottom = new FragmentBottom();
        args.putParcelable(ARG_TRAVEL, travel);
        fragmentBottom.setArguments(args);
        return fragmentBottom;
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
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);
        unbinder = ButterKnife.bind(this, view);

        UserResponseVO.UserInfoBean userInfo = travel.getUserInfo();
        tvName.setText("平台规则：邀请10个人全场5折，8个人全场7折，6个人全场9折");
        String mobile = userInfo.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        tvPhone.setText(mobile);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().postSticky(new AddShopCarEvent(travel));
                Toast.makeText(getContext(), "加入人头购物车成功！", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
