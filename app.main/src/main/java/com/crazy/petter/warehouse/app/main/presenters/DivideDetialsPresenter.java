package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.crazy.petter.warehouse.app.main.beans.PickWaveDtBean;
import com.crazy.petter.warehouse.app.main.views.DivideDetialsView;

/**
 * Created by liuliuchen on 2017/2/16.
 */

public class DivideDetialsPresenter extends BasePresenter {
    DivideDetialsView mDivideDetialsView;

    public DivideDetialsPresenter(DivideDetialsView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        mDivideDetialsView = iBaseView;
    }

    public void getOrderAll(String params) {
        requestData(Constant.SERVER_URL_BASE + Constant.QueryReBinWallWaveDt, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                PickWaveDtBean scanStoreageBean = JsonFormatter.getInstance().json2object(o.toString(), PickWaveDtBean.class);
                mDivideDetialsView.setBottom(scanStoreageBean.getTotalQty(), scanStoreageBean.getTotalPickQty());
            }

            @Override
            public void onFailure(String s) {
                mDivideDetialsView.getOrderAllFailure();
            }
        });

    }

    public void getOrderOne(String params) {
        requestData(Constant.SERVER_URL_BASE + Constant.QueryReBinWallWaveDt, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                PickWaveDtBean scanStoreageBean = JsonFormatter.getInstance().json2object(o.toString(), PickWaveDtBean.class);
                if (scanStoreageBean.getData() != null && scanStoreageBean.getData().size() > 0) {
                    mDivideDetialsView.setOne(scanStoreageBean.getData().get(0));
                } else {
                    mDivideDetialsView.showTips("没有查到明细");
                }

            }

            @Override
            public void onFailure(String s) {
            }
        });
    }

    public void commit(String params) {
        context.showProgress("确认分货中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.QueryReBinWallWave, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                mDivideDetialsView.commitOk();
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                context.stopProgress();
            }
        });
    }
}
