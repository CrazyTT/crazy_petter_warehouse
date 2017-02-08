package com.bjdv.lib.utils.base;

import android.content.Intent;

import com.bjdv.lib.utils.network.Connection;
import com.bjdv.lib.utils.network.RequestCallBack;
import com.bjdv.lib.utils.util.JsonUtil;

import net.wequick.small.Small;

import org.json.JSONObject;

/**
 * Title: 通用处理DOM<br>
 * Description: <br>
 * Date: 16/6/6 <br>
 * Copyright (c) 2015 DATANG BJDV<br>
 *
 * @author phoon-think
 */
public abstract class BasePresenter implements IPresenter<String> {

    private final IBaseView iBaseView;
    private final String tag;
    public Connection mConnection;
    private BaseActivity context;

    public BasePresenter(IBaseView iBaseView, BaseActivity context, String tag) {
        this.iBaseView = iBaseView;
        this.context = context;
        this.tag = tag;
    }

    /**
     * 请求数据 通用处理
     *
     * @param url
     * @param params
     * @param dataCallBack
     */
    @Override
    public void requestData(String url, String params, final DataCallBack dataCallBack) {
        mConnection = Connection.getInstance(context);
        iBaseView.showLoading();
        RequestCallBack callBack = new RequestCallBack() {
            @Override
            public void onResponse(String response) {

                JSONObject respJsonObject = JsonUtil.from(response);
                String resultCode = JsonUtil.getString(respJsonObject, "returnCode");
                String resultInfo = JsonUtil.getString(respJsonObject, "resultInfo");
                String responseBody = JsonUtil.getString(respJsonObject, "responseBody");
                if ("0".equals(resultCode)) {
                    dataCallBack.onSuccess(responseBody);
                } else {
                    dataCallBack.onFailure(resultInfo);//失败提示
                    iBaseView.showTips(resultInfo);
                }
                iBaseView.hideLoading();
            }

            @Override
            public void onErrorResponse(String errorInfo) {
                dataCallBack.onFailure(errorInfo);
                iBaseView.hideLoading();
                iBaseView.showTips(errorInfo);
            }

            @Override
            public void onTokenExpire() {
                dataCallBack.onFailure("登录过期");
                iBaseView.showTips("登录过期，请重新登录");
                Intent intent = Small.getIntentOfUri("login", BasePresenter.this.context);
                BasePresenter.this.context.startActivity(intent);
                BasePresenter.this.context.exit();
            }
        };
        mConnection.requestData(url, params, tag, callBack);
    }

    /**
     * 请求数据 自定义处理
     *
     * @param url
     * @param params
     * @param dataCallBack
     * @param flag
     */
    public void requestData(String url, String params, final DataCallBack dataCallBack, boolean flag) {
        mConnection = Connection.getInstance(context);
        RequestCallBack callBack = new RequestCallBack() {
            public void onResponse(String response) {
                JSONObject respJsonObject = JsonUtil.from(response);
                String resultCode = JsonUtil.getString(respJsonObject, "returnCode");
                String resultInfo = JsonUtil.getString(respJsonObject, "resultInfo");
                String responseBody = JsonUtil.getString(respJsonObject, "responseBody");
                if ("0".equals(resultCode)) {
                    dataCallBack.onSuccess(responseBody);
                } else {
                    dataCallBack.onFailure(resultInfo);
                    BasePresenter.this.iBaseView.showTips(resultInfo);
                }

                BasePresenter.this.iBaseView.hideLoading();
            }

            public void onErrorResponse(String errorInfo) {
                dataCallBack.onFailure(errorInfo);
                BasePresenter.this.iBaseView.hideLoading();
                BasePresenter.this.iBaseView.showTips(errorInfo);
            }

            public void onTokenExpire() {
                Intent intent = Small.getIntentOfUri("login", BasePresenter.this.context);
                BasePresenter.this.context.startActivity(intent);
                BasePresenter.this.context.exit();
            }
        };
        mConnection.requestData(url, params, tag, callBack);
    }

    /**
     * 中断数据请求
     */
    @Override
    public void discardData() {
        mConnection.onDisconnected(tag);
    }

}
