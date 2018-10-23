package org.zsq.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import org.zsq.playcamera.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * desc : 邀请弹框
 * time  : 23/10/18.
 * author : pielan
 */

public class InviteDialogFragment extends DialogFragment {

    Unbinder unbinder;
    @BindView(R.id.ed_time)
    EditText edTime;
    @BindView(R.id.time_layout)
    TextInputLayout timeLayout;
    @BindView(R.id.ed_place)
    EditText edPlace;
    @BindView(R.id.place_layout)
    TextInputLayout placeLayout;

    public static InviteDialogFragment getInstance() {
        InviteDialogFragment shopCarDialogFragment = new InviteDialogFragment();
        return shopCarDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_invite, container, false);
        unbinder = ButterKnife.bind(this, view);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        initData();
        return view;
    }

    private void initData() {
        timeLayout.setHint(getString(R.string.time));
        placeLayout.setHint(getString(R.string.place));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_confirm)
    void confirm() {
        if (TextUtils.isEmpty(edTime.getText())) {
            edTime.setHint(R.string.input_time);
            return;
        }

        if (TextUtils.isEmpty(edPlace.getText())) {
            edPlace.setHint(R.string.input_place);
            return;
        }
    }
}
