package org.zsq.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.devspark.robototextview.widget.RobotoTextView;

import org.zsq.VO.Travel;
import org.zsq.activity.InfoActivity;
import org.zsq.playcamera.R;

public class FragmentTop extends Fragment {

    static final String ARG_TRAVEL = "ARG_TRAVEL";
    Travel travel;
//    @BindView(R.id.image)
    ImageView image;
//    @BindView(R.id.title)
    RobotoTextView title;

    public static FragmentTop newInstance(Travel travel) {
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
            image.setImageResource(travel.getImage());
            title.setText(travel.getName());
        }

    }

    @SuppressWarnings("unchecked")
    private void startInfoActivity(View view, Travel travel) {
        FragmentActivity activity = getActivity();
        ActivityCompat.startActivity(activity,
                InfoActivity.newInstance(activity, travel),
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,
                        new Pair<>(view, "transition_image"))
                        .toBundle());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
