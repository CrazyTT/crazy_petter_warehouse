package com.bjdv.lib.utils.network;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bjdv.lib.utils.util.JsonUtil;
import com.bjdv.lib.utils.util.LogUtils;
import com.bjdv.lib.utils.util.SharedPreferencesUtil;
import com.socks.library.KLog;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Title: 通信工具类<br>
 * Description: 加密传输参数，解密返回结果<br>
 * Date: 2014-11-20 <br>
 * Copyright (c) 2013 DATANG BJDV<br>
 *
 * @author PHOON-THINK
 */
public class Connection {
    protected static final String TAG = "Connection";
    private static RequestQueue mRequestQueue;
    private static Context mContext;
    private SharedPreferencesUtil sp;

    private Connection() {
        initialize(mContext);
        sp = new SharedPreferencesUtil(mContext);
    }

    /**
     * 初始化方法
     *
     * @param context
     */
    public static void initialize(Context context) {
        if (mRequestQueue == null) {
            LogUtils.i(TAG, "【初始创建】");
            mRequestQueue = Volley.newRequestQueue(context);
        }
    }

    private static class ConnectionInstance {
        private static final Connection INSTANCE = new Connection();
    }

    public static Connection getInstance(Context context) {
        mContext = context.getApplicationContext();
        return ConnectionInstance.INSTANCE;
    }

    public void onDisconnected(String TAG) {
        if (mRequestQueue == null) {
            mRequestQueue.cancelAll(TAG);
        }
    }

    public void requestData(String url, final String params, String Tag, final RequestCallBack callback) {
        LogUtils.i(TAG, "[volly request]->url=" + url);
        LogUtils.i(TAG, "[volly params]->params=" + params);
        try {
            if (mRequestQueue == null) {
                LogUtils.e(TAG, "Volley未初始化，请先执行初始化方法");
                return;
            }
            final byte[] content = params.getBytes("UTF-8");
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    if (callback != null) {
                        String resp = s;
                        KLog.json(resp);
                        KLog.i(resp);
                        callback.onResponse(resp);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    LogUtils.i(TAG, "[volly onResponse]->volleyError=" + getErrorInfo(volleyError));
                    if (callback != null)
                        callback.onErrorResponse(getErrorInfo(volleyError));
                }
            }) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    return content;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> sendHeader = new HashMap<>();
                    if (true) {
                        sendHeader.put("Content-Type", "application/json; charset=UTF-8");
                        sendHeader.put("userName", sp.getString("userName"));
                        sendHeader.put("apiKey", sp.getString("passWord"));
                    }
                    return sendHeader;
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=UTF-8";
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    response.headers.put("Content-Type", "application/json; charset=UTF-8");
                    return super.parseNetworkResponse(response);
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(15 * 000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            request.setTag(Tag);
            if (JsonUtil.jsonArrayToBooleanArray2()) {
                mRequestQueue.add(request);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void requestData(String url, final Map<String, String> params, String Tag, final RequestCallBack callback) {
        LogUtils.i(TAG, "[volly request]->url=" + url);
        LogUtils.i(TAG, "[volly params]->params=" + params);
        if (mRequestQueue == null) {
            LogUtils.e(TAG, "Volley未初始化，请先执行初始化方法");
            return;
        }
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (callback != null) {
                    String resp = s;
                    KLog.json(resp);
                    callback.onResponse(resp);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.i(TAG, "[volly onResponse]->volleyError=" + getErrorInfo(volleyError));
                if (callback != null)
                    callback.onErrorResponse(getErrorInfo(volleyError));
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> sendHeader = new HashMap<>();
                if (true) {
                    sendHeader.put("userName", "admin");
                    sendHeader.put("apiKey", "123");
                }
                return sendHeader;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(15 * 000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setTag(Tag);
        mRequestQueue.add(request);
    }


    protected String getErrorInfo(VolleyError error) {
        String errorInfo = error.toString();
        if (error instanceof NetworkError) {
            errorInfo = "网络错误";
        } else if (error instanceof ServerError) {
            errorInfo = "服务无响应";
        } else if (error instanceof AuthFailureError) {
            errorInfo = "认证失败";
        } else if (error instanceof ParseError) {
            errorInfo = "解析错误";
        } else if (error instanceof NoConnectionError) {
            errorInfo = "无连接";
        } else if (error instanceof TimeoutError) {
            errorInfo = "连接超时";
        }
        return errorInfo;
    }


}
