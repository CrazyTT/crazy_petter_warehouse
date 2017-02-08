package com.bjdv.lib.utils.base;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bjdv.lib.utils.network.EncryptUtil;
import com.bjdv.lib.utils.network.RequestCallBack;
import com.bjdv.lib.utils.network.SysException;
import com.bjdv.lib.utils.util.LogUtils;
import com.bjdv.lib.utils.util.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Title: <br>
 * Description: <br>
 * Date: 16/5/16 <br>
 * Copyright (c) 2015 DATANG BJDV<br>
 *
 * @author phoon-think
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    private RequestQueue mRequestQueue;
    private int time = 15 * 1000;
    private String TAG;

    public void initNetwork(RequestQueue requestQueue, String TAG) {
        this.mRequestQueue = requestQueue;
        this.TAG = TAG;
    }

    public void initNetwork(RequestQueue requestQueue, String TAG, int time) {
        this.time = time;
        initNetwork(requestQueue, TAG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }

    private Dialog mProgressDialog;

    public void assambleView(View view, int... ids) {
        for (int id : ids) {
            view.findViewById(id).setOnClickListener(this);
        }
    }

    public void showProgress() {
/*        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.custom_loading, null);
        if (mProgressDialog == null)
        {
            mProgressDialog = new Dialog(this, R.style.AppTheme_Loading);
            mProgressDialog.setContentView(v);
            mProgressDialog.setCancelable(true);
        }*/
        mProgressDialog.show();
    }

    public void stopProgress() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }


    public void requestData(String url, final String params, final RequestCallBack callback) {
        LogUtils.i(TAG, "[volly request]->url=" + url);
        LogUtils.i(TAG, "[volly params]->params=" + params);
        try {
            if (mRequestQueue == null) {
                ToastUtils.showShort(getActivity(), "尚未初始化网络请求，请先初始化");
                return;
            }
            final byte[] content = EncryptUtil.encryptThreeDESECB(params).getBytes("UTF-8");
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    if (callback != null) {
                        try {
                            String resp = EncryptUtil.decryptThreeDESECB(s);
                            callback.onResponse(resp);
                        } catch (SysException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
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
                    Map<String, String> sendHeader = new HashMap<String, String>();
                    sendHeader.put("Content-Type", "application/json; charset=UTF-8");
                    return sendHeader;
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=UTF-8";
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(time,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            request.setTag(TAG);
            mRequestQueue.add(request);
        } catch (SysException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
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

    /**
     * 预加载
     */
    private boolean isPrepared;

    /**
     * 第一次onResume中的调用onVisible避免操作与onFirstVisible操作重复
     */
    private boolean isFirstResume = true;

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint())
            onVisible();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint())
            onInvisible();
    }

    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else
                onVisible();
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstInvisible();
            } else
                onFirstInvisible();
        }
    }

    public synchronized void initPrepare() {
        if (isPrepared)
            onFirstVisible();
        else
            isPrepared = true;
    }

    /**
     * fragment可见(切换回来或onResume)
     */
    public abstract void onVisible();

    /**
     * fragment不可见(切换掉或onPause)
     */
    public void onInvisible() {

    }

    /**
     * 第一次fragment可见（初始化工作）
     */
    public abstract void onFirstVisible();


    /**
     * 第一次fragment不可见（不建议在此处理事件）
     */
    public void onFirstInvisible() {

    }

    public abstract void onViewClick(View v);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                onViewClick(v);
                break;

        }
    }
}
