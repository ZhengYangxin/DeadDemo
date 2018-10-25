package org.zsq.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.zsq.VO.UserInfoBeanVO;
import org.zsq.VO.UserResponseVO;
import org.zsq.playcamera.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * desc :
 * time  : 23/10/18.
 * author : pielan
 */

public class MineRadioAdapter extends RecyclerView.Adapter<MineRadioAdapter.ViewHolder> {

    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;

    private int secret = 0;
    private String title = "";
    private Context context;
    private List<UserResponseVO> mMyLiveList;
    private OnItemClickListener mOnItemClickListener;
    DecimalFormat df;

    public MineRadioAdapter(Context context) {
        this.context = context;
         df = new DecimalFormat("0.0");

    }


    public void notifyAdapter(List<UserResponseVO> myLiveList, boolean isAdd) {
        if (!isAdd) {
            this.mMyLiveList = myLiveList;
        } else {
            this.mMyLiveList.addAll(myLiveList);
        }
        notifyDataSetChanged();
    }

    public List<UserResponseVO> getMyLiveList() {
        if (mMyLiveList == null) {
            mMyLiveList = new ArrayList<>();
        }
        return mMyLiveList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_people, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mMyLiveList.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final UserResponseVO userResponseVO = mMyLiveList.get(holder.getAdapterPosition());
        final UserInfoBeanVO userInfoBeanVO = userResponseVO.getUserInfo();
        final double rating = userResponseVO.getRating();
        if (userInfoBeanVO != null) {
            holder.mTvName.setText(userInfoBeanVO.getName());
            holder.mTvMatch.setText(String.format(context.getString(R.string.match), df.format(rating) + "%"));
            Picasso.with(context).load(userInfoBeanVO.getHeadImg()).into(holder.mHead);
            if (mEditMode == MYLIVE_MODE_CHECK) {
                holder.mCheckBox.setVisibility(View.GONE);
            } else {
                holder.mCheckBox.setVisibility(View.VISIBLE);

                if (userResponseVO.isSelect()) {
                    holder.mCheckBox.setImageResource(R.drawable.ic_checked);
                } else {
                    holder.mCheckBox.setImageResource(R.drawable.ic_uncheck);
                }
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClickListener(holder.getAdapterPosition(), mMyLiveList);
                }
            });
        }

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClickListener(int pos,List<UserResponseVO> myLiveList);
    }
    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.head)
        ImageView mHead;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_match)
        TextView mTvMatch;
        @BindView(R.id.root_view)
        RelativeLayout mRootView;
        @BindView(R.id.check_box)
        ImageView mCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


}