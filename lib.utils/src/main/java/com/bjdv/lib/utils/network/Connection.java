package com.bjdv.lib.utils.network;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
import com.bjdv.lib.utils.util.LogUtils;
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

    private Connection() {
        initialize(mContext);
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
            //final byte[] content = params.getBytes("UTF-8");
            final byte[] content = EncryptUtil.encryptThreeDESECB(params).getBytes("UTF-8");
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    if (callback != null) {
                        //String resp = s;
                        String resp = null;
                        try {
                            resp = EncryptUtil.decryptThreeDESECB(s);
                        } catch (SysException e) {
                            e.printStackTrace();
                        }
                        KLog.json(resp);
                        callback.onResponse(resp);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    LogUtils.i(TAG, "[volly onResponse]->volleyError=" + getErrorInfo(volleyError));

                    if (volleyError instanceof AuthFailureError) {
                        callback.onTokenExpire();
                    }

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
                    sendHeader.put("Content-Type", "application/json; charset=UTF-8");
                    String Authorization = PreferenceManager.getDefaultSharedPreferences(mContext).getString("Authorization", null);
                    if (Authorization != null) {
                        LogUtils.i(">>>cookies=" + Authorization);
                        sendHeader.put("Authorization", Authorization);
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
                    Map<String, String> responseHeaders = response.headers;
                    String Authorization = responseHeaders.get("Authorization");
                    if (Authorization != null) {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
                        editor.putString("Authorization", Authorization);
                        editor.commit();
                        LogUtils.i(TAG, "[Authorization]-->" + Authorization);
                    }
                    return super.parseNetworkResponse(response);
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(15 * 000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            request.setTag(Tag);
            mRequestQueue.add(request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (SysException e) {
            e.printStackTrace();
        }
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
