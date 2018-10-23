package org.zsq.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import org.zsq.playcamera.R;
import org.zsq.view.adapter.PopupAdapter;

import java.util.ArrayList;

/**
 * 功能：
 * <p>
 * Created by danke on 2018/10/23.
 */
public class CustomPopupWindow extends PopupWindow {
    private ICustomIPopupWindow custominterface;
    private Context mContext;
    private final PopupAdapter popupAdapter;
    private final View view;

    public CustomPopupWindow(final Context context, ICustomIPopupWindow custominterface){

        /**
         * 注意：我们的接口同时作为成员变量传入，因为我们用于监听子Item的数据监听
         * */
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.popup_layout, null);
        mContext=context;

        this.setContentView(view);
        //自定义基础，设置我们显示控件的宽，高，焦点，点击外部关闭PopupWindow操作
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //更新试图
        this.update();
        //设置背景
        ColorDrawable colorDrawable = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(colorDrawable);

        ListView mList = (ListView) view.findViewById(R.id.lv_popup_view);

        //数据填充
        ArrayList<String> Lists = new ArrayList<>();
        Lists.add("村里第一帅");
        Lists.add("镇里第一帅");
        Lists.add("市里第一帅");
        Lists.add("省里第一帅");
        Lists.add("中国第一帅");
        Lists.add("世界第一帅");

        //绑定适配器，设置子条目监听
        popupAdapter = new PopupAdapter(mContext,Lists);
        this.custominterface=custominterface;
        mList.setAdapter(popupAdapter);
        mList.setOnItemClickListener(onItemClickListener);
    }

    //监听子Item操作 -数据记录，关闭PopupWindow
    public AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            custominterface.setData(popupAdapter.getItem(position));
            dismiss();
        }
    };
}
