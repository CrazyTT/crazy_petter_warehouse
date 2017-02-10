package com.bjdv.lib.utils.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

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
import com.bjdv.lib.utils.R;
import com.bjdv.lib.utils.network.EncryptUtil;
import com.bjdv.lib.utils.network.RequestCallBack;
import com.bjdv.lib.utils.network.SysException;
import com.bjdv.lib.utils.util.LogUtils;
import com.bjdv.lib.utils.util.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: Activity基类<br>
 * Description: 网络功能、事件绑定、进度条<br>
 * Date: 16/5/16 <br>
 * Copyright (c) 2015 DATANG BJDV<br>
 *
 * @author phoon-think
 */
public  class BaseActivity extends AppCompatActivity  {
    private RequestQueue mRequestQueue;
    private int time = 15 * 1000;
    private String TAG;
    static private List<Activity> mList;

    static {
        mList = new ArrayList<>();
    }

    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList.add(this);
    }

    public void initNetwork(RequestQueue requestQueue, String TAG) {
        this.mRequestQueue = requestQueue;
        this.TAG = TAG;
    }

    public void initNetwork(RequestQueue requestQueue, String TAG, int time) {
        this.time = time;
        initNetwork(requestQueue, TAG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }

    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    private Dialog mProgressDialog;


    public void showProgress(String msg, boolean ifCancle) {
        if (this.mProgressDialog != null) {
            this.mProgressDialog.dismiss();
        }
//        mProgressDialog = ProgressDialog.show(this, "", "加载中,请稍候...", false, true);
        mProgressDialog = new Dialog(this, R.style.process_dialog);
        mProgressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = (TextView) mProgressDialog.findViewById(R.id.text);
        textView.setText(msg);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(ifCancle);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
            }
        });
        mProgressDialog.show();
    }

    public void stopProgress() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }

    public void showToast(String tips) {
        ToastUtils.showShort(this, tips);
    }


    public void requestData(String url, final String params, final RequestCallBack callback) {
        LogUtils.i(TAG, "[volly request]->url=" + url);
        LogUtils.i(TAG, "[volly params]->params=" + params);
        try {
            if (mRequestQueue == null) {
                ToastUtils.showShort(this, "尚未初始化网络请求，请先初始化");
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
}
