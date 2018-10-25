package org.zsq.view.popupwindow;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.zsq.VO.UserInfoBeanVO;
import org.zsq.VO.UserResponseVO;
import org.zsq.adapter.DividerItemDecoration;
import org.zsq.adapter.MineRadioAdapter;
import org.zsq.app.DemoApplication;
import org.zsq.dialog.InviteDialogFragment;
import org.zsq.event.AddShopCarEvent;
import org.zsq.playcamera.R;
import org.zsq.util.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 功能：商品的Popup
 * Created by danke on 2016/6/17.
 */
public class ProductPopup extends SlideFromBottomPopup implements View.OnClickListener, MineRadioAdapter.OnItemClickListener {
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

    private MineRadioAdapter mRadioAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean isSelectAll = false;
    private int mEditMode = MYLIVE_MODE_EDIT;
    private int index = 0;

    private List<UserResponseVO> userResponseVOS;

    public ProductPopup(Activity context) {
        super(context);
    }

    @Override
    public View setChildView(ViewGroup mPopupView) {
        View inflate = UIUtil.inflate(R.layout.fragment_shop_car);
        ButterKnife.bind(this, inflate);
        // 固定listView的高度，防止重复执行getView
//        listView.getLayoutParams().height = mPopupView.getLayoutParams().height;
        EventBus.getDefault().register(this);
        initData();
        initListener();
        return inflate;
    }

    private void initData() {
        mRadioAdapter = new MineRadioAdapter(mContext);
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        DividerItemDecoration itemDecorationHeader = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST);
        //        itemDecorationHeader.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.divider_main_bg_height_1));
        mRecyclerview.addItemDecoration(itemDecorationHeader);
        mLlMycollectionBottomDialog.setVisibility(View.VISIBLE);
        mEditMode = MYLIVE_MODE_EDIT;
        mRadioAdapter.setEditMode(mEditMode);
        mRecyclerview.setAdapter(mRadioAdapter);
        userResponseVOS = new ArrayList<>();
//        initUserData();
        mRadioAdapter.notifyAdapter(userResponseVOS, false);

        //        updataEditMode();
    }

    public void initUserData() {
        if(mRadioAdapter != null && (userResponseVOS == null || userResponseVOS.isEmpty())) {
            userResponseVOS = new ArrayList<>();
            UserResponseVO userResponseVO0 = new UserResponseVO();
            userResponseVO0.setRating(100);

            UserInfoBeanVO userInfoBeanVO0 = new UserInfoBeanVO();
            userInfoBeanVO0.setName("海桐");
            userInfoBeanVO0.setMobile("18267721127");
            userInfoBeanVO0.setHeadImg("http://ph2p7uakt.bkt.clouddn.com/c.png");
            userResponseVO0.setUserInfo(userInfoBeanVO0);
            userResponseVOS.add(userResponseVO0);

            UserResponseVO userResponseVO1 = new UserResponseVO();
            userResponseVO1.setRating(28.172904669025318);

            UserInfoBeanVO userInfoBeanVO1 = new UserInfoBeanVO();
            userInfoBeanVO1.setName("火烧");
            userInfoBeanVO1.setMobile("18667132302");
            userInfoBeanVO1.setHeadImg("http://ph2p7uakt.bkt.clouddn.com/h.png");
            userResponseVO1.setUserInfo(userInfoBeanVO1);
            userResponseVOS.add(userResponseVO1);
            mRadioAdapter.notifyAdapter(userResponseVOS, false);
        }

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
                inviteDialogFragment.show(((AppCompatActivity)mContext).getSupportFragmentManager(), "inviteDialogFragment");
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
        final AlertDialog builder = new AlertDialog.Builder(mContext)
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



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AddShopCarEvent event) {
        if (userResponseVOS == null) {
            userResponseVOS = new ArrayList<>();
        }
        if (event != null && !userResponseVOS.contains(event.getUserResponseVO())) {
            userResponseVOS.add(event.getUserResponseVO());
            Toast.makeText(DemoApplication.getAppContext(), "加入人头购物车成功！", Toast.LENGTH_SHORT).show();
        }
        mRadioAdapter.notifyAdapter(userResponseVOS, false);
    };

    @Override
    public void dismiss() {
        super.dismiss();
        EventBus.getDefault().unregister(this);
    }
}
