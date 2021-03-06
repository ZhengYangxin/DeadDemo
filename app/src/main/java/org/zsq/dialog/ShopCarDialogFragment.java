package org.zsq.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.zsq.VO.UserResponseVO;
import org.zsq.adapter.DividerItemDecoration;
import org.zsq.adapter.MineRadioAdapter;
import org.zsq.adapter.MyLiveList;
import org.zsq.playcamera.R;

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

public class ShopCarDialogFragment extends DialogFragment implements View.OnClickListener, MineRadioAdapter.OnItemClickListener {

    private static final int MYLIVE_MODE_CHECK = 0;
    private static final int MYLIVE_MODE_EDIT = 1;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.tv_select_num)
    TextView mTvSelectNum;
    @BindView(R.id.btn_delete)
    Button mBtnDelete;
    @BindView(R.id.select_all)
    TextView mSelectAll;
    @BindView(R.id.ll_mycollection_bottom_dialog)
    LinearLayout mLlMycollectionBottomDialog;
    Unbinder unbinder;
    @BindView(R.id.btn_sure)
    TextView btnSure;

    private MineRadioAdapter mRadioAdapter = null;
    private LinearLayoutManager mLinearLayoutManager;
    private List<MyLiveList> mList = new ArrayList<>();
    private boolean isSelectAll = false;
    private int mEditMode = MYLIVE_MODE_EDIT;
    private int index = 0;

    public static ShopCarDialogFragment getInstance() {
        ShopCarDialogFragment shopCarDialogFragment = new ShopCarDialogFragment();
        return shopCarDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shop_car, container, false);
        unbinder = ButterKnife.bind(this, view);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        initData();
        initListener();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);

    }

    private void initData() {
        mRadioAdapter = new MineRadioAdapter(getContext());
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        DividerItemDecoration itemDecorationHeader = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
//        itemDecorationHeader.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.divider_main_bg_height_1));
        mRecyclerview.addItemDecoration(itemDecorationHeader);
        mLlMycollectionBottomDialog.setVisibility(View.VISIBLE);
        mRadioAdapter.setEditMode(mEditMode);

//        mRecyclerview.setAdapter(mRadioAdapter);
//        for (int i = 0; i < 30; i++) {
//            MyLiveList myLiveList = new MyLiveList();
//            myLiveList.setTitle("这是第" + i + "个条目");
//            myLiveList.setSource("来源" + i);
//            mList.add(myLiveList);
//            mRadioAdapter.notifyAdapter(mList, false);
//        }
//        updataEditMode();
    }

    private void updataEditMode() {
//        mEditMode = mEditMode == MYLIVE_MODE_CHECK ? MYLIVE_MODE_EDIT : MYLIVE_MODE_CHECK;
        if (mEditMode == MYLIVE_MODE_EDIT) {
            mLlMycollectionBottomDialog.setVisibility(View.VISIBLE);
        } else {
            mLlMycollectionBottomDialog.setVisibility(View.GONE);
            clearAll();
        }
        mRadioAdapter.setEditMode(mEditMode);
    }

    /**
     * 根据选择的数量是否为0来判断按钮的是否可点击.
     *
     * @param size
     */
    private void setBtnBackground(int size) {
        if (size != 0) {
            btnSure.setBackgroundResource(R.color.colorAccent);
            btnSure.setEnabled(true);
            btnSure.setTextColor(Color.WHITE);
            mBtnDelete.setBackgroundResource(R.color.colorAccent);
            mBtnDelete.setEnabled(true);
            mBtnDelete.setTextColor(Color.WHITE);
        } else {
            mBtnDelete.setBackgroundResource(R.color.drawer_color);
            mBtnDelete.setEnabled(false);
            mBtnDelete.setTextColor(Color.WHITE);
            btnSure.setBackgroundResource(R.color.drawer_color);
            btnSure.setEnabled(false);
            btnSure.setTextColor(Color.WHITE);
        }
    }

    private void initListener() {
        mRadioAdapter.setOnItemClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mSelectAll.setOnClickListener(this);
        btnSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                deleteVideo();
                break;
            case R.id.select_all:
                selectAllMain();
                break;
            case R.id.btn_sure:
                InviteDialogFragment inviteDialogFragment = InviteDialogFragment.getInstance();
                inviteDialogFragment.setCancelable(true);
                inviteDialogFragment.show(getChildFragmentManager(), "inviteDialogFragment");
                break;
            default:
                break;
        }
    }

    /**
     * 全选和反选
     */
    private void selectAllMain() {
        if (mRadioAdapter == null) return;
        if (!isSelectAll) {
            for (int i = 0, j = mRadioAdapter.getMyLiveList().size(); i < j; i++) {
                mRadioAdapter.getMyLiveList().get(i).setSelect(true);
            }
            index = mRadioAdapter.getMyLiveList().size();
            mBtnDelete.setEnabled(true);
            mSelectAll.setText("取消全选");
            isSelectAll = true;
        } else {
            for (int i = 0, j = mRadioAdapter.getMyLiveList().size(); i < j; i++) {
                mRadioAdapter.getMyLiveList().get(i).setSelect(false);
            }
            index = 0;
            mBtnDelete.setEnabled(false);
            mSelectAll.setText("全选");
            isSelectAll = false;
        }
        mRadioAdapter.notifyDataSetChanged();
        setBtnBackground(index);
        mTvSelectNum.setText(String.valueOf(index));
    }

    /**
     * 删除逻辑
     */
    private void deleteVideo() {
        if (index == 0) {
            mBtnDelete.setEnabled(false);
            return;
        }
        final AlertDialog builder = new AlertDialog.Builder(getContext())
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg = (TextView) builder.findViewById(R.id.tv_msg);
        Button cancle = (Button) builder.findViewById(R.id.btn_cancle);
        Button sure = (Button) builder.findViewById(R.id.btn_sure);
        if (msg == null || cancle == null || sure == null) return;

        if (index == 1) {
            msg.setText("删除后不可恢复，是否删除该条目？");
        } else {
            msg.setText("删除后不可恢复，是否删除这" + index + "个条目？");
        }
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = mRadioAdapter.getMyLiveList().size(), j = 0; i > j; i--) {
                    UserResponseVO myLive = mRadioAdapter.getMyLiveList().get(i - 1);
                    if (myLive.isSelect()) {

                        mRadioAdapter.getMyLiveList().remove(myLive);
                        index--;
                    }
                }
                index = 0;
                mTvSelectNum.setText(String.valueOf(0));
                setBtnBackground(index);
                if (mRadioAdapter.getMyLiveList().size() == 0) {
                    mLlMycollectionBottomDialog.setVisibility(View.GONE);
                }
                mRadioAdapter.notifyDataSetChanged();
                builder.dismiss();
            }
        });
    }

    private void clearAll() {
        mTvSelectNum.setText(String.valueOf(0));
        isSelectAll = false;
        mSelectAll.setText("全选");
        setBtnBackground(0);
    }

    @Override
    public void onItemClickListener(int pos, List<UserResponseVO> myLiveList) {
        UserResponseVO myLive = myLiveList.get(pos);
        boolean isSelect = myLive.isSelect();
        if (!isSelect) {
            index++;
            myLive.setSelect(true);
            if (index == myLiveList.size()) {
                isSelectAll = true;
                mSelectAll.setText("取消全选");
            }

        } else {
            myLive.setSelect(false);
            index--;
            isSelectAll = false;
            mSelectAll.setText("全选");
        }
        setBtnBackground(index);
        mTvSelectNum.setText(String.valueOf(index));
        mRadioAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            WindowManager.LayoutParams wlp = dialog.getWindow().getAttributes();
            wlp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
            wlp.y = (int) getResources().getDimension(R.dimen.activity_shop_car_bottom);
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.75), (int) (dm.widthPixels * 0.8));

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
