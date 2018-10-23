package org.zsq.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.zsq.playcamera.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * desc :
 * time  : 23/10/18.
 * author : pielan
 */

public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentView(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_person_recommend, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContentView content = (ContentView) holder;
        content.contextText.setText("content" + position);
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class ContentView extends RecyclerView.ViewHolder {
        @BindView(R.id.context_text)
        TextView contextText;

        public ContentView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
