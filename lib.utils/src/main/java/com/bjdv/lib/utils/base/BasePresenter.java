package com.bjdv.lib.utils.base;

import com.bjdv.lib.utils.network.Connection;
import com.bjdv.lib.utils.network.RequestCallBack;
import com.bjdv.lib.utils.util.JsonFormatter;

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
    public BaseActivity context;

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
        RequestCallBack callBack = new RequestCallBack() {
            @Override
            public void onResponse(String response) {
                BaseBean baseBean = JsonFormatter.getInstance().json2object(response, BaseBean.class);
                if (baseBean.isSuccess()) {
                    dataCallBack.onSuccess(response);
                } else {
                    dataCallBack.onFailure(baseBean.getMessage());//失败提示
                    iBaseView.showTips(baseBean.getMessage());
                }
            }

            @Override
            public void onErrorResponse(String errorInfo) {
                dataCallBack.onFailure(errorInfo);
                iBaseView.showTips(errorInfo);
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
