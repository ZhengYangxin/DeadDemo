package org.zsq.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 功能：
 * <p>
 * Created by danke on 2018/10/23.
 */
public abstract class BaseCallBackListen<T> implements NetworkUtils.CallBackListen<T> {
    private Context mContext;

    public BaseCallBackListen(Context context) {
        this.mContext = context;
    }

    @Override
    public void onErrorResponse(String error) {
        Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
    }
}
