package org.zsq.util;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.zsq.view.ProgressBox;

import java.util.logging.Handler;

/**
 * 功能：
 * <p>
 * Created by danke on 2018/10/23.
 */
public class NetworkUtils {

    private static RequestQueue requestQueue;

    public static void init(Context context) {
        //创建一个请求队列
        requestQueue =  Volley.newRequestQueue(context);
    }

    public static <T> void get(String url, final Class<T> clazz, final CallBackListen callBackListen) {
        get(null, url, clazz, callBackListen);
    }

    public static <T> void get(final ProgressBox progressBox, String url, final Class<T> clazz, final CallBackListen callBackListen) {
        if (progressBox != null) {
            progressBox.show();
        }
        //创建一个请求
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            //正确接收数据回调
            @Override
            public void onResponse(String data) {
                if (callBackListen != null) {
                    try {
                        callBackListen.onResponse(JSON.parseObject(data, clazz));
                    } catch (Exception e) {
                        callBackListen.onResponse(e.getMessage());
                        e.printStackTrace();
                    } finally {
//                        if (progressBox != null) {
//                            progressBox.hide();
//                        }
                    }
                }
            }
        }, new Response.ErrorListener() {//异常后的监听数据
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (callBackListen != null) {
                    callBackListen.onErrorResponse(volleyError);
                }
                if (progressBox != null) {
                    progressBox.hide();
                }
            }
        });
        //将get请求添加到队列中
        requestQueue.add(stringRequest);
    }

    public interface CallBackListen<T> {
        void onResponse(T data);
        void onErrorResponse(VolleyError volleyError);
    }

}
